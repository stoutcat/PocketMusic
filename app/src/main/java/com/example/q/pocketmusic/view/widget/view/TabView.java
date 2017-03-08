package com.example.q.pocketmusic.view.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;

/**
 * Created by Cloud on 2017/2/11.
 */

public class TabView extends LinearLayout {
    private Context context;
    private ImageView ico;
    private TextView tv;
    private int selectResource;
    private int noSelectResource;

    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_bottom_tab, this);
        ico = (ImageView) view.findViewById(R.id.ico_iv);
        tv = (TextView) view.findViewById(R.id.name_tv);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabView);
        selectResource = array.getResourceId(R.styleable.TabView_itemTabSelectIco, 0);
        noSelectResource = array.getResourceId(R.styleable.TabView_itemTabNoSelectIco, 0);
        String name = array.getString(R.styleable.TabView_itemTabName);
        ico.setImageResource(noSelectResource);
        tv.setText(name);
        array.recycle();

    }

    public void onSelect(Boolean isSelect) {
        if (isSelect) {
            ico.setImageResource(selectResource);
            tv.setTextColor(ContextCompat.getColor(context, R.color.TabBottomText));
        } else {
            tv.setTextColor(ContextCompat.getColor(context, R.color.Text));
            ico.setImageResource(noSelectResource);
        }
    }
}
