package com.demo.app.activity.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Common;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;

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

public class PowerRunJXTaskListActivity extends BaseActivity{
	private SimpleAdapter adapter;
	private ListView prjxTaskListView;
	private List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
	private SharedPreferences sp;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			adapter.notifyDataSetChanged();
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_power_run_jxtask_list_layout);
		TitleCommon.setTitle(this, null, "检修任务列表",TabMainActivity.class, true);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		prjxTaskListView = (ListView)this.findViewById(R.id.prjxTaskListView);
		adapter = new SimpleAdapter(this, getData(),
				R.layout.jx_task_list_layout_item, new String[] { "text","value" },
				new int[] { R.id.jx_task_title,R.id.jx_task_time });
		prjxTaskListView.setAdapter(adapter);
		prjxTaskListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				String type = data.get(arg2).get("type").toString();
				//电气设备巡视卡
				if("1".equals(type)){
					i.putExtra("id", data.get(arg2).get("id").toString());
					i.putExtra("type", data.get(arg2).get("type").toString());
					i.setClass(PowerRunJXTaskListActivity.this, PowerRunXSKShowActivity.class);
					startActivity(i);
					//红外线
				}else if("2".equals(type)){
					i.putExtra("id", data.get(arg2).get("id").toString());
					i.putExtra("type", data.get(arg2).get("type").toString());
					i.setClass(PowerRunJXTaskListActivity.this, PowerRunRedlineShowActivity.class);
					startActivity(i);
					//安全检查
				}else if("3".equals(type)){
					i.putExtra("id", data.get(arg2).get("id").toString());
					i.putExtra("type", data.get(arg2).get("type").toString());
					i.setClass(PowerRunJXTaskListActivity.this, SafetyCultureCheckShowActivity.class);
					startActivity(i);
					//环境检查
				}else if("4".equals(type)){
					i.putExtra("id", data.get(arg2).get("id").toString());
					i.putExtra("type", data.get(arg2).get("type").toString());
					i.setClass(PowerRunJXTaskListActivity.this, SafetyCultureHJShowActivity.class);
					startActivity(i);
				}
			}
		});
	}
	public List<Map<String, Object>> getData() {
		int uid = sp.getInt("userId", -1);
		data.clear();
		NetworkData.getInstance().maintenanceTaskCardList(new NetworkResponceFace() {
			
			@Override
			public void callback(String result) {
				// TODO Auto-generated method stub
				JSONObject json;
				try {
//					result = URLDecoder.decode(result,"utf-8");
					json = new JSONObject(result);
					JSONArray rel = new JSONArray(json.get("result").toString());
					for(int i=0;i<rel.length();i++){
						JSONObject obj = (JSONObject) rel.get(i);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("text", obj.get("e_project_name").toString());
						map.put("id", obj.get("d_id").toString());
						map.put("type",obj.get("type").toString());
						map.put("value", Common.changeTime(obj.get("create_time").toString())+" "+obj.get("type_name").toString());
						data.add(map);
					}
					handler.obtainMessage().sendToTarget();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, uid+"");
		System.out.println(data);
		return data;
	}
	

}
