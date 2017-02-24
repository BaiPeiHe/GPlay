package com.example.baihe.gplay.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.example.baihe.gplay.R;
import com.example.baihe.gplay.Utils.UIUtils;

/** 根据当前状态来显示不同页面的自定义控件
 *
 *  - 未加载
 *  - 加载中
 *  - 加载失败
 *  - 数据为空
 *  - 加载成功
 *
 * Created by baihe on 2017/2/24.
 */

public abstract class LoadingPage extends FrameLayout {

    private static int STATE_LOAD_UNDO = 1;      // 未加载
    private static int STATE_LOAD_LOADING = 2;   // 正在加载
    private static int STATE_LOAD_ERROR = 3;     // 加载失败
    private static int STATE_LOAD_EMPTY = 4 ;    // 数据为空
    private static int STATE_LOAD_SUCESS = 5;    // 加载成功

    private int mCurrentState = STATE_LOAD_UNDO; // 当前状态
    private View mLoadingPage;
    private View mErrorPage;
    private View mEmptyPage;
    private View mSuccessPage;

    public LoadingPage(Context context) {
        super(context);

        initVie();

    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);

        initVie();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initVie();
    }

    private void initVie(){

        // 初始化 加载中 的布局
        if(mLoadingPage == null){
            mLoadingPage = UIUtils.inFlate(R.layout.page_loading);
            addView(mLoadingPage);
        }

        // 初始化 加载失败 布局
        if(mErrorPage == null){
            mErrorPage = UIUtils.inFlate(R.layout.page_error);
            addView(mErrorPage);
        }

        // 初始化 数据为空 布局
        if(mEmptyPage == null){
            mEmptyPage = UIUtils.inFlate(R.layout.page_empty);
            addView(mEmptyPage);
        }

        shwoRightPage();

    }

    // 根据当前的状态，决定显示那个布局
    private void shwoRightPage() {

        // 加载中
        mLoadingPage.setVisibility((mCurrentState == STATE_LOAD_UNDO || mCurrentState == STATE_LOAD_LOADING) ? View.VISIBLE : View.GONE);
        // 加载失败
        mErrorPage.setVisibility((mCurrentState == STATE_LOAD_ERROR ? View.VISIBLE : View.GONE));

        // 数据为空
         mEmptyPage.setVisibility((mCurrentState == STATE_LOAD_EMPTY ? View.VISIBLE : View.GONE));

        // 状态为成功，并且成功布局为空，初始化 加载成功 布局
        if(mCurrentState == STATE_LOAD_SUCESS && mSuccessPage == null){
            mSuccessPage = onCreateSuccessView();

            if(mSuccessPage != null){
                addView(mSuccessPage);
            }
        }

        if(mSuccessPage != null){
            mSuccessPage.setVisibility((mCurrentState == STATE_LOAD_SUCESS ? View.VISIBLE : View.GONE));
        }


    }

    // 开始加载数据
    public void loadData(){

        if(mCurrentState != STATE_LOAD_LOADING){
            mCurrentState = STATE_LOAD_LOADING;

            new Thread(){
                @Override
                public void run() {
                    final ResultState resultState = onLoad();

                    // 在主线程，刷新页面
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if(resultState != null){

                                // 加载结束后，更新状态
                                mCurrentState = resultState.getState();

                                // 根据最新状态，刷新页面
                                shwoRightPage();
                            }
                        }
                    });
                }
            }.start();
        }
    }

    // 加载成功后显示的布局,由使用者实现
    public abstract View onCreateSuccessView();

    // 加载网络数据，返回值表示网络请求结束后的状态
    public abstract ResultState onLoad();

    public enum ResultState{
        STATE_SUCCESS(STATE_LOAD_SUCESS),
        STATE_EMPTY(STATE_LOAD_EMPTY),
        STATE_ERROR(STATE_LOAD_ERROR);

        private int state;
        private ResultState(int state){
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }
}

