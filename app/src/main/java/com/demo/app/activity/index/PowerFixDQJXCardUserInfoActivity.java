package com.demo.app.activity.index;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.DateBean;
import com.demo.app.bean.MaintenanceRecordBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;
import com.demo.app.view.CustomeTextView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PowerFixDQJXCardUserInfoActivity extends BaseActivity {

	private CustomeEditText2 pname, runit, jxperson, worknum, jxtype, workendtime, workfzr;
	private EditText xsbz;
	private Button saveBtn;
	private RadioGroup radioGroup;
	private RadioButton normalButton, exceptionButton;
	private LinearLayout commonLayout;
	private TextView addDevice;
	private SharedPreferences sp;
	private int isok = 1;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(PowerFixDQJXCardUserInfoActivity.this, "新建成功", Toast.LENGTH_LONG).show();
				PowerFixDQJXCardUserInfoActivity.this.finish();
			} else if (msg.what == 2) {
				Toast.makeText(PowerFixDQJXCardUserInfoActivity.this, "新建失败", Toast.LENGTH_LONG).show();
			} else if (msg.what == 3) {
				Toast.makeText(PowerFixDQJXCardUserInfoActivity.this, "有必填项未填", Toast.LENGTH_LONG).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_power_fix_dqjxcard_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "电气检修记录卡", PowerFixTableActivity.class, true);
		TitleCommon.setGpsTitle(this);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		commonLayout = (LinearLayout)this.findViewById(R.id.pf_dqjx_commonLayout);
		addDevice = (TextView)this.findViewById(R.id.pf_dqjx_addDevice);
		addDevice.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addDevice(false);
			};
		});
		addDevice(true);
		pname = (CustomeEditText2) this.findViewById(R.id.pf_dqjx_pname);
		runit = (CustomeEditText2) this.findViewById(R.id.pf_dqjx_runit);
		jxperson = (CustomeEditText2) this.findViewById(R.id.pf_dqjx_jxperson);
		jxperson.setText(sp.getString("username", ""));
		saveBtn = (Button) this.findViewById(R.id.pf_dqjx_saveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int pnameValue = pname.getEditTexTag();
//				int runitValue = runit.getEditTexTag();
				int jxpersonValue = /*jxperson.getEditTexTag()*/sp.getInt("userId", -1);;
				/*String worknumValue = worknum.getText();
				int jxtypeValue = jxtype.getEditTexTag();// ？
				String workendtimeValue = workendtime.getText();
				int workfzrValue = workfzr.getEditTexTag();
				String xsbzValue = xsbz.getText().toString();*/
				if (pnameValue == -1 || /*runitValue == -1 ||*/ jxpersonValue == -1 /*|| jxtypeValue == -1
						|| workfzrValue == -1 || workfzrValue == -1 | "".equals(workendtimeValue)*/) {
					handler.obtainMessage(3).sendToTarget();
				}else{
					List<DateBean> list = new ArrayList<DateBean>();
					int count = commonLayout.getChildCount();
					for(int i=0;i<count;i++){
						MaintenanceRecordBean bean = new MaintenanceRecordBean();
						bean.setEntry_name_id(pnameValue);
						bean.setGPS(sp.getString("currentAddress", "定位失败"));
//						bean.setCompany_id(runitValue);
						bean.setM_personnel_id(jxpersonValue);
						LinearLayout childP = (LinearLayout)commonLayout.getChildAt(i);
						int countp = childP.getChildCount();
						for(int j=0;j<countp;j++){
							View child = childP.getChildAt(j);
							Object tag = child.getTag();
							if(tag.equals("pf_dqjx_starttime")){
								String time = ((CustomeTextView)child).getValueText().toString();
								if("".equals(time)){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setWork_start_time(time);
							}else if(tag.equals("pf_dqjx_worknum")){
								String number = ((CustomeEditText2)child).getText();
								if("".equals(number)){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setWork_number(((CustomeEditText2)child).getText().toString());
							}else if(tag.equals("pf_dqjx_jxtype")){
								if(((CustomeEditText2)child).getEditTexTag()==-1){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setCheck_type(((CustomeEditText2)child).getEditTexTag());
							}else if(tag.equals("pf_dqjx_workendtime")){
								String time = ((CustomeEditText2)child).getText();
								if("".equals(time)){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setWork_end_time(time);
							}else if(tag.equals("pf_dqjx_workfzr")){
								if(((CustomeEditText2)child).getEditTexTag()==-1){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setPerson_in_charge_id(((CustomeEditText2)child).getEditTexTag());
							}else if(tag.equals("pf_dqjx_xsbz")){
								bean.setNote(((EditText)child).getText().toString());
							}/*else if(tag.equals("pf_dqjx_radioLinear")){
								LinearLayout ll = (LinearLayout)child;
								RadioGroup rg = (RadioGroup)ll.findViewById(R.id.pf_dqjx_radioGroup);
								int checkedId = rg.getCheckedRadioButtonId();
								if (checkedId == R.id.pf_dqjx_normalButton) {
									isok = 1;
								} else if (checkedId == R.id.pf_dqjx_exceptionButton) {
									isok = 0;
								}
								bean.setMaintenance_result(isok);
							}*/
						}
						list.add(bean);
					}
					NetworkData.getInstance().maintenanceRecordAdd(new NetworkResponceFace() {

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

	/**
	 * 添加设备。自动生成ID，匹配选择跳转防止ID重复导致错误。xml必须配置tag通过tag后面循环取值
	 * @param isFirst
	 */
	@SuppressLint("NewApi")
	public void addDevice(boolean isFirst){
		View child =  getLayoutInflater().inflate(R.layout.common_dqjxcard_layout, null);
		CustomeTextView cpf_dqjx_starttime = (CustomeTextView)child.findViewById(R.id.pf_dqjx_starttime);
		Calendar c = Calendar.getInstance();
		String time = (new SimpleDateFormat("yyyy-MM-dd")).format(c.getTime());
		cpf_dqjx_starttime.setValueText(time);			
		if(!isFirst){
		}else{
			cpf_dqjx_starttime.setVisibility(View.GONE);
		}
		CustomeEditText2 cjxtype = (CustomeEditText2) child.findViewById(R.id.pf_dqjx_jxtype);
		cjxtype.setId(View.generateViewId());
		CustomeEditText2 cworkendtime = (CustomeEditText2) child.findViewById(R.id.pf_dqjx_workendtime);
		cworkendtime.setId(View.generateViewId());
		CustomeEditText2 cworkfzr = (CustomeEditText2) child.findViewById(R.id.pf_dqjx_workfzr);
		cworkfzr.setId(View.generateViewId());
//		RadioButton cnormalButton = (RadioButton) child.findViewById(R.id.pf_dqjx_normalButton);
//		cnormalButton.setChecked(true);
		commonLayout.addView(child);
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
			case 99:
				if (editTextId != -1) {
					CustomeEditText2 et = (CustomeEditText2) findViewById(editTextId);
					String time = data.getExtras().getString("time");
					et.setText(time);
				}
				break;
			default:
				break;
			}
		}
	}
}
