package com.dashu.log.walle;

import com.dashu.log.monitor.dao.QueryHistoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WalleApplicationTests {
    @Autowired
    private QueryHistoryRepository queryHistoryRepository;


    @Test
    public void contextLoads() {
//        queryHistoryRepository.insertQueryHistory("hbase","2018-08-23T10:41:45.230Z");
        String str = "Hello I'm your String";
        String[] splited = str.split("\\s+");


    }

}
