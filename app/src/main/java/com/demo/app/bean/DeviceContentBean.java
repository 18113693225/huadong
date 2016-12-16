package com.demo.app.bean;
/**
 * 工作内容bean
 * @author Yong Ren
 *
 */
public class DeviceContentBean {

	// 主键id
	private int id ;
	// 设备id
	private int device_id ;
	// 设备名称
	private String device_name = "";
	// 设备内容序号
	private String number = "";
	// 设备内容描述
	private String content = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDevice_id() {
		return device_id;
	}
	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
