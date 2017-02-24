package com.example.baihe.gplay.Utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

import com.example.baihe.gplay.global.GPlayApplication;

/**
 * 工具类
 * Created by baihe on 2017/2/21.
 */

public class UIUtils {

    public static Context getContext() {
        return GPlayApplication.getContext();
    }

    public static Handler getHandler() {
        return GPlayApplication.getHandler();
    }

    public static int getMainThreadId() {
        return GPlayApplication.getMainThreadId();
    }


    ///////////     加载资源文件      /////////////

    // 获取字符串
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    // 获取字符串数组
    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    // 获取图片
    public static Drawable getDrawable(int id) {
        return getContext().getResources().getDrawable(id);
    }
    // 根据 id 获取颜色的状态选择器
    public static ColorStateList getColorStateList(int id) {
        return getContext().getResources().getColorStateList(id);
    }

    // 获取颜色
    public static int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    // 获取尺寸，返回具体的像素值
    public static int getDimen(int id) {
        return getContext().getResources().getDimensionPixelOffset(id);
    }


    ///////////     dip 和 px 的相互转化      /////////////

    public static int dip2px(float dip) {
        // 设备密度
        float density = getContext().getResources().getDisplayMetrics().density;
        // 四舍五入
        return (int) (dip * density + 0.5f);
    }

    public static float px2dip(int px) {
        // 设备密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density;
    }

    ///////////     加载布局文件      /////////////

    public static View inFlate(int id) {
        return View.inflate(getContext(), id, null);
    }

    ///////////     判断是非运行在主线程      /////////////
    public static boolean isRunOnUIThread() {
        // 当前线程 id
        int currentId = android.os.Process.myTid();
        if(currentId == getMainThreadId()) {
            return true;
        }
        else {
            return false;
        }
    }
    // 运行在主线程
    public static void runOnUIThread( Runnable r) {
        // 在主线程中，直接运行
        if(isRunOnUIThread()){
            r.run();
        }
        else {
            // 如果是子线程，借助 Handler 运行在主线程，发送在消息队列
             getHandler().post(r);
        }
    }
}
