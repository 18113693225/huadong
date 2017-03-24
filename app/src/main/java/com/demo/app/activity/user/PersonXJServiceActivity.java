package com.demo.app.activity.user;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


public class PersonXJServiceActivity extends BaseActivity {
    MapView mMapView = null;
    BaiduMap baiduMap = null;
    private SharedPreferences sp;


    BitmapDescriptor blue = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_location);

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        SDKInitializer.initialize(getApplicationContext());
        setContentViewWithoutTitle(R.layout.person_xj_service_layout);
        TitleCommon.setTitle(this, null, "巡检服务", TabMainActivity.class, true);
        mMapView = (MapView) findViewById(R.id.bmapView);
        sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
        setUpMap();
    }

    public void setUpMap() {
        if (null == baiduMap) {
            baiduMap = mMapView.getMap();
            double latitude = Double.valueOf(sp.getString("latitude", "1")).doubleValue();
            double longitude = Double.valueOf(sp.getString("longitude", "1")).doubleValue();
            LatLng center = new LatLng(latitude, longitude);
            //定义地图状态
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(center)
                    .zoom(18)
                    .build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            MarkerOptions ooA = new MarkerOptions().position(center).icon(blue);
            baiduMap.addOverlay(ooA);
            baiduMap.setMapStatus(mMapStatusUpdate);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }


}
