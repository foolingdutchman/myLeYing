package com.secondproject.mepatch.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.secondproject.leying.R;

/**
 * Created by liushuai on 16/5/19.
 */
public class MeShowFragment extends Fragment {
    private int bg_show;
    private ImageView imageView;
    private View view;

    public void setBg_show(int bg_show) {
        this.bg_show = bg_show;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=View.inflate(getActivity(), R.layout.meshowfragment,null);
        imageView= (ImageView) view.findViewById(R.id.iv_meshow);
        imageView.setImageResource(bg_show);
        return view;
    }
}
