package com.dashu.log.client.dao;

import com.dashu.log.entity.FilterErrorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/27 下午3:23
 **/
public interface ErrorTypeIdRepository extends JpaRepository<FilterErrorType,Long> {

    /**
     * add filter error type id
     * @param errorTypeId
     */
    @Transactional
    @Modifying
    @Query(value = "insert into filter_error_type('error_type_id') values (?1)",nativeQuery = true)
    void addFilterErrorType(int errorTypeId);

    /**
     * get all filter error type id
     * @return
     */
    @Query(value = "select error_type_id from filter_error_type",nativeQuery = true)
    List<Integer> getAllFilterErrorId();

}
