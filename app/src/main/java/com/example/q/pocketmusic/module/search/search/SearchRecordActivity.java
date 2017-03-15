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
import com.example.q.pocketmusic.util.MyToast;
import com.example.q.pocketmusic.view.widget.net.FireworkView;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchRecordActivity extends BaseActivity implements SearchRecordActivityPresenter.IView, SwipeRefreshLayout.OnRefreshListener, View.OnKeyListener, SearchRecordActivityAdapter.OnSelectListener, View.OnClickListener {
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
    public int setContentResource() {
        return R.layout.activity_search_record;
    }


    public void setListener() {
        adapter = new SearchRecordActivityAdapter(this);
        searchEdt.setOnKeyListener(this);
        adapter.setOnSelectListener(this);
        recycler.setRefreshListener(this);
        searchIv.setOnClickListener(this);
    }

    @Override
    public void init() {
        presenter = new SearchRecordActivityPresenter(this, this);
        initRecyclerView(recycler, adapter);
        fireWork.bindEditText(searchEdt);
        presenter.getCacheList();
    }


    //presenter从网络上得到数据
    @Override
    public void setList(List<Record> records, List<Song> songs) {
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
    public void onRefresh() {
        adapter.clear();
        presenter.getList();
    }

    //Enter键
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            String temp = searchEdt.getText().toString().trim();
            presenter.enterSearchListActivity(temp);
            return true;
        }
        return false;
    }


    //查看更多推荐
    @Override
    public void onSelectMore() {
        presenter.enterRecommendListActivity();
    }

    //选择Tag
    @Override
    public void onSelectTag(int tagPosition) {
        Song song = adapter.getTagList().get(tagPosition);
        presenter.enterSongActivityByTag(song);
    }

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

    @Override
    public void onClick(View v) {
        String temp = searchEdt.getText().toString().trim();
        presenter.enterSearchListActivity(temp);
    }
}
