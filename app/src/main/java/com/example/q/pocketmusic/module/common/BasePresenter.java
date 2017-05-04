package com.example.q.pocketmusic.module.common;

import com.example.q.pocketmusic.model.bean.ask.AskSongPost;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Cloud on 2017/1/31.
 */

public abstract class BasePresenter {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.CHINA);
    public final String TAG = this.getClass().getName();


}
