package com.dashu.log.monitor.action;

import com.dashu.log.monitor.EsQuery;
import com.dashu.log.monitor.dao.QueryHistoryRepository;
import com.dashu.log.monitor.util.ReadConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description 敏感信息监控主程序
 * @Author: xuyouchang
 * @Date 2018/8/27 上午10:56
 **/
@Service
public class Monitor {

    @Autowired
    private QueryHistoryRepository queryHistoryRepository;

    /**
     * 获取监控信息
     * @param ymlPath
     * @return
     * @throws IOException
     */
    public  List<Map> getMonitorInfo(String ymlPath) throws IOException {
        ReadConf readConf=new ReadConf();
        List<Map> confMap=readConf.readYml(ymlPath);
        List<Map> messageMap=new ArrayList<>();
        EsQuery esQuery=new EsQuery();

        for (Map map: confMap){
            String index=map.get("index").toString();
            String field=map.get("field").toString();
            String keyword=map.get("keyword").toString();
            // 从对应表中查出历史记录时间
            String oldTimestamp=queryHistoryRepository.findOldestTimestampByIndexName(index);
            if (oldTimestamp==null||oldTimestamp==""){
                oldTimestamp="2018-08-23T10:41:45.230Z";
                queryHistoryRepository.insertQueryHistory(index,oldTimestamp);
            }
            List<Map> resultMap=esQuery.filterSearch(index,field,keyword,oldTimestamp);
            List<String> oldTimestamplist=new ArrayList<>();
            if(resultMap.size()!=0){
                for(Map result: resultMap ){
                    messageMap.add(result);
                    oldTimestamplist.add(result.get("@timestamp").toString());
                }
                //更新时间
                queryHistoryRepository.updateOldTimestampByIndexName(Collections.max(oldTimestamplist),index);
            }
        }
        return messageMap;
    }

}
