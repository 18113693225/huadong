package com.demo.app.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;

import com.baidu.mapapi.SDKInitializer;
import com.demo.app.util.CustomConstants;

/**
 * @author Administrator
 */
public class MainApplication extends Application {

    private boolean serviceIsRunning = false;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub

		/*NetworkData.getInstance().login(new NetworkResponceFace() {

			@Override
			public void callback(String result) {
				// TODO Auto-generated method stub
				
			}
		}, "111", "222");*/
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        SDKInitializer.initialize(getApplicationContext());
        removeTempFromPref();
//	    ActivityManager manager = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
//	    for (RunningServiceInfo service :manager.getRunningServices(Integer.MAX_VALUE)){
//	    	System.out.println(service.service.getClassName());
//	    	if("com.demo.app.service.BackgroundService".equals(service.service.getClassName())){
//	    		System.out.println("++++++++++++service is running++++++++++++++++++"+service.service.getClassName());
//	    		serviceIsRunning = true;
//	    		break;
//	    	}
//	    }
//	    if(!serviceIsRunning){
//    		System.out.println("++++++++++++app start service ++++++++++++++++++");
//	    	startService(new Intent(this, BackgroundService.class));
//	    }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    private void removeTempFromPref() {
        SharedPreferences sp = getSharedPreferences(
                CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
        sp.edit().remove(CustomConstants.PREF_TEMP_IMAGES).commit();
    }
}
