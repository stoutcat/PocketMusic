package com.example.q.pocketmusic.module.home.profile;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.AuthFragment;
import com.example.q.pocketmusic.util.DisplayStrategy;
import com.example.q.pocketmusic.view.dialog.ListDialog;
import com.example.q.pocketmusic.view.widget.view.GuaGuaKa;
import com.example.q.pocketmusic.view.widget.view.IcoTextItem;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


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
    @BindView(R.id.sign_in_btn)
    Button signInBtn;
    @BindView(R.id.help_item)
    IcoTextItem helpItem;
    Unbinder unbinder;
    private ListDialog listDialog;
    private HomeProfileFragmentPresenter presenter;
    private AlertDialog signInDialog;


    @Override
    public int setContentResource() {
        return R.layout.fragment_home_profile;
    }

    @Override
    public void setListener() {

    }

    @Override
    public void init() {
        presenter = new HomeProfileFragmentPresenter(context, this);
        initView();
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


    @OnClick({R.id.head_iv, R.id.instrument_item, R.id.setting_item, R.id.email_item, R.id.collection_item, R.id.contribution_item, R.id.sign_in_btn,R.id.help_item})
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
            case R.id.contribution_item://进入ContributionActivity
                presenter.enterContributionActivity();
                break;
            case R.id.sign_in_btn://签到
                presenter.checkHasSignIn();
                break;
            case R.id.help_item:
                presenter.enterHelpActivity();
        }
    }

    //签到Dialog
    public void alertSignInDialog() {
        Random random = new Random();
        final int reward = random.nextInt(4) + 1;//随机1--4点
        View view = View.inflate(getContext(), R.layout.dialog_sign_in, null);
        GuaGuaKa guaGuaKa = (GuaGuaKa) view.findViewById(R.id.gua_gua_ka);
        guaGuaKa.setAwardText(String.valueOf(reward) + " 点贡献度");
        final Button getRewardBtn = (Button) view.findViewById(R.id.get_reward_btn);
        signInDialog = new AlertDialog.Builder(getContext())
                .setCancelable(false)
                .setView(view)
                .create();
        guaGuaKa.setOnCompleteListener(new GuaGuaKa.OnCompleteListener() {
            @Override
            public void onComplete() {
                getRewardBtn.setVisibility(View.VISIBLE);
            }
        });
        getRewardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addReward(reward);
            }
        });
        signInDialog.show();
    }

    //设置乐器
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
    public void dismissSignDialog() {
        signInDialog.dismiss();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    //空接口
    @Override
    public void showRefreshing(boolean isShow) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick()
    public void onViewClicked() {
    }
}
