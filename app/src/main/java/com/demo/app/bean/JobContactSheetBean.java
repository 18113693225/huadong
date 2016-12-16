package com.demo.app.bean;
/**
 * 项目管理 - 工作联系单
 * @author Yong Ren
 *
 */
public class JobContactSheetBean extends DateBean {

	// 主键ID
	private int id ;
	// 项目名称，关联id
	private int entry_name_id ;
	private String entry_name_name = "";
	// 操作人，关联id
	private int operator_id ;
	private String operator_name = "";
	// 填表日期
	private String create_time = "";
	// 通知人员，多个用户id  ,
	/*
	 *该字段修改规则
	 *1.修改int为string，来记录多个通知人员id信息
	 *2.多个id信息写入规则：'36','59' , 这样的规则，为了方便后台能很好的查询数据 
	 */
	private String inform_staff = ""; 
	//通知人员  姓名
	private String inform_staff_name = "" ;
	// 通知内容
	private String notification_content = "";
	// 执行人确认
	private int executor_confirmation ;
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
	public String getInform_staff() {
		return inform_staff;
	}
	public void setInform_staff(String inform_staff) {
		this.inform_staff = inform_staff;
	}
	public String getNotification_content() {
		return notification_content;
	}
	public void setNotification_content(String notification_content) {
		this.notification_content = notification_content;
	}
	public int getExecutor_confirmation() {
		return executor_confirmation;
	}
	public void setExecutor_confirmation(int executor_confirmation) {
		this.executor_confirmation = executor_confirmation;
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
	public String getInform_staff_name() {
		return inform_staff_name;
	}
	public void setInform_staff_name(String inform_staff_name) {
		this.inform_staff_name = inform_staff_name;
	}
	
}
