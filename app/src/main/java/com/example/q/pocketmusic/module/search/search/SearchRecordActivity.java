package com.example.q.pocketmusic.module.search.search;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.local.Record;
import com.example.q.pocketmusic.model.flag.Divider;
import com.example.q.pocketmusic.model.flag.Tag;
import com.example.q.pocketmusic.model.flag.Text;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.util.LogUtils;
import com.example.q.pocketmusic.util.MyToast;
import com.example.q.pocketmusic.view.widget.net.FireworkView;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchRecordActivity extends BaseActivity implements SearchRecordActivityPresenter.IView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.search_edt)
    EditText searchEdt;
    @BindView(R.id.fire_work)
    FireworkView fireWork;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private SearchRecordActivityPresenter presenter;
    private SearchRecordActivityAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_record);
        ButterKnife.bind(this);
        presenter = new SearchRecordActivityPresenter(this, this);
        adapter = new SearchRecordActivityAdapter(this);
        initView();
    }

    private void initView() {
        setRecycler();
        fireWork.bindEditText(searchEdt);
        //回车键监听
        searchEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String temp = searchEdt.getText().toString().trim();
                    presenter.enterSearchListActivity(temp);
                    return true;
                }
                return false;
            }
        });

        //搜索键的监听
        searchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = searchEdt.getText().toString().trim();
                presenter.enterSearchListActivity(temp);
            }
        });
    }


    //recycler和adapter
    private void setRecycler() {
        initRecyclerView(recycler, adapter);
        recycler.setEmptyView(R.layout.view_not_found);
        //adapter监听
        adapter.setOnSelectListener(new SearchRecordActivityAdapter.OnSelectListener() {

            //进入RecommendListActivity
            @Override
            public void onSelectMore() {
                presenter.enterRecommendListActivity();
            }


            //选择某一个Tag
            @Override
            public void onSelectTag(int tagPosition) {
                Song song = presenter.getSongs().get(tagPosition);
                presenter.enterSongActivityByTag(song);
            }


            //点击某一个记录
            @Override
            public void onSelectRecord(int position) {
                Record record = (Record) adapter.getItem(position);
                presenter.enterSearchListActivityByRecord(record);
            }

            @Override
            public void onSelectDeleteRecord() {
                presenter.deleteAllRecord();
                onRefresh();
            }

        });
        recycler.setRefreshListener(this);
        onRefresh();
    }

    //presenter从网络上得到数据
    @Override
    public void setList(List<Record> records, List<Song> songs) {
        recycler.setRefreshing(false);
        adapter.clear();
        adapter.setTagList(songs);//把TagList注入adapter
        Text text1 = new Text(R.drawable.ico_right_more, "热门搜索");
        Text text2 = new Text(R.drawable.ico_right_delete, "搜索记录");
        adapter.add(text1);//顶部Text,显示图标
        adapter.add(new Tag());//flowLayout
        adapter.add(new Divider());//分割线
        adapter.add(text2);//中部Text，不显示图标
        adapter.addAll(records);//Record
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadFail() {
        MyToast.showToast(this, "获取数据失败，可能网络不太好哦~");
        recycler.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        presenter.getList();
    }
}
