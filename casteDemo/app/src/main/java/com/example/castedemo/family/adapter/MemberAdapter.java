package com.example.castedemo.family.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.castedemo.R;
import com.example.castedemo.camera.UserInfo1Activity;
import com.example.sugardemo.UserInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangchm on 2017/10/26 0026.
 * 已添加的家庭成员列表
 */

public class MemberAdapter extends BaseAdapter {
    private static final String TAG = "MemberAdapter";
    private Context mContext;
    private List<UserInfo> userInfoList;
    DisplayImageOptions options;

    public MemberAdapter(Context mContext, List<UserInfo> userInfoList) {
        this.mContext = mContext;
        this.userInfoList = userInfoList;
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher_round)
                .build();
    }

    @Override
    public int getCount() {
        return userInfoList == null ? 0 : userInfoList.size()+1;
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
        if(position == userInfoList.size()){
            holder.ivSystem.setImageResource(R.drawable.gallery_add);
            holder.tvSystemName.setText("添加");
            holder.ivSystem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent addIntent = new Intent(mContext, UserInfo1Activity.class);
                    mContext.startActivity(addIntent);
                }
            });
        }else{
            UserInfo userInfo = userInfoList.get(position);
            Log.e(TAG,"USERIMAG = "+userInfo.getUserImg());
            if(userInfo.getUserImg() == null){
                ImageLoader.getInstance().displayImage(userInfo.getUserImg(),holder.ivSystem,options);
            }else if(userInfo.getUserImg().contains("assets:")){
                ImageLoader.getInstance().displayImage(userInfo.getUserImg(),holder.ivSystem,options);
            }else {
                ImageLoader.getInstance().displayImage("file://"+userInfo.getUserImg(),holder.ivSystem,options);
            }

            holder.tvSystemName.setText(userInfo.getUserName());
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_system)
        ImageView ivSystem;
        @BindView(R.id.tv_systemName)
        TextView tvSystemName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
