package com.dashu.log.classification.dao;

import com.dashu.log.entity.ErrorLogType;
import com.dashu.log.monitor.ESUtil;

import com.dashu.log.util.HttpUtil;
import org.apache.http.client.methods.HttpGet;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 错误日志类型索引
 * @Author: xuyouchang
 * @Date 2018/12/4 上午10:32
 **/
public class ErrorLogTypeIndex {
    private static final Logger logger = LoggerFactory.getLogger(ErrorLogTypeIndex.class);
    private static final String INDEX = "error_log_type";
    private static final String FIELD = "Message:";
    private ErrorLogTypeIndex errorLogTypeIndex;
    private static final String URL = "http://elastic:elastic@es1:9200/"+INDEX+"/_search?q="+FIELD;

//    public static void main(String[] args) {
//        updateSingleField("WBzgd2cBT8lHxfPTXVmq","IsForbid","1");
//    }

    /**
     * 匹配相似日志
     * @param message
     * @return
     */
    public JSONObject getErrorLogType(String message){
        message = filterIllegalMassage(message);
        HttpUtil httpUtil = new HttpUtil(this);
        String url = this.URL+message;
        logger.error(url);
        try {
            String result = httpUtil.get(url);
            JSONObject jsonObject = new JSONObject(result);
            System.out.println(jsonObject.toString());
            JSONObject hitsObject = new JSONObject(jsonObject.get("hits").toString());
            return  hitsObject;
        } catch (IOException e) {
            logger.error(e.toString());
            return null;
        }
    }

    /**
     * update isFofbid
     * @param docId
     * @return
     */
    public  boolean updateSingleField(String docId,String field,String content){
        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put(field,content);
        UpdateRequest request = new UpdateRequest("error_log_type","doc",docId).doc(jsonMap);
        ESUtil esUtil = new ESUtil();
        RestHighLevelClient client = esUtil.connect();
        try {
            UpdateResponse response = client.update(request);
            client.close();
            return true;
        } catch (IOException e) {
            logger.error(e.toString());
            return false;
        }
    }

    /**
     * 插入error log type
     * @param errorLogType
     * @return
     */
    public boolean insertToIndex(ErrorLogType errorLogType){
        ESUtil esUtil = new ESUtil();
        RestHighLevelClient client = esUtil.connect();

        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put("CreatedTime",errorLogType.getCreatedTime());
        jsonMap.put("HostName",errorLogType.getHostname());
        jsonMap.put("IndexName",errorLogType.getIndexName());
        jsonMap.put("IsForbid",errorLogType.getIsForbid());
        jsonMap.put("LatestUpdateTime",errorLogType.getLatestTime());
        jsonMap.put("Message",errorLogType.getMessage());
        jsonMap.put("MonitorField",errorLogType.getMonitorField());

        IndexRequest indexRequest = new IndexRequest("error_log_type","doc").source(jsonMap);
        try {
            IndexResponse response = client.index(indexRequest);
            return true;
        } catch (IOException e) {
            logger.error(e.toString());
            return false;
        }

    }

    /**
     * es查询非法字符过滤
     * @param message
     * @return
     */
    public static String filterIllegalMassage(String message){
        String[] illegalChar = {"+","-","&&","||","!","(",")","{","}","^","~","*","?",":","\\","\""," ","[","]"};
        for (String s : illegalChar){
            message = message.replace(s,",");
        }
        return message;

    }
}
