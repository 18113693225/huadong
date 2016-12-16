package com.demo.app.activity.index;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.DateBean;
import com.demo.app.bean.StopOrSendContactBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;
//客户经理权力界面
public class CustomerManagerPowerConnectTicketActivity extends BaseActivity {
	private CustomeEditText2 pname,runit,sqperson,sqtm,sqcontent;
	private SharedPreferences sp;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(CustomerManagerPowerConnectTicketActivity.this, "新建成功", Toast.LENGTH_LONG).show();
				CustomerManagerPowerConnectTicketActivity.this.finish();
			} else if (msg.what == 2) {
				Toast.makeText(CustomerManagerPowerConnectTicketActivity.this, "新建失败", Toast.LENGTH_LONG).show();
			} else if (msg.what == 3) {
				Toast.makeText(CustomerManagerPowerConnectTicketActivity.this, "有必填项未填", Toast.LENGTH_LONG).show();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_customer_manager_powerconnectticket_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "新建停（送）电联系单",CustomerManagePowerConnectTicketListActivity.class, true);
		TitleCommon.setGpsTitle(this);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		initLayout();
	}
	public void initLayout(){
		pname = (CustomeEditText2)this.findViewById(R.id.cm_stoppower_pname);
		runit = (CustomeEditText2)this.findViewById(R.id.cm_stoppower_runit);
		sqperson = (CustomeEditText2)this.findViewById(R.id.cm_stoppower_sqperson);
		sqperson.setText(sp.getString("username", ""));
		sqtm = (CustomeEditText2)this.findViewById(R.id.cm_stoppower_sqtime);
		sqcontent = (CustomeEditText2)this.findViewById(R.id.cm_stoppower_sqcontent);
		findViewById(R.id.cm_stoppower_saveBtn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int pnameValue = pname.getEditTexTag();
				String sqTmValue = sqtm.getText();
				int sqpersonValue = sp.getInt("userId", -1);
				String sqcontentValue = sqcontent.getText();
				if(pnameValue==-1||"".equals(sqTmValue)||sqpersonValue==-1||"".equals(sqcontentValue)){
					handler.obtainMessage(3).sendToTarget();
				}else{
					StopOrSendContactBean bean = new StopOrSendContactBean();
					bean.setEntry_name_id(pnameValue);
					Calendar calendar = Calendar.getInstance();
					String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());
					bean.setCreate_time(time);
					
					//必须赋值
//					bean.setReception_time(time);
//					bean.setConfirm_time(time);
					
					bean.setBlackout_began(sqTmValue);
					bean.setOperator_id(sqpersonValue);
					bean.setBlackout_content(sqcontentValue);
					List<DateBean> list = new ArrayList<DateBean>();
					list.add(bean);
					NetworkData.getInstance().stopOrSendContactAdd(new NetworkResponceFace() {
						
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
				String runitValue = data.getExtras().getString("cname");
				if(runitValue!=null){
					runit.setText(runitValue);				
				}
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
