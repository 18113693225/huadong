package com.demo.app.activity.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.adapter.XSContentShowAdapter;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;

public class DXSBXSKContentShowListActivity extends BaseActivity {
    private ListView dqsbxskContentListView;
    private XSContentShowAdapter adapter;
    private List<Map<Object, Object>> data;
    private String d_id;
    private String type;

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
        String title = "巡视内容";
        TitleCommon.setTitle(this, null, title, null, true);
        d_id = getIntent().getStringExtra("did");
        type = getIntent().getStringExtra("type");
        dqsbxskContentListView = (ListView) this.findViewById(R.id.dqsbxskContentListView);
        adapter = new XSContentShowAdapter(this, getData());
        dqsbxskContentListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        dqsbxskContentListView.setAdapter(adapter);
    }

    public List<Map<Object, Object>> getData() {
        data = new ArrayList<Map<Object, Object>>();
        NetworkData.getInstance().maintenanceTaskDeviceConetent(new NetworkResponceFace() {

            @Override
            public void callback(String result) {
                // TODO Auto-generated method stub
                JSONObject json;
                try {
                    json = new JSONObject(result);
                    JSONArray rel = new JSONArray(json.get("result").toString());
                    for (int i = 0; i < rel.length(); i++) {
                        JSONObject obj = (JSONObject) rel.get(i);
                        Map<Object, Object> map = new HashMap<Object, Object>();
                        map.put("text1", (i + 1) + "." + obj.get("device_name").toString());
                        map.put("text", "\"" + (i + 1) + "." + obj.get("device_name").toString() + "\"");
                        map.put("device_content_id", obj.get("device_content_id").toString());
                        map.put("select_val", obj.get("select_val").toString());
                        data.add(map);
                    }
                    Map<Object, Object> m1 = new HashMap<Object, Object>();
                    m1.put("inspectionId", d_id);
                    m1.put("check_result", data);
                    for (int j = 0; j < Constents.jxcontentList.size(); j++) {
                        //去除相同设备ID的巡视内容
                        if (Constents.jxcontentList.get(j).get("deviceId").equals(d_id)) {
                            Constents.jxcontentList.remove(Constents.jxcontentList.get(j));
                        }
                    }
                    handler.obtainMessage().sendToTarget();
                    if (Constents.jxcontentList.size() != 0) {
                        List<Map<Object, Object>> l2 = (List<Map<Object, Object>>) (Constents.jxcontentList.get(0).get("check_result"));
                        for (int k = 0; k < l2.size(); k++) {
                            l2.get(k).remove("text1");
                        }
                    }
                    //重新添加
                    Constents.jxcontentList.add(m1);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, d_id, type);
        return data;
    }

}
