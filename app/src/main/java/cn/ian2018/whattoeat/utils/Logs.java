package cn.ian2018.whattoeat.utils;

import android.util.Log;

/**
 * Created by Administrator on 2016/12/25/025.
 */

public class Logs {
    private static String TAG = "吃什么TAG：";
    private static int ISOPEN = 1;

    public static void d(String msg) {
        if (ISOPEN > 0) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (ISOPEN > 0) {
            Log.e(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (ISOPEN > 0) {
            Log.i(TAG, msg);
        }
    }

    public static void v(String msg) {
        if (ISOPEN > 0) {
            Log.v(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (ISOPEN > 0) {
            Log.w(TAG, msg);
        }
    }
}
