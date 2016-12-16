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
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.util.TitleCommon;
//服务介绍
public class ServiceIntroduceActivity extends BaseActivity {
	private GridView gridView;
	private int[] icon = { R.drawable.icon_electric_power_maintenance,
			R.drawable.icon_electrical_test,
			R.drawable.icon_power_lines_repair,
			R.drawable.icon_power_operations,
			R.drawable.icon_technical_services,
			R.drawable.icon_transmission_line_inspection,
			R.drawable.icon_fire_power_maintenance,
			R.drawable.icon_water_power_maintenance,
			R.drawable.icon_sun_power_station,
			R.drawable.icon_train_station,
			R.drawable.icon_two_tickets,
			R.drawable.icon_safety_culture_maintenance,
			R.drawable.icon_law,
			R.drawable.icon_al};
	private String[] iconName = { "电力运维", "电力检修", "电力抢修", "电气调试", "技术服务",
			"输电线路运检","火电运维","水电运维","光伏电站","轨道交通","两票管理","安全文明","法律法规","事故案例" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_service_introduce_layout);
		TitleCommon.setTitle(this, null, "技术咨询", TabMainActivity.class, true);
		gridView = (GridView) this.findViewById(R.id.indexSerIntroGridView);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.index_serintro_grid_item, new String[] { "image",
						"text" }, new int[] { R.id.si_item_image,
						R.id.si_item_text });
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				if(position>5){
					Toast.makeText(ServiceIntroduceActivity.this, "建设中...", Toast.LENGTH_SHORT).show();
					return;
				}
				i.putExtra("position", position);
				i.setClass(ServiceIntroduceActivity.this, ServiceIntroduceDetailsActivity.class);
				startActivity(i);
			}
		});
	}

	public List<Map<String, Object>> getData() {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < icon.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", icon[i]);
			map.put("text", iconName[i]);
			data.add(map);
		}
		return data;
	}
}
