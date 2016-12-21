package com.player.mothercollege.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.widget.ImageView;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.lzy.ninegrid.NineGridView;
import com.player.mothercollege.R;
import com.squareup.picasso.Picasso;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yolanda.nohttp.NoHttp;

import cn.jpush.android.api.JPushInterface;

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

        final Context mContext = this;
        new Runnable(){
            @Override
            public void run() {
                NoHttp.initialize(MyApplication.this);
                NineGridView.setImageLoader(new PicassoImageLoader());
                UMShareAPI.get(mContext);
                Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
                initX5();
                initJPush();
                initEaseUI();
            }
        }.run();

    }

    private void initEaseUI() {
        EMOptions options = new EMOptions();
        //默认添加好友时不需要验证  改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        EaseUI.getInstance().init(this,options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private void initX5() {
        QbSdk.initX5Environment(this,null);
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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
