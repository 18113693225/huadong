package com.demo.app.adapter;

import java.util.List;
import java.util.Map;

import com.demo.app.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterArrayAdapter extends ArrayAdapter<Object> {

	private Context context;
	private List<Object> items;
	private int resource;
	/**
	 * @param context
	 * @param resource
	 * @param objects
	 */
	public RegisterArrayAdapter(Context context, int resource, List<Object> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.items = objects;
		this.resource = resource;
	}

	/**
	 * @param context
	 * @param resource
	 * @param objects
	 */


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void refresh(List<Object> list) {
		items = list;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, resource, null);
			holder.icon = (ImageView) convertView.findViewById(R.id.register_icon);
			holder.text = (TextView) convertView.findViewById(R.id.register_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Map<String,Object> item = (Map<String,Object>)items.get(position);
		holder.icon.setImageResource(Integer.parseInt(item.get("icon").toString()));
		holder.text.setText(item.get("text").toString());
		return convertView;
	}

	static class ViewHolder {

		TextView text;

		ImageView icon;
	}
}
