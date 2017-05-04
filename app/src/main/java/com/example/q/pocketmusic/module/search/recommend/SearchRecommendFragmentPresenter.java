package com.example.q.pocketmusic.module.search.recommend;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.q.pocketmusic.callback.IBaseView;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.collection.CollectionCount;
import com.example.q.pocketmusic.model.bean.collection.CollectionSong;
import com.example.q.pocketmusic.model.net.LoadRecommendList;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.search.recommend.list.RecommendListActivity;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.ACacheUtil;
import com.example.q.pocketmusic.util.LogUtils;
import com.example.q.pocketmusic.util.MyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by 81256 on 2017/4/14.
 */

public class SearchRecommendFragmentPresenter extends BasePresenter {
    private Context context;
    private IView fragment;

    public SearchRecommendFragmentPresenter(Context context, IView fragment) {
        this.context = context;
        this.fragment = fragment;
    }


    //得到推荐列表
    public void getRecommendList() {
        String url = Constant.RECOMMEND_LIST_URL + "1" + ".html";
        new LoadRecommendList() {
            @Override
            protected void onPostExecute(List<Song> list) {
                super.onPostExecute(list);
                if (list == null) {
                    return;
                }
                for (int i = list.size() - 1; i >= 10; i--) {
                    list.remove(i);
                }
                fragment.setRecommendList(list);
            }
        }.execute(url);
    }

    //进入推荐列表
    public void enterRecommendListActivity() {
        context.startActivity(new Intent(context, RecommendListActivity.class));
    }

    //进入歌曲详情
    public void enterSongActivityByRecommendTag(Song song) {
        Intent intent = new Intent(context, SongActivity.class);
        SongObject object = new SongObject(song, Constant.FROM_RECOMMEND, Constant.SHOW_COLLECTION_MENU, Constant.NET);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, object);
        context.startActivity(intent);
    }


    interface IView extends IBaseView {
        void setRecommendList(List<Song> list);
    }
}
