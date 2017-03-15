package com.example.q.pocketmusic.util;

import android.content.Context;

import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.model.bean.share.ShareSong;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 81256 on 2017/3/14.
 */

public class ACacheUtil {
    //默认缓存10条
    public static void putShareSongCache(Context context, List<ShareSong> list) {
        ACache cache = ACache.get(context);
        for (int i = 0; i < 10; i++) {
            cache.put("share_song_key" + i, list.get(i), 1 * ACache.TIME_HOUR);//默认保存一个小时
        }
    }

    public static List<ShareSong> getShareSongCache(Context context) {
        ACache cache = ACache.get(context);
        List<ShareSong> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ShareSong shareSong = (ShareSong) cache.getAsObject("share_song_key" + i);
            if (shareSong == null) {
                return null;
            }
            list.add(shareSong);
        }
        return list;
    }


    public static void putAskSongCache(Context context, List<AskSongPost> list) {
        ACache cache = ACache.get(context);
        for (int i = 0; i < 10; i++) {
            cache.put("ask_song_key" + i, list.get(i), 1 * ACache.TIME_HOUR);
        }
    }

    public static List<AskSongPost> getAskSongCache(Context context) {
        ACache cache = ACache.get(context);
        List<AskSongPost> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AskSongPost askSongPost = (AskSongPost) cache.getAsObject("ask_song_key" + i);
            if (askSongPost == null) {
                return null;
            }
            list.add(askSongPost);
        }
        return list;
    }

    public static List<Song> getRecommendCache(Context context) {
        ACache cache = ACache.get(context);
        List<Song> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Song song = (Song) cache.getAsObject("recommend_song_key" + i);
            if (song == null) {
                return null;
            }
            list.add(song);
        }
        return list;
    }

    public static List<Song> putRecommendCache(Context context, List<Song> list) {
        ACache cache = ACache.get(context);
        for (int i = 0; i < 10; i++) {
            cache.put("recommend_song_key" + i, list.get(i), 1 * ACache.TIME_HOUR);
        }
        return list;
    }
}
