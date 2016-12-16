package com.demo.app.activity.user;

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
import com.demo.app.activity.TabMainActivity;
import com.demo.app.activity.index.PowerRunJXHandleCardActivity;
import com.demo.app.activity.index.PowerRunJXHandleCardRedlineActivity;
import com.demo.app.activity.index.ProjectManagerDutyTableEditorActivity;
import com.demo.app.activity.index.SafetyCultureCheckHandleCardActivity;
import com.demo.app.activity.index.SafetyCultureHJHandleCardActivity;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;

public class PersonWorkManagerActivity extends BaseActivity {
    private ListView userWorkManagerList;
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    private SharedPreferences sp;
    private SimpleAdapter adapter;
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
        setContentViewWithoutTitle(R.layout.person_projector_manager_layout);
        TitleCommon.setTitle(this, null, "工作管理", TabMainActivity.class, true);
        sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
        userWorkManagerList = (ListView) this.findViewById(R.id.userWorkManagerList);
        adapter = new SimpleAdapter(this, getData(),
                R.layout.user_work_manager_layout_item, new String[]{"title", "company", "person", "time"},
                new int[]{R.id.user_wm_title, R.id.user_wm_company, R.id.user_wm_person, R.id.user_wm_time});
        userWorkManagerList.setAdapter(adapter);
        userWorkManagerList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent i = new Intent();
                String type = data.get(arg2).get("type").toString();
                i.putExtra("type", "pwm");
                i.putExtra("oper_id", data.get(arg2).get("id").toString());
                i.putExtra("oper_type", data.get(arg2).get("type").toString());
                i.putExtra("lid", data.get(arg2).get("lid").toString());
                i.putExtra("create_time", data.get(arg2).get("time").toString());
                i.putExtra("uid", data.get(arg2).get("uid").toString());
                if ("1".equals(type)) {
                    i.setClass(PersonWorkManagerActivity.this, PowerRunJXHandleCardActivity.class);
                    startActivity(i);
                } else if ("2".equals(type)) {
                    i.setClass(PersonWorkManagerActivity.this, PowerRunJXHandleCardRedlineActivity.class);
                    startActivity(i);
                } else if ("3".equals(type)) {
                    i.setClass(PersonWorkManagerActivity.this, SafetyCultureCheckHandleCardActivity.class);
                    startActivity(i);
                } else if ("4".equals(type)) {
                    i.setClass(PersonWorkManagerActivity.this, SafetyCultureHJHandleCardActivity.class);
                    startActivity(i);
                } else if ("5".equals(type)) {
                    i.setClass(PersonWorkManagerActivity.this, ProjectManagerDutyTableEditorActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    public List<Map<String, Object>> getData() {
        int uid = sp.getInt("userId", -1);
        data.clear();
        NetworkData.getInstance().myProjectList(new NetworkResponceFace() {

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
                        map.put("title", obj.get("e_project_name").toString());
                        /*if(obj.has("company_name"))
                            map.put("company", ""+obj.get("company_name"));
						else
							map.put("company", "");*/
                        map.put("time", obj.get("create_time").toString());
                        if (obj.has("user_name")) {
                            map.put("person", "" + obj.get("user_name"));
                        }
                        map.put("person", "");
                        map.put("id", obj.get("d_id").toString());
                        map.put("lid", obj.get("id").toString());
                        map.put("uid", obj.get("create_user_id").toString());
                        map.put("type", obj.get("type").toString());
                        String type = obj.get("type").toString();
                        String typeName = "";
                        if ("1".equals(type)) {
                            typeName = "电气设备巡视卡";
                        } else if ("2".equals(type)) {
                            typeName = "红外线测温记录卡";
                        } else if ("3".equals(type)) {
                            typeName = "安全检查巡视卡";
                        } else if ("4".equals(type)) {
                            typeName = "环境检查巡视卡";
                        } else if ("5".equals(type)) {
                            typeName = "排班表";
                        }
                        map.put("company", typeName);
                        data.add(map);
                    }
                    handler.obtainMessage().sendToTarget();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, uid);
        System.out.println(data);
        return data;
    }
}
