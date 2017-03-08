package com.example.q.pocketmusic.module.setting.help;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.bmob.Help;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.util.MyToast;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HelpActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.activity_help)
    LinearLayout activityHelp;
    private HelpAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        initToolbar(toolbar, "帮助信息");
        adapter = new HelpAdapter(this);
        initRecyclerView(recycler, adapter);
        recycler.setRefreshListener(this);
        onRefresh();
    }


    @Override
    public void onRefresh() {
        BmobQuery<Help> query = new BmobQuery<>();
        query.order("-createdAt");//逆序
        query.findObjects(new FindListener<Help>() {
            @Override
            public void done(List<Help> list, BmobException e) {
                if (e == null) {
                    adapter.clear();
                    adapter.addAll(list);
                } else {
                    MyToast.showToast(context, Constant.STR_NOT_FOUND);
                }
            }
        });
    }
}
