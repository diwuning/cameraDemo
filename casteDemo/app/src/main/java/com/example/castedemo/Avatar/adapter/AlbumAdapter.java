package com.example.castedemo.Avatar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.castedemo.Avatar.bean.MediaBean;
import com.example.castedemo.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/24 0024.
 * 显示本地相册的图片
 */

public class AlbumAdapter extends BaseAdapter {
    private Context mContext;
    private List<MediaBean> mediaBeen;
//    HashMap<String,List<MediaBean>> allPhotosTemp;

    public AlbumAdapter(Context mContext, List<MediaBean> mediaBeen) {
        this.mContext = mContext;
        this.mediaBeen = mediaBeen;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.album_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        MediaBean bean = mediaBeen.get(position);
        ImageLoader.getInstance().displayImage("file://"+bean.getPath(),holder.ivAlbum);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_album)
        ImageView ivAlbum;
        @BindView(R.id.iv_isCheck)
        ImageView ivIsCheck;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
