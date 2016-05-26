package com.secondproject.mepatch.adapters;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by liushuai on 16/5/21.
 */
public class RegistpageAdapter extends PagerAdapter {
    private List<View> viewList;

    public RegistpageAdapter(List<View> viewList) {
        this.viewList = viewList;
    }


    @Override
    public int getCount() {
        Log.i("popwindowadapter",""+viewList.size());
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }
}
