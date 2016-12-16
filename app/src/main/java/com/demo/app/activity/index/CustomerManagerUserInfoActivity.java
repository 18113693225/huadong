package com.demo.app.activity.index;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.CustInfoBackBean;
import com.demo.app.bean.DateBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;
import com.demo.app.view.CustomeRadioGroup;
//客户经理用户信息
public class CustomerManagerUserInfoActivity extends BaseActivity implements
		OnCheckedChangeListener {

	private CustomeEditText2 pname, runit, recordperson, workcontent,
			workComment;
	private RadioGroup serviceGroup, levelGroup, safetyGroup;
	private RadioButton safetyGroupOKBtn, levelGroupOKBtn, serviceOKBtn;
	private SharedPreferences sp;
	private int service = 0;
	private int level = 0;
	private int safety = 0;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(CustomerManagerUserInfoActivity.this, "新建成功",
						Toast.LENGTH_LONG).show();
				CustomerManagerUserInfoActivity.this.finish();
			} else if (msg.what == 2) {
				Toast.makeText(CustomerManagerUserInfoActivity.this, "新建失败",
						Toast.LENGTH_LONG).show();
			} else if (msg.what == 3) {
				Toast.makeText(CustomerManagerUserInfoActivity.this, "有必填项未填",
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
		TitleCommon.setTitle(this, null, "用户服务信息反馈表",CustomerManageUserInfoListActivity.class, true);
		TitleCommon.setGpsTitle(this);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		initLayout();
	}

	public void initLayout() {
		pname = (CustomeEditText2) this.findViewById(R.id.cm_userinfo_pname);
		runit = (CustomeEditText2) this.findViewById(R.id.cm_userinfo_runit);
		recordperson = (CustomeEditText2) this.findViewById(R.id.cm_userinfo_recordperson);
		recordperson.setText(sp.getString("username", ""));
		workcontent = (CustomeEditText2) this.findViewById(R.id.cm_userinfo_workcontent);
		workComment = (CustomeEditText2) this.findViewById(R.id.cm_userinfo_workComment);
		serviceGroup = (CustomeRadioGroup) this.findViewById(R.id.cm_userinfo_levelGroup);
		levelGroup = (CustomeRadioGroup) this.findViewById(R.id.cm_userinfo_serviceGroup);
		safetyGroup = (CustomeRadioGroup) this.findViewById(R.id.cm_userinfo_safetyGroup);
		serviceGroup.setOnCheckedChangeListener(this);
		levelGroup.setOnCheckedChangeListener(this);
		safetyGroup.setOnCheckedChangeListener(this);
		serviceOKBtn = (RadioButton) this.findViewById(R.id.cm_userinfo_serviceOKBtn);
		serviceOKBtn.setChecked(true);
		levelGroupOKBtn = (RadioButton) this.findViewById(R.id.cm_userinfo_levelGroupOKBtn);
		levelGroupOKBtn.setChecked(true);
		safetyGroupOKBtn = (RadioButton) this.findViewById(R.id.cm_userinfo_safetyGroupOKBtn);
		safetyGroupOKBtn.setChecked(true);

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
							List<DateBean> list = new ArrayList<DateBean>();
							list.add(bean);
							NetworkData.getInstance().custInfoBackAdd(new NetworkResponceFace() {
								
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
							}, list);
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
