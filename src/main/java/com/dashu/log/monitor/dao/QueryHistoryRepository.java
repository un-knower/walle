package com.dashu.log.monitor.dao;

import com.dashu.log.Entity.QueryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 查询记录表相关操作
 * @Author: xuyouchang
 * @Date 2018/8/28 下午2:49
 **/
public interface QueryHistoryRepository extends JpaRepository<QueryHistory,Long> {

    @Modifying
    @Transactional
    @Query(value = "insert into query_history(index_name,oldest_time) values (?1,?2)",nativeQuery = true)
    void insertQueryHistory(String index,String newtimestamp);

    @Query(value = "select q.oldestTime from QueryHistory q where q.indexName=?1")
    String findOldestTimestampByIndexName(String indexName);

    //update或delete时必须使用@Modifying对方法进行注解
    @Modifying
    @Transactional
    @Query(value = "update QueryHistory q set q.oldestTime=?1 where q.indexName=?2")
    void updateOldTimestampByIndexName(String newtimestamp,String indexName);
}
