package com.example.baihe.gplay.Utils;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by baihe on 2017/3/13.
 * 单例
 */

public class BitmapHelper {

    private static BitmapUtils mBitmapUtils = null;
    public static BitmapUtils getBitmapUtils() {
        if(mBitmapUtils == null){
            synchronized (BitmapHelper.class){
                if(mBitmapUtils == null){
                    mBitmapUtils = new BitmapUtils(UIUtils.getContext());
                }
            }
        }
        return mBitmapUtils;
    }

}
