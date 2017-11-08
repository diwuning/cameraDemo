package com.example.castedemo.family.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.castedemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangchm on 2017/10/26 0026.
 */

public class TagAdapter extends BaseAdapter {
    private static final String TAG = "TagAdapter";
    private Context mContext;
    private String[] tags;

    public TagAdapter(Context mContext, String[] tags) {
        this.mContext = mContext;
        this.tags = tags;
    }

    @Override
    public int getCount() {
        return tags == null ? 0 : tags.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tag_sel_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        String tag = tags[position];
        holder.tvTag.setText(tag);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_tag)
        TextView tvTag;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
