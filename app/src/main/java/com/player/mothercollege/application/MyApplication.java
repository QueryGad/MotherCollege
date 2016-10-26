package com.player.mothercollege.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.lzy.ninegrid.NineGridView;
import com.player.mothercollege.R;
import com.squareup.picasso.Picasso;
import com.yolanda.nohttp.NoHttp;

/**
 * Created by Administrator on 2016/10/24.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);
        NineGridView.setImageLoader(new PicassoImageLoader());
    }

    /** Picasso 加载 */
    private class PicassoImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Picasso.with(context).load(url)//
                    .placeholder(R.drawable.ic_default_image)//
                    .error(R.drawable.ic_default_image)//
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }
}
