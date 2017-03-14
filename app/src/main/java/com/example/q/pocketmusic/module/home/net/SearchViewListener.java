package com.example.q.pocketmusic.module.home.net;

import android.animation.ObjectAnimator;
import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;


import com.example.q.pocketmusic.R;


/**
 * Created by Cloud on 2017/1/29.
 */

public class SearchViewListener extends RecyclerView.OnScrollListener {
    private View view;
    private float now = 0f;
    private float max;

    public SearchViewListener(View view) {
        this.view = view;
        Context context = view.getContext();
        this.max = context.getResources().getDimension(R.dimen.search_view_top_offset);//直接放回px，这里是205dp
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        //防止快速滑动定位失败
        if (newState == RecyclerView.SCROLL_STATE_SETTLING && (now >= max || now <= 0)) {
            ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", -max + 10);//有一点点的偏移
            translationY.setInterpolator(new LinearInterpolator());
            translationY.start();
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        float old = now;
        now = dy + now;
        if (now >= max) {
            return;
        } else {
            ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", -old, -now);
            translationY.setInterpolator(new LinearInterpolator());
            translationY.start();
        }

    }



}
