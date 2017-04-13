package com.example.q.pocketmusic.module.home.profile.contribution;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.IDisplayStrategy;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.util.CheckUserUtil;
import com.example.q.pocketmusic.util.DisplayStrategy;
import com.example.q.pocketmusic.util.LogUtils;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContributionActivity extends AuthActivity implements SwipeRefreshLayout.OnRefreshListener, ContributionPresenter.IView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.top_iv)
    ImageView topIv;
    @BindView(R.id.nick_name_tv)
    TextView nickNameTv;
    @BindView(R.id.contribution_tv)
    TextView contributionTv;
    private ContributionAdapter adapter;
    private ContributionPresenter presenter;

    @Override
    public int setContentResource() {
        return R.layout.activity_contribution;
    }


    @Override
    public void setListener() {
        adapter = new ContributionAdapter(this);
        recycler.setRefreshListener(this);
    }


    @Override
    public void initView() {
        presenter = new ContributionPresenter(this, this);
        new DisplayStrategy().displayCircle(this, user.getHeadImg(), topIv);
        nickNameTv.setText(user.getNickName());
        contributionTv.setText("硬币：" + user.getContribution()+"枚");
        initToolbar(toolbar, "硬币榜");
        initRecyclerView(recycler, adapter);
        onRefresh();
    }


    @Override
    public void onRefresh() {
        adapter.clear();
        presenter.init();
    }

    @Override
    public void setListResult(List<MyUser> list) {
        adapter.addAll(list);
    }

    @Override
    public void showRefreshing(boolean isShow) {
        recycler.setRefreshing(isShow);
    }
}
