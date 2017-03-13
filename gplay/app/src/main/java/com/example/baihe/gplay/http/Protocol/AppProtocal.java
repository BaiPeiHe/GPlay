package com.example.baihe.gplay.http.Protocol;

import com.example.baihe.gplay.domain.AppInfo;

import org.json.JSONArray;

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

        JSONArray ja =  new JSONArray(result);


        return null;
    }
}
