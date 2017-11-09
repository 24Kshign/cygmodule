package cn.share.jack.cygwidget.utils;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

/**
 * Created by jack on 2017/10/25
 *
 * @author jack
 */

public class CygImageLoader {

    private static RequestOptions getRequestOptions(int placeholderResId) {
        return getRequestOptions(placeholderResId, 0);
    }

    private static RequestOptions getRequestOptions(int placeholderResId, int errorResId) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholderResId != 0 ? placeholderResId : -1)
                .error(errorResId != 0 ? errorResId : (placeholderResId != 0 ? placeholderResId : -1))
                //设置优先级
                .priority(Priority.HIGH);
        //true表示Glide将不会把图片放到内存缓存中去（跳过内存缓存），但是只是会影响内存缓存！Glide 将会仍然利用磁盘缓存来避免重复的网络请求
//                .skipMemoryCache(true)
        //DiskCacheStrategy.NONE表示Glide将不会把图片保存在磁盘缓存中。然而，默认的它将仍然使用内存缓存
//                .diskCacheStrategy(DiskCacheStrategy.NONE);
        //如果不要缓存，需要同时设置上面两段代码
        return options;
    }


    //=======================================================imageUrl=============================================================
    public static void loadImage(String imageUrl, ImageView imageView) {
        Glide.with(imageView.getContext()).load(imageUrl).apply(getRequestOptions(0)).into(imageView);
    }

    public static void loadImage(String imageUrl, ImageView imageView, int placeholderResId) {
        Glide.with(imageView.getContext()).load(imageUrl).apply(getRequestOptions(placeholderResId)).into(imageView);
    }

    public static void loadImage(String imageUrl, ImageView imageView, int placeholderResId, int errorResId) {
        Glide.with(imageView.getContext()).load(imageUrl).apply(getRequestOptions(placeholderResId, errorResId)).into(imageView);
    }

    public static void loadGifImage(String imageUrl, ImageView imageView) {
        Glide.with(imageView.getContext()).asGif().load(imageUrl).apply(getRequestOptions(0)).into(imageView);
    }

    public static void loadGifImage(String imageUrl, ImageView imageView, int placeholderResId, int errorResId) {
        Glide.with(imageView.getContext()).asGif().load(imageUrl).apply(getRequestOptions(placeholderResId, errorResId)).into(imageView);
    }

    public static void loadGifImage(String imageUrl, ImageView imageView, int placeholderResId) {
        Glide.with(imageView.getContext()).asGif().load(imageUrl).apply(getRequestOptions(placeholderResId)).into(imageView);
    }


    //=======================================================resId================================================================
    public static void loadImage(@DrawableRes int resId, ImageView imageView) {
        Glide.with(imageView.getContext()).load(resId).apply(getRequestOptions(0)).into(imageView);
    }

    public static void loadImage(@DrawableRes int resId, ImageView imageView, int placeholderResId) {
        Glide.with(imageView.getContext()).load(resId).apply(getRequestOptions(placeholderResId)).into(imageView);
    }

    public static void loadImage(@DrawableRes int resId, ImageView imageView, int placeholderResId, int errorResId) {
        Glide.with(imageView.getContext()).load(resId).apply(getRequestOptions(placeholderResId, errorResId)).into(imageView);
    }



    //=======================================================imageFile=============================================================
    public static void loadImage(File imageFile, ImageView imageView) {
        Glide.with(imageView.getContext()).load(imageFile).apply(getRequestOptions(0)).into(imageView);
    }

    public static void loadImage(File imageFile, ImageView imageView, int placeholderResId) {
        Glide.with(imageView.getContext()).load(imageFile).apply(getRequestOptions(placeholderResId)).into(imageView);
    }

    public static void loadImage(File imageFile, ImageView imageView, int placeholderResId, int errorResId) {
        Glide.with(imageView.getContext()).load(imageFile).apply(getRequestOptions(placeholderResId, errorResId)).into(imageView);
    }



    //=======================================================byte[]================================================================
    public static void loadImage(byte[] imageByte, ImageView imageView) {
        Glide.with(imageView.getContext()).load(imageByte).apply(getRequestOptions(0)).into(imageView);
    }

    public static void loadImage(byte[] imageByte, ImageView imageView, int placeholderResId) {
        Glide.with(imageView.getContext()).load(imageByte).apply(getRequestOptions(placeholderResId)).into(imageView);
    }

    public static void loadImage(byte[] imageByte, ImageView imageView, int placeholderResId, int errorResId) {
        Glide.with(imageView.getContext()).load(imageByte).apply(getRequestOptions(placeholderResId, errorResId)).into(imageView);
    }

}