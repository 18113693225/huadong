package com.demo.app.activity.user;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeEditText;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
//联系华东
public class PersonContractHDActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewWithoutTitle(R.layout.person_contract_hd_layout);
		TitleCommon.setTitle(this,null, "联系华东", TabMainActivity.class,true);
	}
}
