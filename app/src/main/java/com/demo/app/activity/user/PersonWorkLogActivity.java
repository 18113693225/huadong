package com.demo.app.activity.user;

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
import com.demo.app.activity.index.PowerRunMonthReportActivity;
import com.demo.app.activity.index.PowerRunNewMonthReportActivity;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Common;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
//个人工作日志
public class PersonWorkLogActivity extends BaseActivity implements OnClickListener{
	private Button exist_log_tables, new_log_tables;
	private SimpleAdapter adapter;
	private ListView logListView;
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
		setContentViewWithoutTitle(R.layout.person_work_log_layout);
		TitleCommon.setTitle(this,null, "工作日志", TabMainActivity.class,true);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		exist_log_tables = (Button) this.findViewById(R.id.exist_work_log_tables);
		new_log_tables = (Button) this.findViewById(R.id.new_work_log_tables);
		exist_log_tables.setOnClickListener(this);
		new_log_tables.setOnClickListener(this);
		logListView = (ListView)this.findViewById(R.id.workLogTablesListView);
		adapter = new SimpleAdapter(this, data/*getData()*/,
				R.layout.work_sh_log_layout_item, new String[] { "title","time" },
				new int[] { R.id.sh_log_title,R.id.sh_log_time });
		logListView.setAdapter(adapter);
		logListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String id = data.get(arg2).get("id").toString();
				Intent i = new Intent();
				i.putExtra("id", id);
				i.setClass(PersonWorkLogActivity.this, PersonWorkLogShowActivity.class);
				startActivity(i);
			}
		});
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new_log_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
		exist_log_tables.setBackgroundResource(R.drawable.button_franchise_shape);
		getData();
	}
	public List<Map<String, Object>> getData() {
		int uid = sp.getInt("userId", -1);
		data.clear();
		handler.obtainMessage().sendToTarget();
		NetworkData.getInstance().jobLogList(new NetworkResponceFace() {
			
			@Override
			public void callback(String result) {
				// TODO Auto-generated method stub
				JSONObject json;
				try {
//					result = URLDecoder.decode(result,"utf-8");
					json = new JSONObject(result);
					if(json.get("result")!=null){
						JSONArray rel = new JSONArray(json.get("result").toString());
						for(int i=0;i<rel.length();i++){
							JSONObject obj = (JSONObject) rel.get(i);
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("time", Common.changeTime(obj.get("start_time").toString())+
									"-"+Common.changeTime(obj.get("end_time").toString()));
							map.put("title", obj.get("title").toString());
							map.put("id", obj.get("id").toString());
							data.add(map);
						}
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
		case R.id.exist_work_log_tables:
			new_log_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			exist_log_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			break;
		case R.id.new_work_log_tables:
			new_log_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			exist_log_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			Intent i = new Intent();
			i.setClass(PersonWorkLogActivity.this, PersonWorkLogNewActivity.class);
			startActivity(i);
			break;
		default:
			break;
		}
	}
}
