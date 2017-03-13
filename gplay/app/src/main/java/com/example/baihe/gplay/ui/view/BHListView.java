package com.example.baihe.gplay.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by baihe on 2017/3/13.
 */

public class BHListView extends ListView {
    public BHListView(Context context) {
        super(context);
        initView();
    }

    public BHListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BHListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView(){
        // 设置背景状态选择器的颜色是没有颜色，即全透明
        this.setSelector( new ColorDrawable());

        // 去掉分割线
        this.setDivider(null);
        // 避免，ListView 滑动的时候背景变为黑色
        this.setCacheColorHint(Color.TRANSPARENT);
    }

}
