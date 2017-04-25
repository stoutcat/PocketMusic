package com.example.q.pocketmusic.module.search.recommend;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.collection.CollectionCount;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 81256 on 2017/4/14.
 */

//包括桃李醉春风和收藏夹
public class SearchRecommendFragment extends BaseFragment implements SearchRecommendFragmentPresenter.IView, TagFlowLayout.OnTagClickListener, RecyclerArrayAdapter.OnItemClickListener {

    @BindView(R.id.recommend_left_tv)
    TextView recommendLeftTv;
    @BindView(R.id.recommend_right_iv)
    ImageView recommendRightIv;
    @BindView(R.id.recommend_flow_layout)
    TagFlowLayout recommendFlowLayout;
    @BindView(R.id.collection_left_tv)
    TextView collectionLeftTv;
    @BindView(R.id.collection_right_iv)
    ImageView collectionRightIv;
    @BindView(R.id.collection_recycler)
    EasyRecyclerView collectionRecycler;
    private SearchRecommendFragmentPresenter presenter;
    private SearchRecommendAdapter recommendAdapter;
    private SearchCollectionAdapter collectionAdapter;

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
        collectionAdapter = new SearchCollectionAdapter(getContext());
        collectionAdapter.setOnItemClickListener(this);
        recommendFlowLayout.setOnTagClickListener(this);
    }

    @Override
    public void init() {
        presenter = new SearchRecommendFragmentPresenter(getContext(), this);
        initRecyclerView(collectionRecycler, collectionAdapter);
        presenter.getRecommendList();//推荐列表
        presenter.getCollectionList();//收藏列表
    }

    @Override
    public void setRecommendList(List<Song> list) {
        recommendAdapter = new SearchRecommendAdapter(list, getContext());
        recommendFlowLayout.setAdapter(recommendAdapter);
    }

    @Override
    public void setCollectionList(List<CollectionCount> list) {
        collectionAdapter.addAll(list);
        collectionAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onTagClick(View view, int position, FlowLayout parent) {
        presenter.enterSongActivityByRecommendTag(recommendAdapter.getItem(position));
        return true;
    }

    @OnClick(R.id.recommend_right_iv)
    public void onViewClicked() {
        presenter.enterRecommendListActivity();
    }

    @Override
    public void onItemClick(int position) {
        presenter.enterSongActivityByCollectionTag();
    }
}
