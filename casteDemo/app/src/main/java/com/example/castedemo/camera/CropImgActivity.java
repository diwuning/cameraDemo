package com.example.castedemo.camera;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.castedemo.R;
import com.example.castedemo.cropper.view.CropImageView;
import com.example.castedemo.cropper.view.CropperImage;
import com.example.castedemo.family.FamilyListActivity;
import com.example.castedemo.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
* 自定义截图页面
* */

public class CropImgActivity extends Activity {
    private static final String TAG = "CropImgActivity";

    @BindView(R.id.CropImageView)
    CropImageView cropImageView;
    @BindView(R.id.cropper_layout)
    LinearLayout cropperLayout;
    String imgPath="";
    /**
     * 相机拍照存储路径
     */
    public static String FilePath = Environment.getExternalStorageDirectory()
            + "/messageBoard/photoImgs/";
    Context mContext;
    String status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_img);
        ButterKnife.bind(this);
        mContext = CropImgActivity.this;
        if(!getIntent().getStringExtra("takepath").equals("")){
            imgPath = getIntent().getStringExtra("takepath");
        }

        if(!getIntent().getStringExtra("status").equals("")){
            status = getIntent().getStringExtra("status");
        }

        File file = new File(imgPath);
        // 存储图像（PATH目录）
        Uri source = Uri.fromFile(file);
        //准备截图
        try {
            cropImageView.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), source));
//            mCropImageView.rotateImage(90);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 开始截图，并保存图片
     * @param view
     */
    public void startCropper(View view) {
        //获取截图并旋转90度
        CropperImage cropperImage = cropImageView.getCroppedImage();
        Log.e(TAG, cropperImage.getX() + "," + cropperImage.getY());
        Log.e(TAG, cropperImage.getWidth() + "," + cropperImage.getHeight());
        //进入截图界面时已经旋转过了。这里不用旋转
        Bitmap bitmap = Utils.rotate(cropperImage.getBitmap(), 0);
//        Bitmap bitmap = mCropImageView.getCroppedImage();
        // 系统时间
        long dateTaken = System.currentTimeMillis();
        // 图像名称
        String filename = DateFormat.format("yyyy-MM-dd kk.mm.ss", dateTaken)
                .toString() + ".jpg";
        Uri uri = insertImage(getContentResolver(), filename, dateTaken, FilePath,
                filename, bitmap, null);
        cropperImage.getBitmap().recycle();
        cropperImage.setBitmap(null);


        Log.e(TAG,"status="+status);
        if(status.equals("add")){
            Intent userIntent = new Intent(mContext, UserInfo1Activity.class);
            userIntent.setData(uri);
            userIntent.putExtra("from","crop");
            userIntent.putExtra("path", FilePath + filename);
            Log.e(TAG,"path  1="+FilePath+filename);
            userIntent.putExtra("width", bitmap.getWidth());
            userIntent.putExtra("height", bitmap.getHeight());
            userIntent.putExtra("cropperImage", cropperImage);
            //要启动的activity已经在当前的任务中，那么在该activity之上的activity都会关闭，并且intent会传递给在栈顶的activity

            //如果 Activity 已经是运行在 Task 的 top，则该 Activity 将不会再被启动
            userIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(userIntent);
        }else if(status.equals("update")){
            Intent userIntent = new Intent(mContext, FamilyListActivity.class);
            userIntent.setData(uri);
            userIntent.putExtra("from","crop");
            userIntent.putExtra("path", FilePath + filename);
            Log.e(TAG,"path  1="+FilePath+filename);
            userIntent.putExtra("width", bitmap.getWidth());
            userIntent.putExtra("height", bitmap.getHeight());
            userIntent.putExtra("cropperImage", cropperImage);
            //要启动的activity已经在当前的任务中，那么在该activity之上的activity都会关闭，并且intent会传递给在栈顶的activity

            //如果 Activity 已经是运行在 Task 的 top，则该 Activity 将不会再被启动
            userIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(userIntent);
        }

//        Intent userIntent = new Intent();

//        setResult(RESULT_OK,userIntent);
        bitmap.recycle();
        finish();
//        super.overridePendingTransition(R.anim.fade_in,
//                R.anim.fade_out);
//        doAnimation(cropperImage);
    }

    public static final Uri IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    /**
     * 存储图像并将信息添加入媒体数据库
     */
    private Uri insertImage(ContentResolver cr, String name, long dateTaken,
                            String directory, String filename, Bitmap source, byte[] jpegData) {
        OutputStream outputStream = null;
        String filePath = directory + filename;
        try {
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(directory, filename);
            if (file.createNewFile()) {
                outputStream = new FileOutputStream(file);
                if (source != null) {
                    source.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                } else {
                    outputStream.write(jpegData);
                }
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Throwable t) {
                }
            }
        }
        ContentValues values = new ContentValues(7);
        values.put(MediaStore.Images.Media.TITLE, name);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
        values.put(MediaStore.Images.Media.DATE_TAKEN, dateTaken);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.DATA, filePath);
        return cr.insert(IMAGE_URI, values);
    }

    //关闭截图界面
    public void closeCropper(View view){
        finish();
    }
}
