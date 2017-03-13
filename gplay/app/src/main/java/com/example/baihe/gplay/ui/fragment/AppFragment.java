package com.example.baihe.gplay.ui.fragment;

import android.view.View;

import com.example.baihe.gplay.Utils.UIUtils;
import com.example.baihe.gplay.domain.AppInfo;
import com.example.baihe.gplay.ui.adapter.MyBaseAdapter;
import com.example.baihe.gplay.ui.holder.AppHolder;
import com.example.baihe.gplay.ui.holder.BaseHolder;
import com.example.baihe.gplay.ui.view.BHListView;
import com.example.baihe.gplay.ui.view.LoadingPage;

import java.util.ArrayList;

/** 应用
 * Created by baihe on 2017/2/23.
 */

public class AppFragment extends BaseFragment {

    private ArrayList<AppInfo> dataList;

    @Override
    public View onCreateSuccessView() {
        BHListView view = (BHListView) new BHListView(UIUtils.getContext());

        view.setAdapter(new AppAdapter(dataList));

        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return LoadingPage.ResultState.STATE_ERROR;
    }

    class AppAdapter extends MyBaseAdapter<AppInfo>{

        public AppAdapter(ArrayList<AppInfo> dataList) {
            super(dataList);
        }

        @Override
        public BaseHolder getHolder() {
            return new AppHolder();
        }

        @Override
        public ArrayList onLoadMore() {
            return null;
        }
    }

}
