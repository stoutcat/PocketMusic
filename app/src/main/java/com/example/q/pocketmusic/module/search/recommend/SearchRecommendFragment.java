package com.example.q.pocketmusic.module.search.recommend;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.local.Record;
import com.example.q.pocketmusic.model.flag.Divider;
import com.example.q.pocketmusic.model.flag.Tag;
import com.example.q.pocketmusic.model.flag.Text;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 81256 on 2017/4/14.
 */

//包括桃李醉春风和收藏夹
public class SearchRecommendFragment extends BaseFragment implements SearchRecommendAdapter.OnSelectListener, SearchRecommendFragmentPresenter.IView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private SearchRecommendAdapter adapter;
    private SearchRecommendFragmentPresenter presenter;

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public int setContentResource() {
        return R.layout.fragment_search_recommend;
    }

    @Override
    public void setListener() {
        adapter = new SearchRecommendAdapter(getContext());
        adapter.setOnSelectListener(this);
        recycler.setRefreshListener(this);
    }

    @Override
    public void init() {
        presenter = new SearchRecommendFragmentPresenter(getContext(), this);
        initRecyclerView(recycler, adapter);
        presenter.getCacheList();
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
    }

    @Override
    public void onSelectDeleteRecord() {
        presenter.deleteAllRecord();
        onRefresh();
    }

    //presenter从网络上得到数据
    @Override
    public void setList(List<Record> records, List<Song> songs) {
        adapter.setTagList(songs);//把TagList注入adapter
        Text text1 = new Text(R.drawable.ico_right_more, "热门搜索");
//        Text text2 = new Text(R.drawable.ico_right_delete, "热门手长");
        adapter.add(text1);//顶部Text,显示图标
        adapter.add(new Tag());//flowLayout
        adapter.add(new Divider());//分割线
//        adapter.add(text2);//中部Text，不显示图标
//        adapter.addAll(records);//Record
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        presenter.getList();
    }
}
