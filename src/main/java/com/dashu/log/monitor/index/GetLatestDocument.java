package com.dashu.log.monitor.index;

import com.dashu.log.client.dao.IndexConfRepository;
import com.dashu.log.entity.IndexConf;

import com.dashu.log.monitor.dao.QueryHistoryRepository;
import com.dashu.log.util.ReadConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 获取监控index的最新document
 * @Author: xuyouchang
 * @Date 2018/11/22 下午7:06
 **/
@Service
public class GetLatestDocument {
    private static final Logger logger = LoggerFactory.getLogger(GetLatestDocument.class);

    @Resource
    private QueryHistoryRepository queryHistoryRepository;
    @Resource
    private IndexConfRepository indexConfRepository;

    /**
     * 获取最新document，并更新index的检测时间点
     *
     * @return
     */
    public List<Map> getLatestDoc(){
        List<IndexConf> indexConfList = indexConfRepository.getAllIndexConf();
        List<Map> latestDocMap = new ArrayList<>();
        ESQuery esQuery = new ESQuery();

        for (IndexConf indexConf: indexConfList){
            String index = indexConf.getIndex();
            String field = indexConf.getFiled();
            String keyword = indexConf.getKeywords();
            // 获取上次查询时间游标
            String timeCursor=queryHistoryRepository.findOldestTimestampByIndexName(index);
            if (timeCursor==null||timeCursor==""){
                timeCursor="2018-08-23T10:41:45.230Z";
                queryHistoryRepository.insertQueryHistory(index,timeCursor);
            }
            //获取es记录最新时间
            String latestTimestamp=esQuery.getLatestTime(index);
            List<Map> resultMap=esQuery.filterSearch(index,field,keyword,timeCursor);
            //更新时间游标
            queryHistoryRepository.updateOldTimestampByIndexName(latestTimestamp,index);
            if(resultMap.size()!=0){
                for(Map result: resultMap ){
                    latestDocMap.add(result);
                }
            }
        }
        return latestDocMap;


    }

}
