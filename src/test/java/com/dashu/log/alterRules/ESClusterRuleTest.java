package com.dashu.log.alterRules;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/27 下午2:42
 **/
class ESClusterRuleTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isReject() {
        ESClusterRule esClusterRule = new ESClusterRule();
        esClusterRule.isReject();
    }

    @Test
    void isHealthRule() {
    }
}