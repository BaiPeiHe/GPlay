package com.example.baihe.gplay.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.baihe.gplay.Utils.UIUtils;
import com.example.baihe.gplay.ui.view.LoadingPage;

/** 首页
 * Created by baihe on 2017/2/23.
 */

public class HomeFragment extends BaseFragment {

    // 如果数据加载成功，回调此方法，运行在主线程
    @Override
    public View onCreateSuccessView() {
        TextView view = new TextView(UIUtils.getContext());
        view.setText(getClass().getSimpleName());
        return view;
    }

    // 已经在子线程，可以直接执行耗时的网络请求
    @Override
    public LoadingPage.ResultState onLoad() {
        return LoadingPage.ResultState.STATE_SUCCESS;
    }
}
