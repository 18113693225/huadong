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
//安全
public class SafetyCultureActivity extends BaseActivity {
	private ListView safetyCultureListView;
	private String[] items = { "安全检查巡视卡","环境检查巡视卡" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_safety_culture_layout);
		TitleCommon.setTitle(this, null, "安全文明", TabMainActivity.class, true);
		safetyCultureListView = (ListView) this
				.findViewById(R.id.safetyCultureListView);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.index_power_run_layout_item, new String[] { "text" },
				new int[] { R.id.power_text });
		safetyCultureListView.setAdapter(adapter);
		safetyCultureListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				//---权限值
				i.putExtra("editAuth", getIntent().getIntExtra("editAuth", 1));
				i.putExtra("seeAuth", getIntent().getIntExtra("seeAuth", 1));
				Map<String, Integer> m2 = (Map<String, Integer>) IndexFragment.authorListMap.get("4-"+(arg2+1));
				switch (arg2) {
				case 0:
					i.setClass(SafetyCultureActivity.this, SafetyCultureCheckTableActivity.class);
					break;
				case 1:
					i.setClass(SafetyCultureActivity.this, SafetyCultureHJTableActivity.class);
					break;
				default:
					break;
				}
				if(m2!=null){
					if(m2.get("see")==0){
						Toast.makeText(SafetyCultureActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
						return;
					}
					i.putExtra("editListAuth", m2.get("edit"));
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
