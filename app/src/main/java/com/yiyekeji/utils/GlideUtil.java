package com.yiyekeji.utils;

import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yiyekeji.IMApp;

/**
 * Created by lxl on 2016/11/09.
 */
public class GlideUtil {


    public static void setBitmapToView(String url, ImageView imageView) {

        if (TextUtils.isEmpty(url)||imageView==null) {
            return;
        } else {
            Glide.with(IMApp.getContext())
                    .load(Uri.parse(url))
                    .error(new ColorDrawable())
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(imageView);
        }
    }

}

