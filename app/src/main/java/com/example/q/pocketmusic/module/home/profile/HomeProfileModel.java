package com.example.q.pocketmusic.module.home.profile;

import android.content.Context;

import com.example.q.pocketmusic.callback.IBaseList;
import com.example.q.pocketmusic.callback.IBaseView;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.BmobInfo;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.util.BmobUtil;
import com.example.q.pocketmusic.util.MyToast;

import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by 鹏君 on 2017/4/22.
 */

public class HomeProfileModel {
    private BmobUtil bmobUtil;

    public HomeProfileModel() {
        bmobUtil = new BmobUtil();
    }

    public void getBmobInfoList(ToastQueryListener<BmobInfo> listener) {
        bmobUtil.getInitListWithEqual(BmobInfo.class, null, "type", Constant.BMOB_INFO_LABA, listener);
    }

}
