package com.demo.app.activity.index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
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
import com.demo.app.bean.SafetyToolsBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeTextView;
import com.google.gson.Gson;

public class DeviceManagerAQToolTableShowActivity extends BaseActivity implements View.OnClickListener{

	private Button edit_dm_tables, delete_dm_tables;
	private CustomeTextView pname, runit, djperson;
	private String oper_id;
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
				Toast.makeText(DeviceManagerAQToolTableShowActivity.this, "删除成功！",Toast.LENGTH_SHORT).show();
				finish();
				break;
			case 3:
				Toast.makeText(DeviceManagerAQToolTableShowActivity.this, "删除失败",Toast.LENGTH_SHORT).show();
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
		setContentView(R.layout.index_device_manager_aqtooltable_show_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "安全工具表",DeviceManagerAQToolTableActivity.class, true);
		TitleCommon.setGpsTitle(this);
		commonLayout = (LinearLayout)this.findViewById(R.id.common_dm_aqtool_show_linearlayout);
		oper_id = getIntent().getStringExtra("id");
		ttime = (TextView) this.findViewById(R.id.title_gps_time);
		taddress = (TextView) this.findViewById(R.id.title_gps_address);
		
		pname = (CustomeTextView) this.findViewById(R.id.dm_aqtool_show_pname);
		djperson = (CustomeTextView) this.findViewById(R.id.dm_aqtool_show_djperson);
		runit = (CustomeTextView) this.findViewById(R.id.dm_aqtool_show_runit);
		
		edit_dm_tables = (Button) this.findViewById(R.id.edit_dm_tables);
		delete_dm_tables = (Button) this.findViewById(R.id.delete_dm_tables);
		edit_dm_tables.setOnClickListener(this);
		delete_dm_tables.setOnClickListener(this);
		
	}

	public void showData(String data) {
		try {
			JSONArray rel = new JSONArray(data);
			for (int i = 0; i < rel.length(); i++) {
				JSONObject obj = (JSONObject) rel.get(i);
				Gson gson = new Gson();
				SafetyToolsBean bean = gson.fromJson(obj.toString(),SafetyToolsBean.class);
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
		getData(Integer.parseInt(oper_id));
	}

	public void getData(int id) {
		commonLayout.removeAllViews();
		NetworkData.getInstance().safetyToolsBean(
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
				}, id);
	}
	public void addDevice(final SafetyToolsBean bean){
		View child =  getLayoutInflater().inflate(R.layout.common_aqtool_show_layout, null);
		ttime.setText("巡检时间："+bean.getCreate_time());
		taddress.setText("GPS定位："+bean.getGps());
		pname.setValueText(bean.getEntry_name_name());
		runit.setValueText(bean.getCompany_name());
		djperson.setValueText(bean.getFill_in_user_name());
		
		CustomeTextView dname = (CustomeTextView) child.findViewById(R.id.dm_aqtool_show_dname);
		dname.setValueText(bean.getDevice_name());
		
		CustomeTextView devicenum = (CustomeTextView) child.findViewById(R.id.dm_aqtool_show_devicenum);
		devicenum.setValueText(bean.getDevice_number());
		CustomeTextView tooldesc = (CustomeTextView) child.findViewById(R.id.dm_aqtool_show_tooldesc);
		tooldesc.setValueText(bean.getTool_name());
		CustomeTextView model = (CustomeTextView) child.findViewById(R.id.dm_aqtool_show_model);
		model.setValueText(bean.getModel());
		
		CustomeTextView dylevel = (CustomeTextView) child.findViewById(R.id.dm_aqtool_show_dylevel);
		dylevel.setValueText(bean.getVoltage_level_name());
		CustomeTextView slnum = (CustomeTextView) child.findViewById(R.id.dm_aqtool_show_slnum);
		slnum.setValueText(bean.getSize()+"");
		CustomeTextView sytime = (CustomeTextView) child.findViewById(R.id.dm_aqtool_show_sytime);
		sytime.setValueText(bean.getTest_time());
		CustomeTextView nextsytime = (CustomeTextView) child.findViewById(R.id.dm_aqtool_show_nextsytime);
		nextsytime.setValueText(bean.getNext_test());
		
		CustomeTextView result = (CustomeTextView) child.findViewById(R.id.dm_aqtool_show_result);
		result.setValueText(bean.getTest_result()==0?"异常":"正常");
		CustomeTextView bz = (CustomeTextView) child.findViewById(R.id.dm_aqtool_show_bz);
		bz.setValueText(bean.getNote());
		
		commonLayout.addView(child);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.edit_dm_tables:
			if(getIntent().getIntExtra("editAuth", 1)==0||getIntent().getIntExtra("editListAuth", 1)==0||getIntent().getIntExtra("editItemAuth", 1)==0){
				Toast.makeText(DeviceManagerAQToolTableShowActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
				return;
			}
			delete_dm_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			edit_dm_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			Intent i = new Intent();
			i.putExtra("editData", editString);
			i.setClass(DeviceManagerAQToolTableShowActivity.this, DeviceManagerAQToolEditorTableActivity.class);
			startActivity(i);
//			TwoTicketOperShowActivity.this.finish();
			break;
		case R.id.delete_dm_tables:
			if(getIntent().getIntExtra("editAuth", 1)==0||getIntent().getIntExtra("editListAuth", 1)==0||getIntent().getIntExtra("editItemAuth", 1)==0){
				Toast.makeText(DeviceManagerAQToolTableShowActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
				return;
			}
			delete_dm_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			edit_dm_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			final AlertDialog alertDialog = new AlertDialog.Builder(
					DeviceManagerAQToolTableShowActivity.this).create();
			alertDialog.show();
			Window window = alertDialog.getWindow();
			window.setContentView(R.layout.oper_tickets_delete_alert_layout);
			Button okBtn = (Button) window.findViewById(R.id.oper_ticket_delete_ok);
			Button cancelBtn = (Button) window.findViewById(R.id.oper_ticket_delete_cancel);
			okBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					NetworkData.getInstance().safetyToolsDelete(
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
