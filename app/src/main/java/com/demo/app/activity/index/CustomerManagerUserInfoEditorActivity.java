package com.demo.app.activity.index;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.CustInfoBackBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;
import com.demo.app.view.CustomeRadioGroup;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
//客户经理用户信息编辑
public class CustomerManagerUserInfoEditorActivity extends BaseActivity implements
		OnCheckedChangeListener {

	private CustomeEditText2 pname, runit, recordperson, workcontent,
			workComment;
	private RadioGroup serviceGroup, levelGroup, safetyGroup;
	private RadioButton safetyGroupOKBtn,safetyGroupOK1Btn,safetyGroupOK2Btn,safetyGroupOK3Btn ,
	levelGroupOKBtn,levelGroupOK1Btn,levelGroupOK2Btn,levelGroupOK3Btn, 
	serviceOKBtn,serviceOK1Btn,serviceOK2Btn,serviceOK3Btn;
	private SharedPreferences sp;
	private int service = 0;
	private int level = 0;
	private int safety = 0;
	private String editData;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(CustomerManagerUserInfoEditorActivity.this, "编辑成功",
						Toast.LENGTH_LONG).show();
				CustomerManagerUserInfoEditorActivity.this.finish();
			} else if (msg.what == 2) {
				Toast.makeText(CustomerManagerUserInfoEditorActivity.this, "编辑失败",
						Toast.LENGTH_LONG).show();
			} else if (msg.what == 3) {
				Toast.makeText(CustomerManagerUserInfoEditorActivity.this, "有必填项未填",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_customer_manager_userinfo_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "编辑用户服务信息反馈表",CustomerManagerUserInfoShowActivity.class, true);
		TitleCommon.setGpsTitle(this);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		initLayout();
	}

	public void initLayout() {
		editData = getIntent().getStringExtra("editData");
		Gson gson = new Gson();
		final CustInfoBackBean bean = gson.fromJson(editData,CustInfoBackBean.class);
		final int id = bean.getId();
		pname = (CustomeEditText2) this.findViewById(R.id.cm_userinfo_pname);
		pname.setText(bean.getEntry_name_name());
		pname.setEditTextTag(bean.getEntry_name_id());
		runit = (CustomeEditText2) this.findViewById(R.id.cm_userinfo_runit);
		runit.setText(bean.getCompany_name());
		runit.setEditTextTag(bean.getCompany_id());
		recordperson = (CustomeEditText2) this.findViewById(R.id.cm_userinfo_recordperson);
//		recordperson.setText(sp.getString("username", ""));
		recordperson.setText(bean.getCreate_user_name());
		recordperson.setEditTextTag(bean.getCreate_user_id());
		workcontent = (CustomeEditText2) this.findViewById(R.id.cm_userinfo_workcontent);
		workcontent.setText(bean.getWork_content());
		workComment = (CustomeEditText2) this.findViewById(R.id.cm_userinfo_workComment);
		workComment.setText(bean.getOverall_evaluation());
		serviceGroup = (CustomeRadioGroup) this.findViewById(R.id.cm_userinfo_levelGroup);
		levelGroup = (CustomeRadioGroup) this.findViewById(R.id.cm_userinfo_serviceGroup);
		safetyGroup = (CustomeRadioGroup) this.findViewById(R.id.cm_userinfo_safetyGroup);
		serviceGroup.setOnCheckedChangeListener(this);
		levelGroup.setOnCheckedChangeListener(this);
		safetyGroup.setOnCheckedChangeListener(this);
		service = bean.getService_attitude();
		level = bean.getTechnical_level();
		safety = bean.getSafety_civilization();
		
		serviceOKBtn = (RadioButton) this.findViewById(R.id.cm_userinfo_serviceOKBtn);
		serviceOK1Btn = (RadioButton) this.findViewById(R.id.cm_userinfo_serviceOK1Btn);
		serviceOK2Btn = (RadioButton) this.findViewById(R.id.cm_userinfo_serviceOK2Btn);
		serviceOK3Btn = (RadioButton) this.findViewById(R.id.cm_userinfo_serviceOK3Btn);
		switch (service) {
		case 0:
			serviceOKBtn.setChecked(true);
			break;
		case 1:
			serviceOK1Btn.setChecked(true);
			break;
		case 2:
			serviceOK2Btn.setChecked(true);
			break;
		case 3:
			serviceOK3Btn.setChecked(true);
			break;
		default:
			break;
		}
		levelGroupOKBtn = (RadioButton) this.findViewById(R.id.cm_userinfo_levelGroupOKBtn);
		levelGroupOK1Btn = (RadioButton) this.findViewById(R.id.cm_userinfo_levelGroupOK1Btn);
		levelGroupOK2Btn = (RadioButton) this.findViewById(R.id.cm_userinfo_levelGroupOK2Btn);
		levelGroupOK3Btn = (RadioButton) this.findViewById(R.id.cm_userinfo_levelGroupOK3Btn);
		switch (level) {
		case 0:
			levelGroupOKBtn.setChecked(true);
			break;
		case 1:
			levelGroupOK1Btn.setChecked(true);
			break;
		case 2:
			levelGroupOK2Btn.setChecked(true);
			break;
		case 3:
			levelGroupOK3Btn.setChecked(true);
			break;
		default:
			break;
		}
		safetyGroupOKBtn = (RadioButton) this.findViewById(R.id.cm_userinfo_safetyGroupOKBtn);
		safetyGroupOK1Btn = (RadioButton) this.findViewById(R.id.cm_userinfo_safetyGroupOK1Btn);
		safetyGroupOK2Btn = (RadioButton) this.findViewById(R.id.cm_userinfo_safetyGroupOK2Btn);
		safetyGroupOK3Btn = (RadioButton) this.findViewById(R.id.cm_userinfo_safetyGroupOK3Btn);
		switch (safety) {
		case 0:
			safetyGroupOKBtn.setChecked(true);
			break;
		case 1:
			safetyGroupOK1Btn.setChecked(true);
			break;
		case 2:
			safetyGroupOK2Btn.setChecked(true);
			break;
		case 3:
			safetyGroupOK3Btn.setChecked(true);
			break;
		default:
			break;
		}
		findViewById(R.id.cm_userinfo_saveBtn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int pnameValue = pname.getEditTexTag();
						int recordpersonValue = sp.getInt("userId", -1);
						String workcontentValue = workcontent.getText();
						String workCommentValue = workComment.getText();
						if(pnameValue==-1||recordpersonValue==-1||"".equals(workcontentValue)){
							handler.obtainMessage(3).sendToTarget();
						}else{
							CustInfoBackBean bean = new CustInfoBackBean();
							bean.setId(id);
							bean.setEntry_name_id(pnameValue);
							bean.setCreate_user_id(recordpersonValue);
							Calendar calendar = Calendar.getInstance();
							String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());
							bean.setCreate_time(time);
							bean.setWork_content(workcontentValue);
							bean.setOverall_evaluation(workCommentValue);
							bean.setService_attitude(service);
							bean.setTechnical_level(level);
							bean.setSafety_civilization(safety);
							NetworkData.getInstance().custInfoBackUpdate(new NetworkResponceFace() {
								
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

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		int id = group.getId();
		switch (id) {
		case R.id.cm_userinfo_serviceGroup:
			switch (checkedId) {
			case R.id.cm_userinfo_serviceOKBtn:
				service = 0;
				break;
			case R.id.cm_userinfo_serviceOK1Btn:
				service = 1;
				break;
			case R.id.cm_userinfo_serviceOK2Btn:
				service = 2;
				break;
			case R.id.cm_userinfo_serviceOK3Btn:
				service = 3;
				break;
			default:
				break;
			}
			break;
		case R.id.cm_userinfo_levelGroup:
			switch (checkedId) {
			case R.id.cm_userinfo_levelGroupOKBtn:
				level = 0;
				break;
			case R.id.cm_userinfo_levelGroupOK1Btn:
				level = 1;
				break;
			case R.id.cm_userinfo_levelGroupOK2Btn:
				level = 2;
				break;
			case R.id.cm_userinfo_levelGroupOK3Btn:
				level = 3;
				break;
			default:
				break;
			}
			break;
		case R.id.cm_userinfo_safetyGroup:
			switch (checkedId) {
			case R.id.cm_userinfo_safetyGroupOKBtn:
				safety = 0;
				break;
			case R.id.cm_userinfo_safetyGroupOK1Btn:
				safety = 1;
				break;
			case R.id.cm_userinfo_safetyGroupOK2Btn:
				safety = 2;
				break;
			case R.id.cm_userinfo_safetyGroupOK3Btn:
				safety = 3;
				break;
			default:
				break;
			}
		default:
			break;
		}
	}
}
