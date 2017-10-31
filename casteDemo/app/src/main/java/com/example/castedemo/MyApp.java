package com.example.castedemo;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.orm.SugarContext;

/**
 * Created by Administrator on 2017/10/21 0021.
 */

public class MyApp extends Application {
    private static Context mContext;
    public static MyApp instance;

    public static MyApp getInstance(){
        if(null == instance){
            instance = new MyApp();
        }
        return instance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        mContext = getApplicationContext();
//        getDaoMaster();
        initImageLoader(mContext);
        SugarContext.init(getApplicationContext());
    }

    public void onTerminate() {
        SugarContext.terminate();
        super.onTerminate();
    }

    /**
     * 初始化ImageLoader
     *
     * @param context
     * @date 2016-3-16 上午11:10:28
     * @fileTag 方法说明：
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(2 * 1024 * 1024)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
