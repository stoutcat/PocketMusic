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
public class HomeNetFragment extends BaseFragment implements HomeNetFragmentPresenter.IView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.search_rl)
    RelativeLayout searchRl;
    private NetFragmentAdapter adapter;
    private HomeNetFragmentPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home_net, container, false);
        ButterKnife.bind(this, view);
        presenter = new HomeNetFragmentPresenter(this, getActivity());
        presenter.setSharePage(0);
        adapter = new NetFragmentAdapter(getContext());
        initView();
        return view;
    }


    //没有设置下拉刷新
    private void initView() {
        //初始化Recycler
        initRecyclerView(recycler,adapter);
        recycler.setEmptyView(R.layout.view_not_found);
        recycler.setRefreshListener(this);
        adapter.setListener(new NetFragmentAdapter.OnOptionListener() {

            //乐器列表选择
            @Override
            public void onSelectType(int position) {
                presenter.enterTypeActivity(position);
            }

            //上传列表选择
            @Override
            public void onSelectShare(int position) {
                ShareSong shareSong = (ShareSong) adapter.getItem(position);
                presenter.enterSongActivityByShare(shareSong);
            }


            //设置点击某个RollView
            @Override
            public void onSelectRollView(int picPosition) {

            }
        });

        //recycler的滑动监听，search上移
        recycler.setOnScrollListener(new SearchViewListener(searchRl));

        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.loadMore();
            }
        });

        //初始化轮播，乐器类型列表
        initList();
        presenter.getShareList();
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
    }

    @OnClick(R.id.search_rl)
    public void onClick() {
        Intent intent = new Intent(getActivity(), SearchRecordActivity.class);
        startActivity(intent);
    }


    @Override
    public void setList(List<ShareSong> list) {
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
}
