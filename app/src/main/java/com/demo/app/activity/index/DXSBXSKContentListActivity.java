package com.demo.app.activity.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import android.content.Context;
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

public class DXSBXSKContentListActivity extends BaseActivity {
    private ListView dqsbxskContentListView;
    private XSContentAdapter adapter;
    private List<Map<Object, Object>> data;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
//			adapter.notifyDataSetChanged();
            adapter.refresh(data);
        }

        ;
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

        dqsbxskContentListView = (ListView) this
                .findViewById(R.id.dqsbxskContentListView);
        adapter = new XSContentAdapter(this, getData());
        dqsbxskContentListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        dqsbxskContentListView.setAdapter(adapter);
    }

    public List<Map<Object, Object>> getData() {
        data = new ArrayList<Map<Object, Object>>();
        NetworkData.getInstance().deviceContentList(new NetworkResponceFace() {

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
                        map.put("text", (i + 1) + "."/*obj.get("device_content_id").toString()+"."+*/ + obj.get("device_content_name").toString());
                        map.put("device_content_id", obj.get("device_content_id").toString());
                        map.put("select_val", 1);
                        data.add(map);
                    }
//					Constents.xscontentList.add(data);
                    Map<Object, Object> m1 = new HashMap<Object, Object>();
                    m1.put("deviceId", Constents.current_device_id);
                    m1.put("result", data);
                    /*for(int j=0;j<Constents.xscontentList.size();j++){
                        //去除相同设备ID的巡视内容
						if(Constents.xscontentList.get(j).get("deviceId").equals(Constents.current_device_id)){
							Constents.xscontentList.remove(Constents.xscontentList.get(j));
						}
					}*/
                    //重新添加
                    Constents.xscontentList.add(m1);
                    handler.obtainMessage().sendToTarget();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, Constents.current_project_id, Constents.current_device_id);
        return data;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(Constents.xserrorcontentList, "list");
    }
}
