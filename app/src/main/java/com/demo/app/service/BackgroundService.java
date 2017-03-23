package com.demo.app.service;


import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.APSService;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.demo.app.util.Constents;

public class BackgroundService extends Service implements BDLocationListener {

    private final static String TAG = "###Background service###";
    // 声明AMapLocationClient类对象
    private LocationClientOption option = null;
    // 声明mLocationOption对象
    private LocationClient locationClient = null;

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
        locationClient.stop();
//		mLocationClient.onDestroy();
        super.onDestroy();
    }

    /**
     * <初始化定位>
     */
    public void setLocation() {
        // 初始化定位
        locationClient = new LocationClient(getApplicationContext());
        // 设置定位回调监听
        locationClient.registerLocationListener(this);
        // 初始化定位
        option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(2 * 1000);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setIsNeedLocationDescribe(true);
        option.setTimeOut(10000);
        locationClient.setLocOption(option);
        locationClient.start();
        locationClient.requestLocation();
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
    public void onReceiveLocation(BDLocation bdLocation) {
        int type = bdLocation.getLocType();
        Log.i("TAG", "定位返回码：" + type);
        if (type == 61 || type == 66 || type == 161) {
            // 定位成功回调信息，设置相关消息
            // 定位成功把用户坐标存放起来
            sp.edit().putString("currentAddress", bdLocation.getAddrStr()).commit();
            sp.edit().putString("longitude", bdLocation.getLongitude() + "").commit();
            sp.edit().putString("latitude", bdLocation.getLatitude() + "").commit();
        } else {
            // 显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
            sp.edit().putString("currentAddress", "定位失败").commit();
        }

    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }
}
