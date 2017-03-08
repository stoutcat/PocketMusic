package com.example.q.pocketmusic.module.search.recommend;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.TestActivity;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.LogUtils;
import com.example.q.pocketmusic.util.MyToast;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cloud on 2016/11/14.
 */

public class RecommendListActivity extends BaseActivity implements RecommendListActivityPresenter.IView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private RecommendListActivityPresenter presenter;
    private RecommendListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_list);
        ButterKnife.bind(this);
        presenter = new RecommendListActivityPresenter(this, this);
        adapter = new RecommendListAdapter(this);
        initToolbar(toolbar,"推荐列表");
        initRecyclerView(recycler,adapter,1);
        presenter.setPage(1);
        initView();
    }

    private void initView() {
        //加载更多
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                presenter.loadMore();
            }

            @Override
            public void onMoreClick() {

            }
        });
        //没有更多
        adapter.setNoMore(R.layout.view_nomore, new RecyclerArrayAdapter.OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {
                MyToast.showToast(RecommendListActivity.this, "已经没有更多了");
            }

            @Override
            public void onNoMoreClick() {

            }
        });
        //点击监听，进入SongActivity
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Song song=adapter.getItem(position);
                presenter.enterSongActivity(song);
            }
        });
        presenter.getList();
    }


    @Override
    public void setList(List<Song> list) {
        recycler.setRefreshing(false);
        adapter.clear();
        adapter.addAll(list);
    }

    @Override
    public void loadFail() {
        MyToast.showToast(this, "获取数据失败，可能网络不太好哦~");
        recycler.setRefreshing(false);
    }

    @Override
    public void loadMore(List<Song> list) {
        adapter.addAll(list);
    }

}