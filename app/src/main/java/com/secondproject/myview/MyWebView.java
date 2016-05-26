package com.secondproject.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.lidroid.xutils.view.annotation.event.OnScrollStateChanged;
import com.secondproject.com.secondproject.utils.ScrollInterface;

/**
 * Created by Administrator on 2016/5/23 0023.
 */
public class MyWebView extends WebView {
    ScrollInterface web;
    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWebView(Context context) {
        this(context, null);
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
      //Log.e("hhah",""+l+" "+t+" "+oldl+" "+oldt);
        web.onSChanged(l, t, oldl, oldt);
    }
    public void setOnCustomScroolChangeListener(ScrollInterface t){
        this.web = t;
    }
}
