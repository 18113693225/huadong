package com.demo.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常量定义类
 *
 * @author Yong Ren
 */
public class Constents {

    //网络请求 生产环境
    public static final String REQUEST_URL = "http://api.sc-huadong.cn:8686";
    //测试环境
    //龙哥
//    public static final String REQUEST_URL = "http://192.168.1.164:8080/electricproperty";
    //七届
//    public static final String REQUEST_URL = "http://192.168.1.94:8080/electricproperty";
    public static final String SHARE_CONFIG = "ele";

    /**
     * 微信分享用
     */
    public static final String Wx_APP_ID = "wx022d818355c7417f";
    /**
     * 新浪微博分享用
     */
    public static final String Sina_APP_ID = "2655841444";
    /**
     * 腾讯QQ分享用
     */
    public static final String Qq_APP_ID = "1105717734";
    /**
     * 枚举
     */
    //类别
    public static final String ENUM_TYPE_CATEGORY = "{'1':'变电站','2','火电站','3','水电站','4','风电','5','太阳能','6','输电线路','7','电力电缆','8','其他'}";
    //电压等级
    public static final String ENUM_TYPE_VOLTAGE_LEVEL = "{'1':'0.5kV','2','10kV','3','35kV','4','66kV','5','110kV','6','220kV','7','330kV','8','500kV'}";
    //行业类别
    public static final String ENUM_TYPE_INDUSTRY_CATEGORIES = "{'1':'房地产','2','工业','3','能源','4','交通','5':'其他'}";
    //合作意向
    public static final String ENUM_TYPE_COOPERATION_INTENTION = "{'1':'运行','2','检修','3','调试','4','技改','5','其他'}";
    //专业
    public static final String ENUM_TYPE_COOPERATION_PROFESSIONAL = "{'1':'项目经理','2','电气自动化','3','一次安装','4','二次安装','5','电气调试','6','继电保护','7','高压试验','8','其他','9':'运行','10':'检修'}";
    //相关证书
    public static final String ENUM_TYPE_COOPERATION_CERTIFICATE = "{'1':'初级职称','2','中级职称','3','高级职称','4','低压电工','5','高压电工','6','特种作业证','7','继电保护','8','其他'}";
    /**
     * 关键字定义 定义的数字将和数据库表e_constants对应
     */
    public static final int TYPE_CATEGORY = 100; // 类别
    public static final int TYPE_VOLTAGE_LEVEL = 101; // 电压等级
    public static final int TYPE_INDUSTRY_CATEGORIES = 102; // 行业类别
    public static final int TYPE_COOPERATION_INTENTION = 103; // 合作意向
    public static final int TYPE_MAJOR = 104; // 专业
    public static final int TYPE_CERTIFICATE = 105; // 证书
    public static final int TYPE_PROJECT_TYPE = 106; // 项目类别
    public static final int TYPE_TOOL = 107; // 工器具
    /**
     * 保存项目ID
     */
    public static int current_project_id = 0;
    /**
     * 保存设备ID
     */
    public static int current_device_id = 0;
    /**
     * 存放不同设备巡视卡内容
     */
    public static List<Map<Object, Object>> xscontentList = new ArrayList<Map<Object, Object>>();
    /**
     * 存放不同设备巡视卡错误的内容
     */
    public static List<Map<Object, Object>> xserrorcontentList = new ArrayList<Map<Object, Object>>();
    public static Map<String, ArrayList<Map<Object, Object>>> contentListMap = new HashMap<String, ArrayList<Map<Object, Object>>>();
    /**
     * 存放所有巡视卡(上传使用)
     */
    public static List<List<Map<Object, Object>>> xskcontentList = new ArrayList<>();
    /**
     * 存放不同设备巡视卡内容，检修任务使用
     */
    public static List<Map<Object, Object>> jxcontentList = new ArrayList<Map<Object, Object>>();
    /**
     * 存放当项目ID下设备个数
     */
    public static int devicenum = 0;
    /**
     * 存放不同的项目名称
     */
    public static int xmname;
    /**
     * 存放不同的类别名称
     */
    public static int lbname;
}
