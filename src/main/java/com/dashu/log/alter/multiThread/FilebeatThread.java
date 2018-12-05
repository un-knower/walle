package com.dashu.log.alter.multiThread;

import com.dashu.log.alter.FilebeatAlter;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/26 上午11:24
 **/
public class FilebeatThread extends Thread {
    private String HOSTNAME;

    public FilebeatThread(String hostname){
        this.HOSTNAME = hostname;
    }

    public void run(){
        FilebeatAlter filebeatAlter = new FilebeatAlter(this.HOSTNAME);
        filebeatAlter.alter();
    }

}
