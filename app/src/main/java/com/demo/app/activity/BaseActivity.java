package com.demo.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.demo.app.util.AppActivityManager;

/**
 * all activity extends baseActivity .manager the activity
 *
 * @author fliao
 */
public class BaseActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        AppActivityManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//		AppActivityManager.getAppManager().removeActivity(this);
    }

    /**
     * specific layout without title
     *
     * @param layoutResID
     * @param titleLayoutResID
     */
    public void setContentViewWithoutTitle(int layoutResID) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(layoutResID);
    }

    /**
     * full screen
     *
     * @param layoutResID
     */
    public void setContentViewFullScreen(int layoutResID) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //全屏
        super.setContentView(layoutResID);
    }

    /**
     * ues orifinal method that has not changed
     *
     * @param layoutResID
     */
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }
}
