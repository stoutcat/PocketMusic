package com.example.q.pocketmusic.module.search.recommend;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.callback.IBaseView;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.local.Record;
import com.example.q.pocketmusic.model.db.RecordDao;
import com.example.q.pocketmusic.model.net.LoadRecommendList;
import com.example.q.pocketmusic.module.search.recommend.list.RecommendListActivity;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.ACacheUtil;

import java.util.List;

/**
 * Created by 81256 on 2017/4/14.
 */

public class SearchRecommendFragmentPresenter {
    private Context context;
    private IView fragment;
    private RecordDao recordDao;

    public SearchRecommendFragmentPresenter(Context context, IView fragment) {
        this.context = context;
        this.fragment = fragment;
        recordDao = new RecordDao(context);
    }

    public void getCacheList() {
        List<Song> list = ACacheUtil.getRecommendCache(context);
        if (list == null) {
            getList();
        } else {
            List<Record> records = recordDao.queryForLimitTen();
            fragment.setList(records, list);
        }

    }

    //得到第一页推荐和历史记录
    public void getList() {
        String url = Constant.RECOMMEND_LIST_URL + "1" + ".html";
        new LoadRecommendList() {
            @Override
            protected void onPostExecute(List<Song> list) {
                super.onPostExecute(list);
                if (list == null) {
//                    getCacheList();
                    return;
                }
                for (int i = list.size() - 1; i >= 10; i--) {
                    list.remove(i);
                }
                ACacheUtil.putRecommendCache(context, list);
                List<Record> records = recordDao.queryForLimitTen();
                fragment.setList(records, list);
            }
        }.execute(url);
    }

    public void addRecord(String query) {
        Record record = new Record();
        record.setName(query);
        recordDao.add(record);
    }

    //进入推荐列表
    public void enterRecommendListActivity() {
        context.startActivity(new Intent(context, RecommendListActivity.class));
    }

    //进入歌曲详情
    public void enterSongActivityByTag(Song song) {
        Intent intent = new Intent(context, SongActivity.class);
        SongObject object = new SongObject(song, Constant.FROM_RECOMMEND, Constant.SHOW_COLLECTION_MENU, Constant.NET);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, object);
        context.startActivity(intent);
    }

    public void deleteAllRecord() {
        recordDao.deleteAllRecord();
    }

    interface IView extends IBaseView {

        void setList(List<Record> records, List<Song> list);
    }
}
