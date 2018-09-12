package com.dashu.log.alter;

import com.dashu.log.alter.dao.AlterHistoryRepository;
import com.dashu.log.classification.dao.FilterErrorTypeRepository;
import com.dashu.log.entity.ErrorLogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/9/6 上午10:32
 **/
@Service
public class AlterFilter {
    @Autowired
    private FilterErrorTypeRepository filterErrorTypeRepository;
    @Autowired
    private AlterHistoryRepository alterHistoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(AlterFilter.class);


    /**
     * 告警过滤
     * @param alterInfo
     * @return
     */
    public  boolean alterFilter(ErrorLogType alterInfo){
        //首先判断是否为空值
        if (alterInfo == null) {
            logger.info("alter info is null");
            return false;
        }
        //再判断错误类型是否被禁止
//        int[] forbidErrorType={368,374};
        List<Integer> forbidErrorType=filterErrorTypeRepository.findAllErrorTypeId();
        for(int id : forbidErrorType){
            if (id==alterInfo.getId()){
                logger.info("错误类型 "+id+" 已被禁止告警");
                return false;
            }
        }
        //其他过滤条件
        if (alterInfo.getMessage().length()<100){
            logger.info("告警信息过短");
            return false;
        }else{
            String altermessage=alterInfo.getMessage().split("\n")[0];
            alterHistoryRepository.addAlterHistory(alterInfo.getId(),alterInfo.getBusinessName(),alterInfo.getLogLevel(),alterInfo.getKeywords(),
                    alterInfo.getMessage(),alterInfo.getHostName());
            logger.info("walle 发出告警信息: "+altermessage);
            return true;
        }


    }
}
