package atguigu.com.beijingnews.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者：李银庆 on 2017/6/2 14:42
 */
public class CacheUtils {

    public static void putBoolean(Context context, String key, boolean b) {
        SharedPreferences sp = context.getSharedPreferences("atguigu", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key,b).commit();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("atguigu", Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }
}
