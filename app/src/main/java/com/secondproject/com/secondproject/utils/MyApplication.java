package com.secondproject.com.secondproject.utils;

import android.app.Application;

//�������Ӧ�ó����ʵ�������������������ھ������ǵ�Ӧ�ó������������
public class MyApplication extends Application {
	public static int count = 0;
	public static BitmapCacheHelper bitmapCacheHelper = null;

	//����Ӧ�ó���������ʱ��ͻ�ص��������
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		if (bitmapCacheHelper == null) {
			bitmapCacheHelper = new BitmapCacheHelper();
		}
	}

}
