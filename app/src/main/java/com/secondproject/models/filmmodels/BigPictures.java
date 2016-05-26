package com.secondproject.models.filmmodels;

/**
 * Created by Administrator on 16-5-17.
 */
public class BigPictures {
    private int id;
    private String promotion_name;
    private int movie_id;
    private String promotion_img_url;

    public BigPictures() {
    }

    public BigPictures(int id, String promotion_name, int movie_id, String promotion_img_url) {
        this.id = id;
        this.promotion_name = promotion_name;
        this.movie_id = movie_id;
        this.promotion_img_url = promotion_img_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPromotion_name() {
        return promotion_name;
    }

    public void setPromotion_name(String promotion_name) {
        this.promotion_name = promotion_name;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getPromotion_img_url() {
        return promotion_img_url;
    }

    public void setPromotion_img_url(String promotion_img_url) {
        this.promotion_img_url = promotion_img_url;
    }
}
