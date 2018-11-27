package com.dashu.log.monitor.cluster;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/27 上午11:31
 **/
class ClusterTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void reject() {
        Cluster cluster = new Cluster();
        String ret = cluster.reject();
        System.out.println(ret);
    }

    @Test
    void isHealth() {
    }
}