package com.secondproject.models.filmmodels;

import java.util.List;

/**
 * Created by Administrator on 16-5-19.
 */
public class HotMovieDataObject {
    private List<HotMovie> hotMovieList;

    public HotMovieDataObject(List<HotMovie> hotMovieList) {
        this.hotMovieList = hotMovieList;
    }

    public HotMovieDataObject() {
    }

    public List<HotMovie> getHotMovieList() {
        return hotMovieList;
    }

    public void setHotMovieList(List<HotMovie> hotMovieList) {
        this.hotMovieList = hotMovieList;
    }
}
