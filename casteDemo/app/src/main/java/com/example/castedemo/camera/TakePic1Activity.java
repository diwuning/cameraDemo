package com.example.castedemo.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.castedemo.R;
import com.example.castedemo.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 自定义拍照页面
* */
public class TakePic1Activity extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final String TAG = "TakePic1Activity";
    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    //返回
    @BindView(R.id.iv_back)
    ImageView ivBack;
    //拍照按钮
    @BindView(R.id.takepicture)
    Button takepicture;
    @BindView(R.id.buttonLayout)
    RelativeLayout buttonLayout;
    @BindView(R.id.rl_takePhoto)
    RelativeLayout rlTakePhoto;
    private Camera mCamera;
    Context mContext;
    /**
     * 相机拍照存储路径
     */
    public static String FilePath = Environment.getExternalStorageDirectory()
            + "/messageBoard/photoImgs/";
    private File picDir = new File(FilePath);//设为全局变量 ;
    private String filename = "";
    private boolean safeToTakePicture = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_pic);
        ButterKnife.bind(this);
        mContext = TakePic1Activity.this;
//        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(TakePic1Activity.this, new String[] {Manifest.permission.CAMERA},1);
//        }

        SurfaceHolder holder = surfaceView.getHolder();
        holder.setKeepScreenOn(true);// 屏幕常亮
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(this); //
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == 1) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "权限请求成功", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
//            }
//            return;
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            // 开启相机
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                mCamera = Camera.open(0);
                // i=0 表示后置相机
            } else {
                mCamera = Camera.open();
            }
        } catch (RuntimeException run) {
//            ToastMng_630.getInstance(TakePhotoActivity.this).showToast("摄像头异常");
////            Toast.makeText(TakePhotoActivity.this, "摄像头异常", Toast.LENGTH_SHORT).show();
//            finish();
        }
    }

    @OnClick({R.id.iv_back, R.id.takepicture})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.takepicture:
                if(mCamera != null){//if (!isCameraCanUse()) {
                    if (safeToTakePicture) {
                        takePhoto();//拍照完成
                        safeToTakePicture = false;
                    }

                } else {
                    Toast.makeText(mContext, "摄像头异常", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(TakePhotoActivity.this, "摄像头异常", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void takePhoto() {
        if (mCamera != null) {
            mCamera.takePicture(mShutter, null, mJpeg);
        }
    }

    /* 图像数据处理还未完成时的回调函数 */
    private Camera.ShutterCallback mShutter = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            // 一般显示进度条
        }
    };

    /* 图像数据处理完成后的回调函数 */
    private Camera.PictureCallback mJpeg = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // 保存图片
            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                //三星手机旋转图片
                bitmap = Utils.rotate(bitmap,-90);
                //保存到data/data目录自定义文件夹下
//                picDir = new File(FinalValues.FilePath);//设为全局变量

                if (!picDir.exists()) {
                    picDir.mkdirs();
                }
                //图片路径
                filename = FilePath + System.currentTimeMillis() + ".jpg";
                Log.i(TAG, "path---->" + filename);

                File file = new File(filename);
                FileOutputStream outStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outStream);
                outStream.flush();
                outStream.close();

                // 重新浏览
                // camera.stopPreview();
                // 释放相机
                camera.startPreview();
                //  camera.startPreview();
                safeToTakePicture = true;

                //跳转到截图界面
                Intent cropIntent = new Intent(mContext,CropImgActivity.class);
                cropIntent.putExtra("takepath",filename);
                startActivity(cropIntent);
                finish();
//                // 存储图像（PATH目录）
//                Uri source = Uri.fromFile(file);
//                //准备截图
//                try {
//                    cropImageView.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), source));
////            mCropImageView.rotateImage(90);
//                } catch (IOException e) {
//                    Log.e(TAG, e.getMessage());
//                }
//                showCropperLayout();

            } catch (IOException e) {
                e.printStackTrace();

            }

        }
    };

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // SurfaceView创建时，建立Camera和SurfaceView的联系
        if (mCamera != null) {
            Log.e(TAG, "surfaceCreated");
            try {
                holder.setKeepScreenOn(true);
                mCamera.setPreviewDisplay(holder);
//                mCamera.setDisplayOrientation(getPreviewDegree1(TakePic1Activity.this, 0));
                //预览面面旋转270度
                mCamera.setDisplayOrientation(270);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        initCamera(holder);//实现相机的参数初始化
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // SurfaceView销毁时，取消Camera预览
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    private void initCamera(SurfaceHolder holder) {
        // SurfaceView尺寸发生改变时（首次在屏幕上显示同样会调用此方法），初始化mCamera参数，启动Camera预览
        holder.setKeepScreenOn(true);
        Camera.Parameters parameters = mCamera.getParameters();// 获取mCamera的参数对象
        Camera.Size largestSize = getBestSupportedSize(parameters
                .getSupportedPreviewSizes());
        parameters.setPreviewSize(largestSize.width, largestSize.height);// 设置预览图片尺寸
        parameters.setPictureSize(largestSize.width, largestSize.height);
        mCamera.setParameters(parameters);
//        mCamera.setDisplayOrientation(90);
        try {
            mCamera.startPreview();
        } catch (Exception e) {
            if (mCamera != null) {
                mCamera.release();
                mCamera = null;
            }
        }
    }

    //获取最适合手机的大小
    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes) {
        // 取能适用的最大的SIZE
        Camera.Size largestSize = sizes.get(0);
        int largestArea = sizes.get(0).height * sizes.get(0).width;
        for (Camera.Size s : sizes) {
            int area = s.width * s.height;
            if (area > largestArea) {
                largestArea = area;
                largestSize = s;
            }
        }
        return largestSize;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放相机
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }
}
