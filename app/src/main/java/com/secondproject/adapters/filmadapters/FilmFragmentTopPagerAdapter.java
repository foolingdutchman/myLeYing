package com.secondproject.adapters.filmadapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by zhang on 2016/5/18.
 */
public class FilmFragmentTopPagerAdapter extends PagerAdapter {
    private List<ImageView> list;
    public void setList(List<ImageView> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        int ret=0;
        if (list!=null){
            ret=list.size();
        }
        return ret;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
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
