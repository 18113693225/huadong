package com.demo.app.bean;

public class ListData {
	
	public static final int SEND = 1;
	public static final int RECEIVER = 2;
	private String content;
	private String list="";
	private int flag;
	private String time;
	
	public ListData(String content,int flag,String time,String list) {
		setContent(content);
		setFlag(flag);
		setTime(time);
		setList(list);
	}
	public ListData(String content,int flag,String time) {
		setContent(content);
		setFlag(flag);
		setTime(time);
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}
}

