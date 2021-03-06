package com.secondproject.adapters;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.secondproject.leying.R;
import com.secondproject.models.CinemaItem;
import com.secondproject.models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/19 0019.
 */
public class MySearchListViewAdapter extends BaseAdapter {

    private Context context = null;
    private List<Item> list = null;

    public MySearchListViewAdapter(Context context) {
        this.context = context;
        list = new ArrayList<Item>();
    }

    public void setList(List<Item> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void setAllList(List<Item> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CinemaItem cinemaItem = (CinemaItem) list.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.searchlistviewitem, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView1 = (TextView) convertView.findViewById(R.id.text_searchListView);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView1.setText(cinemaItem.getName1());
        viewHolder.textView1.setTag(cinemaItem.getName1());
        return convertView;
    }

    class ViewHolder{
        TextView textView1;
    }
}
