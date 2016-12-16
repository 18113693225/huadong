package com.demo.app.util;


import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.app.R;
/**
 * 自定义Toast
 * @author Administrator
 *
 */
public class CustomeToast {
	private static Toast toastCustome;
	/**
	 * 自定义Toast
	 * @param ctx 上下文
	 * @param msg 消息
	 * @param icon 图标
	 * @param location bottom,center,top 显示位置
	 */
	public static void showCustomeToast(Context ctx,String msg,int icon,String location){
		View layout = (LinearLayout) View.inflate(ctx,R.layout.custome_toast_layout, null);
		TextView toastText = (TextView)layout.findViewById(R.id.toastText);
		ImageView toastIcon = (ImageView)layout.findViewById(R.id.toastImage);
		toastText.setText(msg);
		toastIcon.setImageResource(icon);
		toastCustome = new Toast(ctx);
		 if("bottom".equals(location)){
			toastCustome.setGravity(Gravity.BOTTOM, 0,50);			
		}else if("top".equals(location)){
			toastCustome.setGravity(Gravity.TOP, 0,50);			
		}else{
			toastCustome.setGravity(Gravity.CENTER, 0,0);			
		}
		toastCustome.setView(layout);
		toastCustome.show();
	}
	/**
	 * 默认toast
	 * @param ctx
	 * @param msg
	 * @param time
	 */
	public static void showDefaultToast(Context ctx,String msg,String time){
		Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
	}
}
