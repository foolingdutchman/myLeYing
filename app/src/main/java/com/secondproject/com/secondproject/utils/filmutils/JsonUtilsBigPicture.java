package com.secondproject.com.secondproject.utils.filmutils;

import android.util.Log;


import com.secondproject.models.filmmodels.BigPictures;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-5-17.
 */
public class JsonUtilsBigPicture {

    public static List<BigPictures> getListFromJson(String json){
        Log.d("movie_id", "==============>" + json);
        List<BigPictures> list = new ArrayList<BigPictures>();
        BigPictures pictures=null;
        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONArray data=jsonObject.getJSONArray("data");
            for (int i = 0; i <data.length() ; i++) {
                pictures=new BigPictures();
                JSONObject jsonObject1= data.getJSONObject(i);
                int id=jsonObject1.getInt("id");
                int movie_id=jsonObject1.optInt("movie_id");
                Log.d("movie_id", "==============>" + movie_id);
                String promotion_img_url=jsonObject1.getString("promotion_img_url");
                pictures.setId(id);
                pictures.setMovie_id(movie_id);
                pictures.setPromotion_img_url(promotion_img_url);
                list.add(pictures);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getImgUrlFromJson(String json){
        List<BigPictures> list=getListFromJson(json);
        List<String> url=new ArrayList<String>();
        for (int i = 0; i <list.size() ; i++) {
            String imgUrl=list.get(i).getPromotion_img_url();
            url.add(imgUrl);
        }
        return url;
    }
}
