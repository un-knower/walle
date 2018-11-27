package com.dashu.log.client.dao;

import com.dashu.log.entity.FilebeatConf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/27 下午3:31
 **/
public interface FilebeatConfRepository extends JpaRepository<FilebeatConf,Long> {
    /**
     * add filebeat host
     * @param hostname
     */
    @Transactional
    @Modifying
    @Query(value = "insert into filebeat_conf('hostname') values (?1)",nativeQuery = true)
    void addHostname(String hostname);

    /**
     * get all hostname
     * @return
     */
    @Query(value = "select hostname from filebeat_conf",nativeQuery = true)
    List<String> getAllHostname();
}
