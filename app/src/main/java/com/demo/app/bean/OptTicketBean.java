package com.demo.app.bean;

import com.demo.app.util.Common;

/**
 * 两票管理 -操作票统计表
 * @author Yong Ren
 *
 */
public class OptTicketBean extends DateBean {
	
	/**
	 * 合格
	 */
	public static final int QUALIFIED_OK = 1 ;
	/**
	 * 不合格
	 */
	public static final int QUALIFIED_NO = 0 ;

	//主键ID
	private int id ;
	//项目名称 关联ID
	private int entry_name_id ;
	//项目名称
	private String entry_name_name ="";
	//运行单位，关联公司表
	private int company_id ;
	//运行单位
	private String company_name = "";
	//创建时间
	private String create_time = Common.getStringTime();
	//记录人 ，关联用户表
	private int create_user_id ;
	
	//记录人
	private String create_user_name = "";
	//序列号
	private String serial_number = "";
	//操作票编号
	private String operation_number="" ;
	//操作人，关联用户表
	private int operation_user_id ;
	//操作人
	private String operation_user_name = "";
	//开始操作时间
	private String oper_start_time = "";
	//结束操作时间
	private String oper_end_time = "";
	//是否合格 1:合格  0 ：不合格
	private int qualified = 0 ;
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
	public int getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(int create_user_id) {
		this.create_user_id = create_user_id;
	}
	public String getOperation_number() {
		return operation_number;
	}
	public void setOperation_number(String operation_number) {
		this.operation_number = operation_number;
	}
	public int getOperation_user_id() {
		return operation_user_id;
	}
	public void setOperation_user_id(int operation_user_id) {
		this.operation_user_id = operation_user_id;
	}
	public String getOper_start_time() {
		return oper_start_time;
	}
	public void setOper_start_time(String oper_start_time) {
		this.oper_start_time = oper_start_time;
	}
	public String getOper_end_time() {
		return oper_end_time;
	}
	public void setOper_end_time(String oper_end_time) {
		this.oper_end_time = oper_end_time;
	}
	public int getQualified() {
		return qualified;
	}
	public void setQualified(int qualified) {
		this.qualified = qualified;
	}
	public static int getQualifiedOk() {
		return QUALIFIED_OK;
	}
	public static int getQualifiedNo() {
		return QUALIFIED_NO;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
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
	public String getOperation_user_name() {
		return operation_user_name;
	}
	public void setOperation_user_name(String operation_user_name) {
		this.operation_user_name = operation_user_name;
	}
}

