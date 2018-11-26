package com.dashu.log.walle;

import com.dashu.log.alter.IndexAlter;
import com.dashu.log.entity.IndexConf;
import com.dashu.log.monitor.dao.IndexConfRepository;
import com.dashu.log.monitor.dao.QueryHistoryRepository;
import com.dashu.log.monitor.filebeat.FilebeatDetection;
import com.dashu.log.monitor.logstash.LogstashDetect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WalleApplicationTests {
    @Autowired
    private QueryHistoryRepository queryHistoryRepository;
    @Resource
    private IndexConfRepository indexConfRepository;


    @Test
    public void contextLoads() {
        LogstashDetect logstashDetect = new LogstashDetect("es1");
        logstashDetect.isAlive();
    }

}
