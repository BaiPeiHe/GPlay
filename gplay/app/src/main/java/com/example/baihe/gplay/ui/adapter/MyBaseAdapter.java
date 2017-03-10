package com.example.baihe.gplay.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.baihe.gplay.Utils.UIUtils;
import com.example.baihe.gplay.ui.holder.BaseHolder;
import com.example.baihe.gplay.ui.holder.MoreHolder;

import java.util.ArrayList;

/** 对 Adapter 的封装
 * Created by baihe on 2017/2/25.
 *
 * 使用泛型
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    private static final int TYPE_NORMAL = 0; // 正常布局类型
    private static final int TYPE_MORE = 1;   // 加载跟多布局类型

    private ArrayList<T> dataList;

    public MyBaseAdapter(ArrayList<T> dataList ){
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        // 怎么加加载布局的数量
        return dataList.size() + 1;
    }

    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 返回布局类型个数
    @Override
    public int getViewTypeCount() {
        // 两种：普通布局+加载更多布局
        return 2;
    }

    // 当前位置要展示那种布局类型
    @Override
    public int getItemViewType(int position) {

        if(position == getCount() - 1){ // 最后一个
            return TYPE_MORE;
        }
        else {
            return getInnerType();
        }
    }

    // 优点：如果布局类型不止两种的时候，可以在子类中重写这个方法来修改
    public int getInnerType(){
        // 返回默认的是普通类型
        return TYPE_NORMAL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BaseHolder holder ;
        if(convertView == null){
            // 1、实现了加载布局，初始化控件，设置 tag
            if(getItemViewType(position) == TYPE_MORE){
                // 加载跟多类型布局,所有页面的布局都一样，所以可以在父类中之间创建
                holder = new MoreHolder(hasMore());
            }
            else {
                holder = getHolder();
            }
        }
        else {
            holder = (BaseHolder) convertView.getTag();
        }
        // 2、根据数据刷新页面
        if(getItemViewType(position) == TYPE_MORE){
            // 加载更多的布局，同时加载更多数据
            MoreHolder moreHolder = (MoreHolder) holder;
            // 只有在有更多数据的时候，才会加载
            if(moreHolder.getData() == MoreHolder.STATE_LOAD_MORE_HASMORE){
                loadMore(moreHolder);
            }
        }
        else {
            holder.setData(getItem(position));
        }

        return holder.getRootView();
    }

    // 默认可以加载更多数据，可以在子类里重写，修改
    public boolean hasMore(){
        return true;
    }

    // 由子类实现，返回当前的 holder
    public abstract BaseHolder<T> getHolder();

    // 标记是否正在加载更多
    private boolean isLoadMore = false;

    // 加载跟多数据
    public void loadMore(final MoreHolder holder){
        if(!isLoadMore){

            new Thread(){
                @Override
                public void run() {
                    final ArrayList<T> moreDataList = onLoadMore();

                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if(moreDataList != null){
                                // 每页20条数据，如果数据小于20条，就认为到了最后一页
                                if(moreDataList.size() < 20){
                                    holder.setData(MoreHolder.STATE_LOAD_MORE_NONE);
                                    Toast.makeText(UIUtils.getContext(),"没有更多数据了",Toast
                                            .LENGTH_SHORT).show();
                                }
                                else {
                                    // 还有跟多数据
                                    holder.setData(MoreHolder.STATE_LOAD_MORE_HASMORE);
                                }

                                // 将更多数据追加到当前集合中
                                dataList.addAll(moreDataList);

                                // 刷新页面
                                MyBaseAdapter.this.notifyDataSetChanged();
                            }
                            else {
                                // 加载更多失败
                                holder.setData(MoreHolder.STATE_LOAD_MORE_ERROR);
                            }

                            isLoadMore = false;
                        }
                    });
                }
            }.start();
        }
    }
    // 由子类实现，加载更多数据
    public abstract ArrayList<T>  onLoadMore();

}
