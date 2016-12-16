package com.demo.app.adapter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.demo.app.R;
import com.demo.app.util.Constents;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NoficeInfoAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> menus;

	public NoficeInfoAdapter(Context context,List<Map<String, Object>> menus){
		this.context = context;
		this.menus = menus;
	}
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
			convertView = View.inflate(context, R.layout.notice_info_tab_item_layout, null);
			holder = new ViewHolder();
			holder.title = (TextView)convertView.findViewById(R.id.info_item_title);
			holder.shortInfo = (TextView)convertView.findViewById(R.id.info_item_short);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String id = menus.get(position).get("id").toString();
		SharedPreferences sp = context.getSharedPreferences(Constents.SHARE_CONFIG, Context.MODE_PRIVATE);
		Set<String>values = sp.getStringSet("infoClick", new HashSet<String>());
		if(values.contains(id)){
			holder.title.setTextColor(Color.GRAY);
			holder.shortInfo.setTextColor(Color.GRAY);
		}else{
			holder.title.setTextColor(Color.BLACK);
			holder.shortInfo.setTextColor(Color.BLACK);
		}			
		holder.title.setText(menus.get(position).get("title").toString());
		holder.shortInfo.setText(menus.get(position).get("short").toString());
		return convertView;
	}

	static class ViewHolder {

		TextView title;
		TextView shortInfo;
	}
}
