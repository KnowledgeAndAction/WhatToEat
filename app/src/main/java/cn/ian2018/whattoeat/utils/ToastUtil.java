package cn.ian2018.whattoeat.utils;

import android.widget.Toast;

import cn.ian2018.whattoeat.MyApplication;

/**
 * Created by Administrator on 2016/12/25/025.
 * 吐司工具
 */

public class ToastUtil {

    public static void showShort(int msgId) {
        Toast.makeText(MyApplication.getContext(), msgId, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(int msgId) {
        Toast.makeText(MyApplication.getContext(), msgId, Toast.LENGTH_LONG).show();
    }

    public static void showShort(String msg) {
        Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg) {
        Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
