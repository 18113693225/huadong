package com.demo.app.bean;

/**
 * 客户管理-工作联系单bean
 * @author Yong Ren
 *
 */
public class WorkContackBean extends DateBean{

	/**
	 * 状态-确认
	 */
	public static final int STATUS_OK = 1 ;
	/**
	 * 状态-非确认
	 */
	public static final int STATUS_NO = 0 ;
	//主键
	private int id ;
	//项目名称 ，关联ID
	private int entry_name_id ;
	//项目名称
	private String entry_name_name = "";
	//操作人 关联用户表ID
	private int operator_id ;
	//操作人
	private String operator_name = "";
	//创建时间
	private String create_time = "";
	//通知人员，用户ID
	private int notify_the_staff ;
	//通知人员 姓名
	private String notify_the_staff_name = "" ;
	//通知内容
	private String contents_notice = "";
	//状态 1：确认  0：非确认
	private int status ;
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
	public int getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(int operator_id) {
		this.operator_id = operator_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public int getNotify_the_staff() {
		return notify_the_staff;
	}
	public void setNotify_the_staff(int notify_the_staff) {
		this.notify_the_staff = notify_the_staff;
	}
	public String getNotify_the_staff_name() {
		return notify_the_staff_name;
	}
	public void setNotify_the_staff_name(String notify_the_staff_name) {
		this.notify_the_staff_name = notify_the_staff_name;
	}
	public String getContents_notice() {
		return contents_notice;
	}
	public void setContents_notice(String contents_notice) {
		this.contents_notice = contents_notice;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public static int getStatusOk() {
		return STATUS_OK;
	}
	public static int getStatusNo() {
		return STATUS_NO;
	}
	public String getEntry_name_name() {
		return entry_name_name;
	}
	public void setEntry_name_name(String entry_name_name) {
		this.entry_name_name = entry_name_name;
	}
	public String getOperator_name() {
		return operator_name;
	}
	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}
	
}
