package com.example.q.pocketmusic.model.net;

import android.content.Context;
import android.os.AsyncTask;

import com.example.q.pocketmusic.model.bean.local.LocalSong;
import com.example.q.pocketmusic.model.db.LocalSongDao;

import java.util.List;

/**
 * Created by Cloud on 2017/2/10.
 */

public class LoadLocalSongList extends AsyncTask<Void,Void,List<LocalSong>>{
    private LocalSongDao localSongDao;
    private Context context;

    public LoadLocalSongList(LocalSongDao localSongDao, Context context) {
        this.localSongDao = localSongDao;
        this.context = context;
    }

    @Override
    protected List<LocalSong> doInBackground(Void... params) {
        List<LocalSong> list = localSongDao.queryForAll();
        return list;
    }
}
