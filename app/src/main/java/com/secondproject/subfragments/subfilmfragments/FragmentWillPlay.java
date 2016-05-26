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

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.secondproject.Config;
import com.secondproject.adapters.filmadapters.GridViewComingMoviesAdapter;
import com.secondproject.com.secondproject.utils.OkHttpUtils;
import com.secondproject.com.secondproject.utils.filmutils.CallBack2;
import com.secondproject.com.secondproject.utils.filmutils.JsonUtilWillPlay;
import com.secondproject.leying.MovieDetailActivity;
import com.secondproject.leying.R;
import com.secondproject.models.filmmodels.ComingMovies;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-5-18.
 */
public class FragmentWillPlay extends Fragment {

    private Context context;
    private DbUtils dbUtils;
    private GridView gridView_willPlay;
    private CallBack2 callBack2 = null;

    public CallBack2 getCallBack2() {
        return callBack2;
    }

    public void setCallBack2(CallBack2 callBack2) {
        this.callBack2 = callBack2;
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    getResult();
                    break;
                case 2:
                    callBack2.callBack2("(" + comingMoviesList.size() + ")部");
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
        View view=View.inflate(context, R.layout.fragment_willplay,null);
        gridView_willPlay= (GridView) view.findViewById(R.id.gridView_willPlay);
        dbUtils=DbUtils.create(context);

        getData();
        return view;
    }

    private List<ComingMovies> comingMoviesList;
    private List<Bitmap> bitmapList;
    /**
     * 请求数据
     */
    private ArrayList<Integer> movieIDList;
    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                movieIDList=new ArrayList<Integer>();
                String json= OkHttpUtils.getStringFromUrl("http://mobile2.leying365.com/movie/coming?pver=1.0&city_id=148&session_id=ENpa0OcfqFuPQzMEYd0zig==&ver=3.1.27&group=0&source=105001&width=128&.sig=76edd36a3ca8f7d87573186e3c89fb16");
                comingMoviesList= JsonUtilWillPlay.getComingList(json);
                for (int i = 0; i <comingMoviesList.size() ; i++) {
                    ComingMovies comingMovies=comingMoviesList.get(i);
//                    try {
//                        dbUtils.save(comingMovies);
//                    } catch (DbException e) {
//                        e.printStackTrace();
//                    }
                   int movieID=comingMoviesList.get(i).getMovie_id();
                    movieIDList.add(movieID);
                }
                handler.sendEmptyMessage(2);
                bitmapList=new ArrayList<Bitmap>();
                List<String> urlList=JsonUtilWillPlay.getImgUrlFromJson(json);
                for (int i = 0; i <urlList.size() ; i++) {
                    String url=urlList.get(i);
                    byte[] bt=OkHttpUtils.getByteArrayFromUrl(url);
                    Bitmap bitmap= BitmapFactory.decodeByteArray(bt, 0, bt.length);
                    bitmapList.add(bitmap);
                }
                handler.sendEmptyMessage(1);
            }
        }).start();
    }

    private GridViewComingMoviesAdapter comingMoviesAdapter;
    /**
     * 处理结果
     */
    private void getResult(){
        comingMoviesAdapter=new GridViewComingMoviesAdapter(context);
        gridView_willPlay.setAdapter(comingMoviesAdapter);
        comingMoviesAdapter.setList(comingMoviesList, bitmapList);

        //设置点击监听
        gridView_willPlay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context,MovieDetailActivity.class);
                Bundle bundle=new Bundle();
                ComingMovies movie=comingMoviesList.get(position);
                bundle.putSerializable("movie",movie);
                bundle.putInt("type", Config.MOVIE_TYPE1);
//                int index= position;
//                bundle.putInt("index", index);
//                bundle.putIntegerArrayList("id", movieIDList);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

}
