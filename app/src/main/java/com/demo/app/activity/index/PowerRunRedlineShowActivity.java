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
import com.demo.app.bean.InfraredTemperatureBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeTextView;
import com.google.gson.Gson;

public class PowerRunRedlineShowActivity extends BaseActivity {

	private Button deviceJxRedlineBtn;
	private CustomeTextView pname, runit, xsr, weather, temperature/*,jgunit*/;
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
		setContentView(R.layout.index_power_run_redline_show_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "红外线测温记录卡",PowerRunJXTaskListActivity.class, true);
		TitleCommon.setGpsTitle(this);
		commonLayout = (LinearLayout)this.findViewById(R.id.common_pr_redline_show_linearlayout);
		oper_id = getIntent().getStringExtra("id");
		oper_type = getIntent().getStringExtra("type");
		ttime = (TextView) this.findViewById(R.id.title_gps_time);
		taddress = (TextView) this.findViewById(R.id.title_gps_address);
		
		pname = (CustomeTextView) this.findViewById(R.id.pr_redline_show_pname);
		weather = (CustomeTextView) this.findViewById(R.id.pr_redline_show_weather);
		runit = (CustomeTextView) this.findViewById(R.id.pr_redline_show_runit);
		xsr = (CustomeTextView) this.findViewById(R.id.pr_redline_show_xsr);
		temperature = (CustomeTextView) this.findViewById(R.id.pr_redline_show_temperature);
//		jgunit = (CustomeTextView) this.findViewById(R.id.pr_redline_show_jgunit);
		
		deviceJxRedlineBtn = (Button) this.findViewById(R.id.deviceJxRedlineBtn);
		deviceJxRedlineBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("data", showStr);
				i.putExtra("lid", lid);
				i.putExtra("oper_type", oper_type);
				i.setClass(PowerRunRedlineShowActivity.this,PowerRunJXHandleCardRedlineActivity.class);
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
				InfraredTemperatureBean bean = gson.fromJson(obj.toString(),InfraredTemperatureBean.class);
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
	public void addDevice(final InfraredTemperatureBean bean){
		lid = bean.getId()+"";
		View child =  getLayoutInflater().inflate(R.layout.common_redline_show_layout, null);
		ttime.setText("巡检时间："+bean.getCreate_time());
		taddress.setText("GPS定位："+bean.getGPS());
		pname.setValueText(bean.getEntry_name_name());
		runit.setValueText(bean.getCompany_name());
		xsr.setValueText(bean.getCreate_user_name());
		temperature.setValueText(bean.getAmbient_temperature());
		weather.setValueText(bean.getWeather());
//		jgunit.setValueText(bean.getInterval_unit());
		
		CustomeTextView dname = (CustomeTextView) child.findViewById(R.id.pr_redline_show_dname);
		dname.setValueText(bean.getDevice_name());
		
		CustomeTextView dnum = (CustomeTextView) child.findViewById(R.id.pr_redline_show_dnum);
		dnum.setValueText(bean.getInterval_unit());
		
		CustomeTextView ax = (CustomeTextView) child.findViewById(R.id.pr_rdeline_show_ax);
		ax.setValueText(bean.getA_temp()+"℃");
		CustomeTextView bx = (CustomeTextView) child.findViewById(R.id.pr_rdeline_show_bx);
		bx.setValueText(bean.getB_temp()+"℃");
		CustomeTextView cx = (CustomeTextView) child.findViewById(R.id.pr_rdeline_show_cx);
		cx.setValueText(bean.getC_temp()+"℃");
		
		CustomeTextView result = (CustomeTextView) child.findViewById(R.id.pr_rdeline_show_result);
		result.setValueText(bean.getCheck_result()==0?"异常":"正常");
		
		CustomeTextView qx = (CustomeTextView) child.findViewById(R.id.pr_rdeline_show_qx);
		qx.setValueText(bean.getDefect_type());
		CustomeTextView bz = (CustomeTextView) child.findViewById(R.id.pr_rdeline_show_bz);
		bz.setValueText(bean.getCheck_info());
		
		commonLayout.addView(child);
	}
}
