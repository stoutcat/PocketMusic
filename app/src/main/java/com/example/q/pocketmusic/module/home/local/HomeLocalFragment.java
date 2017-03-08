package com.example.q.pocketmusic.module.home.local;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.local.localrecord.LocalRecordFragment;
import com.example.q.pocketmusic.module.home.local.localsong.LocalSongFragment;
import com.example.q.pocketmusic.view.dialog.EditDialog;
import com.example.q.pocketmusic.view.widget.net.FloatingDraftButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by YQ on 2016/8/28.
 */
public class HomeLocalFragment extends BaseFragment implements HomeLocalFragmentPresenter.IView {
    @BindView(R.id.local_tab_layout)
    TabLayout localTabLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.local_view_pager)
    ViewPager localViewPager;
    @BindView(R.id.add_local_fab)
    FloatingDraftButton addLocalFab;
    @BindView(R.id.activity_audio_record)
    RelativeLayout activityAudioRecord;
    private LocalFragmentPagerAdapter adapter;
    private FragmentManager fm;
    private List<String> tabs = new ArrayList<>();
    private LocalRecordFragment localRecordFragment;
    private LocalSongFragment localSongFragment;
    private List<Fragment> fragmentList = new ArrayList<>();
    private HomeLocalFragmentPresenter presenter;
    private EditDialog editDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentList.clear();
        tabs.clear();
        //初始化tab名称
        tabs.add("定制乐谱");
        tabs.add("我的独奏");
        //初始化Fragment
        localSongFragment = new LocalSongFragment();
        localRecordFragment = new LocalRecordFragment();
        fragmentList.add(localSongFragment);
        fragmentList.add(localRecordFragment);
        presenter = new HomeLocalFragmentPresenter(getContext(), this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_local, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;

    }


    private void initView() {
        //viewpager
        fm = getChildFragmentManager();
        adapter = new LocalFragmentPagerAdapter(getActivity(), fm, tabs, fragmentList);
        localViewPager.setAdapter(adapter);
        localTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        localTabLayout.setTabTextColors(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorAccent));
        localTabLayout.setupWithViewPager(localViewPager);

        localViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Floating的显示和隐藏
                if (position == 0) {
                    addLocalFab.show();
                } else {
                    addLocalFab.hide();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //必须要在onResume中设置
    @Override
    public void onResume() {
        super.onResume();
        localTabLayout.setTabTextColors(ContextCompat.getColor(getContext(), R.color.colorTitle), ContextCompat.getColor(getContext(), R.color.colorTitle));
        localTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.colorTitle));
    }

    //设置添加本地的dialog
    @Override
    public void showAddDialog(final List<String> list) {
        EditDialog.Builder builder = new EditDialog.Builder(getContext());
        editDialog = builder.setListener(new EditDialog.Builder.OnSelectedListener() {
            @Override
            public void onSelectedOk(String str) {
                presenter.moveFileAndAddDatabase(str, list);//移动，并保存到数据库
                localSongFragment.onRefresh();
                editDialog.dismiss();
            }

            @Override
            public void onSelectedCancel() {
                editDialog.dismiss();
            }
        }).create();
        editDialog.show();
    }


    //setonClick之后，onTouchEvent就一定会返回true
    @OnClick(R.id.add_local_fab)
    public void onClick() {
        presenter.openPicture();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }
}
