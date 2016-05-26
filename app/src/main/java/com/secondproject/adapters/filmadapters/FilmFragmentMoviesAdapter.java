package com.secondproject.adapters.filmadapters;

import android.app.Fragment;
import android.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-5-18.
 */
public class FilmFragmentMoviesAdapter extends MyFragmentAdapter {
    private List<Fragment> list;
    public FilmFragmentMoviesAdapter(FragmentManager fm) {
        super(fm);
        this.list=new ArrayList<Fragment>();
    }

    public void setList(List<Fragment> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        int ret=0;
        if (list!=null){
            ret=list.size();
        }
        return ret;
    }
}
