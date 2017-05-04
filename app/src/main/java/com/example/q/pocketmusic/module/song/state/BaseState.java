package com.example.q.pocketmusic.module.song.state;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.ask.AskSongComment;

/**
 * Created by 鹏君 on 2017/5/4.
 */
//状态基类
public class BaseState {
    private Song song;
    private int loadingWay;

    public BaseState(Song song, int loadingWay) {
        this.song = song;
        this.loadingWay = loadingWay;
    }

    public Song getSong() {
        return song;
    }

    public int getLoadingWay() {
        return loadingWay;
    }
}
