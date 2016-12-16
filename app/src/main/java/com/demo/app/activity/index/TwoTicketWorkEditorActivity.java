package com.demo.app.activity.index;


import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.WorkTicketBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;
import com.google.gson.Gson;

public class TwoTicketWorkEditorActivity extends BaseActivity {
	private CustomeEditText2 pName,runit,rPerson,pnum,workNum,workName,workFzr,workQfr,workStart,workEnd;
	private Button saveBtn;
	private SharedPreferences sp;
	private String editData;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				Toast.makeText(TwoTicketWorkEditorActivity.this, "编辑工作票成功", Toast.LENGTH_LONG).show();
				Intent i = new Intent();
				i.putExtra("id", pName.getEditTexTag());
				i.setClass(TwoTicketWorkEditorActivity.this, TwoTicketWorkShowActivity.class);
				TwoTicketWorkEditorActivity.this.finish();
			}else if(msg.what==2){
				Toast.makeText(TwoTicketWorkEditorActivity.this, "编辑工作票失败", Toast.LENGTH_LONG).show();
			}else if(msg.what==3){
				Toast.makeText(TwoTicketWorkEditorActivity.this, "有必填项未填", Toast.LENGTH_LONG).show();
			}else if (msg.what == 4) {
				Toast.makeText(TwoTicketWorkEditorActivity.this, "结束时间必须大于开始时间",
						Toast.LENGTH_LONG).show();
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_twoticket_work_new_layout);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "编辑工作票统计表",TwoTicketWorkShowActivity.class, true);
		TitleCommon.setGpsTitle(this);
		editData = getIntent().getStringExtra("editData");
		initLayout();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==-1){
			int editTextId = data.getIntExtra("editTextId", -1);
			switch (requestCode) {
			case 98:
				String text = data.getExtras().getString("text");
				int id = data.getExtras().getInt("id");
				if(editTextId!=-1){
					CustomeEditText2 et = (CustomeEditText2)findViewById(editTextId);
					et.setText(text);
					et.setEditTextTag(id);
				}
				break;
			case 99:
				if(editTextId!=-1){
					CustomeEditText2 et = (CustomeEditText2)findViewById(editTextId);
					String time = data.getExtras().getString("time");
					et.setText(time);
				}
				break;
			default:
				break;
			}
		}
		
		
	}
	
	public void initLayout(){
		Gson gson = new Gson();
		WorkTicketBean bean = gson.fromJson(editData,WorkTicketBean.class);
		final int id = bean.getId();
		pName = (CustomeEditText2)this.findViewById(R.id.work_new_pName);
		pName.setText(bean.getEntry_name_name());
		pName.setEditTextTag(bean.getEntry_name_id());
		runit = (CustomeEditText2)this.findViewById(R.id.work_new_runit);
		runit.setText(bean.getCompany_name());
		runit.setEditTextTag(bean.getCompany_id());
		rPerson = (CustomeEditText2)this.findViewById(R.id.work_new_rPerson);
		rPerson.setText(sp.getString("username", ""));
		
		pnum = (CustomeEditText2)this.findViewById(R.id.work_new_pnum);
		pnum.setText(bean.getSerial_number());
		workNum = (CustomeEditText2)this.findViewById(R.id.work_new_workNum);
		workNum.setText(bean.getWork_number());
		
		workName = (CustomeEditText2)this.findViewById(R.id.work_new_workName);
		workName.setText(bean.getWord_ticket_name());
		workName.setEditTextTag(bean.getWork_ticket_id());
		
		workFzr = (CustomeEditText2)this.findViewById(R.id.work_new_workFzr);
		workFzr.setText(bean.getWork_user_name());
		
		workQfr = (CustomeEditText2)this.findViewById(R.id.work_new_workQfr);
		workQfr.setText(bean.getIssue_user_name());
		workQfr.setEditTextTag(bean.getIssue_user_id());
		workStart = (CustomeEditText2)this.findViewById(R.id.work_new_workStart);
		workStart.setText(bean.getWork_start_time());
		workEnd = (CustomeEditText2)this.findViewById(R.id.work_new_workEnd);
		workEnd.setText(bean.getWork_end_time());
		saveBtn = (Button)this.findViewById(R.id.work_new_save);
		saveBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int pNameValue = pName.getEditTexTag();
				int runitValue = runit.getEditTexTag();
				//记录人。当前用户
				int rPersonValue = /*rPerson.getEditTexTag()*/sp.getInt("userId", -1);
				String pnumValue = pnum.getText();
				String workNumValue = workNum.getText();
				int workNameValue = workName.getEditTexTag();
				String workFzrValue = workFzr.getText();
				int workQfrValue = workQfr.getEditTexTag();
				String workStartValue = workStart.getText();
				String workEndValue = workEnd.getText();
				WorkTicketBean bean = new WorkTicketBean();
				bean.setId(id);
				bean.setEntry_name_id(pNameValue);
				bean.setCompany_id(runitValue);
				bean.setCreate_user_id(rPersonValue);
				bean.setSerial_number(pnumValue);
				bean.setWork_number(workNumValue);
				bean.setWork_ticket_id(workNameValue);
				bean.setWork_user_name(workFzrValue);
				bean.setIssue_user_id(workQfrValue);
				bean.setWork_start_time(workStartValue);
				bean.setWork_end_time(workEndValue);
				if(workEndValue.compareTo(workStartValue)<=0){
					handler.obtainMessage(4).sendToTarget();
					return;
				}
				if(pNameValue==-1||runitValue==-1||rPersonValue==-1||workNameValue==-1||workQfrValue==-1
						||"".equals(pnumValue)||"".equals(workNumValue)
						||"".equals(workFzrValue)||"".equals(workStartValue)||"".equals(workEndValue)){
					handler.obtainMessage(3).sendToTarget();
				}else{
//					List<DateBean> list = new ArrayList<DateBean>();
//					list.add(bean);
					NetworkData.getInstance().workTicketUpdate(
							new NetworkResponceFace() {
								
								@Override
								public void callback(String result) {
									// TODO Auto-generated method stub
									JSONObject json;
									try {
										json = new JSONObject(result);
										String status = json.getString("status");
										if("success".equals(status)){
											handler.obtainMessage(1).sendToTarget();
										}else{
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

}
