package com.demo.app.activity.index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.MaintenanceRecordBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeTextView;
import com.google.gson.Gson;

public class PowerFixDQJXCardUserInfoShowActivity extends BaseActivity {

	private Button deviceJxRedlineBtn;
	private CustomeTextView pname, runit, xsr;
	private String oper_id;
	private String oper_type;
	private String lid;
	private TextView ttime,taddress;
	private LinearLayout commonLayout;
	private String showStr = "";
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				 showData(msg.obj.toString());
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
		setContentView(R.layout.index_power_fix_dqjxcard_show_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "红外线测温记录卡",PowerRunJXTaskListActivity.class, true);
		TitleCommon.setGpsTitle(this);
		commonLayout = (LinearLayout)this.findViewById(R.id.common_pf_dqjx_show_linearlayout);
		oper_id = getIntent().getStringExtra("id");
		oper_type = getIntent().getStringExtra("type");
		ttime = (TextView) this.findViewById(R.id.title_gps_time);
		taddress = (TextView) this.findViewById(R.id.title_gps_address);
		
		pname = (CustomeTextView) this.findViewById(R.id.pf_dqjx_show_pname);
		runit = (CustomeTextView) this.findViewById(R.id.pf_dqjx_show_runit);
		xsr = (CustomeTextView) this.findViewById(R.id.pf_dqjx_show_jxperson);
		
		deviceJxRedlineBtn = (Button) this.findViewById(R.id.deviceJxDqjxBtn);
		deviceJxRedlineBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("data", showStr);
				i.putExtra("lid", lid);
				i.putExtra("oper_type", oper_type);
				i.setClass(PowerFixDQJXCardUserInfoShowActivity.this,PowerRunJXHandleCardRedlineActivity.class);
				startActivity(i);
			}
		});
		getData(oper_id);
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
	}

	public void getData(String id) {
		NetworkData.getInstance().maintenanceTaskCardBean(
				new NetworkResponceFace() {

					@Override
					public void callback(String result) {
						// TODO Auto-generated method stub
						JSONObject json;
						try {
							json = new JSONObject(result);
							showStr = json.get("result").toString();
							handler.obtainMessage(1, json.get("result").toString()).sendToTarget();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, id,oper_type);
	}
	public void addDevice(final MaintenanceRecordBean bean){
		lid = bean.getId()+"";
		View child =  getLayoutInflater().inflate(R.layout.common_dqjxcard_show_layout, null);
		ttime.setText("巡检时间："+bean.getWork_start_time());
		taddress.setText("GPS定位："+bean.getGPS());
		pname.setValueText(bean.getEntry_name_name());
		runit.setValueText(bean.getCompany_name());
		xsr.setValueText(bean.getM_personnel_name());
		
		CustomeTextView starttime = (CustomeTextView) child.findViewById(R.id.pf_dqjx_show_starttime);
		starttime.setValueText(bean.getWork_start_time());
		
		CustomeTextView worknum = (CustomeTextView) child.findViewById(R.id.pf_dqjx_show_worknum);
		worknum.setValueText(bean.getWork_number());
		CustomeTextView jxtype = (CustomeTextView) child.findViewById(R.id.pf_dqjx_show_jxtype);
		jxtype.setValueText(bean.getCheck_type()+"");
		CustomeTextView endtime = (CustomeTextView) child.findViewById(R.id.pf_dqjx_show_workendtime);
		endtime.setValueText(bean.getWork_end_time());
		
		CustomeTextView result = (CustomeTextView) child.findViewById(R.id.pf_dqjx_show_result);
		result.setValueText(bean.getMaintenance_result()==0?"异常":"正常");
		
		CustomeTextView workfzr = (CustomeTextView) child.findViewById(R.id.pf_dqjx_show_workfzr);
		workfzr.setValueText(bean.getPerson_in_charge_name());
//		CustomeTextView qx = (CustomeTextView) child.findViewById(R.id.pr_rdeline_show_qx);
//		qx.setValueText(bean.getDefect_type());
		CustomeTextView bz = (CustomeTextView) child.findViewById(R.id.pr_rdeline_show_bz);
		bz.setValueText(bean.getNote());
		
		commonLayout.addView(child);
	}
}
