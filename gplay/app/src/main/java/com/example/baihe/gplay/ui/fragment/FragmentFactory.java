package com.example.baihe.gplay.ui.fragment;

import android.app.Fragment;
import android.content.Intent;

import java.util.HashMap;

/** 生产Fragment 的工厂
 * Created by baihe on 2017/2/23.
 */
public class FragmentFactory {

    private static HashMap<Integer, BaseFragment> mFragmentMap = new HashMap<Integer, BaseFragment>();

    public static BaseFragment createFragmet(int position) {
        // 从集合中取出，没有再创建
        BaseFragment fragmet = mFragmentMap.get(position);

        if(fragmet == null){
            switch (position) {
                case 0:
                    fragmet = new HomeFragment();
                    break;
                case 1:
                    fragmet = new AppFragment();
                    break;
                case 2:
                    fragmet = new GameFragment();
                    break;
                case 3:
                    fragmet = new SubjectFragment();
                    break;
                case 4:
                    fragmet = new RecommendFragment();
                    break;
                case 5:
                    fragmet = new CategoryFragment();
                    break;
                case 6:
                    fragmet = new HotFragment();
                    break;

                default:
                    break;
            }
            // 保存在集合中
            mFragmentMap.put(position,fragmet);
        }
        return fragmet;
    }
}
