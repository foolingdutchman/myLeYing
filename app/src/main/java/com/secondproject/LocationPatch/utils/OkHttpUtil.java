package com.secondproject.LocationPatch.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 16-5-12.
 */
public class OkHttpUtil {
    private static OkHttpClient okHttpClient = null;
    private static Request request = null;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(50, TimeUnit.SECONDS);
        okHttpClient = builder.build();
    }

    public static Response excute(Request request) {

        try {
            return okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringResponse(String url) {
        Response response = null;
        String data = null;
        request = new Request.Builder().url(url).build();
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                data = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;

    }

    public static byte[] getByteArray(String url) {
        byte[] data = null;

        Response response = null;
        request = new Request.Builder().url(url).build();
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                data = response.body().bytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}

