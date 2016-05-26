package com.secondproject.models.filmmodels;

/**
 * Created by Administrator on 16-5-19.
 */
public class HotMoviesObject {
    private HotMovieDataObject dataObject;

    public HotMoviesObject(HotMovieDataObject dataObject) {
        this.dataObject = dataObject;
    }

    public HotMoviesObject() {
    }

    public HotMovieDataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(HotMovieDataObject dataObject) {
        this.dataObject = dataObject;
    }
}
