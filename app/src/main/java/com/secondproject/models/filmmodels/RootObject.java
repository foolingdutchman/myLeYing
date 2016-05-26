package com.secondproject.models.filmmodels;

import java.util.List;

/**
 * Created by Administrator on 16-5-17.
 */
public class RootObject {
    private List<Pictures> data;

    public RootObject(List<Pictures> data) {
        this.data = data;
    }

    public RootObject() {
    }

    public List<Pictures> getData() {
        return data;
    }

    public void setData(List<Pictures> data) {
        this.data = data;
    }
}
