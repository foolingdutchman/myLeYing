package com.secondproject.subfragments.subcinemafragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.secondproject.leying.R;
import com.secondproject.leying.SearchActivity;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class CinmaFragmentTop extends Fragment implements View.OnClickListener{
    private View view = null;
    private Context context = null;
    private FragmentManager fm = null;
    private FragmentTransaction transaction = null;
    private ImageView searchClick = null;
    private ImageView mapClick = null;
    private boolean flag = true;
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(context).inflate(R.layout.cinemafragmenttop, null);
        initView();
        searchClick.setOnClickListener(this);
        mapClick.setOnClickListener(this);
        return view;
    }

    private void initView() {
        searchClick = (ImageView) view.findViewById(R.id.searchClick_cinemaFragmentTop);
        mapClick = (ImageView) view.findViewById(R.id.mapClick_cinemaFragmentTop);
        fm = getFragmentManager();
    }

//    public void searchClick(View view){
//        Intent intent = new Intent(context, SearchActivity.class);
//        startActivity(intent);
//    }
//
//    public void mapClick(View view){
//        CinemaFragmentBottom cinemaFragmentBottom1 = new CinemaFragmentBottom();
//        Bundle bundle = new Bundle();
//        bundle.putInt("index", 1);
//        cinemaFragmentBottom1.setArguments(bundle);
//        transaction.replace(R.id.fragment_cinemaFragment_bottom, cinemaFragmentBottom1)
//                .commit();
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.searchClick_cinemaFragmentTop:
                Intent intent = new Intent(context, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.mapClick_cinemaFragmentTop:
                transaction = fm.beginTransaction();
                if(flag){

                    CinemaFragmentBottom cinemaFragmentBottom1 = new CinemaFragmentBottom();
                    Bundle bundle = new Bundle();
                    bundle.putInt("index", 1);
                    cinemaFragmentBottom1.setArguments(bundle);
                    transaction.replace(R.id.fragment_cinemaFragment_bottom, cinemaFragmentBottom1)
                            .commit();
                    ((ImageView)view).setImageResource(R.mipmap.yingyuan_icon_list);
                    flag = !flag;
                }else{
                    CinemaFragmentBottom cinemaFragmentBottom1 = new CinemaFragmentBottom();
                    Bundle bundle = new Bundle();
                    bundle.putInt("index", 0);
                    cinemaFragmentBottom1.setArguments(bundle);
                    transaction.replace(R.id.fragment_cinemaFragment_bottom, cinemaFragmentBottom1)
                            .commit();
                    ((ImageView)view).setImageResource(R.mipmap.yingyuan_icon_map);
                    flag = !flag;
                }
                break;
        }
    }
}
