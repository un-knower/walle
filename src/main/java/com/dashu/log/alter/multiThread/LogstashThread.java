package com.dashu.log.alter.multiThread;

import com.dashu.log.alter.LogstashAlter;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/26 下午5:37
 **/
public class LogstashThread extends Thread{

    private String HOSTNAME;

    public LogstashThread(String hostname){
        this.HOSTNAME = hostname;
    }

    public void run(){
        LogstashAlter logstashAlter = new LogstashAlter(this.HOSTNAME);
        logstashAlter.alter();
    }
}
