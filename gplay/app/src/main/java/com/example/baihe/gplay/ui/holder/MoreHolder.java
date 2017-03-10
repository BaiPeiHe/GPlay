package com.example.baihe.gplay.ui.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baihe.gplay.R;
import com.example.baihe.gplay.Utils.UIUtils;

/**
 * Created by baihe on 2017/2/25.
 */




public class MoreHolder extends BaseHolder<Integer> {

    // 加载更多的几种状态
    // 1、有跟多加载数据
    // 2、加载更多失败
    // 3、没有更多数据
    public static final int STATE_LOAD_MORE_HASMORE     = 1;
    public static final int STATE_LOAD_MORE_ERROR       = 2;
    public static final int STATE_LOAD_MORE_NONE        = 3;
    private LinearLayout llLoadMore;
    private TextView tvLoadError;

    // 通过布尔参数判断，是否有更多数据
    public MoreHolder(boolean hasMore) {

        // 将状态传递给 data，这样可以父类会触发 refreshView 方法，再将状态传递回来
        setData(hasMore ? STATE_LOAD_MORE_HASMORE : STATE_LOAD_MORE_NONE);
    }

    @Override
    public View initView() {

        View view = UIUtils.inFlate(R.layout.list_item_more);

        llLoadMore = (LinearLayout) view.findViewById(R.id.ll_load_more);
        tvLoadError = (TextView) view.findViewById(R.id.tv_load_error);

        return view;
    }

    @Override
    public void refreshView(Integer data) {
        switch (data){
            case STATE_LOAD_MORE_HASMORE:
                // 显示加载更多
                llLoadMore.setVisibility(View.VISIBLE);
                tvLoadError.setVisibility(View.GONE);
                break;
            case STATE_LOAD_MORE_NONE:
                // 隐藏加载更多
                tvLoadError.setVisibility(View.GONE);
                llLoadMore.setVisibility(View.GONE);
                break;
            case STATE_LOAD_MORE_ERROR:
                // 显示加载失败布局
                tvLoadError.setVisibility(View.VISIBLE);
                llLoadMore.setVisibility(View.GONE);

                break;
        }

    }


}
