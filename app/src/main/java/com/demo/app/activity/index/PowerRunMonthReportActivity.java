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
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;

public class PowerRunMonthReportActivity extends BaseActivity implements OnClickListener{
	private Button exist_month_tables, new_month_tables;
	private SimpleAdapter adapter;
	private ListView monthTablesListView;
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
		setContentView(R.layout.index_power_run_month_report_layout);
		TitleCommon.setTitle(this, null, "变电站运行月报",PowerRunActivity.class, true);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		
		exist_month_tables = (Button) this.findViewById(R.id.exist_pr_month_tables);
		new_month_tables = (Button) this.findViewById(R.id.new_pr_month_tables);
		exist_month_tables.setOnClickListener(this);
		new_month_tables.setOnClickListener(this);
		monthTablesListView = (ListView)this.findViewById(R.id.prMonthTablesListView);
		adapter = new SimpleAdapter(this, data,
				R.layout.index_power_run_new_month_report_item_layout, new String[] { "text","time" },
				new int[] { R.id.fr_month_text,R.id.fr_month_time });
		monthTablesListView.setAdapter(adapter);
		monthTablesListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(Integer.parseInt(data.get(arg2).get("seeItemAuth").toString())==0){
					Toast.makeText(PowerRunMonthReportActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent i = new Intent();
				i.putExtra("id", data.get(arg2).get("id").toString());
				i.putExtra("editAuth", getIntent().getIntExtra("editAuth", 1));
				i.putExtra("editListAuth", getIntent().getIntExtra("editListAuth", 1));
				i.putExtra("editItemAuth", Integer.parseInt(data.get(arg2).get("editItemAuth").toString()));
				i.setClass(PowerRunMonthReportActivity.this, PowerRunMonthReportShowActivity.class);
				startActivity(i);
			}
		});
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new_month_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
		exist_month_tables.setBackgroundResource(R.drawable.button_franchise_shape);
		getData();
	}
	public List<Map<String, Object>> getData() {
		int uid = sp.getInt("userId", -1);
		data.clear();
		NetworkData.getInstance().runMonthlyReportList(new NetworkResponceFace() {
			
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
						map.put("text", obj.get("entry_name_name").toString());
						map.put("time", obj.get("create_time").toString());
						map.put("id", obj.get("id").toString());
						map.put("seeItemAuth", obj.get("val_see").toString());
						map.put("editItemAuth", obj.get("val_edit").toString());
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
		case R.id.exist_pr_month_tables:
			new_month_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			exist_month_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			break;
		case R.id.new_pr_month_tables:
			if(getIntent().getIntExtra("editAuth", 1)==0||getIntent().getIntExtra("editListAuth", 1)==0){
				Toast.makeText(PowerRunMonthReportActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
				return;
			}
			new_month_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			exist_month_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			Intent i = new Intent();
			i.setClass(PowerRunMonthReportActivity.this, PowerRunNewMonthReportActivity.class);
			startActivity(i);				
			break;
		default:
			break;
		}
	}

}
