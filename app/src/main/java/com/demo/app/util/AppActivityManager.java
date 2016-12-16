package com.demo.app.util;

import java.util.Stack;

import android.app.Activity;
import android.util.Log;

/**
 * 管理activityu
 * 
 * @author Administrator
 *
 */
public class AppActivityManager {
	String TAG = "AppActivityManager";
	private static Stack<Activity> activityStack;
	private static AppActivityManager instance = new AppActivityManager();

	private AppActivityManager() {
	}

	/**
	 * 单例
	 */
	public static AppActivityManager getAppManager() {
		return instance;
	}

	/**
	 * 增加	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		if (activityStack.contains(activity)) {
			activityStack.remove(activity);
		}
		activityStack.add(activity);
	}

	/**
	 * 移除
	 */
	public void removeActivity(Activity activity) {
		if (activityStack != null) {
			activityStack.remove(activity);
		}
	}

	/**
	 * 返回当前栈顶activity
	 */
	public Activity currentActivity() {
		if (activityStack != null) {
			Activity activity = activityStack.lastElement();
			return activity;
		}
		return null;
	}

	/**
	 * 结束栈顶所有activity
	 */
	public void finishActivity() {
		if (activityStack != null) {
			Activity activity = activityStack.lastElement();
			finishActivity(activity);
		} else {
			Log.w(TAG,"When invork 'finishActivity()' activityStack is null !.");
		}
	}

	/**
	 * 结束指定activity
	 */
	public void finishActivity(Activity activity) {
		if (null != activityStack && activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		} else {
			Log.w(TAG,
					"When invork 'finishActivity(Activity activity)' activityStack is ["
							+ activityStack + "]" + " activity is [" + activity
							+ "]");
		}
	}

	/**
	 * 结束指定名称activity
	 */
	public void finishActivity(Class<?> cls) {
		if (null != activityStack) {
			for (Activity activity : activityStack) {
				if (activity.getClass().equals(cls)) {
					finishActivity(activity);
				}
			}
		} else {
			Log.w(TAG,
					"When invork 'finishActivity(Class<?> cls)' activityStack is null");
		}
	}

	/**
	 * 关闭所有Activity
	 */
	public void finishAllActivity() {
		if (null != activityStack) {
			for (int i = 0, size = activityStack.size(); i < size; i++) {
				if (null != activityStack.get(i)) {
					activityStack.get(i).finish();
				}
			}
			activityStack.clear();
		} else {
			Log.w(TAG,
					"When invork 'finishAllActivity()' activityStack is null");
		}
	}

	/**
	 * 列出所有activity
	 */
	public void listAllActivity() {
		if (null != activityStack) {
			if(activityStack.size()>0){
				for (int i = 0, size = activityStack.size(); i < size; i++) {
					System.out.println(activityStack.get(i));
				}
			}else{
				System.out.println("activity size equal 0!");
			}
		}
	}
}
