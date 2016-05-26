package com.secondproject.subfragments.subfilmfragments;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.secondproject.Config;
import com.secondproject.adapters.filmadapters.GridViewHotMoviesAdapter;
import com.secondproject.com.secondproject.utils.OkHttpUtils;
import com.secondproject.com.secondproject.utils.filmutils.CallBack1;
import com.secondproject.com.secondproject.utils.filmutils.JsonUtilHotMovies;
import com.secondproject.leying.MovieDetailActivity;
import com.secondproject.leying.R;
import com.secondproject.models.filmmodels.ComingMovies;
import com.secondproject.models.filmmodels.HotMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-5-18.
 */
public class FragmentHotMovies extends Fragment {
    private Context context;
    private GridView gridView_hotMovies;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    getResult();
                    break;
                case 2:
                    callBack1.callBack1("("+hotMovieList.size()+")部");
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=View.inflate(context, R.layout.fragment_hotmovies,null);
        gridView_hotMovies= (GridView) view.findViewById(R.id.gridView_hotMovies);
        getData();

        return view;
    }

    private List<HotMovie> hotMovieList;
    private List<Bitmap> bitmapList;

    /**
     * 请求数据
     */
    private ArrayList<Integer> movieIDList;
    private ArrayList<String> movieNameList;
    private void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                movieIDList=new ArrayList<Integer>();
                movieNameList=new ArrayList<String>();
                String json= OkHttpUtils.getStringFromUrl("http://mobile2.leying365.com/movie/online?pver=1.0&city_id=499&session_id=ENpa0OcfqFuPQzMEYd0zig==&ver=3.1.27&group=0&source=105001&width=128&.sig=5c1ce87258be9b9bd4ab08c103386c04");
                hotMovieList= JsonUtilHotMovies.getHotMovieList(json);
                for (int i = 0; i <hotMovieList.size() ; i++) {
                    int movieID=hotMovieList.get(i).getMovie_id();
                    String movieName=hotMovieList.get(i).getMovie_name();
                    movieNameList.add(movieName);
                    movieIDList.add(movieID);
                }
                handler.sendEmptyMessage(2);
                bitmapList=new ArrayList<Bitmap>();
                List<String> urlList=JsonUtilHotMovies.getImgUrlFromJson(json);
                for (int i = 0; i <urlList.size() ; i++) {
                    String url=urlList.get(i);
                    byte[] bt=OkHttpUtils.getByteArrayFromUrl(url);
                    Bitmap bitmap= BitmapFactory.decodeByteArray(bt,0,bt.length);
                    bitmapList.add(bitmap);
                }
                handler.sendEmptyMessage(1);
            }
        }).start();
    }

    private GridViewHotMoviesAdapter adapter;

    /**
     * 处理结果
     */
    private void getResult(){
        adapter=new GridViewHotMoviesAdapter(context);
        gridView_hotMovies.setAdapter(adapter);
        adapter.setList(hotMovieList, bitmapList);

        //设置点击监听
        gridView_hotMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                Bundle bundle = new Bundle();

                HotMovie movie=hotMovieList.get(position);
                bundle.putSerializable("movie",movie);
                bundle.putInt("type", Config.MOVIE_TYPE2);
//                int index = position;
//                bundle.putInt("index", index);
//                bundle.putIntegerArrayList("id", movieIDList);
//                bundle.putStringArrayList("name",movieNameList);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    private CallBack1 callBack1;

    public CallBack1 getCallBack1() {
        return callBack1;
    }

    public void setCallBack1(CallBack1 callBack1) {
        this.callBack1 = callBack1;
    }
}
