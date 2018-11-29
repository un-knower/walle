package com.dashu.log.client;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/27 下午4:12
 **/
@Component
public class ClientUtil {

    public String successMessage(String data){
        data = data.replace('"','\'');
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status","success");
        jsonObject.put("datas",data);
        jsonObject.put("errortype",0);
        return jsonObject.toString();
    }

    public String nullMessage(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status","the message is null");
        jsonObject.put("errortype",-1);
        return jsonObject.toString();
    }

    public String failMessage(String error){
        error = error.replace('"','\'');
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status","fail");
        jsonObject.put("datas",error);
        jsonObject.put("errortype",1);
        return jsonObject.toString();
    }
}
