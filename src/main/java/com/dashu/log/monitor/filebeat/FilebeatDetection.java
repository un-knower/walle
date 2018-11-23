package com.dashu.log.monitor.filebeat;

import com.dashu.log.util.HttpUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Description filebeat检测
 * @Author: xuyouchang
 * @Date 2018/11/21 上午10:18
 **/
public class FilebeatDetection {
    private static final Logger logger = LoggerFactory.getLogger(FilebeatDetection.class);
    private static String HOSTNAME = null;
    private static String STATS_URL = "http://"+HOSTNAME+":5066/stats";
    private static String INFO_URL = "http://"+HOSTNAME+":5066/";

    public FilebeatDetection(String hostname){
        this.HOSTNAME = hostname;
    }

    /**
     * 检测filebeat是否存活
     * @return 检测结果
     */
    public boolean isAlive(){
        HttpUtil httpUtil = new HttpUtil(this);
        String result = httpUtil.get(INFO_URL);
        JSONObject jsonObject = new JSONObject(result);
        String hostname = jsonObject.get("hostname").toString();
        if (hostname!=null){
            logger.info(hostname+" is alive!");
            return true;
        }else {
            logger.warn(hostname+" is not alive!");
            return false;
        }
    }

    /**
     * 检查filebeat实例状态
     */
    // TODO: 2018/11/21 解析filebeat状态
    public void checkStats(){
        HttpUtil httpUtil =new HttpUtil(this);
        String result = httpUtil.get(STATS_URL);
        JSONObject jsonObject = new JSONObject(result);
        String beat = jsonObject.get("beat").toString();
        String libbeat = jsonObject.get("libbeat").toString();
        String system = jsonObject.get("system").toString();
        String pipeline = jsonObject.get("filebeat").toString();
        String registrar = jsonObject.get("registrar").toString();
        logger.info("filebeat stats");
    }
}
