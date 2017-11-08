package com.example.castedemo.Avatar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.castedemo.Avatar.bean.MediaBean;
import com.example.castedemo.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/25 0025.
 * 系统头像
 */

public class SystemAvaterAdapter extends BaseAdapter {
    private Context mContext;
    private String[] systemAvaters;
    private List<MediaBean> mediaBeen;
    int location = -1;

    public SystemAvaterAdapter(Context mContext, List<MediaBean> mediaBeen) {
        this.mContext = mContext;
        this.mediaBeen = mediaBeen;
    }

    public void setData(int position){
        this.location = position;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mediaBeen == null ? 0 : mediaBeen.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.sys_avater_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        MediaBean bean = mediaBeen.get(position);
//        String path = systemAvaters[position];
        ImageLoader.getInstance().displayImage(bean.getPath(),holder.ivSystem);
        if(bean.getDisplayName()!= null && !bean.getDisplayName().equals("")){
            holder.tvSystemName.setText(bean.getDisplayName());
            holder.tvSystemName.setVisibility(View.VISIBLE);
        }else{
            holder.tvSystemName.setVisibility(View.GONE);
        }

        if(location == position){
            holder.ivAvaterBg.setVisibility(View.VISIBLE);
            holder.tvSystemName.setTextColor(mContext.getResources().getColor(R.color.orange));
        }else{
            holder.ivAvaterBg.setVisibility(View.GONE);
            holder.tvSystemName.setTextColor(mContext.getResources().getColor(R.color.text_white));
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_system)
        ImageView ivSystem;
        @BindView(R.id.tv_systemName)
        TextView tvSystemName;
        @BindView(R.id.iv_avaterBg)
        ImageView ivAvaterBg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
