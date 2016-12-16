package com.demo.app.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 安全文明 - 安全检查巡视卡 
 * @author Yong Ren
 *
 */
public class SafetyTrainingBean extends DateBean {

	// 主键id
	private int id ;
	// 项目名称 ，关联id
	private int entry_name_id ;
	private String entry_name_name = "";
	//项目类别
	private String project_type = "";
	//运行单位
	private int company_id ;
	private String company_name = "";
	//gps
	private String GPS = "";
	//检测时间
	private String create_time = "";
	//巡视人
	private int fill_in_user_id ;
	private String fill_in_user_name = "";
	//安全选项 字符串类型
	private String security_options = "";
	//设备
	private int device_id ;
	private String device_name = "";
	//设备编号
	private String device_number = "";
	//设备内容集合
	/**
	 * 设备内容集合
	 * 
	 * map 对应参数值  :
	 * device_content_id 设备内容ID
	 * select_val 选择值  0：异常  1：正常
	 */
	private List<Map<Object , Object>> deviceContentList = new ArrayList<Map<Object,Object>>();
	
	//巡视结果  0:异常  1：正常
	private int check_result = 0 ;
	//备注
	private String note = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEntry_name_id() {
		return entry_name_id;
	}
	public void setEntry_name_id(int entry_name_id) {
		this.entry_name_id = entry_name_id;
	}
	public String getEntry_name_name() {
		return entry_name_name;
	}
	public void setEntry_name_name(String entry_name_name) {
		this.entry_name_name = entry_name_name;
	}
	public String getProject_type() {
		return project_type;
	}
	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getGPS() {
		return GPS;
	}
	public void setGPS(String gPS) {
		GPS = gPS;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public int getFill_in_user_id() {
		return fill_in_user_id;
	}
	public void setFill_in_user_id(int fill_in_user_id) {
		this.fill_in_user_id = fill_in_user_id;
	}
	public String getFill_in_user_name() {
		return fill_in_user_name;
	}
	public void setFill_in_user_name(String fill_in_user_name) {
		this.fill_in_user_name = fill_in_user_name;
	}
	public String getSecurity_options() {
		return security_options;
	}
	public void setSecurity_options(String security_options) {
		this.security_options = security_options;
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
	public String getDevice_number() {
		return device_number;
	}
	public void setDevice_number(String device_number) {
		this.device_number = device_number;
	}
	public int getCheck_result() {
		return check_result;
	}
	public void setCheck_result(int check_result) {
		this.check_result = check_result;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List<Map<Object, Object>> getDeviceContentList() {
		return deviceContentList;
	}
	public void setDeviceContentList(List<Map<Object, Object>> deviceContentList) {
		this.deviceContentList = deviceContentList;
	}
}
