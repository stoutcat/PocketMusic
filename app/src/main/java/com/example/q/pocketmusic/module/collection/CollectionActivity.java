package com.example.q.pocketmusic.module.collection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.collection.CollectionSong;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cloud on 2016/11/14.
 */

public class CollectionActivity extends AuthActivity implements CollectionPresenter.IView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private CollectionPresenter presenter;
    private CollectionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        presenter = new CollectionPresenter(this, this, user);
        adapter = new CollectionAdapter(this);
        initView();
    }

    private void initView() {
        initToolbar(toolbar, "我的收藏");
        initRecyclerView(recycler, adapter,1);
        recycler.setRefreshListener(this);
        recycler.setRefreshing(true);
        onRefresh();

        adapter.setOnSelectListener(new CollectionAdapter.OnSelectListener() {
            //点击more
            @Override
            public void onSelectMore(int position) {
                alertBottomDialog(position);
            }
            @Override
            public void onSelectItem(int position) {
                presenter.queryAndEnterSongActivity(adapter.getItem(position));
            }
        });
    }

    //弹出底部dialog
    private void alertBottomDialog(final int position) {
        BottomSheetMenuDialog dialog = new BottomSheetBuilder(this)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setMenu(R.menu.menu_collection_list)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete://删除收藏
                                presenter.deleteCollection(adapter.getItem(position));
                                break;
                        }
                    }
                })
                .createDialog();
        dialog.show();
    }



    @Override
    public void setCollectionList(List<CollectionSong> list) {
        adapter.clear();
        adapter.addAll(list);
    }

    @Override
    public void deleteCollectionResult(CollectionSong item) {
        adapter.remove(item);
    }

    @Override
    public void onRefresh() {
        presenter.getCollectionList();
    }
}