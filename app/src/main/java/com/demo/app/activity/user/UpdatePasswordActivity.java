package com.demo.app.activity.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.bean.UserBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText3;

public class UpdatePasswordActivity extends BaseActivity implements
		OnClickListener {
	private Button getCodeBtn, registerBtn;
	private CustomeEditText3 phone,code,password,cpassword;
	private CountDownTimer timer;
	private TextView endText;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				getCodeBtn.setBackgroundResource(R.color.gray);
				getCodeBtn.setClickable(false);
				timer.start();
				break;
			case 1:
				getCodeBtn.setText("重新获得("+Integer.parseInt(msg.obj.toString())+")");
				break;
			case 2:
				getCodeBtn.setClickable(true);
				getCodeBtn.setText("获取验证码");
				getCodeBtn.setBackgroundResource(R.drawable.button_exit_shape);
				break;
			case 3:
				String message = msg.obj.toString();
				if("1".equals(message)){
					Toast.makeText(UpdatePasswordActivity.this, "验证码已失效", Toast.LENGTH_SHORT).show();					
				}else if("2".equals(message)){
					Toast.makeText(UpdatePasswordActivity.this, "不存在的手机号码", Toast.LENGTH_SHORT).show();					
				}else if("3".equals(message)){
					Toast.makeText(UpdatePasswordActivity.this, "手机号码已经被注册", Toast.LENGTH_SHORT).show();					
				}else if("4".equals(message)){
					Toast.makeText(UpdatePasswordActivity.this, "验证码已经发送", Toast.LENGTH_SHORT).show();					
				}else if("5".equals(message)){
					Toast.makeText(UpdatePasswordActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();					
				}else if("6".equals(message)){
					Toast.makeText(UpdatePasswordActivity.this, "错误的注册用户类型", Toast.LENGTH_SHORT).show();					
				}else{
					Toast.makeText(UpdatePasswordActivity.this, "注册失败", Toast.LENGTH_SHORT).show();					
				}
				break;
			case 4:
				Toast.makeText(UpdatePasswordActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();					
				break;
			case 5:
				timer.cancel();
//				Intent registerIntent = new Intent();
//				registerIntent.setClass(UpdatePasswordActivity.this, RegisterActivity.class);
//				startActivity(registerIntent);
				UpdatePasswordActivity.this.finish();
				break;
			default:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewWithoutTitle(R.layout.update_password_layout);
		String type = getIntent().getStringExtra("type");
		String title = "修改密码";
		if("forget".equals(type)){
			title = "忘记密码";
		}
		TitleCommon.setTitle(this, null, title, TabMainActivity.class, true);
		timer = new CountDownTimer(60000, 1000) {
			public void onTick(long millisUntilFinished) {
				Log.e("+++", millisUntilFinished/1000+"");
				handler.obtainMessage(1,millisUntilFinished/1000).sendToTarget();
			}
			public void onFinish() {
				handler.obtainMessage(2).sendToTarget();
			}
		};
		getCodeBtn = (Button) this.findViewById(R.id.update_register_getcode);
		registerBtn = (Button) this.findViewById(R.id.update_register_register);
		getCodeBtn.setOnClickListener(this);
		registerBtn.setOnClickListener(this);

		phone = (CustomeEditText3)this.findViewById(R.id.update_register_phone);
		code = (CustomeEditText3)this.findViewById(R.id.update_register_code);
		password = (CustomeEditText3)this.findViewById(R.id.update_register_password);
		cpassword = (CustomeEditText3)this.findViewById(R.id.update_register_confirmpassword);
		endText = (TextView)this.findViewById(R.id.update_register_end);
		endText.setText(Html.fromHtml("已有账号？<font color=\"#ff8200\">点此登陆</font>"));
		endText.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.update_register_getcode:
			String phoneValue = phone.getText();
			if("".equals(phoneValue)||!isMobile(phoneValue)){
				Toast.makeText(UpdatePasswordActivity.this, "请输入正确的电话号码！", Toast.LENGTH_SHORT).show();
			}else{
				NetworkData.getInstance().smsCode(new NetworkResponceFace() {
					
					@Override
					public void callback(String result) {
						// TODO Auto-generated method stub
						Log.e("regist", result);
						try {
							JSONObject json = new JSONObject(result);
							String status = json.getString("status");
							if("success".equals(status)){
								//成功
								handler.obtainMessage(0).sendToTarget();
							}else{
								//失败
								handler.obtainMessage(4).sendToTarget();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, phoneValue);
			}
			break;
		case R.id.update_register_register:
			String phoneV = phone.getText();
			String codeValue = code.getText();
			String passwordValue = password.getText();
			String cpasswordValue = cpassword.getText();
			if("".equals(phoneV)){
				Toast.makeText(UpdatePasswordActivity.this, "请输入正确的电话号码！", Toast.LENGTH_SHORT).show();
			}else if("".equals(codeValue)||codeValue.length()!=6){
				Toast.makeText(UpdatePasswordActivity.this, "请输入正确的验证码！", Toast.LENGTH_SHORT).show();
			}else if("".equals(passwordValue)||passwordValue.length()<6||!cpasswordValue.equals(passwordValue)||passwordValue.length()>16){
				Toast.makeText(UpdatePasswordActivity.this, "请输入正确的密码！", Toast.LENGTH_SHORT).show();
			}else{
				NetworkData.getInstance().updatePassword(new NetworkResponceFace() {
					
					@Override
					public void callback(String result) {
						// TODO Auto-generated method stub
						Log.e("regist", result);
//						TitleCommon.handlerRegist(handler, QuickRegisterActivity.this, result);
						JSONObject json;
						try {
							json = new JSONObject(result);
							String status = json.getString("status");
							if("success".equals(status)){
								//成功
								handler.obtainMessage(5).sendToTarget();
							}else{
								//失败
								String message = json.getString("message");
								handler.obtainMessage(3,message).sendToTarget();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, phoneV,codeValue,passwordValue);
			}
		
			break;
		case R.id.update_register_end:
			UpdatePasswordActivity.this.finish();
			break;
		default:
			break;
		}
	}
	/**
	 * 验证手机号码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
}
