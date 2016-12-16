package com.demo.app.activity.user;

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
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.JobPlanBean;
import com.demo.app.bean.OptTicketBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeTextView;
import com.google.gson.Gson;

public class PersonWorkLogShowActivity extends BaseActivity implements
		OnClickListener {
	private Button edit_log_tables, delete_log_tables;
	private CustomeTextView start, end, title, content;
	private int sh_id;
	private String editString = "";
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				Gson gson = new Gson();
				JobPlanBean bean = gson.fromJson(msg.obj.toString(),JobPlanBean.class);
				showData(bean);
				break;
			case 2:
				Toast.makeText(PersonWorkLogShowActivity.this, "删除成功！",Toast.LENGTH_SHORT).show();
				finish();
				break;
			case 3:
				Toast.makeText(PersonWorkLogShowActivity.this, "删除失败",Toast.LENGTH_SHORT).show();
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
		setContentView(R.layout.person_work_log_show_layout);
		TitleCommon.setTitle(this, null, "工作日志", PersonWorkScheduleActivity.class,true);
		sh_id = Integer.parseInt(getIntent().getStringExtra("id"));
//		getData(oper_id);
		edit_log_tables = (Button) this.findViewById(R.id.edit_log_tables);
		delete_log_tables = (Button) this.findViewById(R.id.delete_log_tables);
		edit_log_tables.setOnClickListener(this);
		delete_log_tables.setOnClickListener(this);
		start = (CustomeTextView)this.findViewById(R.id.work_log_show_start);
		end = (CustomeTextView)this.findViewById(R.id.work_log_show_end);
		title = (CustomeTextView)this.findViewById(R.id.work_log_show_title);
		content = (CustomeTextView)this.findViewById(R.id.work_log_show_content);
		

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getData(sh_id);
	}
	public void showData(JobPlanBean bean) {
		start.setValueText(bean.getStart_time());
		end.setValueText(bean.getEnd_time());
		title.setValueText(bean.getTitle());
		content.setValueText(bean.getContent());
		
	}

	public void getData(int id) {
		NetworkData.getInstance().jobLogBean(new NetworkResponceFace() {

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
		case R.id.edit_log_tables:
			delete_log_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			edit_log_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			Intent i = new Intent();
			i.putExtra("editData", editString);
			i.setClass(PersonWorkLogShowActivity.this, PersonWorkLogEditorActivity.class);
			startActivity(i);
//			TwoTicketOperShowActivity.this.finish();
			break;
		case R.id.delete_log_tables:
			delete_log_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			edit_log_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			final AlertDialog alertDialog = new AlertDialog.Builder(
					PersonWorkLogShowActivity.this).create();
			alertDialog.show();
			Window window = alertDialog.getWindow();
			window.setContentView(R.layout.oper_tickets_delete_alert_layout);
			Button okBtn = (Button) window.findViewById(R.id.oper_ticket_delete_ok);
			Button cancelBtn = (Button) window.findViewById(R.id.oper_ticket_delete_cancel);
			okBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					NetworkData.getInstance().jobLogDelete(
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
							},sh_id + "");
					alertDialog.dismiss();
				}
			});
			cancelBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					alertDialog.dismiss();
					delete_log_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
					edit_log_tables.setBackgroundResource(R.drawable.button_franchise_shape);
				}
			});
			break;
		default:
			break;
		}
	}
}
