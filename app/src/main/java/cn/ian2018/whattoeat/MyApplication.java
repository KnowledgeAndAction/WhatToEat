package cn.ian2018.whattoeat;

import android.app.Application;
import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.ian2018.whattoeat.bean.ExceptionFile;
import cn.ian2018.whattoeat.bean.PhoneInfo;
import cn.ian2018.whattoeat.utils.Constant;
import cn.ian2018.whattoeat.utils.Logs;
import cn.ian2018.whattoeat.utils.PhoneInfoUtil;

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

        // 捕获全局未捕获的异常
        catchException();
    }

    private void catchException() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                // 输出异常
                ex.printStackTrace();

                // 将打印的异常存到SD卡中
                String path = getExternalFilesDir("log").getPath() + File.separator + "whattoeat_error.log";
                File file = new File(path);
                try {
                    PrintWriter printWriter = new PrintWriter(file);
                    ex.printStackTrace(printWriter);
                    printWriter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                // 将异常文件上传到服务器
                postErrorFile(file);

                // 手动退出应用
                System.exit(0);
            }
        });
    }

    private void postErrorFile(File file) {
        ExceptionFile exceptionFile = new ExceptionFile();
        BmobFile bmobFile = new BmobFile(file);
        PhoneInfo phoneInfo = PhoneInfoUtil.getPhoneInfo();
        exceptionFile.setExceptionFile(bmobFile);
        exceptionFile.setPhoneBrand(phoneInfo.getPhoneBrand());
        exceptionFile.setPhoneBrandType(phoneInfo.getPhoneBrandType());
        exceptionFile.setAndroidVersion(phoneInfo.getAndroidVersion());
        exceptionFile.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Logs.d("上传日志成功");
                } else {
                    Logs.e("上传日志失败" + e.toString());
                }
            }
        });
    }

    public static Context getContext(){
        return sContext;
    }
}
