package com.example.q.pocketmusic.module.home.profile.setting;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;

import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.BasePresenter;

import com.example.q.pocketmusic.module.home.profile.setting.help.HelpActivity;
import com.example.q.pocketmusic.util.MyToast;

import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

/**
 * Created by Cloud on 2016/11/14.
 */

public class SettingPresenter extends BasePresenter {
    private IView activity;
    private Context context;

    public SettingPresenter(IView activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public void checkUpdate(final Boolean showToast) {
        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
            @Override
            public void onUpdateReturned(int i, UpdateResponse updateResponse) {
                if (i == UpdateStatus.Yes) {
                    activity.setCheckUpdateResult(true, showToast);
                } else if (i == UpdateStatus.No) {
                    activity.setCheckUpdateResult(false, showToast);
                }
            }
        });
        BmobUpdateAgent.forceUpdate(context);

    }


    public void logOut() {
        MyUser.logOut();
        android.os.Process.killProcess(android.os.Process.myPid());
        ContextWrapper wrapper = ((ContextWrapper) context);
        Intent i = wrapper.getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(wrapper.getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);//重启app
    }

    public void grade() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
        if (intent.resolveActivity(context.getPackageManager()) != null) { //可以接收
            context.startActivity(intent);
        } else {
            MyToast.showToast(context, "没有找到应用市场~");
        }
    }

    //分享apk
    public void shareApp() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "推荐一款app:" + "<口袋乐谱>" + "---官网地址：" + "http://pocketmusic.bmob.site/");
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            MyToast.showToast(context, "你的手机不支持分享~");
        }
    }



    public interface IView {

        void setCheckUpdateResult(boolean hasUpdate, boolean showToast);
    }
}
