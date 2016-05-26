package com.secondproject.adapters.filmadapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.secondproject.leying.R;
import com.secondproject.models.filmmodels.HotMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-5-19.
 */
public class GridViewHotMoviesAdapter extends BaseAdapter{

    private List<HotMovie> hotMovieList;
    private List<Bitmap> bitmapList;
    private Context context;

    public GridViewHotMoviesAdapter(Context context) {
        this.context = context;
        this.hotMovieList=new ArrayList<HotMovie>();
        this.bitmapList=new ArrayList<Bitmap>();
    }

    public void setList(List<HotMovie> hotMovieList,List<Bitmap> bitmapList){
        this.hotMovieList=hotMovieList;
        this.bitmapList=bitmapList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        int ret=0;
        if (hotMovieList!=null){
            ret=hotMovieList.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        return hotMovieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.item_gridview_hotmovies,null);
            holder.imageView_hotMovies= (ImageView) convertView.findViewById(R.id.imageView_hotMovies);
            holder.textView_movie_name= (TextView) convertView.findViewById(R.id.textView_movie_name);
            holder.linear_score= (LinearLayout) convertView.findViewById(R.id.linear_score);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.imageView_hotMovies.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.imageView_hotMovies.setImageBitmap(bitmapList.get(position));
        holder.textView_movie_name.setText(hotMovieList.get(position).getMovie_name());
        //TODO 评价,CACHE,图片显示失真

        return convertView;
    }

    class ViewHolder{
        ImageView imageView_hotMovies;
        TextView textView_movie_name;
        LinearLayout  linear_score;
    }
}
