package com.example.baihe.gplay.http.Protocol;

import com.example.baihe.gplay.Utils.IOUtils;
import com.example.baihe.gplay.Utils.StringUtils;
import com.example.baihe.gplay.Utils.UIUtils;
import com.example.baihe.gplay.http.HttpHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by baihe on 2017/3/9.
 *
 * 请求网络数据的基类
 */

public abstract class BaseProtocol<T> {

    // index 表示从那个位置开始向后取20个数据
    public T getData(int index) {
        // 先判断是否有缓存，有的话就加载缓存
        String result = getCache(index);
        if(StringUtils.isEmpty(result)){
            // 从服务器请求数据
            result = getDataFromServer(index);
            if(!StringUtils.isEmpty(result)){ // 写入缓存
                setCache(index, result);
            }
        }

        // 解析数据
        if(result != null){
            T data = parseData(result);
            return data;
        }
        return null;
    }

    private String getDataFromServer(int index){
        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey() + "?index=" +
                index + getParams());

        if(httpResult != null){
            String result = httpResult.getString();
            System.out.println("访问结果: " + result);
            return result;
        }

        return null;
    }
    // 获取网络请求的关键词，由子类实现
    public abstract String getKey();
    // 获取网络请求的参数，由子类实现
    public abstract String getParams();


    // 存储缓存
    public void setCache(int index, String json){
        // 以 url 为文件名，json为文件内容，直接保存在文件中

        File cacheDir = UIUtils.getContext().getCacheDir(); // 本应用的缓存文件夹
        // 创建缓存文件
        // 参数1：创建文件的地址，参数2：创建文件的名称
        File cacheFile = new File(cacheDir,getKey() + "？index=" + index + getParams());

        FileWriter writer = null;

        try {
            writer = new FileWriter(cacheFile);

            // 设置缓存失效的截止时间，30分钟的有效期,
            long deadline = System.currentTimeMillis() + 30 * 60 * 1000;
            // 在第一行输入缓存失效时间，注意：一定要换行
            writer.write(deadline + "\n");

            // 写入 json
            writer.write(json);
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 关闭
            IOUtils.close(writer);
        }
    }

    // 读取缓存
    public String getCache(int index){
        // 以 url 为文件名，json为文件内容，直接保存在文件中

        File cacheDir = UIUtils.getContext().getCacheDir(); // 本应用的缓存文件夹
        // 创建缓存文件
        File cacheFile = new File(cacheDir,getKey() + "？index=" + index + getParams());

        // 判断缓存是否存在
        if(cacheFile.exists()){

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(cacheFile));

                String deadLine = reader.readLine();
                long deadTime = Long.parseLong(deadLine);

                if(System.currentTimeMillis() < deadTime){ // 当前时间小于截止时间，说明缓存有效
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line = reader.readLine())!= null){
                        sb.append(line);
                    }
                    return sb.toString();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(reader);
            }

        }
        return null;

    }

    // 解析数据，由子类实现
    public abstract T parseData(String result);


}
