package com.demo.app.activity.index;


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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.WorkContackBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;
import com.google.gson.Gson;
//客户经理工作连接票编辑
public class CustomerManagerWorkConnectTicketEditorActivity extends BaseActivity {
	private CustomeEditText2 pname,operperson,recordtm,noticeperson,noticecontent;
	private SharedPreferences sp;
	private int isok = 0;
	private String editData;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(CustomerManagerWorkConnectTicketEditorActivity.this, "编辑成功", Toast.LENGTH_LONG).show();
				CustomerManagerWorkConnectTicketEditorActivity.this.finish();
			} else if (msg.what == 2) {
				Toast.makeText(CustomerManagerWorkConnectTicketEditorActivity.this, "编辑失败", Toast.LENGTH_LONG).show();
			} else if (msg.what == 3) {
				Toast.makeText(CustomerManagerWorkConnectTicketEditorActivity.this, "有必填项未填", Toast.LENGTH_LONG).show();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_customer_manager_workconnectticket_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "编辑工作联系单",CustomerManagerWorkConnectTicketShowActivity.class, true);
		TitleCommon.setGpsTitle(this);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		initLayout();
	}
	
	public void initLayout(){
		editData = getIntent().getStringExtra("editData");
		Gson gson = new Gson();
		final WorkContackBean bean = gson.fromJson(editData,WorkContackBean.class);
		final int id = bean.getId();
		pname = (CustomeEditText2)this.findViewById(R.id.cm_workconn_pname);
		pname.setText(bean.getEntry_name_name());
		pname.setEditTextTag(bean.getEntry_name_id());
		operperson = (CustomeEditText2)this.findViewById(R.id.cm_workconn_operperson);
//		operperson.setText(sp.getString("username", ""));
		operperson.setText(bean.getOperator_name());
		operperson.setEditTextTag(bean.getOperator_id());
		
		recordtm = (CustomeEditText2)this.findViewById(R.id.cm_workconn_recordTm);
		recordtm.setText(bean.getCreate_time());
		noticeperson = (CustomeEditText2)this.findViewById(R.id.cm_workconn_noticeperson);
		noticeperson.setText(bean.getNotify_the_staff_name());
		noticeperson.setEditTextTag(bean.getNotify_the_staff());
		noticecontent = (CustomeEditText2)this.findViewById(R.id.cm_workconn_noticecontent);
		noticecontent.setText(bean.getContents_notice());
		CheckBox box = (CheckBox)findViewById(R.id.cm_workconn_radioCkeck);
		if(bean.getStatus()==1){
			box.setChecked(true);
			isok = 1;
		}
		box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					isok = 1;
				}else{
					isok = 0;
				}
			}
		});
		findViewById(R.id.cm_workconn_saveBtn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int pnameValue = pname.getEditTexTag();
				String recordTmValue = recordtm.getText();
				int operpersonValue = sp.getInt("userId", -1);
				int noticepersonValue = noticeperson.getEditTexTag();
				String noticecontentValue = noticecontent.getText();
				if(pnameValue==-1||"".equals(recordTmValue)||noticepersonValue==-1||"".equals(noticecontentValue)){
					handler.obtainMessage(3).sendToTarget();
				}else{
					WorkContackBean bean = new WorkContackBean();
					bean.setId(id);
					bean.setEntry_name_id(pnameValue);
					bean.setCreate_time(recordTmValue);
					bean.setOperator_id(operpersonValue);
					bean.setNotify_the_staff(noticepersonValue);
					bean.setContents_notice(noticecontentValue);
					bean.setStatus(isok);
					NetworkData.getInstance().workContackUpdate(new NetworkResponceFace() {
						
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
					}, bean);
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
			default:
				break;
			}
		}
	}
}
