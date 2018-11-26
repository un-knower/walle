package com.dashu.log.monitor.dao;

import com.dashu.log.entity.FilebeatConf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/26 上午10:50
 **/
public interface FileBeatConfRepository extends JpaRepository<FilebeatConf,Long> {

    @Query(value = "select t.id,t.hostname from filebeat_conf as t",nativeQuery = true)
    List<FilebeatConf> getAllHostname();
}
