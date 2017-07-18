package com.demo.app.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.adapter.RegisterArrayAdapter;
import com.demo.app.util.TitleCommon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends BaseActivity implements
        OnItemClickListener {

    public String[] items = {"终端用户注册", "加盟商注册", "电工注册", "E推广注册"};
    public int[] icons = {R.drawable.icon_mobile, R.drawable.icon_join,
            R.drawable.icon_electrician, R.drawable.icon_e};
    private ListView registerListView;
    private RegisterArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithoutTitle(R.layout.register_layout);
        registerListView = (ListView) this.findViewById(R.id.registerListView);
        /*SimpleAdapter adapter = new SimpleAdapter(this, getData(),
                R.layout.register_layout_item, new String[] { "icon", "text" },
				new int[] { R.id.register_icon, R.id.register_text });*/
        adapter = new RegisterArrayAdapter(this, R.layout.register_layout_item, getData());
        registerListView.setAdapter(adapter);
        registerListView.setOnItemClickListener(this);
        TitleCommon.setTitle(this, null, "立即注册", TabMainActivity.class, true);
    }

    public List<Object> getData() {
        List<Object> data = new ArrayList<Object>();
        for (int i = 0; i < items.length; i++) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("icon", icons[i]);
            m.put("text", items[i]);
            data.add(m);
        }
        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position,
                            long arg3) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClass(RegisterActivity.this, EndUserRegisterActivity.class);
                break;
            case 1:
                intent.setClass(RegisterActivity.this, FranchiseeRegisterActivity.class);
                break;
            case 2:
                intent.setClass(RegisterActivity.this, ElectricianRegisterActivity.class);
                break;
            case 3:
                intent.setClass(RegisterActivity.this, ESpreadRegisterActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
