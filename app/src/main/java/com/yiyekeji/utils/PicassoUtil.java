package com.yiyekeji.utils;

import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yiyekeji.IMApp;

/**
 * Created by lxl on 2016/11/09.
 */
public class PicassoUtil {


    public static void setBitmapToView(String url, ImageView imageView) {

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if (TextUtils.isEmpty(url)) {
        } else {
            //听说用uri比较快
            Picasso.with(IMApp.getContext())
                    .load(Uri.parse(url))
                    .placeholder(new ColorDrawable())
                    .error(new ColorDrawable())
                    .into(imageView);
        }
    }

}

