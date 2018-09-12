package com.dashu.log.monitor;


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
     *
     * @return 获取最新时间
     * @throws IOException
     */
    public String getLatestTime() throws IOException {
        RestHighLevelClient client=connect();
        SearchRequest searchRequest=new SearchRequest("kafka");
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.sort("@timestamp",SortOrder.DESC);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response=client.search(searchRequest);
        String lasttime=response.getHits().getHits()[0].getSourceAsMap().get("@timestamp").toString();
        client.close();

        return lasttime;

    }

    /**
     * 关键字过滤查询
     * @param index
     * @param keyword
     * @return
     * @throws IOException
     */
    public static List<Map> filterSearch(String index,String field,String keyword,String timestamp) throws IOException {
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
        MatchPhraseQueryBuilder matchQueryBuilder=QueryBuilders.matchPhraseQuery(field,keyword);
        RangeQueryBuilder rangeQueryBuilder=QueryBuilders.rangeQuery("@timestamp").gt(timestamp);
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(matchQueryBuilder).must(rangeQueryBuilder));
        searchRequest.source(searchSourceBuilder);

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
//                System.out.println("xyc"+map.get("message"));
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
