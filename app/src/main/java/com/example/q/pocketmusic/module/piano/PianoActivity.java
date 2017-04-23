package com.example.q.pocketmusic.module.piano;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PianoActivity extends BaseActivity implements PianoPresenter.IView {
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.do_1)
    Button do1;
    @BindView(R.id.re_2)
    Button re2;
    @BindView(R.id.mi_3)
    Button mi3;
    @BindView(R.id.fa_4)
    Button fa4;
    @BindView(R.id.sol_5)
    Button sol5;
    @BindView(R.id.la_6)
    Button la6;
    @BindView(R.id.xi_7)
    Button xi7;
    @BindView(R.id.parent)
    LinearLayout parent;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.tab_iv)
    ImageView tabIv;
    @BindView(R.id.enter_iv)
    ImageView enterIv;
    @BindView(R.id.bo_lang_iv)
    ImageView boLangIv;
    @BindView(R.id.title_edt)
    EditText titleEdt;

    private PianoPresenter presenter;

    @Override
    public int setContentResource() {
        return R.layout.activity_piano;
    }

    @Override
    public void setListener() {

    }

    //强制横屏
    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void init() {
        presenter = new PianoPresenter(this, this);
    }


    @OnClick({R.id.back_iv, R.id.tab_iv, R.id.enter_iv, R.id.bo_lang_iv, R.id.do_1, R.id.re_2, R.id.mi_3, R.id.fa_4, R.id.sol_5, R.id.la_6, R.id.xi_7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                contentTv.setText(presenter.setBack());
                break;
            case R.id.tab_iv:
                contentTv.setText(presenter.setTab());
                break;
            case R.id.enter_iv:
                contentTv.setText(presenter.setEnter());
                break;
            case R.id.bo_lang_iv:
                contentTv.setText(presenter.setBoLang());
                break;
            case R.id.do_1:
                contentTv.setText(presenter.sound(view.getId()));
                break;
            case R.id.re_2:
                contentTv.setText(presenter.sound(view.getId()));
                break;
            case R.id.mi_3:
                contentTv.setText(presenter.sound(view.getId()));
                break;
            case R.id.fa_4:
                contentTv.setText(presenter.sound(view.getId()));
                break;
            case R.id.sol_5:
                contentTv.setText(presenter.sound(view.getId()));
                break;
            case R.id.la_6:
                contentTv.setText(presenter.sound(view.getId()));
                break;
            case R.id.xi_7:
                contentTv.setText(presenter.sound(view.getId()));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
