package com.demo.app.bean;
/**
 * gps bean
 * @author Yong Ren
 *
 */
public class GPSBean extends DateBean {

	// 主键DI
	private int id ;
	// 用户ID
	private int user_id ;
	// 经度
	private String  longitude = "";
	// 纬度
	private String latitude = "";
	// 地址
	private String address = "";
	// 时间
	private String creat_time = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCreat_time() {
		return creat_time;
	}
	public void setCreat_time(String creat_time) {
		this.creat_time = creat_time;
	}
}
