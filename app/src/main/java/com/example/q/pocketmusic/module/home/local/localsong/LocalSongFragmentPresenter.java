package com.example.q.pocketmusic.module.home.local.localsong;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Environment;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.local.Img;
import com.example.q.pocketmusic.model.bean.local.LocalSong;
import com.example.q.pocketmusic.model.db.ImgDao;
import com.example.q.pocketmusic.model.db.LocalSongDao;
import com.example.q.pocketmusic.model.net.LoadLocalSongList;
import com.example.q.pocketmusic.model.net.SynchronizeLocalSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.share.ShareActivity;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.LogUtils;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by YQ on 2016/9/2.
 */
public class LocalSongFragmentPresenter extends BasePresenter {
    private Context context;
    private IView fragment;
    private LocalSongDao localSongDao;
    private ImgDao imgDao;


    //Dao有必要关闭吗？iterator呢？
    public LocalSongFragmentPresenter(Context context, IView fragment) {
        this.context = context;
        this.fragment = fragment;
        localSongDao = new LocalSongDao(context);
        imgDao = new ImgDao(context);

    }

    //对于数据库的操作，只用一个list,防止数据删除错误
    public void loadLocalSong() {
        new LoadLocalSongList(localSongDao, context) {
            @Override
            protected void onPostExecute(List<LocalSong> localSongs) {
                super.onPostExecute(localSongs);
                fragment.setList(localSongs);
                LogUtils.e(TAG, "本地乐谱数量：" + localSongs.size());
            }
        }.execute();

    }

    //删除乐谱要删除数据库和list.position,还有本地的文件！
    public void deleteSong(LocalSong localSong) {
        deleteFromDatabase(localSong);
    }

    private void deleteFromDatabase(LocalSong localSong) {
        //从数据库删除
        localSongDao.delete(localSong);
        ForeignCollection<Img> imgs = localSong.getImgs();
        CloseableIterator<Img> iterator = imgs.closeableIterator();
        String parent = null;
        try {
            while (iterator.hasNext()) {
                Img img = iterator.next();
                imgDao.delete(img);
                //删除文件
                File file = new File(img.getUrl());
                parent = file.getParent();
                if (file.exists()) {
                    boolean isInvalid = file.delete();
                }
            }
            //删除文件夹
            if (parent != null) {
                new File(parent).delete();
            }
        } finally {
            try {
                iterator.close();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    //同步乐谱
    public void synchronizedSong() {
        //先遍历文件夹的图片，添加到数据库（不重复添加），然后再从数据库取出来
        new SynchronizeLocalSong(imgDao,localSongDao){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                loadLocalSong();
            }
        }.execute();
    }


    public void enterShareActivity(LocalSong localSong) {
        Intent intent = new Intent(context, ShareActivity.class);
        intent.putExtra(ShareActivity.LOCAL_SONG, localSong);
        context.startActivity(intent);
    }

    public void enterPictureActivity(LocalSong localSong) {
        Intent intent = new Intent(context, SongActivity.class);
        Song song = new Song();
        song.setName(localSong.getName());
        SongObject songObject = new SongObject(song, Constant.FROM_LOCAL, Constant.SHOW_NO_MENU, Constant.LOCAL);
        intent.putExtra(SongActivity.LOCAL_SONG, localSong);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, songObject);
        context.startActivity(intent);
    }


    public interface IView {
        void setList(List<LocalSong> list);
    }
}
