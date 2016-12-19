package com.demo.app.activity.index;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
import com.demo.app.bean.SafetyTrainingBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;

public class SafetyCultureCheckActivity extends BaseActivity {
    private CustomeEditText2 pname, ptype, runit, xsr/*,safetyselect*/;
    private TextView addDevice;
    private Button saveBtn;
    private LinearLayout commonLayout;
    private int isok = 1;
    public static String xiangmu = "";
    public static String leibie = "";
    private List<String> LBs = new ArrayList<>();
    private SharedPreferences sp;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                Toast.makeText(SafetyCultureCheckActivity.this, "新建成功", Toast.LENGTH_LONG).show();
                SafetyCultureCheckActivity.this.finish();
            } else if (msg.what == 2) {
                Toast.makeText(SafetyCultureCheckActivity.this, "新建失败", Toast.LENGTH_LONG).show();
            } else if (msg.what == 3) {
                Toast.makeText(SafetyCultureCheckActivity.this, "有必填项未填", Toast.LENGTH_LONG).show();
            } else if (msg.what == 4) {
                Toast.makeText(SafetyCultureCheckActivity.this, "添加设备大于已有设备数量", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_safety_culture_check_xsk_layout);
        TitleCommon.setTitle(this, null, "安全检查巡视卡", SafetyCultureCheckTableActivity.class, true);
        TitleCommon.setGpsTitle(this);
        xiangmu = "";
        leibie = "";
        LBs.clear();
        Constents.xskcontentList.clear();
        Constents.xscontentList.clear();
        Constents.xserrorcontentList.clear();
        Constents.contentListMap.clear();
        Constents.devicenum = 0;
        sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
        commonLayout = (LinearLayout) this.findViewById(R.id.sc_check_commonLayout);
        pname = (CustomeEditText2) this.findViewById(R.id.sc_check_pname);
        ptype = (CustomeEditText2) this.findViewById(R.id.sc_check_ptype);
        runit = (CustomeEditText2) this.findViewById(R.id.sc_check_runit);
        xsr = (CustomeEditText2) this.findViewById(R.id.sc_check_xsr);
        xsr.setText(sp.getString("username", ""));
//		safetyselect = (CustomeEditText2)this.findViewById(R.id.sc_check_safetyselect);

        addDevice = (TextView) this.findViewById(R.id.sc_check_addDevice);
        addDevice.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int count = commonLayout.getChildCount();
//                if (count >= Constents.devicenum) {
//                    handler.obtainMessage(4).sendToTarget();
//                } else {
                addDevice();
                //  }
            }
        });
        addDevice();

        findViewById(R.id.sc_check_saveBtn).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int pnameValue = pname.getEditTexTag();
                int xsrValue = sp.getInt("userId", -1);
//				String security = safetyselect.getText();
                if (pnameValue == -1 || xsrValue == -1/*||"".equals(security)*/) {
                    handler.obtainMessage(3).sendToTarget();
                } else {
                    boolean flag = true;
                    Calendar calendar = Calendar.getInstance();
                    String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());
                    List<DateBean> list = new ArrayList<DateBean>();
                    int count = commonLayout.getChildCount();
                    for (int i = 0; i < count; i++) {
                        SafetyTrainingBean bean = new SafetyTrainingBean();
                        bean.setGPS(sp.getString("currentAddress", "定位失败"));
                        bean.setEntry_name_id(pnameValue);
                        bean.setCreate_time(time);
                        bean.setFill_in_user_id(xsrValue);
//						bean.setSecurity_options(security);

                        LinearLayout childP = (LinearLayout) commonLayout.getChildAt(i);
                        int countp = childP.getChildCount();
                        for (int j = 0; j < countp; j++) {
                            View child = childP.getChildAt(j);
                            Object tag = child.getTag();
                            if (tag.equals("pr_new_dname")) {
                                if (((CustomeEditText2) child).getEditTexTag() == -1) {
                                    handler.obtainMessage(3).sendToTarget();
                                    return;
                                }
                                bean.setDevice_id(((CustomeEditText2) child).getEditTexTag());
                            } else if (tag.equals("pr_new_dnum")) {
                                if (Constents.contentListMap.containsKey(i + "")) {
                                    bean.setDeviceContentList(Constents.contentListMap.get(i + ""));
                                }
                                bean.setDevice_number(((CustomeEditText2) child).getText().toString());
                            } else if (tag.equals("pr_xsk_radioLinear")) {
                                LinearLayout ll = (LinearLayout) child;
                                RadioGroup rg = (RadioGroup) ll.findViewById(R.id.pr_new_radioGroup);
                                int checkedId = rg.getCheckedRadioButtonId();
                                if (checkedId == R.id.pr_new_normalButton) {
                                    isok = 1;
                                } else if (checkedId == R.id.pr_new_exceptionButton) {
                                    isok = 0;
                                }
                                bean.setCheck_result(isok);
                            } else if (tag.equals("pr_new_xsbz")) {
                                bean.setNote(((EditText) child).getText().toString());
                            }
                        }
                        list.add(bean);
                    }
                    NetworkData.getInstance().safetyTrainingAdd(new NetworkResponceFace() {

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
     */
    @SuppressLint("NewApi")
    public void addDevice() {
        final int count = commonLayout.getChildCount();
        if (Constents.contentListMap.size() < count) {
            Toast.makeText(SafetyCultureCheckActivity.this, "请选择内容后再添加设备", Toast.LENGTH_LONG).show();
            return;
        }
        final View child = getLayoutInflater().inflate(R.layout.common_prxsk_layout, null);
        Button pz = (Button) child.findViewById(R.id.poto_pz);
        pz.setVisibility(View.GONE);
        CustomeEditText2 cdname = (CustomeEditText2) child.findViewById(R.id.pr_new_dname);
        cdname.setId(View.generateViewId());
        RadioButton cnormalButton = (RadioButton) child.findViewById(R.id.pr_new_normalButton);
        cnormalButton.setChecked(true);
        CustomeEditText2 ce = (CustomeEditText2) child.findViewById(R.id.pr_new_dnum);

        Button bt = (Button) ce.findViewById(R.id.custome_button2);
        if (ce.getTag().equals("pr_new_dnum")) {
            bt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int btId = commonLayout.indexOfChild(child);
                    if (LBs.size() < btId + 1) {
                        Toast.makeText(SafetyCultureCheckActivity.this, "请选择设备类别，相同的设备不能重复添加！,", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Intent i = new Intent();
                        i.putExtra("did", xiangmu);
                        i.putExtra("type", LBs.get(btId));
                        i.putExtra("btId", btId);
                        i.setClass(SafetyCultureCheckActivity.this, DXSBXSKContentListActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        SafetyCultureCheckActivity.this.startActivity(i);
                    }
                }
            });
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
                    String ptypeValue = data.getExtras().getString("ptype");
                    String runitValue = data.getExtras().getString("cname");
                    int id = data.getExtras().getInt("id");

                    if (ptypeValue == null && runitValue == null) {
                        leibie = id + "";
                        if (!LBs.contains(leibie)) {
                            LBs.add(leibie);
                        }
                    } else {
                        xiangmu = id + "";
                    }
                    if (ptypeValue != null) {
                        ptype.setText(ptypeValue);
                        runit.setText(runitValue);
                    }
                    if (editTextId != -1) {
                        CustomeEditText2 et = (CustomeEditText2) findViewById(editTextId);
                        et.setText(text);
                        et.setEditTextTag(id);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
