package com.example.q.pocketmusic.module.search.type;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.net.LoadTypeSongList;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.song.SongActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by YQ on 2016/8/29.
 */
public class SongTypeActivityPresenter extends BasePresenter {
    private Context context;
    private IView activity;
    private List<Song> list = new ArrayList<>();
    private int mPage;

    public SongTypeActivityPresenter(IView activity, Context context) {
        this.context = context;
        this.activity = activity;
    }

    public void loadMore(int typeId) {
        mPage++;
        String url;
        url = Constant.BASE_URL + "/qiyue/" + Constant.namesUrl[typeId] + mPage + ".html";//http://www.qupu123.com/qiyue/kouqin/2.html
        new LoadTypeSongList(list, typeId) {
            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                setIntResult(integer);
            }
        }.execute(url);
    }

    public void setList(int typeId) {
        new LoadTypeSongList(list, typeId) {
            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                setIntResult(integer);
            }
        }.execute(Constant.BASE_URL + "/qiyue/" + Constant.namesUrl[typeId] + mPage + ".html");
    }


    private void setIntResult(int result) {
        if (result == Constant.SUCCESS) {
            activity.setMore(list);
        } else {
            activity.loadFail();
        }
    }

    public void setPage(int page) {
        this.mPage = page;
    }

    public void enterSongActivity(Song song) {
        Intent intent = new Intent(context, SongActivity.class);
        SongObject object = new SongObject(song, Constant.FROM_TYPE, Constant.SHOW_COLLECTION_MENU, Constant.NET);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, object);
        context.startActivity(intent);
    }

    public interface IView {
        void setMore(List<Song> list);

        void loadFail();

    }
}
