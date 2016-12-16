package com.demo.app.activity.user;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.bean.UserBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
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
import android.widget.TextView;
import android.widget.Toast;
//ESpread用户注册
public class ESpreadRegisterActivity extends BaseActivity implements View.OnClickListener{
	private CustomeEditText companyName,companyPhone,companyAddr,BankName,companyBankCardID/*,companyPassword*/;
	private Button espread_regist;
	private SharedPreferences sp;
	private TextView userProtocolTextview;
	private CheckBox userProtocolCheckbox;
	private Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewWithoutTitle(R.layout.register_espread_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this,null, "E推广注册", RegisterActivity.class,true);
		initLayout();
	}
	
	public void initLayout(){
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		userProtocolCheckbox = (CheckBox)this.findViewById(R.id.userProtocolEsCheckbox);
		userProtocolCheckbox.setOnClickListener(this);
		userProtocolTextview = (TextView)this.findViewById(R.id.userProtocolEsTextview);
		userProtocolTextview.setOnClickListener(this);
		companyName = (CustomeEditText)this.findViewById(R.id.espread_companyName);
		companyPhone = (CustomeEditText)this.findViewById(R.id.espread_companyPhone);
		companyAddr = (CustomeEditText)this.findViewById(R.id.espread_companyAddr);
		BankName = (CustomeEditText)this.findViewById(R.id.espread_companyBankName);
		companyBankCardID = (CustomeEditText)this.findViewById(R.id.espread_companyBankCardID);
//		companyPassword = (CustomeEditText)this.findViewById(R.id.espread_companyPassword);
		TextView endText = (TextView)this.findViewById(R.id.espread_exist_login);
		endText.setText(Html.fromHtml("已有账号？<font color=\"#ff8200\">点此登陆</font>"));
		endText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(ESpreadRegisterActivity.this, TabMainActivity.class);
				startActivity(i);
			}
		});
		espread_regist = (Button)this.findViewById(R.id.espread_regist);
		espread_regist.setBackgroundResource(R.color.gray);
		espread_regist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = companyName.getText();
				String phone = companyPhone.getText();
				String addr = companyAddr.getText();
				String Bank_Name = BankName.getText();
				String BankCardID = companyBankCardID.getText();
//				String password = companyPassword.getText();
				UserBean bean = new UserBean();
				bean.setUser_type(4);
				bean.setRegest_setup(2);
				bean.setUser_name(name);
//				bean.setPhone(phone);
				bean.setPhone(sp.getString("userphone", ""));
				bean.setAddress(addr);
				bean.setCard_bank(Bank_Name);
				bean.setCard_number(BankCardID);
//				bean.setPassword(password);
				if(!"".equals(name)&&!"".equals(phone)&&!"".equals(addr)/*&&!"".equals(password)*/){
					NetworkData.getInstance().regist(new NetworkResponceFace() {
						
						@Override
						public void callback(String result) {
							// TODO Auto-generated method stub
							Log.e("regist", result);
							TitleCommon.handlerRegist(handler, ESpreadRegisterActivity.this, result);
						}
					}, bean);
				}else{
					Toast.makeText(ESpreadRegisterActivity.this, "有必填项未输入！", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.userProtocolEsTextview:
			Intent i = new Intent();
			i.setClass(this, UserProtocolActivity.class);
			startActivity(i);
			break;
		case R.id.userProtocolEsCheckbox:
			boolean flag = userProtocolCheckbox.isChecked();
			if(flag){
				espread_regist.setClickable(true);
				espread_regist.setBackgroundResource(R.drawable.button_login_shape);
			}else{
				espread_regist.setClickable(false);
				espread_regist.setBackgroundResource(R.color.gray);
			}
			break;
		default:
			break;
		}
	}
}
