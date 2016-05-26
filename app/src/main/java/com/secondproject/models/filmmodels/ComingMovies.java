package com.secondproject.models.filmmodels;

import java.io.Serializable;

/**
 * Created by Administrator on 16-5-19.
 */
public class ComingMovies  implements Serializable{

    private int movie_want_see_num;
    private String movie_name;
    private int movie_id;
    private String movie_director;
    private String movie_cast;
    private String movie_img_url;

    public ComingMovies(int movie_want_see_num, String movie_name, int movie_id, String movie_director, String movie_cast, String movie_img_url) {
        this.movie_want_see_num = movie_want_see_num;
        this.movie_name = movie_name;
        this.movie_id = movie_id;
        this.movie_director = movie_director;
        this.movie_cast = movie_cast;
        this.movie_img_url = movie_img_url;
    }

    public ComingMovies() {
    }

    public int getMovie_want_see_num() {
        return movie_want_see_num;
    }

    public void setMovie_want_see_num(int movie_want_see_num) {
        this.movie_want_see_num = movie_want_see_num;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_director() {
        return movie_director;
    }

    public void setMovie_director(String movie_director) {
        this.movie_director = movie_director;
    }

    public String getMovie_cast() {
        return movie_cast;
    }

    public void setMovie_cast(String movie_cast) {
        this.movie_cast = movie_cast;
    }

    public String getMovie_img_url() {
        return movie_img_url;
    }

    public void setMovie_img_url(String movie_img_url) {
        this.movie_img_url = movie_img_url;
    }
}
