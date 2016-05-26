package com.secondproject.subfragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.secondproject.leying.R;
import com.secondproject.subfragments.subcinemafragments.CinemaFragmentBottom;
import com.secondproject.subfragments.subcinemafragments.CinmaFragmentTop;

/**
 * Created by Administrator on 2016/5/16 0016.
 */
public class CinemaFragment extends Fragment {

    private View view = null;
    private Context context = null;
    private FragmentManager fm = null;
    private FragmentTransaction transaction1 = null;
    private FragmentTransaction transaction2 = null;
    private CinemaFragmentBottom cinemaFragmentBottom = null;
    private CinmaFragmentTop cinmaFragmentTop = null;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(context).inflate(R.layout.cinemafragment, null);
        initView();
        transaction1.replace(R.id.fragment_cinemaFragment_bottom, cinemaFragmentBottom);
        transaction1.commit();
        transaction2.replace(R.id.fragment_cinemaFragment_top, cinmaFragmentTop);
        transaction2.commit();
        return view;
    }

    private void initView() {
        fm = getFragmentManager();
        transaction1 = fm.beginTransaction();
        transaction2 = fm.beginTransaction();
        cinemaFragmentBottom = new CinemaFragmentBottom();
        Bundle bundle = new Bundle();
        bundle.putInt("index", 0);
        cinemaFragmentBottom.setArguments(bundle);
        cinmaFragmentTop = new CinmaFragmentTop();
    }
}
