package com.demo.app.activity;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.demo.app.R;
import com.demo.app.util.TitleCommon;

/**
 * Created by LXG on 2016/12/20.
 */

public class UpdateActivity extends BaseActivity {

    private WebView mWebView;

    private ProgressBar mPrgBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        TitleCommon.setTitle(this, null, "软件更新", TabMainActivity.class, true);
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
        }
        return true;
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        mWebView = (WebView) findViewById(R.id.news_web);
        mPrgBar = (ProgressBar) findViewById(R.id.prgBar);
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
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setDefaultTextEncodingName("UTF-8");
        mWebSettings.setBuiltInZoomControls(false);
        mWebView.loadUrl("http://app.qq.com/#id=detail&appid=1105648833");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        }
    }
}
