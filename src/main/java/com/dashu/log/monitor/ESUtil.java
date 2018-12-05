package com.dashu.log.monitor;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/12/4 上午10:35
 **/
public class ESUtil {

    /**
     * 连接es集群
     * @return
     */
    public RestHighLevelClient connect(){
        //lowLevelClient用户授权访问
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "elastic"));
        //建立连接
        RestHighLevelClient client=new RestHighLevelClient(RestClient.builder(new HttpHost("es1", 9200),new HttpHost("es2",9200),new HttpHost("es3",9200))
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
