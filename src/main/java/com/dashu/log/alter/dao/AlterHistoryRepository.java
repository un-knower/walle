package com.dashu.log.alter.dao;

import com.dashu.log.entity.AlterHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/9/7 上午10:46
 **/
public interface AlterHistoryRepository extends JpaRepository<AlterHistory,Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into alter_history(business_id, business_name, log_level, keywords, message, host_name) values (?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    void addAlterHistory(Integer business_id, String business_name, String log_level, String keywords, String message, String host_name);
}
