package com.demo.app.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.demo.app.R;
import com.demo.app.util.Constents;

public class WelcomeActivity extends Activity {
    private final long SPLASH_LENGTH = 2000;
    Handler handler = new Handler();
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "PU9KsKxWhshrIYZETsxzsSGc9p5tqNDq");
        sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
        if (!sp.getBoolean("openApk", false)) {
            setContentView(R.layout.welcome);
            handler.postDelayed(new Runnable() { // 使用handler的postDelayed实现延时跳转

                public void run() {
                    Intent intent = new Intent(WelcomeActivity.this,
                            TabMainActivity.class);
                    startActivity(intent);
                    sp.edit().putBoolean("openApk", true).commit();
                    finish();
                }
            }, SPLASH_LENGTH);// 2秒后跳转至应用主界面MainActivity
        } else {
            Intent intent = new Intent(WelcomeActivity.this,
                    TabMainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
