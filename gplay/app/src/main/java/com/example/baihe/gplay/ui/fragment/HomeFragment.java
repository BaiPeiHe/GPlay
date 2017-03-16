package com.example.baihe.gplay.ui.fragment;

import android.view.View;

import com.example.baihe.gplay.Utils.UIUtils;
import com.example.baihe.gplay.domain.AppInfo;
import com.example.baihe.gplay.http.Protocol.HomeProtocol;
import com.example.baihe.gplay.ui.adapter.MyBaseAdapter;
import com.example.baihe.gplay.ui.holder.BaseHolder;
import com.example.baihe.gplay.ui.holder.HomeBannerHolder;
import com.example.baihe.gplay.ui.holder.HomeHolder;
import com.example.baihe.gplay.ui.view.BHListView;
import com.example.baihe.gplay.ui.view.LoadingPage;

import java.util.ArrayList;

/** 首页
 * Created by baihe on 2017/2/23.
 */

public class HomeFragment extends BaseFragment {

    private ArrayList<AppInfo> dataList;
    private ArrayList<String> bannerData;

    // 如果数据加载成功，回调此方法，运行在主线程
    @Override
    public View onCreateSuccessView() {

        // listView
        BHListView view = (BHListView) new BHListView(UIUtils.getContext());

        // banner
        HomeBannerHolder bannerHolder = new HomeBannerHolder();
        view.addHeaderView(bannerHolder.getRootView());

        view.setAdapter(new HomeAdapter(dataList));
        // banner 数据
        if(bannerData != null){
            bannerHolder.setData(bannerData);
        }

        return view;
    }

    // 已经在子线程，可以直接执行耗时的网络请求
    @Override
    public LoadingPage.ResultState onLoad() {

        // 请求网络
        HomeProtocol protocol = new HomeProtocol();
        dataList = protocol.getData(0);// 加载第一页数据

        bannerData = protocol.getBannerData();

        return check(dataList);
    }



    class HomeAdapter extends MyBaseAdapter<AppInfo> {

        public HomeAdapter(ArrayList<AppInfo> dataList) {
            super(dataList);
        }

        @Override
        public BaseHolder<AppInfo> getHolder() {
            return new HomeHolder();
        }

        @Override
        public ArrayList<AppInfo> onLoadMore() {
            // 加载数据，此方法就是在子线程中的

            ArrayList<AppInfo> moreDataList = new ArrayList<AppInfo>();

            HomeProtocol protocol = new HomeProtocol();
            moreDataList = protocol.getData(dataList.size());

            return moreDataList;
        }
    }
}

