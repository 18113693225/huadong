package com.demo.app.bean;

import com.demo.app.util.Common;

/**
 * 设备管理 - 备品备件登记薄 bean
 * @author Yong Ren
 *
 */
public class SparePartsBean extends DateBean {

	// 主键id
	private int id ;
	//创建时间
	private String create_time = Common.getStringTime();
	//gps
	private String gps = "";
	// 项目名称，关联ID
	private int entry_name_id ;	
	private String entry_name_name = "";
	// 运行单位，关联公司表
	private int company_id ;
	private String company_name = "";
	// 填表人，默认为最后一次修改人
	private int fill_in_user_id ;
	private String fill_in_user_name = "";
	// 序号
	//private int serial_number ;
	// 备件名称
	//private String parts_name = ""; 
	
	//设备ID
	private int device_id ;
	//设备名称
	private String device_name = "";
	// 规格
	private String specifications = "";
	// 入库数量
	private String storage_quantity = "";
	// 入库时间
	private String storage_time = "";
	// 出库数量
	private String outgoing_quantity = "";
	// 出库时间
	private String delivery_time = "";
	// 库存余额
	private String stock_balance = "";
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
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	public String getStorage_quantity() {
		return storage_quantity;
	}
	public void setStorage_quantity(String storage_quantity) {
		this.storage_quantity = storage_quantity;
	}
	public String getStorage_time() {
		return storage_time;
	}
	public void setStorage_time(String storage_time) {
		this.storage_time = storage_time;
	}
	public String getOutgoing_quantity() {
		return outgoing_quantity;
	}
	public void setOutgoing_quantity(String outgoing_quantity) {
		this.outgoing_quantity = outgoing_quantity;
	}
	public String getDelivery_time() {
		return delivery_time;
	}
	public void setDelivery_time(String delivery_time) {
		this.delivery_time = delivery_time;
	}
	public String getStock_balance() {
		return stock_balance;
	}
	public void setStock_balance(String stock_balance) {
		this.stock_balance = stock_balance;
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
