package com.demo.app.activity.index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.WorkContackBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeTextView;
import com.google.gson.Gson;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
//客户经理工作连接票展示
public class CustomerManagerWorkConnectTicketShowActivity extends BaseActivity implements
		OnClickListener {
	private Button edit_oper_tables, delete_oper_tables;
	private CustomeTextView pname,rperson, time, noticeperson, content;
	private CheckBox checkbox;
	private int oper_id;
	private String editString = "";
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				Gson gson = new Gson();
				WorkContackBean bean = gson.fromJson(msg.obj.toString(),WorkContackBean.class);
				showData(bean);
				break;
			case 2:
				Toast.makeText(CustomerManagerWorkConnectTicketShowActivity.this, "删除成功！",Toast.LENGTH_SHORT).show();
				finish();
				break;
			case 3:
				Toast.makeText(CustomerManagerWorkConnectTicketShowActivity.this, "删除失败",Toast.LENGTH_SHORT).show();
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
		setContentView(R.layout.index_customer_manager_workconnectticket_show_layout);
		TitleCommon.setTitle(this, null, "工作联系单", CustomerManageWorkConnectListActivity.class,
				true);
		TitleCommon.setGpsTitle(this);
		oper_id = Integer.parseInt(getIntent().getStringExtra("id"));
//		getData(oper_id);
		edit_oper_tables = (Button) this.findViewById(R.id.edit_cm_workconn_tables);
		delete_oper_tables = (Button) this.findViewById(R.id.delete_cm_workconn_tables);
		edit_oper_tables.setOnClickListener(this);
		delete_oper_tables.setOnClickListener(this);
		pname = (CustomeTextView)this.findViewById(R.id.cm_workconn_show_pname);
		rperson = (CustomeTextView)this.findViewById(R.id.cm_workconn_show_operperson);
		time = (CustomeTextView)this.findViewById(R.id.cm_workconn_show_recordtm);
		noticeperson = (CustomeTextView)this.findViewById(R.id.cm_workconn_show_noticeperson);
		content = (CustomeTextView)this.findViewById(R.id.cm_workconn_show_noticecontent);
		checkbox = (CheckBox)this.findViewById(R.id.cm_workconn_show_radioCkeck);
		checkbox.setClickable(false);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getData(oper_id);
	}
	public void showData(WorkContackBean bean) {
		pname.setValueText(bean.getEntry_name_name());
		rperson.setValueText(bean.getOperator_name());
		time.setValueText(bean.getCreate_time());
		noticeperson.setValueText(bean.getNotify_the_staff_name());
		content.setValueText(bean.getContents_notice());
		if(bean.getStatus()==1){
			checkbox.setChecked(true);
		}else{
			checkbox.setChecked(false);
		}
	}

	public void getData(int id) {
		NetworkData.getInstance().workContackBean(new NetworkResponceFace() {

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
		case R.id.edit_cm_workconn_tables:
			if(getIntent().getIntExtra("editAuth", 1)==0||getIntent().getIntExtra("editListAuth", 1)==0||getIntent().getIntExtra("editItemAuth", 1)==0){
				Toast.makeText(CustomerManagerWorkConnectTicketShowActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
				return;
			}
			delete_oper_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			edit_oper_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			Intent i = new Intent();
			i.putExtra("editData", editString);
			i.setClass(CustomerManagerWorkConnectTicketShowActivity.this, CustomerManagerWorkConnectTicketEditorActivity.class);
			startActivity(i);
//			TwoTicketOperShowActivity.this.finish();
			break;
		case R.id.delete_cm_workconn_tables:
			if(getIntent().getIntExtra("editAuth", 1)==0||getIntent().getIntExtra("editListAuth", 1)==0||getIntent().getIntExtra("editItemAuth", 1)==0){
				Toast.makeText(CustomerManagerWorkConnectTicketShowActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
				return;
			}
			delete_oper_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			edit_oper_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			final AlertDialog alertDialog = new AlertDialog.Builder(
					CustomerManagerWorkConnectTicketShowActivity.this).create();
			alertDialog.show();
			Window window = alertDialog.getWindow();
			window.setContentView(R.layout.oper_tickets_delete_alert_layout);
			Button okBtn = (Button) window.findViewById(R.id.oper_ticket_delete_ok);
			Button cancelBtn = (Button) window.findViewById(R.id.oper_ticket_delete_cancel);
			okBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					NetworkData.getInstance().workContackDelete(
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
					delete_oper_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
					edit_oper_tables.setBackgroundResource(R.drawable.button_franchise_shape);
				}
			});
			break;
		default:
			break;
		}
	}
}
