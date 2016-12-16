package com.demo.app.network;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.demo.app.bean.AuthorityBean;
import com.demo.app.bean.CustInfoBackBean;
import com.demo.app.bean.DateBean;
import com.demo.app.bean.EnvironmentalInspectionBean;
import com.demo.app.bean.EquipmentLedgerBean;
import com.demo.app.bean.GPSBean;
import com.demo.app.bean.InfraredTemperatureBean;
import com.demo.app.bean.InspectionCardBean;
import com.demo.app.bean.JobContactSheetBean;
import com.demo.app.bean.JobLogBean;
import com.demo.app.bean.JobPlanBean;
import com.demo.app.bean.MaintenanceRecordBean;
import com.demo.app.bean.MyProjectBean;
import com.demo.app.bean.OptTicketBean;
import com.demo.app.bean.RunMonthlyReportBean;
import com.demo.app.bean.SafetyToolsBean;
import com.demo.app.bean.SafetyTrainingBean;
import com.demo.app.bean.SparePartsBean;
import com.demo.app.bean.StopOrSendContactBean;
import com.demo.app.bean.UserBean;
import com.demo.app.bean.WatchAtchBean;
import com.demo.app.bean.WorkContackBean;
import com.demo.app.bean.WorkTicketBean;
import com.demo.app.util.Common;
import com.google.gson.Gson;


public class NetworkData {

    private static NetworkData INSTANCE = null;

    private static final String TAG = "NetworkData";

    private static final String LIST = "List";
    private static final String ADD = "Add";
    private static final String UPDATE = "Update";
    private static final String DELETE = "Delete";

    //网络请求锁
//	private boolean regisLock = false ,loginLock = false , smsCodelock = false , describeInfolock = false ,downLoadFilelock = false ,
//			dateBeanByIDlock = false ,dateBeanListlock = false,dateBeanAddlock = false,dateBeanUpdatelock = false,dateBeanDeletelock = false,
//			describeInfoByIDlock = false ,updatePasswordlock = false ,consultationlock = false 
//			;


    public static NetworkData getInstance() {
//		if(INSTANCE == null)
        INSTANCE = new NetworkData();
        return INSTANCE;
    }

    /**
     * 用户登录信息
     *
     * @param face     请求结果回调类
     * @param phone    登录用户手机号码
     * @param password 登录密码
     * @return result 当前登录用户json格式数据
     */
    public void login(NetworkResponceFace face, String phone, String password) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                NetworkResponceFace face = (NetworkResponceFace) params[0];
                String phone = "" + params[1];
                String password = "" + params[2];

                String result = "";
                try {
                    HttpWorker hw = new HttpWorker();
                    String url = "/api/login";
                    Log.d(TAG, "checkRegist url:" + url);
                    JSONObject json = new JSONObject();
                    try {
                        json.accumulate("phone", phone)
                                .accumulate("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    result = hw.post(url, json.toString());
                    Log.d(TAG, "checkRegist result:" + result);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } catch (NetworkUnaviableException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } finally {
                    face.callback(result);
                }
                return null;
            }
        }.execute(face, phone, password);
    }

    /**
     * 异步检查注册信息
     * 主要是实现异步请求，通过接口模式实现数据的回传，即页面数据的显示
     *
     * @param face
     */
    public void regist(NetworkResponceFace face, UserBean bean) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                NetworkResponceFace face = (NetworkResponceFace) params[0];
                UserBean bean = (UserBean) params[1];
                String json = Common.toJson(bean);
                String result = "";
                try {
                    HttpWorker hw = new HttpWorker();
                    String url = "/api/regist";
                    Log.d(TAG, "checkRegist url:" + url);
                    result = hw.post(url, json);
                    Log.d(TAG, "checkRegist result:" + result);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } catch (NetworkUnaviableException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } finally {
                    face.callback(result);
                }
                return null;
            }
        }.execute(face, bean);
    }

    /**
     * 短信验证码发送请求
     *
     * @param face  回调接口
     * @param phone
     */
    public void smsCode(NetworkResponceFace face, String phone) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                NetworkResponceFace face = (NetworkResponceFace) params[0];
                String phone = (String) params[1];
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("phone", phone);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                String json = jsonObject.toString();
                String result = "";
                try {
                    HttpWorker hw = new HttpWorker();
                    String url = "/api/smsCode";
                    Log.d(TAG, "checkRegist url:" + url);
                    result = hw.post(url, json);
                    Log.d(TAG, "checkRegist result:" + result);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } catch (NetworkUnaviableException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } finally {
                    face.callback(result);
                }
                return null;
            }
        }.execute(face, phone);
    }

    /**
     * 公司简介
     *
     * @param face 回调接口
     */
    public void companyProfile(NetworkResponceFace face) {
        getDescribeInfo(face, "/api/companyProfile");
    }

    /**
     * 业务介绍
     *
     * @param face 回调接口
     */
    public void businessIntroduction(NetworkResponceFace face) {
        getDescribeInfo(face, "/api/businessIntroduction");
    }

    /**
     * 业务介绍详情
     * 单个业务介绍
     *
     * @param face
     * @param id   1:电力运维
     *             2:电力检修
     *             3:电力抢修
     *             4:电气调试
     *             5:技术服务
     *             6:输电线路运检
     */
    public void businessIntroductionInfo(NetworkResponceFace face, int id) {
        getDescribeInfoByID(face, "/api/businessIntroductionInfo", id);
    }

    /**
     * 信息发布数据获取
     *
     * @param face
     */
    public void informationDelivery(NetworkResponceFace face) {
        getDescribeInfo(face, "/api/informationDelivery");
    }

    /**
     * 信息发布数据详细信息获取
     *
     * @param face
     */
    public void informationDeliveryInfo(NetworkResponceFace face, int id) {
        getDescribeInfoByID(face, "/api/informationDeliveryInfo", id);
    }

    /**
     * 信息发布数据获取
     *
     * @param face
     */
    public void slideList(NetworkResponceFace face) {
        getDescribeInfo(face, "/api/slideList");
    }

    /**
     * 修改密码
     *
     * @param face
     * @param phone
     * @param code
     * @param password
     */
    public void updatePassword(NetworkResponceFace face, String phone, String code, String password) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                NetworkResponceFace face = (NetworkResponceFace) params[0];
                String phone = (String) params[1];
                String code = (String) params[2];
                String password = (String) params[3];
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("phone", phone);
                    jsonObject.put("code", code);
                    jsonObject.put("password", password);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                String json = jsonObject.toString();
                String result = "";
                try {
                    HttpWorker hw = new HttpWorker();
                    String url = "/api/updatePassword";
                    Log.d(TAG, "checkRegist url:" + url);
                    result = hw.post(url, json);
                    Log.d(TAG, "checkRegist result:" + result);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } catch (NetworkUnaviableException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } finally {
                    face.callback(result);
                }
                return null;
            }
        }.execute(face, phone, code, password);
    }


    /**
     * 个人信息修改
     *
     * @param phone       手机号码
     * @param user_name   用户姓名
     * @param address     地址信息
     * @param card_bank   开卡银行
     * @param card_number 银行卡号
     * @param user_id     用户id
     */
    public void updateEUserDetailInfo(NetworkResponceFace face, String phone, String user_name, String address, String card_bank, String card_number, String user_id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phone", phone);
        map.put("user_name", user_name);
        map.put("address", address);
        map.put("card_bank", card_bank);
        map.put("card_number", card_number);
        map.put("user_id", user_id);
        getDescribeInfo(face, "/api/updateEUserDetailInfo", map);
    }

    //////////////////////////////////////////////////


    /**
     * 获取描述信息
     * 主要是用来获取文字信息的接口数据
     *
     * @param face
     * @param url
     */
    private void getDescribeInfo(NetworkResponceFace face, final String url) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                NetworkResponceFace face = (NetworkResponceFace) params[0];
                String result = "";
                try {
                    HttpWorker hw = new HttpWorker();
                    Log.d(TAG, "checkRegist url:" + url);
                    result = hw.post(url, new JSONObject().toString());
                    Log.d(TAG, "checkRegist result:" + result);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } catch (NetworkUnaviableException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } finally {
                    face.callback(result);
                }
                return null;
            }
        }.execute(face);
    }

    /**
     * 获取描述信息
     * 主要是用来获取文字信息的接口数据
     *
     * @param face
     * @param url
     */
    private void getDescribeInfo(NetworkResponceFace face, String url, Map<String, Object> map) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                NetworkResponceFace face = (NetworkResponceFace) params[0];
                String url = (String) params[1];
                Map<String, Object> map = (Map<String, Object>) params[2];
                String result = "";
                try {
                    Gson gson = new Gson();
                    String json = gson.toJson(map);
                    HttpWorker hw = new HttpWorker();
                    result = hw.post(url, json);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } catch (NetworkUnaviableException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } finally {
                    face.callback(result);
                }
                return null;
            }
        }.execute(face, url, map);
    }

    /**
     * 获取描述信息根据ID值
     *
     * @param face
     * @param url
     */
    private void getDescribeInfoByID(NetworkResponceFace face, String url, int id) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                NetworkResponceFace face = (NetworkResponceFace) params[0];
                String url = (String) params[1];
                int id = (Integer) params[2];
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("id", id);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                String json = jsonObject.toString();
                String result = "";
                try {
                    HttpWorker hw = new HttpWorker();
                    Log.d(TAG, "checkRegist url:" + url);
                    result = hw.post(url, json);
                    Log.d(TAG, "checkRegist result:" + result);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } catch (NetworkUnaviableException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } finally {
                    face.callback(result);
                }
                return null;
            }
        }.execute(face, url, id);
    }

    /**
     * 咨询
     *
     * @param face
     * @param message 提交的问题
     */
    public void consultation(NetworkResponceFace face, String message) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                NetworkResponceFace face = (NetworkResponceFace) params[0];
                String message = (String) params[1];
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("message", message);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                String json = jsonObject.toString();
                String result = "";
                String url = "/api/consultation";
                try {
                    HttpWorker hw = new HttpWorker();
                    Log.d(TAG, "checkRegist url:" + url);
                    result = hw.post(url, json);
                    Log.d(TAG, "checkRegist result:" + result);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } catch (NetworkUnaviableException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } finally {
                    face.callback(result);
                }
                return null;
            }
        }.execute(face, message);
    }

    /////////////两票管理////////////

    /**
     * 工作票统计表-以后统计表
     *
     * @param face
     * @param uid
     */
    public void workTicketList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, WorkTicketBean.class);
    }

    /**
     * 工作票统计表-获取一条数据
     *
     * @param face
     * @param uid
     */
    public void workTicketBean(NetworkResponceFace face, int id) {
        dateBeanByID(face, "" + id, WorkTicketBean.class);
    }

    /**
     * 工作票统计表-新建统计表
     *
     * @param face
     * @param uid
     */
    public void workTicketAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, WorkTicketBean.class);
    }

    /**
     * 工作票统计表-修改统计表
     *
     * @param face
     * @param uid
     */
    public void workTicketUpdate(NetworkResponceFace face, WorkTicketBean bean) {
        dateBeanUpdate(face, bean, WorkTicketBean.class);
    }


    /**
     * 工作票统计表-删除统计表
     *
     * @param face
     * @param uid
     */
    public void workTicketDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, WorkTicketBean.class);
    }


    /**
     * 操作票统计表-已有统计表
     *
     * @param face
     * @param uid
     */
    public void optTicketList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, OptTicketBean.class);
    }

    /**
     * 操作票统计表-一条数据
     *
     * @param face
     * @param uid
     */
    public void optTicketBean(NetworkResponceFace face, int id) {
        dateBeanByID(face, "" + id, OptTicketBean.class);
    }

    /**
     * 操作票统计表-新建统计表
     *
     * @param face
     * @param uid
     */
    public void optTicketAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, OptTicketBean.class);
    }

    /**
     * 操作票统计表-修改统计表
     *
     * @param face
     * @param uid
     */
    public void optTicketUpdate(NetworkResponceFace face, OptTicketBean bean) {
        dateBeanUpdate(face, bean, OptTicketBean.class);
    }


    /**
     * 操作票统计表-删除统计表
     *
     * @param face
     * @param uid
     */
    public void optTicketDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, OptTicketBean.class);
    }


    ////////////////////////////////////////////////
    //客户管理

    /**
     * 用户服务信息反馈表-已有统计表
     *
     * @param face
     * @param uid
     */
    public void custInfoBackList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, CustInfoBackBean.class);
    }

    /**
     * 用户服务信息反馈表-一条数据
     *
     * @param face
     * @param uid
     */
    public void custInfoBackBean(NetworkResponceFace face, int id) {
        dateBeanByID(face, "" + id, CustInfoBackBean.class);
    }

    /**
     * 用户服务信息反馈表-新建统计表
     *
     * @param face
     * @param uid
     */
    public void custInfoBackAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, CustInfoBackBean.class);
    }

    /**
     * 用户服务信息反馈表-修改统计表
     *
     * @param face
     * @param uid
     */
    public void custInfoBackUpdate(NetworkResponceFace face, CustInfoBackBean bean) {
        dateBeanUpdate(face, bean, CustInfoBackBean.class);
    }


    /**
     * 用户服务信息反馈表-删除统计表
     *
     * @param face
     * @param uid
     */
    public void custInfoBackDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, CustInfoBackBean.class);
    }

    /**
     * 工作联系单-已有统计表
     *
     * @param face
     * @param uid
     */
    public void workContackList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, WorkContackBean.class);
    }


    /**
     * 工作联系单-一条数据
     *
     * @param face
     * @param uid
     */
    public void workContackBean(NetworkResponceFace face, int id) {
        dateBeanByID(face, "" + id, WorkContackBean.class);
    }

    /**
     * 工作联系单-新建统计表
     *
     * @param face
     * @param uid
     */
    public void workContackAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, WorkContackBean.class);
    }

    /**
     * 工作联系单-修改统计表
     *
     * @param face
     * @param uid
     */
    public void workContackUpdate(NetworkResponceFace face, WorkContackBean bean) {
        dateBeanUpdate(face, bean, WorkContackBean.class);
    }


    /**
     * 工作联系单-删除统计表
     *
     * @param face
     * @param uid
     */
    public void workContackDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, WorkContackBean.class);
    }


    ///---停（送）点联系单

    /**
     * 停（送）点联系单-已有统计表
     *
     * @param face
     * @param uid
     */
    public void stopOrSendContactList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, StopOrSendContactBean.class);
    }

    /**
     * 停（送）点联系单-一条数据
     *
     * @param face
     * @param uid
     */
    public void stopOrSendContactBean(NetworkResponceFace face, int id) {
        dateBeanByID(face, "" + id, StopOrSendContactBean.class);
    }

    /**
     * 停（送）点联系单-新建统计表
     *
     * @param face
     * @param uid
     */
    public void stopOrSendContactAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, StopOrSendContactBean.class);
    }

    /**
     * 停（送）点联系单-修改统计表
     *
     * @param face
     * @param uid
     */
    public void stopOrSendContactUpdate(NetworkResponceFace face, StopOrSendContactBean bean) {
        dateBeanUpdate(face, bean, StopOrSendContactBean.class);
    }


    /**
     * 停（送）点联系单-删除统计表
     *
     * @param face
     * @param uid
     */
    public void stopOrSendContactDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, StopOrSendContactBean.class);
    }

    /*****************************************************************
     *  电力检修
     */
    ///---电力检修记录卡

    /**
     * 电力检修记录卡-已有统计表
     *
     * @param face
     * @param uid
     */
    public void maintenanceRecordList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, MaintenanceRecordBean.class);
    }

    /**
     * 电力检修记录卡-一条数据
     *
     * @param face
     * @param uid
     */
    public void maintenanceRecordBean(NetworkResponceFace face, int project_id, String create_time, int create_user_id) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("project_id", project_id);
        map.put("create_time", create_time);
        map.put("create_user_id", create_user_id);

        getDescribeInfo(face, "/api/maintenanceRecordBean", map);
    }

    /**
     * 电力检修记录卡-新建统计表
     *
     * @param face
     * @param uid
     */
    public void maintenanceRecordAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, MaintenanceRecordBean.class);
    }

    /**
     * 电力检修记录卡-修改统计表
     *
     * @param face
     * @param uid
     */
    public void maintenanceRecordUpdate(NetworkResponceFace face, MaintenanceRecordBean bean) {
        dateBeanUpdate(face, bean, MaintenanceRecordBean.class);
    }


    /**
     * 电力检修记录卡-删除统计表
     *
     * @param face
     * @param uid
     */
    public void maintenanceRecordDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, MaintenanceRecordBean.class);
    }


    /*****************************************************************
     *  设备管理
     */
    ///---安全工具表格

    /**
     * 安全工具表格-已有统计表
     *
     * @param face
     * @param uid
     */
    public void safetyToolsList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, SafetyToolsBean.class);
    }

    /**
     * 安全工具表格-一条数据
     *
     * @param face
     * @param uid
     */
    public void safetyToolsBean(NetworkResponceFace face, int id) {
        dateBeanByID(face, "" + id, SafetyToolsBean.class);
    }

    /**
     * 安全工具表格-新建统计表
     *
     * @param face
     * @param uid
     */
    public void safetyToolsAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, SafetyToolsBean.class);
    }

    /**
     * 安全工具表格-修改统计表
     *
     * @param face
     * @param uid
     */
    public void safetyToolsUpdate(NetworkResponceFace face, List<DateBean> list) {
        dateBeanUpdate(face, list, SafetyToolsBean.class);
    }


    /**
     * 安全工具表格-删除统计表
     *
     * @param face
     * @param uid
     */
    public void safetyToolsDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, SafetyToolsBean.class);
    }

    ///---备品备件登记薄

    /**
     * 备品备件登记薄-已有统计表
     *
     * @param face
     * @param uid
     */
    public void sparePartsList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, SparePartsBean.class);
    }

    /**
     * 备品备件登记薄-已有统计表
     *
     * @param face
     * @param uid
     */
    public void sparePartsBean(NetworkResponceFace face, int id) {
        dateBeanByID(face, "" + id, SparePartsBean.class);
    }

    /**
     * 备品备件登记薄-新建统计表
     *
     * @param face
     * @param uid
     */
    public void sparePartsAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, SparePartsBean.class);
    }

    /**
     * 备品备件登记薄-修改统计表
     *
     * @param face
     * @param uid
     */
    public void sparePartsUpdate(NetworkResponceFace face, List<DateBean> list) {
        dateBeanUpdate(face, list, SparePartsBean.class);
    }


    /**
     * 备品备件登记薄格-删除统计表
     *
     * @param face
     * @param uid
     */
    public void sparePartsDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, SparePartsBean.class);
    }

    ///---设备台账登记薄

    /**
     * 设备台账登记薄-已有统计表
     *
     * @param face
     * @param uid
     */
    public void equipmentLedgerList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, EquipmentLedgerBean.class);
    }

    /**
     * 设备台账登记薄-一条数据
     *
     * @param face
     * @param uid
     */
    public void equipmentLedgerBean(NetworkResponceFace face, int id) {
        dateBeanByID(face, "" + id, EquipmentLedgerBean.class);
    }

    /**
     * 设备台账登记薄-新建统计表
     *
     * @param face
     * @param uid
     */
    public void equipmentLedgerAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, EquipmentLedgerBean.class);
    }

    /**
     * 设备台账登记薄-修改统计表
     *
     * @param face
     * @param uid
     */
    public void equipmentLedgerUpdate(NetworkResponceFace face, List<DateBean> list) {
        dateBeanUpdate(face, list, EquipmentLedgerBean.class);
    }


    /**
     * 设备台账登记薄-删除统计表
     *
     * @param face
     * @param uid
     */
    public void equipmentLedgerDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, EquipmentLedgerBean.class);
    }

    /*****************************************************************
     *  电力运行
     */
    ///---运行月报表

    /**
     * 运行月报表-已有统计表
     *
     * @param face
     * @param uid
     */
    public void runMonthlyReportList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, RunMonthlyReportBean.class);
    }

    /**
     * 运行月报表-一条数据
     *
     * @param face
     * @param uid
     */
    public void runMonthlyReportBean(NetworkResponceFace face, int id) {
        dateBeanByID(face, "" + id, RunMonthlyReportBean.class);
    }

    /**
     * 运行月报表-新建统计表
     *
     * @param face
     * @param uid
     */
    public void runMonthlyReportAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, RunMonthlyReportBean.class);
    }

    /**
     * 运行月报表-修改统计表
     *
     * @param face
     * @param uid
     */
    public void runMonthlyReportUpdate(NetworkResponceFace face, RunMonthlyReportBean bean) {
        dateBeanUpdate(face, bean, RunMonthlyReportBean.class);
    }


    /**
     * 运行月报表-删除统计表
     *
     * @param face
     * @param uid
     */
    public void runMonthlyReportDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, RunMonthlyReportBean.class);
    }

    ///---电气设备巡视卡

    /**
     * 电气设备巡视卡-已有统计表
     *
     * @param face
     * @param uid
     */
    public void inspectionCardList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, InspectionCardBean.class);
    }

    /**
     * 电气设备巡视卡-一条数据
     *
     * @param face
     * @param uid
     */
    public void inspectionCardBean(NetworkResponceFace face, int id) {
        dateBeanByID(face, "" + id, InspectionCardBean.class);
    }

    /**
     * 电气设备巡视卡-新建统计表
     *
     * @param face
     */
    public void inspectionCardAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, InspectionCardBean.class);
    }

    /**
     * 电气设备巡视卡-修改统计表
     *
     * @param face
     * @param uid
     */
    public void inspectionCardUpdate(NetworkResponceFace face, InspectionCardBean bean) {
        dateBeanUpdate(face, bean, InspectionCardBean.class);
    }


    /**
     * 电气设备巡视卡-删除统计表
     *
     * @param face
     * @param uid
     */
    public void inspectionCardDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, InspectionCardBean.class);
    }

    ///---红外线测温记录薄

    /**
     * 红外线测温记录薄-已有统计表
     *
     * @param face
     * @param uid
     */
    public void infraredTemperatureList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, InfraredTemperatureBean.class);
    }

    /**
     * 红外线测温记录薄-一条数据
     *
     * @param face
     * @param uid
     */
    public void infraredTemperatureBean(NetworkResponceFace face, int id) {
        dateBeanByID(face, "" + id, InfraredTemperatureBean.class);
    }

    /**
     * 红外线测温记录薄-新建统计表
     *
     * @param face
     * @param uid
     */
    public void infraredTemperatureAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, InfraredTemperatureBean.class);
    }

    /**
     * 红外线测温记录薄-修改统计表
     *
     * @param face
     * @param uid
     */
    public void infraredTemperatureUpdate(NetworkResponceFace face, InfraredTemperatureBean bean) {
        dateBeanUpdate(face, bean, InfraredTemperatureBean.class);
    }


    /**
     * 红外线测温记录薄-删除统计表
     *
     * @param face
     * @param uid
     */
    public void infraredTemperatureDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, InfraredTemperatureBean.class);
    }

    /*****************************************************************
     *  项目管理
     */
    ///---值班表

    /**
     * 值班表-已有统计表
     *
     * @param face
     * @param uid
     */
    public void watchAtchList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, WatchAtchBean.class);
    }

    /**
     * 值班表-一条数据
     *
     * @param face
     * @param uid
     */
    public void watchAtchBean(NetworkResponceFace face, int id) {
        dateBeanByID(face, "" + id, WatchAtchBean.class);
    }

    /**
     * 值班表-新建统计表
     *
     * @param face
     * @param uid
     */
    public void watchAtchAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, WatchAtchBean.class);
    }

    /**
     * 值班表-修改统计表
     *
     * @param face
     * @param uid
     */
    public void watchAtchUpdate(NetworkResponceFace face, WatchAtchBean bean) {
        dateBeanUpdate(face, bean, WatchAtchBean.class);
    }


    /**
     * 值班表-删除统计表
     *
     * @param face
     * @param uid
     */
    public void watchAtchDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, WatchAtchBean.class);
    }

    ///---工作联系单

    /**
     * 工作联系单-已有统计表
     *
     * @param face
     * @param uid
     */
    public void jobContactSheetList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, JobContactSheetBean.class);
    }

    /**
     * 工作联系单-一条数据
     *
     * @param face
     * @param uid
     */
    public void jobContactSheetBean(NetworkResponceFace face, int id) {
        dateBeanByID(face, "" + id, JobContactSheetBean.class);
    }

    /**
     * 工作联系单-新建统计表
     *
     * @param face
     * @param uid
     */
    public void jobContactSheetAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, JobContactSheetBean.class);
    }

    /**
     * 工作联系单-修改统计表
     *
     * @param face
     * @param uid
     */
    public void jobContactSheetUpdate(NetworkResponceFace face, JobContactSheetBean bean) {
        dateBeanUpdate(face, bean, JobContactSheetBean.class);
    }


    /**
     * 工作联系单-删除统计表
     *
     * @param face
     * @param uid
     */
    public void jobContactSheetDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, JobContactSheetBean.class);
    }
    /***********************************************************
     *  安全文明
     */
    ///---安全检查巡视卡

    /**
     * 安全检查巡视卡-已有统计表
     *
     * @param face
     * @param uid
     */
    public void safetyTrainingList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, SafetyTrainingBean.class);
    }

    /**
     * 安全检查巡视卡-一条数据
     *
     * @param face
     * @param uid
     */
    public void safetyTrainingBean(NetworkResponceFace face, int project_id, String create_time, int create_user_id) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("project_id", project_id);
        map.put("create_time", create_time);
        map.put("create_user_id", create_user_id);

        getDescribeInfo(face, "/api/safetyTrainingBean", map);

    }

    /**
     * 安全检查巡视卡-新建统计表
     *
     * @param face
     * @param uid
     */
    public void safetyTrainingAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, SafetyTrainingBean.class);
    }

    /**
     * 安全检查巡视卡-修改统计表
     *
     * @param face
     * @param uid
     */
    public void safetyTrainingUpdate(NetworkResponceFace face, List<DateBean> list) {
        dateBeanUpdate(face, list, SafetyTrainingBean.class);
    }


    /**
     * 安全检查巡视卡-删除统计表
     *
     * @param face
     * @param uid
     */
    public void safetyTrainingDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, SafetyTrainingBean.class);
    }

    ///---环境检查巡视卡

    /**
     * 环境检查巡视卡-已有统计表
     *
     * @param face
     * @param uid
     */
    public void environmentalInspectionList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, EnvironmentalInspectionBean.class);
    }

    /**
     * 环境检查巡视卡-一条数据
     *
     * @param face
     * @param uid
     */
    public void environmentalInspectionBean(NetworkResponceFace face, int project_id, String create_time, int create_user_id) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("project_id", project_id);
        map.put("create_time", create_time);
        map.put("create_user_id", create_user_id);

        getDescribeInfo(face, "/api/environmentalInspectionBean", map);

    }


    /**
     * 环境检查巡视卡-新建统计表
     *
     * @param face
     * @param uid
     */
    public void environmentalInspectionAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, EnvironmentalInspectionBean.class);
    }

    /**
     * 环境检查巡视卡-修改统计表
     *
     * @param face
     * @param uid
     */
    public void environmentalInspectionUpdate(NetworkResponceFace face, List<DateBean> list) {
        dateBeanUpdate(face, list, EnvironmentalInspectionBean.class);
    }


    /**
     * 环境检查巡视卡-删除统计表
     *
     * @param face
     * @param uid
     */
    public void environmentalInspectionDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, EnvironmentalInspectionBean.class);
    }

    /*********************************************************************
     * GPS 数据保存
     */
    /**
     * GPS-新建统计表
     *
     * @param face
     * @param uid
     */
    public void gpsAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, GPSBean.class);
    }


    /*********************************************************************
     * 工作计划 数据保存
     */


    /**
     * 工作计划 -已有统计表
     *
     * @param face
     * @param uid
     */
    public void jobPlanList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, JobPlanBean.class);
    }

    /**
     * 工作计划 -一条数据
     *
     * @param face
     * @param uid
     */
    public void jobPlanBean(NetworkResponceFace face, int id) {
        dateBeanByID(face, "" + id, JobPlanBean.class);
    }

    /**
     * 工作计划 -新建统计表
     *
     * @param face
     * @param uid
     */
    public void jobPlanAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, JobPlanBean.class);
    }

    /**
     * 工作计划 -修改统计表
     *
     * @param face
     * @param uid
     */
    public void jobPlanUpdate(NetworkResponceFace face, JobPlanBean bean) {
        dateBeanUpdate(face, bean, JobPlanBean.class);
    }


    /**
     * 工作计划 -删除统计表
     *
     * @param face
     * @param uid
     */
    public void jobPlanDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, JobPlanBean.class);
    }


    /**
     * 工作日志 -已有统计表
     *
     * @param face
     * @param uid
     */
    public void jobLogList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, JobLogBean.class);
    }

    /**
     * 工作日志 -一条数据
     *
     * @param face
     * @param uid
     */
    public void jobLogBean(NetworkResponceFace face, int id) {
        dateBeanByID(face, "" + id, JobLogBean.class);
    }

    /**
     * 工作日志 -新建统计表
     *
     * @param face
     * @param uid
     */
    public void jobLogAdd(NetworkResponceFace face, List<DateBean> list) {
        dateBeanAdd(face, list, JobLogBean.class);
    }

    /**
     * 工作日志 -修改统计表
     *
     * @param face
     * @param uid
     */
    public void jobLogUpdate(NetworkResponceFace face, JobLogBean bean) {
        dateBeanUpdate(face, bean, JobLogBean.class);
    }


    /**
     * 工作日志 -删除统计表
     *
     * @param face
     * @param uid
     */
    public void jobLogDelete(NetworkResponceFace face, String id) {
        dateBeanDelete(face, id, JobLogBean.class);
    }

    /**
     * 获取常量数据
     *
     * @param face
     * @param type 详细见常量表
     */
    public void getConstants(NetworkResponceFace face, int type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);

        getDescribeInfo(face, "/api/getConstants", map);
    }


    /**
     * 检修任务  列表数据
     *
     * @param face
     * @param uid  登录用户ID
     */
    public void maintenanceTaskCardList(NetworkResponceFace face, String uid) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", uid);

        getDescribeInfo(face, "/api/maintenanceTaskCardList", map);
    }

    /**
     * 检修任务  列表数据
     *
     * @param face
     * @param id   业务id
     * @param type 1:电气设备巡视卡   2：红外线测温记录薄
     */
    public void maintenanceTaskCardBean(NetworkResponceFace face, String id, String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("type", type);

        getDescribeInfo(face, "/api/maintenanceTaskCardBean", map);
    }

    /**
     * 检修任务   检修处理
     *
     * @param face
     * @param id           电气设备巡视卡 id
     * @param dealResult   检修处理结果
     * @param type         1:电气设备检修巡视卡  2：红外线测温记录薄
     * @param check_result //提交的查看内容项列表数据
     *                     "check_result" :[
     *                     {
     *                     "deviceId":1, //设备列表对应的数据ID
     *                     "select_val": 0            //对应的检修状态  0：异常 1：正常
     *                     },
     *                     {
     *                     "deviceId":2,
     *                     "select_val": 0
     *                     }
     *                     ]
     */
    public void maintenanceTaskCardResult(NetworkResponceFace face, String id, String dealResult, String type, String check_result) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("type", type);
        map.put("deal_result", dealResult);
        map.put("check_result", check_result);

        getDescribeInfo(face, "/api/maintenanceTaskCardResult", map);
    }

    /**
     * 检修任务 - 设备内容检查结果数据
     *
     * @param face
     * @param id   任务id，   d_id数据
     * @param type 提交类型  1：设备检修巡视卡  2：红外测温检修卡
     */
    public void maintenanceTaskDeviceConetent(NetworkResponceFace face, String id, String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("type", type);

        getDescribeInfo(face, "/api/maintenanceTaskDeviceConetent", map);
    }


    /*********************************************************************88
     * 我 -
     */
    /**
     * 项目任务列表数据
     *
     * @param face
     * @param uid  返回值中
     *             1：电气设备巡视卡
     *             2：红外线测温记录薄
     *             3：安全检查巡视卡
     *             4：环境检查巡视卡
     */
    public void myProjectList(NetworkResponceFace face, int uid) {
        dateBeanList(face, "" + uid, MyProjectBean.class);
    }

    /**
     * 获取该项目任务对应的设备信息
     *
     * @param face
     * @param project_id     项目ID
     * @param create_time    任务创建时间
     * @param create_user_id 任务创建人
     * @param type           类型  1:巡视检查  2：红外检查
     */
    public void myProjectBean(NetworkResponceFace face, int project_id, String create_time, String create_user_id, String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("project_id", project_id);
        map.put("create_time", create_time);
        map.put("create_user_id", create_user_id);
        map.put("type", type);

        getDescribeInfo(face, "/api/myProjectBean", map);
    }

    /**
     * h获取项目任务  - 选择设备 - 设备内容列表数据
     *
     * @param face
     * @param project_id     项目id
     * @param create_time    任务创建时间
     * @param create_user_id 任务创建人
     * @param type           类型  1:巡视检查  2：红外检查
     * @param device_id      选择设备ID
     */
    public void myProjectDeviceContents(NetworkResponceFace face, int project_id, String create_time, String create_user_id, String type, String device_id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("project_id", project_id);
        map.put("create_time", create_time);
        map.put("create_user_id", create_user_id);
        map.put("type", type);
        map.put("device_id", device_id);

        getDescribeInfo(face, "/api/myProjectDeviceContents", map);
    }

    /**
     * 获取用户个人信息
     *
     * @param face
     * @param id   用户id
     */
    public void getUserInfo(NetworkResponceFace face, String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);

        getDescribeInfo(face, "/api/getUserInfo", map);
    }

    /**
     * 获取栏目权限列表数据
     *
     * @param face
     * @param uid        登录用户id用户选择的项目组
     * @param project_id 当前登录
     */
    public void authorityColumnList(NetworkResponceFace face, int uid, int project_id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", uid);
        map.put("project_id", project_id);

        getDescribeInfo(face, "/api/authorityColumnList", map);
    }

    /**
     * 获取栏目表单权限列表数据
     *
     * @param face
     * @param uid        登录用户id用户选择的项目组
     * @param project_id 当前登录
     */
    public void authorityList(NetworkResponceFace face, int uid, int project_id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", uid);
        map.put("project_id", project_id);

        getDescribeInfo(face, "/api/authorityFormList", map);
    }


    /**
     * 获取apk版本信息
     *
     * @param face
     */
    public void apkVersion(NetworkResponceFace face) {
        Map<String, Object> map = new HashMap<String, Object>();

        getDescribeInfo(face, "/api/apkVersion", map);
    }


    /******************************************************************
     * 下拉列表数据
     */
    /**
     * 项目
     *
     * @param face
     * @param uid  登录用户id
     */
    public void projectList(NetworkResponceFace face, int uid) {
        getDescribeInfoByID(face, "/api/projectList", uid);
    }

    /**
     * 运行单位
     *
     * @param face
     */
    public void executeCompanyList(NetworkResponceFace face) {
        getDescribeInfo(face, "/api/executeCompanyList");
    }

    /**
     * 用户
     *
     * @param face
     * @param uid  登录用户ID
     */
    public void userList(NetworkResponceFace face, int uid) {
        getDescribeInfoByID(face, "/api/userList", uid);
    }

    /**
     * 设备
     *
     * @param face
     * @param project_id 选择项目ID
     */
    public void deviceList(NetworkResponceFace face, int project_id) {
        getDescribeInfoByID(face, "/api/deviceList", project_id);
    }

    /**
     * 设备内容列表
     *
     * @param face
     * @param device_id 选择设备ID
     */
    public void deviceContentList(NetworkResponceFace face, int project_id, int device_id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("project_id", project_id);
        map.put("device_id", device_id);

        getDescribeInfo(face, "/api/deviceContentList", map);
    }


    /**
     * 团队
     *
     * @param face
     */
    public void teamList(NetworkResponceFace face) {
        getDescribeInfo(face, "/api/teamList");
    }

    /**
     * 工作票名称
     *
     * @param face
     */
    public void workTicketNameList(NetworkResponceFace face) {
        getDescribeInfo(face, "/api/workTicketNameList");
    }


    /**
     * 下载服务器文件
     * @param fileName 需要下载的文件名称 eg：dlyx.doc
     * @param saveFilePath 下载文件保存的目录地址 eg：/data/local/tmp/
     */
    /*public void downLoadFile(NetworkResponceFace face ,String fileName , String saveFilePath){

		if (!downLoadFilelock) {
			downLoadFilelock = true;
			new AsyncTask<Object, Object, Object>() {
				@Override
				protected Object doInBackground(Object... params) {
					NetworkResponceFace face = (NetworkResponceFace) params[0];
					String fileName = (String) params[1];
					int fileNameIndex = Common.getFileNameIndex(fileName);
					fileName = ""+fileNameIndex ;
					String saveFilePath = (String) params[2];
					
					try {
						if(!saveFilePath.endsWith("/") && !saveFilePath.endsWith("\\"))
			             	saveFilePath = saveFilePath + File.separator ;
			        	saveFilePath = saveFilePath + fileName + ".doc" ;
			        	fileName = URLEncoder.encode(fileName, "utf-8");
			        	//http://192.168.1.5:8080/electricproperty/static/doc/1.doc、
			             
			             * 通过URL取得HttpURLConnection 
			             * 要网络连接成功，需在AndroidMainfest.xml中进行权限配置 
			             * <uses-permission android:name="android.permission.INTERNET" /> 
			               
			        	String uri = Constents.REQUEST_URL + File.separator + "static" + File.separator + "doc" + File.separator + fileNameIndex +".doc" ;
			            URL url=new URL(uri);  
			            HttpURLConnection conn=(HttpURLConnection)url.openConnection();  
			            //取得inputStream，并进行读取  
			            InputStream input=conn.getInputStream();  
			            BufferedReader in=new BufferedReader(new InputStreamReader(input));  
			            String line=null;  
			            StringBuffer sb=new StringBuffer();  
			            while((line=in.readLine())!=null){  
			                sb.append(line);  
			            }
			           
			            RandomAccessFile r = new RandomAccessFile(saveFilePath,"rw");
			            r.seek(0);
			            r.write(sb.toString().getBytes());
			            r.close();
			            
					} catch (Exception e) {
						Log.d(TAG ,"downLoadFile error:" + e.getMessage());
					} finally{
						downLoadFilelock = false;
						face.callback(saveFilePath);
					}
					return null;
				}
			}.execute(face,fileName ,saveFilePath);
		}
    }  */


    //////////////////////////////////////

    /**
     * 数据对象bean操作-列表
     *
     * @param face
     * @param uid
     */
    public void dateBeanList(NetworkResponceFace face, String uid, Class cls) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                NetworkResponceFace face = (NetworkResponceFace) params[0];
                String uid = (String) params[1];
                Class cls = (Class) params[2];
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("uid", uid);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                String json = jsonObject.toString();

                String result = "";
                String url = "/api/" + getDateBeanType(cls, LIST);
                try {
                    HttpWorker hw = new HttpWorker();
                    Log.d(TAG, "checkRegist url:" + url);
                    result = hw.post(url, json);
                    Log.d(TAG, "checkRegist result:" + result);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } catch (NetworkUnaviableException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } finally {
                    face.callback(result);
                }
                return null;
            }
        }.execute(face, uid, cls);
    }


    /**
     * 数据对象bean操作-一个数据
     *
     * @param face
     * @param uid
     */
    public void dateBeanByID(NetworkResponceFace face, String id, Class cls) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                NetworkResponceFace face = (NetworkResponceFace) params[0];
                String id = (String) params[1];
                Class cls = (Class) params[2];
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("id", id);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                String json = jsonObject.toString();

                String result = "";
                String url = "/api/" + getDateBeanType(cls, LIST);
                try {
                    HttpWorker hw = new HttpWorker();
                    Log.d(TAG, "checkRegist url:" + url);
                    result = hw.post(url, json);
                    Log.d(TAG, "checkRegist result:" + result);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } catch (NetworkUnaviableException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } finally {
                    face.callback(result);
                }
                return null;
            }
        }.execute(face, id, cls);
    }

    /**
     * 数据对象bean操作-新增
     *
     * @param face
     * @param uid
     */
    public void dateBeanAdd(NetworkResponceFace face, List list, Class cls) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                NetworkResponceFace face = (NetworkResponceFace) params[0];
                List<DateBean> list = (List<DateBean>) params[1];
                Class cls = (Class) params[2];
                Gson gson = new Gson();
                String json = gson.toJson(list);

                String result = "";
                String url = "/api/" + getDateBeanType(cls, ADD);
                try {
                    HttpWorker hw = new HttpWorker();
                    Log.d(TAG, "checkRegist url:" + url);
                    result = hw.post(url, json);
                    Log.d(TAG, "checkRegist result:" + result);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } catch (NetworkUnaviableException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } finally {
                    face.callback(result);
                }
                return null;
            }
        }.execute(face, list, cls);
    }

    /**
     * 数据对象bean操作-修改
     *
     * @param face
     * @param uid
     */
    public void dateBeanUpdate(NetworkResponceFace face, List<DateBean> list, Class cls) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                NetworkResponceFace face = (NetworkResponceFace) params[0];
                List<DateBean> list = (List<DateBean>) params[1];
                Class cls = (Class) params[2];
                Gson gson = new Gson();
                String json = gson.toJson(list);

                String result = "";
                String url = "/api/" + getDateBeanType(cls, UPDATE);
                try {
                    HttpWorker hw = new HttpWorker();
                    Log.d(TAG, "checkRegist url:" + url);
                    result = hw.post(url, json);
                    Log.d(TAG, "checkRegist result:" + result);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } catch (NetworkUnaviableException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } finally {
                    face.callback(result);
                }
                return null;
            }
        }.execute(face, list, cls);
    }


    /**
     * 数据对象bean操作-修改
     *
     * @param face
     * @param uid
     */
    public void dateBeanUpdate(NetworkResponceFace face, DateBean bean, Class cls) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                NetworkResponceFace face = (NetworkResponceFace) params[0];
                DateBean bean = (DateBean) params[1];
                Class cls = (Class) params[2];
                Gson gson = new Gson();
                String json = gson.toJson(bean);

                String result = "";
                String url = "/api/" + getDateBeanType(cls, UPDATE);
                try {
                    HttpWorker hw = new HttpWorker();
                    Log.d(TAG, "checkRegist url:" + url);
                    result = hw.post(url, json);
                    Log.d(TAG, "checkRegist result:" + result);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } catch (NetworkUnaviableException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } finally {
                    face.callback(result);
                }
                return null;
            }
        }.execute(face, bean, cls);
    }

    /**
     * 数据对象bean操作-删除
     *
     * @param face
     * @param uid
     */
    public void dateBeanDelete(NetworkResponceFace face, String id, Class cls) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                NetworkResponceFace face = (NetworkResponceFace) params[0];
                String id = (String) params[1];
                Class cls = (Class) params[2];
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("id", id);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                String json = jsonObject.toString();

                String result = "";
                String url = "/api/" + getDateBeanType(cls, DELETE);
                try {
                    HttpWorker hw = new HttpWorker();
                    Log.d(TAG, "checkRegist url:" + url);
                    result = hw.post(url, json);
                    Log.d(TAG, "checkRegist result:" + result);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } catch (NetworkUnaviableException e) {
                    Log.d(TAG, "checkRegist error:" + e.getMessage());
                } finally {
                    face.callback(result);
                }
                return null;
            }
        }.execute(face, id, cls);
    }

    /**
     * 获取操作bean的类型
     *
     * @param bean
     * @return
     */
    private String getDateBeanType(Class cls, String controlType) {
        String type = "";
        if (cls.equals(CustInfoBackBean.class)) {
            //客户管理-用户信息反馈表
            type = "custInfoBack" + controlType;

        } else if (cls.equals(OptTicketBean.class)) {
            //两票管理 -操作票统计表
            type = "optTicket" + controlType;
        } else if (cls.equals(StopOrSendContactBean.class)) {
            //客户管理-停（送）点联系单
            type = "stopOrSendContact" + controlType;
        } else if (cls.equals(UserBean.class)) {
            //注册用户bean
            type = "regist";
        } else if (cls.equals(WorkContackBean.class)) {
            //客户管理-工作联系单bean
            type = "workContack" + controlType;
        } else if (cls.equals(WorkTicketBean.class)) {
            //两票管理 - 工作票统计表
            type = "workTicket" + controlType;
        } else if (cls.equals(MaintenanceRecordBean.class)) {
            //电力巡视-电力巡视记录卡
            type = "maintenanceRecord" + controlType;
        } else if (cls.equals(EquipmentLedgerBean.class)) {
            //设备管理 - 设备台账登记薄 b
            type = "equipmentLedger" + controlType;
        } else if (cls.equals(JobContactSheetBean.class)) {
            //项目管理 - 工作联系单
            type = "jobContactSheet" + controlType;
        } else if (cls.equals(RunMonthlyReportBean.class)) {
            //电力运行 - 运行月报表
            type = "runMonthlyReport" + controlType;
        } else if (cls.equals(SafetyToolsBean.class)) {
            //设备管理 - 安全工具表格 bean
            type = "safetyTools" + controlType;
        } else if (cls.equals(SparePartsBean.class)) {
            //设备管理 - 备品备件登记薄 bean
            type = "spareParts" + controlType;
        } else if (cls.equals(WatchAtchBean.class)) {
            //项目管理 - 值班表
            type = "watchAtch" + controlType;
        } else if (cls.equals(SafetyTrainingBean.class)) {
            //安全文明 - 安全检查巡视卡
            type = "safetyTraining" + controlType;
        } else if (cls.equals(EnvironmentalInspectionBean.class)) {
            //安全文明 - 环境检查巡视卡
            type = "environmentalInspection" + controlType;
        } else if (cls.equals(InspectionCardBean.class)) {
            //电力运行 - 电气设备巡视卡
            type = "inspectionCard" + controlType;
        } else if (cls.equals(InfraredTemperatureBean.class)) {
            //电力运行 - 红外线测温记录薄
            type = "infraredTemperature" + controlType;
        } else if (cls.equals(GPSBean.class)) {
            //gps
            type = "gps" + controlType;
        } else if (cls.equals(JobPlanBean.class)) {
            //工作计划
            type = "jobPlan" + controlType;
        } else if (cls.equals(JobLogBean.class)) {
            //工作日志
            type = "jobLog" + controlType;
        } else if (cls.equals(MyProjectBean.class)) {
            //我-项目管理
            type = "myProject" + controlType;
        } else if (cls.equals(AuthorityBean.class)) {
            //我-项目管理
            type = "authority" + controlType;
        }

        return type;
    }

}
