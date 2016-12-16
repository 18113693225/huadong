package com.demo.app.adapter;

import java.util.List;
import java.util.Map;

import com.demo.app.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class XSContentShowAdapter extends BaseAdapter {

    private Context context;
    private List<Map<Object, Object>> datas;

    public XSContentShowAdapter(Context context, List<Map<Object, Object>> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public void refresh(List<Map<Object, Object>> list) {
        datas = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.custome_mutil_choice_item, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.content_text);
            holder.box = (CheckBox) convertView.findViewById(R.id.content_radio);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text.setText(datas.get(position).get("text1").toString());
        String selected = datas.get(position).get("select_val").toString();
//		holder.box.setClickable(false);
        if (selected.equals("1")) {
            holder.box.setChecked(true);
            holder.text.setBackgroundColor(Color.WHITE);
        } else {
            holder.box.setChecked(false);
            holder.text.setBackgroundColor(Color.WHITE);
        }
        holder.box.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    datas.get(position).put("select_val", 1);
                } else {
                    datas.get(position).put("select_val", 0);
                }
//				Constents.xscontent = datas;
//				System.out.println(Constents.xscontent);
            }
        });
        return convertView;
    }

    static class ViewHolder {

        TextView text;

        CheckBox box;
    }
}
