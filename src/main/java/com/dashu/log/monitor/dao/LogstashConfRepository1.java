package com.dashu.log.monitor.dao;

import com.dashu.log.entity.LogstashConf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/26 下午5:33
 **/
public interface LogstashConfRepository1 extends JpaRepository<LogstashConf,Long> {

    @Query(value = "select t.id,t.hostname from logstash_conf as t",nativeQuery = true)
    List<LogstashConf> getAllHostname();
}
