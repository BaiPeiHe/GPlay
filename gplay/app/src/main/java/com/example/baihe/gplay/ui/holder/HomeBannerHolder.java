package com.example.baihe.gplay.ui.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.baihe.gplay.R;
import com.example.baihe.gplay.Utils.BitmapHelper;
import com.example.baihe.gplay.Utils.UIUtils;
import com.example.baihe.gplay.http.HttpHelper;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/** 首页 Banner Holder
 * Created by baihe on 2017/3/14.
 */

public class HomeBannerHolder extends BaseHolder<ArrayList<String>> {

    private ViewPager mViewPager;
    private ArrayList<String> data;
    private LinearLayout llContainer;
    private int mPreviousPos; // 前一个被选中的指示器点的位置

    @Override
    public View initView() {
        // 创建布局
        RelativeLayout rlRoot = new RelativeLayout(UIUtils.getContext());
        // 初始化布局，根布局的上层控件是 listView，所以要使用 ListView 定义的 LayoutParams
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams
                .MATCH_PARENT,UIUtils.dip2px(150));
        rlRoot.setLayoutParams(params);

        // ViewPager
        mViewPager = new ViewPager(UIUtils.getContext());
        RelativeLayout.LayoutParams vpParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        // 添加到相对布局上
        rlRoot.addView(mViewPager,vpParams);

        // 指示器
        llContainer = new LinearLayout(UIUtils.getContext());
        llContainer.setOrientation(LinearLayout.HORIZONTAL); // 水平方向

        RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        // 设置内边距
        int padding = UIUtils.dip2px(10);
        llContainer.setPadding(padding,padding,padding,padding);

        // 添加规则，设置展示位置
        llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);// 底部对齐
        llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT); // 右对齐

        rlRoot.addView(llContainer,llParams);

        return rlRoot;
    }

    @Override
    public void refreshView(final ArrayList<String> data) {
        this.data = data;

        // 填充数据
        mViewPager.setAdapter(new HomeBannerAdapter());

        // 设置当前显示的 Item
        mViewPager.setCurrentItem(data.size() * 10000);

        // 初始化指示器
        for(int i = 0 ; i < data.size(); i++){
            ImageView point = new ImageView(UIUtils.getContext());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            if(i == 0){ // 默认第一个选中
                point.setImageResource(R.mipmap.indicator_selected);
            }
            else {
                point.setImageResource(R.mipmap.indicator_normal);

                params.leftMargin = UIUtils.dip2px(4); // 左边距
            }

            point.setLayoutParams(params);

            llContainer.addView(point);
        }


        // 监听 ViewPager 的切换事件
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int currentPosition = position % data.size();

                // 当前点设置为被选中状态
                ImageView point = (ImageView) llContainer.getChildAt(currentPosition);
                point.setImageResource(R.mipmap.indicator_selected);

                // 上一个点设置为不选中状态
                ImageView prePoint = (ImageView) llContainer.getChildAt(mPreviousPos);
                prePoint.setImageResource(R.mipmap.indicator_normal);

                mPreviousPos = currentPosition;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 实现无线轮播
        HomeBannerTask task = new HomeBannerTask();
        task.start();

    }


    class HomeBannerTask implements Runnable{

        public void start(){
            // 移除之前所有消息，避免干扰
            UIUtils.getHandler().removeCallbacksAndMessages(null);
            // 延时 3 秒发送消息，触发 run 方法
            UIUtils.getHandler().postDelayed(this, 3 * 1000);
        }

        @Override
        public void run() {
            int currentItem = mViewPager.getCurrentItem();
            currentItem++;
            mViewPager.setCurrentItem(currentItem);

            // 再次延时 3 秒发送消息，触发 run 方法
            UIUtils.getHandler().postDelayed(this, 3 * 1000);
        }
    }

    class HomeBannerAdapter extends PagerAdapter{

        private final BitmapUtils mBitmapUtils;

        public HomeBannerAdapter(){
            mBitmapUtils = BitmapHelper.getBitmapUtils();
        }

        @Override
        public int getCount() {
//            return data.size();
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            final int finalPosition = position % data.size();

            String url = data.get(finalPosition);
            ImageView view = new ImageView(UIUtils.getContext());
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            mBitmapUtils.display(view, HttpHelper.URL + "image?name=" + url);

            container.addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("TAG","Position ：" + finalPosition);
                }
            });

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

}
