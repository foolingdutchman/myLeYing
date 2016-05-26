package com.secondproject.leying;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.secondproject.Config;
import com.secondproject.mepatch.Callback.Myfragmentcallback;
import com.secondproject.mepatch.MyFragment;
import com.secondproject.mepatch.domain.Record;
import com.secondproject.mepatch.domain.User;
import com.secondproject.subfragments.FilmFragment;

import net.tsz.afinal.FinalDb;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity implements Myfragmentcallback {


    public static android.support.v4.app.FragmentManager fragmentManager;
    public static SharedPreferences sharedPreferences;
    private Bitmap usersketch;
    private FragmentManager fm = null;
    private FragmentTransaction transaction = null;
    private FilmFragment filmFragment = null;
    private ActionBar actionBar = null;
    private FinalDb db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        //检查登录状态
        checkIslogin();
        initView();
        initDataBase();
        initResolve();

    }

    private void initView() {
        fragmentManager=getSupportFragmentManager();
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        filmFragment = new FilmFragment();
        actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initResolve() {
        transaction.replace(R.id.topFragment_main, filmFragment);
        transaction.commit();
    }

    private void checkIslogin() {
        sharedPreferences=getSharedPreferences(Config.USER_PREFERENCE,MODE_PRIVATE);
        String name = sharedPreferences.getString("name", null);
        if (name!=null) {
            Config.CURRENT_USER=name;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == Config.PROFILE_LOG && resultCode == Config.PROFILE_RESP) {
            Bundle bundle = data.getExtras();
            User currentuser = (User) bundle.getSerializable("currentuser");
            Log.i("profileIntent", "backuser-----" + currentuser.getName()
                    + "---uri: " + currentuser.getSketch() + "---" + currentuser.getMoblenumber());
            MyFragment myFragment = new MyFragment();
            myFragment.setUser(currentuser);
            getFragmentManager().beginTransaction().replace(R.id.topFragment_main, myFragment).commit();
            super.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void initDataBase() {
        db = FinalDb.create(this, Config.LEYING_DATABASE);
        db.update(new User());
        db.update(new Record());
    }

    @Override
    public Bitmap getSketch() {
        return usersketch;
    }

}
