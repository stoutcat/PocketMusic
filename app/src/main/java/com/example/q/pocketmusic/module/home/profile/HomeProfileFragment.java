package com.example.q.pocketmusic.module.home.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.AuthFragment;
import com.example.q.pocketmusic.util.CheckUserUtil;
import com.example.q.pocketmusic.util.DisplayStrategy;
import com.example.q.pocketmusic.view.dialog.ListDialog;
import com.example.q.pocketmusic.view.widget.net.SnackBarUtil;
import com.example.q.pocketmusic.view.widget.view.IcoTextItem;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Cloud on 2017/1/26.
 */

public class HomeProfileFragment extends AuthFragment implements HomeProfileFragmentPresenter.IView {

    @BindView(R.id.head_iv)
    ImageView headIv;
    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.email_item)
    IcoTextItem emailItem;
    @BindView(R.id.instrument_item)
    IcoTextItem instrumentItem;
    @BindView(R.id.contribution_item)
    IcoTextItem contributionItem;
    @BindView(R.id.collection_item)
    IcoTextItem collectionItem;
    @BindView(R.id.setting_item)
    IcoTextItem settingItem;
    private ListDialog listDialog;
    private HomeProfileFragmentPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_profile, container, false);
        ButterKnife.bind(this, view);
        presenter = new HomeProfileFragmentPresenter(context, this);
        initView();
        //贡献值实时更新
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        if (user != null) {
            //设置
            presenter.setUser(user);
            //设置昵称
            userNameTv.setText(user.getNickName());
            //设置头像
            new DisplayStrategy().displayCircle(context, user.getHeadImg(), headIv);
            //设置乐器
            instrumentItem.setSubText(user.getInstrument());
            //设置贡献值，数据更新有问题
//            contributionItem.setSubText(String.valueOf(user.getContribution()) + " 点");
        }
    }


    @OnClick({R.id.head_iv, R.id.instrument_item, R.id.setting_item, R.id.email_item, R.id.collection_item, R.id.contribution_item})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_iv://设置头像
                presenter.setHeadIv();
                break;
            case R.id.email_item://用户邮箱
                presenter.enterSuggestionActivity();
                break;
            case R.id.instrument_item://设置乐器
                setInstrument();
                break;
            case R.id.setting_item://设置界面
                presenter.enterSettingActivity();
                break;
            case R.id.collection_item://进入收藏列表界面
                presenter.enterCollectionActivity();
                break;
            case R.id.contribution_item://得到当前贡献度,需要实时获得，不能使用本地缓存对象
                CheckUserUtil.getUserContribution(this, context, this, new CheckUserUtil.UserContributionListener() {
                    @Override
                    public void onSuccess(Integer contribution) {
                        SnackBarUtil.IndefiniteSnackbar(contributionItem, "当前贡献度" + contribution, 2000, R.color.black, SnackBarUtil.orange).show();
                    }
                });
                break;

        }
    }

    private void setInstrument() {
        String[] instruments = this.getResources().getStringArray(R.array.instrument);
        List<String> list = Arrays.asList(instruments);
        ListDialog.Builder builder = new ListDialog.Builder(context);
        listDialog = builder.setTitleStr("请选择你喜欢的乐器")
                .setList(list)
                .setOnItemSelectListener(new ListDialog.Builder.OnItemSelectListener() {
                    @Override
                    public void onSelect(int position, String str) {
                        presenter.setInstrument(str);
                    }
                })
                .build();
        listDialog.show();
    }

    @Override
    public void setHeadIvResult(String photoPath) {
        new DisplayStrategy().displayCircle(context, photoPath, headIv);
    }


    @Override
    public void setInstrumentResult(String instrumentStr) {
        listDialog.dismiss();
        instrumentItem.setSubText(instrumentStr);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }
}
