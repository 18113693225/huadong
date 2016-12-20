package com.demo.app.view;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.alibaba.fastjson.JSON;
import com.demo.app.R;
import com.demo.app.activity.index.PowerRunXSKActivity;
import com.demo.app.adapter.ImagePublishAdapter;
import com.demo.app.api.ApiService;
import com.demo.app.api.service.CardService;
import com.demo.app.bean.ImageItem;
import com.demo.app.util.Constents;
import com.demo.app.util.CustomConstants;
import com.demo.app.util.IntentConstants;
import com.demo.app.util.PreferenceUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PublishActivity extends Activity {
    private static final String TAG = "PublishActivity";
    private GridView mGridView;
    private ImagePublishAdapter mAdapter;
    private TextView sendTv;
    public static List<ImageItem> mDataList = new ArrayList<>();
    private SharedPreferences sp;
    private CardService cardService;
    private ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_publish);

        initData();
        initView();
    }

    protected void onPause() {
        super.onPause();
        saveTempToPref();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveTempToPref();
    }

    private void saveTempToPref() {
        SharedPreferences sp = getSharedPreferences(CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
        String prefStr = JSON.toJSONString(mDataList);
        // Toast.makeText(getApplicationContext(), "prefStr值:" + prefStr,
        // Toast.LENGTH_SHORT).show();
        Log.i(TAG, "prefStr值:" + prefStr);
        sp.edit().putString(CustomConstants.PREF_TEMP_IMAGES, prefStr).commit();

    }

    private void getTempFromPref() {
        SharedPreferences sp = getSharedPreferences(CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
        String prefStr = sp.getString(CustomConstants.PREF_TEMP_IMAGES, null);
        if (!TextUtils.isEmpty(prefStr)) {
            List<ImageItem> tempImages = JSON.parseArray(prefStr, ImageItem.class);
            mDataList = tempImages;
        }
    }

    private void removeTempFromPref() {
        SharedPreferences sp = getSharedPreferences(CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
        sp.edit().remove(CustomConstants.PREF_TEMP_IMAGES).commit();
    }

    @SuppressWarnings("unchecked")
    private void initData() {
        Intent intent = getIntent();
        if (intent.hasExtra("btId")) {
            PreferenceUtils.cacheBtId(PublishActivity.this, intent.getIntExtra("btId", -1));
        }
        sp = getSharedPreferences(Constents.SHARE_CONFIG, MODE_PRIVATE);
        cardService = ApiService.createCardService();
        getTempFromPref();
        List<ImageItem> incomingDataList = (List<ImageItem>) getIntent()
                .getSerializableExtra(IntentConstants.EXTRA_IMAGE_LIST);
        if (incomingDataList != null) {
            mDataList.addAll(incomingDataList);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyDataChanged(); // 当在ImageZoomActivity中删除图片时，返回这里需要刷新
    }

    public void initView() {
        dialog = new ProgressDialog(PublishActivity.this);
        TextView titleTv = (TextView) findViewById(R.id.title);
        titleTv.setText("");
        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImagePublishAdapter(this, mDataList);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == getDataSize()) {
                    new PopupWindows(PublishActivity.this, mGridView);
                } else {
                    Intent intent = new Intent(PublishActivity.this, ImageZoomActivity.class);
                    intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST, (Serializable) mDataList);
                    intent.putExtra(IntentConstants.EXTRA_CURRENT_IMG_POSITION, position);
                    startActivity(intent);
                }
            }
        });
        sendTv = (TextView) findViewById(R.id.action);
        sendTv.setText("发送");
        sendTv.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                dialog.show();
                Map<String, RequestBody> photos = new HashMap<>();
                removeTempFromPref();
                int id = sp.getInt("userId", 0);
                int XMid = Integer.valueOf(PowerRunXSKActivity.xiangmu).intValue();
                int LBid = Integer.valueOf(PowerRunXSKActivity.leibie).intValue();
                if (mDataList.size() > 0) {
                    for (int i = 0; i < mDataList.size(); i++) {
                        String substring = mDataList.get(i).sourcePath.substring(mDataList.get(i).sourcePath.lastIndexOf("/") + 1, mDataList.get(i).sourcePath.length());
                        photos.put("file\"; filename=\"" + substring, RequestBody.create(MediaType.parse("multipart/form-data"), new File(mDataList.get(i).sourcePath)));
                    }
                }
                Call<String> imgCall = cardService.addImage(photos, XMid, LBid, id);
                imgCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        dialog.hideDialog();
                        ArrayList<String> lists = new ArrayList<String>();
                        JSONObject json;
                        try {
                            json = new JSONObject(response.body());
                            String status = json.getString("status");
                            JSONArray arr = json.getJSONArray("img_ids");
                            for (int i = 0; i < arr.length(); i++) {
                                lists.add(arr.getString(i));
                            }
                            if (status.equals("success")) {
                                mDataList.clear();
                                int btId = PreferenceUtils.getBtId(PublishActivity.this);
                                String id = btId + "";
                                Map<String, ArrayList<String>> m = new HashMap<String, ArrayList<String>>();
                                m.put(id, lists);
                                EventBus.getDefault().post(m, "photo");
                                Toast.makeText(PublishActivity.this, "上传成功,请前往巡视卡保存。", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PublishActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        response.body();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        dialog.hideDialog();
                        Toast.makeText(PublishActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private int getDataSize() {
        return mDataList == null ? 0 : mDataList.size();
    }

    private int getAvailableSize() {
        int availSize = CustomConstants.MAX_IMAGE_SIZE - mDataList.size();
        if (availSize >= 0) {
            return availSize;
        }
        return 0;
    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.item_popupwindow, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_2));

            setWidth(LayoutParams.MATCH_PARENT);
            setHeight(LayoutParams.MATCH_PARENT);
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    takePhoto();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(PublishActivity.this, ImageBucketChooseActivity.class);
                    intent.putExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE, getAvailableSize());
                    startActivity(intent);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";

    public void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File vFile = new File(Environment.getExternalStorageDirectory() + "/myimage/",
                String.valueOf(System.currentTimeMillis()) + ".jpg");
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile();
            vDirPath.mkdirs();
        } else {
            if (vFile.exists()) {
                vFile.delete();
            }
        }
        path = vFile.getPath();
        Uri cameraUri = Uri.fromFile(vFile);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (mDataList.size() < CustomConstants.MAX_IMAGE_SIZE && resultCode == -1 && !TextUtils.isEmpty(path)) {
                    ImageItem item = new ImageItem();
                    item.sourcePath = path;
                    mDataList.add(item);
                }
                break;
        }
    }

    private void notifyDataChanged() {
        mAdapter.notifyDataSetChanged();
    }

}