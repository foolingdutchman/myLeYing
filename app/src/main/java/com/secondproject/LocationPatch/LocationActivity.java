package com.secondproject.LocationPatch;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.baidu.location.service.LocationApplication;
import com.baidu.location.service.LocationService;
import com.secondproject.LocationPatch.Callback.LocationCallback;
import com.secondproject.LocationPatch.domain.City;
import com.secondproject.LocationPatch.fragments.LocationFragment;
import com.secondproject.LocationPatch.utils.JSONUtils;
import com.secondproject.LocationPatch.utils.OkHttpUtil;
import com.secondproject.leying.R;


import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity implements LocationCallback {

    private LocationFragment locationFragment;
    private LocationService locationService;
    private LocationApplication locationApplication;
    private byte[] data;
    private List<City>hotCities;
    private List<City>allCities;
    private FragmentManager fragmentManager;
    private static String LOCATION_URL="http://mobile2.leying365.com/city/list?city_id=499&group=0&pver=" +
            "1.0&session_id=&source=103001&ver=3.1.25&.sig=376b3e3802a068745044109bc759e36a";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
         locationApplication=(LocationApplication)getApplication();
//        locationApplication=LocationApplication.getLocationInstance();
        fragmentManager=getSupportFragmentManager();
        initData();


    }

    private void initData() {
        hotCities=new ArrayList<>();
        allCities=new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                data = OkHttpUtil.getByteArray(LOCATION_URL);
                if (data!=null) {
                    hotCities= JSONUtils.gethotCities(data);
                    allCities=JSONUtils.getAllCities(data);
                    for (int i = 0; i <allCities.size() ; i++) {
                        Log.i("locationactivity", allCities.get(i).getName());
                    }
                    locationFragment=new LocationFragment();
                    locationFragment.locationCallback=LocationActivity.this;
                    locationFragment.initData(hotCities, allCities);
                    locationFragment.setLocationapplaction(locationApplication);
                    fragmentManager.beginTransaction().replace(R.id.fl_location_container, locationFragment).commit();
                }
            }
        }).start();

    }

    @Override
    public void stop() {
        this.finish();
    }



}
