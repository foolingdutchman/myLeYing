package com.secondproject.models;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/18 0018.
 */
public class BrandItem extends Item{

    private String img;

    public void parseJson(JSONObject jsonObject){
        name = jsonObject.optString("name");
        img = jsonObject.optString("img");
        code = jsonObject.optString("code");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
