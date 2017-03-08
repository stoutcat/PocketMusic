package com.example.q.pocketmusic.module.search.recommend;


import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.net.LoadRecommendList;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.song.SongActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cloud on 2016/11/14.
 */

public class RecommendListActivityPresenter  extends BasePresenter {
    private IView activity;
    private Context context;
    private List<Song> list = new ArrayList<>();
    private int mPage;

    public RecommendListActivityPresenter(IView activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    //可以得到推荐列表
    public void getList() {
        String url = Constant.RECOMMEND_LIST_URL + "1" + ".html";
        new LoadRecommendList(list) {
            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                setIntResult(integer);
            }
        }.execute(url);
    }

    //推荐列表加载更多
    public void loadMore() {
        mPage++;
        String url = Constant.RECOMMEND_LIST_URL + mPage + ".html";
        new LoadRecommendList(list) {
            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                activity.loadMore(list);
            }
        }.execute(url);
    }

    private void setIntResult(int result) {
        if (result == Constant.SUCCESS) {
            activity.setList(list);
        } else {
            activity.loadFail();
        }
    }

    public void setPage(int page) {
        this.mPage=page;
    }

    public void enterSongActivity(Song song) {
        Intent intent = new Intent(context, SongActivity.class);
        SongObject object=new SongObject(song, Constant.FROM_RECOMMEND,Constant.SHOW_COLLECTION_MENU,Constant.NET);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL,object);
        context.startActivity(intent);
    }

    public interface IView {
        void setList(List<Song> list);

        void loadFail();

        void loadMore(List<Song> list);
    }
}
