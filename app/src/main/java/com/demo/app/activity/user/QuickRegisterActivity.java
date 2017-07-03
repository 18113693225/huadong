package com.demo.app.activity.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText3;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuickRegisterActivity extends BaseActivity implements
        OnClickListener {
    private Button getCodeBtn, registerBtn;
    private CustomeEditText3 phone, code, password;
    private CountDownTimer timer;
    private TextView endText;
    private SharedPreferences sp;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    getCodeBtn.setBackgroundResource(R.color.gray);
                    getCodeBtn.setClickable(false);
                    timer.start();
                    break;
                case 1:
                    getCodeBtn.setText("重新获得(" + Integer.parseInt(msg.obj.toString()) + ")");
                    break;
                case 2:
                    getCodeBtn.setClickable(true);
                    getCodeBtn.setText("获取验证码");
                    getCodeBtn.setBackgroundResource(R.drawable.button_exit_shape);
                    break;
                case 3:
                    String message = msg.obj.toString();
                    if ("1".equals(message)) {
                        Toast.makeText(QuickRegisterActivity.this, "验证码已失效", Toast.LENGTH_SHORT).show();
                    } else if ("2".equals(message)) {
                        Toast.makeText(QuickRegisterActivity.this, "不存在的手机号码", Toast.LENGTH_SHORT).show();
                    } else if ("3".equals(message)) {
                        Toast.makeText(QuickRegisterActivity.this, "手机号码已经被注册", Toast.LENGTH_SHORT).show();
                    } else if ("4".equals(message)) {
                        Toast.makeText(QuickRegisterActivity.this, "验证码已经发送", Toast.LENGTH_SHORT).show();
                    } else if ("5".equals(message)) {
                        Toast.makeText(QuickRegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    } else if ("6".equals(message)) {
                        Toast.makeText(QuickRegisterActivity.this, "错误的注册用户类型", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(QuickRegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 4:
                    Toast.makeText(QuickRegisterActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    timer.cancel();
                    Intent registerIntent = new Intent();
                    registerIntent.setClass(QuickRegisterActivity.this, RegisterActivity.class);
                    startActivity(registerIntent);
                    QuickRegisterActivity.this.finish();
                    break;
                default:
                    break;
            }
        }

        ;
    };

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
        p = Pattern.compile("^[1][0-9][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentViewWithoutTitle(R.layout.quick_register_layout);
        TitleCommon.setTitle(this, null, "快速注册", TabMainActivity.class, true);
        sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
        timer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                Log.e("+++", millisUntilFinished / 1000 + "");
                handler.obtainMessage(1, millisUntilFinished / 1000).sendToTarget();
            }

            public void onFinish() {
                handler.obtainMessage(2).sendToTarget();
            }
        };
        getCodeBtn = (Button) this.findViewById(R.id.quick_register_getcode);
        registerBtn = (Button) this.findViewById(R.id.quick_register_register);
        getCodeBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

        phone = (CustomeEditText3) this.findViewById(R.id.quick_register_phone);
        code = (CustomeEditText3) this.findViewById(R.id.quick_register_code);
        password = (CustomeEditText3) this.findViewById(R.id.quick_register_password);
        endText = (TextView) this.findViewById(R.id.quick_register_end);
        endText.setText(Html.fromHtml("已有账号？<font color=\"#ff8200\">点此登陆</font>"));
        endText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            case R.id.quick_register_getcode:
                final String phoneValue = phone.getText();
                if ("".equals(phoneValue) || !isMobile(phoneValue)) {
                    Toast.makeText(QuickRegisterActivity.this, "请输入正确的电话号码！", Toast.LENGTH_SHORT).show();
                } else {
                    NetworkData.getInstance().smsCode(new NetworkResponceFace() {

                        @Override
                        public void callback(String result) {
                            // TODO Auto-generated method stub
                            Log.e("regist", result);
                            try {
                                JSONObject json = new JSONObject(result);
                                String status = json.getString("status");
                                if ("success".equals(status)) {
                                    //成功
                                    handler.obtainMessage(0).sendToTarget();
                                    sp.edit().putString("userphone", phoneValue).commit();
                                } else {
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
            case R.id.quick_register_register:
                String phoneV = phone.getText();
                String codeValue = code.getText();
                String passwordValue = password.getText();
                if ("".equals(phoneV)) {
                    Toast.makeText(QuickRegisterActivity.this, "请输入正确的电话号码！", Toast.LENGTH_SHORT).show();
                } else if ("".equals(codeValue) || codeValue.length() != 6) {
                    Toast.makeText(QuickRegisterActivity.this, "请输入正确的验证码！", Toast.LENGTH_SHORT).show();
                } else if ("".equals(passwordValue) || passwordValue.length() < 6 || passwordValue.length() > 16) {
                    Toast.makeText(QuickRegisterActivity.this, "请输入正确的密码！", Toast.LENGTH_SHORT).show();
                } else {
                    UserBean bean = new UserBean();
                    bean.setSms_code(codeValue);
                    bean.setPassword(passwordValue);
                    bean.setRegest_setup(1);
//				bean.setUser_type(5);
                    bean.setPhone(phoneV);
                    NetworkData.getInstance().regist(new NetworkResponceFace() {

                        @Override
                        public void callback(String result) {
                            // TODO Auto-generated method stub
                            Log.e("regist", result);
//						TitleCommon.handlerRegist(handler, QuickRegisterActivity.this, result);
                            JSONObject json;
                            try {
                                json = new JSONObject(result);
                                String status = json.getString("status");
                                if ("success".equals(status)) {
                                    //成功
                                    handler.obtainMessage(5).sendToTarget();
                                } else {
                                    //失败
                                    String message = json.getString("message");
                                    handler.obtainMessage(3, message).sendToTarget();
                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }, bean);
                }

                break;
            case R.id.quick_register_end:
                QuickRegisterActivity.this.finish();
                break;
            default:
                break;
        }
    }
}
