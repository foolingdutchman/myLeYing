package com.secondproject.com.secondproject.utils;

import com.secondproject.models.Item;
import com.secondproject.models.ViewPagerItem;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public interface CallBack {
    public void callBack(HashMap<String, List<Item>> map);
    public void callBackListView(HashMap<String, List<Item>> map);
}
