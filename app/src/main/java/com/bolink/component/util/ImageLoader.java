package com.bolink.component.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * @className: InterfaceImageLoader
 * @classDescription: 图片加载库封装（不依赖单一第三方库，用作解耦）
 * @author: leibing
 * @createTime: 2016/8/15
 */
public class ImageLoader {
    // sington
    private static ImageLoader instance = null;
    private ImageLoader(){
    }
    
    /**
     * sington
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param
     * @return
     */
    public static ImageLoader getInstance(){
        if (instance == null)
            instance = new ImageLoader();
        return instance;
    }

    /**
     * 图片加载
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param context 上下文
     * @param imageView 图片显示控件
     * @param remoteUrl 远程图片链接
     * @return
     */
    public void load(Context context, ImageView imageView, String remoteUrl){
        load(context, imageView, remoteUrl, null, null,false);
    }

    /**
     * 图片加载
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param context 上下文
     * @param imageView 图片显示控件
     * @param localFile 本地图片
     * @return
     */
    public void load(Context context, ImageView imageView, File localFile){
        load(context, imageView, localFile, null, null,false);
    }

    /**
     * 图片加载
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param context 上下文
     * @param imageView 图片显示控件
     * @param remoteUrl 远程图片链接
     * @param isCropCircle 是否圆角
     * @return
     */
    public void load(Context context, ImageView imageView, String remoteUrl, boolean isCropCircle){
        load(context, imageView, remoteUrl, null, null,isCropCircle);
    }

    /**
     * 图片加载
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param context 上下文
     * @param imageView 图片显示控件
     * @param localFile 本地图片
     * @param isCropCircle 是否圆角
     * @return
     */
    public void load(Context context, ImageView imageView, File localFile, boolean isCropCircle){
        load(context, imageView, localFile, null, null,isCropCircle);
    }

    /**
     * 图片加载
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param context 上下文
     * @param imageView 图片显示控件
     * @param localFile 本地图片
     * @param defaultImage 默认占位图片
     * @return
     */
    public void load(Context context, ImageView imageView, File localFile, Drawable defaultImage){
        load(context, imageView, localFile, defaultImage, null, false);
    }

    /**
     * 图片加载
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param context 上下文
     * @param imageView 图片显示控件
     * @param url 图片链接
     * @param defaultImage 默认占位图片
     * @return
     */
    public void load(Context context, ImageView imageView, String url, Drawable defaultImage){
        load(context, imageView, url, defaultImage, null, false);
    }

    /**
     * 图片加载
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param context 上下文
     * @param imageView 图片显示控件
     * @param url 图片链接
     * @param defaultImage 默认占位图片
     * @param errorImage 加载失败后图片
     * @param  isCropCircle 是否圆角
     * @return
     */
    public void load(Context context, ImageView imageView, String url, Drawable defaultImage,
                     Drawable errorImage , boolean isCropCircle){
                // 图片加载库采用Glide框架
                DrawableTypeRequest request = Glide.with(context).load(url);
                // 设置scaleType
                request.centerCrop();
                // 圆角裁切
                if (isCropCircle)
                    request.bitmapTransform(new CropCircleTransformation(context));
                request.thumbnail(0.1f) //用原图的1/10作为缩略图
                .placeholder(defaultImage) //设置资源加载过程中的占位Drawable
                .crossFade() //设置加载渐变动画
                .priority(Priority.NORMAL) //指定加载的优先级，优先级越高越优先加载，
                // 但不保证所有图片都按序加载
                // 枚举Priority.IMMEDIATE，Priority.HIGH，Priority.NORMAL，Priority.LOW
                // 默认为Priority.NORMAL
                .fallback(null) //设置model为空时要显示的Drawable
                // 如果没设置fallback，model为空时将显示error的Drawable，
                // 如果error的Drawable也没设置，就显示placeholder的Drawable
                .error(errorImage) //设置load失败时显示的Drawable
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model,
                                               Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource,
                                                   String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        return false;
                    }
                }) //请求监听
                .skipMemoryCache(true) //设置跳过内存缓存，但不保证一定不被缓存
                // （比如请求已经在加载资源且没设置跳过内存缓存，这个资源就会被缓存在内存中）
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略DiskCacheStrategy.SOURCE：
                // 缓存原始数据，DiskCacheStrategy.RESULT：
                // 缓存变换(如缩放、裁剪等)后的资源数据，
                // DiskCacheStrategy.NONE：什么都不缓存，
                // DiskCacheStrategy.ALL：缓存SOURC和RESULT。
                // 默认采用DiskCacheStrategy.RESULT策略，
                // 对于download only操作要使用DiskCacheStrategy.SOURCE
                .into(imageView);
    }

    /**
     * 图片加载
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param context 上下文
     * @param imageView 图片显示控件
     * @param localPath 图片本地链接
     * @param defaultImage 默认占位图片
     * @param errorImage 加载失败后图片
     * @param  isCropCircle 是否圆角
     * @return
     */
    public void load(Context context, ImageView imageView, File localPath, Drawable defaultImage,
                     Drawable errorImage , boolean isCropCircle){
        if (!localPath.exists()){
            Toast.makeText(context, "not local file  resources,please checkout it!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // 图片加载库采用Glide框架
        DrawableTypeRequest request = Glide.with(context).load(localPath);
        // 设置scaleType
        request.centerCrop();
        // 圆角裁切
        if (isCropCircle)
            request.bitmapTransform(new CropCircleTransformation(context));
        request.thumbnail(0.1f) //用原图的1/10作为缩略图
                .placeholder(defaultImage) //设置资源加载过程中的占位Drawable
                .crossFade() //设置加载渐变动画
                .priority(Priority.NORMAL) //指定加载的优先级，优先级越高越优先加载，
                // 但不保证所有图片都按序加载
                // 枚举Priority.IMMEDIATE，Priority.HIGH，Priority.NORMAL，Priority.LOW
                // 默认为Priority.NORMAL
                .fallback(null) //设置model为空时要显示的Drawable
                // 如果没设置fallback，model为空时将显示error的Drawable，
                // 如果error的Drawable也没设置，就显示placeholder的Drawable
                .error(errorImage) //设置load失败时显示的Drawable
                .skipMemoryCache(true) //设置跳过内存缓存，但不保证一定不被缓存
                // （比如请求已经在加载资源且没设置跳过内存缓存，这个资源就会被缓存在内存中）
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略DiskCacheStrategy.SOURCE：
                // 缓存原始数据，DiskCacheStrategy.RESULT：
                // 缓存变换(如缩放、裁剪等)后的资源数据，
                // DiskCacheStrategy.NONE：什么都不缓存，
                // DiskCacheStrategy.ALL：缓存SOURC和RESULT。
                // 默认采用DiskCacheStrategy.RESULT策略，
                // 对于download only操作要使用DiskCacheStrategy.SOURCE
                .into(imageView);
    }

    /**
     * 清除内存缓存
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param context
     * @return
     */
    public void clearMemory(Context context){
        // 图片加载库采用Glide框架
        // 必须在UI线程中调用
        Glide.get(context).clearMemory();

    }

    /**
     * 清除磁盘缓存
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param context
     * @return
     */
    public void clearDiskCache(final Context context){
        // 图片加载库采用Glide框架
        // 必须在后台线程中调用，建议同时clearMemory()
        ThreadManager.getInstance().getNewCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        });
    }

    /**
     * 清除view缓存
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param view
     * @return
     */
    public void clearViewCache(View view){
        // 图片加载库采用Glide框架
        Glide.clear(view);
    }

    /**
     * 获取SD卡下图片路径
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param fullPath SD下图片完整路径
     * @return
     */
    public static String getSDSource(String fullPath){
        return "file://"+ fullPath;
    }

    /**
     * 获取ASSETS下图片路径
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param fileName 图片名称
     * @return
     */
    public static String getAssetsSource(String fileName){
        return "file:///android_asset/"+fileName;
    }

    /**
     * 获取Raw下视频可以解析一张图片路径
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param context 上下文
     * @param  rawRid 视频id
     * @return
     */
    public static String getRawSource(Context context, int rawRid){
        return "android.resource://"+context.getPackageName()+"/raw/"+rawRid;
    }

    /**
     *
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param context 上下文
     * @param drawRid drawable目录下图片id
     * @return
     */
    public static String getDrawableSource(Context context, int drawRid){
        return "android.resource://"+context.getPackageName()+"/drawable/"+drawRid;
    }
}
