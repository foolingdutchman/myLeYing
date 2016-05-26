package com.secondproject.com.secondproject.utils.filmutils;

import com.secondproject.models.filmmodels.Pictures;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-5-17.
 */
public class JsonUtils {
    public static List<Pictures> getListFromJson(String json){
        List<Pictures> list = new ArrayList<Pictures>();
        Pictures pictures=null;
        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONArray data=jsonObject.getJSONArray("data");
            for (int i = 0; i <data.length() ; i++) {
                pictures=new Pictures();
                JSONObject jsonObject1= (JSONObject) data.get(i);
                int id=jsonObject1.getInt("id");
                String promotion_img_url=jsonObject1.getString("promotion_img_url");
                pictures.setId(id);
                pictures.setPromotion_img_url(promotion_img_url);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getImgUrlFromJson(String json){
        List<Pictures> list=getListFromJson(json);
        List<String> url=new ArrayList<String>();
        for (int i = 0; i <list.size() ; i++) {
            String imgUrl=list.get(i).getPromotion_img_url();
            url.add(imgUrl);
        }
        return url;
    }
}
