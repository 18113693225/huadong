package com.demo.app.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.widget.ProgressBar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.demo.app.R;
import com.demo.app.util.Constents;
import com.demo.app.util.TitleCommon;

public class NoticeInfoDetailsActivity extends BaseActivity {
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_notice_info_details_layout);
		int id = this.getIntent().getIntExtra("id", 0);
		String  title = "信息发布详情";
		String  url = Constents.REQUEST_URL+"/api/informationDeliveryInfo";
		String postData = "id="+id;
		TitleCommon.setTitle(this, null, title, TabMainActivity.class, true);
		
		webView = (WebView)this.findViewById(R.id.notice_detail_webview);
		final ProgressBar index_service_intro_progressBar = (ProgressBar)this.findViewById(R.id.index_notice_detail_progressBar);


		webView.getSettings().setJavaScriptEnabled(true);  
		webView.getSettings().setRenderPriority(RenderPriority.HIGH);  
		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //设置 缓存模式  
         // 开启 DOM storage API 功能  
		webView.getSettings().setDomStorageEnabled(true);  
         //开启 database storage API 功能  
		webView.getSettings().setDatabaseEnabled(true);   
         String cacheDirPath = getFilesDir().getAbsolutePath()+"/webcache";  
 //      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;  
         Log.i("ServiceIntroduceDetail", "cacheDirPath="+cacheDirPath);  
         //设置数据库缓存路径  
         webView.getSettings().setDatabasePath(cacheDirPath);  
         //设置  Application Caches 缓存目录  
         webView.getSettings().setAppCachePath(cacheDirPath);  
         //开启 Application Caches 功能  
         webView.getSettings().setAppCacheEnabled(true);
		
		webView.getSettings().setDefaultTextEncodingName("utf-8") ;
		
//	    webView.postUrl(url, /*EncodingUtils.getBytes("2", "utf-8")*/postData.getBytes());
		webView.loadUrl(url+"?id="+id);
		webView.setWebViewClient(new WebViewClient(){
	           @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            // TODO Auto-generated method stub
	               //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
	             view.loadUrl(url);
	            return true;
	        }
	       });
	    webView.setWebChromeClient(new WebChromeClient() {
	        @Override
	        public void onProgressChanged(WebView view, int newProgress) {
	            super.onProgressChanged(view, newProgress);
	            System.out.println(newProgress);
	            index_service_intro_progressBar.setProgress(newProgress);
	            if(newProgress==100){
	            	index_service_intro_progressBar.setVisibility(View.GONE);
	            }
	        }
	    });
	}
}
