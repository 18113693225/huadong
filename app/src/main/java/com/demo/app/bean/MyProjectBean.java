package com.demo.app.bean;

public class MyProjectBean extends DateBean{

	// 项目ID
	private int id ;
	// 项目名称
	private String e_project_name = "";
	//巡视人id
	private int create_user_id ;
	// 巡视人名字
	private String user_name = "";
	// 巡视人公司
	private String company_name = "";
	// 巡视时间
	private String create_time = "";
	//巡视类别  1：电气设备巡视卡  2：红外线测温记录薄
	private String type = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getE_project_name() {
		return e_project_name;
	}
	public void setE_project_name(String e_project_name) {
		this.e_project_name = e_project_name;
	}
	public int getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(int create_user_id) {
		this.create_user_id = create_user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
