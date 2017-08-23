package com.huasco.http;

import com.hdl.myhttputils.MyHttpUtils;
import com.hdl.myhttputils.bean.StringCallBack;

import java.util.Map;

/**
 * Created by jk on 2017/8/23.
 */

public class HttpUtils {
    
    /**
     * 发送get请求
     *
     * @param url
     * @param params
     * @param timeout - 超时时间
     */
    public static void doGet(String url, Map<String, Object> params,int timeout,StringCallBack callBack) {
        MyHttpUtils.build()//构建myhttputils
        .url(url)//请求的url
        .setConnTimeOut(timeout * 1000) // 设置超时时间
        .setReadTimeOut(timeout * 1000) // 设置超时时间
        .addParams(params) // 添加请求参数
        .onExecute(callBack);
    }
    
    
    /**
     * 发送post请求
     *
     * @param url
     * @param params
     */
    public static void doPost(String url, Map<String, Object> params,int timeout,StringCallBack callBack) {
        MyHttpUtils.build()//构建myhttputils
        .url(url)//请求的url
        .setConnTimeOut(timeout * 1000) // 设置超时时间
        .setReadTimeOut(timeout * 1000) // 设置超时时间
        .addParams(params) // 添加请求参数
        .onExecuteByPost(callBack);
    }
    
    
    /**
     * 发送post请求
     *
     * @param url
     * @param params
     */
    public static void request(boolean isPost, String url, Map<String, Object> params,int timeout,StringCallBack callBack) {
        timeout = timeout == 0 ? 5 : timeout;
        if (isPost){
            doPost(url,params,timeout,callBack);
        }else {
            doGet(url,params,timeout,callBack);
        }
    }
    
    
}
