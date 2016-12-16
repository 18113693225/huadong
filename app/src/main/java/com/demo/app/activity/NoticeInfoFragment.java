package com.demo.app.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.demo.app.R;
import com.demo.app.adapter.IndexViewPagerAdapter;
import com.demo.app.adapter.NoficeInfoAdapter;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class NoticeInfoFragment extends Fragment {

	private View noticeInfoView;
	private ViewPager viewPager;
	private LinearLayout pageNumLiner;
	private Button mPreSelectedBt;
	private List<View> lists;
	private ListView infoListView;
	private NoficeInfoAdapter adapter;
	private SharedPreferences sp;
	private List<Map<String, Object>> data;
	private List<Map<String, Object>> linkdata;
	private IndexViewPagerAdapter ImageAdapter;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				System.out.println("fadfadf"+data.size());
				adapter.notifyDataSetChanged();
				break;
			case 2:
				handleImage();
				break;
			case 3:
				ImageView v1 = new ImageView(getActivity());
				v1.setScaleType(ImageView.ScaleType.FIT_XY);
				v1.setImageBitmap((Bitmap) msg.obj);
				System.out.println((Bitmap) msg.obj);
				lists.add(v1);
				ImageAdapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		};
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e("+++", "onCreateView"+noticeInfoView);
		// TODO Auto-generated method stub
		if (null != noticeInfoView) {
            ViewGroup parent = (ViewGroup) noticeInfoView.getParent();
            if (null != parent) {
                parent.removeView(noticeInfoView);
            }
        } else {
        	noticeInfoView = inflater.inflate(R.layout.notice_info_tab_layout, null);
        	initLayout();
        	getImageData();
        }
		return noticeInfoView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.e("+++", "onActivityCreated");
//		getData();
		TitleCommon.setTitle(getActivity(), noticeInfoView, "信息发布",TabMainActivity.class, false);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getData();
//		adapter.refresh(data);
//		getImageData();
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.e("+++NoticeInfoFragment", "onDestroyView++++++++++++++++NoticeInfoFragment");
	}
	public void initLayout() {
		lists = new ArrayList<View>();
		data = new ArrayList<Map<String,Object>>();
		linkdata = new ArrayList<Map<String,Object>>();
//		getData();
		infoListView = (ListView)noticeInfoView.findViewById(R.id.infoListView);	
		System.out.println("=======infoListView"+infoListView);
		sp = getActivity().getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
//		adapter =  new SimpleAdapter(getActivity(), data, R.layout.notice_info_tab_item_layout,
//					new String[]{"title","short"}, new int[]{R.id.info_item_title,R.id.info_item_short});	
		adapter = new NoficeInfoAdapter(getActivity(), data);
		infoListView.setAdapter(adapter);
		infoListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Set<String> values = sp.getStringSet("infoClick", new HashSet<String>());
				values.add(data.get(arg2).get("id").toString());
				sp.edit().putStringSet("infoClick", values).commit();
				Intent i = new Intent();
				i.putExtra("id", Integer.parseInt(data.get(arg2).get("id").toString()));
				i.setClass(getActivity(), NoticeInfoDetailsActivity.class);
				startActivity(i);
			}
		});
	}

	/**
	 * 获取图片路径
	 */
	public void getImageData(){
		NetworkData.getInstance().slideList(new NetworkResponceFace() {

			@Override
			public void callback(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject json = new JSONObject(result);
					JSONArray rel = new JSONArray(json.get("result").toString());
					for (int i = 0; i < rel.length(); i++) {
						JSONObject obj = (JSONObject) rel.get(i);
						Map<String, Object> map = new HashMap<String, Object>();
						String type = obj.get("brower_type").toString();
						if("2".equals(type)){
							map.put("link", obj.get("brower_link").toString());
							linkdata.add(map);									
						}
					}
					handler.obtainMessage(2).sendToTarget();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * 解析幻灯片图片
	 */
	public void handleImage(){
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.nav_dot_02);
		pageNumLiner = (LinearLayout) noticeInfoView.findViewById(R.id.info_pageNumLiner);
		pageNumLiner.removeAllViews();
		lists.clear();
		for (int i = 0; i < linkdata.size(); i++) {
			Button bt = new Button(getActivity());
			bt.setLayoutParams(new ViewGroup.LayoutParams(
					bitmap.getWidth() / 2, bitmap.getHeight() / 2));
			if (i == 0) {
				bt.setBackgroundResource(R.drawable.nav_dot_01);
				mPreSelectedBt = bt;
			} else {
				bt.setBackgroundResource(R.drawable.nav_dot_02);
			}
			pageNumLiner.addView(bt);
		}
		viewPager = (ViewPager) noticeInfoView.findViewById(R.id.info_search_viewpager);
		
		for(int i=0;i<linkdata.size();i++){
			/*ImageView v1 = new ImageView(getActivity());
			v1.setScaleType(ImageView.ScaleType.FIT_XY);
			v1.setImageBitmap(TitleCommon.getHttpBitmap(Constents.REQUEST_URL+File.separator+linkdata.get(i).get("link")));
			lists.add(v1);*/
			TitleCommon.getHttpBitmapThread(Constents.REQUEST_URL+File.separator+linkdata.get(i).get("link"),handler,3);
		}
		ImageAdapter = new IndexViewPagerAdapter(lists);
		viewPager.setAdapter(ImageAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				if (mPreSelectedBt != null) {
					mPreSelectedBt.setBackgroundResource(R.drawable.nav_dot_02);
				}
				Button currentBt = (Button) pageNumLiner.getChildAt(position);
				currentBt.setBackgroundResource(R.drawable.nav_dot_01);
				mPreSelectedBt = currentBt;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
	/**
	 * 获取列表数据
	 * @return
	 */
	public List<Map<String, Object>> getData() {
		data.clear();
		linkdata.clear();
		NetworkData.getInstance().informationDelivery(new NetworkResponceFace() {
			
			@Override
			public void callback(String result) {
				// TODO Auto-generated method stub
				JSONObject json;
				try {
					json = new JSONObject(result);
					JSONArray rel = new JSONArray(json.get("result").toString());
					for(int i=0;i<rel.length();i++){
						JSONObject obj = (JSONObject) rel.get(i);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("title", obj.get("title").toString());
						map.put("short", obj.get("content").toString());
						map.put("id", obj.get("id").toString());
						data.add(map);
					}
					handler.obtainMessage(1).sendToTarget();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		/*try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.out.println("+++++++++++++++++++++++"+data.size());
		return data;
	}
}
