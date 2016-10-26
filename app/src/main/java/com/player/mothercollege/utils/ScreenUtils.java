package com.player.mothercollege.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/9/26.
 */
public class ScreenUtils {

    /**
     * 宽
     */
    public static int getWidth(Context context){

        WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    };

    /**
     * 高
     */
    public static int getHeigth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
}
