package com.secondproject.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.secondproject.leying.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class MyPagerAdapter extends PagerAdapter {

    private List<ImageView> list = null;
    private Context context = null;

    public MyPagerAdapter(Context context){
        this.context = context;
        list = new ArrayList<ImageView>();
    }

    public void setList(List<ImageView> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void setAllList(List<ImageView> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));

        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }
}
