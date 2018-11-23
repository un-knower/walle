package com.dashu.log.alter;

import com.dashu.log.util.HttpUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description walle通知器
 * @Author: xuyouchang
 * @Date 2018/11/22 下午3:04
 **/
public class WalleNotify {
    private static final Logger logger = LoggerFactory.getLogger(WalleNotify.class);
    private static final String NOTIFY_SERVER = "http://10.1.3.124:9093/api/v1/alerts";

    /**
     * 发送消息
     * @param altername
     * @param info
     * @return
     */
    public boolean sendMessage(String altername,String info){
        String message = constructMessage(altername,info);
        HttpUtil httpUtil = new HttpUtil(this);
        httpUtil.post(NOTIFY_SERVER,message);
        return true;
    }

    /**
     * 构造消息
     * @param altername
     * @param info
     * @return
     */
    public String constructMessage(String altername,String info){

        JSONObject labelsObject=new JSONObject();
        labelsObject.put("altername",altername);

        JSONObject annotationsObject=new JSONObject();
        annotationsObject.put("info",info);

        JSONObject labels=new JSONObject();
        labels.put("labels",labelsObject);
        labels.put("annotations",annotationsObject);

        JSONArray jsonArray=new JSONArray();
        jsonArray.put(labels);

        return jsonArray.toString();
    }


}
