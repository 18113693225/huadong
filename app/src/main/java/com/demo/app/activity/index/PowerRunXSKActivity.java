package com.demo.app.activity.index;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.bean.DateBean;
import com.demo.app.bean.InspectionCardBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.PreferenceUtils;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;
import com.demo.app.view.PublishActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PowerRunXSKActivity extends BaseActivity {
    public static String xiangmu = "";
    public static String leibie = "";
    private CustomeEditText2 pname, ptype, runit, xsr;
    private TextView addDevice;
    private Button saveBtn;
    private LinearLayout commonLayout;
    private int isok = 1;
    private SharedPreferences sp;
    private Map<String, ArrayList<String>> photosMap = new HashMap<String, ArrayList<String>>();
    private List<String> LBs = new ArrayList<>();
    private List<String> Times = new ArrayList<>();
    private List<String> Latitudes = new ArrayList<>();
    private List<String> Longitudes = new ArrayList<>();
    private List<Integer> editTextIds = new ArrayList<>();
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                Toast.makeText(PowerRunXSKActivity.this, "新建成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PowerRunXSKActivity.this, TabMainActivity.class);
                startActivity(intent);
                PowerRunXSKActivity.this.finish();
            } else if (msg.what == 2) {
                Toast.makeText(PowerRunXSKActivity.this, "新建失败", Toast.LENGTH_LONG).show();
            } else if (msg.what == 3) {
                Toast.makeText(PowerRunXSKActivity.this, "有必填项未填", Toast.LENGTH_LONG).show();
            } else if (msg.what == 4) {
                Toast.makeText(PowerRunXSKActivity.this, "添加设备大于已有设备数量", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_power_run_xsk_layout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        TitleCommon.setTitle(this, null, "电气设备巡视卡", PowerRunActivity.class, true);
        TitleCommon.setGpsTitle(this);
        xiangmu = "";
        leibie = "";
        LBs.clear();
        editTextIds.clear();
        Times.clear();
        Latitudes.clear();
        Longitudes.clear();
        Constents.xskcontentList.clear();
        Constents.xscontentList.clear();
        Constents.xserrorcontentList.clear();
        Constents.contentListMap.clear();
        Constents.devicenum = 0;
        sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
        commonLayout = (LinearLayout) this.findViewById(R.id.pr_xsk_commonLayout);
        addDevice = (TextView) this.findViewById(R.id.pr_new_addDevice);
        addDevice.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int count = commonLayout.getChildCount();
                /*
                 * if(count>=Constents.devicenum){
				 * handler.obtainMessage(4).sendToTarget(); }else{
				 */
                addDevice();
                // }
            }
        });
        addDevice();
        pname = (CustomeEditText2) this.findViewById(R.id.pr_new_pname);
        ptype = (CustomeEditText2) this.findViewById(R.id.pr_new_ptype);
        runit = (CustomeEditText2) this.findViewById(R.id.pr_new_runit);
        xsr = (CustomeEditText2) this.findViewById(R.id.pr_new_xsr);
        xsr.setText(sp.getString("username", ""));
        saveBtn = (Button) this.findViewById(R.id.pr_new_saveBtn);
        saveBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int count = commonLayout.getChildCount();
//                if (Constents.contentListMap.size() < count) {
//                    Toast.makeText(PowerRunXSKActivity.this, "最后一条设备未添加内容", Toast.LENGTH_LONG).show();
//                    return;
//                }
                int pnameValue = pname.getEditTexTag();
                int ptypeValue = ptype.getEditTexTag();
                int runitValue = runit.getEditTexTag();
                int xsrValue = /* xsr.getEditTexTag() */sp.getInt("userId", -1);
                if (pnameValue == -1 || /* runitValue == -1|| */ xsrValue == -1) {
                    handler.obtainMessage(3).sendToTarget();
                } else {
                    boolean flag = true;
                    Calendar calendar = Calendar.getInstance();
                    String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());
                    List<DateBean> list = new ArrayList<DateBean>();
                    for (int i = 0; i < count; i++) {
                        InspectionCardBean bean = new InspectionCardBean();

                        bean.setEntry_name_id(pnameValue);
                        // bean.setCompany_id(runitValue);
                        bean.setInspection_man(xsrValue);
                        bean.setCheck_time(time);
                        bean.setGPS(sp.getString("currentAddress", "定位失败"));

                        LinearLayout childP = (LinearLayout) commonLayout.getChildAt(i);
                        int countp = childP.getChildCount();
                        for (int j = 0; j < countp; j++) {
                            View child = childP.getChildAt(j);
                            Object tag = child.getTag();
                            bean.setEquipment_check_time(Times.get(i));
                            bean.setLatitude(Latitudes.get(i));
                            bean.setLongitude(Longitudes.get(i));
                            if (tag.equals("pr_new_dname")) {
                                if (((CustomeEditText2) child).getEditTexTag() == -1) {
                                    handler.obtainMessage(3).sendToTarget();
                                    return;
                                }
                                bean.setDevice_name(((CustomeEditText2) child).getEditTexTag());
                            } else if (tag.equals("pr_new_dnum")) {
                                if (Constents.contentListMap.containsKey(LBs.get(i) + "")) {
                                    bean.setDeviceContentList(Constents.contentListMap.get(LBs.get(i) + ""));
                                }
                                bean.setEquipment_number(((CustomeEditText2) child).getText().toString());
                            } else if (tag.equals("pr_xsk_radioLinear")) {
                                LinearLayout ll = (LinearLayout) child;
                                RadioGroup rg = (RadioGroup) ll.findViewById(R.id.pr_new_radioGroup);
                                int checkedId = rg.getCheckedRadioButtonId();
                                if (checkedId == R.id.pr_new_normalButton) {
                                    isok = 1;
                                } else if (checkedId == R.id.pr_new_exceptionButton) {
                                    isok = 0;
                                }
                                bean.setInspection_result(isok);
                            } else if (tag.equals("pr_new_qxradioLinear") && isok == 0) {
                                LinearLayout ll = (LinearLayout) child;
                                RadioGroup rg = (RadioGroup) ll.findViewById(R.id.pr_new_qxradioGroup);
                                int checkedId = rg.getCheckedRadioButtonId();
                                String qx = "危机缺陷";
                                if (checkedId == R.id.pr_new_qxwjButton) {
                                    qx = "危机缺陷";
                                } else if (checkedId == R.id.pr_new_qxzdButton) {
                                    qx = "重大缺陷";
                                } else if (checkedId == R.id.pr_new_qxybButton) {
                                    qx = "一般缺陷";
                                }
                                bean.setDefect_type(qx);
                            } else if (tag.equals("pr_new_photos")) {
                                if (((Button) child).getText().toString().equals("照片已经上传")) {
                                    bean.setPhotos(photosMap.get(i + ""));
                                }
                            } else if (tag.equals("pr_new_xsbz")) {
                                bean.setInspection_content(((EditText) child).getText().toString());
                            }
                        }
                        list.add(bean);
                    }
                    NetworkData.getInstance().inspectionCardAdd(new NetworkResponceFace() {

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
//        final int count = commonLayout.getChildCount();
//        if (Constents.contentListMap.size() < count) {
//            Toast.makeText(PowerRunXSKActivity.this, "请选择内容后再添加设备", Toast.LENGTH_LONG).show();
//            return;
//        }
        final View child = getLayoutInflater().inflate(R.layout.common_prxsk_layout, null);
        LinearLayout ll = (LinearLayout) child.findViewById(R.id.err_ll);
        ll.setVisibility(View.VISIBLE);
        CustomeEditText2 cdname = (CustomeEditText2) child.findViewById(R.id.pr_new_dname);
        cdname.setId(View.generateViewId());
        Button pz = (Button) child.findViewById(R.id.poto_pz);
        pz.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (xiangmu == "" || leibie == "") {
                    Toast.makeText(PowerRunXSKActivity.this, "请选择项目或者类别", Toast.LENGTH_LONG).show();
                } else {
                    int btId = commonLayout.indexOfChild(child);
                    Intent newIntent = new Intent(PowerRunXSKActivity.this, PublishActivity.class);
                    newIntent.putExtra("btId", btId);
                    startActivity(newIntent);
                }
            }
        });
        RadioButton cnormalButton = (RadioButton) child.findViewById(R.id.pr_new_normalButton);
        cnormalButton.setChecked(true);
        final LinearLayout qxradioLinear = (LinearLayout) child.findViewById(R.id.pr_new_qxradioLinear);
        RadioGroup radioGroup = (RadioGroup) child.findViewById(R.id.pr_new_radioGroup);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.pr_new_normalButton) {
                    qxradioLinear.setVisibility(View.GONE);
                } else if (checkedId == R.id.pr_new_exceptionButton) {
                    qxradioLinear.setVisibility(View.VISIBLE);
                }
            }
        });
        RadioButton qxwjButton = (RadioButton) child.findViewById(R.id.pr_new_qxwjButton);
        qxwjButton.setChecked(true);
        CustomeEditText2 ce = (CustomeEditText2) child.findViewById(R.id.pr_new_dnum);

        Button bt = (Button) ce.findViewById(R.id.custome_button2);
        if (ce.getTag().equals("pr_new_dnum")) {
            bt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int btId = commonLayout.indexOfChild(child);
                    if (LBs.size() < btId + 1) {
                        Toast.makeText(PowerRunXSKActivity.this, "请选择设备类别，相同的设备不能重复添加！,", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Intent i = new Intent();
                        i.putExtra("did", xiangmu);
                        i.putExtra("type", LBs.get(btId));
                        i.putExtra("btId", btId);
                        i.setClass(PowerRunXSKActivity.this, DXSBXSKContentListActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        PowerRunXSKActivity.this.startActivity(i);
                    }
                }
            });
        }
        commonLayout.addView(child);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
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
                        Calendar calendar = Calendar.getInstance();
                        String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());
                        if (editTextIds.contains(editTextId)) {
                            int index = editTextIds.indexOf(editTextId);
                            LBs.set(index, leibie);
                        } else {
                            editTextIds.add(editTextId);
                            LBs.add(leibie);
                            Times.add(time);
                            Latitudes.add(sp.getString("latitude", ""));
                            Longitudes.add(sp.getString("longitude", ""));
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

    @Subscriber(tag = "photo")
    public void onMessageEvent(Map<String, ArrayList<String>> map) {
        int btId = PreferenceUtils.getBtId(PowerRunXSKActivity.this);
        LinearLayout child = (LinearLayout) commonLayout.getChildAt(btId);
        Button pz = (Button) child.findViewById(R.id.poto_pz);
        pz.setText("照片已经上传");
        photosMap.putAll(map);
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        //    EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
