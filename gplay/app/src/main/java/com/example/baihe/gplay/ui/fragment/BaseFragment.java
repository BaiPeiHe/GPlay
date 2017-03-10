package com.example.baihe.gplay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baihe.gplay.Utils.UIUtils;
import com.example.baihe.gplay.ui.view.LoadingPage;

import java.util.ArrayList;

/**
 * Created by baihe on 2017/2/23.
 */

public abstract class BaseFragment extends Fragment {

    private LoadingPage mLoadingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mLoadingPage = new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessView() {

                // 注意：一定要使用  BaseFragment  调用方法
                return BaseFragment.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return BaseFragment.this.onLoad();
            }
        };

        return mLoadingPage;

    }

    // 加载成功的布局，由子类来实现
    public abstract View onCreateSuccessView();

    // 加载数据的方法，由子类实现
    public abstract LoadingPage.ResultState onLoad();


    // 开始加载数据
    public void loadData(){
        if(mLoadingPage != null){
            mLoadingPage.loadData();
        }
    }

    // 对网络请求的数据进行校验
    public LoadingPage.ResultState check(Object obj){
        if(obj != null){
            if(obj instanceof ArrayList){ // 判断是否为集合
                ArrayList list = (ArrayList) obj;
                if(list.isEmpty()){ // 判断是否为空
                    return LoadingPage.ResultState.STATE_EMPTY;
                }
                else {
                    return LoadingPage.ResultState.STATE_SUCCESS;
                }
            }
        }
        return LoadingPage.ResultState.STATE_ERROR;
    }

}

