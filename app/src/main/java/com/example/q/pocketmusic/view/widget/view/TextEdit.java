package com.example.q.pocketmusic.view.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;


/**
 * Created by Cloud on 2017/2/10.
 */

public class TextEdit extends LinearLayout {
    private Context context;
    private EditText inputEdt;
    private ImageView ico;

    //一定要用this！不要用super！
    public TextEdit(Context context) {
        this(context, null);
    }

    //一定要用this！不要用super！
    public TextEdit(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_text_edt, this);
        inputEdt = (EditText) view.findViewById(R.id.input_edt);
        ico = (ImageView) view.findViewById(R.id.ico_iv);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextEdit);
        String hintString = array.getString(R.styleable.TextEdit_itemInputHint);
        boolean isPassword = array.getBoolean(R.styleable.TextEdit_itemInputPassword, false);
        int resourceId = array.getResourceId(R.styleable.TextEdit_itemIcoResource, 0);
        if (resourceId != 0) {
            ico.setImageResource(resourceId);
        }
        inputEdt.setHint(hintString);
        if (isPassword) {
            inputEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);//密码隐藏
        }
        array.recycle();
    }

    public String getInputString() {
        return inputEdt.getText().toString().trim();
    }

    public void setInputString(String s) {
        inputEdt.setText(s);
    }
}
