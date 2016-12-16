package com.demo.app.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LXG on 2016/12/13.
 */

public class PreferenceUtils {
    public static final String APP_ID = "com.demo.app";
    public static final String KEY_BTID = "Id";


    /**
     * 保存Id
     */
    public static void cacheBtId(Context context, int id) {
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putInt(KEY_BTID, id);
        e.apply();
    }

    /**
     * 获取Id
     */
    public static int getBtId(Context context) {
        if (context == null) {
            return -1;
        }
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
                .getInt(KEY_BTID, -1);
    }
}
