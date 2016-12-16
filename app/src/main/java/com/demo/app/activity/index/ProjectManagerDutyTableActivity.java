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
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.DateBean;
import com.demo.app.bean.WatchAtchBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;

public class ProjectManagerDutyTableActivity extends BaseActivity {
	private CustomeEditText2 pname,ptype,runit,recordperson,classname,dutyfz,zhuduty,fuduty,shixiperson,dutytime,workGroup,dutyStarttime,dutyEndtime;
	private SharedPreferences sp;
//	private RadioGroup radioGroup;
//	private RadioButton whiteBtn,blackBtn;
	private int isok = 1;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(ProjectManagerDutyTableActivity.this, "新建成功", Toast.LENGTH_LONG).show();
				ProjectManagerDutyTableActivity.this.finish();
			} else if (msg.what == 2) {
				Toast.makeText(ProjectManagerDutyTableActivity.this, "新建失败", Toast.LENGTH_LONG).show();
			} else if (msg.what == 3) {
				Toast.makeText(ProjectManagerDutyTableActivity.this, "有必填项未填", Toast.LENGTH_LONG).show();
			} else if (msg.what == 4) {
				Toast.makeText(ProjectManagerDutyTableActivity.this, "结束时间必须大于开始时间",
						Toast.LENGTH_LONG).show();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_project_manager_dutytable_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "排班表",ProjectManagerActivity.class, true);
		TitleCommon.setGpsTitle(this);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		initLayout();
	}

	public void initLayout(){
		pname = (CustomeEditText2)this.findViewById(R.id.pm_duty_pname);
		ptype = (CustomeEditText2)this.findViewById(R.id.pm_duty_ptype);
		runit = (CustomeEditText2)this.findViewById(R.id.pm_duty_runit);
		recordperson = (CustomeEditText2)this.findViewById(R.id.pm_duty_recordperson);
		recordperson.setText(sp.getString("username", ""));
		classname = (CustomeEditText2)this.findViewById(R.id.pm_duty_classname);
		dutyfz = (CustomeEditText2)this.findViewById(R.id.pm_duty_dutyfz);
		zhuduty = (CustomeEditText2)this.findViewById(R.id.pm_duty_zhuduty);
		fuduty = (CustomeEditText2)this.findViewById(R.id.pm_duty_fuduty);
		shixiperson = (CustomeEditText2)this.findViewById(R.id.pm_duty_shixiperson);
		workGroup = (CustomeEditText2)this.findViewById(R.id.pm_duty_group);
		dutytime = (CustomeEditText2)this.findViewById(R.id.pm_duty_dutytime);
		dutyStarttime = (CustomeEditText2)this.findViewById(R.id.pm_duty_start_dutytime);
		dutyEndtime = (CustomeEditText2)this.findViewById(R.id.pm_duty_end_dutytime);

		/*radioGroup = (RadioGroup)this.findViewById(R.id.pm_duty_radioGroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==R.id.pm_duty_radioWhite){
					isok = 1;
				}else {
					isok = 0;
				}
			}
		});
		whiteBtn = (RadioButton)this.findViewById(R.id.pm_duty_radioWhite);
		whiteBtn.setChecked(true);
		blackBtn = (RadioButton)this.findViewById(R.id.pm_duty_radioBlack);*/
		findViewById(R.id.pm_duty_saveBtn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int pnameValue = pname.getEditTexTag();
				int recordpersonValue = sp.getInt("userId", -1);
				String classnameValue = classname.getText();
				/*int dutyfzValue = dutyfz.getEditTexTag();
				int zhudutyValue = zhuduty.getEditTexTag();
				int fudutyValue = fudutyValue.getEditTexTag();
				int shixipersonValue = shixiperson.getEditTexTag();*/
				String dutyfzValue = dutyfz.getText();
				String zhudutyValue = zhuduty.getText();
				String fudutyValue = fuduty.getText();
				String shixipersonValue = shixiperson.getText();
				
				String dutytimeValue = dutytime.getText();
				String dutyStarttimeValue = dutyStarttime.getText();
				String dutyEndtimeValue = dutyEndtime.getText();
				String workGroupValue = workGroup.getText();
				Calendar calendar = Calendar.getInstance();
				String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());
				
				if(pnameValue==-1||recordpersonValue==-1||zhudutyValue.equals("")||dutyfzValue.equals("")
						||fudutyValue.equals("")||workGroupValue.equals("")||
						shixipersonValue.equals("")||"".equals(classnameValue)||"".equals(dutytimeValue)){
					handler.obtainMessage(3).sendToTarget();
				}else{
					WatchAtchBean bean  = new WatchAtchBean();
					bean.setGps(sp.getString("currentAddress", "定位失败"));
					bean.setEntry_name_id(pnameValue);
					bean.setFill_in_user_id(recordpersonValue);
					bean.setClass_name(classnameValue);
					bean.setLong_value(dutyfzValue);
					bean.setPositive_class(zhudutyValue);
					bean.setDeputy_duty_officer(fudutyValue);
					bean.setIntern(shixipersonValue);
//					bean.setLong_value_name(dutyfzValue);
//					bean.setPositive_class_name(zhudutyValue);
//					bean.setDeputy_duty_officer_name(fudutyValue);
//					bean.setIntern_name(shixipersonValue);
					bean.setWork_group(workGroupValue);
					bean.setDuty_time(dutytimeValue);
//					bean.setDuty_date(isok+"");
					bean.setCreate_time(time);
					//
					bean.setDuty_start_time(dutyStarttimeValue);
					bean.setDuty_end_time(dutyEndtimeValue);
					if(dutyEndtimeValue.compareTo(dutyStarttimeValue)<=0){
						handler.obtainMessage(4).sendToTarget();
						return;
					}
					List<DateBean> list = new ArrayList<DateBean>();
					list.add(bean);
					NetworkData.getInstance().watchAtchAdd(new NetworkResponceFace() {
	
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
				String ptypeValue = data.getExtras().getString("ptype");
				String runitValue = data.getExtras().getString("cname");
				if(ptypeValue!=null&&runitValue!=null){
					ptype.setText(ptypeValue);
					runit.setText(runitValue);				
				}
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
					et.setText(members);
				}
				break;
			default:
				break;
			}
		}
	}
}
