package com.dashu.log;

import com.dashu.log.Entity.ErrorLogType;
import com.dashu.log.classification.action.Classification;
import com.dashu.log.monitor.action.Monitor;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description 主程序入口
 * @Author: xuyouchang
 * @Date 2018/8/27 下午5:28
 **/
public class MainExe {
    //YML配置文件路径
    private static final String YML_PATH="/Users/dashu/xyc/walle/src/main/java/com/dashu/log/monitor/monitor.yml";

    public static void main(String[] args) throws IOException {
        while (true){
            //获取关键监控信息
            Monitor monitor=new Monitor();
            List<Map> messageMap=monitor.getMonitorInfo(YML_PATH);
            //分类
            Classification classification=new Classification();
            List<ErrorLogType> alterInfoList=classification.alterInfo(messageMap);
            //告警
            for(ErrorLogType alterInfo:alterInfoList){
                String businessId=alterInfo.getBusinessId();
                String businessName=alterInfo.getBusinessName();
                String logLevel=alterInfo.getLogLevel();
                String category=alterInfo.getCategory();
                String keywords=alterInfo.getKeywords();
                String message=alterInfo.getMessage();
            }
            try {
                TimeUnit.MINUTES.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
