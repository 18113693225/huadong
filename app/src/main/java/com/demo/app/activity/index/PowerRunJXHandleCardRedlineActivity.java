package com.demo.app.activity.index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.user.PersonWorkManagerActivity;
import com.demo.app.bean.InfraredTemperatureBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;
import com.demo.app.view.CustomeTextView;
import com.google.gson.Gson;

public class PowerRunJXHandleCardRedlineActivity extends BaseActivity {
	private CustomeEditText2 pname,runit,jxperson,weather,temperature/*,jgunit*/;
	private Button saveBtn;
	private TextView ttime,taddress;
	private LinearLayout commonLayout;
	private EditText jxlog;
	private String oper_id;
	private String oper_type;
	private String lid;
	private String pwm;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				showData(msg.obj.toString());
			}else if (msg.what == 1) {
				Toast.makeText(PowerRunJXHandleCardRedlineActivity.this, "保存成功", Toast.LENGTH_LONG).show();
				PowerRunJXHandleCardRedlineActivity.this.finish();
			} else if (msg.what == 2) {
				Toast.makeText(PowerRunJXHandleCardRedlineActivity.this, "保存失败", Toast.LENGTH_LONG).show();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_power_run_redline_jx_layout);
		//判断从哪里进入的
		pwm = getIntent().getStringExtra("type");
		if(pwm!=null){
			TitleCommon.setTitle(this, null, "红外线测温记录卡",PersonWorkManagerActivity.class, true);	
		}else{
			TitleCommon.setTitle(this, null, "红外线测温记录卡",PowerRunRedlineShowActivity.class, true);
		}
		TitleCommon.setGpsTitle(this);
		oper_id = getIntent().getStringExtra("oper_id");
		oper_type = getIntent().getStringExtra("oper_type");
		lid = getIntent().getStringExtra("lid");
		commonLayout = (LinearLayout)this.findViewById(R.id.pr_rdeline_jx_commonLayout);
		pname = (CustomeEditText2)this.findViewById(R.id.pr_rdeline_jx_pname);
		runit = (CustomeEditText2)this.findViewById(R.id.pr_rdeline_jx_runit);
		jxperson = (CustomeEditText2)this.findViewById(R.id.pr_rdeline_jx_weather);
		temperature = (CustomeEditText2)this.findViewById(R.id.pr_rdeline_jx_temperature);
		weather = (CustomeEditText2)this.findViewById(R.id.pr_rdeline_jx_recordperson);
//		jgunit = (CustomeEditText2)this.findViewById(R.id.pr_rdeline_jx_jgunit);
		ttime = (TextView) this.findViewById(R.id.title_gps_time);
		taddress = (TextView) this.findViewById(R.id.title_gps_address);
		jxlog = (EditText)this.findViewById(R.id.pr_rdeline_jx_jxlog);
		
		saveBtn = (Button)this.findViewById(R.id.pr_rdeline_jx_saveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String jxlogValue = jxlog.getText().toString();
				Gson gson = new Gson();
				NetworkData.getInstance().maintenanceTaskCardResult(new NetworkResponceFace() {

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
					
				}, lid, jxlogValue, oper_type,gson.toJson(Constents.jxcontentList));
			}
		});
		//个人中心工作管理
		if(getIntent().getStringExtra("data")==null){
			getData(oper_id);
			jxlog.setVisibility(View.GONE);
			saveBtn.setVisibility(View.GONE);
			findViewById(R.id.pr_rdeline_jx_end).setVisibility(View.INVISIBLE);
		}else{//电力运行检修任务
			showData(getIntent().getStringExtra("data"));			
		}
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
							handler.obtainMessage(0, json.get("result").toString()).sendToTarget();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, id,oper_type);
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
	public void addDevice(final InfraredTemperatureBean bean){
		View child =  getLayoutInflater().inflate(R.layout.common_prredline_jx_layout, null);
		ttime.setText("巡检时间："+bean.getCreate_time());
		taddress.setText("GPS定位："+bean.getGPS());
		pname.setText(bean.getEntry_name_name());
		runit.setText(bean.getCompany_name());
		weather.setText(bean.getWeather());
		temperature.setText(bean.getAmbient_temperature());
		jxperson.setText(bean.getCreate_user_name());
//		jgunit.setText(bean.getInterval_unit());
		
		CustomeEditText2 dname = (CustomeEditText2)child.findViewById(R.id.pr_rdeline_jx_dname);
		dname.setText(bean.getDevice_name());
		CustomeEditText2 dnum = (CustomeEditText2)child.findViewById(R.id.pr_rdeline_jx_dnum);
		dnum.setText(bean.getInterval_unit());
		CustomeEditText2 ax = (CustomeEditText2)child.findViewById(R.id.pr_rdeline_jx_ax);
		ax.setText(bean.getA_temp()+"");
		CustomeEditText2 bx = (CustomeEditText2)child.findViewById(R.id.pr_rdeline_jx_bx);
		bx.setText(bean.getA_temp()+"");
		CustomeEditText2 cx = (CustomeEditText2)child.findViewById(R.id.pr_rdeline_jx_cx);
		cx.setText(bean.getA_temp()+"");
		
		RadioButton result = (RadioButton) child.findViewById(R.id.pr_rdeline_jx_isok);
		if(bean.getCheck_result()==0&&pwm!=null){
			result.setChecked(false);
		}else{
			result.setChecked(true);
		}
		CustomeTextView qx = (CustomeTextView)child.findViewById(R.id.pr_rdeline_jx_qx);
		qx.setValueText(bean.getDefect_type());
		commonLayout.addView(child);
	}
}
