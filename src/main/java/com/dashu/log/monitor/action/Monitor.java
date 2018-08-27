package com.dashu.log.monitor.action;

import com.dashu.log.monitor.dao.EsQuery;
import com.dashu.log.monitor.util.ReadConf;
import org.elasticsearch.client.RestHighLevelClient;

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
public class Monitor {
//    public static void main(String[] args) throws IOException {
//        String path="/Users/dashu/xyc/walle/src/main/java/com/dashu/log/monitor/monitor.yml";
//        getMonitorInfo(path);
//    }

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
            //todo 从对应表中查出最新时间
            String oldTimestamp="";
            List<Map> resultMap=esQuery.filterSearch(index,field,keyword,oldTimestamp);
            List<String> timelist=new ArrayList<>();
            for(Map result: resultMap ){
                messageMap.add(result);
                timelist.add(result.get("@timestamp").toString());
            }
            //todo 更新时间
            String newTimestamp=Collections.max(timelist);
        }
        return messageMap;
    }

}
