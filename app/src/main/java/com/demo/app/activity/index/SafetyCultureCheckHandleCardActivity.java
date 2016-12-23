package com.demo.app.activity.index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.user.PersonWorkManagerActivity;
import com.demo.app.bean.SafetyTrainingBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;
import com.demo.app.view.CustomeTextView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SafetyCultureCheckHandleCardActivity extends BaseActivity {
    private SharedPreferences sp;
    private CustomeEditText2 pname, ptype, runit, xsr/*,safetyselect*/;
    private Button saveBtn;
    private TextView ttime, taddress;
    private LinearLayout commonLayout;
    private EditText jxlog;
    private String oper_id;
    private String oper_type;
    private String lid;
    private String pwm;
    private List<String> ids = new ArrayList<>();
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                showData(msg.obj.toString());
            } else if (msg.what == 1) {
                Toast.makeText(SafetyCultureCheckHandleCardActivity.this, "保存成功", Toast.LENGTH_LONG).show();
                SafetyCultureCheckHandleCardActivity.this.finish();
            } else if (msg.what == 2) {
                Toast.makeText(SafetyCultureCheckHandleCardActivity.this, "保存失败", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_safety_culture_check_handle_xsk_layout);
        sp = getSharedPreferences(Constents.SHARE_CONFIG, MODE_PRIVATE);
        Constents.jxcontentList.clear();
        ids.clear();
        //判断从哪里进入的
        pwm = getIntent().getStringExtra("type");
        if (pwm != null) {
            TitleCommon.setTitle(this, null, "安全检查巡视卡", PersonWorkManagerActivity.class, true);
        } else {
            TitleCommon.setTitle(this, null, "安全检查巡视卡", SafetyCultureCheckShowActivity.class, true);
        }
        TitleCommon.setGpsTitle(this);
        oper_id = getIntent().getStringExtra("oper_id");
        oper_type = getIntent().getStringExtra("oper_type");
        lid = getIntent().getStringExtra("lid");
        commonLayout = (LinearLayout) this.findViewById(R.id.sc_check_jx_commonLayout);
        pname = (CustomeEditText2) this.findViewById(R.id.sc_check_jx_pname);
        ptype = (CustomeEditText2) this.findViewById(R.id.sc_check_jx_ptype);
        runit = (CustomeEditText2) this.findViewById(R.id.sc_check_jx_runit);
        xsr = (CustomeEditText2) this.findViewById(R.id.sc_check_jx_xsr);
//		safetyselect = (CustomeEditText2)this.findViewById(R.id.sc_check_jx_safetyselect);
        ttime = (TextView) this.findViewById(R.id.title_gps_time);
        taddress = (TextView) this.findViewById(R.id.title_gps_address);
        jxlog = (EditText) this.findViewById(R.id.sc_check_jx_jxlog);
        saveBtn = (Button) this.findViewById(R.id.sc_check_jx_saveBtn);
        saveBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String userId = sp.getInt("userId", 0) + "";
                String jxlogValue = jxlog.getText().toString();
                Gson gson = new Gson();
                NetworkData.getInstance().maintenanceTaskCardResult(new NetworkResponceFace() {

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

                }, oper_id, jxlogValue, oper_type, gson.toJson(Constents.jxcontentList),userId, ids);
            }
        });
        //个人中心工作管理
        if (getIntent().getStringExtra("data") == null) {
            getData(oper_id);
            saveBtn.setVisibility(View.GONE);
            jxlog.setVisibility(View.GONE);
            findViewById(R.id.sc_check_jx_end).setVisibility(View.INVISIBLE);
        } else {//电力运行检修任务
            showData(getIntent().getStringExtra("data"));
        }
    }

    public void getData(String id) {
        NetworkData.getInstance().maintenanceTaskCardBean(
                new NetworkResponceFace() {

                    @Override
                    public void callback(String result) {
                        // TODO Auto-generated method stub
                        JSONObject json;
                        try {
                            json = new JSONObject(result);
                            handler.obtainMessage(0, json.get("result").toString()).sendToTarget();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, id, oper_type);
    }

    public void showData(String data) {
        try {
            JSONArray rel = new JSONArray(data);
            for (int i = 0; i < rel.length(); i++) {
                JSONObject obj = (JSONObject) rel.get(i);
                Gson gson = new Gson();
                SafetyTrainingBean bean = gson.fromJson(obj.toString(), SafetyTrainingBean.class);
                ids.add(bean.getId() + "");
                addDevice(bean);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void addDevice(final SafetyTrainingBean bean) {
        View child = getLayoutInflater().inflate(R.layout.common_xsk_jx_show_layout, null);
        ttime.setText("巡检时间：" + bean.getCreate_time());
        taddress.setText("GPS定位：" + bean.getGPS());
        pname.setText(bean.getEntry_name_name());
        runit.setText(bean.getCompany_name());
        ptype.setText(bean.getProject_type());
        xsr.setText(bean.getFill_in_user_name());
//		safetyselect.setText(bean.getSecurity_options());

        CustomeEditText2 dname = (CustomeEditText2) child.findViewById(R.id.pr_device_jx_dname);
        CustomeEditText2 dnum = (CustomeEditText2) child.findViewById(R.id.pr_device_jx_dnum);
        dname.setText(bean.getDevice_name());
        dnum.setText(bean.getDevice_number());
//		RadioButton result = (RadioButton) child.findViewById(R.id.pr_device_jx_isok);
//		if(bean.getCheck_result()==0&&pwm!=null){
//			result.setChecked(false);
//		}else{
//			result.setChecked(true);
//		}
        ImageView status_check = (ImageView) child.findViewById(R.id.status_check);
        TextView text_check = (TextView) child.findViewById(R.id.text_check);
//        if (bean.getCheck_result() == 0 ) {
//            status_check.setImageResource(R.drawable.icon_close);
//            text_check.setText("不合格");
//        } else {
//            status_check.setImageResource(R.drawable.icon_check);
//            text_check.setText("合格");
//        }
        CustomeTextView qx = (CustomeTextView) child.findViewById(R.id.pr_device_jx_qx);
        qx.setVisibility(View.GONE);
//		qx.setValueText(bean.getDefect_type());
        Button xscontentBtn = (Button) child.findViewById(R.id.pr_device_jx_exception);
        xscontentBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                System.out.println(bean.getDevice_id() + "---");
                Intent i = new Intent();
                i.putExtra("did", bean.getId() + "");
                i.putExtra("type", 3 + "");
                i.setClass(SafetyCultureCheckHandleCardActivity.this, DXSBXSKContentShowListActivity.class);
                startActivity(i);
            }
        });
        commonLayout.addView(child);
    }
}
