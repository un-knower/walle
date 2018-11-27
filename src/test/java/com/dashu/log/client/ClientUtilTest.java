package com.dashu.log.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/27 下午5:01
 **/
class ClientUtilTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void successMessage() {
        ClientUtil clientUtil = new ClientUtil();
        String meg = clientUtil.successMessage("syx");
        System.out.println(meg);
    }

    @Test
    void nullMessage() {
    }

    @Test
    void failMessage() {
    }
}