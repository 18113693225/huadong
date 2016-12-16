package com.demo.app.bean;

import com.demo.app.util.Common;

/**
 * 设备管理 - 设备台账登记薄 bean
 * @author Yong Ren
 *
 */
public class EquipmentLedgerBean extends DateBean {

	// 主键id
	private int id ;
	//创建时间
	private String create_time = Common.getStringTime();
	//gps
	private String gps = "";
	// 项目名称，关联id
	private int entry_name_id ;
	private String entry_name_name = "";
	// 运行单位，关联公司表
	private int comany_id ;
	private String company_name = "";
	// 填表人，默认为最后一次修改人
	private int fill_in_user_id ;
	private String fill_in_user_name = "";
	
	//设备ID
	private int device_id ;
	//设备名称
	private String device_name = "";
	// 序号
	//private int serial_number ;
	// 资产编号
	private String asset_number = "";
	// 资产名称
	private String asset_name = "";
	// 规格型号
	private String specification_model = "";
	// 生产厂家
	private String manufacturer = "";
	// 生产日期
	private String production_date = "";
	// 数量
	private int size ;
	// 预试时间
	private String pre_test_time = "";
	// 备注
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
	public int getComany_id() {
		return comany_id;
	}
	public void setComany_id(int comany_id) {
		this.comany_id = comany_id;
	}
	public int getFill_in_user_id() {
		return fill_in_user_id;
	}
	public void setFill_in_user_id(int fill_in_user_id) {
		this.fill_in_user_id = fill_in_user_id;
	}
	public String getAsset_number() {
		return asset_number;
	}
	public void setAsset_number(String asset_number) {
		this.asset_number = asset_number;
	}
	public String getAsset_name() {
		return asset_name;
	}
	public void setAsset_name(String asset_name) {
		this.asset_name = asset_name;
	}
	public String getSpecification_model() {
		return specification_model;
	}
	public void setSpecification_model(String specification_model) {
		this.specification_model = specification_model;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getProduction_date() {
		return production_date;
	}
	public void setProduction_date(String production_date) {
		this.production_date = production_date;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getPre_test_time() {
		return pre_test_time;
	}
	public void setPre_test_time(String pre_test_time) {
		this.pre_test_time = pre_test_time;
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
}
