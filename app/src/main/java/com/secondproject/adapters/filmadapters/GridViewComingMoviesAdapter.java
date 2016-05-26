package com.secondproject.adapters.filmadapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.secondproject.Config;
import com.secondproject.leying.R;
import com.secondproject.mepatch.domain.Record;
import com.secondproject.models.filmmodels.ComingMovies;

import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-5-19.
 */
public class GridViewComingMoviesAdapter extends BaseAdapter {
    private Context context;
    private List<ComingMovies> comingMoviesList;
    private List<Bitmap> bitmapList;


    public GridViewComingMoviesAdapter(Context context) {
        this.context = context;
        comingMoviesList=new ArrayList<ComingMovies>();
        bitmapList=new ArrayList<Bitmap>();


    }

    public void setList(List<ComingMovies> comingMoviesList,List<Bitmap> bitmapList){
        this.comingMoviesList=comingMoviesList;
        this.bitmapList=bitmapList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        int ret=0;
        if (comingMoviesList!=null){
            ret=comingMoviesList.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        return comingMoviesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.item_gridview_willplay,null);
            holder.imageView_willPlay= (ImageView) convertView.findViewById(R.id.imageView_willPlay);
            holder.textView_movie_name_will= (TextView) convertView.findViewById(R.id.textView_movie_name_will);
            holder.num_wantsee= (TextView) convertView.findViewById(R.id.num_wantsee);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.imageView_willPlay.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.imageView_willPlay.setImageBitmap(bitmapList.get(position));
        holder.textView_movie_name_will.setText(comingMoviesList.get(position).getMovie_name());
        holder.num_wantsee.setText(comingMoviesList.get(position).getMovie_want_see_num() + "");
        //TODO ,CACHE,图片显示失真,日期画线

        return convertView;
    }

    class ViewHolder{
        ImageView imageView_willPlay;
        TextView textView_movie_name_will;
        TextView num_wantsee;
    }
}
