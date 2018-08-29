package com.dashu.log.monitor.dao;

import com.dashu.log.Entity.ErrorLogType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/8/28 下午2:48
 **/
public interface ErrorLogTypeRepository extends JpaRepository<ErrorLogType,Long> {

}
