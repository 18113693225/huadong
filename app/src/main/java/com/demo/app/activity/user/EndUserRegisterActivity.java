package com.demo.app.activity.user;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.bean.UserBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeButton;
import com.demo.app.view.CustomeEditText;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
//终端用户注册
public class EndUserRegisterActivity extends BaseActivity implements View.OnClickListener {
	private CustomeEditText companyName,name,zhizhaonum/*,contactType*/,companyAddr,guimo,tailength/*,password*/;
	private Button endUser_register;
	private TableLayout endUser_tableLayout;
	private SharedPreferences sp;
	private TextView userProtocolTextview;
	private CheckBox userProtocolCheckbox;
	private Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewWithoutTitle(R.layout.register_enduse_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this,null, "终端用户注册", RegisterActivity.class,true);
		initLayout();
	}
	
	public void initLayout(){
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		userProtocolCheckbox = (CheckBox)this.findViewById(R.id.userProtocolEuCheckbox);
		userProtocolCheckbox.setOnClickListener(this);
		userProtocolTextview = (TextView)this.findViewById(R.id.userProtocolEuTextview);
		userProtocolTextview.setOnClickListener(this);
		
		endUser_tableLayout = (TableLayout)findViewById(R.id.endUser_tableLayout);
		companyName = (CustomeEditText)findViewById(R.id.endUser_companyName);
		name = (CustomeEditText)findViewById(R.id.endUser_name);
		zhizhaonum = (CustomeEditText)findViewById(R.id.endUser_zhizhaonum);
//		contactType = (CustomeEditText)findViewById(R.id.endUser_contactType);
		companyAddr = (CustomeEditText)findViewById(R.id.endUser_companyAddr);
		guimo = (CustomeEditText)findViewById(R.id.endUser_guimo);
		tailength = (CustomeEditText)findViewById(R.id.endUser_tailength);
//		password = (CustomeEditText)findViewById(R.id.endUser_password);
		TextView endText = (TextView)this.findViewById(R.id.endUser_exist_login);
		endText.setText(Html.fromHtml("已有账号？<font color=\"#ff8200\">点此登陆</font>"));
		endText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(EndUserRegisterActivity.this, TabMainActivity.class);
				startActivity(i);
			}
		});
		endUser_register = (Button)findViewById(R.id.endUser_register);
		endUser_register.setBackgroundResource(R.color.gray);
		endUser_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String company_Name =companyName.getText();
				String conn_name = name.getText();
				String zhizhao_num = zhizhaonum.getText();
//				String contact_Type = contactType.getText();
				String company_Addr = companyAddr.getText();
				String gui_mo = guimo.getText();
				String tai_length = tailength.getText();
//				String pass = password.getText();
				String type = getCustomeButtonValue("type");
				String level = getCustomeButtonValue("level");
				String ct = getCustomeButtonValue("ct");
				String coper = getCustomeButtonValue("coper");
				UserBean bean = new UserBean();
				bean.setUser_type(1);
				bean.setRegest_setup(2);
				bean.setCompany_name(company_Name);
				bean.setUser_name(conn_name);
				bean.setLicense_number(zhizhao_num);
//				bean.setPhone(contact_Type);
				bean.setPhone(sp.getString("userphone", ""));
				bean.setAddress(company_Addr);
				bean.setCategory(type);
				bean.setVoltage_level(level);
				bean.setScale(Integer.parseInt("".equals(gui_mo)?"0":gui_mo));
				bean.setNumber(Integer.parseInt("".equals(tai_length)?"0":tai_length));
				bean.setIndustry_categories(ct);
				if("5".equals(ct)){
					bean.setOther_info_a(getCustomeButtonText("ct"));
					Log.i("Industry---others", getCustomeButtonText("ct"));
				}
				bean.setCooperation_intention(coper);
				if("5".equals(coper)){
					bean.setOther_info_b(getCustomeButtonText("coper"));
					Log.i("coper---others", getCustomeButtonText("coper"));
				}
//				bean.setPassword(pass);
				//暂时这样限制
				if(Integer.parseInt("".equals(gui_mo)?"0":gui_mo)>99999){
					Toast.makeText(EndUserRegisterActivity.this, "(规模/容量)数据值过大，请检查。", Toast.LENGTH_SHORT).show();
					return;
				}else if(Integer.parseInt("".equals(tai_length)?"0":tai_length)>99999){
					Toast.makeText(EndUserRegisterActivity.this, "(台数/长度)数据值过大，请检查。", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!"".equals(company_Name)&&!"".equals(conn_name)/*&&!"".equals(contact_Type)*/&&!"".equals(company_Addr)&&
						!"".equals(gui_mo)&&!"".equals(tai_length)/*&&!"".equals(pass)*/){
					NetworkData.getInstance().regist(new NetworkResponceFace() {
						
						@Override
						public void callback(String result) {
							// TODO Auto-generated method stub
							Log.e("regist", result);
							TitleCommon.handlerRegist(handler, EndUserRegisterActivity.this, result);
						}
					}, bean);
				}else{
					Toast.makeText(EndUserRegisterActivity.this, "有必填项未输入！", Toast.LENGTH_LONG).show();
				}
				
				System.out.println(company_Name+"-"+zhizhao_num+"-"+company_Addr+"-"+gui_mo+"-"+tai_length+"-"+
						type+"-"+level+"-"+ct+"-"+coper);
			}
		});
	}
	
	/**
	 * 获取自定义button值
	 * @param tag
	 * @return
	 */
	public String getCustomeButtonValue(String tag){
		String value = "1";
		int count = endUser_tableLayout.getChildCount();
		for(int i=0;i<count;i++){
			View child = endUser_tableLayout.getChildAt(i);
			if(child instanceof TableRow && tag.equals(child.getTag())){
				TableRow child_tr = (TableRow)child;
				int bt_count = child_tr.getChildCount();
				for(int j=0;j<bt_count;j++){
					View child_sd = child_tr.getChildAt(j);
					if(child_sd instanceof CustomeButton){
						CustomeButton bt = (CustomeButton)child_tr.getChildAt(j);
						if(bt.isSelected()){
							value = bt.getButtonValue();
							return value;
						}
					}
				}
			}
		}
		return "1";
	}
	/**
	 * 获取自定义button文字类容
	 * @param tag
	 * @return
	 */
	public String getCustomeButtonText(String tag){
		String value = "其它";
		int count = endUser_tableLayout.getChildCount();
		for(int i=0;i<count;i++){
			View child = endUser_tableLayout.getChildAt(i);
			if(child instanceof TableRow && tag.equals(child.getTag())){
				TableRow child_tr = (TableRow)child;
				int bt_count = child_tr.getChildCount();
				for(int j=0;j<bt_count;j++){
					View child_sd = child_tr.getChildAt(j);
					if(child_sd instanceof CustomeButton){
						CustomeButton bt = (CustomeButton)child_tr.getChildAt(j);
						if(bt.isSelected()){
							value = bt.getText().toString();
							return value;
						}
					}
				}
			}
		}
		return "其它";
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.userProtocolEuTextview:
			Intent i = new Intent();
			i.setClass(this, UserProtocolActivity.class);
			startActivity(i);
			break;
		case R.id.userProtocolEuCheckbox:
			boolean flag = userProtocolCheckbox.isChecked();
			if(flag){
				endUser_register.setClickable(true);
				endUser_register.setBackgroundResource(R.drawable.button_login_shape);
			}else{
				endUser_register.setClickable(false);
				endUser_register.setBackgroundResource(R.color.gray);
			}
			break;
		default:
			break;
		}
	}
}
