package com.dashu.log.alter;

import com.dashu.log.alterRules.ESClusterRule;

/**
 * @Description es集群告警
 * @Author: xuyouchang
 * @Date 2018/11/22 下午5:23
 **/
public class ESClusterAlter {

    /**
     * 告警
     */
    public void alter(){
        ESClusterRule esClusterRule = new ESClusterRule();
        esClusterRule.isHealthRule();
        esClusterRule.isReject();
    }
}
