package com.demo.app.activity.index;

import android.os.Bundle;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.util.TitleCommon;


public class ErrContentListActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dqsbxsk_content_list_layout);

        String title = "缺陷内容";
        TitleCommon.setTitle(this, null, title, null, true);


    }


}
