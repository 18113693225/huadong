package com.demo.app.activity.index;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.RenderPriority;
import android.widget.ProgressBar;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.activity.TabMainActivity;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;
//公司介绍
public class CompanyIntroduceActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_company_introduce_layout);
		TitleCommon.setTitle(this, null, "业务介绍", TabMainActivity.class, true);
		WebView webView = (WebView) this
				.findViewById(R.id.company_intro_webview);
		final ProgressBar company_intro_progressBar = (ProgressBar)this.findViewById(R.id.company_intro_progressBar);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setRenderPriority(RenderPriority.HIGH);
		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 设置缓存模式

		// 开启 DOM storage API 功能
		webView.getSettings().setDomStorageEnabled(true);
		// 开启 database storage API 功能
		webView.getSettings().setDatabaseEnabled(true);
		String cacheDirPath = getFilesDir().getAbsolutePath() + "/webcache";
		// String cacheDirPath =
		// getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
		Log.i("ServiceIntroduceDetail", "cacheDirPath=" + cacheDirPath);
		// 设置数据库缓存路径
		webView.getSettings().setDatabasePath(cacheDirPath);
		// 设置 Application Caches 缓存目录
		webView.getSettings().setAppCachePath(cacheDirPath);
		// 开启 Application Caches 功能
		webView.getSettings().setAppCacheEnabled(true);

		// 启用支持javascript
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		webView.postUrl(Constents.REQUEST_URL+"/api/companyProfile","id=2".getBytes());
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}
		});
		 webView.setWebChromeClient(new WebChromeClient() {
		        @Override
		        public void onProgressChanged(WebView view, int newProgress) {
		            super.onProgressChanged(view, newProgress);
		            System.out.println(newProgress);
		            company_intro_progressBar.setProgress(newProgress);
		            if(newProgress==100){
		            	company_intro_progressBar.setVisibility(View.GONE);
		            }
		        }
		    });
	}
}
