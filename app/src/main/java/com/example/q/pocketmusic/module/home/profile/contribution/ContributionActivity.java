package com.example.q.pocketmusic.module.home.profile.contribution;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.IDisplayStrategy;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.util.CheckUserUtil;
import com.example.q.pocketmusic.util.DisplayStrategy;
import com.jude.easyrecyclerview.EasyRecyclerView;

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
    public void init() {
        presenter = new ContributionPresenter(this, this, adapter);
        new DisplayStrategy().displayCircle(this,user.getHeadImg(),topIv);
        nickNameTv.setText(user.getNickName());
        contributionTv.setText("贡献度："+user.getContribution());
        initToolbar(toolbar, "贡献度榜单");
        initRecyclerView(recycler, adapter);
        onRefresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onRefresh() {
        adapter.clear();
        presenter.init();
    }
}
