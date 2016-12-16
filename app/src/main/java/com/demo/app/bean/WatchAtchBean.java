package com.demo.app.bean;

/**
 * 项目管理 - 值班表
 * 
 * @author Yong Ren
 * 
 */
public class WatchAtchBean extends DateBean {

	// 主键id
	private int id;
	// gps
	private String gps = "";
	// 项目类型
	private String project_type = "";
	// 项目名称 ，关联id
	private int entry_name_id;
	private String entry_name_name = "";
	// 运行单位，关联公司表
	private int company_id;
	private String company_name = "";
	// 填表人
	private int fill_in_user_id;
	private String fill_in_user_name = "";
	// 创建时间
	private String create_time = "";
	// 班名，一班，二班..
	private String class_name = "";
	// 值长
	// 值长名字
	private String long_value= "";
	// 正值班员
	private String positive_class = "";
	// 正值班员名字
//	private String positive_class_name;
	// 副值班员
	private String deputy_duty_officer = "";
	// 副值班员名字
//	private String deputy_duty_officer_name = "";
	// 实习人员
	private String intern = "";
	// 实习人员 名字
//	private String intern_name = "";
	// 值班时间，具体的时间值
	private String duty_time = "";
	// 值班日期（白班/夜班/全天/具体日期）
	// 1白 0黑
	private String duty_date = "";

	// 值班开始时间
	private String duty_start_time = "";
	// 值班结束时间
	private String duty_end_time = "";
	// 工作班组 ，当前记录选择人员名字，逗号隔开
	private String work_group = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	public String getProject_type() {
		return project_type;
	}
	public void setProject_type(String project_type) {
		this.project_type = project_type;
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
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getLong_value() {
		return long_value;
	}
	public void setLong_value(String long_value) {
		this.long_value = long_value;
	}
	public String getPositive_class() {
		return positive_class;
	}
	public void setPositive_class(String positive_class) {
		this.positive_class = positive_class;
	}
	public String getDeputy_duty_officer() {
		return deputy_duty_officer;
	}
	public void setDeputy_duty_officer(String deputy_duty_officer) {
		this.deputy_duty_officer = deputy_duty_officer;
	}
	public String getIntern() {
		return intern;
	}
	public void setIntern(String intern) {
		this.intern = intern;
	}
	public String getDuty_time() {
		return duty_time;
	}
	public void setDuty_time(String duty_time) {
		this.duty_time = duty_time;
	}
	public String getDuty_date() {
		return duty_date;
	}
	public void setDuty_date(String duty_date) {
		this.duty_date = duty_date;
	}
	public String getDuty_start_time() {
		return duty_start_time;
	}
	public void setDuty_start_time(String duty_start_time) {
		this.duty_start_time = duty_start_time;
	}
	public String getDuty_end_time() {
		return duty_end_time;
	}
	public void setDuty_end_time(String duty_end_time) {
		this.duty_end_time = duty_end_time;
	}
	public String getWork_group() {
		return work_group;
	}
	public void setWork_group(String work_group) {
		this.work_group = work_group;
	}
	

}
