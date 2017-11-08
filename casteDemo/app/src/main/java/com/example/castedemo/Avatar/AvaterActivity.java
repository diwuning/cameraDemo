package com.example.castedemo.Avatar;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.castedemo.Avatar.adapter.AlbumAdapter;
import com.example.castedemo.Avatar.adapter.SystemAvaterAdapter;
import com.example.castedemo.Avatar.bean.MediaBean;
import com.example.castedemo.R;
import com.example.castedemo.camera.CropImgActivity;
import com.example.castedemo.camera.TakePic1Activity;
import com.example.castedemo.camera.UserInfo1Activity;
import com.example.castedemo.family.FamilyListActivity;
import com.example.sugardemo.UserInfoDao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AvaterActivity extends Activity {
    private static final String TAG = "AvaterActivity";

    @BindView(R.id.iv_takeAvater)
    ImageView ivTakeAvater;
    @BindView(R.id.rl_takePhoto)
    RelativeLayout rlTakePhoto;
    @BindView(R.id.gv_default)
    GridView gvDefault;
    @BindView(R.id.gv_album)
    GridView gvAlbum;
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;
    @BindView(R.id.rl_cancel)
    RelativeLayout rlCancel;
    @BindView(R.id.iv_confirm)
    ImageView ivConfirm;
    @BindView(R.id.rl_confirm)
    RelativeLayout rlConfirm;
    AlbumAdapter albumAdapter;
    Context mContext;
    UserInfoDao userInfoDao;
    String userId = "";
    String status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avater);
        ButterKnife.bind(this);
        mContext = AvaterActivity.this;
        userInfoDao = new UserInfoDao();
        if(getIntent().getStringExtra("status") != null && !getIntent().getStringExtra("status").equals("")){
            status = getIntent().getStringExtra("status");
        }

        initAlbum();
    }

    public void initAlbum(){
        //获取本地相册图片
        getAllPhotoInfo();
        //获取系统头像
        getSystemAvater();
    }

    List<MediaBean> mediaBeen = new ArrayList<>();
    /**
     * 读取手机中所有图片信息
     */
    private void getAllPhotoInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                List<MediaBean> mediaBeen = new ArrayList<>();
                HashMap<String,List<MediaBean>> allPhotosTemp = new HashMap<>();//所有照片
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                String[] projImage = { MediaStore.Images.Media._ID
                        , MediaStore.Images.Media.DATA
                        ,MediaStore.Images.Media.SIZE
                        ,MediaStore.Images.Media.DISPLAY_NAME};
                final Cursor mCursor = getContentResolver().query(mImageUri,
                        projImage,
                        MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED+" desc");

                if(mCursor!=null){
                    while (mCursor.moveToNext()) {
                        // 获取图片的路径
                        String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        int size = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE))/1024;
                        String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                        //用于展示相册初始化界面
                        mediaBeen.add(new MediaBean(path,size,displayName));
                        // 获取该图片的父路径名
                        String dirPath = new File(path).getParentFile().getAbsolutePath();
                        //存储对应关系
                        if (allPhotosTemp.containsKey(dirPath)) {
                            List<MediaBean> data = allPhotosTemp.get(dirPath);
                            data.add(new MediaBean(path,size,displayName));
//                            Log.e(TAG,"getAllPhotoInfo  "+data.size()+",path="+data.get(0).getPath()+",name="+data.get(0).getDisplayName());
                            continue;
                        } else {
                            List<MediaBean> data = new ArrayList<>();
                            data.add(new MediaBean(path,size,displayName));
                            allPhotosTemp.put(dirPath,data);
//                            Log.e(TAG,"getAllPhotoInfo  else "+data.size()+",path="+data.get(0).getPath()+",name="+data.get(0).getDisplayName());
                        }
                    }
                    mCursor.close();
                }
                //更新界面
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //...
                        Log.e(TAG,"mediaBeen="+mediaBeen.size());
                        albumAdapter = new AlbumAdapter(AvaterActivity.this,mediaBeen);
                        gvAlbum.setAdapter(albumAdapter);
                        gvAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent cropIntent = new Intent(mContext, CropImgActivity.class);
                                cropIntent.putExtra("status",status);
                                cropIntent.putExtra("takepath",mediaBeen.get(position).getPath());
                                startActivity(cropIntent);
                            }
                        });
                    }
                });
            }
        }).start();
    }

    /*
    * 获取系统头像
    * */
    public void getSystemAvater(){
//        String[] systemAvater = new String[0];
        List<MediaBean> picBean = new ArrayList<MediaBean>();
        try {
            final String[] systemAvater = getResources().getAssets().list("pictures");
            for(int i=0;i<systemAvater.length;i++){
                systemAvater[i] = "assets://pictures/"+systemAvater[i];
                Log.e(TAG,"system="+systemAvater[i]);
                MediaBean bean = new MediaBean();
                bean.setPath(systemAvater[i]);
                picBean.add(bean);
            }
            SystemAvaterAdapter adapter = new SystemAvaterAdapter(AvaterActivity.this,picBean);
            int size = systemAvater.length;
            gvDefault.setNumColumns(size);
            int width = 127;
            int gridWidth = size*(width+38);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridWidth, ViewGroup.LayoutParams.FILL_PARENT);
            gvDefault.setLayoutParams(params);
            gvDefault.setColumnWidth(127);
            gvDefault.setHorizontalSpacing(38);
            gvDefault.setAdapter(adapter);

            gvDefault.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(status.equals("add")){
                        Intent userIntent = new Intent(mContext, UserInfo1Activity.class);
                        userIntent.putExtra("path",systemAvater[position]);
                        userIntent.putExtra("from","system");
                        userIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(userIntent);
                    }else{
                        Intent userIntent = new Intent(mContext, FamilyListActivity.class);
                        userIntent.putExtra("path",systemAvater[position]);
                        userIntent.putExtra("from","system");
                        userIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(userIntent);
                    }
                    finish();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.rl_takePhoto, R.id.rl_cancel, R.id.rl_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_takePhoto:
                Intent takeIntent = new Intent(AvaterActivity.this, TakePic1Activity.class);
                takeIntent.putExtra("status",status);
                startActivity(takeIntent);
                break;
            case R.id.rl_cancel:
                finish();
                break;
            case R.id.rl_confirm:
                break;
        }
    }
}
