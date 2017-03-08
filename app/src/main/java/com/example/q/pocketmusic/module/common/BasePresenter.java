package com.example.q.pocketmusic.module.common;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Cloud on 2017/1/31.
 */

public class BasePresenter {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.CHINA);
    public final String TAG=this.getClass().getName();
}
