package com.demo.app.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.demo.app.R;
import com.demo.app.activity.BaseActivity;

import com.demo.app.activity.BigImageActivity;
import com.demo.app.api.ApiService;
import com.demo.app.api.service.CardService;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.ImageRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by LXG on 2016/12/14.
 */

public class PowerRunJXLookImageActivity extends BaseActivity implements ImageRecyclerView.OnItemClickListener {
    private ImageRecyclerView rv;
    ArrayList<String> list = new ArrayList<>();
    CardService cardService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_power_run_look_image_layout);
        cardService = ApiService.createCardService();
        TitleCommon.setTitle(this, null, "查看图片", PowerRunJXHandleCardActivity.class, true);
        initView();

    }

    private void initView() {
        rv = (ImageRecyclerView) findViewById(R.id.rv);
        Intent intent = getIntent();
        int str = intent.getIntExtra("id", 0);
        getData(str);
    }

    private void getData(int str) {
        Call<String> allImageCall = cardService.allImage(str);
        allImageCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                JSONObject json;
                try {
                    json = new JSONObject(response.body());
                    if (json.getString("status").equals("success")) {
                        JSONArray arr = json.getJSONArray("result");
                        for (int i = 0; i < arr.length(); i++) {
                            list.add(Constents.REQUEST_URL + "/" + arr.getJSONObject(i).getString("img_url"));
                        }
                        rv.setData(list, PowerRunJXLookImageActivity.this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    public void onSubjectItemClick(View v, String data, int position) {
        Intent intent = new Intent(PowerRunJXLookImageActivity.this, BigImageActivity.class);
        intent.putStringArrayListExtra("imgs", list);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}
