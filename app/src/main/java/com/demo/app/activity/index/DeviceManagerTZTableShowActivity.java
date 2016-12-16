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
import com.demo.app.bean.EquipmentLedgerBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeTextView;
import com.google.gson.Gson;

public class DeviceManagerTZTableShowActivity extends BaseActivity implements View.OnClickListener{

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
				Toast.makeText(DeviceManagerTZTableShowActivity.this, "删除成功！",Toast.LENGTH_SHORT).show();
				finish();
				break;
			case 3:
				Toast.makeText(DeviceManagerTZTableShowActivity.this, "删除失败",Toast.LENGTH_SHORT).show();
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
		setContentView(R.layout.index_device_manager_tztable_show_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "设备台账登记表",DeviceManagerTZTableActivity.class, true);
		TitleCommon.setGpsTitle(this);
		commonLayout = (LinearLayout)this.findViewById(R.id.common_dm_tz_show_linearlayout);
		oper_id = getIntent().getStringExtra("id");
		ttime = (TextView) this.findViewById(R.id.title_gps_time);
		taddress = (TextView) this.findViewById(R.id.title_gps_address);
		
		pname = (CustomeTextView) this.findViewById(R.id.dm_tz_show_pname);
		djperson = (CustomeTextView) this.findViewById(R.id.dm_tz_show_djperson);
		runit = (CustomeTextView) this.findViewById(R.id.dm_tz_show_runit);
		
		edit_dm_tables = (Button) this.findViewById(R.id.edit_dm_tz_tables);
		delete_dm_tables = (Button) this.findViewById(R.id.delete_dm_tz_tables);
		edit_dm_tables.setOnClickListener(this);
		delete_dm_tables.setOnClickListener(this);
		
	}

	public void showData(String data) {
		try {
			JSONArray rel = new JSONArray(data);
			for (int i = 0; i < rel.length(); i++) {
				JSONObject obj = (JSONObject) rel.get(i);
				Gson gson = new Gson();
				EquipmentLedgerBean bean = gson.fromJson(obj.toString(),EquipmentLedgerBean.class);
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
		NetworkData.getInstance().equipmentLedgerBean(
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
	public void addDevice(final EquipmentLedgerBean bean){
		View child =  getLayoutInflater().inflate(R.layout.common_tz_show_layout, null);
		ttime.setText("巡检时间："+bean.getCreate_time());
		taddress.setText("GPS定位："+bean.getGps());
		pname.setValueText(bean.getEntry_name_name());
		runit.setValueText(bean.getCompany_name());
		djperson.setValueText(bean.getFill_in_user_name());
		
//		CustomeTextView dname = (CustomeTextView) child.findViewById(R.id.dm_bpbj_show_dname);
//		dname.setValueText(bean.getDevice_name());
		
		CustomeTextView zichangnum = (CustomeTextView) child.findViewById(R.id.dm_tz_show_zichangnum);
		zichangnum.setValueText(bean.getAsset_number());
		CustomeTextView zichangname = (CustomeTextView) child.findViewById(R.id.dm_tz_show_zichangname);
		zichangname.setValueText(bean.getAsset_name());
		CustomeTextView guigemodel = (CustomeTextView) child.findViewById(R.id.dm_tz_show_guigemodel);
		guigemodel.setValueText(bean.getSpecification_model());
		
		CustomeTextView scfactory = (CustomeTextView) child.findViewById(R.id.dm_tz_show_scfactory);
		scfactory.setValueText(bean.getManufacturer());
		CustomeTextView sctime = (CustomeTextView) child.findViewById(R.id.dm_tz_show_sctime);
		sctime.setValueText(bean.getProduction_date());
		CustomeTextView slnum = (CustomeTextView) child.findViewById(R.id.dm_tz_show_slnum);
		slnum.setValueText(bean.getSize()+"");
		CustomeTextView trytime = (CustomeTextView) child.findViewById(R.id.dm_tz_show_trytime);
		trytime.setValueText(bean.getPre_test_time());
		CustomeTextView bz = (CustomeTextView) child.findViewById(R.id.dm_tz_show_bz);
		bz.setValueText(bean.getNote());
		
		commonLayout.addView(child);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.edit_dm_tz_tables:
			if(getIntent().getIntExtra("editAuth", 1)==0||getIntent().getIntExtra("editListAuth", 1)==0||getIntent().getIntExtra("editItemAuth", 1)==0){
				Toast.makeText(DeviceManagerTZTableShowActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
				return;
			}
			delete_dm_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			edit_dm_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			Intent i = new Intent();
			i.putExtra("editData", editString);
			i.setClass(DeviceManagerTZTableShowActivity.this, DeviceManagerTZEditorTableActivity.class);
			startActivity(i);
//			TwoTicketOperShowActivity.this.finish();
			break;
		case R.id.delete_dm_tz_tables:
			if(getIntent().getIntExtra("editAuth", 1)==0||getIntent().getIntExtra("editListAuth", 1)==0||getIntent().getIntExtra("editItemAuth", 1)==0){
				Toast.makeText(DeviceManagerTZTableShowActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
				return;
			}
			delete_dm_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			edit_dm_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			final AlertDialog alertDialog = new AlertDialog.Builder(
					DeviceManagerTZTableShowActivity.this).create();
			alertDialog.show();
			Window window = alertDialog.getWindow();
			window.setContentView(R.layout.oper_tickets_delete_alert_layout);
			Button okBtn = (Button) window.findViewById(R.id.oper_ticket_delete_ok);
			Button cancelBtn = (Button) window.findViewById(R.id.oper_ticket_delete_cancel);
			okBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					NetworkData.getInstance().equipmentLedgerDelete(
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
