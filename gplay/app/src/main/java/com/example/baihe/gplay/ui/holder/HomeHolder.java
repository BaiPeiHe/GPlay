package com.example.baihe.gplay.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.example.baihe.gplay.R;
import com.example.baihe.gplay.Utils.UIUtils;
import com.example.baihe.gplay.domain.AppInfo;

/** 首页 holder
 * Created by baihe on 2017/2/25.
 */

public class HomeHolder extends BaseHolder<AppInfo> {

    private TextView tvContent;

    @Override
    // 加载布局文件
    // 初始化控件，findViewById
    public View initView() {
        // 加载布局文件
        View view = UIUtils.inFlate(R.layout.list_item_homw);
        // 初始化控件
        tvContent = (TextView) view.findViewById(R.id.tv_content);

        return view;
    }

    @Override
    public void refreshView(AppInfo data) {

        tvContent.setText(data.name);
    }


}
