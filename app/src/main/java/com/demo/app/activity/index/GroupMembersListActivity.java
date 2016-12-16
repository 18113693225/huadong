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
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
//小组成员活动列表
public class GroupMembersListActivity extends BaseActivity {
	private ListView groupMemberListView;
	private SimpleAdapter  adapter;
	private List<Map<String,Object>> data;
	private List<String> returnValue = new ArrayList<String>();
	private List<String> returnName = new ArrayList<String>();

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			adapter.notifyDataSetChanged();;
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_members_list_layout);
		String title = "选择组员";
		TitleCommon.setTitle(this, null, title, null, false);
		groupMemberListView = (ListView) this.findViewById(R.id.groupMemberListView);
//		adapter = new XSContentAdapter(this,getData());
//		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, getData());
		adapter = new SimpleAdapter(this, getData(),
				R.layout.custome_user_mutil_choice_item, new String[]{"text"},
				new int[]{R.id.user_text});
		groupMemberListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); 
		groupMemberListView.setAdapter(adapter);
		
		groupMemberListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				LinearLayout ll = ((LinearLayout)view);
				CheckBox box = (CheckBox)ll.findViewById(R.id.user_radio);
				if(box.isChecked()){
					box.setChecked(false);
				}else{
					box.setChecked(true);
				}
				String item = data.get(position).get("text").toString();
				String itemId = data.get(position).get("id").toString();
				if(!returnName.contains(item)){
					returnName.add(item);					
				}else{
					returnName.remove(item);
				}
				if(!returnValue.contains(itemId)){
					returnValue.add(itemId);					
				}else{
					returnValue.remove(itemId);
				}
			}
		});
		findViewById(R.id.group_members_saveBtn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				if(returnValue.size()==0){
					i.putExtra("members", "");
					i.putExtra("membersId", "");
				}else{
					i.putExtra("members", returnName.toString());
					//[36, 61]
					StringBuffer sb = new StringBuffer();
					for (String id : returnValue) {
						sb.append("'"+id+"',");
					}
					i.putExtra("membersId", sb.subSequence(0, sb.length()-1));					
				}
				int editTextId = getIntent().getIntExtra("editTextId", -1);
				i.putExtra("editTextId", editTextId);
				System.out.println(returnValue.toString()+"--"+returnName.toString());
				GroupMembersListActivity.this.setResult(RESULT_OK, i);
				GroupMembersListActivity.this.finish();
			}
		});
	}

	public List<Map<String,Object>> getData() {
		data = new ArrayList<Map<String,Object>>();
		SharedPreferences sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		int uid = sp.getInt("userId", -1);
		NetworkData.getInstance().userList(new NetworkResponceFace() {

			@Override
			public void callback(String result) {
				// TODO Auto-generated method stub
				JSONObject json;
				try {
					json = new JSONObject(result);
					JSONArray rel = new JSONArray(json.get("result").toString());
					for (int i = 0; i < rel.length(); i++) {
						JSONObject obj = (JSONObject) rel.get(i);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("text", obj.get("name").toString());
						map.put("id", obj.get("id").toString());
						data.add(map);
//						data.add(obj.get("id").toString());
//						data.add(obj.get("name").toString());
					}
					handler.obtainMessage().sendToTarget();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		},uid);
		return data;
	}
}
