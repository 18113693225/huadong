package com.demo.app.activity.index;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.adapter.XSContentAdapter;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DXSBXSKContentListActivity extends BaseActivity {
    private ListView dqsbxskContentListView;
    private XSContentAdapter adapter;
    private ArrayList<Map<Object, Object>> data;
    private String d_id;
    private String type;
    private int btId;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            adapter.refresh(data);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dqsbxsk_content_list_layout);
        SharedPreferences sp = getSharedPreferences(Constents.SHARE_CONFIG,
                Context.MODE_PRIVATE);

        int uid = sp.getInt("userId", -1);
        String title = "巡视内容";
        TitleCommon.setTitle(this, null, title, null, true);

        Intent intent = getIntent();
        d_id = intent.getStringExtra("did");
        type = intent.getStringExtra("type");
        btId = intent.getIntExtra("btId", -1);
        dqsbxskContentListView = (ListView) this
                .findViewById(R.id.dqsbxskContentListView);

        if (!Constents.contentListMap.containsKey(type)) {
            adapter = new XSContentAdapter(this, getData(), Integer.valueOf(type).intValue());
        } else {
            adapter = new XSContentAdapter(this, Constents.contentListMap.get(type), btId);
        }

        dqsbxskContentListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        dqsbxskContentListView.setAdapter(adapter);
    }

    public ArrayList<Map<Object, Object>> getData() {
        data = new ArrayList<Map<Object, Object>>();
        NetworkData.getInstance().deviceContentList(new NetworkResponceFace() {

            @Override
            public void callback(String result) {
                JSONObject json;
                try {
                    json = new JSONObject(result);
                    JSONArray rel = new JSONArray(json.get("result").toString());
                    for (int i = 0; i < rel.length(); i++) {
                        JSONObject obj = (JSONObject) rel.get(i);
                        Map<Object, Object> map = new HashMap<Object, Object>();
                        map.put("text", (i + 1) + "."/*obj.get("device_content_id").toString()+"."+*/ + obj.get("device_content_name").toString());
                        map.put("device_content_id", obj.get("device_content_id").toString());
                        map.put("select_val", 1);
                        data.add(map);
                    }
                    handler.obtainMessage().sendToTarget();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, Integer.valueOf(d_id).intValue(), Integer.valueOf(type).intValue());
        return data;
    }

}
