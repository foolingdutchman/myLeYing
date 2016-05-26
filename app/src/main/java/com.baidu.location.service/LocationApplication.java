package com.baidu.location.service;


import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.secondproject.com.secondproject.utils.BitmapCacheHelper;

/**
 * 主Application，所有百度定位SDK的接口说明请参考线上文档：http://developer.baidu.com/map/loc_refer/index.html
 *
 * 百度定位SDK官方网站：http://developer.baidu.com/map/index.php?title=android-locsdk
 * 
 * 直接拷贝com.baidu.location.service包到自己的工程下，简单配置即可获取定位结果，也可以根据demo内容自行封装
 */
public class LocationApplication extends Application {
//    private static LocationApplication locationApplication;
//    private LocationApplication(){
//        locationApplication=new LocationApplication();
//    }
//    public static LocationApplication getLocationInstance(){
//        if (locationApplication==null){
//            locationApplication=new LocationApplication();
//        }
//        return locationApplication;
//    }
	public LocationService locationService;
    public Vibrator mVibrator;
    public static int count = 0;
    public static BitmapCacheHelper bitmapCacheHelper = null;
    @Override
    public void onCreate() {
        super.onCreate();
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        WriteLog.getInstance().init(); // 初始化日志
        SDKInitializer.initialize(getApplicationContext());
        if (bitmapCacheHelper == null) {
            bitmapCacheHelper = new BitmapCacheHelper();
        }
       
    }
}