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
import com.demo.app.bean.SafetyTrainingBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeTextView;
import com.google.gson.Gson;

public class SafetyCultureCheckShowActivity extends BaseActivity {

	private Button deviceJxBtn, xscontentBtn;
	private CustomeTextView pname, ptype, runit, xsr,/*safetyselect,*/ dname, dnum, result, log;
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
		setContentView(R.layout.index_safety_culture_check_xsk_show_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "安全检查巡视卡",PowerRunJXTaskListActivity.class, true);
		TitleCommon.setGpsTitle(this);
		commonLayout = (LinearLayout)this.findViewById(R.id.common_sc_check_xsk_show_linearlayout);
		oper_id = getIntent().getStringExtra("id");
		oper_type = getIntent().getStringExtra("type");
		ttime = (TextView) this.findViewById(R.id.title_gps_time);
		taddress = (TextView) this.findViewById(R.id.title_gps_address);
		
		pname = (CustomeTextView) this.findViewById(R.id.sc_check_show_pname);
		ptype = (CustomeTextView) this.findViewById(R.id.sc_check_show_ptype);
		runit = (CustomeTextView) this.findViewById(R.id.sc_check_show_runit);
		xsr = (CustomeTextView) this.findViewById(R.id.sc_check_show_xsr);
//		safetyselect = (CustomeTextView) this.findViewById(R.id.sc_check_show_safetyselect);

		deviceJxBtn = (Button) this.findViewById(R.id.deviceJxScBtn);
		deviceJxBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("data", showStr);
				i.putExtra("oper_id", oper_id);
				i.putExtra("oper_type", oper_type);
				i.putExtra("lid", lid);
				i.setClass(SafetyCultureCheckShowActivity.this,SafetyCultureCheckHandleCardActivity.class);
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
				SafetyTrainingBean bean = gson.fromJson(obj.toString(),SafetyTrainingBean.class);
				addDevice(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*pname.setValueText(bean.getEntry_name_name());
		runit.setValueText(bean.getCompany_name());
		rperson.setValueText(bean.getCreate_user_name());
		num.setValueText(bean.getSerial_number());
		opernum.setValueText(bean.getOperation_number());
		operperson.setValueText(bean.getOperation_user_name());
		operstart.setValueText(bean.getOper_start_time());
		operend.setValueText(bean.getOper_end_time());
		int ok = bean.getQualified();
		isok.setValueText(ok == 1 ? "合格" : "不合格");*/

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
	public void addDevice(final SafetyTrainingBean bean){
		lid = bean.getId()+"";
		View child =  getLayoutInflater().inflate(R.layout.common_xsk_show_layout, null);
		ttime.setText("巡检时间："+bean.getCreate_time());
		taddress.setText("GPS定位："+bean.getGPS());
		pname.setValueText(bean.getEntry_name_name());
		runit.setValueText(bean.getCompany_name());
		ptype.setValueText(bean.getProject_type());
		xsr.setValueText(bean.getFill_in_user_name());
//		safetyselect.setValueText(bean.getSecurity_options());
		dname = (CustomeTextView) child.findViewById(R.id.pr_xsk_show_dname);
		dname.setValueText(bean.getDevice_name());
		dnum = (CustomeTextView) child.findViewById(R.id.pr_xsk_show_dnum);
		dnum.setValueText(bean.getDevice_number());
		result = (CustomeTextView) child.findViewById(R.id.pr_xsk_show_result);
		result.setValueText(bean.getCheck_result()==0?"异常":"正常");
		CustomeTextView qx = (CustomeTextView) child.findViewById(R.id.pr_xsk_show_qx);
		qx.setVisibility(View.GONE);
//		qx.setValueText(bean.getDefect_type());
		
		log = (CustomeTextView) child.findViewById(R.id.pr_xsk_show_log);
		log.setValueText(bean.getNote());
		xscontentBtn = (Button) child.findViewById(R.id.pr_xsk_show_xscontent);
		xscontentBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(bean.getDevice_id()+"---");
				Intent i = new Intent();
				i.putExtra("did", bean.getId()+"");
				i.putExtra("type", oper_type+"");
				i.setClass(SafetyCultureCheckShowActivity.this, DXSBXSKContentShowListActivity.class);
				startActivity(i);
			}
		});
		commonLayout.addView(child);
	}
}
