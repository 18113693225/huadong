package com.demo.app.activity.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;

public class UpdateUserInfoActivity extends BaseActivity {

	private ListView userinfoListView;
	private List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();;
	private SharedPreferences sp;
	private SimpleAdapter adapter;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(UpdateUserInfoActivity.this, "修改成功", Toast.LENGTH_LONG).show();
				UpdateUserInfoActivity.this.finish();
			} else if (msg.what == 2) {
				Toast.makeText(UpdateUserInfoActivity.this, "修改失败", Toast.LENGTH_LONG).show();
			} else if (msg.what == 3) {
				Toast.makeText(UpdateUserInfoActivity.this, "有必填项未填", Toast.LENGTH_LONG).show();
			} else if(msg.what == 4){
				adapter.notifyDataSetChanged();
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewWithoutTitle(R.layout.update_userinfo_layout);
		TitleCommon.setTitle(this, null, "个人信息修改", TabMainActivity.class, true);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		userinfoListView = (ListView)this.findViewById(R.id.userinfoListViwe);
		adapter = new SimpleAdapter(this, getData(), 
				R.layout.userinfo_layout_item, new String[]{"title","value"}, new int[]{R.id.userinfo_title,R.id.userinfo_value});
		userinfoListView.setAdapter(adapter);
		userinfoListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(arg2==1){
					Toast.makeText(UpdateUserInfoActivity.this, "电话号码不能修改！", Toast.LENGTH_SHORT).show();
				}else{
					Intent i = new Intent();
					i.putExtra("type", arg2);
					i.putExtra("value", data.get(arg2).get("value").toString());
					i.setClass(UpdateUserInfoActivity.this, ModifyUserInfoCommonActivity.class);
					startActivityForResult(i, 100);					
				}
			}
		});
		findViewById(R.id.userinfo_saveBtn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = "";
				String phone = "";
				String address = "";
				String card_bank = "";
				String account = "";
				for(int i=0;i<data.size();i++){
					Map<String,Object> m = data.get(i);
					switch (i) {
					case 0:
						name = m.get("value").toString();
						break;
					case 1:
						phone = m.get("value").toString();
						break;
					case 2:
						address = m.get("value").toString();
						break;
					case 3:
						card_bank = m.get("value").toString();
						break;
					case 4:
						account = m.get("value").toString();
						break;
					default:
						break;
					}
				}
				
				NetworkData.getInstance().updateEUserDetailInfo(
						new NetworkResponceFace() {
							@Override
							public void callback(String result) {
								// TODO Auto-generated method stub
								JSONObject json;
								try {
									json = new JSONObject(result);
									String status = json.getString("status");
									if ("success".equals(status)) {
										handler.obtainMessage(1).sendToTarget();
									} else {
										handler.obtainMessage(2).sendToTarget();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									handler.obtainMessage(2).sendToTarget();

								}
							}
						}, phone,name,address,card_bank,account,sp.getInt("userId", -1)+"");
			}
		});
	}

	public List<Map<String,Object>> getData(){
		int uid = sp.getInt("userId", -1);
		data.clear();
		NetworkData.getInstance().getUserInfo(new NetworkResponceFace() {
			@Override
			public void callback(String result) {
				// TODO Auto-generated method stub
				String[] title = {"姓名：","电话：","地址：","卡户银行：","银行卡号："};
				JSONObject json;
				try {
					json = new JSONObject(result);
					JSONObject obj = (JSONObject) json.get("result");
					for(int i=0;i<title.length;i++){
						Map<String,Object> m = new HashMap<String, Object>();
						m.put("title", title[i]);
						String value = "";
						switch (i) {
						case 0:
							value = obj.getString("name");
							break;
						case 1:
							value = obj.getString("phone");
							break;
						case 2:
							value = obj.getString("address");
							break;
						case 3:
							value = obj.getString("card_bank");
							break;
						case 4:
							value = obj.getString("card_number");
							break;
						default:
							break;
						}
						m.put("value", value);
						data.add(m);
					}
					handler.obtainMessage(4).sendToTarget();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, uid+"");
		System.out.println(data);
		return data;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == -1) {
			switch (requestCode) {
			case 100:
				String contentValue = intent.getExtras().getString("contentValue");
				int type = intent.getExtras().getInt("type");
				data.get(type).put("value", contentValue);
				handler.obtainMessage(4).sendToTarget();
				break;
			default:
				break;
			}
		}
	}
}
