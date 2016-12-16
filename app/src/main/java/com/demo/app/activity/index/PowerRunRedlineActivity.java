package com.demo.app.activity.index;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.DateBean;
import com.demo.app.bean.InfraredTemperatureBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;

public class PowerRunRedlineActivity extends BaseActivity {

	private CustomeEditText2 pname,runit,weather,temperature,recordperson,/*jgunit,*/dname,ax,bx,cx;
	private Button saveBtn;
	private TextView addDevice;
	private LinearLayout commonLayout;
	private SharedPreferences sp;
	private int isok = 1;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(PowerRunRedlineActivity.this, "新建成功", Toast.LENGTH_LONG).show();
				PowerRunRedlineActivity.this.finish();
			} else if (msg.what == 2) {
				Toast.makeText(PowerRunRedlineActivity.this, "新建失败", Toast.LENGTH_LONG).show();
			} else if (msg.what == 3) {
				Toast.makeText(PowerRunRedlineActivity.this, "有必填项未填", Toast.LENGTH_LONG).show();
			} else if(msg.what == 4){
				Toast.makeText(PowerRunRedlineActivity.this, "添加设备大于已有设备数量", Toast.LENGTH_LONG).show();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_power_run_redline_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "红外线测温记录卡",PowerRunActivity.class, true);
		TitleCommon.setGpsTitle(this);
		Constents.xscontentList.clear();
		Constents.devicenum = 0;
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		pname = (CustomeEditText2)this.findViewById(R.id.pr_rdeline_pname);
		runit = (CustomeEditText2)this.findViewById(R.id.pr_rdeline_runit);
		weather = (CustomeEditText2)this.findViewById(R.id.pr_rdeline_weather);
		temperature = (CustomeEditText2)this.findViewById(R.id.pr_rdeline_temperature);
		recordperson = (CustomeEditText2)this.findViewById(R.id.pr_rdeline_recordperson);
		recordperson.setText(sp.getString("username", ""));
//		jgunit = (CustomeEditText2)this.findViewById(R.id.pr_rdeline_jgunit);
		
		commonLayout = (LinearLayout)this.findViewById(R.id.pr_redline_commonLayout);
		addDevice = (TextView)this.findViewById(R.id.pr_rdeline_addDevice);
		addDevice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int count = commonLayout.getChildCount();
				/*if(count>=Constents.devicenum){
					handler.obtainMessage(4).sendToTarget();
				}else{*/
					addDevice();					
//				}
			}
		});
		addDevice();
		dname = (CustomeEditText2)this.findViewById(R.id.pr_rdeline_dname);
		ax = (CustomeEditText2)this.findViewById(R.id.pr_rdeline_ax);
		bx = (CustomeEditText2)this.findViewById(R.id.pr_rdeline_bx);
		cx = (CustomeEditText2)this.findViewById(R.id.pr_rdeline_cx);
		
		saveBtn = (Button)this.findViewById(R.id.pr_rdeline_saveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				int pnameValue = pname.getEditTexTag();
				int runitValue = runit.getEditTexTag();
				String weatherValue = weather.getText();
				String temperatureValue = temperature.getText();
				int recordpersonValue = /*recordperson.getEditTexTag()*/sp.getInt("userId", -1);
//				String jgunitValue = jgunit.getText();
				Calendar calendar = Calendar.getInstance();
				String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());
				List<DateBean> list = new ArrayList<DateBean>();
				
				int count = commonLayout.getChildCount();
				for(int i=0;i<count;i++){
					InfraredTemperatureBean bean = new InfraredTemperatureBean();
					bean.setEntry_name_id(pnameValue);
//					bean.setCompany_id(runitValue);
					bean.setWeather(weatherValue);
					bean.setAmbient_temperature(temperatureValue);
					bean.setRecord_user_id(recordpersonValue);
//					bean.setInterval_unit(jgunitValue);
					bean.setCreate_time(time);
					bean.setGPS(sp.getString("currentAddress", "定位失败"));
					
					LinearLayout childP = (LinearLayout)commonLayout.getChildAt(i);
					int countp = childP.getChildCount();
					for(int j=0;j<countp;j++){
						View child = childP.getChildAt(j);
						Object tag = child.getTag();
						if(tag.equals("pr_rdeline_dname")){
							if(((CustomeEditText2)child).getEditTexTag()==-1){
								handler.obtainMessage(3).sendToTarget();
								return;
							}
							bean.setDevice_id(((CustomeEditText2)child).getEditTexTag());
						}else if(tag.equals("pr_rdeline_dnum")){
							bean.setInterval_unit(((CustomeEditText2)child).getText());
						}else if(tag.equals("pr_rdeline_ax")){
							String a = ((CustomeEditText2)child).getText();
							double at = 0;
							if(!"".equals(a)){
								at = Double.parseDouble(a);								
							}else{
								handler.obtainMessage(3).sendToTarget();
								return;
							}
							bean.setA_temp(at);
						}else if(tag.equals("pr_rdeline_bx")){
							String b = ((CustomeEditText2)child).getText();
							double bt = 0;
							if(!"".equals(b)){
								bt = Double.parseDouble(b);								
							}else{
								handler.obtainMessage(3).sendToTarget();
								return;
							}
							bean.setB_temp(bt);
						}else if(tag.equals("pr_rdeline_cx")){
							String c = ((CustomeEditText2)child).getText();
							double ct = 0;
							if(!"".equals(c)){
								ct = Double.parseDouble(c);								
							}else{
								handler.obtainMessage(3).sendToTarget();
								return;
							}
							bean.setC_temp(ct);
						}else if(tag.equals("pr_rdeline_radioLinear")){
							LinearLayout ll = (LinearLayout)child;
							RadioGroup rg = (RadioGroup)ll.findViewById(R.id.pr_rdeline_radioGroup);
							int checkedId = rg.getCheckedRadioButtonId();
							if (checkedId == R.id.pr_rdeline_normalButton) {
								isok = 1;
							} else if (checkedId == R.id.pr_rdeline_exceptionButton) {
								isok = 0;
							}
							bean.setCheck_result(isok);
						}else if(tag.equals("pr_rdeline_qxradioLinear")&&isok==0){
							LinearLayout ll = (LinearLayout)child;
							RadioGroup rg = (RadioGroup)ll.findViewById(R.id.pr_rdeline_qxradioGroup);
							int checkedId = rg.getCheckedRadioButtonId();
							String qx = "危机缺陷";
							if (checkedId == R.id.pr_rdeline_qxwjButton) {
								qx = "危机缺陷";
							} else if (checkedId == R.id.pr_rdeline_qxzdButton) {
								qx = "重大缺陷";
							} else if (checkedId == R.id.pr_rdeline_qxybButton) {
								qx = "一般缺陷";
							} 
							bean.setDefect_type(qx);
						}else if(tag.equals("pr_rdeline_xsbz")){
							bean.setCheck_info(((EditText)child).getText().toString());
						}
					}
					list.add(bean);
				}
				NetworkData.getInstance().infraredTemperatureAdd(new NetworkResponceFace() {

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
		});
	}

	/**
	 * 添加设备。自动生成ID，匹配选择跳转防止ID重复导致错误。xml必须配置tag通过tag后面循环取值
	 * @param isFirst
	 */
	@SuppressLint("NewApi")
	public void addDevice(){
		View child =  getLayoutInflater().inflate(R.layout.common_prredline_layout, null);
		CustomeEditText2 cdname = (CustomeEditText2) child.findViewById(R.id.pr_rdeline_dname);
		cdname.setId(View.generateViewId());
		RadioButton cnormalButton = (RadioButton) child.findViewById(R.id.pr_rdeline_normalButton);
		cnormalButton.setChecked(true);
		final LinearLayout qxradioLinear = (LinearLayout)child.findViewById(R.id.pr_rdeline_qxradioLinear);
		RadioGroup radioGroup = (RadioGroup)child.findViewById(R.id.pr_rdeline_radioGroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.pr_rdeline_normalButton) {
					qxradioLinear.setVisibility(View.GONE);
				} else if (checkedId == R.id.pr_rdeline_exceptionButton) {
					qxradioLinear.setVisibility(View.VISIBLE);
				}
			}
		});
		RadioButton qxwjButton = (RadioButton) child.findViewById(R.id.pr_rdeline_qxwjButton);
		qxwjButton.setChecked(true);
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
