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
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.WorkTicketBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeTextView;
import com.google.gson.Gson;

public class TwoTicketWorkShowActivity extends BaseActivity implements
		OnClickListener {
	private Button edit_work_tables, delete_work_tables;
	private CustomeTextView pname,runit,rperson,num,worknum,workname,fzr,qfz,start,end;
	private int work_id;
	private String editString = "";
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				Gson gson = new Gson();
				WorkTicketBean bean = gson.fromJson(msg.obj.toString(), WorkTicketBean.class);
				showData(bean);
				break;
			case 2:
				Toast.makeText(TwoTicketWorkShowActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
				finish();
				break;
			case 3:
				Toast.makeText(TwoTicketWorkShowActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
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
		setContentView(R.layout.index_twoticket_work_show_layout);
		TitleCommon.setTitle(this, null, "工作票统计表",TwoTicketWorkActivity.class, true);
		TitleCommon.setGpsTitle(this);
		work_id = Integer.parseInt(getIntent().getStringExtra("id"));
//		getData(work_id);
		edit_work_tables = (Button) this.findViewById(R.id.edit_work_tables);
		delete_work_tables = (Button) this.findViewById(R.id.delete_work_tables);
		edit_work_tables.setOnClickListener(this);
		delete_work_tables.setOnClickListener(this);
		pname = (CustomeTextView)this.findViewById(R.id.tt_w_show_pname);
		runit = (CustomeTextView)this.findViewById(R.id.tt_w_show_runit);
		rperson = (CustomeTextView)this.findViewById(R.id.tt_w_show_rperson);
		num = (CustomeTextView)this.findViewById(R.id.tt_w_show_num);
		worknum = (CustomeTextView)this.findViewById(R.id.tt_w_show_worknum);
		workname = (CustomeTextView)this.findViewById(R.id.tt_w_show_workname);
		fzr = (CustomeTextView)this.findViewById(R.id.tt_w_show_fzr);
		qfz = (CustomeTextView)this.findViewById(R.id.tt_w_show_qfz);
		start = (CustomeTextView)this.findViewById(R.id.tt_w_show_start);
		end = (CustomeTextView)this.findViewById(R.id.tt_w_show_end);

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getData(work_id);
	}
	public void showData(WorkTicketBean bean){
		pname.setValueText(bean.getEntry_name_name());
		runit.setValueText(bean.getCompany_name());
		rperson.setValueText(bean.getCreate_user_name());
		num.setValueText(bean.getSerial_number());
		worknum.setValueText(bean.getWork_number());
		workname.setValueText(bean.getWord_ticket_name());
		fzr.setValueText(bean.getWork_user_name());
		qfz.setValueText(bean.getIssue_user_name());
		start.setValueText(bean.getWork_start_time());
		end.setValueText(bean.getWork_end_time());
	}
	public void getData(int id) {
		NetworkData.getInstance().workTicketBean(new NetworkResponceFace() {
			
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
		case R.id.edit_work_tables:
			if(getIntent().getIntExtra("editAuth", 1)==0||getIntent().getIntExtra("editListAuth", 1)==0||getIntent().getIntExtra("editItemAuth", 1)==0){
				Toast.makeText(TwoTicketWorkShowActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
				return;
			}
			delete_work_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			edit_work_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			Intent i = new Intent();
			i.putExtra("editData", editString);
			i.setClass(TwoTicketWorkShowActivity.this, TwoTicketWorkEditorActivity.class);
			startActivity(i);
//			TwoTicketWorkShowActivity.this.finish();
			break;
		case R.id.delete_work_tables:
			if(getIntent().getIntExtra("editAuth", 1)==0||getIntent().getIntExtra("editListAuth", 1)==0||getIntent().getIntExtra("editItemAuth", 1)==0){
				Toast.makeText(TwoTicketWorkShowActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
				return;
			}
			delete_work_tables.setBackgroundResource(R.drawable.button_franchise_shape);
			edit_work_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
			final AlertDialog alertDialog = new AlertDialog.Builder(TwoTicketWorkShowActivity.this).create();  
			alertDialog.show();  
			Window window = alertDialog.getWindow();  
			window.setContentView(R.layout.work_tickets_delete_alert_layout);
			Button okBtn = (Button)window.findViewById(R.id.work_ticket_delete_ok);
			Button cancelBtn = (Button)window.findViewById(R.id.work_ticket_delete_cancel);
			okBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					NetworkData.getInstance().workTicketDelete(new NetworkResponceFace() {
						
						@Override
						public void callback(String result) {
							// TODO Auto-generated method stub
							JSONObject json;
							try {
								json = new JSONObject(result);
								String status = json.getString("status");
								if("success".equals(status)){
									handler.obtainMessage(2).sendToTarget();									
								}else{
									handler.obtainMessage(3).sendToTarget();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, work_id+"");
					alertDialog.dismiss();
				}
			});
			cancelBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					alertDialog.dismiss();
					delete_work_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
					edit_work_tables.setBackgroundResource(R.drawable.button_franchise_shape);
				}
			});
			break;
		default:
			break;
		}
	}
}
