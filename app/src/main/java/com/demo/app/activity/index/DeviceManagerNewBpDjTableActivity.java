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
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.bean.DateBean;
import com.demo.app.bean.SparePartsBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;

public class DeviceManagerNewBpDjTableActivity extends BaseActivity {
	private CustomeEditText2 pname,runit,djperson;
	private Button saveBtn;
	private TextView addDevice;
	private LinearLayout commonLayout;
	private SharedPreferences sp;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(DeviceManagerNewBpDjTableActivity.this, "新建成功", Toast.LENGTH_LONG).show();
				DeviceManagerNewBpDjTableActivity.this.finish();
			} else if (msg.what == 2) {
				Toast.makeText(DeviceManagerNewBpDjTableActivity.this, "新建失败", Toast.LENGTH_LONG).show();
			} else if (msg.what == 3) {
				Toast.makeText(DeviceManagerNewBpDjTableActivity.this, "有必填项未填", Toast.LENGTH_LONG).show();
			}else if(msg.what == 4){
				Toast.makeText(DeviceManagerNewBpDjTableActivity.this, "添加设备大于已有设备数量", Toast.LENGTH_LONG).show();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_device_manager_new_bpbj_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "新建备品备件登记簿",DeviceManagerBpDjTableActivity.class, true);
		TitleCommon.setGpsTitle(this);
		Constents.xscontentList.clear();
		Constents.devicenum = 0;
		commonLayout = (LinearLayout)this.findViewById(R.id.dm_bpbj_commonLayout);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		pname = (CustomeEditText2)this.findViewById(R.id.dm_bpbj_pname);
		runit = (CustomeEditText2)this.findViewById(R.id.dm_bpbj_runit);
		djperson = (CustomeEditText2)this.findViewById(R.id.dm_bpbj_djperson);
		djperson.setText(sp.getString("username", ""));
		findViewById(R.id.dm_bpbj_addDevice).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int count = commonLayout.getChildCount();
				if(count>=Constents.devicenum){
					handler.obtainMessage(4).sendToTarget();
				}else{
					addDevice();					
				}
			}
		});
		addDevice();
		findViewById(R.id.dm_bpbj_saveBtn).setOnClickListener(new OnClickListener() {
			
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
						SparePartsBean bean = new SparePartsBean();
						
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
							if(tag.equals("dm_bpbj_dname")){//设备名称
								if(((CustomeEditText2)child).getEditTexTag()==-1){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setDevice_id(((CustomeEditText2)child).getEditTexTag());
							}else if(tag.equals("dm_bpbj_guige")){//规格
								if("".equals(((CustomeEditText2)child).getText().toString())){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setSpecifications(((CustomeEditText2)child).getText().toString());								
							}else if(tag.equals("dm_bpbj_rukunum")){//入库数量
								if("".equals(((CustomeEditText2)child).getText().toString())){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setStorage_quantity(((CustomeEditText2)child).getText().toString());
							}else if(tag.equals("dm_bpbj_rukutime")){//入库时间
								if("".equals(((CustomeEditText2)child).getText().toString())){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setStorage_time(((CustomeEditText2)child).getText().toString());
							}else if(tag.equals("dm_bpbj_chukunum")){//出库数量
								if("".equals(((CustomeEditText2)child).getText().toString())){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setOutgoing_quantity(((CustomeEditText2)child).getText().toString());
							}else if(tag.equals("dm_bpbj_chukutime")){//出库时间
								if("".equals(((CustomeEditText2)child).getText().toString())){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setDelivery_time(((CustomeEditText2)child).getText().toString());
							}else if(tag.equals("dm_bpbj_kuchunyuer")){//余额
								if("".equals(((CustomeEditText2)child).getText().toString())){
									handler.obtainMessage(3).sendToTarget();
									return;
								}
								bean.setStock_balance(((CustomeEditText2)child).getText().toString());
							}
						}
						list.add(bean);
					}
					NetworkData.getInstance().sparePartsAdd(new NetworkResponceFace() {
	
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
	public void addDevice(){
		View child =  getLayoutInflater().inflate(R.layout.common_bpbj_layout, null);
		CustomeEditText2 dname = (CustomeEditText2) child.findViewById(R.id.dm_bpbj_dname);
		dname.setId(View.generateViewId());
		CustomeEditText2 rukutime = (CustomeEditText2) child.findViewById(R.id.dm_bpbj_rukutime);
		rukutime.setId(View.generateViewId());
		CustomeEditText2 chukutime = (CustomeEditText2) child.findViewById(R.id.dm_bpbj_chukutime);
		chukutime.setId(View.generateViewId());
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

