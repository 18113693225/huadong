package com.demo.app.activity.index;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.google.gson.JsonObject;

public class TwoTicketOperActivity extends BaseActivity implements
        OnClickListener {
    private ListView twoTicketOperListView;
    private Button exist_oper_tables, new_oper_tables;
    private ProgressBar progress;
    private String[] items = {"表1", "表2", "表3"};
    private SimpleAdapter adapter;
    private SharedPreferences sp;
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                adapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);
            } else {
                adapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_twoticket_oper_layout);
        TitleCommon.setTitle(this, null, "操作票统计表", TwoTicketManagerActivity.class, true);

        sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);

        progress = (ProgressBar) this.findViewById(R.id.twoTicketOper_progressBar);
        progress.setVisibility(View.GONE);
        exist_oper_tables = (Button) this.findViewById(R.id.exist_oper_tables);
        new_oper_tables = (Button) this.findViewById(R.id.new_oper_tables);
        exist_oper_tables.setOnClickListener(this);
        new_oper_tables.setOnClickListener(this);

        twoTicketOperListView = (ListView) this.findViewById(R.id.twoTicketOperListView);
        adapter = new SimpleAdapter(this, data,
                R.layout.oper_ticket_layout_item, new String[]{"text"},
                new int[]{R.id.oper_text});
        twoTicketOperListView.setAdapter(adapter);
        twoTicketOperListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                if (Integer.parseInt(data.get(arg2).get("seeItemAuth").toString()) == 0) {
                    Toast.makeText(TwoTicketOperActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent();
                i.putExtra("id", data.get(arg2).get("id").toString());
                i.putExtra("editAuth", getIntent().getIntExtra("editAuth", 1));
                i.putExtra("editListAuth", getIntent().getIntExtra("editListAuth", 1));
                i.putExtra("editItemAuth", Integer.parseInt(data.get(arg2).get("editItemAuth").toString()));
                i.setClass(TwoTicketOperActivity.this, TwoTicketOperShowActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        new_oper_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
        exist_oper_tables.setBackgroundResource(R.drawable.button_franchise_shape);
        getData();
    }

    public List<Map<String, Object>> getData() {
        data.clear();
        handler.obtainMessage(2).sendToTarget();
        int uid = sp.getInt("userId", -1);
        NetworkData.getInstance().optTicketList(new NetworkResponceFace() {

            @Override
            public void callback(String result) {
                // TODO Auto-generated method stub
                JSONObject json;
                try {
                    json = new JSONObject(result);
                    JSONArray rel = new JSONArray(json.get("result").toString());
                    for (int i = 0; i < rel.length(); i++) {
                        JSONObject obj = (JSONObject) rel.get(i);
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("text", obj.get("company_name").toString());
                        map.put("id", obj.get("id").toString());
                        map.put("seeItemAuth", obj.get("val_see").toString());
                        map.put("editItemAuth", obj.get("val_edit").toString());
                        data.add(map);
                    }
                    handler.obtainMessage(1).sendToTarget();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    handler.obtainMessage(2).sendToTarget();
                }
            }
        }, uid);
        System.out.println(data);
        return data;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.exist_oper_tables:
                new_oper_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
                exist_oper_tables.setBackgroundResource(R.drawable.button_franchise_shape);
                break;
            case R.id.new_oper_tables:
                if (getIntent().getIntExtra("editAuth", 1) == 0 || getIntent().getIntExtra("editListAuth", 1) == 0) {
                    Toast.makeText(TwoTicketOperActivity.this, "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
                    return;
                }
                new_oper_tables.setBackgroundResource(R.drawable.button_franchise_shape);
                exist_oper_tables.setBackgroundResource(R.drawable.button_franchise_shape1);
                Intent i = new Intent();
                i.setClass(TwoTicketOperActivity.this, TwoTicketOperNewActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }
}
