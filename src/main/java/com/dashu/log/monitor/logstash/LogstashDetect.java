package com.dashu.log.monitor.logstash;

import com.dashu.log.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/26 下午4:30
 **/
public class LogstashDetect {
    private static Logger logger = LoggerFactory.getLogger(LogstashDetect.class);
    private static String HOSTNAME = null;
    private static String BASE_URL = null;

    public LogstashDetect(String hostname){
        this.HOSTNAME = hostname;
        this.BASE_URL = "http://"+this.HOSTNAME+":9600";
    }

    /**
     * 是否存活
     * @return
     */
    public boolean isAlive(){
        HttpUtil httpUtil = new HttpUtil(this);
        try {
            httpUtil.get(BASE_URL);
            return true;
        } catch (IOException e) {
            logger.error(e.toString());
            return false;
        }
    }
}
