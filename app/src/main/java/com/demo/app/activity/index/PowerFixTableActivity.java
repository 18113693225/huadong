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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Common;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;

public class PowerFixTableActivity extends BaseActivity implements OnClickListener{
	private Button exist_dm_dj_tables, new_dm_dj_tables;
	private SimpleAdapter adapter;
	private ListView dmDjTablesListView;
	private SharedPreferences sp;
	private List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			adapter.notifyDataSetChanged();
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_power_fix_dqjxtable_layout);
		TitleCommon.setTitle(this, null, "电力检修",PowerFixActivity.class, true);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		exist_dm_dj_tables = (Button) this.findViewById(R.id.exist_pf_dqjx_tables);
		new_dm_dj_tables = (Button) this.findViewById(R.id.new_pf_dqjx_tables);
		exist_dm_dj_tables.setOnClickListener(this);
		new_dm_dj_tables.setOnClickListener(this);
		dmDjTablesListView = (ListView)this.findViewById(R.id.pfdqjxTablesListView);
		adapter = new SimpleAdapter(this, data,R.layout.work_sh_log_layout_item, new String[] { "title","time" },
				new int[] { R.id.sh_log_title,R.id.sh_log_time });
		dmDjTablesListView.setAdapter(adapter);
		dmDjTablesListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(Integer.parseInt(data.get(arg2).get("seeItemAuth").toString())==0){
					Toast.makeText(PowerFixTableActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent i = new Intent();
				i.putExtra("id", data.get(arg2).get("id").toString());
				i.putExtra("p_id", data.get(arg2).get("p_id").toString());
				i.putExtra("create_time", data.get(arg2).get("create_time").toString());
				i.putExtra("m_personnel_id", data.get(arg2).get("m_personnel_id").toString());
				i.putExtra("editAuth", getIntent().getIntExtra("editAuth", 1));
				i.putExtra("editListAuth", getIntent().getIntExtra("editListAuth", 1));
				i.putExtra("editItemAuth", Integer.parseInt(data.get(arg2).get("editItemAuth").toString()));
				i.setClass(PowerFixTableActivity.this, PowerFixTableShowActivity.class);
				startActivity(i);
			}
		});
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new_dm_dj_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
		exist_dm_dj_tables.setBackgroundResource(R.drawable.button_franchise_shape);
		getData();
	}
	public List<Map<String, Object>> getData() {
		int uid = sp.getInt("userId", -1);
		data.clear();
		NetworkData.getInstance().maintenanceRecordList(new NetworkResponceFace() {
			
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
						map.put("title", obj.get("entry_name_name").toString());
						map.put("time", Common.changeTime(obj.get("work_start_time").toString()));
						map.put("create_time", obj.get("create_time").toString());
						map.put("m_personnel_id", obj.get("m_personnel_id").toString());
						map.put("id", obj.get("id").toString());
						map.put("p_id", obj.get("entry_name_id").toString());
						map.put("seeItemAuth", "1");
						map.put("editItemAuth", "1");
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
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.exist_pf_dqjx_tables:
			new_dm_dj_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			exist_dm_dj_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			break;
		case R.id.new_pf_dqjx_tables:
			if(getIntent().getIntExtra("editAuth", 1)==0||getIntent().getIntExtra("editListAuth", 1)==0){
				Toast.makeText(PowerFixTableActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
				return;
			}
			new_dm_dj_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			exist_dm_dj_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			Intent i = new Intent();
			i.setClass(PowerFixTableActivity.this, PowerFixDQJXCardUserInfoActivity.class);
			startActivity(i);
			break;
		default:
			break;
		}
	}

}
