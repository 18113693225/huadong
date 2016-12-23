package com.demo.app.activity;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.demo.app.R;

/**
 * Created by LXG on 2016/12/20.
 */

public class UpdateActivity extends BaseActivity {

    private WebView mWebView;

    private ProgressBar mPrgBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
//        TitleCommon.setTitle(this, null, "软件更新", TabMainActivity.class, true);
        initView();
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        mWebView = (WebView) findViewById(R.id.news_web);
        mPrgBar = (ProgressBar) findViewById(R.id.prgBar);

        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setDefaultTextEncodingName("UTF-8");
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebView.loadUrl("http://app.qq.com/#id=detail&appid=1105648833");


        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    if (mPrgBar != null) {
                        mPrgBar.setVisibility(View.GONE);
                        mPrgBar.setProgress(0);
                    }
                } else {
                    if (mPrgBar != null) {
                        if (View.GONE == mPrgBar.getVisibility()) {
                            mPrgBar.setVisibility(View.VISIBLE);
                        }
                        mPrgBar.setProgress(newProgress);
                    }
                }
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.endsWith(".apk")) {
                    Uri uri = Uri.parse(url);
                    Intent viewIntent = new Intent(Intent.ACTION_VIEW, uri);
                    UpdateActivity.this.startActivity(viewIntent);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//
//                //编写 javaScript方法
//                String javascript = "javascript:function hideOther(){ " +
//                        "var headerHide = document.getElementByClassName('mod-sub-header');" +
//                        "headerHide.remove();}";
//
//                //创建方法
//                view.loadUrl(javascript);
//                //加载方法
//                view.loadUrl("javascript:hideOther();");
//
//            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }
}
