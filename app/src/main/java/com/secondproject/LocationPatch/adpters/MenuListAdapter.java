package com.secondproject.LocationPatch.adpters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.secondproject.LocationPatch.domain.City;
import com.secondproject.LocationPatch.stickyheader.StickyListHeadersAdapter;
import com.secondproject.leying.R;

import java.util.HashMap;
import java.util.List;


public class MenuListAdapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer
{
	private LayoutInflater mInflater;

		private List<String> pyStr;
		private Context mContext;
	    private int[] mSectionIndices;
	    private Character[] mSectionLetters;
	    private HashMap<String, List<City>> citygroup;

		public MenuListAdapter(HashMap<String, List<City>> citygroup, List<String> pyStr, Context mContext)
		{
			super();
			this.pyStr = pyStr;
			this.mContext = mContext;
			mInflater=LayoutInflater.from(mContext);
			this.citygroup=citygroup;
			if (citygroup!=null) {
				for (char i = 'A'; i <='Z' ; i++) {

					if (citygroup.containsKey(i+"")&&!(citygroup.get(i+"").isEmpty())) {
						for (int j = 0; j < citygroup.get(i + "").size(); j++) {
							Log.i("listadapter",i+"-----------"+ citygroup.get(i + "").get(j).getName());
						}
					}

				}
			}

		}




		@Override
		public int getCount() 
		{
			if (pyStr == null) 
			{
				return 0;
			} 
			else 
			{
				return this.pyStr.size();
			}
		}

		@Override
		public Object getItem(int position) 
		{
			if (pyStr == null) 
			{
				return null;
			} 
			else 
			{
				return this.pyStr.get(position);
			}
		}

		@Override
		public long getItemId(int position) 
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			ViewHolder holder = null;
			if (convertView == null) 
			{
				holder = new ViewHolder();
				convertView = LayoutInflater.from(this.mContext).inflate(R.layout.menu_list_item, null, false);
				holder.gridView = (GridView) convertView.findViewById(R.id.listview_item_gridview);
				convertView.setTag(holder);
			} 
			else 
			{
				holder = (ViewHolder) convertView.getTag();
			}

			if (this.pyStr != null) 
			{

				if (holder.gridView != null) 
				{
					MenuGridAdapter gridViewAdapter = new MenuGridAdapter(mContext,citygroup.get((pyStr.get(position))));
					holder.gridView.setAdapter(gridViewAdapter);
				}
			}
			return convertView;
		}
	@Override
	public int getPositionForSection(int section) {
		if (mSectionIndices.length == 0) {
			return 0;
		}

		if (section >= mSectionIndices.length) {
			section = mSectionIndices.length - 1;
		} else if (section < 0) {
			section = 0;
		}
		return mSectionIndices[section];
	}

	@Override
	public int getSectionForPosition(int position) {
		for (int i = 0; i < mSectionIndices.length; i++) {
			if (position < mSectionIndices[i]) {
				return i - 1;
			}
		}
		return mSectionIndices.length - 1;
	}

	@Override
	public Object[] getSections() {
		return mSectionLetters;
	}
	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder;

		if (convertView == null) {
			holder = new HeaderViewHolder();
			convertView = mInflater.inflate(R.layout.header, parent, false);
			holder.text = (TextView) convertView.findViewById(R.id.text1);
			convertView.setTag(holder);
		} else {
			holder = (HeaderViewHolder) convertView.getTag();
		}

		// set header text as first char in name
		CharSequence headerChar = pyStr.get(position);
		holder.text.setText(headerChar);

		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		return pyStr.get(position).subSequence(0, 1).charAt(0);
	}

	private class ViewHolder
		{

			GridView gridView;
		}

	class HeaderViewHolder {
		TextView text;
	}


}
