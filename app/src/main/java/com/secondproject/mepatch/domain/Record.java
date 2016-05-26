package com.secondproject.mepatch.domain;


import com.secondproject.Config;

import net.tsz.afinal.annotation.sqlite.Table;

import java.io.Serializable;

/**
 * Created by liushuai on 16/5/1.
 */

@Table(name= Config.RECORD_TABLE)
public class Record implements Serializable{
    private int id;
    private String name;
    private String title;
    private String Icon;
    private String summary;
    private float  price;
    private int paid;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }
}
