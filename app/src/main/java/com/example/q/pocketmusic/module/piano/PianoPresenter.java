package com.example.q.pocketmusic.module.piano;

import android.content.Context;
import android.widget.LinearLayout;

import com.example.q.pocketmusic.util.MusicUtils;

/**
 * Created by 鹏君 on 2017/4/22.
 */

public class PianoPresenter {
    private Context context;
    private IView activity;
    private MusicUtils utils;
    private StringBuilder builder;

    public PianoPresenter(Context context, IView activity) {
        this.context = context;
        this.activity = activity;
        utils = new MusicUtils(context);
        builder = new StringBuilder();
    }

    public String sound(int id) {
        utils.soundPlay(id);
        builder.append(utils.getNote(id));
        return builder.toString();
    }

    public String setBack() {
        if (builder.length() <= 0) {
            return "";
        }
        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    public String setTab() {
        return builder.append("  ").toString();
    }

    public String setEnter() {
        return builder.append("\n").toString();
    }

    public String setBoLang() {
        return builder.append("~").toString();
    }

    public interface IView {

    }
}
