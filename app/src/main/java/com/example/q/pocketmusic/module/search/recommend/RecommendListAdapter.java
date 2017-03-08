package com.example.q.pocketmusic.module.search.recommend;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.Song;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Cloud on 2016/11/15.
 */

public class RecommendListAdapter extends RecyclerArrayAdapter<Song> {
    public RecommendListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<Song> {
        TextView nameTv;
        TextView artistTv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_recommend_list);
            nameTv = $(R.id.name_tv);
            artistTv = $(R.id.artist_tv);
        }

        @Override
        public void setData(Song data) {
            super.setData(data);
            nameTv.setText(data.getName());
            if (data.getArtist() != null) {
                artistTv.setText(data.getArtist());
            } else {
                artistTv.setText("暂无");
            }
        }

    }
}
