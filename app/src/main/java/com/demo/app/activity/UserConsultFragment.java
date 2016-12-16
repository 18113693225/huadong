package com.demo.app.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.adapter.TextAdapter;
import com.demo.app.api.HttpData;
import com.demo.app.api.HttpGatDataListener;
import com.demo.app.bean.ListData;
import com.demo.app.util.TitleCommon;

public class UserConsultFragment extends Fragment implements HttpGatDataListener, OnClickListener {

	private View userConsultView;
	private HttpData httpData;
	private ListView lv;
	private EditText sendtext;
	private Button send_btn;
	private String content_str;
	private List<ListData> lists;
	private TextAdapter adapter;
	private String[] welcome_array;
	private double currentTime = 0, oldTime = 0;
	private Toast mToast;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (null != userConsultView) {
            ViewGroup parent = (ViewGroup) userConsultView.getParent();
            if (null != parent) {
                parent.removeView(userConsultView);
            }
        } else {
        	userConsultView = inflater.inflate(R.layout.user_consult_tab_layout, null);
        }
		return userConsultView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		TitleCommon.setTitle(getActivity(), userConsultView, "用户咨询", TabMainActivity.class, false);
		initLayout();
	}

	public void initLayout() {
		
		lv = (ListView) getActivity().findViewById(R.id.lv);
		sendtext = (EditText) getActivity().findViewById(R.id.sendText);
		send_btn = (Button) getActivity().findViewById(R.id.send_btn);
		lists = new ArrayList<ListData>();
		send_btn.setOnClickListener(this);
		adapter = new TextAdapter(lists, getActivity());
		lv.setAdapter(adapter);
		ListData listData;
		listData = new ListData(getRandomWelcomeTips(), ListData.RECEIVER,
				getTime());
		lists.add(listData);
	}
	
	@Override
	public void getDataUrl(String data) {
		parseText(data);
	}
	
	private String getRandomWelcomeTips() {
		String welcome_tip = null;
		welcome_array = this.getResources()
				.getStringArray(R.array.welcome_tips);
		int index = (int) (Math.random() * (welcome_array.length - 1));
		welcome_tip = welcome_array[index];
		return welcome_tip;
	}

	public void parseText(String str) {
		try {
			JSONObject jb = new JSONObject(str);
			// System.out.println(jb.getString("code"));
			// System.out.println(jb.getString("text"));
			String string = "";
			try {
				string = "\n" + jb.getJSONArray("list").toString();
			} catch (Exception e) {
			}
			ListData listData;
			listData = new ListData(jb.getString("text"), ListData.RECEIVER,
					getTime(), string);
			lists.add(listData);
			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	int ret = 0;// 函数调用返回值

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send_btn:
			getTime();
			sendMess();
			break;
		}

	}

	/**
	 * 发送信息
	 */
	private void sendMess() {
		content_str = sendtext.getText().toString();
		sendtext.setText("");
		String dropk = content_str.replace(" ", "");
		String droph = dropk.replace("\n", "");
		ListData listData;
		listData = new ListData(content_str, ListData.SEND, getTime());
		lists.add(listData);
		if (lists.size() > 30) {
			for (int i = 0; i < lists.size(); i++) {
				lists.remove(i);
			}
		}
		adapter.notifyDataSetChanged();
		httpData = (HttpData) new HttpData(
				"http://www.tuling123.com/openapi/api?key=82b8a9538beb4b9cb92285710c9fe581&info="
						+ droph, this).execute();
		
	}
	
	private String getTime() {
		currentTime = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Date curDate = new Date();
		String str = format.format(curDate);
		if (currentTime - oldTime >= 500) {
			oldTime = currentTime;
			return str;
		} else {
			return "";
		}
	}
}
