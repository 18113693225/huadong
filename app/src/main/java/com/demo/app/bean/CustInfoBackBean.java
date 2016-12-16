package com.demo.app.bean;
/**
 * 客户管理-用户信息反馈表
 * @author Yong Ren
 *
 */
public class CustInfoBackBean extends DateBean {

	//主键ID
	private int id ;
	//项目名称，关联ID
	private int entry_name_id ;
	//项目名称
	private String entry_name_name = "";
	//运行单位，关联公司表
	private int company_id ;
	//运行单位
	private String company_name = "";
	//创建时间
	private String create_time = "";
	//记录人，关联用户表
	private int create_user_id ;
	//记录人
	private String create_user_name = "";
	//工作内容
	private String work_content = "";
	//服务态度（0：非常满意/1：满意/2:一般/3:不满意）
	private int service_attitude ; 
	//技术水平（0：非常满意/1：满意/2:一般/3:不满意）
	private int technical_level ;
	//安全文明管理（0：非常满意/1：满意/2:一般/3:不满意）
	private int  safety_civilization ;
	//总体评价（不输入也可提交）
	private String overall_evaluation = "";
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
	public String getWork_content() {
		return work_content;
	}
	public void setWork_content(String work_content) {
		this.work_content = work_content;
	}
	
	public int getService_attitude() {
		return service_attitude;
	}
	public void setService_attitude(int service_attitude) {
		this.service_attitude = service_attitude;
	}
	public int getTechnical_level() {
		return technical_level;
	}
	public void setTechnical_level(int technical_level) {
		this.technical_level = technical_level;
	}
	public int getSafety_civilization() {
		return safety_civilization;
	}
	public void setSafety_civilization(int safety_civilization) {
		this.safety_civilization = safety_civilization;
	}
	public String getOverall_evaluation() {
		return overall_evaluation;
	}
	public void setOverall_evaluation(String overall_evaluation) {
		this.overall_evaluation = overall_evaluation;
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

}
