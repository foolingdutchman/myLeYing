package com.secondproject.models.filmmodels;

import java.util.List;

/**
 * Created by Administrator on 16-5-17.
 */
public class BigPicturesObject {
    private List<BigPictures> data;

    public BigPicturesObject(List<BigPictures> data) {
        this.data = data;
    }

    public BigPicturesObject() {
    }

    public List<BigPictures> getData() {
        return data;
    }

    public void setData(List<BigPictures> data) {
        this.data = data;
    }
}
