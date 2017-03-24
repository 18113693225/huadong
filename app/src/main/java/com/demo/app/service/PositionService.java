package com.demo.app.service;


import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class PositionService {

    private LocationClient locationClient = null;
    private LocationClientOption option = null;
    private BDLocationListener listener = null;

    public PositionService(Context context) {
        ensureSaneDefaults();
        if (null == locationClient) {
            locationClient = new LocationClient(context.getApplicationContext());
            locationClient.setLocOption(option);
        }
    }

    private void ensureSaneDefaults() {
        if (option == null) {
            option = defaultOption();
        }
    }

    private LocationClientOption defaultOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(2 * 1000);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setIsNeedLocationDescribe(true);
        option.setTimeOut(10000);
        return option;
    }

    public void registerListener(BDLocationListener mListener) {
        listener = mListener;
        locationClient.registerLocationListener(mListener);
    }

    public void unRegisterListener() {
        if (listener != null) {
            locationClient.unRegisterLocationListener(listener);
        }
    }

    public void start() {
        if (!locationClient.isStarted()) {
            locationClient.start();
            locationClient.requestLocation();
        }
    }

    public void stop() {
        if (locationClient != null) {
            locationClient.stop();
        }
    }
}
