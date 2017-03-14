package com.example.q.pocketmusic.module.search.type;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.ConvertUtil;
import com.example.q.pocketmusic.util.MyToast;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongTypeActivity extends BaseActivity implements SongTypeActivityPresenter.IView, RecyclerArrayAdapter.OnMoreListener, RecyclerArrayAdapter.OnItemClickListener {

    @BindView(R.id.top_iv)
    ImageView topIv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    //500*300
    private int[] topDrawable = new int[]{R.drawable.iv_top_quanbu, R.drawable.iv_top_hulusi, R.drawable.iv_top_jita,
            R.drawable.iv_top_gangqin, R.drawable.iv_top_sakesi, R.drawable.iv_top_erhu, R.drawable.iv_top_guzheng,
            R.drawable.iv_top_dianziqin, R.drawable.iv_top_pipa, R.drawable.iv_top_kouqin};
    private SongTypeActivityPresenter presenter;
    private SongTypeActivityAdapter adapter;
    private Integer typeId;
    public final static String PARAM_POSITION = "position";


    @Override
    public int setContentResource() {
        return R.layout.activity_type_song;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SongTypeActivityPresenter(this, this);
        presenter.setPage(1);
        setListener();
        init();
    }

    public void setListener() {
        adapter = new SongTypeActivityAdapter(this);
        adapter.setOnItemClickListener(this);
        adapter.setMore(R.layout.view_more, this);
    }

    @Override
    public void init() {
        //获取传过来的recycler位置信息
        Intent intent = getIntent();
        int position = intent.getIntExtra(PARAM_POSITION, 0);

        //获取乐器类型
        typeId = position;

        toolbar.setTitle(Constant.types[typeId]);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //只能通过这样才可以设置标题的颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorTitle));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorTranslate));

        //设置顶部图片
        topIv.setBackgroundResource(topDrawable[typeId]);
        initRecyclerView(recycler, adapter, 1, false);


        //加载数据
        recycler.setRefreshing(true);
        presenter.setList(position);
    }


    //加载更多和初始化列表
    @Override
    public void setMore(List<Song> moreList) {
        recycler.setRefreshing(false);
        adapter.addAll(moreList);
    }

    //加载失败
    @Override
    public void loadFail() {
        MyToast.showToast(getApplicationContext(), CommonString.STR_NOT_NET);
    }


    @Override
    public void onMoreShow() {
        presenter.loadMore(typeId);
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void onItemClick(int position) {
        presenter.enterSongActivity(adapter.getItem(position));
    }
}
