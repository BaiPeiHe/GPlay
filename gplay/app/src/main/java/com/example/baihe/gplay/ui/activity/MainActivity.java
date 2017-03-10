package com.example.baihe.gplay.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.baihe.gplay.R;
import com.example.baihe.gplay.Utils.UIUtils;
import com.example.baihe.gplay.ui.fragment.BaseFragment;
import com.example.baihe.gplay.ui.fragment.FragmentFactory;
import com.example.baihe.gplay.ui.view.PagerTab;

public class MainActivity extends BaseActivity {

    private ViewPager mViewPager;
    private PagerTab mPagerTab;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPagerTab = (PagerTab) findViewById(R.id.pager_tab);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        // 绑定
        mPagerTab.setViewPager(mViewPager);

        mPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

                BaseFragment fragment = FragmentFactory.createFragmet(position);

                // 开始加载数据
                fragment.loadData();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyAdapter extends FragmentPagerAdapter {

        private final String[] mTabNames;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            mTabNames = UIUtils.getStringArray(R.array.tab_names);
        }
        // 返回分类标题
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabNames[position];
        }

        // 返回当前页面位置的 fragment 对象
        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.createFragmet(position);
            return fragment;
        }

        // fragment 的数量
        @Override
        public int getCount() {
            return mTabNames.length;
        }
    }

}
