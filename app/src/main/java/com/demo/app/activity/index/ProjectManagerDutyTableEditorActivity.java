package com.demo.app.activity.index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.user.PersonWorkManagerActivity;
import com.demo.app.bean.WatchAtchBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;
import com.google.gson.Gson;

public class ProjectManagerDutyTableEditorActivity extends BaseActivity {
	private CustomeEditText2 pname,ptype,runit,recordperson,classname,dutyfz,zhuduty,fuduty,shixiperson,group,dutytime,dutystime,dutyetime;
	private TextView ttime,taddress;
	private String oper_id;
	private String oper_type;
	private String uid;
	private String pwm;
	private SharedPreferences sp;
//	private RadioButton whiteBtn,blackBtn;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				showData(msg.obj.toString());
			}else if (msg.what == 1) {
				Toast.makeText(ProjectManagerDutyTableEditorActivity.this, "保存成功", Toast.LENGTH_LONG).show();
				ProjectManagerDutyTableEditorActivity.this.finish();
			} else if (msg.what == 2) {
				Toast.makeText(ProjectManagerDutyTableEditorActivity.this, "保存失败", Toast.LENGTH_LONG).show();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_project_manager_dutytable_editor_layout);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		//判断从哪里进入的
		pwm = getIntent().getStringExtra("type");
		if(pwm!=null){
			TitleCommon.setTitle(this, null, "项目管理-排班表",PersonWorkManagerActivity.class, true);	
		}else{
			TitleCommon.setTitle(this, null, "项目管理-排班表",SafetyCultureHJShowActivity.class, true);			
		}
		TitleCommon.setGpsTitle(this);
		oper_id = getIntent().getStringExtra("oper_id");
		oper_type = getIntent().getStringExtra("oper_type");
		uid = getIntent().getStringExtra("uid");
		ttime = (TextView) this.findViewById(R.id.title_gps_time);
		taddress = (TextView) this.findViewById(R.id.title_gps_address);
		pname = (CustomeEditText2)this.findViewById(R.id.pm_duty_editor_pname);
		ptype = (CustomeEditText2)this.findViewById(R.id.pm_duty_editor_ptype);
		runit = (CustomeEditText2)this.findViewById(R.id.pm_duty_editor_runit);
		recordperson = (CustomeEditText2)this.findViewById(R.id.pm_duty_editor_recordperson);
		classname = (CustomeEditText2)this.findViewById(R.id.pm_duty_editor_classname);
		dutyfz = (CustomeEditText2)this.findViewById(R.id.pm_duty_editor_dutyfz);
		zhuduty = (CustomeEditText2)this.findViewById(R.id.pm_duty_editor_zhuduty);
		fuduty = (CustomeEditText2)this.findViewById(R.id.pm_duty_editor_fuduty);
		shixiperson = (CustomeEditText2)this.findViewById(R.id.pm_duty_editor_shixiperson);
		group = (CustomeEditText2)this.findViewById(R.id.pm_duty_editor_group);
		dutytime = (CustomeEditText2)this.findViewById(R.id.pm_duty_editor_dutytime);
		dutystime = (CustomeEditText2)this.findViewById(R.id.pm_duty_editor_start_dutytime);
		dutyetime = (CustomeEditText2)this.findViewById(R.id.pm_duty_editor_end_dutytime);

//		whiteBtn = (RadioButton)this.findViewById(R.id.pm_duty_editor_radioWhite);
//		whiteBtn.setChecked(true);
//		blackBtn = (RadioButton)this.findViewById(R.id.pm_duty_editor_radioBlack);
		//个人中心工作管理
		if(getIntent().getStringExtra("data")==null){
			getData(oper_id);
			findViewById(R.id.pm_duty_editor_saveBtn).setVisibility(View.INVISIBLE);
			findViewById(R.id.pm_duty_editor_end).setVisibility(View.INVISIBLE);
		}else{//电力运行检修任务
			showData(getIntent().getStringExtra("data"));
		}
	}
	
	public void getData(String id) {
		NetworkData.getInstance().myProjectBean(
				new NetworkResponceFace() {

					@Override
					public void callback(String result) {
						// TODO Auto-generated method stub
						JSONObject json;
						try {
							json = new JSONObject(result);
							handler.obtainMessage(0,json.get("result").toString()).sendToTarget();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, Integer.parseInt(getIntent().getStringExtra("lid")),getIntent().getStringExtra("create_time"),uid, oper_type);
	}
	public void showData(String data) {
		try {
			JSONArray rel = new JSONArray(data);
			for (int i = 0; i < rel.length(); i++) {
				JSONObject obj = (JSONObject) rel.get(i);
				Gson gson = new Gson();
				WatchAtchBean bean = gson.fromJson(obj.toString(),WatchAtchBean.class);
				addDevice(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addDevice(WatchAtchBean bean){
		ttime.setText("GPS定位："+bean.getCreate_time());
		taddress.setText("GPS定位："+bean.getGps());
		pname.setText(bean.getEntry_name_name());
		runit.setText(bean.getCompany_name());
		ptype.setText(bean.getProject_type());
		recordperson.setText(bean.getFill_in_user_name());
		classname.setText(bean.getClass_name());
		
		dutyfz.setText(bean.getLong_value());
		zhuduty.setText(bean.getPositive_class());
		fuduty.setText(bean.getDeputy_duty_officer());
		shixiperson.setText(bean.getIntern());
		
		group.setText(bean.getWork_group());
		dutytime.setText(bean.getDuty_time());
		dutystime.setText(bean.getDuty_start_time());
		dutyetime.setText(bean.getDuty_end_time());
//		String re = bean.getDuty_date();
//		if("0".equals(re)){
//			blackBtn.setChecked(true);
//		}
//		safetyselect.setText(bean.getEnvironmental_options());
	}
}
