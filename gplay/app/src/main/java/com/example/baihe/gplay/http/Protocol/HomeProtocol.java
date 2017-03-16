package com.example.baihe.gplay.http.Protocol;

import com.example.baihe.gplay.domain.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/** 首页网络数据解析
 * Created by baihe on 2017/3/9.
 */

public class HomeProtocol extends BaseProtocol<ArrayList<AppInfo>> {

    private ArrayList<String> bannerInfos;

    @Override
    public String getKey() {
        return "home";
    }

    @Override
    public String getParams() {
        return ""; // 如果没有参数，就传空串，不要传 null
    }

    @Override
    public ArrayList<AppInfo> parseData(String result) {
        // JsonObject
        // 解析思路，如果遇到{}，就是 JsonObject，遇到[]，就是 JsonArray
        try {
            JSONObject jo = new JSONObject(result);

            // 解析列表数据
            JSONArray jaList = jo.getJSONArray("list");
            ArrayList<AppInfo> appInfos = new ArrayList<AppInfo>();
            for (int i = 0; i < jaList.length(); i++){
                JSONObject jo1 = jaList.getJSONObject(i);
                AppInfo info = new AppInfo();

                info.des            = jo1.getString("des");
                info.downloadUrl    = jo1.getString("downloadUrl");
                info.iconUrl        = jo1.getString("iconUrl");
                info.id             = jo1.getString("id");
                info.name           = jo1.getString("name");
                info.packageName    = jo1.getString("packageName");
                info.size           = jo1.getLong("size");
                info.stars          = (float) jo1.getDouble("stars");

                appInfos.add(info);
            }

            // 解析 banner 数据
            JSONArray jaBanner = jo.getJSONArray("picture");
            bannerInfos = new ArrayList<String>();
            for(int i = 0; i < jaBanner.length(); i++){
                String pic = jaBanner.getString(i);
                bannerInfos.add(pic);
            }

            return appInfos;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<String> getBannerData() {
        return bannerInfos;
    }

}
