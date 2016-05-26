package com.secondproject.models;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class ViewPagerItem extends Item{
    private String id;
    private String promotion_name;
    private String mobile_img;
    private String promotion_url;
    private String forward_style;
    private String play_id;
    private String movie_id;
    private String movie_name;
    private String cinema_id;
    private String cinema_name;
    private String group_id;
    private String promotion_img_url;

    public void parseJson(JSONObject jsonObject){
        promotion_img_url = jsonObject.optString("promotion_img_url");
        movie_id = jsonObject.optString("movie_id");
        promotion_url = jsonObject.optString("promotion_url");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPromotion_name() {
        return promotion_name;
    }

    public void setPromotion_name(String promotion_name) {
        this.promotion_name = promotion_name;
    }

    public String getMobile_img() {
        return mobile_img;
    }

    public void setMobile_img(String mobile_img) {
        this.mobile_img = mobile_img;
    }

    public String getPromotion_url() {
        return promotion_url;
    }

    public void setPromotion_url(String promotion_url) {
        this.promotion_url = promotion_url;
    }

    public String getForward_style() {
        return forward_style;
    }

    public void setForward_style(String forward_style) {
        this.forward_style = forward_style;
    }

    public String getPlay_id() {
        return play_id;
    }

    public void setPlay_id(String play_id) {
        this.play_id = play_id;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(String cinema_id) {
        this.cinema_id = cinema_id;
    }

    public String getCinema_name() {
        return cinema_name;
    }

    public void setCinema_name(String cinema_name) {
        this.cinema_name = cinema_name;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getPromotion_img_url() {
        return promotion_img_url;
    }

    public void setPromotion_img_url(String promotion_img_url) {
        this.promotion_img_url = promotion_img_url;
    }
}
