package com.demo.app.activity.index;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.DateBean;
import com.demo.app.bean.JobContactSheetBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;

public class ProjectManagerWorkConnectTicketActivity extends BaseActivity {
	private CustomeEditText2 pname,operperson,recordtm,noticeperson,noticecontent;
	private SharedPreferences sp;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(ProjectManagerWorkConnectTicketActivity.this, "新建成功", Toast.LENGTH_LONG).show();
				ProjectManagerWorkConnectTicketActivity.this.finish();
			} else if (msg.what == 2) {
				Toast.makeText(ProjectManagerWorkConnectTicketActivity.this, "新建失败", Toast.LENGTH_LONG).show();
			} else if (msg.what == 3) {
				Toast.makeText(ProjectManagerWorkConnectTicketActivity.this, "有必填项未填", Toast.LENGTH_LONG).show();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_project_manager_workconnectticket_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "工作联系单",ProjectManagerWorkConnectTicketListActivity.class, true);
		TitleCommon.setGpsTitle(this);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		initLayout();
	}
	
	public void initLayout(){
		pname = (CustomeEditText2)this.findViewById(R.id.pm_workconn_pname);
		operperson = (CustomeEditText2)this.findViewById(R.id.pm_workconn_operperson);
		operperson.setText(sp.getString("username", ""));
		recordtm = (CustomeEditText2)this.findViewById(R.id.pm_workconn_recordtm);
		noticeperson = (CustomeEditText2)this.findViewById(R.id.pm_workconn_noticeperson);
		noticecontent = (CustomeEditText2)this.findViewById(R.id.pm_workconn_noticecontent);
		findViewById(R.id.pm_workconn_saveBtn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int pnameValue = pname.getEditTexTag();
				String recordTmValue = recordtm.getText();
				int operpersonValue = sp.getInt("userId", -1);
				String noticepersonValue = noticeperson.getStringEditTexTag();
				String noticecontentValue = noticecontent.getText();
				if(pnameValue==-1||"".equals(recordTmValue)||"-1".equals(noticepersonValue)||"".equals(noticecontentValue)){
					handler.obtainMessage(3).sendToTarget();
				}else{
					JobContactSheetBean bean = new JobContactSheetBean();
					bean.setEntry_name_id(pnameValue);
					bean.setCreate_time(recordTmValue);
					bean.setOperator_id(operpersonValue);
					bean.setInform_staff(noticepersonValue);
					bean.setNotification_content(noticecontentValue);
					List<DateBean> list = new ArrayList<DateBean>();
					list.add(bean);
					NetworkData.getInstance().jobContactSheetAdd(new NetworkResponceFace() {
						
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
					}, list);
				}
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == -1) {
			int editTextId = data.getIntExtra("editTextId", -1);
			switch (requestCode) {
			case 98:
				String text = data.getExtras().getString("text");
				int id = data.getExtras().getInt("id");
				if (editTextId != -1) {
					CustomeEditText2 et = (CustomeEditText2) findViewById(editTextId);
					et.setText(text);
					et.setEditTextTag(id);
				}
				break;
			case 99:
				if (editTextId != -1) {
					CustomeEditText2 et = (CustomeEditText2) findViewById(editTextId);
					String time = data.getExtras().getString("time");
					et.setText(time);
				}
				break;
			case 97:
				if (editTextId != -1) {
					CustomeEditText2 et = (CustomeEditText2) findViewById(editTextId);
					String members = data.getExtras().getString("members");
					String membersId = data.getExtras().getString("membersId");
					et.setText(members);
					et.setEditTextTag(membersId);
				}
				break;
			default:
				break;
			}
		}
	}
}
