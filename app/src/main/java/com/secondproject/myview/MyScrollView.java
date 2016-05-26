package com.secondproject.myview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

/**
 * Created by Administrator on 16-5-16.
 */
public class MyScrollView extends ScrollView {
    private View xuanTingView;
    private View linear_shangou;
    private int gaoDu;
    private ViewGroup xuanTingQuYu;
    private ViewGroup yuanShiQuYu;
    private boolean isXuanTing;
    int y;
    public void setXuanTing(View xuanTingView,View linear_shangou, final ViewGroup yuanShiQuYu,ViewGroup xuanTingQuYu){
        this.xuanTingView=xuanTingView;
        this.yuanShiQuYu=yuanShiQuYu;
        this.xuanTingQuYu=xuanTingQuYu;
        this.linear_shangou=linear_shangou;

        //全局测量监听
        this.xuanTingView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        gaoDu = MyScrollView.this.yuanShiQuYu.getTop();//悬停控件到悬停位置需要滑动的距离
                        MyScrollView.this.xuanTingView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        y = getScrollY();//手势滑动距离
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                getXuanTing();
                break;
            case MotionEvent.ACTION_UP:
                handler.sendEmptyMessageDelayed(110, 30);
                break;
        }
        return super.onTouchEvent(ev);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 110:
                    int newY=getScrollY();
                    if(y==newY){
                        getXuanTing();
                    }else {
                        y=newY;
                        getXuanTing();
                        sendEmptyMessageDelayed(110,30);
                    }
                    break;
            }
        }
    };

    private void getXuanTing(){
        if (y < gaoDu&&isXuanTing) {
            MyScrollView.this.xuanTingQuYu.removeView(MyScrollView.this.xuanTingView);
            MyScrollView.this.xuanTingQuYu.addView(MyScrollView.this.linear_shangou);
            MyScrollView.this.yuanShiQuYu.addView(MyScrollView.this.xuanTingView);
            isXuanTing=false;
        } else if (y >=gaoDu&&(ViewGroup) MyScrollView.this.yuanShiQuYu != null&&!isXuanTing) {
            ((ViewGroup) MyScrollView.this.xuanTingView.getParent()).removeView(MyScrollView.this.xuanTingView);
            MyScrollView.this.xuanTingQuYu.removeView(MyScrollView.this.linear_shangou);
            MyScrollView.this.xuanTingQuYu.addView(MyScrollView.this.xuanTingView);
            isXuanTing=true;
        }
    }
    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //scrollview的源代码是拦截事件的，也就是说，分发后，它拦截了，于是它的子view都拿不到事件
    //因此，我们重写拦截的方法，在一定的条件返回false(不拦截)，就可以使得子view拿到事件。
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//
//        return true;
//    }
}
