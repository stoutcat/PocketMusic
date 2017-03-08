package com.example.q.pocketmusic.module.search.search;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.local.Record;
import com.example.q.pocketmusic.model.db.RecordDao;
import com.example.q.pocketmusic.model.net.LoadRecommendList;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.search.recommend.RecommendListActivity;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.MyToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YQ on 2016/9/21.
 */

public class SearchRecordActivityPresenter extends BasePresenter {
    private Context context;
    private IView activity;
    private List<Song> songs = new ArrayList<>();
    private List<Record> records = new ArrayList<>();
    private RecordDao recordDao;

    public SearchRecordActivityPresenter(Context context, IView activity) {
        this.context = context;
        this.activity = activity;
        songs.clear();
        records.clear();
        recordDao = new RecordDao(context);
    }

    public List<Song> getSongs() {
        return songs;
    }

    //得到第一页推荐和历史记录
    public void getList() {
        String url = Constant.RECOMMEND_LIST_URL + "1" + ".html";
        if (songs.size() == 0) {//第一次请求,刷新之后不在请求
            new LoadRecommendList(songs) {
                @Override
                protected void onPostExecute(Integer integer) {
                    super.onPostExecute(integer);
                    //去除掉一部分
                    for (int i = songs.size() - 1; i >= 10; i--) {
                        songs.remove(i);
                    }
                    //然后得到 records
                    setIntResult(integer);
                }
            }.execute(url);
        } else {//清除记录之后不用进行网络请求
            setIntResult(Constant.SUCCESS);
        }
    }


    private void setIntResult(int result) {
        records = recordDao.queryForLimitTen();
        if (result == Constant.SUCCESS) {
            activity.setList(records, songs);
        } else {
            activity.loadFail();
        }
    }

    public void addRecord(String query) {
        Record record = new Record();
        record.setName(query);
        recordDao.add(record);
    }

    public void deleteAllRecord() {
        recordDao.deleteAllRecord();
    }

    //进入会搜索列表
    public void enterSearchListActivity(String temp) {
        String query = temp.replaceAll(" ", "");//消除空格
        if (!TextUtils.isEmpty(query)) {
            //添加搜索记录
            addRecord(query);
            //跳转页面
            Intent intent = new Intent(context, SearchListActivity.class);
            intent.putExtra(SearchListActivity.PARAM_QUERY, query);
            context.startActivity(intent);
        } else {
            MyToast.showToast(context, "不能为空哦~");
        }
    }

    //通过记录进入搜索列表
    public void enterSearchListActivityByRecord(Record record) {
        String query = record.getName().replaceAll(" ", "");//消除空格
        Intent intent = new Intent(context, SearchListActivity.class);
        intent.putExtra(SearchListActivity.PARAM_QUERY, query);
        context.startActivity(intent);
    }

    //进入歌曲详情
    public void enterSongActivityByTag(Song song) {
        Intent intent = new Intent(context, SongActivity.class);
        SongObject object = new SongObject(song, Constant.FROM_RECOMMEND, Constant.SHOW_COLLECTION_MENU, Constant.NET);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, object);
        context.startActivity(intent);
    }

    //进入推荐列表
    public void enterRecommendListActivity() {
        Intent intent = new Intent(context, RecommendListActivity.class);
        context.startActivity(intent);
    }


    public interface IView {
        void setList(List<Record> records, List<Song> songs);

        void loadFail();
    }
}
