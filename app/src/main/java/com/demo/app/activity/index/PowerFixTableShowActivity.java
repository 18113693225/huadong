package com.demo.app.activity.index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.MaintenanceRecordBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeTextView;
import com.google.gson.Gson;

public class PowerFixTableShowActivity extends BaseActivity implements View.OnClickListener{

	private Button edit_dm_tables, delete_dm_tables;
	private CustomeTextView pname, runit, djperson;
	private String oper_id;
	private String p_id;
	private String m_personnel_id ;
	private String create_time;
	private TextView ttime,taddress;
	private LinearLayout commonLayout;
	private String editString = "";
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				 showData(msg.obj.toString());
				break;
			case 2:
				Toast.makeText(PowerFixTableShowActivity.this, "删除成功！",Toast.LENGTH_SHORT).show();
				finish();
				break;
			case 3:
				Toast.makeText(PowerFixTableShowActivity.this, "删除失败",Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_power_fix_dqjxtable_show_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "电气检修记录卡",PowerFixTableActivity.class, true);
		TitleCommon.setGpsTitle(this);
		commonLayout = (LinearLayout)this.findViewById(R.id.common_pf_dqjx_show_linearlayout);
		oper_id = getIntent().getStringExtra("id");
		p_id = getIntent().getStringExtra("p_id");
		create_time = getIntent().getStringExtra("create_time");
		m_personnel_id = getIntent().getStringExtra("m_personnel_id");
		ttime = (TextView) this.findViewById(R.id.title_gps_time);
		taddress = (TextView) this.findViewById(R.id.title_gps_address);
		
		pname = (CustomeTextView) this.findViewById(R.id.pf_dqjx_show_pname);
		djperson = (CustomeTextView) this.findViewById(R.id.pf_dqjx_show_djperson);
		runit = (CustomeTextView) this.findViewById(R.id.pf_dqjx_show_runit);
		
		edit_dm_tables = (Button) this.findViewById(R.id.edit_pf_dqjx_tables);
		delete_dm_tables = (Button) this.findViewById(R.id.delete_pf_dqjx_tables);
		edit_dm_tables.setOnClickListener(this);
		delete_dm_tables.setOnClickListener(this);
		
	}

	public void showData(String data) {
		try {
			JSONArray rel = new JSONArray(data);
			for (int i = 0; i < rel.length(); i++) {
				JSONObject obj = (JSONObject) rel.get(i);
				Gson gson = new Gson();
				MaintenanceRecordBean bean = gson.fromJson(obj.toString(),MaintenanceRecordBean.class);
				addDevice(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getData(Integer.parseInt(p_id));
	}

	public void getData(int id) {
		commonLayout.removeAllViews();
		SharedPreferences sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		int uid = sp.getInt("userId", -1);
		NetworkData.getInstance().maintenanceRecordBean(
				new NetworkResponceFace() {

					@Override
					public void callback(String result) {
						// TODO Auto-generated method stub
						JSONObject json;
						try {
							json = new JSONObject(result);
							editString = json.get("result").toString();
							handler.obtainMessage(1, json.get("result").toString()).sendToTarget();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, id,create_time,Integer.parseInt(m_personnel_id));
	}
	public void addDevice(final MaintenanceRecordBean bean){
		View child =  getLayoutInflater().inflate(R.layout.common_dqjxcard_show_layout, null);
		ttime.setText("巡检时间："+bean.getWork_start_time());
		taddress.setText("GPS定位："+bean.getGPS());
		pname.setValueText(bean.getEntry_name_name());
		runit.setValueText(bean.getCompany_name());
		djperson.setValueText(bean.getM_personnel_name());
		
		CustomeTextView starttime = (CustomeTextView) child.findViewById(R.id.pf_dqjx_show_starttime);
		starttime.setValueText(bean.getWork_start_time());
		
		CustomeTextView worknum = (CustomeTextView) child.findViewById(R.id.pf_dqjx_show_worknum);
		worknum.setValueText(bean.getWork_number());
		CustomeTextView jxtype = (CustomeTextView) child.findViewById(R.id.pf_dqjx_show_jxtype);
		if (bean.getCheck_type()==1) {
			
			jxtype.setValueText("计划检修");
			
		}else if (bean.getCheck_type()==2){
			
			jxtype.setValueText("应急检修");
		}
//		jxtype.setValueText(bean.getCheck_type()+"");
		CustomeTextView endtime = (CustomeTextView) child.findViewById(R.id.pf_dqjx_show_workendtime);
		endtime.setValueText(bean.getWork_end_time());
		
//		CustomeTextView result = (CustomeTextView) child.findViewById(R.id.pf_dqjx_show_result);
//		result.setValueText(bean.getMaintenance_result()==0?"异常":"正常");
		
		CustomeTextView workfzr = (CustomeTextView) child.findViewById(R.id.pf_dqjx_show_workfzr);
		workfzr.setValueText(bean.getPerson_in_charge_name());
//		CustomeTextView qx = (CustomeTextView) child.findViewById(R.id.pr_rdeline_show_qx);
//		qx.setValueText(bean.getDefect_type());
		CustomeTextView bz = (CustomeTextView) child.findViewById(R.id.pf_dqjx_show_bz);
		bz.setValueText(bean.getNote());
		
		commonLayout.addView(child);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.edit_pf_dqjx_tables:
			if(getIntent().getIntExtra("editAuth", 1)==0||getIntent().getIntExtra("editListAuth", 1)==0||getIntent().getIntExtra("editItemAuth", 1)==0){
				Toast.makeText(PowerFixTableShowActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
				return;
			}
			delete_dm_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			edit_dm_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			Intent i = new Intent();
			i.putExtra("editData", editString);
			i.setClass(PowerFixTableShowActivity.this, DeviceManagerAQToolEditorTableActivity.class);
			startActivity(i);
//			TwoTicketOperShowActivity.this.finish();
			break;
		case R.id.delete_pf_dqjx_tables:
			if(getIntent().getIntExtra("editAuth", 1)==0||getIntent().getIntExtra("editListAuth", 1)==0||getIntent().getIntExtra("editItemAuth", 1)==0){
				Toast.makeText(PowerFixTableShowActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
				return;
			}
			delete_dm_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			edit_dm_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			final AlertDialog alertDialog = new AlertDialog.Builder(
					PowerFixTableShowActivity.this).create();
			alertDialog.show();
			Window window = alertDialog.getWindow();
			window.setContentView(R.layout.oper_tickets_delete_alert_layout);
			Button okBtn = (Button) window.findViewById(R.id.oper_ticket_delete_ok);
			Button cancelBtn = (Button) window.findViewById(R.id.oper_ticket_delete_cancel);
			okBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					NetworkData.getInstance().maintenanceRecordDelete(
							new NetworkResponceFace() {

								@Override
								public void callback(String result) {
									// TODO Auto-generated method stub
									JSONObject json;
									try {
										json = new JSONObject(result);
										String status = json.getString("status");
										if ("success".equals(status)) {
											handler.obtainMessage(2).sendToTarget();
										} else {
											handler.obtainMessage(3).sendToTarget();
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							},oper_id);
					alertDialog.dismiss();
				}
			});
			cancelBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					alertDialog.dismiss();
					delete_dm_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
					edit_dm_tables.setBackgroundResource(R.drawable.button_franchise_shape);
				}
			});
			break;
		default:
			break;
		}
	}
}
