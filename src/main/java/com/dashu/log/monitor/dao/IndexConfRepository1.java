package com.dashu.log.monitor.dao;

import com.dashu.log.entity.IndexConf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/26 上午10:49
 **/
public interface IndexConfRepository1 extends JpaRepository<IndexConf,Long> {

    @Query(value = "select t.id,t.filed,t.index,t.keywords from index_conf as t ",nativeQuery = true)
    List<IndexConf> getAllConf();

}
