package com.dashu.log;

import com.dashu.log.classification.action.Classification;
import com.dashu.log.entity.ErrorLogType;
import com.dashu.log.monitor.action.Monitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description 主程序入口
 * @Author: xuyouchang
 * @Date 2018/8/30 上午11:15
 **/
@Component
public class MainRunner implements CommandLineRunner {
    //YML配置文件路径
    private static final String YML_PATH="/Users/dashu/xyc/walle/src/main/resources/monitor.yml";
    private static final Logger logger = LoggerFactory.getLogger(MainRunner.class);

    @Resource
    private Monitor monitor;
    @Resource
    private Classification classification;

    @Override
    public void run(String... args) throws Exception {
        logger.info("walle start monitor ...");
        int i=1;
        while (true){
            //获取关键监控信息
            List<Map> messageMap= null;
            try {
                messageMap = monitor.getMonitorInfo(YML_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //分类
            List<ErrorLogType> alterInfoList=classification.alterInfo(messageMap);
            //告警
            for(ErrorLogType alterInfo:alterInfoList){
//                String businessId=alterInfo.getBusinessId();
                String businessName=alterInfo.getBusinessName();
                String logLevel=alterInfo.getLogLevel();
                String category=alterInfo.getCategory();
                String keywords=alterInfo.getKeywords();
                String message=alterInfo.getMessage();
            }


            //等待时间设置
            TimeUnit.SECONDS.sleep(20);
            System.out.println("scan "+i+"time");
            i++;



        }
    }
}
