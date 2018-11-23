package com.dashu.log;

import com.dashu.log.alter.ESClusterAlter;
import com.dashu.log.alter.FilebeatAlter;
import com.dashu.log.alter.IndexAlter;
import com.dashu.log.alterRules.FilebeatRule;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description 监控、告警任务调度
 * @Author: xuyouchang
 * @Date 2018/11/23 下午1:22
 **/
@Component
public class Walle {
    @Resource
    private IndexAlter indexAlter;

    /**
     * es index
     */
    @Scheduled(cron = "*/5 * * * * *")
    public void indexAlter(){
        indexAlter.alter();
    }

    /**
     * filebeat
     */
    @Scheduled(cron = "0 */5 * * * *")
    public void filebeatAlter(){
        // TODO: 2018/11/23 获取hostname,多线程处理
        String hostname = "es1";
        FilebeatAlter filebeatAlter = new FilebeatAlter(hostname);
        filebeatAlter.alter();
    }

    /**
     * es cluster
     */
    @Scheduled(cron = "0 */5 * * * *")
    public void esClusterAlter(){
        ESClusterAlter esClusterAlter = new ESClusterAlter();
        esClusterAlter.alter();
    }


}
