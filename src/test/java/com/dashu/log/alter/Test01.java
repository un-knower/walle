package com.dashu.log.alter;

import com.dashu.log.monitor.ESUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/12/6 下午6:37
 **/
public class Test01 {
    public static void main(String[] args) {
        String message = "2018-12-06 15:31:52,554 ERROR org.apache.hadoop.hive.ql.exec.Task: [Thread-28317]: Examining task ID: task_1541498229315_240388_r_000102 (and more";
        ESUtil esUtil = new ESUtil();
        RestHighLevelClient client = esUtil.connect();
        SearchRequest request = new SearchRequest("error_log_type");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("Message", message)
                .fuzziness(Fuzziness.AUTO);
        sourceBuilder.query(matchQueryBuilder);
        request.source(sourceBuilder);

        try {
            SearchResponse response = client.search(request);
            SearchHits hits = response.getHits();
            SearchHit hit = hits.getHits()[0];
            Map source =hit.getSourceAsMap();
            System.out.println(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
