package com.secondproject.mepatch.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.secondproject.leying.R;

/**
 * Created by liushuai on 16/5/20.
 */
public class RegistFragment1 extends Fragment {
    private View view ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();

        return view;
    }

    private void initView() {
        view= LinearLayout.inflate(getActivity(), R.layout.registpage1,null);

    }
}
