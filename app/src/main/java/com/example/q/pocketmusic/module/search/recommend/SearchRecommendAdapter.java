package com.example.q.pocketmusic.module.search.recommend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.local.Record;
import com.example.q.pocketmusic.model.flag.Divider;
import com.example.q.pocketmusic.model.flag.Tag;
import com.example.q.pocketmusic.model.flag.Text;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by 81256 on 2017/4/14.
 */

public class SearchRecommendAdapter extends RecyclerArrayAdapter<Object> {
    public static final int TYPE_RECOMMEND_TOP = 0;
    public static final int TYPE_TAG = 1;
    public static final int TYPE_DIVIDER = 2;
    public static final int TYPE_RECORD = 3;
    private List<Song> tagList;
    private OnSelectListener onSelectListener;
    private Context context;

    //从外部设置TagList到内部
    public void setTagList(List<Song> tagList) {
        this.tagList = tagList;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void onSelectMore();

        void onSelectTag(int tagPosition);

        void onSelectRecord(int position);

        void onSelectDeleteRecord();
    }

    public List<Song> getTagList() {
        return tagList;
    }

    public SearchRecommendAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int getViewType(int position) {
        if (getItem(position) instanceof Text) {
            return TYPE_RECOMMEND_TOP;
        } else if (getItem(position) instanceof Tag) {
            return TYPE_TAG;
        } else if (getItem(position) instanceof Divider) {
            return TYPE_DIVIDER;
        } else {
            return TYPE_RECORD;
        }

    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_RECOMMEND_TOP) {
            return new TextViewHolder(parent);
        } else if (viewType == TYPE_TAG) {
            return new TagViewHolder(parent);
        } else if (viewType == TYPE_DIVIDER) {
            return new DividerViewHolder(parent);
        }
        return new SearchRecordViewHolder(parent);
    }

    //推荐顶部的holder，点击进入推荐列表
    private class TextViewHolder extends BaseViewHolder<Text> {
        TextView leftTv;
        ImageView rightIv;

        TextViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_search_text);
            leftTv = $(R.id.left_tv);
            rightIv = $(R.id.right_iv);

        }

        @Override
        public void setData(final Text data) {
            super.setData(data);
            leftTv.setText(data.getName());
            rightIv.setImageResource(data.getmResource());
            rightIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSelectListener != null) {
                        switch (data.getmResource()) {
                            case R.drawable.ico_right_more:
                                onSelectListener.onSelectMore();
                                break;
                            case R.drawable.ico_right_delete:
                                onSelectListener.onSelectDeleteRecord();
                                break;
                        }

                    }
                }
            });
        }
    }

    //热门/推荐的holder
    private class TagViewHolder extends BaseViewHolder<Tag> {
        TagFlowLayout flowLayout;
        LayoutInflater inflater;

        TagViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_search_recommend);
            flowLayout = $(R.id.flow_layout);
            inflater = LayoutInflater.from(getContext());
        }

        @Override
        public void setData(Tag data) {
            super.setData(data);
            flowLayout.setAdapter(new TagAdapter<Song>(tagList) {
                @Override
                public View getView(FlowLayout parent, int position, Song song) {
                    TextView tagTv = (TextView) inflater.inflate(R.layout.item_tag, parent, false);
                    tagTv.setText(song.getName());
                    if (onSelectListener != null) {
                        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                            @Override
                            public boolean onTagClick(View view, int position, FlowLayout parent) {
                                onSelectListener.onSelectTag(position);
                                return true;
                            }
                        });

                    }
                    return tagTv;
                }
            });
        }
    }

    //分割线的holder
    private class DividerViewHolder extends BaseViewHolder<Divider> {

        DividerViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_search_divider);
        }
    }

    //搜索记录的holder
    private class SearchRecordViewHolder extends BaseViewHolder<Record> {
        TextView nameTv;
        LinearLayout recordLl;

        public SearchRecordViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_search_record);
            recordLl = $(R.id.record_ll);
            nameTv = $(R.id.name_tv);
            recordLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSelectListener != null) {
                        onSelectListener.onSelectRecord(getAdapterPosition());
                    }
                }
            });
        }

        @Override
        public void setData(Record data) {
            super.setData(data);
            nameTv.setText(data.getName());
        }
    }

}
