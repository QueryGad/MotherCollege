package com.player.mothercollege.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.lzy.ninegrid.NineGridView;
import com.player.mothercollege.R;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yolanda.nohttp.NoHttp;

/**
 * Created by Administrator on 2016/10/24.
 */
public class MyApplication extends Application{

    {
        PlatformConfig.setWeixin("wx5d8221afabe4cb19", "8a4ba69af30f67e7ee534a49b3bb16b1");
        PlatformConfig.setSinaWeibo("2287258691", "911565c9f78aa17ca533bf396075bea3");
        PlatformConfig.setQQZone("1105742863", "NxmA4qBbYB6diJPW");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);
        NineGridView.setImageLoader(new PicassoImageLoader());
        UMShareAPI.get(this);
        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
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
