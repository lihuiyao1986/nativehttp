package com.huasco.plugins;

import com.alibaba.fastjson.JSON;
import com.hdl.myhttputils.bean.StringCallBack;
import com.huasco.http.HttpUtils;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送http请求的插件
 */

public class NativeHttp extends CordovaPlugin {
    
    private CallbackContext callbackContext;
    
    private static String ERROR_CODE_LABEL = "responseCode";
    
    private static String ERROR_MSG_LABEL = "message";
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        if ("request".equals(action)) { // 发送请求
            return doRequest(args, callbackContext);
        }
        return super.execute(action, args, callbackContext);
    }
    
    /**
     * 添加缓存
     *
     * @param args
     * @param callbackContext
     * @return
     */
    private Boolean doRequest(JSONArray args, CallbackContext callbackContext) throws JSONException {
        JSONObject options = args.optJSONObject(0);
        String params = trimNull(options.getString("params"), "");
        String method = trimNull(options.getString("method"), "post");
        String url = trimNull(options.getString("url"), "");
        int timeout = Integer.parseInt(trimNull(options.getString("timeout"),"5"));
        Map<String,Object> paramObj = null;
        try{
            if (!isEmpty(params)){
                paramObj = JSON.parseObject(params, Map.class);
            }
        }catch (Exception ex){
        }
        HttpUtils.request(method.equalsIgnoreCase("post"),url,paramObj,timeout,new StringCallBack(){
            
            @Override
            public void onSucceed(String s) {
                NativeHttp.this.callbackContext.success(JSON.toJSONString(s));
            }
            
            @Override
            public void onFailed(Throwable throwable) {
                NativeHttp.this.callbackContext.success(packError());
                
            }
        });
        return true;
    }
    
    public static boolean isEmpty(String str){
        if(str == null || str.trim().equals(""))
            return true;
        return false;
    }
    
    public static String trimNull(String str,String defaultStr){
        if (isEmpty(str)){
            return defaultStr;
        }else{
            return str.trim();
        }
    }
    
    /**
     * 组装错误信息
     ** @return
     */
    private String packError() {
        Map<String, Object> errorInfo = new HashMap<String, Object>();
        errorInfo.put(ERROR_CODE_LABEL, "9999");
        errorInfo.put(ERROR_MSG_LABEL, "系统繁忙,请稍后再试");
        return JSON.toJSONString(errorInfo);
    }
}
