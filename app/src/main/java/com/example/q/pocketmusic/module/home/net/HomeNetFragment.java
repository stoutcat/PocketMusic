package com.example.q.pocketmusic.module.home.net;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.model.flag.BannerBean;
import com.example.q.pocketmusic.model.flag.ContentLL;
import com.example.q.pocketmusic.model.flag.Divider;
import com.example.q.pocketmusic.model.flag.TextTv;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.search.search.SearchRecordActivity;
import com.example.q.pocketmusic.util.LogUtils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cloud on 2016/11/17.
 */
//网络Fragment
public class HomeNetFragment extends BaseFragment implements HomeNetFragmentPresenter.IView, SwipeRefreshLayout.OnRefreshListener, NetFragmentAdapter.OnOptionListener, RecyclerArrayAdapter.OnMoreListener {

    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.search_rl)
    RelativeLayout searchRl;
    private NetFragmentAdapter adapter;
    private HomeNetFragmentPresenter presenter;


    @Override
    public int setContentResource() {
        return R.layout.fragment_home_net;
    }

    @Override
    public void setListener() {
        adapter = new NetFragmentAdapter(getContext());
        recycler.setRefreshListener(this);
        adapter.setListener(this);
        recycler.setOnScrollListener(new SearchViewListener(searchRl));
        adapter.setMore(R.layout.view_more, this);
    }

    @Override
    public void init() {
        presenter = new HomeNetFragmentPresenter(this, getActivity());
        presenter.setSharePage(0);
        initRecyclerView(recycler, adapter);
        initView();
    }


    //没有设置下拉刷新
    private void initView() {
        //初始化Recycler
        recycler.setEmptyView(R.layout.view_not_found);
        initList();
        presenter.getCacheList();
    }

    private void initList() {
        TextTv textTv1 = new TextTv();
        textTv1.setName("我的乐器");
        TextTv textTv2 = new TextTv();
        textTv2.setName("谱友来荐");
        adapter.add(new BannerBean());//轮播
        adapter.add(textTv1);//文字
        adapter.add(new ContentLL());//乐器类型
        adapter.add(textTv2);//文字
        adapter.add(new Divider());//分割线
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.search_rl)
    public void onClick() {
        Intent intent = new Intent(getActivity(), SearchRecordActivity.class);
        startActivity(intent);
    }


    @Override
    public void setList(List<ShareSong> list) {
        recycler.setRefreshing(false);
        adapter.addAll(list);
    }

    @Override
    public void setMore(List<ShareSong> list) {
        adapter.addAll(list);
    }


    @Override
    public void onRefresh() {
        presenter.setSharePage(0);
        adapter.clear();
        initList();
        presenter.getShareList();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void onSelectType(int position) {
        presenter.enterTypeActivity(position);
    }

    @Override
    public void onSelectShare(int position) {
        ShareSong shareSong = (ShareSong) adapter.getItem(position);
        presenter.enterSongActivityByShare(shareSong);
    }

    @Override
    public void onSelectRollView(int picPosition) {

    }

    @Override
    public void onMoreShow() {
        presenter.loadMore();
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void showRefreshing(boolean isShow) {
        recycler.setRefreshing(isShow);
    }
}
