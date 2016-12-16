package com.demo.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.demo.app.R;
import com.demo.app.adapter.ImageViewPagerAdapter;
import com.demo.app.util.AppActivityManager;
import com.demo.app.view.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

public class BigImageActivity extends AppCompatActivity {
    private Button back;
    ImageViewPagerAdapter adapter;
    HackyViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppActivityManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_big_image);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        ArrayList<String> list = intent.getStringArrayListExtra("imgs");
        pager = (HackyViewPager) findViewById(R.id.pager);
        adapter = new ImageViewPagerAdapter(getSupportFragmentManager(), list);
        pager.setAdapter(adapter);
        pager.setCurrentItem(position);
        back = (Button) findViewById(R.id.title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BigImageActivity.this.finish();
            }
        });
    }

}
