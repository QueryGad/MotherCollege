package com.player.mothercollege.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 缓存工具类
 * @author wangs
 *
 */
public class CacheUtils {
    /**
     * 保存缓存
     * @param context
     * @param url : key
     * @param json : value
     */
    public static void saveCache(Context context, String url, String json) {
        SharedPreferences sp = context.getSharedPreferences("cache", context.MODE_PRIVATE);
        sp.edit().putString(url, json).commit();
    }
    /**
     * 获取缓存
     * @param context
     * @param url :key
     * @return
     */
    public static String getCache(Context context, String url) {
        SharedPreferences sp = context.getSharedPreferences("cache", context.MODE_PRIVATE);
        return sp.getString(url, "");
    }
    /**
     * 存储已读
     * @param context
     * @param newsId
     */
    public static void saveReadNewsId(Context context, int newsId) {
        SharedPreferences sp = context.getSharedPreferences("cache", context.MODE_PRIVATE);
        String value = sp.getString("readNewsId", "");
        if(TextUtils.isEmpty(value)){//第一次存储id
            value = String.valueOf(newsId);  // newsId + ""
        }else{
            value += "#"+newsId;
        }
        sp.edit().putString("readNewsId", value).commit();
        
    }
    public static List<Integer> getReadNewsId(Context context) {
        List<Integer> readNewsIdList = new ArrayList<Integer>();
        SharedPreferences sp = context.getSharedPreferences("cache", context.MODE_PRIVATE);
        String value = sp.getString("readNewsId", "");
        if(!TextUtils.isEmpty(value)){
            String[] split = value.split("#");
            for (int i = 0; i < split.length; i++) {
                readNewsIdList.add(Integer.parseInt(split[i]));
            }
        }
        return readNewsIdList;
    }
    
}
