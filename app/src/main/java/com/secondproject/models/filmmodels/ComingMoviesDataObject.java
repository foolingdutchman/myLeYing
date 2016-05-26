package com.secondproject.models.filmmodels;

import java.util.List;

/**
 * Created by Administrator on 16-5-19.
 */
public class ComingMoviesDataObject {
    private List<ComingMovies> list;

    public ComingMoviesDataObject(List<ComingMovies> list) {
        this.list = list;
    }

    public ComingMoviesDataObject() {
    }

    public List<ComingMovies> getList() {
        return list;
    }

    public void setList(List<ComingMovies> list) {
        this.list = list;
    }
}
