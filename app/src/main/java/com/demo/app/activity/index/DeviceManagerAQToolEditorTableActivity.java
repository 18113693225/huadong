package com.demo.app.activity.index;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
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
import android.widget.TextView;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.DateBean;
import com.demo.app.bean.SafetyToolsBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;
import com.google.gson.Gson;
//AQ工具编辑器表
public class DeviceManagerAQToolEditorTableActivity extends BaseActivity {
	private CustomeEditText2 pname,runit,djperson;
	private Button saveBtn;
	private TextView addDevice;
	private LinearLayout commonLayout;
	private SharedPreferences sp;
	private String editData;
	private int isok = 1;
	private int listID;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(DeviceManagerAQToolEditorTableActivity.this, "编辑成功", Toast.LENGTH_LONG).show();
				DeviceManagerAQToolEditorTableActivity.this.finish();
			} else if (msg.what == 2) {
				Toast.makeText(DeviceManagerAQToolEditorTableActivity.this, "编辑失败", Toast.LENGTH_LONG).show();
			} else if (msg.what == 3) {
				Toast.makeText(DeviceManagerAQToolEditorTableActivity.this, "有必填项未填", Toast.LENGTH_LONG).show();
			}else if(msg.what == 4){
				Toast.makeText(DeviceManagerAQToolEditorTableActivity.this, "添加设备大于已有设备数量", Toast.LENGTH_LONG).show();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_device_manager_new_aqtooltable_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "编辑安全工具表",DeviceManagerAQToolTableShowActivity.class, true);
		TitleCommon.setGpsTitle(this);
		Constents.xscontentList.clear();
		Constents.devicenum = 0;
		editData = getIntent().getStringExtra("editData");
		
		commonLayout = (LinearLayout)this.findViewById(R.id.dm_aqtool_commonLayout);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		pname = (CustomeEditText2)this.findViewById(R.id.dm_aqtool_pname);
		runit = (CustomeEditText2)this.findViewById(R.id.dm_aqtool_runit);
		
		djperson = (CustomeEditText2)this.findViewById(R.id.dm_aqtool_djperson);
		findViewById(R.id.dm_aqtool_addDevice).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int count = commonLayout.getChildCount();
				if(count>=Constents.devicenum){
					handler.obtainMessage(4).sendToTarget();
				}else{
					addDevice(null);					
				}
			}
		});
//		addDevice();
		findViewById(R.id.dm_aqtool_saveBtn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int pnameValue = pname.getEditTexTag();
				int djpersonValue = sp.getInt("userId", -1);
				if(pnameValue==-1||djpersonValue==-1){
					handler.obtainMessage(3).sendToTarget();
				}else{
					Calendar calendar = Calendar.getInstance();
					String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());
					List<DateBean> list = new ArrayList<DateBean>();
					int count = commonLayout.getChildCount();
					for(int i=0;i<count;i++){
						SafetyToolsBean bean = new SafetyToolsBean();
						bean.setId(listID);
						bean.setEntry_name_id(pnameValue);
	//					bean.setCompany_id(runitValue);
						bean.setFill_in_user_id(djpersonValue);
						bean.setCreate_time(time);
						bean.setGps(sp.getString("currentAddress", "定位失败"));
						
						LinearLayout childP = (LinearLayout)commonLayout.getChildAt(i);
						int countp = childP.getChildCount();
						for(int j=0;j<countp;j++){
							View child = childP.getChildAt(j);
							Object tag = child.getTag();
							if(tag.equals("dm_aqtool_dname")){//设备名称
								if(((CustomeEditText2)child).getEditTexTag()==-1){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setDevice_id(((CustomeEditText2)child).getEditTexTag());
							}else if(tag.equals("dm_aqtool_devicenum")){//编号
								if("".equals(((CustomeEditText2)child).getText().toString())){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setDevice_number(((CustomeEditText2)child).getText().toString());								
							}else if(tag.equals("dm_aqtool_tooldesc")){//工器具
								if(((CustomeEditText2)child).getEditTexTag()==-1){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setTool_id(((CustomeEditText2)child).getEditTexTag());
							}else if(tag.equals("dm_aqtool_model")){//型号
								if("".equals(((CustomeEditText2)child).getText().toString())){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setModel(((CustomeEditText2)child).getText().toString());
							}else if(tag.equals("dm_aqtool_dylevel")){//电压等级
								if(((CustomeEditText2)child).getEditTexTag()==-1){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setVoltage_level(((CustomeEditText2)child).getEditTexTag());
							}else if(tag.equals("dm_aqtool_slnum")){//数量
								if("".equals(((CustomeEditText2)child).getText().toString())){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setSize(Integer.parseInt(((CustomeEditText2)child).getText().toString()));								
							}else if(tag.equals("dm_aqtool_sytime")){//试验日期
								if("".equals(((CustomeEditText2)child).getText().toString())){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setTest_time(((CustomeEditText2)child).getText().toString());								
							}else if(tag.equals("dm_aqtool_radioLinear")){//结果
								LinearLayout ll = (LinearLayout)child;
								RadioGroup rg = (RadioGroup)ll.findViewById(R.id.dm_aqtool_radioGroup);
								int checkedId = rg.getCheckedRadioButtonId();
								if (checkedId == R.id.dm_aqtool_okButton) {
									isok = 1;
								} else if (checkedId == R.id.dm_aqtool_notOkButton) {
									isok = 0;
								}
								bean.setTest_result(isok);
							}else if(tag.equals("dm_aqtool_nextsytime")){//下次试验日期
								if("".equals(((CustomeEditText2)child).getText().toString())){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setNext_test(((CustomeEditText2)child).getText().toString());								
							}else if(tag.equals("dm_aqtool_bz")){//备注
								bean.setNote(((EditText)child).getText().toString());
							}
						}
						list.add(bean);
					}
					NetworkData.getInstance().safetyToolsUpdate(new NetworkResponceFace() {
	
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
		showData(editData);
	}
	
	public void showData(String data) {
		try {
			JSONArray rel = new JSONArray(data);
			for (int i = 0; i < rel.length(); i++) {
				JSONObject obj = (JSONObject) rel.get(i);
				Gson gson = new Gson();
				SafetyToolsBean bean = gson.fromJson(obj.toString(),SafetyToolsBean.class);
				addDevice(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 添加设备。自动生成ID，匹配选择跳转防止ID重复导致错误。xml必须配置tag通过tag后面循环取值
	 * @param isFirst
	 */
	@SuppressLint("NewApi")
	public void addDevice(SafetyToolsBean bean){
		View child =  getLayoutInflater().inflate(R.layout.common_aqtool_layout, null);
		CustomeEditText2 dname = (CustomeEditText2) child.findViewById(R.id.dm_aqtool_dname);
		dname.setId(View.generateViewId());
		CustomeEditText2 tooldesc = (CustomeEditText2) child.findViewById(R.id.dm_aqtool_tooldesc);
		tooldesc.setId(View.generateViewId());
		CustomeEditText2 dylevel = (CustomeEditText2) child.findViewById(R.id.dm_aqtool_dylevel);
		dylevel.setId(View.generateViewId());
		CustomeEditText2 sytime = (CustomeEditText2) child.findViewById(R.id.dm_aqtool_sytime);
		sytime.setId(View.generateViewId());
		CustomeEditText2 nextsytime = (CustomeEditText2) child.findViewById(R.id.dm_aqtool_nextsytime);
		nextsytime.setId(View.generateViewId());
		RadioButton okButton = (RadioButton) child.findViewById(R.id.dm_aqtool_okButton);
		okButton.setChecked(true);
		RadioButton notOkButton = (RadioButton) child.findViewById(R.id.dm_aqtool_notOkButton);

		if(bean!=null){
			pname.setText(bean.getEntry_name_name());
			pname.setEditTextTag(bean.getEntry_name_id());
			runit.setText(bean.getCompany_name());
			runit.setEditTextTag(bean.getCompany_id());
			djperson.setText(bean.getFill_in_user_name());
			djperson.setEditTextTag(bean.getFill_in_user_id());
			listID =  bean.getId();
			dname.setText(bean.getDevice_name());
			dname.setEditTextTag(bean.getDevice_id());
			CustomeEditText2 devicenum = (CustomeEditText2) child.findViewById(R.id.dm_aqtool_devicenum);
			devicenum.setText(bean.getDevice_number());
			tooldesc.setText(bean.getTool_name());
			tooldesc.setEditTextTag(bean.getTool_id());
			CustomeEditText2 model = (CustomeEditText2) child.findViewById(R.id.dm_aqtool_model);
			model.setText(bean.getModel());
			dylevel.setText(bean.getVoltage_level_name());
			dylevel.setEditTextTag(bean.getVoltage_level());
			CustomeEditText2 slnum = (CustomeEditText2) child.findViewById(R.id.dm_aqtool_slnum);
			slnum.setText(bean.getSize()+"");
			sytime.setText(bean.getTest_time());
			if(bean.getTest_result()==0){
				notOkButton.setChecked(true);
			}
			nextsytime.setText(bean.getNext_test());
			EditText bz = (EditText) child.findViewById(R.id.dm_aqtool_bz);
			bz.setText(bean.getNote());
		}
			
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
