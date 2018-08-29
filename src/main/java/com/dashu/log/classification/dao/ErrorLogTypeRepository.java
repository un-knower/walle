package com.dashu.log.classification.dao;

import com.dashu.log.Entity.ErrorLogType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/8/29 上午11:06
 **/
public interface ErrorLogTypeRepository extends JpaRepository<ErrorLogType,Long> {

    @Modifying
    @Transactional
    @Query(value = "insert into error_log_type(business_id,business_name,log_level,category,keywords,message) values (?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    void addNewErrorLogType(String business_id,String business_name,String log_level,String category,String keywords,String message);
}
