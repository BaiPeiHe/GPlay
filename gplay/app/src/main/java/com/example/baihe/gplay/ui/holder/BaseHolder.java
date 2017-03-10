package com.example.baihe.gplay.ui.holder;

import android.view.View;

/**
 * Created by baihe on 2017/2/25.
 */

public abstract class BaseHolder<T> {

    private final View mTootViwe;
    private T data;

    // 当 new 这个对象的时候，就会加载布局，初始化控件，设置 tag
    public BaseHolder(){
        mTootViwe = initView();
        // 3、打一个 tag
        mTootViwe.setTag(this);
    }

    // 1、加载布局文件
    // 2、初始化控件 findViewById
    public abstract View initView();

    // 设置当前 item 的数据
    public void setData(T data){
        this.data = data;
        refreshView(data);
    }

    // 获取当前 item 的数据
    public T getData(){
        return this.data;
    }

    // 4、根据数据刷新页面
    public abstract void refreshView(T data);

    public View getRootView(){
        return mTootViwe;
    }
}
