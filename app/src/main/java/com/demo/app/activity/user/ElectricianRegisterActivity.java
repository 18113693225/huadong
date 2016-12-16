package com.demo.app.activity.user;

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
//电工注册
public class ElectricianRegisterActivity extends BaseActivity implements View.OnClickListener{
	private CustomeEditText electrician_name,electrician_contactType,electrician_ID,electrician_certificateID/*,electrician_password*/;
	private Button electrician_regist;
	private TableLayout electrician_table;
	private Handler handler = new Handler();
	private TextView userProtocolTextview;
	private CheckBox userProtocolCheckbox;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewWithoutTitle(R.layout.register_electrician_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this,null, "电工注册", RegisterActivity.class,true);
		initLayout();
	}
	
	public void initLayout(){
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		userProtocolCheckbox = (CheckBox)this.findViewById(R.id.userProtocolCheckbox);
		userProtocolCheckbox.setOnClickListener(this);
		userProtocolTextview = (TextView)this.findViewById(R.id.userProtocolTextview);
		userProtocolTextview.setOnClickListener(this);
		
		electrician_table = (TableLayout)this.findViewById(R.id.electrician_table);
		
		electrician_name = (CustomeEditText)this.findViewById(R.id.electrician_name);
		electrician_contactType = (CustomeEditText)this.findViewById(R.id.electrician_contactType);
		electrician_ID = (CustomeEditText)this.findViewById(R.id.electrician_ID);
		electrician_certificateID = (CustomeEditText)this.findViewById(R.id.electrician_certificateID);
//		electrician_password = (CustomeEditText)this.findViewById(R.id.electrician_password);
		TextView endText = (TextView)this.findViewById(R.id.electrician_exist_login);
		endText.setText(Html.fromHtml("已有账号？<font color=\"#ff8200\">点此登陆</font>"));
		endText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(ElectricianRegisterActivity.this, TabMainActivity.class);
				startActivity(i);
			}
		});
		electrician_regist = (Button)this.findViewById(R.id.electrician_regist);
		electrician_regist.setBackgroundResource(R.color.gray);
		electrician_regist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = electrician_name.getText();
				String phone = electrician_contactType.getText();
				String ID = electrician_ID.getText();
				String certificateID = electrician_certificateID.getText();
//				String password = electrician_password.getText();
				String sex = getCustomeButtonValue(electrician_table, "sex");
				String major = getCustomeButtonValue(electrician_table, "major");
				String certificate =  getCustomeButtonValue(electrician_table, "book");
				UserBean bean = new UserBean();
				bean.setUser_type(3);
				bean.setRegest_setup(2);
				bean.setUser_name(name);
				bean.setPhone(sp.getString("userphone", ""));
				bean.setSex(sex.charAt(0));
				bean.setCard_id(ID);
				bean.setProfessional(major);
				bean.setCoa(certificate);
				bean.setCoa_number(certificateID);
				bean.setContact_information(phone);
//				bean.setPassword(password);
				
				if(!"".equals(name)&&!"".equals(phone)&&!"".equals(ID)/*&&!"".equals(password)*/){
					NetworkData.getInstance().regist(new NetworkResponceFace() {
						
						@Override
						public void callback(String result) {
							// TODO Auto-generated method stub
							Log.e("regist", result);
							TitleCommon.handlerRegist(handler, ElectricianRegisterActivity.this, result);
						}
					}, bean);
				}else{
					Toast.makeText(ElectricianRegisterActivity.this, "有必填项未输入！", Toast.LENGTH_LONG).show();
				}
			}
		});
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.userProtocolTextview:
			Intent i = new Intent();
			i.setClass(this, UserProtocolActivity.class);
			startActivity(i);
			break;
		case R.id.userProtocolCheckbox:
			boolean flag = userProtocolCheckbox.isChecked();
			if(flag){
				electrician_regist.setClickable(true);
				electrician_regist.setBackgroundResource(R.drawable.button_login_shape);
			}else{
				electrician_regist.setClickable(false);
				electrician_regist.setBackgroundResource(R.color.gray);
			}
			break;
		default:
			break;
		}
	}
}
