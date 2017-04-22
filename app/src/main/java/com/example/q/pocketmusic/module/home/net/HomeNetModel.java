package com.example.q.pocketmusic.module.home.net;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.util.ACacheUtil;
import com.example.q.pocketmusic.util.BmobUtil;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by 鹏君 on 2017/4/22.
 */

public class HomeNetModel {
    private BmobUtil bmobUtil;

    public HomeNetModel() {
        bmobUtil = new BmobUtil();
    }

    public void getInitShareList(ToastQueryListener<ShareSong> listener) {
        bmobUtil.getInitList(ShareSong.class, "user", listener);
    }

    public void getMoreShareList(int mPage, ToastQueryListener<ShareSong> toastQueryListener) {
        bmobUtil.getMoreList(ShareSong.class, "user", mPage, toastQueryListener);
    }


}
