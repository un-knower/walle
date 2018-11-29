package com.dashu.log.client;

import com.dashu.log.client.dao.LogstashConfRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/28 下午5:15
 **/
@RestController
public class LogstashClient {
    @Resource
    private LogstashConfRepository logstashConfRepository;
    @Resource
    private ClientUtil clientUtil;

    /**
     * add Logstash Conf
     * @param hostname
     * @return
     */
    @RequestMapping(value = "/logstash/addLogstashConf")
    public String addLogstashConf(@RequestParam(value = "hostname")String hostname){
        if (hostname == null){
            return clientUtil.nullMessage();
        }
        try {
            logstashConfRepository.addHostname(hostname);
            return clientUtil.successMessage(hostname);
        }catch (Exception e){
            return clientUtil.failMessage(e.toString());
        }
    }

    /**
     * get Logstash Conf
     * @return
     */
    @RequestMapping(value = "/logstash/getLogstashConf",method = RequestMethod.GET)
    public String getLogstashConf(){
        try {
            List<String> logstashConfList = logstashConfRepository.getAllHostanme();
            return clientUtil.successMessage(logstashConfList.toString());
        }catch (Exception e){
            return clientUtil.failMessage(e.toString());
        }
    }
}
