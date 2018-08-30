package com.dashu.log.monitor;

import com.dashu.log.monitor.dao.QueryHistoryRepository;
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
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description es相关查询操作
 * @Author: xuyouchang
 * @Date 2018/8/27 上午10:43
 **/

public class EsQuery {

    /**
     * 关键字过滤查询
     * @param index
     * @param keyword
     * @return
     * @throws IOException
     */
    public  List<Map> filterSearch(String index,String field,String keyword,String timestamp) throws IOException {
        if (index==null||index==""){
            index="kafka";
        }
        if (field==null||field==""){
            field="loglevel";
        }
        if (keyword==null||keyword==""){
            keyword="ERROR";
        }

        RestHighLevelClient client=connect();
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder=QueryBuilders.matchQuery(field,keyword);
        RangeQueryBuilder rangeQueryBuilder=QueryBuilders.rangeQuery("@timestamp").gt(timestamp);
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(matchQueryBuilder).must(rangeQueryBuilder));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest);
        String scrollId = searchResponse.getScrollId();
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        List<Map> mapList = new ArrayList<>();
        while (searchHits != null && searchHits.length > 0) {
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
    }

    /**
     * 连接es集群
     * @return
     */
    public static RestHighLevelClient connect(){
        //lowLevelClient用户授权访问
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "elastic"));
        //建立连接
        RestHighLevelClient client=new RestHighLevelClient(RestClient.builder(new HttpHost("es1", 9200))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }

                }).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback(){
                    @Override
                    public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                        //设置超时时间
                        return requestConfigBuilder.setConnectTimeout(500000)
                                .setSocketTimeout(600000);
                    }
                }).setMaxRetryTimeoutMillis(600000));
        return client;
    }


}
