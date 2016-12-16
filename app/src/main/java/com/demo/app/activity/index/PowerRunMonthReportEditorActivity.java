package com.demo.app.activity.index;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.RunMonthlyReportBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;
import com.google.gson.Gson;

public class PowerRunMonthReportEditorActivity extends BaseActivity {
	private CustomeEditText2 pname, runit, recordperson, aqyxts, bydzcz,
			byqzdl, zysbzgfh, qzxs, sbdqsyqh, byqfjt, qzdzhd, byzysb, fxcl;
	private Button saveBtn;
	private SharedPreferences sp;
	private String editData;
	private TextView ttime,taddress;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(PowerRunMonthReportEditorActivity.this, "编辑成功", Toast.LENGTH_LONG).show();
				PowerRunMonthReportEditorActivity.this.finish();
			} else if (msg.what == 2) {
				Toast.makeText(PowerRunMonthReportEditorActivity.this, "编辑失败", Toast.LENGTH_LONG).show();
			} else if (msg.what == 3) {
				Toast.makeText(PowerRunMonthReportEditorActivity.this, "有必填项未填", Toast.LENGTH_LONG).show();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_power_run_new_month_report_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "编辑运行月报表",PowerRunMonthReportShowActivity.class, true);
		TitleCommon.setGpsTitle(this);
		editData = getIntent().getStringExtra("editData");
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		Gson gson = new Gson();
		RunMonthlyReportBean bean = gson.fromJson(editData,RunMonthlyReportBean.class);
		final int id = bean.getId();
		
		pname = (CustomeEditText2)this.findViewById(R.id.pr_month_pname);
		runit = (CustomeEditText2)this.findViewById(R.id.pr_month_runit);
		recordperson = (CustomeEditText2)this.findViewById(R.id.pr_month_recordperson);
		aqyxts = (CustomeEditText2)this.findViewById(R.id.pr_month_aqyxts);
		bydzcz = (CustomeEditText2)this.findViewById(R.id.pr_month_bydzcz);
		byqzdl = (CustomeEditText2)this.findViewById(R.id.pr_month_byqzdl);
		zysbzgfh = (CustomeEditText2)this.findViewById(R.id.pr_month_zysbzgfh);
		qzxs = (CustomeEditText2)this.findViewById(R.id.pr_month_qzxs);
		sbdqsyqh = (CustomeEditText2)this.findViewById(R.id.pr_month_sbdqsyqh);
		byqfjt = (CustomeEditText2)this.findViewById(R.id.pr_month_byqfjt);
		qzdzhd = (CustomeEditText2)this.findViewById(R.id.pr_month_qzdzhd);
		byzysb = (CustomeEditText2)this.findViewById(R.id.pr_month_byzysb);
		fxcl = (CustomeEditText2)this.findViewById(R.id.pr_month_fxcl);
		ttime = (TextView) this.findViewById(R.id.title_gps_time);
		taddress = (TextView) this.findViewById(R.id.title_gps_address);
		
		ttime.setText(bean.getCreate_time());
		taddress.setText(bean.getGps());
		pname.setEditTextTag(bean.getEntry_name_id());
		pname.setText(bean.getEntry_name_name());
		runit.setText(bean.getCompany_name());
		recordperson.setText(bean.getFill_in_user_name());
		aqyxts.setText(bean.getSafe_running_days()+"");
		bydzcz.setText(bean.getReverse_operation()+"");
		byqzdl.setText(bean.getTotal_quantity_of_electricity()+"");
		zysbzgfh.setText(bean.getMaximum_load());
		qzxs.setText(bean.getNumber_of_visits()+"");
		sbdqsyqh.setText(bean.getSwitch_number()+"");
		
		byqfjt.setText(bean.getStall_number()+"");
		qzdzhd.setText(bean.getFixed_check()+"");
		byzysb.setText(bean.getStop_give_info()+"");
		fxcl.setText(bean.getExcetion_info()+"");
		
		saveBtn = (Button)this.findViewById(R.id.pr_month_saveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int pnameValue = pname.getEditTexTag();
				int runitValue = runit.getEditTexTag();
				int recordpersonValue = /*recordperson.getEditTexTag()*/sp.getInt("userId", -1);
				
				int aqyxtsValue = aqyxts.getIntText();
				int bydzczValue = bydzcz.getIntText();
				String byqzdlValue = byqzdl.getText();
				String zysbzgfhValue = zysbzgfh.getText();
				int qzxsValue = qzxs.getIntText();
				int sbdqsyqhValue = sbdqsyqh.getIntText();
				int byqfjtValue = byqfjt.getIntText();
				int qzdzhdValue = qzdzhd.getIntText();
				String byzysbValue = byzysb.getText();
				String excetion_info = fxcl.getText();
				
				if(pnameValue==-1/*||runitValue==-1*/||recordpersonValue==-1||aqyxtsValue==-1||bydzczValue==-1||qzxsValue==-1
						||sbdqsyqhValue==-1||byqfjtValue==-1||qzdzhdValue==-1||"".equals(byqzdlValue)||"".equals(zysbzgfhValue)
						||"".equals(byzysbValue)||"".equals(excetion_info)){
					handler.obtainMessage(3).sendToTarget();
				}else{
					RunMonthlyReportBean bean = new RunMonthlyReportBean();
					Calendar calendar = Calendar.getInstance();
					String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());
					bean.setId(id);
					bean.setCreate_time(time);
					bean.setGps(sp.getString("currentAddress", "定位失败"));
					bean.setEntry_name_id(pnameValue);
//					bean.setCompany_id(runitValue);
					bean.setFill_in_user_id(recordpersonValue);
					bean.setSafe_running_days(aqyxtsValue);
					bean.setReverse_operation(bydzczValue);
					bean.setTotal_quantity_of_electricity(byqzdlValue);
					bean.setMaximum_load(zysbzgfhValue);
					bean.setNumber_of_visits(qzxsValue);
					bean.setSwitch_number(sbdqsyqhValue);
					bean.setStall_number(byqfjtValue);
					bean.setFixed_check(qzdzhdValue);
					bean.setStop_give_info(byzysbValue);
					bean.setExcetion_info(excetion_info);
					NetworkData.getInstance().runMonthlyReportUpdate(new NetworkResponceFace() {

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
					}, bean);
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
				String runitValue = data.getExtras().getString("cname");
				if(runitValue!=null){
					runit.setText(runitValue);					
				}
				int id = data.getExtras().getInt("id");
				if (editTextId != -1) {
					CustomeEditText2 et = (CustomeEditText2) findViewById(editTextId);
					et.setText(text);
					et.setEditTextTag(id);
				}
				break;
			default:
				break;
			}
		}
	}

}
