package cn.ian2018.whattoeat;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/12/25/025.
 */

public class MyApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static Context getContext(){
        return sContext;
    }
}
