package com.dashu.log.alterRules;

import com.dashu.log.alter.WalleNotify;
import com.dashu.log.monitor.filebeat.FilebeatDetection;
import com.dashu.log.util.RunCmdLine;

import java.util.List;

/**
 * @Description filebeat告警规则
 * @Author: xuyouchang
 * @Date 2018/11/22 下午4:52
 **/
public class FilebeatRule {

    /**
     * filebeat是否存活
     * @param cmd
     * @param hostname
     */
    public void isAliveRule(String cmd,String hostname){
        FilebeatDetection filebeatDetection = new FilebeatDetection(hostname);
        boolean isAlive = filebeatDetection.isAlive();
        if (!isAlive){
            WalleNotify notify = new WalleNotify();
            notify.sendMessage("filebeat","the filebeat of "+hostname+" is down!");
            RunCmdLine runCmdLine = new RunCmdLine(this);
            List ret = runCmdLine.callShell(cmd);
            if (ret == null){
                notify.sendMessage("filebeat","we try to up the filebeat of "+hostname+",but fail please check it!");
            }else{
                notify.sendMessage("filebeat","the filebeat of "+hostname+" is up!");
            }
        }
    }

}
