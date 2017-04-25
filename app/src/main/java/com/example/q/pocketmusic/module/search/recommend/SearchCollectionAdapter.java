package com.example.q.pocketmusic.module.search.recommend;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.collection.CollectionCount;
import com.example.q.pocketmusic.model.bean.collection.CollectionSong;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2017/4/25.
 */

public class SearchCollectionAdapter extends RecyclerArrayAdapter<CollectionCount> {
    public SearchCollectionAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<CollectionCount> {
        TextView titleTv;
        TextView positionTv;
        TextView collectionNumTv;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_collection_search);
            titleTv = $(R.id.title_tv);
            positionTv = $(R.id.position_tv);
            collectionNumTv = $(R.id.collection_num_tv);
        }

        @Override
        public void setData(CollectionCount data) {
            super.setData(data);
            titleTv.setText(data.getName());
            positionTv.setText(getAdapterPosition() + 1 + "");
            collectionNumTv.setText(data.getNum() + "");
        }
    }
}
