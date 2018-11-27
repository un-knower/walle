package com.dashu.log.client.dao;

import com.dashu.log.entity.IndexConf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/27 下午3:36
 **/
public interface IndexConfRepository extends JpaRepository<IndexConf,Long> {
    /**
     * get all index conf
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select t.id,t.index,t.filed,t.keywords from index_conf as t",nativeQuery = true)
    List<IndexConf> getAllIndexConf();
    /**
     * add index conf
     * @param index
     * @param filed
     * @param keywords
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into index_conf('index','filed','keywords') values (?1,?2,?3)",nativeQuery = true)
    void addIndexConf(String index,String filed,String keywords);
}
