/**
 *
 */
package com.demo.app.activity;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.demo.app.R;
import com.demo.app.activity.index.IndexFragment;
import com.demo.app.activity.user.UserLoginFragment;

import com.demo.app.service.PositionService;
import com.demo.app.util.Constents;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Response;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author S
 */
public class TabMainActivity extends FragmentActivity implements Response, BDLocationListener {
    public static FragmentTabHost fragmentTabHost;
    public static ViewPager viewPage;
    private List<Fragment> list = new ArrayList<Fragment>();
    private String texts[] = {"首页", "咨询", "信息发布", "我"};
    private int imageButton[] = {R.drawable.icon_menu_home, R.drawable.icon_menu_comment, R.drawable.icon_menu_send, R.drawable.icon_menu_user_profile};
    private Class fragmentArray[] = {IndexFragment.class, UserConsultFragment.class, NoticeInfoFragment.class,
            UserLoginFragment.class};
    private SharedPreferences sp;
    private static int exit = 0;
    private static Long start;
    private static Long end;
    private IWeiboShareAPI mWeiboShareAPI;
    PositionService mPositionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tab_main_layout);
        sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
        // 实例化tabhost
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.maincontent);
        fragmentTabHost.setOnTabChangedListener(new OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                // TODO Auto-generated method stub
                FragmentManager fragmentManager = TabMainActivity.this.getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                int position = fragmentTabHost.getCurrentTab();
                for (int i = 0; i < list.size(); i++) {
                    if (position == i) {
                        transaction.show(list.get(i));
                    } else {
                        transaction.hide(list.get(i));
                    }
                }
                transaction.commit();
//	            viewPage.setCurrentItem(position);
            }
        });

        for (int i = 0; i < texts.length; i++) {
            TabSpec spec = fragmentTabHost.newTabSpec(texts[i]).setIndicator(getView(i));

            fragmentTabHost.addTab(spec, fragmentArray[i], null);

            // 设置背景(必须在addTab之后，由于需要子节点（底部菜单按钮）否则会出现空指针异常)
            // fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.bt_selector);
        }
        initPager();

        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(
                this, Constents.Sina_APP_ID);
        if (savedInstanceState != null) {
            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
        }
        if (null == mPositionService) {
            mPositionService = new PositionService(this);
            mPositionService.registerListener(this);
            mPositionService.start();
        }

    }

    private View getView(int i) {
        // 取得布局实例
        View view = View.inflate(this, R.layout.tab_icon, null);

        // 取得布局对象
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.text);

        // 设置图标
        imageView.setImageResource(imageButton[i]);
        // 设置标题
        textView.setText(texts[i]);
        return view;
    }

    private void initPager() {
        IndexFragment p1 = new IndexFragment();
        UserConsultFragment p2 = new UserConsultFragment();
        NoticeInfoFragment p3 = new NoticeInfoFragment();
        UserLoginFragment p4 = new UserLoginFragment();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
//		viewPage.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        exit++;
        System.out.println("###############exit" + exit);
        if (exit == 1) {
            start = System.currentTimeMillis();
            Toast.makeText(this, "再点击退出", Toast.LENGTH_SHORT).show();
            return;
        } else {
            end = System.currentTimeMillis();
            if (end - start < 3000) {
//				System.exit(0);
//				AppActivityManager.getAppManager().finishAllActivity();
                finish();
            } else {
                Toast.makeText(this, "再点击退出", Toast.LENGTH_SHORT).show();
                start = System.currentTimeMillis();
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        sp.edit().putBoolean("openApk", false).commit();
        sp.edit().putBoolean("hasLogin", false).commit();
        sp.edit().putString("user_project_id", "-1").commit();
        if (mPositionService != null) {
            mPositionService.unRegisterListener();
            mPositionService.stop();
        }
    }

    /**
     * @see {@link Activity#onNewIntent}
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }

    @Override
    public void onResponse(BaseResponse baseResp) {
        // TODO Auto-generated method stub
        if (baseResp != null) {
            switch (baseResp.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    Toast.makeText(this, "取消分享", Toast.LENGTH_LONG).show();
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    Toast.makeText(this, "分享失败" + "Error Message: " + baseResp.errMsg,
                            Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data,
                    qqShareListener);
        }
    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
//			Toast.makeText(TabMainActivity.this, "取消分享！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Toast.makeText(TabMainActivity.this, "分享完成！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            Toast.makeText(TabMainActivity.this, "分享失败！", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        int type = bdLocation.getLocType();
        if (type == 61 || type == 66 || type == 161) {
            sp.edit().putString("currentAddress", bdLocation.getAddrStr()).commit();
            sp.edit().putString("longitude", bdLocation.getLongitude() + "").commit();
            sp.edit().putString("latitude", bdLocation.getLatitude() + "").commit();
            Log.i("TAG", "定位返回码：" + type + "  经度:" + bdLocation.getLongitude() + "纬度:" + bdLocation.getLatitude());
        } else {
            sp.edit().putString("currentAddress", "定位失败").commit();
        }
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }
}
