package com.secondproject.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.secondproject.leying.R;
import com.secondproject.models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/18 0018.
 */
public class MypopupWindowAdapter extends BaseAdapter {

    private Context context = null;
    private List<Item> list = null;
    public boolean flag = false;
    private int count = 0;

    public MypopupWindowAdapter(Context context) {
        this.context = context;
        list = new ArrayList<Item>();
    }

    public void setList(List<Item> list){
        this.list = list;
        flag = true;
        count ++;
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
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.popupwindowitem, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.text_popupWindow);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(list.get(position).name);
        if(position == 0 && flag && count == 1){
            flag = false;
            viewHolder.textView.setBackgroundResource(R.mipmap.yingyuan_xiala_p);
        }else if(viewHolder.textView.getTag() != null){
            if(((int)(viewHolder.textView.getTag())) == position){

                viewHolder.textView.setBackgroundResource(R.mipmap.yingyuan_xiala_p);
            }
        }
        return convertView;
    }

    class ViewHolder{
        TextView textView;
    }

}
