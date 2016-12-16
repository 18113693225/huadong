package com.demo.app.bean;

import com.demo.app.util.Common;


/**
 * 两票管理 - 工作票统计表
 * @author Yong Ren
 *
 */
public class WorkTicketBean extends DateBean {
	
	//主键id
	private int id ;

	//项目名称，关联ID
	private int entry_name_id ;
	
	//项目名称
	private String entry_name_name = "";
	//运行单位，关联公司表
	private int company_id ;
	//运行单位
	private String company_name= "" ;
	//创建时间
	private String create_time = Common.getStringTime();
	//记录人，关联用户表
	private int create_user_id ;
	//记录人
	private String create_user_name= "";
	//序列号
	private String serial_number = "";
	//工作编号
	private String work_number = "" ;
	//工作票名称（下拉选择各种工作票票名）
	private int work_ticket_id ; 
	//工作票名称
	private String word_ticket_name = "";
	//工作许可人
	private int permit_user_id ;
	//工作许可人
	private String permit_user_name = "";
	//工作负责人
	private String work_user_name = "" ;
	//工作签发人
	private int issue_user_id ;
	//工作签发人
	private String issue_user_name = "";
	//开始时间
	private String work_start_time = "" ;
	//结束时间
	private String work_end_time = "" ;
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
	public int getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(int create_user_id) {
		this.create_user_id = create_user_id;
	}
	public String getWork_number() {
		return work_number;
	}
	public void setWork_number(String work_number) {
		this.work_number = work_number;
	}
	public int getPermit_user_id() {
		return permit_user_id;
	}
	public void setPermit_user_id(int permit_user_id) {
		this.permit_user_id = permit_user_id;
	}
	public String getWork_user_name() {
		return work_user_name;
	}
	public void setWork_user_name(String work_user_name) {
		this.work_user_name = work_user_name;
	}
	public int getIssue_user_id() {
		return issue_user_id;
	}
	public void setIssue_user_id(int issue_user_id) {
		this.issue_user_id = issue_user_id;
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
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public int getWork_ticket_id() {
		return work_ticket_id;
	}
	public void setWork_ticket_id(int work_ticket_id) {
		this.work_ticket_id = work_ticket_id;
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
	public String getWord_ticket_name() {
		return word_ticket_name;
	}
	public void setWord_ticket_name(String word_ticket_name) {
		this.word_ticket_name = word_ticket_name;
	}
	public String getPermit_user_name() {
		return permit_user_name;
	}
	public void setPermit_user_name(String permit_user_name) {
		this.permit_user_name = permit_user_name;
	}
	public String getIssue_user_name() {
		return issue_user_name;
	}
	public void setIssue_user_name(String issue_user_name) {
		this.issue_user_name = issue_user_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
