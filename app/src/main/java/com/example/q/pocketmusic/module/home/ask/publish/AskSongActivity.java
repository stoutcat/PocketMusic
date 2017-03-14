package com.example.q.pocketmusic.module.home.ask.publish;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.view.widget.view.TextEdit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cloud on 2016/11/14.
 */

public class AskSongActivity extends AuthActivity implements AskSongPresenter.IView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_tet)
    TextEdit titleTet;
    @BindView(R.id.content_tet)
    TextEdit contentTet;
    @BindView(R.id.ok_btn)
    Button okBtn;
    private AskSongPresenter presenter;
    public static final int REQUEST_ASK = 1001;//跳转到求谱界面


    @Override
    public int setContentResource() {
        return R.layout.activity_ask_song;
    }

    @Override
    public void setListener() {

    }

    @Override
    public void init() {
        initToolbar(toolbar, "求谱信息");
        presenter = new AskSongPresenter(this, this);
    }


    @OnClick({R.id.ok_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok_btn:
                String title = titleTet.getInputString();
                String content = contentTet.getInputString();
                presenter.askForSong(title, content, user);
                break;
        }
    }

    @Override
    public void setAskResult(Integer success) {
        setResult(success);
    }
}