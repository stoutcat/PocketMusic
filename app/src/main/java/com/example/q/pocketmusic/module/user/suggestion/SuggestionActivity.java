package com.example.q.pocketmusic.module.user.suggestion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.bmob.UserSuggestion;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.util.MyToast;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Cloud on 2016/11/14.
 */

public class SuggestionActivity extends AuthActivity implements SuggestionPresenter.IView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.user_input_edt)
    EditText userInputEdt;
    @BindView(R.id.send_suggestion_btn)
    Button sendSuggestionBtn;
    @BindView(R.id.input_ll)
    LinearLayout userInputLl;
    private SuggestionPresenter presenter;
    private SuggestionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        ButterKnife.bind(this);
        presenter = new SuggestionPresenter(this, this, user);
        adapter = new SuggestionAdapter(this);
        initToolbar(toolbar, "反馈意见");
        initRecycler();
        adapter.addHeader(new SuggestionHeader(context));
        recycler.setRefreshing(true);
        recycler.setRefreshListener(this);
        onRefresh();
    }

    private void initRecycler() {
        recycler.setLayoutManager(new LinearLayoutManager(context));
        recycler.setRefreshingColorResources(R.color.colorAccent);
        recycler.setAdapter(adapter);
    }


    @OnClick(R.id.send_suggestion_btn)
    public void onClick() {
        String suggestion = userInputEdt.getText().toString().trim();
        userInputEdt.setText("");
        presenter.sendSuggestion(suggestion);
    }

    @Override
    public void sendSuggestionResult(UserSuggestion userSuggestion) {
        recycler.showRecycler();
        adapter.add(userSuggestion);
    }

    @Override
    public void getSuggestionListResult(List<UserSuggestion> list) {
        adapter.clear();
        adapter.addAll(list);
    }

    @Override
    public void onRefresh() {
        presenter.getSuggestionList();
    }

}