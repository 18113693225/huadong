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
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.DateBean;
import com.demo.app.bean.EquipmentLedgerBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;
import com.google.gson.Gson;

public class DeviceManagerTZEditorTableActivity extends BaseActivity {
	private CustomeEditText2 pname,runit,djperson;
	private LinearLayout commonLayout;
	private SharedPreferences sp;
	private String editData;
	private int listID;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(DeviceManagerTZEditorTableActivity.this, "编辑成功", Toast.LENGTH_LONG).show();
				DeviceManagerTZEditorTableActivity.this.finish();
			} else if (msg.what == 2) {
				Toast.makeText(DeviceManagerTZEditorTableActivity.this, "编辑失败", Toast.LENGTH_LONG).show();
			} else if (msg.what == 3) {
				Toast.makeText(DeviceManagerTZEditorTableActivity.this, "有必填项未填", Toast.LENGTH_LONG).show();
			}else if(msg.what == 4){
				Toast.makeText(DeviceManagerTZEditorTableActivity.this, "添加设备大于已有设备数量", Toast.LENGTH_LONG).show();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_device_manager_new_tz_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "编辑设备台账登记表",DeviceManagerTZTableShowActivity.class, true);
		TitleCommon.setGpsTitle(this);
		Constents.xscontentList.clear();
		Constents.devicenum = 0;
		editData = getIntent().getStringExtra("editData");
		commonLayout = (LinearLayout)this.findViewById(R.id.dm_tz_commonLayout);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		pname = (CustomeEditText2)this.findViewById(R.id.dm_tz_pname);
		runit = (CustomeEditText2)this.findViewById(R.id.dm_tz_runit);
		djperson = (CustomeEditText2)this.findViewById(R.id.dm_tz_djperson);
		djperson.setText(sp.getString("username", ""));
		findViewById(R.id.dm_tz_addDevice).setOnClickListener(new OnClickListener() {
			
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
		findViewById(R.id.dm_tz_saveBtn).setOnClickListener(new OnClickListener() {
			
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
						EquipmentLedgerBean bean = new EquipmentLedgerBean();
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
							if(tag.equals("dm_tz_zichangnum")){//资产编号
								if("".equals(((CustomeEditText2)child).getText().toString())){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setAsset_number(((CustomeEditText2)child).getText().toString());
							}else if(tag.equals("dm_tz_zichangname")){//资产名称
								if("".equals(((CustomeEditText2)child).getText().toString())){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setAsset_name(((CustomeEditText2)child).getText().toString());								
							}else if(tag.equals("dm_tz_guigemodel")){//型号规格
								if("".equals(((CustomeEditText2)child).getText().toString())){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setSpecification_model(((CustomeEditText2)child).getText().toString());
							}else if(tag.equals("dm_tz_scfactory")){//生产厂家
								if("".equals(((CustomeEditText2)child).getText().toString())){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setManufacturer(((CustomeEditText2)child).getText().toString());
							}else if(tag.equals("dm_tz_sctime")){//生产时间
								if("".equals(((CustomeEditText2)child).getText().toString())){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setProduction_date(((CustomeEditText2)child).getText().toString());
							}else if(tag.equals("dm_tz_slnum")){//数量
								String size = ((CustomeEditText2)child).getText().toString();
								if("".equals(size)){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setSize(Integer.parseInt(size));
							}else if(tag.equals("dm_tz_trytime")){//预时时间
								if("".equals(((CustomeEditText2)child).getText().toString())){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setPre_test_time(((CustomeEditText2)child).getText().toString());
							}else if(tag.equals("dm_tz_bz")){//备注
								bean.setNote(((EditText)child).getText().toString());
							}
						}
						list.add(bean);
					}
					NetworkData.getInstance().equipmentLedgerUpdate(new NetworkResponceFace() {
	
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
				EquipmentLedgerBean bean = gson.fromJson(obj.toString(),EquipmentLedgerBean.class);
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
	public void addDevice(EquipmentLedgerBean bean){
		View child =  getLayoutInflater().inflate(R.layout.common_tz_layout, null);
		CustomeEditText2 sctime = (CustomeEditText2) child.findViewById(R.id.dm_tz_sctime);
		sctime.setId(View.generateViewId());
		CustomeEditText2 trytime = (CustomeEditText2) child.findViewById(R.id.dm_tz_trytime);
		trytime.setId(View.generateViewId());
		if(bean!=null){
			listID = bean.getId();
			pname.setText(bean.getEntry_name_name());
			pname.setEditTextTag(bean.getEntry_name_id());
			runit.setText(bean.getCompany_name());
			runit.setEditTextTag(bean.getComany_id());
			djperson.setText(bean.getFill_in_user_name());
			djperson.setEditTextTag(bean.getFill_in_user_id());
			CustomeEditText2 zichangnum = (CustomeEditText2) child.findViewById(R.id.dm_tz_zichangnum);
			zichangnum.setText(bean.getAsset_number());
			CustomeEditText2 zichangname = (CustomeEditText2) child.findViewById(R.id.dm_tz_zichangname);
			zichangname.setText(bean.getAsset_name());
			CustomeEditText2 guigemodel = (CustomeEditText2) child.findViewById(R.id.dm_tz_guigemodel);
			guigemodel.setText(bean.getSpecification_model());
			CustomeEditText2 scfactory = (CustomeEditText2) child.findViewById(R.id.dm_tz_scfactory);
			scfactory.setText(bean.getManufacturer());
			CustomeEditText2 slnum = (CustomeEditText2) child.findViewById(R.id.dm_tz_slnum);
			slnum.setText(bean.getSize()+"");
			sctime.setText(bean.getProduction_date());
			trytime.setText(bean.getPre_test_time());
			EditText bz = (EditText) child.findViewById(R.id.dm_tz_bz);
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

