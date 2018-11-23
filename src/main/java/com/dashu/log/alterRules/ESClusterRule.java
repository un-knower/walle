package com.dashu.log.alterRules;

import com.dashu.log.alter.WalleNotify;
import com.dashu.log.monitor.cluster.Cluster;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/22 下午5:09
 **/
public class ESClusterRule {

    /**
     * es集群是否健康
     */
    public void isHealthRule(){
        Cluster cluster = new Cluster();
        boolean ishealth = cluster.isHealth();
        if (!ishealth){
            WalleNotify notify =new WalleNotify();
            notify.sendMessage("es cluster","es cluster is not health now, please check it!");
        }
    }
}
