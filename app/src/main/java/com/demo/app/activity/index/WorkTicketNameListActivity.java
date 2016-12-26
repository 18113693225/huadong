package com.demo.app.activity.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;

//工作单名单
public class WorkTicketNameListActivity extends BaseActivity {
    private ListView workTicketNameListView;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> data;
    private int uid;
    private int type = -1;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            adapter.notifyDataSetChanged();
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_ticketname_list_layout);
        SharedPreferences sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
        uid = sp.getInt("userId", -1);
        final int editTextId = getIntent().getIntExtra("editTextId", -1);
        String editTextTag = getIntent().getStringExtra("editTextTag");
        String title = "项目名称";

        switch (editTextId) {
            // 项目名称
            case R.id.work_new_pName:
            case R.id.oper_new_pname:
            case R.id.pf_dqjx_pname:
            case R.id.pr_new_pname:
            case R.id.pr_rdeline_pname:
            case R.id.pr_month_pname:
            case R.id.sc_check_pname:
            case R.id.sc_hj_pname:
            case R.id.pm_duty_pname:
            case R.id.pm_workconn_pname:
            case R.id.cm_workconn_pname:
            case R.id.cm_stoppower_pname:
            case R.id.cm_userinfo_pname:
            case R.id.dm_aqtool_pname:
            case R.id.dm_bpbj_pname:
            case R.id.dm_tz_pname:
                title = "项目名称";
                type = 1;
                break;
            // 运行单位
            case R.id.work_new_runit:
            case R.id.oper_new_runit:
            case R.id.pf_dqjx_runit:
            case R.id.pr_new_runit:
            case R.id.pr_rdeline_runit:
            case R.id.pr_month_runit:
            case R.id.sc_check_runit:
            case R.id.sc_hj_runit:
                title = "运行单位";
                type = 2;
                break;
            // 记录人
            case R.id.work_new_rPerson:
            case R.id.oper_new_rperson:
            case R.id.pr_rdeline_recordperson:
            case R.id.pr_month_recordperson:
            case R.id.pm_duty_recordperson:
                title = "记录人";
                type = 3;
                break;
            // 工作票名称
            case R.id.work_new_workName:
                title = "工作票名称";
                type = 4;
                break;
            // 工作签发人
            case R.id.work_new_workQfr:
                title = "工作签发人";
                type = 5;
                break;
            // 操作人
            case R.id.oper_new_operperson:
                title = "操作人";
                type = 6;
                break;
            // 检查人员
            case R.id.pf_dqjx_jxperson:
                title = "检修人员";
                type = 7;
                break;
            // 工作负责人
            case R.id.pf_dqjx_workfzr:
            case R.id.work_new_workFzr:
                title = "负责人";
                type = 8;
                break;
            // 项目类别
            case R.id.pr_new_ptype:
                title = "项目类别";
                type = 9;
                break;
            // 设备名称
            case R.id.pr_new_dname:
                title = "设备名称";
                type = 10;
                break;
            // 巡视人
            case R.id.pr_new_xsr:
            case R.id.sc_check_xsr:
            case R.id.sc_hj_xsr:
                title = "巡视人";
                type = 11;
                break;
            case R.id.pr_new_dnum:
                title = "查看巡视内容";
                type = 12;
                break;
            //值班员
            case R.id.pm_duty_zhuduty:
            case R.id.pm_duty_fuduty:
            case R.id.pm_duty_dutyfz:
            case R.id.pm_duty_shixiperson:
                title = "值班员";
                type = 3;
                break;
            //通知人员
            case R.id.pm_workconn_noticeperson:
            case R.id.cm_workconn_noticeperson:
                title = "通知人员";
                type = 3;
                break;
            default:
                break;
        }

        //检修类别
        if ("pf_dqjx_jxtype".equals(editTextTag)) {
            title = "检修类别";
            type = 15;
        } else if ("pf_dqjx_workfzr".equals(editTextTag)) {//工作负责人
            title = "负责人";
            type = 8;
        } else if ("pr_rdeline_dname".equals(editTextTag) || "pr_new_dname".equals(editTextTag)
                || "dm_aqtool_dname".equals(editTextTag) || "dm_bpbj_dname".equals(editTextTag)) {
            title = "设备名称";
            type = 10;
        } else if ("dm_aqtool_tooldesc".equals(editTextTag)) {//工器具名称
            title = "工器具名称";
            type = 13;
        } else if ("dm_aqtool_dylevel".equals(editTextTag)) {//电压等级
            title = "电压等级";
            type = 14;
        }
        TitleCommon.setTitle(this, null, title, null, true);

        workTicketNameListView = (ListView) this.findViewById(R.id.workTicketNameListView);
        adapter = new SimpleAdapter(this, getData(type), R.layout.oper_ticket_layout_item, new String[]{"text"},
                new int[]{R.id.oper_text});
        workTicketNameListView.setAdapter(adapter);
        workTicketNameListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                Intent i = new Intent();
                i.putExtra("id", Integer.parseInt(data.get(position).get("id").toString()));
                Constents.xmname = Integer.parseInt(data.get(position).get("id").toString());
                Constents.lbname = Integer.parseInt(data.get(position).get("id").toString());
                if (type == 1) {
                    //选择了项目名称之后保存项目ID备用
                    if (Constents.xmname != Integer.parseInt(data.get(position).get("id").toString())) {
                        Constents.xscontentList.clear();
                        Constents.xserrorcontentList.clear();
                        Constents.current_project_id = Integer.parseInt(data.get(position).get("id").toString());
                        i.putExtra("ptype", data.get(position).get("ptype").toString());
                        i.putExtra("cname", data.get(position).get("cname").toString());
                        getDeviceNum();
                    } else {
                        Constents.current_project_id = Integer.parseInt(data.get(position).get("id").toString());
                        i.putExtra("ptype", data.get(position).get("ptype").toString());
                        i.putExtra("cname", data.get(position).get("cname").toString());
                        getDeviceNum();
                    }

                }
                if (type == 10) {
                    //选择了设备名称之后保存设备ID备用
                    if (Constents.lbname == Integer.parseInt(data.get(position).get("id").toString())) {
                        Constents.xscontentList.clear();
                        Constents.xserrorcontentList.clear();
                        Constents.current_device_id = Integer.parseInt(data.get(position).get("id").toString());
                        //选择了设备后调用巡视内容
                        getXSContent();
                    } else {
                        Constents.current_device_id = Integer.parseInt(data.get(position).get("id").toString());
                        //选择了设备后调用巡视内容
                        getXSContent();
                    }

                }
                i.putExtra("editTextId", editTextId);
                i.putExtra("text", data.get(position).get("text").toString());
                WorkTicketNameListActivity.this.setResult(RESULT_OK, i);
                WorkTicketNameListActivity.this.finish();
            }
        });
    }

    public List<Map<String, Object>> getData(int type) {
        data = new ArrayList<Map<String, Object>>();
        switch (type) {
            case 1:
                NetworkData.getInstance().projectList(new NetworkResponceFace() {

                    @Override
                    public void callback(String result) {
                        // TODO Auto-generated method stub
                        JSONObject json;
                        try {
                            json = new JSONObject(result);
                            JSONArray rel = new JSONArray(json.get("result").toString());
                            for (int i = 0; i < rel.length(); i++) {
                                JSONObject obj = (JSONObject) rel.get(i);
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("text", obj.get("e_project_name").toString());
                                map.put("id", obj.get("id").toString());
                                map.put("ptype", obj.get("e_project_item_name").toString());
                                map.put("cname", obj.get("e_project_done_depart_name").toString());
                                data.add(map);
                            }
                            handler.obtainMessage().sendToTarget();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, uid);
                break;
            case 2:
                NetworkData.getInstance().executeCompanyList(new NetworkResponceFace() {

                    @Override
                    public void callback(String result) {
                        // TODO Auto-generated method stub
                        JSONObject json;
                        try {
                            json = new JSONObject(result);
                            JSONArray rel = new JSONArray(json.get("result").toString());
                            for (int i = 0; i < rel.length(); i++) {
                                JSONObject obj = (JSONObject) rel.get(i);
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("text", obj.get("execute_company_name").toString());
                                map.put("id", obj.get("id").toString());
//							if(!data.contains(map)){
                                data.add(map);
//							}
                            }
                            handler.obtainMessage().sendToTarget();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
                NetworkData.getInstance().userList(new NetworkResponceFace() {

                    @Override
                    public void callback(String result) {
                        // TODO Auto-generated method stub
                        JSONObject json;
                        try {
                            json = new JSONObject(result);
                            JSONArray rel = new JSONArray(json.get("result").toString());
                            for (int i = 0; i < rel.length(); i++) {
                                JSONObject obj = (JSONObject) rel.get(i);
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("text", obj.get("name").toString());
                                map.put("id", obj.get("id").toString());
                                data.add(map);
                            }
                            handler.obtainMessage().sendToTarget();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, uid);
                break;
            case 4:
                NetworkData.getInstance().workTicketNameList(new NetworkResponceFace() {

                    @Override
                    public void callback(String result) {
                        // TODO Auto-generated method stub
                        JSONObject json;
                        try {
                            json = new JSONObject(result);
                            JSONArray rel = new JSONArray(json.get("result").toString());
                            for (int i = 0; i < rel.length(); i++) {
                                JSONObject obj = (JSONObject) rel.get(i);
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("text", obj.get("work_ticket_name").toString());
                                map.put("id", obj.get("id").toString());
                                data.add(map);
                            }
                            handler.obtainMessage().sendToTarget();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 9:
                break;
            case 10:
                NetworkData.getInstance().deviceList(new NetworkResponceFace() {

                    @Override
                    public void callback(String result) {
                        // TODO Auto-generated method stub
                        JSONObject json;
                        try {
                            json = new JSONObject(result);
                            JSONArray rel = new JSONArray(json.get("result").toString());
                            for (int i = 0; i < rel.length(); i++) {
                                JSONObject obj = (JSONObject) rel.get(i);
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("text", obj.get("device_name").toString());
                                map.put("id", obj.get("device_id").toString());
                                data.add(map);
                            }
                            handler.obtainMessage().sendToTarget();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, Constents.current_project_id);
                break;
        /*case 12:
            NetworkData.getInstance().deviceContentList(new NetworkResponceFace() {
				
				@Override
				public void callback(String result) {
					// TODO Auto-generated method stub
					JSONObject json;
					try {
						json = new JSONObject(result);
						JSONArray rel = new JSONArray(json.get("result").toString());
						for (int i = 0; i < rel.length(); i++) {
							JSONObject obj = (JSONObject) rel.get(i);
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("text", obj.get("device_content_name").toString());
							map.put("id", obj.get("device_content_id").toString());
							data.add(map);
						}
//						handler.obtainMessage().sendToTarget();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			},Constents.current_project_id,Constents.current_device_id);
			break;*/
            case 13:
            /*String[] desc = { "验电器", "绝缘手套", "绝缘靴", "绝缘梯", "接地线夹", "安全帽",
                    "安全围栏", "红布帘", "红马甲", "登高板", "保险带", "防毒面具", "万用表" };
			for (int i = 0; i < desc.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("text", desc[i]);
				map.put("id", i+1);
				data.add(map);
			}*/
                NetworkData.getInstance().getConstants(new NetworkResponceFace() {

                    @Override
                    public void callback(String result) {
                        // TODO Auto-generated method stub
                        JSONObject json;
                        try {
                            json = new JSONObject(result);
                            JSONArray rel = new JSONArray(json.get("result").toString());
                            for (int i = 0; i < rel.length(); i++) {
                                JSONObject obj = (JSONObject) rel.get(i);
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("text", obj.get("value").toString());
                                map.put("id", obj.get("id").toString());
                                data.add(map);
                            }
                            handler.obtainMessage().sendToTarget();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, Constents.TYPE_TOOL);
                break;
            case 14:
			/*String[] level = { "0.5kV", "10kV", "10kV", "66kV", "110kV", "220kV",
					"330kV", "500kV"};
			for (int i = 0; i < level.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("text", level[i]);
				map.put("id", i+1);
				data.add(map);
			}*/
                NetworkData.getInstance().getConstants(new NetworkResponceFace() {

                    @Override
                    public void callback(String result) {
                        // TODO Auto-generated method stub
                        JSONObject json;
                        try {
                            json = new JSONObject(result);
                            JSONArray rel = new JSONArray(json.get("result").toString());
                            for (int i = 0; i < rel.length(); i++) {
                                JSONObject obj = (JSONObject) rel.get(i);
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("text", obj.get("value").toString());
                                map.put("id", obj.get("id").toString());
                                data.add(map);
                            }
                            handler.obtainMessage().sendToTarget();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, Constents.TYPE_VOLTAGE_LEVEL);
                break;
            case 15:
                String[] level = {"计划检修", "应急检修"};
                for (int i = 0; i < level.length; i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("text", level[i]);
                    map.put("id", i + 1);
                    data.add(map);
                }
                break;
            default:
                break;
        }

        return data;
    }

    public void getXSContent() {
        final List<Map<Object, Object>> content = new ArrayList<Map<Object, Object>>();
        NetworkData.getInstance().deviceContentList(new NetworkResponceFace() {

            @Override
            public void callback(String result) {
                // TODO Auto-generated method stub
                JSONObject json;
                try {
                    json = new JSONObject(result);
                    JSONArray rel = new JSONArray(json.get("result").toString());
                    for (int i = 0; i < rel.length(); i++) {
                        JSONObject obj = (JSONObject) rel.get(i);
                        Map<Object, Object> map = new HashMap<Object, Object>();
                        map.put("text", obj.get("device_content_id").toString() + "." + obj.get("device_content_name").toString());
                        map.put("device_content_id", obj.get("device_content_id").toString());
                        map.put("select_val", 1);
                        content.add(map);
                    }
                    Map<Object, Object> m1 = new HashMap<Object, Object>();
                    m1.put("deviceId", Constents.current_device_id);
                    m1.put("result", content);
					/*for(int j=0;j<Constents.xscontentList.size();j++){
						if(Constents.xscontentList.get(j).get("deviceId").equals(Constents.current_device_id)){
							Constents.xscontentList.remove(Constents.xscontentList.get(j));
						}
					}*/
                    Constents.xscontentList.add(m1);
//					handler.obtainMessage().sendToTarget();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, Constents.current_project_id, Constents.current_device_id);
    }

    /**
     * 获取设备数量
     */
    public void getDeviceNum() {
        NetworkData.getInstance().deviceList(new NetworkResponceFace() {

            @Override
            public void callback(String result) {
                // TODO Auto-generated method stub
                JSONObject json;
                try {
                    json = new JSONObject(result);
                    JSONArray rel = new JSONArray(json.get("result").toString());
                    Constents.devicenum = rel.length();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, Constents.current_project_id);
    }
}
