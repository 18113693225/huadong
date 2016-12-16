package com.demo.app.activity.user;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.demo.app.R;
import com.demo.app.util.Constents;

public class UserProtocolActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_protocol_layout);
		WebView userProtocolWebView = (WebView)this.findViewById(R.id.userProtocolWebView);
		userProtocolWebView.loadUrl(Constents.REQUEST_URL+"/api/agreement");
		userProtocolWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}
		});
	}
}
