package com.secondproject.mepatch.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by liushuai on 16/5/19.
 */
public class ShowPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> meShowFragmentList;


    public ShowPageAdapter(FragmentManager fm) {
        super(fm);
    }



    public void setMeShowFragmentList(List<Fragment> meShowFragmentList) {
        this.meShowFragmentList = meShowFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return meShowFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return meShowFragmentList.size();
    }
}
