package com.demo.app.activity.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.adapter.UserProjectListAdapter;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;

public class UserProjectGroupListActivity extends BaseActivity {
	private List<Map<Object, Object>> data;
	private UserProjectListAdapter adapter;
	private SharedPreferences sp;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			adapter.notifyDataSetChanged();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewWithoutTitle(R.layout.user_project_group_layout);
		if(getIntent().getStringExtra("type")!=null){
			TitleCommon.setTitle(this, null, "请选择所属项目", TabMainActivity.class, true);
		}else{
			TitleCommon.setTitle(this, null, "请选择所属项目", TabMainActivity.class, true);			
		}
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		ListView userProjectGroupListView = (ListView) this
				.findViewById(R.id.userProjectGroupListView);
		userProjectGroupListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		adapter = new UserProjectListAdapter(this, getData());
		userProjectGroupListView.setAdapter(adapter);
		userProjectGroupListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						String p_id = data.get(position).get("id").toString();
						//保存用户当前项目组
						sp.edit().putString("user_project_id", p_id).commit();
						for (Map<Object,Object> m : data) {
							if(m.get("id").toString().equals(p_id)){
								m.put("select_val", 1);
							}else{
								m.put("select_val", 0);
							}
						}
						adapter.notifyDataSetChanged();
					}
				});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		getData();
	}

	public List<Map<Object, Object>> getData() {
		int uid = sp.getInt("userId", -1);
		data = new ArrayList<Map<Object, Object>>();
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
						Map<Object, Object> map = new HashMap<Object, Object>();
						map.put("text", obj.get("e_project_name").toString());
						map.put("id", obj.get("id").toString());
						if(sp.getString("user_project_id", "-1").equals(obj.get("id").toString())){
							map.put("select_val", 1);
						}else{
							map.put("select_val", 0);
						}
						data.add(map);
					}
					handler.obtainMessage().sendToTarget();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, uid);
		return data;
	}
}
