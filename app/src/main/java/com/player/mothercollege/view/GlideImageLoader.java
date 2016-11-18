package com.player.mothercollege.view;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2016/11/18.
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        String load = (String)path;
        Glide.with(context).load(load).into(imageView);
    }
}
