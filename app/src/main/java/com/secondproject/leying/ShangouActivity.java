package com.secondproject.leying;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShangouActivity extends AppCompatActivity {
    private TextView textView_cinemaName;
    private TextView textView_cinemaAddr;
    private ListView listView_shanGou;
    private ImageView imageView_close;
    private void initView(){
        textView_cinemaName= (TextView) findViewById(R.id.textView_cinemaName);
        textView_cinemaAddr= (TextView) findViewById(R.id.textView_cinemaAddr);
        listView_shanGou= (ListView) findViewById(R.id.listView_shanGou);
        imageView_close= (ImageView) findViewById(R.id.imageView_close);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangou);
        getSupportActionBar().hide();
        initView();
        setClickListenerForClose();
    }

    public void setClickListenerForClose(){
        imageView_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            AnimationSet animationSet=new AnimationSet(true);
                Animation animation=new RotateAnimation(0f,3600f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                animation.setDuration(3000);
                animationSet.addAnimation(animation);
                Animation animation1=new ScaleAnimation(1.0f,0f,1.0f,0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                animation1.setDuration(2100);
                animationSet.addAnimation(animation1);
                imageView_close.startAnimation(animationSet);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
