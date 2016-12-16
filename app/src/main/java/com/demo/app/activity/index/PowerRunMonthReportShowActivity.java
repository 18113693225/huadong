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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.RunMonthlyReportBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeTextView;
import com.google.gson.Gson;

public class PowerRunMonthReportShowActivity extends BaseActivity implements
		OnClickListener {
	private Button edit_pr_tables, delete_pr_tables;
	private CustomeTextView pname, runit, rperson, aqyxts, bydzcz, byqzdl,
	zysbzgfh, qzxs, sbdqsyqh,byqfjt,qzdzhd,byzysb,fxcl;
	private int oper_id;
	private TextView ttime,taddress;
	private String editString = "";
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				Gson gson = new Gson();
				RunMonthlyReportBean bean = gson.fromJson(msg.obj.toString(),RunMonthlyReportBean.class);
				showData(bean);
				break;
			case 2:
				Toast.makeText(PowerRunMonthReportShowActivity.this, "删除成功！",Toast.LENGTH_SHORT).show();
				finish();
				break;
			case 3:
				Toast.makeText(PowerRunMonthReportShowActivity.this, "删除失败",Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_power_run_month_report_show_layout);
		TitleCommon.setTitle(this, null, "运行月报表", PowerRunMonthReportActivity.class,
				true);
		TitleCommon.setGpsTitle(this);
		oper_id = Integer.parseInt(getIntent().getStringExtra("id"));
//		getData(oper_id);
		edit_pr_tables = (Button) this.findViewById(R.id.edit_pr_tables);
		delete_pr_tables = (Button) this.findViewById(R.id.delete_pr_tables);
		edit_pr_tables.setOnClickListener(this);
		delete_pr_tables.setOnClickListener(this);
		pname = (CustomeTextView)this.findViewById(R.id.pr_month_show_pname);
		runit = (CustomeTextView)this.findViewById(R.id.pr_month_show_runit);
		rperson = (CustomeTextView)this.findViewById(R.id.pr_month_show_rperson);
		aqyxts = (CustomeTextView)this.findViewById(R.id.pr_month_show_aqyxts);
		bydzcz = (CustomeTextView)this.findViewById(R.id.pr_month_show_bydzcz);
		byqzdl = (CustomeTextView)this.findViewById(R.id.pr_month_show_byqzdl);
		zysbzgfh = (CustomeTextView)this.findViewById(R.id.pr_month_show_zysbzgfh);
		qzxs = (CustomeTextView)this.findViewById(R.id.pr_month_show_qzxs);
		sbdqsyqh = (CustomeTextView)this.findViewById(R.id.pr_month_show_sbdqsyqh);
		
		byqfjt = (CustomeTextView)this.findViewById(R.id.pr_month_show_byqfjt);
		qzdzhd = (CustomeTextView)this.findViewById(R.id.pr_month_show_qzdzhd);
		byzysb = (CustomeTextView)this.findViewById(R.id.pr_month_show_byzysb);
		fxcl = (CustomeTextView)this.findViewById(R.id.pr_month_show_fxcl);

		ttime = (TextView) this.findViewById(R.id.title_gps_time);
		taddress = (TextView) this.findViewById(R.id.title_gps_address);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getData(oper_id);
	}
	public void showData(RunMonthlyReportBean bean) {
		ttime.setText(bean.getCreate_time());
		taddress.setText(bean.getGps());
		pname.setValueText(bean.getEntry_name_name());
		runit.setValueText(bean.getCompany_name());
		rperson.setValueText(bean.getFill_in_user_name());
		aqyxts.setValueText(bean.getSafe_running_days()+" 天");
		bydzcz.setValueText(bean.getReverse_operation()+" 次");
		byqzdl.setValueText(bean.getTotal_quantity_of_electricity()+" kW/h");
		zysbzgfh.setValueText(bean.getMaximum_load());
		qzxs.setValueText(bean.getNumber_of_visits()+" 次");
		sbdqsyqh.setValueText(bean.getSwitch_number()+" 次");
		
		byqfjt.setValueText(bean.getStall_number()+" 次");
		qzdzhd.setValueText(bean.getFixed_check()+" 次");
		byzysb.setValueText(bean.getStop_give_info()+"");
		fxcl.setValueText(bean.getExcetion_info()+"");
		
	}

	public void getData(int id) {
		NetworkData.getInstance().runMonthlyReportBean(new NetworkResponceFace() {

			@Override
			public void callback(String result) {
				// TODO Auto-generated method stub
				JSONObject json;
				try {
					json = new JSONObject(result);
					JSONArray rel = new JSONArray(json.get("result").toString());
					JSONObject obj = (JSONObject) rel.get(0);
					editString = obj.toString();
					handler.obtainMessage(1, obj.toString()).sendToTarget();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, id);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.edit_pr_tables:
			if(getIntent().getIntExtra("editAuth", 1)==0||getIntent().getIntExtra("editListAuth", 1)==0||getIntent().getIntExtra("editItemAuth", 1)==0){
				Toast.makeText(PowerRunMonthReportShowActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
				return;
			}
			delete_pr_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			edit_pr_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			Intent i = new Intent();
			i.putExtra("editData", editString);
			i.setClass(PowerRunMonthReportShowActivity.this, PowerRunMonthReportEditorActivity.class);
			startActivity(i);
//			TwoTicketOperShowActivity.this.finish();
			break;
		case R.id.delete_pr_tables:
			if(getIntent().getIntExtra("editAuth", 1)==0||getIntent().getIntExtra("editListAuth", 1)==0||getIntent().getIntExtra("editItemAuth", 1)==0){
				Toast.makeText(PowerRunMonthReportShowActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
				return;
			}
			delete_pr_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			edit_pr_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			final AlertDialog alertDialog = new AlertDialog.Builder(
					PowerRunMonthReportShowActivity.this).create();
			alertDialog.show();
			Window window = alertDialog.getWindow();
			window.setContentView(R.layout.oper_tickets_delete_alert_layout);
			Button okBtn = (Button) window.findViewById(R.id.oper_ticket_delete_ok);
			Button cancelBtn = (Button) window.findViewById(R.id.oper_ticket_delete_cancel);
			okBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					NetworkData.getInstance().runMonthlyReportDelete(
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
							},oper_id + "");
					alertDialog.dismiss();
				}
			});
			cancelBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					alertDialog.dismiss();
					delete_pr_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
					edit_pr_tables.setBackgroundResource(R.drawable.button_franchise_shape);
				}
			});
			break;
		default:
			break;
		}
	}
}
