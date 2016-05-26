package com.secondproject.LocationPatch.utils;

import android.util.Log;

import com.secondproject.LocationPatch.domain.City;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liushuai on 16/5/17.
 */
public class JSONUtils {

    public static List<City> gethotCities(byte[] data) {
        String string = new String(data);
//        Log.i("system.info", "加载--------> " + string);
        if (data != null) {
            try {
                List<City> Jsondata = new ArrayList<>();

                JSONObject jsonobject = new JSONObject(string);
                JSONObject hotdata = jsonobject.getJSONObject("data");
                JSONArray jsonArray = hotdata.getJSONArray("hot_city");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject feed = jsonArray.getJSONObject(i);

                    int id = feed.getInt("id");
                    String name = feed.getString("name");
                    String pinyin = feed.getString("pinyin");
                    long lat =feed.getLong("lat");
                    long lng = feed.getLong("lng");
                    long upperlat = feed.getLong("upperlat");
                    long upperlng =feed.getLong("upperlng");
                    long lowerlat =feed.getLong("lowerlat");
                    long lowerlng =feed.getLong("lowerlng");
                    City city = new City();
                    city.setId(id);
                    city.setName(name);
                    city.setLat(lat);
                    city.setLng(lng);
                    city.setPingyin(pinyin);
                    city.setLowerlat(lowerlat);
                    city.setLowerlng(lowerlng);
                    city.setUpperlng(upperlng);
                    city.setLowerlat(upperlat);
                    Jsondata.add(city);
//                    Log.i("system.info", city.getName());
                }
                for (int i = 0; i < Jsondata.size(); i++) {
                    Log.i("system.info", "HOTCITY--------"+Jsondata.get(i).getName());
                }
                return Jsondata;

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return null;
    }

    public static List<City> getAllCities(byte[] data) {
        String string = new String(data);
//        Log.i("system.info", "加载--------> " + string);
        if (data != null) {
            try {
                List<City> Jsondata = new ArrayList<>();

                JSONObject jsonobject = new JSONObject(string);
                JSONObject hotdata = jsonobject.getJSONObject("data");
                JSONArray jsonArray = hotdata.getJSONArray("all_city");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject feed = jsonArray.getJSONObject(i);

                    int id = feed.getInt("id");
                    String name = feed.getString("name");
                    String pinyin = feed.getString("pinyin");
//                    long lat =Long.parseLong(feed.getString("lat"));
//                    long lng = Long.parseLong(feed.getString("lng"));
//                    long upperlat = Long.parseLong(feed.getString("upperlat"));
//                    long upperlng =Long.parseLong(feed.getString("upperlng"));
//                    long lowerlat =Long.parseLong(feed.getString("lowerlat"));
//                    long lowerlng =Long.parseLong(feed.getString("lowerlng"));
                    City city = new City();
                    city.setId(id);
                    city.setName(name);
                    city.setPingyin(pinyin);
//                    city.setLowerlat(lowerlat);
//                    city.setLat(lat);
//                    city.setLng(lng);
//                    city.setLowerlng(lowerlng);
//                    city.setUpperlng(upperlng);
//                    city.setLowerlat(upperlat);
                    Jsondata.add(city);
//                    Log.i("system.info", "ALLCITY--------" + city.getName());
                }
                for (int i = 0; i < Jsondata.size(); i++) {
                    Log.i("system.info", "ALLCITY--------"+Jsondata.get(i).getName());
                }
                return Jsondata;

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return null;
    }

    public static List<String> getAllCitieName(byte[] data) {
        String string = new String(data);
//        Log.i("system.info", "加载--------> " + string);
        if (data != null) {
            try {
                List<String> Jsondata = new ArrayList<>();

                JSONObject jsonobject = new JSONObject(string);
                JSONObject hotdata = jsonobject.getJSONObject("data");
                JSONArray jsonArray = hotdata.getJSONArray("all_city");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject feed = jsonArray.getJSONObject(i);

                    int id = feed.getInt("id");
                    String name = feed.getString("name");
                    String pinyin = feed.getString("pinyin");
                    long lat = feed.getLong("lat");
                    long lng = feed.getLong("lng");
                    long upperlat = feed.getLong("upperlat");
                    long upperlng = feed.getLong("upperlng");
                    long lowerlat = feed.getLong("lowerlat");
                    long lowerlng = feed.getLong("lowerlng");
//                    City city = new City();
//                    city.setId(id);
//                    city.setName(name);
//                    city.setLat(lat);
//                    city.setLng(lng);
//                    city.setPingyin(pinyin);
//                    city.setLowerlat(lowerlat);
//                    city.setLowerlng(lowerlng);
//                    city.setUpperlng(upperlng);
//                    city.setLowerlat(upperlat);
                    Jsondata.add(name);
                    Log.i("system.info", "ALLCITY--------" +name);
                }
                for (int i = 0; i < Jsondata.size(); i++) {
                    Log.i("system.info", "ALLCITY--------"+Jsondata.get(i));
                }
                return Jsondata;

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return null;
    }

    public static HashMap<String, String> getLocationInfo(String location) {
        HashMap<String, String> locationInfo = new HashMap<>();
        String[] data = location.split("\n");
        for (int i = 0; i < data.length; i++) {
            String[] item = data[i].split(":");
            String key = item[0].trim();
            String value = item[1].trim();
            locationInfo.put(key, value);
        }
        return locationInfo;

    }

}