package com.secondproject.com.secondproject.utils.filmutils;

import com.secondproject.models.filmmodels.ComingMovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-5-19.
 */
public class JsonUtilWillPlay {
    public static List<ComingMovies> getComingList(String json){
        List<ComingMovies> comingMoviesList=new ArrayList<ComingMovies>();
        ComingMovies comingMovies=null;
        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONObject jsonObject1=jsonObject.getJSONObject("data");
            JSONArray jsonArray=jsonObject1.getJSONArray("movie_data");
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject jsonObject2=jsonArray.getJSONObject(i);
                int movie_want_see_num=jsonObject2.getInt("movie_want_see_num");
                int movie_id=jsonObject2.getInt("movie_id");
                String movie_name=jsonObject2.getString("movie_name");
                String movie_director=jsonObject2.getString("movie_director");
                String movie_cast=jsonObject2.getString("movie_cast");
                String movie_img_url=jsonObject2.getString("movie_img_url");

                comingMovies=new ComingMovies(movie_want_see_num,movie_name,movie_id,movie_director,movie_cast,movie_img_url);
                comingMoviesList.add(comingMovies);

            }
            return comingMoviesList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getImgUrlFromJson(String json){
        List<ComingMovies> list=getComingList(json);
        List<String> url=new ArrayList<String>();
        for (int i = 0; i <list.size() ; i++) {
            String imgUrl=list.get(i).getMovie_img_url();
            url.add(imgUrl);
        }
        return url;
    }
}
