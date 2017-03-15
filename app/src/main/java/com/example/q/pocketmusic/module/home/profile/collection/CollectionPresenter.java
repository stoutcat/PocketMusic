package com.example.q.pocketmusic.module.home.profile.collection;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.callback.IBaseList;
import com.example.q.pocketmusic.callback.IBaseView;
import com.example.q.pocketmusic.callback.ToastQueryListListener;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.collection.CollectionPic;
import com.example.q.pocketmusic.model.bean.collection.CollectionSong;
import com.example.q.pocketmusic.module.song.SongActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Cloud on 2016/11/14.
 */

public class CollectionPresenter {
    private IView activity;
    private Context context;
    private MyUser user;

    public CollectionPresenter(IView activity, Context context, MyUser user) {
        this.activity = activity;
        this.context = context;
        this.user = user;
    }

    //获得收藏曲谱列表
    public void getCollectionList() {
        BmobQuery<CollectionSong> query = new BmobQuery<>();
        query.order("-updatedAt");
        query.addWhereRelatedTo("collections", new BmobPointer(user));//在user表的Collections找user
        query.findObjects(new ToastQueryListener<CollectionSong>(context, activity) {
            @Override
            public void onSuccess(List<CollectionSong> list) {
                activity.setCollectionList(list);
            }
        });
    }

    //先查询，后进入SongActivity
    public void queryAndEnterSongActivity(final CollectionSong collectionSong) {
        activity.showLoading(true);
        BmobQuery<CollectionPic> query = new BmobQuery<>();
        query.addWhereEqualTo("collectionSong", new BmobPointer(collectionSong));
        query.findObjects(new ToastQueryListener<CollectionPic>(context, activity) {
            @Override
            public void onSuccess(List<CollectionPic> list) {
                activity.showLoading(false);
                Song song = new Song();
                song.setName(collectionSong.getName());
                song.setContent(collectionSong.getContent());
                List<String> urls = new ArrayList<String>();
                for (CollectionPic pic : list) {
                    urls.add(pic.getUrl());
                }
                song.setIvUrl(urls);
                song.setNeedGrade(collectionSong.getNeedGrade());//设置消耗
                Intent intent = new Intent(context, SongActivity.class);
                SongObject songObject = new SongObject(song, Constant.FROM_COLLECTION, Constant.SHOW_ONLY_DOWNLOAD, Constant.NET);
                intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, songObject);
                context.startActivity(intent);
            }
        });


    }

    //删除收藏
    public void deleteCollection(final CollectionSong collectionSong) {
        activity.showLoading(true);
        BmobRelation relation = new BmobRelation();
        relation.remove(collectionSong);
        user.setCollections(relation);
        user.update(new ToastUpdateListener(context, activity) {
            @Override
            public void onSuccess() {
                //删除收藏多个图片表,
                BmobQuery<CollectionPic> query = new BmobQuery<CollectionPic>();
                query.addWhereEqualTo("collectionSong", collectionSong);
                query.findObjects(new ToastQueryListener<CollectionPic>(context, activity) {
                    @Override
                    public void onSuccess(List<CollectionPic> list) {
                        List<BmobObject> pics = new ArrayList<BmobObject>();
                        pics.addAll(list);
                        new BmobBatch().deleteBatch(pics).doBatch(new ToastQueryListListener<BatchResult>(context, activity) {
                            @Override
                            public void onSuccess(List<BatchResult> list) {
                                //删除收藏记录
                                collectionSong.delete(new ToastUpdateListener(context, activity) {
                                    @Override
                                    public void onSuccess() {
                                        activity.showLoading(false);
                                        activity.deleteCollectionResult(collectionSong);
                                    }
                                });
                            }
                        });
                    }
                });

            }
        });
    }


    public interface IView extends IBaseList {

        void setCollectionList(List<CollectionSong> list);

        void deleteCollectionResult(CollectionSong item);
    }
}
