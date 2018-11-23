package com.dashu.log.walle;

import com.dashu.log.alter.IndexAlter;
import com.dashu.log.monitor.dao.QueryHistoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WalleApplicationTests {
    @Autowired
    private QueryHistoryRepository queryHistoryRepository;
    @Resource
    private IndexAlter indexAlter;


    @Test
    public void contextLoads() {

    }

}
