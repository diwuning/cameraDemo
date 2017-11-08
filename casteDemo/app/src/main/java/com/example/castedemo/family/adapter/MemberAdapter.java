package com.example.castedemo.family.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.castedemo.R;
import com.example.castedemo.camera.UserInfo1Activity;
import com.example.castedemo.view.CircleImageView;
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
    int location = -1;
    boolean[] checkFlag;
    private SparseBooleanArray spare;

    public MemberAdapter(Context mContext, List<UserInfo> userInfoList) {
        this.mContext = mContext;
        this.userInfoList = userInfoList;
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher_round)
                .build();
        checkFlag = new boolean[userInfoList.size()];
        spare=new SparseBooleanArray();
    }
    public void setDataChange(int position) {
//        for (int i=0;i<userInfoList.size();i++){
//            setItemCheck(i,false);
//        }
//        setItemCheck(position,true);
        this.location = position;
        notifyDataSetChanged();
        Log.e(TAG, "getView: "+spare.toString() );
//       this.location = position;
//        for(int i=0;i>userInfoList.size();i++){
//            checkFlag[i] = false;
//        }
//        checkFlag[position] = true;
//        Log.e(TAG, "setDataChange: "+location+"" );
//        notifyDataSetChanged();
//        setItemCheck(position,true);
//        notifyDataSetChanged();
       // Log.e(TAG,"1111111111="+spare.toString());
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.sys_avater_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }


        if(position == userInfoList.size()){
            holder.ivSystem.setImageResource(R.drawable.add);
            holder.iv_avaterBg.setVisibility(View.GONE);
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
          //  Log.e(TAG,"USERIMAG = "+userInfo.getUserImg());
            if(userInfo.getUserImg() == null){
                ImageLoader.getInstance().displayImage(userInfo.getUserImg(),holder.ivSystem,options);
            }else if(userInfo.getUserImg().contains("assets:")){
                ImageLoader.getInstance().displayImage(userInfo.getUserImg(),holder.ivSystem,options);
            }else {
                ImageLoader.getInstance().displayImage("file://"+userInfo.getUserImg(),holder.ivSystem,options);
            }
//            holder.ivSystem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    for (int i=0;i<userInfoList.size();i++){
//                        setItemCheck(i,false);
//                    }
//                    setItemCheck(position,true);
//                    if (isItemChecked(position)){
//                        holder.iv_avaterBg.setVisibility(View.VISIBLE);
//                        holder.tvSystemName.setTextColor(mContext.getResources().getColor(R.color.orange));
//                        holder.ivCheckFlag.setVisibility(View.VISIBLE);
//                    }
//                }
//            });
//            holder.iv_avaterBg.setVisibility(View.GONE);
//            holder.tvSystemName.setTextColor(mContext.getResources().getColor(R.color.text_white));
//            holder.ivCheckFlag.setVisibility(View.GONE);
//            Log.e(TAG, "getView: "+isItemChecked(position) );
            holder.tvSystemName.setText(userInfo.getUserName());
            if(location == position){
                holder.iv_avaterBg.setVisibility(View.VISIBLE);
                holder.tvSystemName.setTextColor(mContext.getResources().getColor(R.color.orange));
                holder.ivCheckFlag.setVisibility(View.VISIBLE);
            }else{
                holder.iv_avaterBg.setVisibility(View.GONE);
                holder.tvSystemName.setTextColor(mContext.getResources().getColor(R.color.text_white));
                holder.ivCheckFlag.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    public class ViewHolder {
        @BindView(R.id.iv_system)
        CircleImageView ivSystem;
        @BindView(R.id.tv_systemName)
        TextView tvSystemName;
        @BindView(R.id.iv_avaterBg)
        ImageView iv_avaterBg;
        @BindView(R.id.iv_checkFlag)
        ImageView ivCheckFlag;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    private void setItemCheck(int position,boolean isChecked){
        spare.put(position,isChecked);
    }
    private boolean isItemChecked(int position){
        return spare.get(position);
    }
}
