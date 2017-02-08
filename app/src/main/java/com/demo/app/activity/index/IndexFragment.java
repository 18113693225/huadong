/**
 *
 */
package com.demo.app.activity.index;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.demo.app.R;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.activity.user.UserProjectGroupListActivity;
import com.demo.app.adapter.IndexViewPagerAdapter;
import com.demo.app.bean.AuthorityBean;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeGridView;
import com.google.gson.Gson;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author S
 */
public class IndexFragment extends Fragment {

    private View indexView;
    private ViewPager viewPager;
    private LinearLayout pageNumLiner;
    private List<Map<String, Object>> linkdata;
    private CustomeGridView indexGridView;
    private Button mPreSelectedBt;
    private SharedPreferences sp;
    private TextView showjxtextnum;
    private ImageView showjximage;
    private WebView gifWebView;
    private IndexViewPagerAdapter ImageAdapter;
    // 图片封装为一个数组
    private int[] icon = {R.drawable.icon_company_intro,
            R.drawable.icon_business_intro,
            R.drawable.icon_electric_power_operation,
            R.drawable.icon_electricity_try_repairing,
            R.drawable.icon_project_management, R.drawable.icon_safety_culture,
            R.drawable.icon_equipment_management,
            R.drawable.icon_two_tickets_management,
            R.drawable.icon_customer_management};
    private String[] iconName = {"业务简介", "技术咨询", "电力运行", "电力检修", "项目管理",
            "安全文明", "设备管理", "两票管理", "客户管理"};
    private List<View> lists;
    /**
     * 栏目权限map
     */
    private Map<Integer, Map<String, Integer>> authorMap /*= new HashMap<Integer, Map<String,Integer>>()*/;
    public static Map<String, Map<String, Integer>> authorListMap/* = new HashMap<String, Map<String,Integer>>()*/;


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                if (sp.getBoolean("hasLogin", false)) {
                    showjxtextnum.setText(msg.obj.toString());
                    showjxtextnum.setVisibility(View.VISIBLE);
                    if (Integer.parseInt(msg.obj.toString()) > 0) {
                        gifWebView.setVisibility(View.VISIBLE);
                        showjximage.setVisibility(View.GONE);
                    } else {
                        gifWebView.setVisibility(View.GONE);
                        showjximage.setVisibility(View.VISIBLE);
                    }
                } else {
                    showjxtextnum.setVisibility(View.GONE);
                    showjximage.setVisibility(View.VISIBLE);
                    gifWebView.setVisibility(View.GONE);
                }
//				getColumnAuthority();
            } else if (msg.what == 2) {
                Gson gson = new Gson();
                try {
                    JSONArray array = new JSONArray(msg.obj.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        AuthorityBean bean = gson.fromJson(obj.toString(), AuthorityBean.class);
                        int column = bean.getColumn_id();
                        int edit = bean.getVal_edit();
                        int see = bean.getVal_see();
                        Map<String, Integer> m = new HashMap<String, Integer>();
                        m.put("column", column);
                        m.put("edit", edit);
                        m.put("see", see);
                        authorMap.put(column, m);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
//				getColumnListAuthority();
            } else if (msg.what == 3) {
                Gson gson = new Gson();
                try {
                    JSONArray array = new JSONArray(msg.obj.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        AuthorityBean bean = gson.fromJson(obj.toString(), AuthorityBean.class);
                        int column = bean.getColumn_id();
                        String column_type = bean.getColumn_type_id();
                        int edit = bean.getVal_edit();
                        int see = bean.getVal_see();
                        Map<String, Integer> m2 = new HashMap<String, Integer>();
                        m2.put("edit", edit);
                        m2.put("see", see);
                        Map<String, Object> m = new HashMap<String, Object>();
//						m.put("type", Integer.parseInt(column_type.replace(column+"-", "")));
//						m.put(column_type, m2);
                        authorListMap.put(column_type, m2);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
//				getImageData();
            } else if (msg.what == 4) {
                handleImage();
            } else if (msg.what == 5) {
                ImageView v1 = new ImageView(getActivity());
                v1.setScaleType(ImageView.ScaleType.FIT_XY);
                v1.setImageBitmap((Bitmap) msg.obj);
                System.out.println((Bitmap) msg.obj);
                lists.add(v1);
                ImageAdapter.notifyDataSetChanged();
            }
        }

        ;
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        System.out.println("onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        authorMap = new HashMap<Integer, Map<String, Integer>>();
        authorListMap = new HashMap<String, Map<String, Integer>>();
        linkdata = new ArrayList<Map<String, Object>>();
        lists = new ArrayList<View>();
        System.out.println("onCreateView" + authorListMap + "---" + authorMap);
        if (null != indexView) {
            ViewGroup parent = (ViewGroup) indexView.getParent();
            if (null != parent) {
                parent.removeView(indexView);
            }
        } else {
            indexView = inflater.inflate(R.layout.index_tab, null);
            getImageData();
        }
        return indexView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated");
        TitleCommon.setTitle(getActivity(), indexView, "电力物业",
                TabMainActivity.class, false);
        sp = getActivity().getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
        //===========
        /*lists = new ArrayList<View>();
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.nav_dot_02);
		pageNumLiner = (LinearLayout) indexView.findViewById(R.id.pageNumLiner);
		for (int i = 0; i < 3; i++) {
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
		viewPager = (ViewPager) indexView.findViewById(R.id.search_viewpager);
		ImageView v1 = new ImageView(getActivity());
		v1.setScaleType(ImageView.ScaleType.FIT_XY);
		v1.setImageResource(R.drawable.banner_img_01);
		ImageView v2 = new ImageView(getActivity());
		v2.setScaleType(ImageView.ScaleType.FIT_XY);
		v2.setImageResource(R.drawable.banner_img_02);

		ImageView v3 = new ImageView(getActivity());
		v3.setScaleType(ImageView.ScaleType.FIT_XY);
		v3.setImageResource(R.drawable.banner_img_01);

		lists.add(v1);
		lists.add(v2);
		lists.add(v3);
		IndexViewPagerAdapter ImageAdapter = new IndexViewPagerAdapter(lists);
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
		});*/
        //===========
        //查看检修任务
        LinearLayout showjxtask = (LinearLayout) indexView.findViewById(R.id.show_jx_task_linear);
        showjximage = (ImageView) indexView.findViewById(R.id.show_jx_task_image);
        gifWebView = (WebView) indexView.findViewById(R.id.gifWebView);
        gifWebView.setBackgroundColor(getResources().getColor(R.color.test));
        gifWebView.loadUrl("file:///android_asset/gif.html");
        showjxtextnum = (TextView) indexView.findViewById(R.id.show_jx_task_textnum);
        final SharedPreferences sp = getActivity().getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
        showjxtask.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //未登录跳转到登陆页
                if (!sp.getBoolean("hasLogin", false)) {
                    TabMainActivity activity = (TabMainActivity) getActivity();
//					activity.viewPage.setCurrentItem(3);
                    activity.fragmentTabHost.setCurrentTab(3);
                } else {
                    Intent i = new Intent();
                    i.setClass(getActivity(), PowerRunJXTaskListActivity.class);
                    startActivity(i);
                }
            }
        });

        indexGridView = (CustomeGridView) indexView.findViewById(R.id.indexGridView);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), getData(),
                R.layout.index_tab_grid_item, new String[]{"image", "text"},
                new int[]{R.id.grid_item_image, R.id.grid_item_text});
        indexGridView.setAdapter(adapter);
        indexGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                // TODO Auto-generated method stub
                Intent i = new Intent();
                switch (position) {
                    case 0:
                        i.setClass(getActivity(), CompanyIntroduceActivity.class);
                        break;
                    case 1:
                        i.setClass(getActivity(), ServiceIntroduceActivity.class);
                        break;
                    case 2:
                        i.setClass(getActivity(), PowerRunActivity.class);
                        break;
                    case 3:
                        i.setClass(getActivity(), PowerFixActivity.class);
                        break;
                    case 4:
                        i.setClass(getActivity(), ProjectManagerActivity.class);
                        break;
                    case 5:
                        i.setClass(getActivity(), SafetyCultureActivity.class);
                        break;
                    case 6:
                        i.setClass(getActivity(), DeviceManagerActivity.class);
                        break;
                    case 7:
                        i.setClass(getActivity(), TwoTicketManagerActivity.class);
                        break;
                    case 8:
                        i.setClass(getActivity(), CustomerManagerActivity.class);
                        break;
                    default:
                        break;
                }
                if (sp.getBoolean("hasLogin", false) || position < 2) {
                    //传递栏目权限
                    if (position >= 2) {
                        //判断是否选择了项目组,-1没选择项目组
                        if (sp.getString("user_project_id", "-1").equals("-1")) {
                            i.putExtra("type", "index");
                            i.setClass(getActivity(), UserProjectGroupListActivity.class);
                        } else {
                            Map<String, Integer> m = authorMap.get(position - 1);
                            int edit = 1;
                            int see = 1;
                            if (m != null) {
//							Toast.makeText(getActivity(), "-edit:--"+m.get("edit")+"--see:--"+m.get("see"), Toast.LENGTH_LONG).show();
                                edit = m.get("edit");
                                see = m.get("see");
                                if (see == 0) {
                                    Toast.makeText(getActivity(), "对不起，权限不足，请联系管理员!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else {
//							Toast.makeText(getActivity(), "-edit:--"+1+"--see:--"+1, Toast.LENGTH_LONG).show();							
                            }
                            i.putExtra("editAuth", edit);
                            i.putExtra("seeAuth", see);
                        }
                    }
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_LONG).show();
                }
            }
        });
//		getJxData();
    }

    /**
     * 栏目数据
     *
     * @return
     */
    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data.add(map);
        }
        return data;
    }

    /**
     * 查询检修任务数量
     */
    public void getJxData() {
        int uid = sp.getInt("userId", -1);
        linkdata.clear();
        NetworkData.getInstance().maintenanceTaskCardList(new NetworkResponceFace() {

            @Override
            public void callback(String result) {
                // TODO Auto-generated method stub
                JSONObject json;
                try {
                    json = new JSONObject(result);
                    if ("success".equals(json.get("status"))) {
                        JSONArray rel = new JSONArray(json.get("result").toString());
                        int length = rel.length();
                        handler.obtainMessage(1, length).sendToTarget();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, uid + "");
    }

    /**
     * 获取栏目权限
     */
    public void getColumnAuthority() {
        int uid = sp.getInt("userId", -1);
        int pid = Integer.parseInt(sp.getString("user_project_id", "-1"));
        NetworkData.getInstance().authorityColumnList(new NetworkResponceFace() {

            @Override
            public void callback(String result) {
                // TODO Auto-generated method stub
                JSONObject json;
                try {
                    json = new JSONObject(result);
                    if ("success".equals(json.get("status"))) {
                        String authority = json.get("result").toString();
                        System.out.println("========column============" + authority);
                        handler.obtainMessage(2, authority).sendToTarget();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, uid, pid);
    }

    /**
     * 获取列表权限
     */
    public void getColumnListAuthority() {
        int uid = sp.getInt("userId", -1);
        int pid = Integer.parseInt(sp.getString("user_project_id", "-1"));
        NetworkData.getInstance().authorityList(new NetworkResponceFace() {

            @Override
            public void callback(String result) {
                // TODO Auto-generated method stub
                JSONObject json;
                try {
                    json = new JSONObject(result);
                    if ("success".equals(json.get("status"))) {
                        String authority = json.get("result").toString();
                        System.out.println("========list============" + authority);
                        handler.obtainMessage(3, authority).sendToTarget();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, uid, pid);
    }

    /**
     * 获取幻灯片数据
     */
    public void getImageData() {
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
                        if ("3".equals(type)) {
                            map.put("link", obj.get("brower_link").toString());
                            linkdata.add(map);
                        }
                    }
                    handler.obtainMessage(4).sendToTarget();
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
    public void handleImage() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.nav_dot_02);
        pageNumLiner = (LinearLayout) indexView.findViewById(R.id.pageNumLiner);
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
        viewPager = (ViewPager) indexView.findViewById(R.id.search_viewpager);

        for (int i = 0; i < linkdata.size(); i++) {
			/*ImageView v1 = new ImageView(getActivity());
			v1.setScaleType(ImageView.ScaleType.FIT_XY);
			v1.setImageBitmap(TitleCommon.getHttpBitmap(Constents.REQUEST_URL+File.separator+linkdata.get(i).get("link")));
			lists.add(v1);*/
            TitleCommon.getHttpBitmapThread(Constents.REQUEST_URL + File.separator + linkdata.get(i).get("link"), handler, 5);
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

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        System.out.println("index-onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        System.out.println("index-onResume");
        getJxData();
        getColumnAuthority();
        getColumnListAuthority();
//		getImageData();
        super.onResume();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        System.out.println("index-onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        System.out.println("onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        System.out.println("index-onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        System.out.println("index-onDestroy");
        super.onDestroy();
    }
}