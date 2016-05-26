package com.secondproject.mainfragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.secondproject.leying.R;
import com.secondproject.mepatch.MyFragment;
import com.secondproject.subfragments.CinemaFragment;
import com.secondproject.subfragments.ExerciseFragment;
import com.secondproject.subfragments.FilmFragment;


/**
 * Created by Administrator on 2016/5/16 0016.
 */
public class BottomFragment extends Fragment implements View.OnClickListener{

    private View view = null;
    private Context context = null;
    private ImageView image1 = null;
    private ImageView image2 = null;
    private ImageView image3 = null;
    private ImageView image4 = null;
    private TextView textView1 = null;
    private TextView textView2 = null;
    private TextView textView3 = null;
    private TextView textView4 = null;
    private int[] images = new int[]{R.drawable.bottom_icon2, R.drawable.bottom_icon3, R.drawable.bottom_icon4, R.drawable.bottom_icon5};
    private int[] imagess = new int[]{R.drawable.bottom_icon2_p, R.drawable.bottom_icon3_p, R.drawable.bottom_icon4_p, R.drawable.bottom_icon5_p};
    private FragmentManager fm = null;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = LayoutInflater.from(context).inflate(R.layout.activity_main_bottom, null);
        initView();
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
        return view;
    }

    private void initView() {
        image1 = (ImageView) view.findViewById(R.id.iamge1_bottom);
        image2 = (ImageView) view.findViewById(R.id.iamge2_bottom);
        image3 = (ImageView) view.findViewById(R.id.iamge3_bottom);
        image4 = (ImageView) view.findViewById(R.id.iamge4_bottom);
        textView1 = (TextView) view.findViewById(R.id.text1_bottom);
        textView2 = (TextView) view.findViewById(R.id.text2_bottom);
        textView3 = (TextView) view.findViewById(R.id.text3_bottom);
        textView4 = (TextView) view.findViewById(R.id.text4_bottom);
        fm = getFragmentManager();
    }

    @Override
    public void onClick(View v) {
       FragmentTransaction transaction = fm.beginTransaction();
        switch (v.getId()){

            case R.id.text1_bottom:
                FilmFragment filmFragment = new FilmFragment();
                transaction.replace(R.id.topFragment_main, filmFragment);
                image1.setImageResource(imagess[0]);
                image2.setImageResource(images[1]);
                image3.setImageResource(images[2]);
                image4.setImageResource(images[3]);

                textView1.setTextColor(Color.parseColor("#CE2B2B"));
                textView2.setTextColor(Color.parseColor("#9B9C9E"));
                textView3.setTextColor(Color.parseColor("#9B9C9E"));
                textView4.setTextColor(Color.parseColor("#9B9C9E"));
                transaction.commit();
                break;
            case R.id.text2_bottom:
                CinemaFragment cinemaFragment = new CinemaFragment();
                transaction.replace(R.id.topFragment_main, cinemaFragment);
                image2.setImageResource(imagess[1]);
                image1.setImageResource(images[0]);
                image3.setImageResource(images[2]);
                image4.setImageResource(images[3]);

                textView2.setTextColor(Color.parseColor("#CE2B2B"));
                textView1.setTextColor(Color.parseColor("#9B9C9E"));
                textView3.setTextColor(Color.parseColor("#9B9C9E"));
                textView4.setTextColor(Color.parseColor("#9B9C9E"));
                transaction.commit();
                break;
            case R.id.text3_bottom:
                ExerciseFragment exerciseFragment = new ExerciseFragment();
                transaction.replace(R.id.topFragment_main, exerciseFragment);
                image3.setImageResource(imagess[2]);
                image2.setImageResource(images[1]);
                image1.setImageResource(images[0]);
                image4.setImageResource(images[3]);

                textView3.setTextColor(Color.parseColor("#CE2B2B"));
                textView2.setTextColor(Color.parseColor("#9B9C9E"));
                textView1.setTextColor(Color.parseColor("#9B9C9E"));
                textView4.setTextColor(Color.parseColor("#9B9C9E"));
                transaction.commit();
                break;
            case R.id.text4_bottom:
                MyFragment myFragment = new MyFragment();
                transaction.replace(R.id.topFragment_main, myFragment);
                image4.setImageResource(imagess[3]);
                image2.setImageResource(images[1]);
                image3.setImageResource(images[2]);
                image1.setImageResource(images[0]);

                textView4.setTextColor(Color.parseColor("#CE2B2B"));
                textView2.setTextColor(Color.parseColor("#9B9C9E"));
                textView3.setTextColor(Color.parseColor("#9B9C9E"));
                textView1.setTextColor(Color.parseColor("#9B9C9E"));
                transaction.commit();
                break;

        }

    }

}
