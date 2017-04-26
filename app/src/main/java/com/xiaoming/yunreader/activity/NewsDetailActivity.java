package com.xiaoming.yunreader.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.xiaoming.yunreader.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class NewsDetailActivity extends AppCompatActivity {
    @ViewInject(R.id.wv_news_detail)
    private WebView wv_news_detail;
    @ViewInject(R.id.pbLoding)
    private ProgressBar pbLoding;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        x.view().inject(this);

        String newsUrl = getIntent().getStringExtra("newsUrl");
        wv_news_detail.loadUrl(newsUrl);
        WebSettings settings = wv_news_detail.getSettings();
        //显示缩放按钮
        settings.setBuiltInZoomControls(true);
        //支持缩放
        settings.setUseWideViewPort(true);
        //支持Js功能
        settings.setJavaScriptEnabled(true);
        //设置小号字体
        settings.setTextSize(WebSettings.TextSize.SMALLER);


        wv_news_detail.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pbLoding.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbLoding.setVisibility(View.INVISIBLE);
            }
            //所有的连都会走此方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        wv_news_detail.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                mToolbar.setTitle(title);
                super.onReceivedTitle(view, title);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                break;
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
