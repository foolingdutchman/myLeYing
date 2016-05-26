package com.secondproject.mepatch.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.secondproject.leying.R;

/**
 * Created by liushuai on 16/5/19.
 */
public class MeSettingAdapter extends BaseAdapter {
    private int[] icons;
    private String[] settings;
    private Context context;

    public MeSettingAdapter(int[] icons, String[] settings, Context context) {
        this.icons = icons;
        this.settings = settings;
        this.context = context;

    }

    @Override
    public int getCount() {
        Log.i("mesettingadapter",settings.length+"");
        return settings.length;
    }

    @Override
    public Object getItem(int position) {
        return settings[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.mysettingitem, null);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_me_setting);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv_me_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(settings[position]);
        viewHolder.iv.setImageResource(icons[position]);
        return convertView;
    }
    class ViewHolder {
        ImageView iv;
        TextView tv;
    }
}
