package com.demo.app.activity.index;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;

public class TwoTicketManagerDownLoadActivity extends BaseActivity {
	private ListView twoTicketDownListView;
	private String[] items = {"变电站（发电厂）倒闸操作票",
			"变电站（发电厂）第一种工作票", "变电站（发电厂）第二种工作票", "电力电缆第一种工作票", "电力电缆第二种工作票",
			"变电站一级动火工作票", "变电站二级动火工作票", "二次工作安全措施票" };
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(TwoTicketManagerDownLoadActivity.this, "下载成功",Toast.LENGTH_LONG).show();
			} else if (msg.what == 2) {
				Toast.makeText(TwoTicketManagerDownLoadActivity.this, "下载失败",Toast.LENGTH_LONG).show();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_twoticket_manager_down_layout);
		TitleCommon.setTitle(this, null, "下载", TwoTicketManagerActivity.class, true);
		twoTicketDownListView = (ListView) this
				.findViewById(R.id.twoTicketDownListView);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.index_power_run_layout_item, new String[] { "text" },
				new int[] { R.id.power_text });
		twoTicketDownListView.setAdapter(adapter);
		twoTicketDownListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
//				Uri content_url = Uri.parse(Constents.REQUEST_URL+"/api/downWordPage?id="+Common.getFileNameIndex(items[arg2]));
				String fileName = "两票管理/"+items[arg2] ;
				try {
					fileName = URLEncoder.encode(URLEncoder.encode(fileName, "utf-8"), "utf-8") ;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				Uri content_url = Uri.parse(Constents.REQUEST_URL+"/api/downWordPage?fileName="+fileName);
				intent.setData(content_url);
				startActivity(intent);
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
