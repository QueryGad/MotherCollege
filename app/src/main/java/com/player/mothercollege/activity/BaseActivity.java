package com.player.mothercollege.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by Administrator on 2016/10/24.
 */
public abstract class BaseActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView();
        initViews();
        initListeners();
        initData();
    }
    public abstract void setContentView();
    public abstract void initViews();
    public abstract void initListeners();
    public abstract void initData();


}
