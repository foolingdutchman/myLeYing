package com.secondproject.mepatch.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.alipay.sdk.pay.demo.PayActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.secondproject.Config;
import com.secondproject.leying.R;
import com.secondproject.mepatch.domain.Record;

import net.tsz.afinal.FinalDb;

import java.util.List;

/**
 * Created by Administrator on 16-5-25.
 */
public class PaylistAdapter extends BaseAdapter {
    private List<Record> list;
    private Context context;
    private int type;
    private FinalDb db;

    public PaylistAdapter(List<Record> list, Context context,int type) {
        this.list = list;
        this.context = context;
        this.type=type;
        db=FinalDb.create(context,Config.LEYING_DATABASE);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.pailistitem, null);
            viewHolder = new ViewHolder();
            viewHolder.titletext = (TextView) convertView.findViewById(R.id.tv_pay_name);
            viewHolder.summarytext = (TextView) convertView.findViewById(R.id.tv_pay_hint);
            viewHolder.iconimage = (ImageView) convertView.findViewById(R.id.iv_pay_icon);
            viewHolder.button = (Button) convertView.findViewById(R.id.bt_pay);
            convertView.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.titletext.setText(list.get(position).getTitle());
        viewHolder.summarytext.setText(list.get(position).getSummary());
        if (type== Config.TICKET_PAID) {
            viewHolder.button.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent=new Intent(context,PayActivity.class);
//                context.startActivity(intent);
                    Record record=list.get(position);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("payInfo", record);
                    Intent intent=new Intent(context, PayActivity.class);
                    intent.putExtras(bundle);
                    ((Activity)context).startActivityForResult(intent, Config.PAY_REQ);

                }
            });
        }
        ImageLoader.getInstance().displayImage(list.get(position).getIcon(),viewHolder.iconimage);
        return convertView;
    }

    class ViewHolder {
        TextView titletext;
        TextView summarytext;
        ImageView iconimage;
        Button button;
    }
}
