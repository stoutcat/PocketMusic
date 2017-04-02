package com.example.q.pocketmusic.module.search.search;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.q.pocketmusic.callback.IBaseList;
import com.example.q.pocketmusic.callback.IBaseView;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;

import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.share.SharePic;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.model.net.LoadSearchSongList;
import com.example.q.pocketmusic.callback.IBasePresenter;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by YQ on 2016/9/4.
 */
public class SearchListActivityPresenter extends BasePresenter {
    private Context context;
    private IView activity;
    private int mPage;

    public SearchListActivityPresenter(Context context, IView activity) {
        this.context = context;
        this.activity = activity;
    }

    public int getmPage() {
        return mPage;
    }

    //这里有问题，最好是能够先搜Bmob再搜全网
    public void getList(final String query) {
        //来自网络
        final List<Song> songs = new ArrayList<>();
        getListFromNet(query, songs);
    }

    private void getListFromNet(String query, final List<Song> songs) {
        new LoadSearchSongList(mPage) {
            @Override
            protected void onPostExecute(final List<Song> list) {
                if (list != null) {
                    songs.addAll(list);//Bmob和网络搜索组合
                }
                activity.setList(songs);
            }
        }.execute(query);
    }

    public void setPage(int page) {
        this.mPage = page;
    }

    public void enterSongActivity(Song song, int searchFrom) {
        Intent intent = new Intent(context, SongActivity.class);
        SongObject object = new SongObject(song, searchFrom, Constant.SHOW_COLLECTION_MENU, Constant.NET);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, object);
        context.startActivity(intent);
    }

    public interface IView extends IBaseList {
        void setList(List<Song> list);
    }
}
