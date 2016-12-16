package com.demo.app.activity.index;


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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.OptTicketBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;
import com.google.gson.Gson;

public class TwoTicketOperEditorActivity extends BaseActivity {

	private CustomeEditText2 pname, runit, rperson, pnum, opernum, operperson,
			operstart, operend;
	private Button save;
	private RadioGroup radioGroup;
	private RadioButton radioOK, radioNotOk;
	private SharedPreferences sp;
	private int isok = OptTicketBean.QUALIFIED_OK;
	private String editData;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(TwoTicketOperEditorActivity.this, "编辑操作票成功",
						Toast.LENGTH_LONG).show();
				Intent i = new Intent();
				i.putExtra("id", pname.getEditTexTag());
				i.setClass(TwoTicketOperEditorActivity.this, TwoTicketOperShowActivity.class);
				TwoTicketOperEditorActivity.this.finish();
				
			} else if (msg.what == 2) {
				Toast.makeText(TwoTicketOperEditorActivity.this, "编辑操作票失败",
						Toast.LENGTH_LONG).show();
			} else if (msg.what == 3) {
				Toast.makeText(TwoTicketOperEditorActivity.this, "有必填项未填",
						Toast.LENGTH_LONG).show();
			} else if (msg.what == 4) {
				Toast.makeText(TwoTicketOperEditorActivity.this, "结束时间必须大于开始时间",
						Toast.LENGTH_LONG).show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_twoticket_oper_new_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		TitleCommon.setTitle(this, null, "编辑操作票统计表",TwoTicketOperShowActivity.class, true);
		TitleCommon.setGpsTitle(this);
		editData = getIntent().getStringExtra("editData");
		initLayout();
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
				int id = data.getExtras().getInt("id");
				if(editTextId!=-1){
					CustomeEditText2 et = (CustomeEditText2)findViewById(editTextId);
					et.setText(text);
					et.setEditTextTag(id);
				}
				break;
			case 99:
				if(editTextId!=-1){
					CustomeEditText2 et = (CustomeEditText2)findViewById(editTextId);
					String time = data.getExtras().getString("time");
					et.setText(time);
				}
				break;
			default:
				break;
			}
		}

	}

	public void initLayout() {
		Gson gson = new Gson();
		OptTicketBean bean = gson.fromJson(editData,OptTicketBean.class);
		final int id = bean.getId();
		pname = (CustomeEditText2) this.findViewById(R.id.oper_new_pname);
		pname.setText(bean.getEntry_name_name());
		pname.setEditTextTag(bean.getEntry_name_id());
		
		runit = (CustomeEditText2) this.findViewById(R.id.oper_new_runit);
		runit.setText(bean.getCompany_name());
		runit.setEditTextTag(bean.getCompany_id());
		
		rperson = (CustomeEditText2) this.findViewById(R.id.oper_new_rperson);
		rperson.setText(sp.getString("username", ""));
		
		pnum = (CustomeEditText2) this.findViewById(R.id.oper_new_pnum);
		pnum.setText(bean.getSerial_number());
		opernum = (CustomeEditText2) this.findViewById(R.id.oper_new_opernum);
		opernum.setText(bean.getOperation_number());
		
		operperson = (CustomeEditText2) this.findViewById(R.id.oper_new_operperson);
		operperson.setText(bean.getOperation_user_name());
		operperson.setEditTextTag(bean.getOperation_user_id());
		
		operstart = (CustomeEditText2) this.findViewById(R.id.oper_new_operstart);
		operstart.setText(bean.getOper_start_time());
		operend = (CustomeEditText2) this.findViewById(R.id.oper_new_operend);
		operend.setText(bean.getOper_end_time());
		
		radioGroup = (RadioGroup) this.findViewById(R.id.oper_new_radioGroup);
		radioOK = (RadioButton) this.findViewById(R.id.oper_new_radioOk);
		radioOK.setChecked(true);
		radioNotOk = (RadioButton) this.findViewById(R.id.oper_new_radioNotOk);
		isok = bean.getQualified();
		if (isok == 1) {
			radioOK.setChecked(true);
		} else {
			radioNotOk.setChecked(true);
		}
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.oper_new_radioOk) {
					isok = OptTicketBean.QUALIFIED_OK;
				} else if (checkedId == R.id.oper_new_radioNotOk) {
					isok = OptTicketBean.QUALIFIED_NO;
				}
			}
		});

		save = (Button) this.findViewById(R.id.oper_new_save);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int pnameValue = pname.getEditTexTag();
				int runitValue = runit.getEditTexTag();
				//当前用户
				int rpersonValue = /*rperson.getEditTexTag()*/sp.getInt("userId", -1);
				String pnumValue = pnum.getText();
				String opernumValue = opernum.getText();
				int operpersonValue = operperson.getEditTexTag();
				String operstartValue = operstart.getText();
				String operendValue = operend.getText();
				
				if(operendValue.compareTo(operstartValue)<=0){
					handler.obtainMessage(4).sendToTarget();
					return;
				}
				if (pnameValue == -1 || runitValue == -1 || rpersonValue == -1
						|| operpersonValue == -1 || "".equals(pnumValue)
						|| "".equals(opernumValue) || "".equals(operstartValue)
						|| "".equals(operendValue)) {
					handler.obtainMessage(3).sendToTarget();
				} else {
					OptTicketBean bean = new OptTicketBean();
					bean.setId(id);
					bean.setEntry_name_id(pnameValue);
					bean.setCompany_id(runitValue);
					bean.setCreate_user_id(rpersonValue);
					bean.setSerial_number(pnumValue);
					bean.setOperation_number(opernumValue);
					bean.setOperation_user_id(operpersonValue);
					bean.setOper_start_time(operstartValue);
					bean.setOper_end_time(operendValue);
					bean.setQualified(isok);
//					List<DateBean> list = new ArrayList<DateBean>();
//					list.add(bean);
					NetworkData.getInstance().optTicketUpdate(
							new NetworkResponceFace() {

								@Override
								public void callback(String result) {
									// TODO Auto-generated method stub
									JSONObject json;
									try {
										json = new JSONObject(result);
										String status = json
												.getString("status");
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
}
