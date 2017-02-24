package com.example.baihe.gplay.ui.fragment;

import android.view.View;

import com.example.baihe.gplay.ui.view.LoadingPage;

/** 游戏
 * Created by baihe on 2017/2/23.
 */

public class GameFragment extends BaseFragment {
    @Override
    public View onCreateSuccessView() {
        return null;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return LoadingPage.ResultState.STATE_EMPTY;
    }
}
