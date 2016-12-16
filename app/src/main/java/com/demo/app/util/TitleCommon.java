/**
 * 
 */
package com.demo.app.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONException;
import org.json.JSONObject;

import com.demo.app.R;
import com.demo.app.activity.TabMainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author S
 *
 */
public class TitleCommon {
	/**
	 * 
	 * @param mContext 上下文
	 * @param view 
	 * @param title 名称
	 * @param cls 上一个activity
	 * @param btVisible 返回按钮是否存在
	 * <设置Title>
	 */
	public static void setTitle(final Context mContext, View view, String title, final Class<?> cls,
			boolean btVisible) {
		// FrameLayout ll = (FrameLayout) LayoutInflater.from(mContext).inflate(
		// R.layout.common_title, null);
		Button bt;
		TextView tt;
		final Activity mActivity = (Activity) mContext;
		if (view != null) {
			bt = (Button) view.findViewById(R.id.title_back);
			tt = (TextView) view.findViewById(R.id.title_name);
		} else {
			bt = (Button) mActivity.findViewById(R.id.title_back);
			tt = (TextView) mActivity.findViewById(R.id.title_name);
		}
		tt.setText(title);
		if (!btVisible) {
			bt.setVisibility(View.GONE);
		} else {
			bt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(cls==null){
						mActivity.finish();
					}else{
						Intent registerIntent = new Intent();
						registerIntent.setClass(mContext, cls);
						mContext.startActivity(registerIntent);
						mActivity.finish();						
					}
				}
			});
		}
	}

	/**
	 * 
	 * @param mContext
	 * @param time
	 *            时间
	 * @param address
	 *            地址
	 */
	public static void setGpsTitle(Context mContext) {
		Activity mActivity = (Activity) mContext;
		SharedPreferences sp = mContext.getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		TextView ttime = (TextView) mActivity.findViewById(R.id.title_gps_time);
		TextView taddress = (TextView) mActivity.findViewById(R.id.title_gps_address);
		Calendar c = Calendar.getInstance();
		String time = (new SimpleDateFormat("yyyy年MM月dd日")).format(c.getTime());
		ttime.setText("填表时间："+time);
		taddress.setText("GPS定位："+sp.getString("currentAddress", "定位失败"));
	}

	/**
	 * @param activity 注册activity
	 * @param result 服务器返回消息
	 * <处理注册消息>
	 */
	public static void handlerRegist(Handler mHandler, final Activity activity, String result) {
		final Context mContext = (Context) activity;
		JSONObject json;
		try {
			json = new JSONObject(result);
			String status = json.getString("status");
			if ("success".equals(status)) {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(mContext, "注册成功！", Toast.LENGTH_SHORT).show();
						Intent i = new Intent();
						i.setClass(mContext, TabMainActivity.class);
						mContext.startActivity(i);
						activity.finish();
					}
				});
			} else {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(mContext, "注册失败！", Toast.LENGTH_SHORT).show();
					}
				});
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	  /** 
     * 获取网落图片资源  
     * @param url 
     * @return 
     */  
	public static Bitmap getHttpBitmap(final String url) {
		//创建一个线程池 
        ExecutorService pool = Executors.newFixedThreadPool(1); 
       System.out.println("解析图片："+url);
        Callable c = new Callable(){
			@Override
			public Object call() throws Exception {
				URL myFileURL;
				Bitmap bitmap = null;
				try {
					myFileURL = new URL(url);
					// 获得连接
					HttpURLConnection conn = (HttpURLConnection) myFileURL
							.openConnection();
					// 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
					conn.setConnectTimeout(6000);
					// 连接设置获得数据流
					conn.setDoInput(true);
					// 不使用缓存
					conn.setUseCaches(false);
					// 这句可有可无，没有影响
					// conn.connect();
					// 得到数据流
					InputStream is = conn.getInputStream();
					// 解析得到图片
					bitmap = BitmapFactory.decodeStream(is);
					// 关闭数据流
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return bitmap;
			}
        }; 
        Future f = pool.submit(c);
		try {
			return (Bitmap) f.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static void getHttpBitmapThread(final String url,final Handler handler,final int what) {
		System.out.println("解析图片："+url);
		new Thread(new Runnable() {
			public void run() {
				URL myFileURL;
				Bitmap bitmap = null;
				try {
					myFileURL = new URL(url);
					// 获得连接
					HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
					// 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
					conn.setConnectTimeout(6000);
					// 连接设置获得数据流
					conn.setDoInput(true);
					// 不使用缓存
					conn.setUseCaches(false);
					// 这句可有可无，没有影响
					// conn.connect();
					// 得到数据流
					InputStream is = conn.getInputStream();
					// 解析得到图片
					bitmap = BitmapFactory.decodeStream(is);
					// 关闭数据流
					is.close();
					handler.obtainMessage(what, bitmap).sendToTarget();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
