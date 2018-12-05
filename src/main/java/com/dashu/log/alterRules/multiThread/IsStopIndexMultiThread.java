package com.dashu.log.alterRules.multiThread;

import com.dashu.log.alter.WalleNotify;
import com.dashu.log.client.dao.IndexConfRepository;
import com.dashu.log.entity.IndexConf;
import com.dashu.log.util.HttpUtil;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/12/5 下午3:29
 **/
public class IsStopIndexMultiThread extends Thread {
    private Thread t;
    private static Logger logger = LoggerFactory.getLogger(IsStopIndexMultiThread.class);
    private IndexConf INDEX_CONF;
    private String INDEX_NAME;
    private String BASE_URL = "http://elastic:elastic@es1:9200/";
    private IndexConfRepository indexConfRepository;

    public IsStopIndexMultiThread (IndexConf indexConf,IndexConfRepository indexConfRepository){
        this.INDEX_CONF = indexConf;
        this.INDEX_NAME = indexConf.getIndex();
        this.indexConfRepository = indexConfRepository;
    }

    public void run(){
        HttpUtil httpUtil = new HttpUtil(this);
        String url = this.BASE_URL+this.INDEX_NAME+"/_stats/indexing";
        try {
            String result = httpUtil.get(url);
            JSONObject resultObject = new JSONObject(result);
            JSONObject allObject = new JSONObject(resultObject.get("_all").toString());
            JSONObject totalObject = new JSONObject(allObject.get("total").toString());
            JSONObject indexingObject = new JSONObject(totalObject.get("indexing").toString());
            Integer index_total = indexingObject.getInt("index_total");

            Date curTime = new Date();
            long scan_time = Long.valueOf(this.INDEX_CONF.getScanTime()).longValue();
            logger.info("curtime:"+curTime.getTime()+" scantime:"+scan_time);
            long timeInterval;
            if (scan_time!=0){
                timeInterval = (curTime.getTime()-scan_time)/1000/60;
                System.out.println(timeInterval);
                if (timeInterval>this.INDEX_CONF.getScanInterval()){                //判断距上次扫描时间间隔
                    indexConfRepository.updateScanTime(String.valueOf(curTime.getTime()),this.INDEX_NAME);
                    int diff = index_total - this.INDEX_CONF.getIndexTotal();      //判断index中的文档数是否增加
                    if (diff <= 0){
                        WalleNotify notify = new WalleNotify();
                        notify.sendMessage("stop indexing",this.INDEX_NAME+" is stop indexing");
                    }else {
                        indexConfRepository.updateIndexTotalNum(index_total,this.INDEX_NAME);   //更新文档数
                    }
                }
            }else {
                indexConfRepository.updateScanTime(String.valueOf(curTime.getTime()),this.INDEX_NAME);
                int diff = index_total - this.INDEX_CONF.getIndexTotal();      //判断index中的文档数是否增加
                if (diff <= 0){
                    WalleNotify notify = new WalleNotify();
                    notify.sendMessage("stop indexing",this.INDEX_NAME+" is stop indexing");
                }else {
                    indexConfRepository.updateIndexTotalNum(index_total,this.INDEX_NAME);   //更新文档数
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start () {
        logger.info("the thread "+this.INDEX_NAME+" is start");
        if (t == null) {
            t = new Thread (this, this.INDEX_NAME);
            t.start ();
        }
    }
}
