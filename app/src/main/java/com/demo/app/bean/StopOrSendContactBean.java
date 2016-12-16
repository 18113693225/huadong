package com.demo.app.bean;

/**
 * 客户管理-停（送）点联系单
 * @author Yong Ren
 *
 */
public class StopOrSendContactBean extends DateBean {

	/**
	 * 已经执行
	 */
	public static final int STATUS_OK = 1 ;
	/**
	 * 未执行
	 */
	public static final int STATUS_NO = 0 ;
	
	//主键ID
	private int id ;
	//项目名称，关联ID
	private int entry_name_id ;
	//项目名称
	private String entry_name_name = "";
	//运行单位，关联公司表
	private int company_id ;
	//运行单位
	private String company_name = "" ;
	//创建时间
	private String create_time = "";
	//申请人，关联用户表id
	private int operator_id ;
	//申请人
	private String operator_name = "";
	//申请停送点时间
	private String blackout_began = "" ;
	//停送点内容
	private String blackout_content = "";
	//运维负责人确认，关联用户表id
	private int responsible_id ;
	
	//运维负责人
	private String responsible_name = "";
	//确认接收联系单时间
	private String reception_time = "";
	//执行情况 1：已经执行  0：未执行
	private int status ;
	//执行确认人，关联用户表id
	private int perform_confirmation ;
	//执行确认人
	private String perform_confirmation_name = "";
	//确认时间
	private String confirm_time = "";
	//备注
	private String remarks = "";
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
	public int getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(int operator_id) {
		this.operator_id = operator_id;
	}
	public String getBlackout_began() {
		return blackout_began;
	}
	public void setBlackout_began(String blackout_began) {
		this.blackout_began = blackout_began;
	}
	public String getBlackout_content() {
		return blackout_content;
	}
	public void setBlackout_content(String blackout_content) {
		this.blackout_content = blackout_content;
	}
	public int getResponsible_id() {
		return responsible_id;
	}
	public void setResponsible_id(int responsible_id) {
		this.responsible_id = responsible_id;
	}
	public String getReception_time() {
		return reception_time;
	}
	public void setReception_time(String reception_time) {
		this.reception_time = reception_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPerform_confirmation() {
		return perform_confirmation;
	}
	public void setPerform_confirmation(int perform_confirmation) {
		this.perform_confirmation = perform_confirmation;
	}
	public String getConfirm_time() {
		return confirm_time;
	}
	public void setConfirm_time(String confirm_time) {
		this.confirm_time = confirm_time;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getOperator_name() {
		return operator_name;
	}
	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}
	public String getResponsible_name() {
		return responsible_name;
	}
	public void setResponsible_name(String responsible_name) {
		this.responsible_name = responsible_name;
	}
	public String getPerform_confirmation_name() {
		return perform_confirmation_name;
	}
	public void setPerform_confirmation_name(String perform_confirmation_name) {
		this.perform_confirmation_name = perform_confirmation_name;
	}
}
