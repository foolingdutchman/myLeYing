package com.secondproject.leying;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;

import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.secondproject.com.secondproject.utils.ScrollInterface;
import com.secondproject.myview.MyWebView;

public class WebViewActivity extends AppCompatActivity {
    private ActionBar actionBar = null;
    private MyWebView webView = null;
    private LinearLayout lay_bottom_layout = null;
    private LinearLayout lay_top_layout = null;
    private ImageView imageView = null;
    private TextView textView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
        initResolve();
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView_back_detailsActivity);
        textView = (TextView) findViewById(R.id.text_webViewActivity);
        webView = (MyWebView)findViewById(R.id.webView_webViewActivity);
        lay_bottom_layout = (LinearLayout) findViewById(R.id.linear_webViewActivity);
        lay_top_layout = (LinearLayout) findViewById(R.id.linear2_webViewActivity);
        actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initResolve() {
        //允许webview上的网页使用javasctipt
        webView.getSettings().setJavaScriptEnabled(true);
        //支持alert()这样的Javascript脚本，就是允许网页弹窗
        webView.setWebChromeClient(new WebChromeClient());
        //如果不使用此代码，超链接会跳入到浏览器
        webView.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String str = bundle.getString("path");
        webView.loadUrl(str);

        textView.setText(bundle.getString("text"));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView.setOnCustomScroolChangeListener(new ScrollInterface() {
            @Override
            public void onSChanged(int l, int t, int oldl, int oldt) {
                // TODO Auto-generated method stub
                float webcontent = webView.getContentHeight() * webView.getScale();//webview的高度
                float webnow = webView.getHeight()+ webView.getScrollY();//当前webview的高度
                if( webView.getContentHeight()* webView.getScale() -( webView.getHeight()+ webView.getScrollY()) <= 250){

                    //已经处于底端
                    lay_bottom_layout.setVisibility(View.VISIBLE);

                }else {
                    lay_bottom_layout.setVisibility(View.GONE);
                }
//已经处于顶端
                Log.d("height", "=================>" + webView.getScrollY());
                if (webView.getScrollY() <= 100) {
                    lay_top_layout.setVisibility(View.VISIBLE);
                }else{
                    lay_top_layout.setVisibility(View.GONE);
                }
            }
        });
    }
}
