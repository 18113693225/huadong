package com.demo.app.activity.user;

import com.demo.app.R;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.activity.index.WorkTicketNameListActivity;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
//修改用户信息
public class ModifyUserInfoCommonActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_userinfo_common_layout);
//		final int type = Integer.parseInt(getIntent().getStringExtra("type"));
		final int type = getIntent().getIntExtra("type", -1);
		String value = getIntent().getStringExtra("value");
		final EditText content = (EditText)findViewById(R.id.modify_userinfo_content);
		content.setText(value);
		String title = "";
		switch (type) {
		case 0:
			title = "更改姓名";
			break;
		case 1:
			title = "更改电话";
			break;
		case 2:
			title = "更改地址";
			break;
		case 3:
			title = "更改卡户银行";
			break;
		case 4:
			title = "更改卡号";
			break;
		default:
			break;
		}
		TitleCommon.setTitle(this, null, title, UpdateUserInfoActivity.class, true);
		findViewById(R.id.modify_userinfo_saveBtn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String contentValue = content.getText().toString();
				Intent i = new Intent();
				i.putExtra("contentValue", contentValue);
				i.putExtra("type", type);
				ModifyUserInfoCommonActivity.this.setResult(RESULT_OK, i);
				ModifyUserInfoCommonActivity.this.finish();
			}
		});
	}

}
