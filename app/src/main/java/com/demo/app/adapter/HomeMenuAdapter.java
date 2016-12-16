package com.demo.app.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeMenuAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> menus;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return menus.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return menus.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void refresh(List<Map<String, Object>> list) {
		menus = list;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "11", Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}

	static class ViewHolder {

		TextView text;

		ImageView icon;
	}
}
