package com.dashu.log.monitor.filebeat;

import com.dashu.log.util.HttpUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * @Description filebeat检测
 * @Author: xuyouchang
 * @Date 2018/11/21 上午10:18
 **/
public class FilebeatDetection {
    private static final Logger logger = LoggerFactory.getLogger(FilebeatDetection.class);
    private static String HOSTNAME = null;
    private static String STATS_URL = null;
    private static String INFO_URL = null;

    public FilebeatDetection(String hostname){
        this.HOSTNAME = hostname;
        this.STATS_URL = "http://"+this.HOSTNAME+":5066/stats";
        this.INFO_URL = "http://"+ this.HOSTNAME+":5066/";
    }

    /**
     * 检测filebeat是否存活
     * @return 检测结果
     */
    public boolean isAlive(){
        HttpUtil httpUtil = new HttpUtil(this);
        try {

            String result = httpUtil.get(this.INFO_URL);
            return true;
        } catch (IOException e) {
            logger.error(e.toString());
            return false;
        }
    }

    /**
     * 检查filebeat实例状态
     */
    // TODO: 2018/11/21 解析filebeat状态
    public void checkStats(){
        HttpUtil httpUtil =new HttpUtil(this);
        String result = null;
        try {
            result = httpUtil.get(STATS_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(result);
        String beat = jsonObject.get("beat").toString();
        String libbeat = jsonObject.get("libbeat").toString();
        String system = jsonObject.get("system").toString();
        String pipeline = jsonObject.get("filebeat").toString();
        String registrar = jsonObject.get("registrar").toString();
        logger.info("filebeat stats");
    }
}
