package com.demo.app.activity.user;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.activity.index.TwoTicketOperNewActivity;
import com.demo.app.bean.DateBean;
import com.demo.app.bean.JobLogBean;
import com.demo.app.bean.JobPlanBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class PersonWorkLogNewActivity extends BaseActivity {
	private CustomeEditText2 start,end,title,content; 
	private Button saveBtn;
	private SharedPreferences sp;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(PersonWorkLogNewActivity.this, "新建成功", Toast.LENGTH_LONG).show();
				PersonWorkLogNewActivity.this.finish();
			} else if (msg.what == 2) {
				Toast.makeText(PersonWorkLogNewActivity.this, "新建失败", Toast.LENGTH_LONG).show();
			} else if (msg.what == 3) {
				Toast.makeText(PersonWorkLogNewActivity.this, "有必填项未填", Toast.LENGTH_LONG).show();
			} else if (msg.what == 4) {
				Toast.makeText(PersonWorkLogNewActivity.this, "结束时间必须大于开始时间",
						Toast.LENGTH_LONG).show();
			} 
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewWithoutTitle(R.layout.person_work_new_log_layout);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TitleCommon.setTitle(this, null, "工作日志", PersonWorkLogActivity.class, true);
		sp = getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		start = (CustomeEditText2)this.findViewById(R.id.work_log_start);
		end = (CustomeEditText2)this.findViewById(R.id.work_log_end);
		title = (CustomeEditText2)this.findViewById(R.id.work_log_title);
		content = (CustomeEditText2)this.findViewById(R.id.work_log_content);
		saveBtn = (Button)this.findViewById(R.id.work_log_save);
		saveBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int uid = sp.getInt("userId", -1);
				String startValue  = start.getText();
				String endValue = end.getText();
				String tilteValue = title.getText();
				String contentValue = content.getText();
				if(endValue.compareTo(startValue)<=0){
					handler.obtainMessage(4).sendToTarget();
					return;
				}
				if ("".equals(startValue) || "".equals(endValue)
						|| "".equals(tilteValue) || "".equals(contentValue)) {
					handler.obtainMessage(3).sendToTarget();
				} else {
					JobLogBean bean  = new JobLogBean();
					bean.setCreate_user_id(uid);
					bean.setStart_time(startValue);
					bean.setEnd_time(endValue);
					bean.setTitle(tilteValue);
					bean.setContent(contentValue);
					List<DateBean> list = new ArrayList<DateBean>();
					list.add(bean);
					NetworkData.getInstance().jobLogAdd(
							new NetworkResponceFace() {
								@Override
								public void callback(String result) {
									// TODO Auto-generated method stub
									JSONObject json;
									try {
										json = new JSONObject(result);
										String status = json
												.getString("status");
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == -1) {
			int editTextId = data.getIntExtra("editTextId", -1);
			switch (requestCode) {
			case 99:
				if (editTextId != -1) {
					CustomeEditText2 et = (CustomeEditText2) findViewById(editTextId);
					String time = data.getExtras().getString("time");
					et.setText(time);
				}
				break;
			default:
				break;
			}
		}
	}
}
