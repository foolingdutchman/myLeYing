package com.secondproject.subfragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.secondproject.LocationPatch.LocationActivity;
import com.secondproject.adapters.filmadapters.FilmFragmentMoviesAdapter;
import com.secondproject.adapters.filmadapters.FilmFragmentTopPagerAdapter;
import com.secondproject.com.secondproject.utils.OkHttpUtils;
import com.secondproject.com.secondproject.utils.filmutils.CallBack1;
import com.secondproject.com.secondproject.utils.filmutils.CallBack2;
import com.secondproject.com.secondproject.utils.filmutils.JsonUtilsBigPicture;
import com.secondproject.leying.MovieDetailActivity;
import com.secondproject.leying.R;
import com.secondproject.leying.ShangouActivity;
import com.secondproject.models.filmmodels.BigPictures;
import com.secondproject.myview.MyScrollView;
import com.secondproject.subfragments.subfilmfragments.FragmentHotMovies;
import com.secondproject.subfragments.subfilmfragments.FragmentWillPlay;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 16-5-17.
 */
public class FilmFragment extends Fragment implements CallBack1,CallBack2,View.OnClickListener {
    private Context context;
    private View view;
    int num=0;

    //轮播更新UI
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //TODO viewpager轮播效果不佳
            switch (msg.what) {
                case 1:
                    ++num;
                    int index = num % 5;
                    viewPager_firstPage_top.setCurrentItem(index,false);
                    break;
                case 2:
                    getViewPagerImg();//调用顶部图片轮播
                    break;
            }
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(context, R.layout.test, null);

        initView();
        getViewPagerBitmap();//拿到数据
        getViewPagerMovies();//调用热映电影，即将上映电影的展示
        setXuanTing();//设置悬停

        return view;
    }



    //顶部变量
    private ViewPager viewPager_firstPage_top;
    private String jsonUrl="http://mobile2.leying365.com/advert/list?pver=1.0&city_id=148&promotion_type=7&session_id=ENpa0OcfqFuPQzMEYd0zig==&ver=3.1.27&group=0&source=105001&.sig=385ebcdede2e34ee288e6875fe93531d";
    private FilmFragmentTopPagerAdapter pagerAdapter;
    private  List<ImageView> imageViewList;
    private List<Bitmap> bitmapList;
    private ImageView imageView;
    private LinearLayout circlePoint;
    private ImageView[] imageViewsOfCirclePoint;


    //初始化
    private void initView(){
        viewPager_firstPage_top= (ViewPager) view.findViewById(R.id.viewPager_firstPage_top);

        imageViewList = new ArrayList<ImageView>();
        bitmapList=new ArrayList<Bitmap>();
        movieIDList=new ArrayList<Integer>();
        movieNameList=new ArrayList<String>();

        pagerAdapter=new FilmFragmentTopPagerAdapter();

        //小圆点初始化
        circlePoint= (LinearLayout) view.findViewById(R.id.circlePoint);
        int count=circlePoint.getChildCount();
        imageViewsOfCirclePoint=new ImageView[count];
        for (int i = 0; i <count ; i++) {
            imageViewsOfCirclePoint[i]= (ImageView) circlePoint.getChildAt(i);
        }

        //定位,闪购控件初始化
        linear_shangou= (LinearLayout) view.findViewById(R.id.linear_shangou);
        textView_location_movie= (TextView) view.findViewById(R.id.textView_location_movie);
        imageView_location_movie= (ImageView) view.findViewById(R.id.imageView_location_movie);
        imageView_shanGou= (ImageView) view.findViewById(R.id.imageView_shanGou);

        //给定位，闪购控件设置监听
        textView_location_movie.setOnClickListener(this);
        imageView_location_movie.setOnClickListener(this);
        imageView_shanGou.setOnClickListener(this);
    }


    //获取List<Bitmap>,获取movieID
    private ArrayList<Integer> movieIDList;
    private ArrayList<String> movieNameList;
    private void getViewPagerBitmap(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json= OkHttpUtils.getStringFromUrl(jsonUrl);
                List<BigPictures> bigPicturesList= JsonUtilsBigPicture.getListFromJson(json);
                if (bigPicturesList!=null){
                    for (int i = 0; i <bigPicturesList.size() ; i++) {
                        int movieID= bigPicturesList.get(i).getMovie_id();
                        String movieName=bigPicturesList.get(i).getPromotion_name();
                        movieIDList.add(movieID);
                        movieNameList.add(movieName);
                    }
                }
                List<String> list1= JsonUtilsBigPicture.getImgUrlFromJson(json);
                for (int i = 0; i < 5 ; i++) {
                    String url = list1.get(i);
                    byte[] bt = OkHttpUtils.getByteArrayFromUrl(url);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bt, 0, bt.length);
                    bitmapList.add(bitmap);
                }
                handler.sendEmptyMessage(2);
            }
        }).start();


    }

    //获取图片轮播
    private void getViewPagerImg() {
        for (int i = 0; i <5 ; i++) {
            imageView=new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(bitmapList.get(i));
            imageView.setTag(i);
            imageViewList.add(imageView);

            //点击监听
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,MovieDetailActivity.class);
                    Bundle bundle=new Bundle();
                    int index= (int) v.getTag();
                    bundle.putInt("index", index);
                    bundle.putIntegerArrayList("id", movieIDList);
                    bundle.putStringArrayList("name",movieNameList);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }

        //填充数据
        viewPager_firstPage_top.setAdapter(pagerAdapter);
        pagerAdapter.setList(imageViewList);

        //给viewPager_firstPage_top设置监听
        viewPager_firstPage_top.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < imageViewsOfCirclePoint.length; i++) {
                    imageViewsOfCirclePoint[i].setImageResource(R.mipmap.icon_dot_default);
                }
                imageViewsOfCirclePoint[position].setImageResource(R.mipmap.icon_dot_selected);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(4000);
                        handler.sendEmptyMessage(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //电影列表变量
    private ViewPager viewPager_movies;
    private List<Fragment> listMovies;
    private LinearLayout linear_hotplay;
    private LinearLayout linear_willplay;
    private TextView textView_num_hotPlay;
    private TextView textView_num_willPlay;
    //热映电影，即将上映电影的展示
    //TODO 这俩fragment高度待测
    private void getViewPagerMovies(){
        //初始化
        viewPager_movies= (ViewPager) view.findViewById(R.id.viewPager_movies);
        linear_hotplay= (LinearLayout) view.findViewById(R.id.linear_hotplay);
        linear_hotplay.setSelected(true);//热映默认被选
        linear_willplay= (LinearLayout) view.findViewById(R.id.linear_willplay);
        textView_num_hotPlay= (TextView) view.findViewById(R.id.textView_num_hotPlay);
        textView_num_willPlay= (TextView) view.findViewById(R.id.textView_num_willPlay);

        listMovies=new ArrayList<Fragment>();

        //接口回调，子fragment给父fragment传值
        FragmentHotMovies fragmentHotMovies=new FragmentHotMovies();
        fragmentHotMovies.setCallBack1(this);
        FragmentWillPlay fragmentWillPlay=new FragmentWillPlay();
        fragmentWillPlay.setCallBack2(this);
        listMovies.add(fragmentHotMovies);
        listMovies.add(fragmentWillPlay);

        //电影列表viewPager填充数据
        FilmFragmentMoviesAdapter filmFragmentMoviesAdapter=
                new FilmFragmentMoviesAdapter(getChildFragmentManager());
        viewPager_movies.setAdapter(filmFragmentMoviesAdapter);
        filmFragmentMoviesAdapter.setList(listMovies);

        //给linear_hotplay设置监听
        linear_hotplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager_movies.setCurrentItem(0);
            }
        });

        //给linear_willplay设置监听
        linear_willplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager_movies.setCurrentItem(1);
            }
        });


        //给viewPager_movies设置监听
        viewPager_movies.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    linear_willplay.setSelected(false);
                    linear_hotplay.setSelected(true);
                }

                if (position==1){
                    linear_hotplay.setSelected(false);
                    linear_willplay.setSelected(true);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //悬停变量
    private MyScrollView myScrollView;
    private LinearLayout linear_biaoti;
    private LinearLayout yuanshiquyu;
    private LinearLayout xuantingquyu;
    //设置悬停
    public void setXuanTing(){
        linear_biaoti= (LinearLayout) view.findViewById(R.id.linear_biaoti);
        yuanshiquyu= (LinearLayout) view.findViewById(R.id.yuanshiquyu);
        xuantingquyu= (LinearLayout) view.findViewById(R.id.xuantingquyu);
        myScrollView= (MyScrollView) view.findViewById(R.id.myScrollView);

        myScrollView.setXuanTing(linear_biaoti,linear_shangou,yuanshiquyu,xuantingquyu);
    }

    @Override
    public void callBack1(String str1) {
        textView_num_hotPlay.setText(str1);
    }

    @Override
    public void callBack2(String str2) {
        textView_num_willPlay.setText(str2);
    }

    //定位，闪购
    private LinearLayout linear_shangou;
    private TextView textView_location_movie;
    private ImageView imageView_location_movie;
    private ImageView imageView_shanGou;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView_location_movie:
            case R.id.imageView_location_movie:
                Intent intent=new Intent(context, LocationActivity.class);
                context.startActivity(intent);
                break;
            case R.id.imageView_shanGou:
                Intent intent1=new Intent(context, ShangouActivity.class);
                context.startActivity(intent1);
                break;
        }
    }
}
