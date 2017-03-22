package com.demo.app.service;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.APSService;
import com.demo.app.util.Constents;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class BackgroundService extends Service implements AMapLocationListener {

    private final static String TAG = "###Background service###";
    // 声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    // 声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = null;

    private SharedPreferences sp;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
        setLocation();
        startService(new Intent(this, APSService.class));
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        mLocationClient.stopLocation();
//		mLocationClient.onDestroy();
        super.onDestroy();
    }

    /**
     * <初始化定位>
     */
    public void setLocation() {
        // 初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        // 设置定位回调监听
        mLocationClient.setLocationListener(this);
        // 初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
        mLocationOption.setLocationMode(AMapLocationMode.Battery_Saving);
        // 设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        // 设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        // 设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(false);
        // 设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        // 设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2 * 1000);
        // 给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 启动定位
        mLocationClient.startLocation();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        try {
            if (Build.VERSION.SDK_INT > 18) {
                startForeground(1120, new Notification());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        // TODO Auto-generated method stub
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                // 定位成功回调信息，设置相关消息
                // 定位成功把用户坐标存放起来
                sp.edit().putString("currentAddress", amapLocation.getAddress()).commit();
                sp.edit().putString("longitude", amapLocation.getLongitude() + "").commit();
                sp.edit().putString("latitude", amapLocation.getLatitude() + "").commit();
            } else {
                // 显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                sp.edit().putString("currentAddress", "定位失败").commit();
            }
        }
    }
}
