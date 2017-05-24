package com.demo.app.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.demo.app.R;
import com.demo.app.util.Constents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XSErrAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Map<Object, Object>> datas;
    private int btId;


    public XSErrAdapter(Context context, ArrayList<Map<Object, Object>> datas, int btId) {
        this.context = context;
        this.datas = datas;
        this.btId = btId;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return datas == null ? 0 : datas.size();


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
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final XSErrAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.custome_mutil_choice_item, null);
            holder = new XSErrAdapter.ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.content_text);
            holder.box = (CheckBox) convertView.findViewById(R.id.content_radio);
            convertView.setTag(holder);
        } else {
            holder = (XSErrAdapter.ViewHolder) convertView.getTag();
        }
        holder.text.setText(datas.get(position).get("text").toString());
        Constents.errListMap.put(btId + "", datas);
        if (datas.get(position).get("select_val").toString().equals("1")) {
            holder.box.setChecked(true);
            holder.text.setBackgroundColor(Color.WHITE);
        } else {
            holder.box.setChecked(false);
            holder.text.setBackgroundColor(Color.WHITE);
        }
        holder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    datas.get(position).put("select_val", 1);
                    holder.text.setBackgroundColor(Color.WHITE);
                } else {
                    datas.get(position).put("select_val", 0);
                    holder.text.setBackgroundColor(Color.WHITE);
                }
                if (Constents.errListMap.containsKey(btId + "")) {
                    Constents.errListMap.remove(btId + "");
                    Constents.errListMap.put(btId + "", datas);
                } else {
                    Constents.errListMap.put(btId + "", datas);
                }
                // Constents.xserrorcontentList = datas;
            }
        });
        return convertView;
    }

    static final class ViewHolder {

        TextView text;

        CheckBox box;
    }
}
