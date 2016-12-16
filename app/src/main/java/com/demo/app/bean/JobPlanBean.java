package com.demo.app.bean;
/**
 * 工作计划
 * @author Yong Ren
 *
 */
public class JobPlanBean extends DateBean {

	// id
	private int id ;
	// 开始时间
	private String start_time = "";
	// 结束时间
	private String end_time = "";
	// 标题
	private String title = "";
	// 内容
	private String content = "";
	//创建人id
	private int create_user_id ;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(int create_user_id) {
		this.create_user_id = create_user_id;
	}
	
}
