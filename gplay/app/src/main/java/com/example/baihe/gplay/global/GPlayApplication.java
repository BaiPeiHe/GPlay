package com.example.baihe.gplay.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by baihe on 2017/2/21.
 */

public class GPlayApplication extends Application {


    private static Context context;
    private static Handler handler;
    // 主线程 ID
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        handler = new Handler();

        mainThreadId = android.os.Process.myTid();
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }
}
