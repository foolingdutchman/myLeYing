package com.secondproject.leying;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.secondproject.com.secondproject.utils.OkHttpUtils;


public class EntryActivity extends AppCompatActivity {
    private ActionBar actionBar = null;
    private ImageView imageView = null;
    private ImageView imageView2 = null;
    private int count = 0;
    private float alpha = 0;
    private String path = "http://img.leying365.com/data/resource/file/dynamic/adContent/2016/5/19/20160519113602449_fnwl6_1242.jpg";
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 110:
                        Bitmap bitmap = (Bitmap) msg.obj;
                        imageView2.setImageBitmap(bitmap);
                    break;
                case 111:
                    alpha += 0.2f;
                    imageView2.setAlpha(alpha);
                    break;
                case 119:
                    Intent intent = new Intent(EntryActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        initView();
        initResolve();

    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.image_entry);
        imageView2  = (ImageView) findViewById(R.id.image2_entry);
        actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initResolve() {
        Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale1);
        imageView.setImageResource(R.mipmap.qidongye);
        imageView.startAnimation(scale);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                   byte[] data = OkHttpUtils.getByteArrayFromUrl(path);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0 , data.length);
                    Thread.sleep(1000);
                    while(true){
                        if(count == 0){
                            Message message = handler.obtainMessage();
                            message.what = 110;
                            message.obj = bitmap;
                            handler.sendMessage(message);
                        }else{
                            handler.sendEmptyMessage(111);
                        }
                        count ++;
                        Thread.sleep(300);

                        if(count >= 5){
                            break;
                        }

                    }

                    handler.sendEmptyMessage(119);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
