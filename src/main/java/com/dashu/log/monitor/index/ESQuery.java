package com.dashu.log.monitor.index;

import com.dashu.log.monitor.ESUtil;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description index查询相关操作
 * @Author: xuyouchang
 * @Date 2018/11/22 下午5:19
 **/
public class ESQuery {
    private static final Logger logger = LoggerFactory.getLogger(ESQuery.class);
    /**
     *
     * @return 获取最新时间
     * @throws IOException
     */
    public String getLatestTime(String index)  {
        ESUtil esUtil = new ESUtil();
        RestHighLevelClient client= esUtil.connect();
        SearchRequest searchRequest=new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.sort("@timestamp",SortOrder.DESC);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response= null;
        try {
            response = client.search(searchRequest);
            String lasttime=response.getHits().getHits()[0].getSourceAsMap().get("@timestamp").toString();
            client.close();
            return lasttime;
        } catch (IOException e) {
            logger.error("get latest time fail:"+e.toString());
            return null;
        }
    }

    /**
     * 关键字过滤查询
     * @param index
     * @param keyword
     * @return
     * @throws IOException
     */
    public  List<Map> filterSearch(String index, String field, String keyword, String timestamp) {
        if (index==null||index==""){
            logger.error("filter search option is failed, because lack of index");
            return null;
        }
        if (field==null||field==""){
            logger.error("filter search option is failed, because lack of field");
            return null;
        }
        if (keyword==null||keyword==""){
            logger.error("filter search option is failed, because lack of keyword");
            return null;
        }

        ESUtil esUtil = new ESUtil();
        RestHighLevelClient client=esUtil.connect();
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchPhraseQueryBuilder matchQueryBuilder=QueryBuilders.matchPhraseQuery(field,keyword);
        RangeQueryBuilder rangeQueryBuilder=QueryBuilders.rangeQuery("@timestamp").gt(timestamp);
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(matchQueryBuilder).must(rangeQueryBuilder));
        searchRequest.source(searchSourceBuilder);
        try{
            SearchResponse searchResponse = client.search(searchRequest);
            String scrollId = searchResponse.getScrollId();
            SearchHit[] searchHits = searchResponse.getHits().getHits();

            List<Map> mapList = new ArrayList<>();
            while (searchHits != null && searchHits.length > 0) {
                if (mapList.size()>100){
                    break;
                }
                for (SearchHit hit : searchHits) {
                    Map<String, Object> map = hit.getSourceAsMap();
                    mapList.add(map);
                }
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(scroll);
                searchResponse = client.searchScroll(scrollRequest);
                scrollId = searchResponse.getScrollId();
                searchHits = searchResponse.getHits().getHits();

            }
            client.close();
            return mapList;
        }catch (IOException e){
            logger.error("filter search request is failed !");
            return null;
        }

    }

}
