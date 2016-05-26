package com.secondproject.com.secondproject.utils.filmutils;

import com.secondproject.models.filmmodels.HotMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-5-19.
 */
public class JsonUtilHotMovies {
    public static List<HotMovie> getHotMovieList(String json){
        List<HotMovie> hotMovieList=new ArrayList<HotMovie>();
        HotMovie hotMovie=null;
        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONObject jsonObject1=jsonObject.getJSONObject("data");
            JSONArray jsonArray=jsonObject1.getJSONArray("movie_data");
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject jsonObject2= jsonArray.getJSONObject(i);
                int id=jsonObject2.getInt("movie_id");
                String name=jsonObject2.getString("movie_name");
                String director=jsonObject2.getString("movie_director");
                String cast=jsonObject2.getString("movie_cast");
                int wantsee=jsonObject2.getInt("movie_want_see_num");
                String movie_img_url=jsonObject2.getString("movie_img_url");
                int is_new=jsonObject2.getInt("is_new");

                hotMovie=new HotMovie();
                hotMovie.setMovie_id(id);
                hotMovie.setMovie_name(name);
                hotMovie.setMovie_director(director);
                hotMovie.setMovie_cast(cast);
                hotMovie.setMovie_want_see_num(wantsee);
                hotMovie.setMovie_img_url(movie_img_url);
                hotMovie.setIs_new(is_new);

                hotMovieList.add(hotMovie);
            }

            return hotMovieList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getImgUrlFromJson(String json){
        List<HotMovie> list=getHotMovieList(json);
        List<String> url=new ArrayList<String>();
        for (int i = 0; i <list.size() ; i++) {
            String imgUrl=list.get(i).getMovie_img_url();
            url.add(imgUrl);
        }
        return url;
    }
}
