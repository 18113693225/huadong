package com.demo.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import android.annotation.SuppressLint;
import com.demo.app.bean.DateBean;
import com.google.gson.Gson;

/**
 * 公共资源类
 * @author Yong Ren
 *
 */
public class Common {
	
	/**
	 * 将对象转换为json字符串
	 * @param c 类对象
	 * @return
	 */
	public static String toJson(DateBean bean){
		Gson gson = new Gson();
		return gson.toJson(bean);
	}
	
	
	public static String toJson(Map<String, Object> map){
		Gson gson = new Gson();
		return gson.toJson(map);
	}
	
	
	public static String getStringTime(){
		Date nowTime=new Date();
		System.out.println(nowTime);
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return time.format(nowTime) ;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String changeTime(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
		try {
			Date date = sdf.parse(time);
			String tm = sdf2.format(date);
			return tm;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/*public static String getFileName(int index){
		String fileName = "";
		
		switch (index) {
		case 1:
			fileName = "变电站（发电厂）倒闸操作票";
			break;
		case 2:
			fileName = "变电站（发电厂）第二种工作票";
			break;
		case 3:
			fileName = "变电站（发电厂）第一种工作票";
			break;
		case 4:
			fileName = "变电站二级动火工作票";
			break;
		case 5:
			fileName = "变电站一级动火工作票";
			break;
		case 6:
			fileName = "电力电缆第二种工作票";
			break;
		case 7:
			fileName = "电力电缆第一种工作票";
			break;
		case 8:
			fileName = "二次工作安全措施票";
			break;
			//电力运行
		case 9:
			fileName = "避雷器动作记录线下薄";
			break;
		case 10:
			fileName = "变压器分接开关运行记录线下薄";
			break;
		case 11:
			fileName = "断路器跳闸记录线下薄";
			break;
		case 12:
			fileName = "继电保护整定值记录线下薄";
			break;
		case 13:
			fileName = "交接班记录线下薄";
			break;
		case 14:
			fileName = "接地线装拆记录线下薄";
			break;
		case 15:
			fileName = "命令记录线下簿";
			break;
		case 16:
			fileName = "设备定期试验轮换记录线下簿";
			break;
		case 17:
			fileName = "五防”解锁钥匙使用登记线下薄";
			break;
		case 18:
			fileName = "消防安全检查登记线下薄";
			break;
		default:
			break;
		}
		
		return fileName ;
	}
	
	public static int getFileNameIndex(String name){
		int index = 0;
		
		if(name.equals("变电站（发电厂）倒闸操作票"))
			index = 1 ;
		else if(name.equals(  "变电站（发电厂）第二种工作票"))
			index = 2;
		else if(name.equals( "变电站（发电厂）第一种工作票"))
			index = 3;
		else if(name.equals( "变电站二级动火工作票"))
			index = 4 ;
		else if(name.equals(  "变电站一级动火工作票"))
			index = 5 ;
		else if(name.equals(  "电力电缆第二种工作票"))
			index = 6;
		else if(name.equals( "电力电缆第一种工作票"))
			index = 7;
		else if(name.equals(  "二次工作安全措施票"))
			index = 8;
			//电力运行
		else if(name.equals( "避雷器动作记录线下薄"))
			index = 9;
		else if(name.equals( "变压器分接开关运行记录线下薄"))
			index = 10;
		else if(name.equals( "断路器跳闸记录线下薄"))
			index = 11;
		else if(name.equals( "继电保护整定值记录线下薄"))
			index = 12;
		else if(name.equals(  "交接班记录线下薄"))
			index = 13;
		else if(name.equals( "接地线装拆记录线下薄"))
			index = 14;
		else if(name.equals(  "命令记录线下簿"))
			index = 15;
		else if(name.equals(  "设备定期试验轮换记录线下簿"))
			index = 16;
		else if(name.equals(  "五防”解锁钥匙使用登记线下薄"))
			index = 17;
		else if(name.equals(  "消防安全检查登记线下薄"))
			index = 18;
		return index ;
	}
*/
}
