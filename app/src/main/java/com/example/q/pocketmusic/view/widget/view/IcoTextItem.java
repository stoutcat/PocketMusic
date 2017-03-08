package com.example.q.pocketmusic.view.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allenliu.badgeview.BadgeFactory;
import com.allenliu.badgeview.BadgeView;
import com.example.q.pocketmusic.R;

/**
 * Created by YQ on 2016/10/17.
 */

public class IcoTextItem extends RelativeLayout {
    private TextView mTitleTv;
    private TextView mSubTv;
    private ImageView mIco;

    private Context context;
    private String mTitle;
    private int icoId;
    private BadgeView badgeView;

    public IcoTextItem(Context context) {
        this(context, null);
    }

    public IcoTextItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IcoTextItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ico_text, this);
        mTitleTv = (TextView) view.findViewById(R.id.title_tv);
        mIco = (ImageView) view.findViewById(R.id.ico);
        mSubTv = (TextView) view.findViewById(R.id.subtitle_tv);

        TypedArray types = context.obtainStyledAttributes(attrs, R.styleable.IcoTextItem);
        mTitle = types.getString(R.styleable.IcoTextItem_itemTitleText);
        icoId = types.getResourceId(R.styleable.IcoTextItem_itemTitleIco, 0);
        types.recycle();

        //初始化文字
        mTitleTv.setText(mTitle);
        mIco.setImageResource(icoId);
    }

    public void setSubText(String subText) {
        mSubTv.setVisibility(VISIBLE);
        mSubTv.setText(subText);
    }

    public void bindBadge(Boolean isBind) {
        if (isBind) {
            if (badgeView == null) {
                badgeView = BadgeFactory.createDot(context)
                        .bind(mIco);
            }
        } else {
            if (badgeView!=null){
                badgeView.unbind();
            }
        }
    }


}
