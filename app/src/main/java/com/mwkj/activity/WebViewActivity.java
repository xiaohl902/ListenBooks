package com.mwkj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qf.kenlibrary.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${WU} on 2016/11/1.
 */
public class WebViewActivity extends BaseActivity {

    @Bind(R.id.wv)
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    @Override
    protected int getContentId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void init() {

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        webViewInit();
        webView.loadUrl(url);
    }

    private void webViewInit() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
//        webView.getSettings().setDefaultFontSize(18);
        webView.getSettings().setFixedFontFamily("微软雅黑");
        webView.getSettings().setLoadWithOverviewMode(true);//设置网页缩放至屏幕大小
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            //返回键
            if(webView.canGoBack()){
                //返回webview
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
