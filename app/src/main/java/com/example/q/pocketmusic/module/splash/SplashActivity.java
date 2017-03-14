package com.example.q.pocketmusic.module.splash;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseLongArray;
import android.view.MotionEvent;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.home.HomeActivity;

public class SplashActivity extends BaseActivity {
    private static final int ENTER_HOME_ACTIVITY = 1001;
    private boolean isEnter = false;
    private Handler handler = new Handler();

    @Override
    public int setContentResource() {
        return R.layout.activity_splash;
    }

    @Override
    public void setListener() {

    }

    @Override
    public void init() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isEnter) {
                    enterHomeActivity();
                }
                finish();
            }
        }, 2000);
    }

    private void enterHomeActivity() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnter) {
            enterHomeActivity();
            isEnter = true;
        }
        return true;
    }
}
