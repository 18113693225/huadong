package com.demo.app.activity.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
//被特许人注册
public class FranchiseeRegisterActivity extends BaseActivity implements View.OnClickListener {
	private Button franchiseCompanyBtn,franchisePersonalBtn,companyRegist,personRegist;
	private TableLayout companyTable,personalTable;
	//公司
	private CustomeEditText companyName,name,zhizaonum,companyAddr/*,companyContactPerson*/,companyPhone,companyjmArea/*,companyPassword*/;
	//个人
	private CustomeEditText personalName,personalID,personalAddr,personalPhone,personalArea/*,personalPassword*/;
	private SharedPreferences sp;
	private TextView userProtocolCpTextview,userProtocolPeTextview;
	private CheckBox userProtocolCpCheckbox,userProtocolPeCheckbox;
	private Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewWithoutTitle(R.layout.register_franchise_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this,null, "加盟商注册", RegisterActivity.class,true);
		initLayout();
	}
	@SuppressLint("NewApi")
	public void initLayout(){
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		franchiseCompanyBtn = (Button)this.findViewById(R.id.franchise_company);
		franchisePersonalBtn = (Button)this.findViewById(R.id.franchise_personal);
		franchiseCompanyBtn.setOnClickListener(this);
		franchisePersonalBtn.setOnClickListener(this);
		companyTable = (TableLayout)this.findViewById(R.id.company_tablelayout);
		personalTable = (TableLayout)this.findViewById(R.id.personal_tablelayout);
		//公司
		companyName = (CustomeEditText)this.findViewById(R.id.franchise_companyName);
		name = (CustomeEditText)this.findViewById(R.id.franchise_name);
		zhizaonum = (CustomeEditText)this.findViewById(R.id.franchise_zhizaonum);
		companyAddr = (CustomeEditText)this.findViewById(R.id.franchise_companyAddr);
//		companyContactPerson = (CustomeEditText)this.findViewById(R.id.franchise_companyContactPerson);
		companyPhone = (CustomeEditText)this.findViewById(R.id.franchise_companyPhone);
		companyjmArea = (CustomeEditText)this.findViewById(R.id.franchise_companyjmArea);
//		companyPassword = (CustomeEditText)this.findViewById(R.id.franchise_companyPassword);
		TextView endText = (TextView)this.findViewById(R.id.franchise_exist_login);
		endText.setText(Html.fromHtml("已有账号？<font color=\"#ff8200\">点此登陆</font>"));
		endText.setOnClickListener(this);
		companyRegist = (Button)this.findViewById(R.id.franchise_companyRegist);
		companyRegist.setBackgroundResource(R.color.gray);
		companyRegist.setOnClickListener(this);
		//个人
		personalName = (CustomeEditText)this.findViewById(R.id.franchise_personalName);
		personalID = (CustomeEditText)this.findViewById(R.id.franchise_personalID);
		personalAddr = (CustomeEditText)this.findViewById(R.id.franchise_personalAddr);
		personalPhone = (CustomeEditText)this.findViewById(R.id.franchise_personalPhone);
		personalArea = (CustomeEditText)this.findViewById(R.id.franchise_personalArea);
//		personalPassword = (CustomeEditText)this.findViewById(R.id.franchise_personalPassword);
		TextView endTextp = (TextView)this.findViewById(R.id.franchise_pexist_login);
		endTextp.setText(Html.fromHtml("已有账号？<font color=\"#ff8200\">点此登陆</font>"));
		endTextp.setOnClickListener(this);
		personRegist = (Button)this.findViewById(R.id.franchise_personRegist);
		personRegist.setBackgroundResource(R.color.gray);
		personRegist.setOnClickListener(this);
		
		userProtocolCpCheckbox = (CheckBox)this.findViewById(R.id.userProtocolCpCheckbox);
		userProtocolCpCheckbox.setOnClickListener(this);
		userProtocolCpTextview = (TextView)this.findViewById(R.id.userProtocolCpTextview);
		userProtocolCpTextview.setOnClickListener(this);
		userProtocolPeCheckbox = (CheckBox)this.findViewById(R.id.userProtocolPeCheckbox);
		userProtocolPeCheckbox.setOnClickListener(this);
		userProtocolPeTextview = (TextView)this.findViewById(R.id.userProtocolPeTextview);
		userProtocolPeTextview.setOnClickListener(this);
	}
	//公司
	public void registCompany(){
		String company_Name = companyName.getText();
		String name_value = name.getText();
		String zhizao_num = zhizaonum.getText();
		String company_Addr = companyAddr.getText();
//		String company_ContactPerson = companyContactPerson.getText();
		String company_Phone = companyPhone.getText();
		String company_jmArea = companyjmArea.getText();
//		String company_Password = companyPassword.getText();
		String oper = getCustomeButtonValue(companyTable, "franchise_coper");
		
		UserBean bean = new UserBean();
		bean.setUser_type(2);
		bean.setRegest_setup(2);
		bean.setCompany_name(company_Name);
		bean.setUser_name(name_value);
		bean.setLicense_number(zhizao_num);
		bean.setCompany_address(company_Addr);
//		bean.setContacts(company_ContactPerson);
//		bean.setPhone(company_Phone);
		bean.setContact_information(company_Phone);
		bean.setPhone(sp.getString("userphone", ""));
		bean.setJoining_region(company_jmArea);
		if("5".equals(oper)){
			bean.setOther_info_c(getCustomeButtonText(companyTable, "franchise_coper"));
			Log.i("franchise_coper---others", getCustomeButtonText(companyTable, "franchise_coper"));
		}
		bean.setCooperation_intention(oper);
//		bean.setPassword(company_Password);
		bean.setCompany_or_personal(0);//公司
		if(!"".equals(company_Phone)&&!"".equals(name_value)){
			NetworkData.getInstance().regist(new NetworkResponceFace() {
				
				@Override
				public void callback(String result) {
					// TODO Auto-generated method stub
					Log.e("regist", result);
					TitleCommon.handlerRegist(handler, FranchiseeRegisterActivity.this, result);
				}
			}, bean);
		}else{
			Toast.makeText(FranchiseeRegisterActivity.this, "有必填项未输入！", Toast.LENGTH_LONG).show();
		}
	}
	//个人
	public void registPerson(){
		String personal_Name = personalName.getText();
		String personal_ID = personalID.getText();
		String personal_Addr = personalAddr.getText();
		String personal_Phone = personalPhone.getText();
		String personal_Area = personalArea.getText();
//		String personal_Password = personalPassword.getText();
		UserBean bean = new UserBean();
		bean.setUser_type(2);
		bean.setRegest_setup(2);
		bean.setUser_name(personal_Name);
		bean.setCard_id(personal_ID);
		bean.setAddress(personal_Addr);
//		bean.setPhone(personal_Phone);
		bean.setContact_information(personal_Phone);
		bean.setPhone(sp.getString("userphone", ""));
		bean.setJoining_region(personal_Area);
//		bean.setPassword(personal_Password);
		String oper = getCustomeButtonValue(companyTable, "franchise_person_coper");
		if("5".equals(oper)){
			bean.setOther_info_d(getCustomeButtonText(companyTable, "franchise_person_coper"));
			Log.i("franchise_person_coper---others", getCustomeButtonText(companyTable, "franchise_person_coper"));
		}
		bean.setCooperation_intention(oper);
		bean.setCompany_or_personal(1);//个人
		if (!"".equals(personal_Name) && !"".equals(personal_ID) && !"".equals(personal_Addr)
				&& !"".equals(personal_Phone)) {
			NetworkData.getInstance().regist(new NetworkResponceFace() {

				@Override
				public void callback(String result) {
					// TODO Auto-generated method stub
					Log.e("regist", result);
					TitleCommon.handlerRegist(handler, FranchiseeRegisterActivity.this, result);
				}
			}, bean);
		} else {
			Toast.makeText(FranchiseeRegisterActivity.this, "有必填项未输入！", Toast.LENGTH_LONG).show();
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.franchise_company:
			franchiseCompanyBtn.setBackgroundResource(R.drawable.button_franchise_shape);
			franchisePersonalBtn.setBackgroundResource(R.drawable.button_franchise_shape1);
			companyTable.setVisibility(View.VISIBLE);
			personalTable.setVisibility(View.GONE);
			break;
		case R.id.franchise_personal:
			franchiseCompanyBtn.setBackgroundResource(R.drawable.button_franchise_shape1);
			franchisePersonalBtn.setBackgroundResource(R.drawable.button_franchise_shape);
			companyTable.setVisibility(View.GONE);
			personalTable.setVisibility(View.VISIBLE);
			break;
		case R.id.franchise_companyRegist:
			registCompany();
			break;
		case R.id.franchise_personRegist:
			registPerson();
			break;
		case R.id.franchise_exist_login:
		case R.id.franchise_pexist_login:
			Intent i = new Intent();
			i.setClass(FranchiseeRegisterActivity.this, TabMainActivity.class);
			startActivity(i);
			break;
		case R.id.userProtocolCpCheckbox:
			boolean flag = userProtocolCpCheckbox.isChecked();
			if(flag){
				companyRegist.setClickable(true);
				companyRegist.setBackgroundResource(R.drawable.button_login_shape);
			}else{
				companyRegist.setClickable(false);
				companyRegist.setBackgroundResource(R.color.gray);
			}
			break;
		case R.id.userProtocolPeCheckbox:
			boolean flagPe = userProtocolPeCheckbox.isChecked();
			if(flagPe){
				personRegist.setClickable(true);
				personRegist.setBackgroundResource(R.drawable.button_login_shape);
			}else{
				personRegist.setClickable(false);
				personRegist.setBackgroundResource(R.color.gray);
			}
			break;
		case R.id.userProtocolCpTextview:
		case R.id.userProtocolPeTextview:
			Intent intent = new Intent();
			intent.setClass(this, UserProtocolActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	/**
	 * 获取自定义button值
	 * @param tag
	 * @return
	 */
	public String getCustomeButtonValue(TableLayout table, String tag){
		String value = "1";
		int count = table.getChildCount();
		for(int i=0;i<count;i++){
			View child = table.getChildAt(i);
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
	 * 获取自定义button 文字内容
	 * @param tag
	 * @return
	 */
	public String getCustomeButtonText(TableLayout table, String tag){
		String value = "其它";
		int count = table.getChildCount();
		for(int i=0;i<count;i++){
			View child = table.getChildAt(i);
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
}
