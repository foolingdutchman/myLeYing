package com.secondproject.models.filmmodels;

/**
 * Created by Administrator on 16-5-19.
 */
public class ComingMoviesObject {
    private ComingMoviesDataObject comingMoviesDataObject;

    public ComingMoviesObject() {
    }

    public ComingMoviesObject(ComingMoviesDataObject comingMoviesDataObject) {

        this.comingMoviesDataObject = comingMoviesDataObject;
    }

    public ComingMoviesDataObject getComingMoviesDataObject() {
        return comingMoviesDataObject;
    }

    public void setComingMoviesDataObject(ComingMoviesDataObject comingMoviesDataObject) {
        this.comingMoviesDataObject = comingMoviesDataObject;
    }
}
