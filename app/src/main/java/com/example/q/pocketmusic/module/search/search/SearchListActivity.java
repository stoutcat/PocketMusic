package com.example.q.pocketmusic.module.search.search;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.util.MyToast;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchListActivity extends BaseActivity implements SearchListActivityPresenter.IView, RecyclerArrayAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener
,RecyclerArrayAdapter.OnMoreListener{
    public final static String PARAM_QUERY = "query";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.top_rl)
    LinearLayout topRl;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    private SearchListActivityPresenter presenter;
    private SearchListActivityAdapter adapter;
    private String query;


    @Override
    public int setContentResource() {
        return R.layout.activity_search_list;
    }


    public void setListener() {
        adapter = new SearchListActivityAdapter(this);
        adapter.setMore(R.layout.view_more,this);
        adapter.setOnItemClickListener(this);
        recycler.setRefreshListener(this);
    }

    @Override
    public void init() {
        presenter = new SearchListActivityPresenter(this, this);
        query = getIntent().getStringExtra(PARAM_QUERY);
        initToolbar(toolbar, "搜索结果");
        initRecyclerView(recycler, adapter, 1,true);
        onRefresh();
    }

    //搜索结束
    @Override
    public void setList(List<Song> lists) {
        recycler.setRefreshing(false);
        if (lists == null) {
            MyToast.showToast(context, CommonString.STR_NOT_MORE);
        } else {
           // LogUtils.e(TAG, "获得搜索条目:" + String.valueOf(lists.size()));
            adapter.addAll(lists);
        }
    }


    @Override
    public void onItemClick(int position) {
        presenter.enterSongActivity(adapter.getItem(position), adapter.getItem(position).getSearchFrom());
    }


    @Override
    public void onRefresh() {
        adapter.clear();
        presenter.setPage(0);
        presenter.loadInit(query);
    }

    @Override
    public void onMoreShow() {
        presenter.loadMore(query);
    }

    @Override
    public void onMoreClick() {

    }
}
