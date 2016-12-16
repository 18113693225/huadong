package com.demo.app.activity.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.util.TitleCommon;
//项目经理
public class ProjectManagerActivity extends BaseActivity {
	private String[] items = { "排班表", "工作联系单"};
	private ListView projectManagerListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_project_manager_layout);
		TitleCommon.setTitle(this, null, "项目管理", TabMainActivity.class, true);
		projectManagerListView = (ListView) this
				.findViewById(R.id.projectManagerListView);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.index_power_run_layout_item, new String[] { "text" },
				new int[] { R.id.power_text });
		projectManagerListView.setAdapter(adapter);
		projectManagerListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				//---权限值
				i.putExtra("editAuth", getIntent().getIntExtra("editAuth", 1));
				i.putExtra("seeAuth", getIntent().getIntExtra("seeAuth", 1));
				Map<String, Integer> m2 = (Map<String, Integer>) IndexFragment.authorListMap.get("3-"+(arg2+1));
				switch (arg2) {
				case 0:
					if(getIntent().getIntExtra("editAuth", 1)==0){
						Toast.makeText(ProjectManagerActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
						return;
					}
					if(m2!=null){
						if(m2.get("edit")==0){
							Toast.makeText(ProjectManagerActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
							return;
						}
					}
					i.setClass(ProjectManagerActivity.this, ProjectManagerDutyTableActivity.class);
					break;
				case 1:
					i.setClass(ProjectManagerActivity.this, ProjectManagerWorkConnectTicketListActivity.class);
					if(m2!=null){
						if(m2.get("see")==0){
							Toast.makeText(ProjectManagerActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
							return;
						}
						i.putExtra("editListAuth", m2.get("edit"));
					}
					break;
				default:
					break;
				}
				startActivity(i);
			}
		});
	}

	public List<Map<String, Object>> getData() {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < items.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", items[i]);
			data.add(map);
		}
		return data;
	}
}
