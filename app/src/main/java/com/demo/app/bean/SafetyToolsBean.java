package com.demo.app.bean;

import com.demo.app.util.Common;

/**
 * 设备管理 - 安全工具表格 bean 
 * @author Yong Ren
 *
 */
public class SafetyToolsBean extends DateBean {

	//主键ID
	private int id ;
	//创建时间
	private String create_time = Common.getStringTime();
	//gps
	private String gps = "";
	//项目名称，关联ID
	private int entry_name_id ;
	private String entry_name_name = "";
	//运行单位，关联公司表
	private int company_id ;
	private String company_name= "";
	//填表人，默认为最后一次修改人
	private int fill_in_user_id ;
	private String fill_in_user_name = "";
	//设备ID
	private int device_id ;
	//设备名称
	private String device_name = "" ;
	//设备编号
	private String device_number = "";
	
	//工具名称 下拉可选择，验电器，绝缘手套...
	private int tool_id ;
	private String tool_name = "";
	//型号
	private String model = "";
	//电压等级（220kV，110kV，66KV,35KV,10KV） ，通过接口获取
	private int voltage_level ;
	
	private String voltage_level_name = "";
	//数量
	private int size ;
	//试验日期
	private String test_time = "";
	//试验结果（1：合格   0:不合格）
	private int test_result ;
	//下次试验日期
	private String next_test = "";
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
	public int getFill_in_user_id() {
		return fill_in_user_id;
	}
	public void setFill_in_user_id(int fill_in_user_id) {
		this.fill_in_user_id = fill_in_user_id;
	}
	public String getDevice_number() {
		return device_number;
	}
	public void setDevice_number(String device_number) {
		this.device_number = device_number;
	}
	public int getTool_id() {
		return tool_id;
	}
	public void setTool_id(int tool_id) {
		this.tool_id = tool_id;
	}
	public String getTool_name() {
		return tool_name;
	}
	public void setTool_name(String tool_name) {
		this.tool_name = tool_name;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getVoltage_level() {
		return voltage_level;
	}
	public void setVoltage_level(int voltage_level) {
		this.voltage_level = voltage_level;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getTest_time() {
		return test_time;
	}
	public void setTest_time(String test_time) {
		this.test_time = test_time;
	}
	public int getTest_result() {
		return test_result;
	}
	public void setTest_result(int test_result) {
		this.test_result = test_result;
	}
	public String getNext_test() {
		return next_test;
	}
	public void setNext_test(String next_test) {
		this.next_test = next_test;
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
	public String getFill_in_user_name() {
		return fill_in_user_name;
	}
	public void setFill_in_user_name(String fill_in_user_name) {
		this.fill_in_user_name = fill_in_user_name;
	}
	public int getDevice_id() {
		return device_id;
	}
	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getVoltage_level_name() {
		return voltage_level_name;
	}
	public void setVoltage_level_name(String voltage_level_name) {
		this.voltage_level_name = voltage_level_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	
}
