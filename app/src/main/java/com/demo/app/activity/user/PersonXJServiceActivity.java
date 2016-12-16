package com.demo.app.activity.user;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.util.TitleCommon;

import android.os.Bundle;
import android.util.Log;

public class PersonXJServiceActivity extends BaseActivity implements LocationSource,AMapLocationListener{
	//声明变量
    private MapView mapView;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption = null;
    
    
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewWithoutTitle(R.layout.person_xj_service_layout);
		TitleCommon.setTitle(this,null, "巡检服务", TabMainActivity.class,true);
		mapView = (MapView) findViewById(R.id.xjservicemap);
		mapView.onCreate(savedInstanceState);// 必须要写
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
	}
	public void setUpMap(){
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
//		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
		aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
//		aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
	}
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		// TODO Auto-generated method stub
		if (mListener != null && amapLocation != null) {
	         if (amapLocation != null
	             && amapLocation.getErrorCode() == 0) {
	             mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
	             amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
	             amapLocation.getLatitude();//获取纬度
	             amapLocation.getLongitude();//获取经度
	             amapLocation.getAccuracy();//获取精度信息
	             SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	             Date date = new Date(amapLocation.getTime());
	             df.format(date);//定位时间
	         } else {
	             String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
	             Log.e("AmapErr",errText);
	         }
	     }
	}
	@Override
	public void activate(OnLocationChangedListener listener) {
		// TODO Auto-generated method stub
		mListener = listener;
		if (mlocationClient == null) {
			mlocationClient = new AMapLocationClient(this);
			mLocationOption = new AMapLocationClientOption();
			//设置定位监听
			mlocationClient.setLocationListener(this);
			//设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
//			mLocationOption.setLocationMode(AMapLocationMode.Device_Sensors);
			mLocationOption.setInterval(2000);
//			mLocationOption.setOnceLocation(true);
			// 设置是否返回地址信息（默认返回地址信息）
			mLocationOption.setNeedAddress(true);
			// 设置是否只定位一次,默认为false
			mLocationOption.setOnceLocation(false);
			// 设置是否强制刷新WIFI，默认为强制刷新
			mLocationOption.setWifiActiveScan(true);
			// 设置是否允许模拟位置,默认为false，不允许模拟位置
			mLocationOption.setMockEnable(false);
			//设置定位参数
			mlocationClient.setLocationOption(mLocationOption);
			mlocationClient.startLocation();
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
			
		}
	}
	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}
}
