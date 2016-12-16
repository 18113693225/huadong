package com.demo.app.application;

import android.app.Application;
import android.content.SharedPreferences;

import com.demo.app.util.CustomConstants;
import com.squareup.leakcanary.LeakCanary;

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
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
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
    }

    private void removeTempFromPref() {
        SharedPreferences sp = getSharedPreferences(
                CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
        sp.edit().remove(CustomConstants.PREF_TEMP_IMAGES).commit();
    }
}
