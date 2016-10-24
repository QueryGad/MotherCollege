package com.player.mothercollege.application;

import android.app.Application;

import com.yolanda.nohttp.NoHttp;

/**
 * Created by Administrator on 2016/10/24.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);
    }
}
