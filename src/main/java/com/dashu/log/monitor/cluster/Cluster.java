package com.dashu.log.monitor.cluster;

import com.dashu.log.util.HttpUtil;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * @Description es集群级别服务指标监控
 * @Author: xuyouchang
 * @Date 2018/11/1 上午10:39
 **/
public class Cluster {
    private static String BASE_URL = "http://elastic:elastic@es1:9200/";
    private static final Logger logger = LoggerFactory.getLogger(Cluster.class);


    /**
     * 集群健康监控
     * @return
     */
    public  boolean isHealth() {
        String URL = BASE_URL+"_cluster/health";
        HttpUtil httpUtil = new HttpUtil(this);
        String clusterHealth = null;
        try {
            clusterHealth = httpUtil.get(URL);
            JSONObject healthObject = new JSONObject(clusterHealth);
            String status = healthObject.get("status").toString();
            if (status.equals("green")){
                return true;
            }else{
                return false;
            }

        } catch (IOException e) {
            logger.error(e.toString());
            return false;
        }

    }

}
