package com.example.q.pocketmusic.module.home.local;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.example.q.pocketmusic.callback.IBaseView;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.local.Img;
import com.example.q.pocketmusic.model.bean.local.LocalSong;
import com.example.q.pocketmusic.model.db.ImgDao;
import com.example.q.pocketmusic.model.db.LocalSongDao;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.lead.LeadSongActivity;
import com.example.q.pocketmusic.module.piano.PianoActivity;
import com.example.q.pocketmusic.util.FileUtils;
import com.example.q.pocketmusic.util.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Cloud on 2016/11/17.
 */

public class HomeLocalFragmentPresenter extends BasePresenter {
    private Context context;
    private IView fragment;


    public HomeLocalFragmentPresenter(Context context, IView fragment) {
        this.context = context;
        this.fragment = fragment;

    }

    public void enterLeadActivity() {
        Intent intent = new Intent(context, LeadSongActivity.class);
        ((BaseActivity) context).startActivityForResult(intent, LeadSongActivity.REQUEST_LEAD);
    }

    public void enterPianoActivity() {
        context.startActivity(new Intent(context, PianoActivity.class));
    }


    public interface IView extends IBaseView {

    }
}
