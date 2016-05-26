package com.secondproject.LocationPatch.domain;

/**
 * Created by liushuai on 16/5/17.
 */
public class City {
    private int id;
    private String name;
    private String pinyin;
    private long lat;
    private long lng;
    private long uppertat;
    private long upperlng;
    private long lowerlat;
    private long lowerlng;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPingyin() {
        return pinyin;
    }

    public void setPingyin(String pingyin) {
        this.pinyin = pingyin;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLng() {
        return lng;
    }

    public void setLng(long lng) {
        this.lng = lng;
    }

    public long getUppertat() {
        return uppertat;
    }

    public void setUppertat(long uppertat) {
        this.uppertat = uppertat;
    }

    public long getUpperlng() {
        return upperlng;
    }

    public void setUpperlng(long upperlng) {
        this.upperlng = upperlng;
    }

    public long getLowerlat() {
        return lowerlat;
    }

    public void setLowerlat(long lowerlat) {
        this.lowerlat = lowerlat;
    }

    public long getLowerlng() {
        return lowerlng;
    }

    public void setLowerlng(long lowerlng) {
        this.lowerlng = lowerlng;
    }
}
