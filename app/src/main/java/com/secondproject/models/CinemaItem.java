package com.secondproject.models;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class CinemaItem extends Item{
    private int id1;
    private String id;
    private String name1;
    private String address;
    private String feature_code;
    private String brand_code;
    private String district_code;
    private String show_num;
    private String remain_num;
    private String min_price;
    private String max_price;
    private String have_goods;
    private String coordinate;
    private String promotion_type;
    private String cinema_card;
    private String card_num_hint;

    public void parseJson(JSONObject jsonObject){
        id = jsonObject.optString("id");
        name1 = jsonObject.optString("name");
        address = jsonObject.optString("address");
        show_num = jsonObject.optString("show_num");
        remain_num = jsonObject.optString("remain_num");
        min_price = jsonObject.optString("min_price");
        max_price = jsonObject.optString("max_price");
        coordinate = jsonObject.optString("coordinate");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name) {
        this.name1 = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFeature_code() {
        return feature_code;
    }

    public void setFeature_code(String feature_code) {
        this.feature_code = feature_code;
    }

    public String getBrand_code() {
        return brand_code;
    }

    public void setBrand_code(String brand_code) {
        this.brand_code = brand_code;
    }

    public String getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
    }

    public String getShow_num() {
        return show_num;
    }

    public void setShow_num(String show_num) {
        this.show_num = show_num;
    }

    public String getRemain_num() {
        return remain_num;
    }

    public void setRemain_num(String remain_num) {
        this.remain_num = remain_num;
    }

    public String getMin_price() {
        return min_price;
    }

    public void setMin_price(String min_price) {
        this.min_price = min_price;
    }

    public String getMax_price() {
        return max_price;
    }

    public void setMax_price(String max_price) {
        this.max_price = max_price;
    }

    public String getHave_goods() {
        return have_goods;
    }

    public void setHave_goods(String have_goods) {
        this.have_goods = have_goods;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getPromotion_type() {
        return promotion_type;
    }

    public void setPromotion_type(String promotion_type) {
        this.promotion_type = promotion_type;
    }

    public String getCinema_card() {
        return cinema_card;
    }

    public void setCinema_card(String cinema_card) {
        this.cinema_card = cinema_card;
    }

    public String getCard_num_hint() {
        return card_num_hint;
    }

    public void setCard_num_hint(String card_num_hint) {
        this.card_num_hint = card_num_hint;
    }
}
