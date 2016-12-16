package com.demo.app.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 电力运行 - 电气设备巡视卡
 *
 * @author Yong Ren
 */
public class InspectionCardBean extends DateBean {

    // 主键id
    private int id;
    // 项目名称，关联id
    private int entry_name_id;
    private String entry_name_name = "";
    //项目类别，只做显示
    private String project_item = "";
    // 运行单位，关联公司表
    private int company_id;
    private String company_name = "";
    // 巡视时间
    private String check_time = "";
    // 巡视人
    private int inspection_man;
    private String create_user_name = "";
    // 设备名称
    private int device_id;
    private String device_name = "";
    //设备内容集合
    /**
     * 设备内容集合
     * <p>
     * map 对应参数值  :
     * device_content_id 设备内容ID
     * select_val 选择值  0：异常  1：正常
     */
    private List<Map<Object, Object>> deviceContentList = new ArrayList<Map<Object, Object>>();
    // 设备编号
    private String equipment_number = "";
    // 巡视结果   0:异常  1：正常
    private int inspection_result;
    //巡视图片
    private List<String> photos = new ArrayList<>();
    // 巡视内容
    private String inspection_content = "";
    // GPS定位
    private String GPS = "";

    //缺陷类别 ， 危机缺陷  、重大缺陷 、 一般缺陷
    private String defect_type = "";

    //是否有图片
    private int imgNumber;

    public int getImgNumber() {
        return imgNumber;
    }

    public void setImgNumber(int imgNumber) {
        this.imgNumber = imgNumber;
    }

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

    public String getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }

    public int getInspection_man() {
        return inspection_man;
    }

    public void setInspection_man(int inspection_man) {
        this.inspection_man = inspection_man;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_name(int device_id) {
        this.device_id = device_id;
    }

    public String getEquipment_number() {
        return equipment_number;
    }

    public void setEquipment_number(String equipment_number) {
        this.equipment_number = equipment_number;
    }

    public int getInspection_result() {
        return inspection_result;
    }

    public void setInspection_result(int inspection_result) {
        this.inspection_result = inspection_result;
    }

    public String getInspection_content() {
        return inspection_content;
    }

    public void setInspection_content(String inspection_content) {
        this.inspection_content = inspection_content;
    }

    public String getGPS() {
        return GPS;
    }

    public void setGPS(String gPS) {
        GPS = gPS;
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

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public List<Map<Object, Object>> getDeviceContentList() {
        return deviceContentList;
    }

    public void setDeviceContentList(List<Map<Object, Object>> deviceContentList) {
        this.deviceContentList = deviceContentList;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public String getProject_item() {
        return project_item;
    }

    public void setProject_item(String project_item) {
        this.project_item = project_item;
    }

    public String getDefect_type() {
        return defect_type;
    }

    public void setDefect_type(String defect_type) {
        this.defect_type = defect_type;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

}
