package com.alipay.sdk.pay.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.secondproject.leying.R;


public class ExternalFragment extends Fragment {
	private View view;
	private TextView nametext;
	private TextView dscrtext;
	private TextView pricetext;
	private String name;
	private String desc;
	private String price;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.pay_external, container, false);
		initView();
		nametext.setText(name);
		dscrtext.setText(desc);
		pricetext.setText(price);
		return view;
	}
	public  void setInfo(String name,String desc,String price){
		this.name=name;
		this.desc=desc;
		this.price=price;

	}

	private void initView() {
	nametext= (TextView) view.findViewById(R.id.product_subject);
		dscrtext= (TextView) view.findViewById(R.id.product_discription);
		pricetext= (TextView) view.findViewById(R.id.product_price);
	}
}
