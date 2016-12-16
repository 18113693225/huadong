package com.demo.app.bean;
/**
 * 权限表
 * @author Yong Ren
 *
 */
public class AuthorityBean extends DateBean {

	// id
	private int id ;
	//栏目ID  1:电力运行 2：电力检修 3：项目管理 4：安全文明 5：设备管理 6：两票管理 7：客户管理
	private int column_id ;
	//栏目子类别  栏目类型ID 
	/*
	 * 1-1：电气设备巡视卡 
		1-2：红外线测温记录薄 
		1-3：运行月报表 
		2-1：电气检修记录卡 
		3-1：值班表 
		3-2：工作联系单 
		4-1：安全检查巡视卡 
		4-2：环境检查巡视卡 
		5-1：安全工具表 
		5-2：备品备件登记薄 
		5-3：设备台账登记表 
		6-1：工作票统计表 
		6-2：操作票统计表 
		7-1：工作联系单 
		7-2：停（送）电联系单 
		7-3：用户信息反馈表
	 */
	private String column_type_id ;
	
	//表单id
	private int form_id ;
//	//是否可编辑  1：可编辑  0：不可编辑
//	private int val_edit ;
//	//是否可查看  1：可以查看 0 ：不可以查看
//	private int val_see ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getColumn_id() {
		return column_id;
	}
	public void setColumn_id(int column_id) {
		this.column_id = column_id;
	}
	public String getColumn_type_id() {
		return column_type_id;
	}
	public void setColumn_type_id(String column_type_id) {
		this.column_type_id = column_type_id;
	}
	public int getForm_id() {
		return form_id;
	}
	public void setForm_id(int form_id) {
		this.form_id = form_id;
	}
//	public int getVal_edit() {
//		return val_edit;
//	}
//	public void setVal_edit(int val_edit) {
//		this.val_edit = val_edit;
//	}
//	public int getVal_see() {
//		return val_see;
//	}
//	public void setVal_see(int val_see) {
//		this.val_see = val_see;
//	}
}
