package com.demo.app.bean;

import com.demo.app.util.Common;

/**
 * 电力检修 - 电力检修记录卡
 * @author Yong Ren
 *
 */
public class MaintenanceRecordBean extends DateBean {

	//主键id
	private int id ;
	//项目名称，关联ID
	private int entry_name_id ;
	//项目名称
	private String entry_name_name = "";
	//运行单位，关联公司表
	private int company_id ;
	//运行单位
	private String company_name = "";
	//检修人员，自动关联手机
	private int m_personnel_id ;
	//检修人员
	private String m_personnel_name = "";
	//记录GPS和地址信息
	private String GPS = "";
	//工作内容
	private String work_number = "";
	//检修类别（下拉选择各种工作票票名）
	private int check_type ;
	//开始时间
	private String work_start_time = Common.getStringTime();
	//工作完成时间
	private String work_end_time = "";
	//检修结果（下拉选择 1：合格 ，0：不合格）
	private int maintenance_result ;
	//工作负责人（下拉选择项目组人员）
	private int person_in_charge_id ;
	//工作负责人
	private String person_in_charge_name = "";
	//创建时间
	private String create_time = Common.getStringTime();
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
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	public int getM_personnel_id() {
		return m_personnel_id;
	}
	public void setM_personnel_id(int m_personnel_id) {
		this.m_personnel_id = m_personnel_id;
	}
	public String getGPS() {
		return GPS;
	}
	public void setGPS(String gPS) {
		GPS = gPS;
	}
	public String getWork_number() {
		return work_number;
	}
	public void setWork_number(String work_number) {
		this.work_number = work_number;
	}
	public int getCheck_type() {
		return check_type;
	}
	public void setCheck_type(int check_type) {
		this.check_type = check_type;
	}
	public String getWork_start_time() {
		return work_start_time;
	}
	public void setWork_start_time(String work_start_time) {
		this.work_start_time = work_start_time;
	}
	public String getWork_end_time() {
		return work_end_time;
	}
	public void setWork_end_time(String work_end_time) {
		this.work_end_time = work_end_time;
	}
	public int getMaintenance_result() {
		return maintenance_result;
	}
	public void setMaintenance_result(int maintenance_result) {
		this.maintenance_result = maintenance_result;
	}
	public int getPerson_in_charge_id() {
		return person_in_charge_id;
	}
	public void setPerson_in_charge_id(int person_in_charge_id) {
		this.person_in_charge_id = person_in_charge_id;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public String getM_personnel_name() {
		return m_personnel_name;
	}
	public void setM_personnel_name(String m_personnel_name) {
		this.m_personnel_name = m_personnel_name;
	}
	public String getPerson_in_charge_name() {
		return person_in_charge_name;
	}
	public void setPerson_in_charge_name(String person_in_charge_name) {
		this.person_in_charge_name = person_in_charge_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
}
