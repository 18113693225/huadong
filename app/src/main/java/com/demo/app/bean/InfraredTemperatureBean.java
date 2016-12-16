package com.demo.app.bean;


/**
 * 电力运行 - 红外线测温记录薄
 * @author Yong Ren
 *
 */
public class InfraredTemperatureBean extends DateBean {

	// 主键id
	private int id ;
	//项目名称 ，关联id
	private int entry_name_id ;
	private String entry_name_name = "";
	//运行单位，关联公司表
	private int company_id ;
	private String company_name = "";
	//gps
	private String GPS = "";
	//检测时间
	private String create_time = "";
	//天气情况
	private String weather = "";
	//环境温度
	private String ambient_temperature = "";
	//记录人
	private int record_user_id ;
	private String create_user_name = "";
	//间隔单元
	private String interval_unit = "";
	//设备名称
	private int device_id ;
	private String device_name = "";
	//A相 测温温度（℃）
	private double a_temp ;
	//B相 测温温度（℃）
	private double b_temp ;
	//C相 测温温度（℃）
	private double c_temp ;
	//检查结果  0：异常  1：正常
	private int check_result ;
	//缺陷类别 ， 危机缺陷  、重大缺陷 、 一般缺陷
	private String defect_type = "";
	//巡视备注
	private String check_info = "";
	
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
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getAmbient_temperature() {
		return ambient_temperature;
	}
	public void setAmbient_temperature(String ambient_temperature) {
		this.ambient_temperature = ambient_temperature;
	}
	public int getRecord_user_id() {
		return record_user_id;
	}
	public void setRecord_user_id(int record_user_id) {
		this.record_user_id = record_user_id;
	}
	public String getInterval_unit() {
		return interval_unit;
	}
	public void setInterval_unit(String interval_unit) {
		this.interval_unit = interval_unit;
	}
	public int getDevice_id() {
		return device_id;
	}
	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}
	public double getA_temp() {
		return a_temp;
	}
	public void setA_temp(double a_temp) {
		this.a_temp = a_temp;
	}
	public double getB_temp() {
		return b_temp;
	}
	public void setB_temp(double b_temp) {
		this.b_temp = b_temp;
	}
	public double getC_temp() {
		return c_temp;
	}
	public void setC_temp(double c_temp) {
		this.c_temp = c_temp;
	}
	public String getEntry_name_name() {
		return entry_name_name;
	}
	public void setEntry_name_name(String entry_name_name) {
		this.entry_name_name = entry_name_name;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCreate_user_name() {
		return create_user_name;
	}
	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getGPS() {
		return GPS;
	}
	public void setGPS(String gPS) {
		GPS = gPS;
	}
	public int getCheck_result() {
		return check_result;
	}
	public void setCheck_result(int check_result) {
		this.check_result = check_result;
	}
	public String getDefect_type() {
		return defect_type;
	}
	public void setDefect_type(String defect_type) {
		this.defect_type = defect_type;
	}
	public String getCheck_info() {
		return check_info;
	}
	public void setCheck_info(String check_info) {
		this.check_info = check_info;
	}
	
}
