package com.demo.app.activity.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.demo.app.R;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.activity.UpdateActivity;
import com.demo.app.network.NetworkData;
import com.demo.app.network.NetworkResponceFace;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeImageTextButton;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class UserLoginFragment extends Fragment implements OnClickListener, OnItemClickListener {

    private View loginView;
    private TextView registerText, forgetPwText, login_show_name;
    private EditText userNameText, userPasswordText;
    private Button loginBtn;
    private LinearLayout loginLinearLayout;
    private LinearLayout personCenterLinearLayout;
    private FrameLayout userTitle;
    private ListView personListView;
    private CheckBox loginCheckBox;
    private SharedPreferences sp;
    public static final int SINA_SHARE_WAY_WEBPAGE = 3;
    IWeiboShareAPI mWeiboShareAPI = null;
    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read," + "follow_app_official_microblog,"
            + "invitation_write";
    private int[] icon = {R.drawable.icon_setting, R.drawable.icon_inspection_service, R.drawable.icn_work_log,
            R.drawable.icon_plan, R.drawable.icon_contact, R.drawable.icon_recommed, R.drawable.icon_update,
            R.drawable.icon_modify, R.drawable.icon_psd_set, R.drawable.icon_modify};
    private String[] iconName = {"工作管理", "服务跟踪", "工作日志", "工作计划", "联系平台", "推荐好友", "软件更新", "个人信息修改", "密码设置", "项目组选择"};
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    int status = Integer.parseInt(msg.obj.toString());
                    if (status == 1) {
                        Toast.makeText(getActivity(), "连接错误！", Toast.LENGTH_SHORT).show();
                    } else if (status == 2) {
                        // Toast.makeText(getActivity(), "登陆成功",
                        // Toast.LENGTH_SHORT).show();
                        userTitle.setVisibility(View.GONE);
                        loginLinearLayout.setVisibility(View.GONE);
                        personCenterLinearLayout.setVisibility(View.VISIBLE);
                        login_show_name.setText(sp.getString("username", "") + "推荐码：" + sp.getString("user_code", ""));
                    } else if (status == 3) {
                        Toast.makeText(getActivity(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    } else if (status == 4) {
                        Toast.makeText(getActivity(), "请先注册类型", Toast.LENGTH_SHORT).show();
                        Intent registerIntent = new Intent();
                        registerIntent.setClass(getActivity(), RegisterActivity.class);
                        startActivity(registerIntent);
                    }
                    break;

                default:
                    break;
            }

        }

        ;

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        if (null != loginView) {
            ViewGroup parent = (ViewGroup) loginView.getParent();
            if (null != parent) {
                parent.removeView(loginView);
            }
        } else {
            loginView = inflater.inflate(R.layout.user_login_tab_layout, null);
            initLayout();
        }
        return loginView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        TitleCommon.setTitle(getActivity(), loginView, "用户登陆", TabMainActivity.class, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // initLayout();/
        if (sp.getBoolean("hasLogin", false)) {
            handler.obtainMessage(0, 2).sendToTarget();
        }
    }

    public void initLayout() {
        sp = getActivity().getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
        personCenterLinearLayout = (LinearLayout) loginView.findViewById(R.id.personCenterLinearLayout);
        loginLinearLayout = (LinearLayout) loginView.findViewById(R.id.loginLinearLayout);
        userTitle = (FrameLayout) loginView.findViewById(R.id.userTitle);
        loginCheckBox = (CheckBox) loginView.findViewById(R.id.loginCheckBox);
        loginCheckBox.setChecked(sp.getBoolean("rememberPassword", false));
        loginCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                sp.edit().putBoolean("rememberPassword", isChecked).commit();
                if (!isChecked) {
                    sp.edit().putString("userphone", "").commit();
                    sp.edit().putString("password", "").commit();
                }
            }
        });
        registerText = (TextView) loginView.findViewById(R.id.register);
        registerText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// 下划线
        registerText.setOnClickListener(this);
        forgetPwText = (TextView) loginView.findViewById(R.id.forgetPw);
        forgetPwText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// 下划线
        forgetPwText.setOnClickListener(this);
        loginBtn = (Button) loginView.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);

        userNameText = (EditText) loginView.findViewById(R.id.userName);
        userPasswordText = (EditText) loginView.findViewById(R.id.userPassword);
        if (sp.getBoolean("rememberPassword", false)) {
            userNameText.setText(sp.getString("userphone", ""));
            userPasswordText.setText(sp.getString("password", ""));
        }
        personListView = (ListView) loginView.findViewById(R.id.personListView);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), getData(), R.layout.person_center_layout_item,
                new String[]{"icon", "text"}, new int[]{R.id.p_icon, R.id.p_text});
        personListView.setAdapter(adapter);

        login_show_name = (TextView) loginView.findViewById(R.id.login_show_name);
        LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.footer_layout, null);
        personListView.addFooterView(layout);
        Button exit = (Button) layout.findViewById(R.id.button_exit);
        exit.setOnClickListener(this);
        personListView.setOnItemClickListener(this);
        /*
         * Button b = new Button(getActivity());
		 * b.setBackgroundResource(R.drawable.button_exit_shape);
		 * AbsListView.LayoutParams lp = new
		 * AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
		 * AbsListView.LayoutParams.WRAP_CONTENT); lp.setMargins(10, 10, 10,
		 * 10); b.setLayoutParams(lp); b.setText("退出登录");
		 * personListView.addFooterView(b);
		 */
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            case R.id.register:
                Intent registerIntent = new Intent();
                registerIntent.setClass(getActivity(), QuickRegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.forgetPw:
                Intent forgetIntent = new Intent();
                forgetIntent.putExtra("type", "forget");
                forgetIntent.setClass(getActivity(), UpdatePasswordActivity.class);
                startActivity(forgetIntent);
                break;
            case R.id.loginBtn:
                final String phone = userNameText.getText().toString();
                final String password = userPasswordText.getText().toString();

                if ("".equals(phone)) {
                    Toast.makeText(getActivity(), "账号不能为空", Toast.LENGTH_SHORT).show();
                } else if ("".equals(password)) {
                    Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    NetworkData.getInstance().login(new NetworkResponceFace() {

                        @Override
                        public void callback(String result) {
                            // TODO Auto-generated method stub
                        /*
                         * userTitle.setVisibility(View.GONE);
						 * loginLinearLayout.setVisibility(View.GONE);
						 * personCenterLinearLayout.setVisibility(View.VISIBLE);
						 */
                            if ("".equals(result)) {
                                handler.obtainMessage(0, 1).sendToTarget();
                            } else {
                                JSONObject dataJson;
                                try {
                                    dataJson = new JSONObject(result);
                                    System.out.println(dataJson);
                                    String status = dataJson.getString("status");
                                    if ("success".equals(status)) {
                                        JSONObject rel = new JSONObject(dataJson.get("result").toString());
                                        int step = Integer.parseInt(rel.getString("regest_setup"));
                                        sp.edit().putInt("userId", Integer.parseInt(rel.get("id").toString())).commit();
                                        if (step == 1) {
                                            handler.obtainMessage(0, 4).sendToTarget();
                                            sp.edit().putString("userphone", phone).commit();
                                        } else if (step == 2) {
                                            // 保存userid
                                            sp.edit().putString("username", rel.get("name").toString()).commit();
                                            sp.edit().putString("user_code", rel.get("user_code").toString()).commit();
                                            handler.obtainMessage(0, 2).sendToTarget();
                                            // 记住密码
                                            if (sp.getBoolean("rememberPassword", false)) {
                                                sp.edit().putString("userphone", phone).commit();
                                                sp.edit().putString("password", password).commit();
                                            }
                                            sp.edit().putBoolean("hasLogin", true).commit();
                                        }
                                    } else {
                                        handler.obtainMessage(0, 3).sendToTarget();
                                    }
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                    handler.obtainMessage(0, 1).sendToTarget();
                                }
                            }
                        }
                    }, phone, password);
                }
                break;
            case R.id.button_exit:
                userTitle.setVisibility(View.VISIBLE);
                loginLinearLayout.setVisibility(View.VISIBLE);
                personCenterLinearLayout.setVisibility(View.GONE);
                sp.edit().putBoolean("hasLogin", false).commit();
                sp.edit().putString("user_project_id", "-1").commit();
                if (!sp.getBoolean("rememberPassword", false)) {
                    userNameText.setText("");
                    userPasswordText.setText("");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 验证手机号码
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", icon[i]);
            map.put("text", iconName[i]);
            data.add(map);
        }
        return data;
    }

    public void startActivity(Class<?> cls) {
        Intent i = new Intent();
        i.setClass(getActivity(), cls);
        startActivity(i);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        // TODO Auto-generated method stub
        switch (position) {
            case 0:
                startActivity(PersonWorkManagerActivity.class);
                break;
            case 1:
                startActivity(PersonXJServiceActivity.class);
                break;
            case 2:
                startActivity(PersonWorkLogActivity.class);
                break;
            case 3:
                startActivity(PersonWorkScheduleActivity.class);
                break;
            case 4:
                startActivity(PersonContractHDActivity.class);
                break;
            case 5:
                ShareUtils();
                break;
            case 6:
                startActivity(UpdateActivity.class);
                break;
            case 7:
                startActivity(UpdateUserInfoActivity.class);
                break;
            case 8:
                startActivity(UpdatePasswordActivity.class);
                break;
            case 9:
                startActivity(UserProjectGroupListActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 分享
     */
    public void ShareUtils() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        dialog.getWindow().setContentView(R.layout.person_share_layout_item);
        CustomeImageTextButton wxBtn = (CustomeImageTextButton) dialog.findViewById(R.id.wxshare);
        CustomeImageTextButton wbBtn = (CustomeImageTextButton) dialog.findViewById(R.id.wbshare);
        CustomeImageTextButton qqBtn = (CustomeImageTextButton) dialog.findViewById(R.id.qqshare);
        wxBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                IWXAPI api = WXAPIFactory.createWXAPI(getActivity(), Constents.Wx_APP_ID, true);
                api.registerApp(Constents.Wx_APP_ID);
                // WXTextObject textObj = new WXTextObject();
                // textObj.text = "打造中国电力物业第一品牌";
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = "http://app.qq.com/#id=detail&appid=1105648833";
                Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.logoo);
                WXMediaMessage msg = new WXMediaMessage();
                // msg.mediaObject = textObj;
                msg.mediaObject = webpage;
                msg.setThumbImage(thumb);
                msg.title = "四川华东电气";
                msg.description = "打造中国电力物业第一品牌";
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                api.sendReq(req);
            }
        });
        wbBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(getActivity(), "2655841444");
                initWeibo();
            }
        });
        qqBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                initQQShare();
            }
        });
    }

    /**
     * 新浪微博
     */
    public void initWeibo() {
        IWeiboShareAPI mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(getActivity(), Constents.Sina_APP_ID);
        // // 获取微博客户端相关信息，如是否安装、支持 SDK 的版本
        boolean isInstalledWeibo = mWeiboShareAPI.isWeiboAppInstalled();
        final int supportApiLevel = mWeiboShareAPI.getWeiboAppSupportAPI();
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        boolean register = mWeiboShareAPI.registerApp();
        System.out.println(isInstalledWeibo + "-支持SDK版本：-" + supportApiLevel + "-是否注册成功：" + register);
        if (!isInstalledWeibo) {
            Toast.makeText(getActivity(), "请先安装新浪微博客户端！", Toast.LENGTH_LONG).show();
            return;
        }
        if (supportApiLevel >= 10351) {
            weiboMessage.mediaObject = getWebpageObj();
            weiboMessage.textObject = getTextObj();

            SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
            request.transaction = String.valueOf(System.currentTimeMillis());
            request.multiMessage = weiboMessage;

            mWeiboShareAPI.sendRequest(getActivity(), request);
        }

    }

    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = "四川华东电气";
        return textObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.actionUrl = "http://app.qq.com/#id=detail&appid=1105648833";
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = "电力物业";
        mediaObject.description = "打造中国电力物业第一品牌";
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logoo);
        mediaObject.setThumbImage(bmp);
        return mediaObject;
    }

    /**
     * 腾讯QQ
     */
    public void initQQShare() {
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        final Tencent mTencent = Tencent.createInstance(Constents.Qq_APP_ID, getActivity());
        // 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取

        // TODO Auto-generated method stub
        final Bundle params = new Bundle();
        // 分享的标题
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "华东电力物业");
        // 好友点击后跳转的url
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://app.qq.com/#id=detail&appid=1105648833");
        // 描述
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "打造中国电力物业第一品牌");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://api.sc-huadong.cn:8686/images/logo.png");
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 0x00);
        // QQ分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != mTencent) {
                    mTencent.shareToQQ(getActivity(), params, qqShareListener);
                }
            }
        });
    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            // Toast.makeText(getActivity(), "取消分享！",
            // Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Toast.makeText(getActivity(), "分享完成！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            Toast.makeText(getActivity(), "分享失败！", Toast.LENGTH_SHORT).show();
        }
    };
}
