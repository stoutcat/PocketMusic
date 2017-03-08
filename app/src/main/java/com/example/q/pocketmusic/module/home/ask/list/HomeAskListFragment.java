package com.example.q.pocketmusic.module.home.ask.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.ask.publish.AskSongActivity;
import com.example.q.pocketmusic.util.LogUtils;
import com.example.q.pocketmusic.view.widget.net.TitleBar;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cloud on 2017/1/26.
 */
public class HomeAskListFragment extends BaseFragment implements HomeAskListFragmentPresenter.IView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private HomeAskListFragmentPresenter presenter;
    private HomeAskListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_ask_list, container, false);
        ButterKnife.bind(this, view);
        presenter = new HomeAskListFragmentPresenter(context, this);
        adapter = new HomeAskListAdapter(getContext());
        presenter.setmPage(0);
        initView();
        return view;
    }

    private void initView() {
        titleBar.setMyCenterTitle("大家都在找");
        titleBar.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
        titleBar.setMySettingIcon(R.drawable.ico_add);
        initRecyclerView(recycler, adapter, 72);
        recycler.setRefreshListener(this);
        setListener();
        recycler.setRefreshing(true);
        onRefresh();//刷新
    }

    private void setListener() {
        adapter.setListener(new HomeAskListAdapter.OnItemClickListener() {
            //点击头像跳转到某个人的信息界面
            @Override
            public void onClickHeadIv(int position) {
                presenter.enterOtherProfileActivity(adapter.getItem(position));
            }

            //点击内容跳转到评论
            @Override
            public void onClickContent(int position) {
                presenter.enterCommentActivity(adapter.getItem(position));

            }
        });
        //求谱
        titleBar.setSettingIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.enterAskSongActivity();
            }
        });

        //加载更多
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                presenter.getMore();
            }

            @Override
            public void onMoreClick() {

            }
        });

    }


    @Override
    public void setPostList(List<AskSongPost> list) {
        adapter.addAll(list);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AskSongActivity.REQUEST_ASK && resultCode == Constant.SUCCESS) {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        presenter.getPostList();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }
}
