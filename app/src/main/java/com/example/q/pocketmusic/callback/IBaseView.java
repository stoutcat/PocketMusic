package com.example.q.pocketmusic.callback;

/**
 * Created by Cloud on 2017/1/28.
 */

public interface IBaseView {
    void showLoading(boolean isShow);

    void finish();

    int setContentResource();

    void setListener();

    void init();
}
