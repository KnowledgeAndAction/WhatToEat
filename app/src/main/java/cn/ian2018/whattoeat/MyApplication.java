package cn.ian2018.whattoeat;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;
import cn.ian2018.whattoeat.utils.Constant;

/**
 * Created by Administrator on 2016/12/25/025.
 */

public class MyApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化Bomb
        Bmob.initialize(this, Constant.BMOB_APP_ID);
        sContext = this;
    }

    public static Context getContext(){
        return sContext;
    }
}
