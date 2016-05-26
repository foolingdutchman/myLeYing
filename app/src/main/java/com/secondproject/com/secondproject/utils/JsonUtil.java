package com.secondproject.com.secondproject.utils;

import com.secondproject.models.BrandItem;
import com.secondproject.models.CinemaItem;
import com.secondproject.models.DistrictItem;
import com.secondproject.models.Item;
import com.secondproject.models.SpecialItem;
import com.secondproject.models.ViewPagerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class JsonUtil {

    public static HashMap<String, List<Item>> parseJson(String str){
        List<Item> list = null;

        HashMap<String, List<Item>> map = null;
        if(str != null){
            list = new ArrayList<Item>();
            map = new HashMap<String, List<Item>>();
            try {
                JSONObject jsonObject = new JSONObject(str);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for(int i = 0; i < jsonArray.length(); i ++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    ViewPagerItem viewPagerItem = new ViewPagerItem();
                    viewPagerItem.parseJson(jsonObject1);
                    list.add(viewPagerItem);
                }
                map.put("viewPager", list);
                return map;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static HashMap<String, List<Item>> parseJson1(String str){
        List<Item> cinemaList = null;
        List<Item> brandList = null;
        List<Item> specialList = null;
        List<Item> districtList = null;
        HashMap<String, List<Item>> map = null;
        if(str != null){
            cinemaList = new ArrayList<Item>();
            brandList  = new ArrayList<Item>();
            specialList = new ArrayList<Item>();
            districtList = new ArrayList<Item>();
            map = new HashMap<String, List<Item>>();
            try {
                JSONObject jsonObject = new JSONObject(str);
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                JSONArray jsonArray = jsonObject1.getJSONArray("cinema_data");
                JSONArray jsonArray1 = jsonObject1.getJSONArray("district_data");
                JSONArray jsonArray2 = jsonObject1.getJSONArray("brand_data");
                JSONArray jsonArray3 = jsonObject1.getJSONArray("special_data");

                for(int i = 0; i < jsonArray.length(); i ++){
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    CinemaItem cinemaItem = new CinemaItem();
                    cinemaItem.parseJson(jsonObject2);
                    cinemaList.add(cinemaItem);
                }
                map.put("cinema_data", cinemaList);
                DistrictItem districtItem1 = new DistrictItem();
                districtItem1.name = "全部城区";
                districtList.add(districtItem1);
                for(int j = 0; j < jsonArray1.length(); j ++){
                    JSONObject jsonObject3 = jsonArray1.getJSONObject(j);
                    DistrictItem districtItem = new DistrictItem();
                    districtItem.parseJson(jsonObject3);
                    districtList.add(districtItem);
                }
                map.put("district_data", districtList);
                BrandItem brandItem1 = new BrandItem();
                brandItem1.name = "全部品牌";
                brandList.add(brandItem1);
                for(int j = 0; j < jsonArray2.length(); j ++){
                    JSONObject jsonObject4 = jsonArray2.getJSONObject(j);
                    BrandItem brandItem = new BrandItem();
                    brandItem.parseJson(jsonObject4);
                    brandList.add(brandItem);
                }
                map.put("brand_data", brandList);

                SpecialItem specialItem1 = new SpecialItem();
                specialItem1.name = "特色筛选";
                specialList.add(specialItem1);
                for(int j = 0; j < jsonArray3.length(); j ++){
                    JSONObject jsonObject5 = jsonArray3.getJSONObject(j);
                    SpecialItem specialItem = new SpecialItem();
                    specialItem.parseJson(jsonObject5);
                    specialList.add(specialItem);
                }
                map.put("special_data", specialList);

                return map;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
