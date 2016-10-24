package com.player.mothercollege.utils;

import android.util.Log;

public class MyLog {
     private static boolean iskey = true;
     public static void testLog(String msg){
		if (iskey) {
			Log.e("我打的log", msg);
		}
    	 
     }
}
