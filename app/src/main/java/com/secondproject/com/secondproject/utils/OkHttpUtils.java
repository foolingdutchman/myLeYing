package com.secondproject.com.secondproject.utils;

import android.app.DownloadManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 16-5-12.
 */
public class OkHttpUtils {

    private static OkHttpClient okHttpClient = null;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(50, TimeUnit.SECONDS);
        okHttpClient = builder.build();
    }
    public static Response execute(Request request) throws IOException {

        return okHttpClient.newCall(request).execute();
    }

    public static String getStringFromUrl(String url){

        Request request = new Request.Builder().url(url).build();

        try {
            Response response = execute(request);
            if(response.isSuccessful()){
                String res = response.body().string();
                return res;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getByteArrayFromUrl(String url){

        Request request = new Request.Builder().url(url).build();

        try {
            Response response = execute(request);
            if(response.isSuccessful()){
                byte[] data = response.body().bytes();
                return data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
