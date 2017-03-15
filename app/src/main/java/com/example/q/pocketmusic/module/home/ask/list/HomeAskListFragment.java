package com.example.q.pocketmusic.module.home.ask.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.ask.publish.AskSongActivity;
import com.example.q.pocketmusic.util.LogUtils;
import com.example.q.pocketmusic.view.widget.net.TitleBar;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cloud on 2017/1/26.
 */
public class HomeAskListFragment extends BaseFragment implements HomeAskListFragmentPresenter.IView, SwipeRefreshLayout.OnRefreshListener, HomeAskListAdapter.OnItemClickListener, View.OnClickListener
        , RecyclerArrayAdapter.OnMoreListener {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private HomeAskListFragmentPresenter presenter;
    private HomeAskListAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.fragment_home_ask_list;
    }

    @Override
    public void setListener() {
        adapter = new HomeAskListAdapter(getContext());
        adapter.setListener(this);
      //  titleBar.setSettingIconOnClickListener(this);
        adapter.setMore(R.layout.view_more, this);
        recycler.setRefreshListener(this);
    }

    @Override
    public void init() {
        presenter = new HomeAskListFragmentPresenter(context, this);
        initRecyclerView(recycler, adapter, 72,false);
        presenter.setmPage(0);
        titleBar.setMyCenterTitle("大家都在找");
        titleBar.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
        titleBar.setMySettingIcon(R.drawable.ico_add);
        titleBar.setSettingIconOnClickListener(this);//需要修改
        presenter.getCacheList();
    }


    @Override
    public void setPostList(List<AskSongPost> list) {
        recycler.setRefreshing(false);
        adapter.addAll(list);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AskSongActivity.REQUEST_ASK && resultCode == Constant.SUCCESS) {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        presenter.getPostList();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void onClickHeadIv(int position) {
        presenter.enterOtherProfileActivity(adapter.getItem(position));
    }

    @Override
    public void onClickContent(int position) {
        presenter.enterCommentActivity(adapter.getItem(position));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_setting_icon:
                presenter.enterAskSongActivity();
                break;
        }

    }

    @Override
    public void onMoreShow() {
        presenter.getMore();
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void showRefreshing(boolean isShow) {
        recycler.setRefreshing(isShow);
    }
}
