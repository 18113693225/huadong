package com.demo.app.bean;

import com.demo.app.util.Common;

/**
 * 电力运行 - 运行月报表
 * @author Yong Ren
 *
 */
public class RunMonthlyReportBean extends DateBean {

	// 主键ID
	private int id ;
	// 项目名称，关联id
	private int entry_name_id ;
	private String entry_name_name = "";
	// 运行单位，关联公司表
	private int company_id ;
	private String company_name = "";
	// 填表人
	private int fill_in_user_id ;
	private String fill_in_user_name = "";
	//安全运行天数
	private int safe_running_days ;
	//本月倒闸操作数
	private int reverse_operation;
	//本月全站电量合计
	private String total_quantity_of_electricity = "";
	//主要设备最高负荷
	private String maximum_load = "";
	//巡视次数
	private int number_of_visits ;
	//设备定期试验切换
	private int switch_number ;
	//变压器分接头开关档位调整
	private int stall_number ;
	//全站定值核对
	private int fixed_check ;
	//本月主设备、线路停、送电情况
	private String stop_give_info = "";
	//发现、处理、遗留缺陷情况
	private String excetion_info = "";
	//gps
	private String gps = "";
	//创建时间
	private String create_time = Common.getStringTime();
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
	public String getEntry_name_name() {
		return entry_name_name;
	}
	public void setEntry_name_name(String entry_name_name) {
		this.entry_name_name = entry_name_name;
	}
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public int getFill_in_user_id() {
		return fill_in_user_id;
	}
	public void setFill_in_user_id(int fill_in_user_id) {
		this.fill_in_user_id = fill_in_user_id;
	}
	public String getFill_in_user_name() {
		return fill_in_user_name;
	}
	public void setFill_in_user_name(String fill_in_user_name) {
		this.fill_in_user_name = fill_in_user_name;
	}
	public int getSafe_running_days() {
		return safe_running_days;
	}
	public void setSafe_running_days(int safe_running_days) {
		this.safe_running_days = safe_running_days;
	}
	public int getReverse_operation() {
		return reverse_operation;
	}
	public void setReverse_operation(int reverse_operation) {
		this.reverse_operation = reverse_operation;
	}
	public String getTotal_quantity_of_electricity() {
		return total_quantity_of_electricity;
	}
	public void setTotal_quantity_of_electricity(
			String total_quantity_of_electricity) {
		this.total_quantity_of_electricity = total_quantity_of_electricity;
	}
	public String getMaximum_load() {
		return maximum_load;
	}
	public void setMaximum_load(String maximum_load) {
		this.maximum_load = maximum_load;
	}
	public int getNumber_of_visits() {
		return number_of_visits;
	}
	public void setNumber_of_visits(int number_of_visits) {
		this.number_of_visits = number_of_visits;
	}
	public int getSwitch_number() {
		return switch_number;
	}
	public void setSwitch_number(int switch_number) {
		this.switch_number = switch_number;
	}
	public int getStall_number() {
		return stall_number;
	}
	public void setStall_number(int stall_number) {
		this.stall_number = stall_number;
	}
	public int getFixed_check() {
		return fixed_check;
	}
	public void setFixed_check(int fixed_check) {
		this.fixed_check = fixed_check;
	}
	public String getStop_give_info() {
		return stop_give_info;
	}
	public void setStop_give_info(String stop_give_info) {
		this.stop_give_info = stop_give_info;
	}
	public String getExcetion_info() {
		return excetion_info;
	}
	public void setExcetion_info(String excetion_info) {
		this.excetion_info = excetion_info;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
}
