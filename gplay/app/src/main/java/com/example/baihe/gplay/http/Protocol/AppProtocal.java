package com.example.baihe.gplay.http.Protocol;

import com.example.baihe.gplay.domain.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by baihe on 2017/3/13.
 */

public class AppProtocal extends BaseProtocol<ArrayList<AppInfo>> {
    @Override
    public String getKey() {
        return "app";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<AppInfo> parseData(String result) {

        try {
            JSONArray ja =  new JSONArray(result);
            ArrayList<AppInfo> appInfos = new ArrayList<AppInfo>();

            for(int i = 0; i < ja.length(); i++){
                JSONObject jo1 = ja.getJSONObject(i);
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

            return appInfos;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
