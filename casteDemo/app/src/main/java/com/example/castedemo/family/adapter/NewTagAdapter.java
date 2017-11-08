package com.example.castedemo.family.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.castedemo.R;
import com.example.sugardemo.UserTag;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangchm on 2017/10/26 0026.
 * 偏好标签页
 */

public class NewTagAdapter extends BaseAdapter {
    private static final String TAG = "TagAdapter";
    private Context mContext;
    private List<UserTag> tagBeen;

    public NewTagAdapter(Context mContext, List<UserTag> tagBeen) {
        this.mContext = mContext;
        this.tagBeen = tagBeen;
    }

    @Override
    public int getCount() {
        return tagBeen == null ? 0 : tagBeen.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tag_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        UserTag bean = tagBeen.get(position);
        if(bean.isSel()){
            holder.ivCheck.setVisibility(View.VISIBLE);
        }else{
            holder.ivCheck.setVisibility(View.GONE);
        }
        holder.tvTag.setText(bean.getTag());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.iv_tagCheck)
        ImageView ivCheck;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
