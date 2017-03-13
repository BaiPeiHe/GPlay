package com.example.baihe.gplay.ui.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.baihe.gplay.R;
import com.example.baihe.gplay.Utils.BitmapHelper;
import com.example.baihe.gplay.Utils.UIUtils;
import com.example.baihe.gplay.domain.AppInfo;
import com.example.baihe.gplay.http.HttpHelper;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by baihe on 2017/3/13.
 */

public class AppHolder extends BaseHolder<AppInfo> {
    private TextView tvName,tvSize,tvDes;
    private ImageView ivPic;
    private RatingBar rbStar;
    private BitmapUtils mBitmapUtils;

    @Override
    // 加载布局文件
    // 初始化控件，findViewById
    public View initView() {
        // 加载布局文件
        View view = UIUtils.inFlate(R.layout.list_item_home);
        // 初始化控件
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvSize = (TextView) view.findViewById(R.id.tv_size);
        tvDes = (TextView) view.findViewById(R.id.tv_des);

        ivPic = (ImageView) view.findViewById(R.id.iv_icon);

        rbStar = (RatingBar) view.findViewById(R.id.rb_star);

        mBitmapUtils = BitmapHelper.getBitmapUtils();

        return view;
    }

    @Override
    public void refreshView(AppInfo data) {

        tvName.setText(data.name);
        tvDes.setText(data.des);
        tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(),data.size));
        rbStar.setRating(data.stars);

        mBitmapUtils.display(ivPic, HttpHelper.URL + "image?name=" + data.iconUrl);
//        tvContent.setText(data.name);
    }
}
