package com.secondproject.models;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/18 0018.
 */
public class DistrictItem extends Item{

    public void parseJson(JSONObject jsonObject){
        name = jsonObject.optString("name");
        code = jsonObject.optString("code");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
