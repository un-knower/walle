package com.dashu.log.alterRules.multiThread;

import com.dashu.log.alter.WalleNotify;
import com.dashu.log.alterRules.ESIndexRule;
import com.dashu.log.classification.dao.ErrorLogTypeIndex;
import com.dashu.log.classification.dao.ErrorLogTypeRepository;
import com.dashu.log.entity.ErrorLogType;
import com.dashu.log.filter.DocFilter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 日志分析线程
 * @Author: xuyouchang
 * @Date 2018/11/29 上午9:49
 **/
public class IsErrorMultiThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(IsErrorMultiThread.class);
    private List<Map> latestDoc;
    private ErrorLogTypeRepository errorLogTypeRepository;
    private DocFilter docFilter;
    private ESIndexRule esIndexRule;
    private Integer SCORE_THRESHOLD;

    public IsErrorMultiThread(List<Map> latestDoc, ErrorLogTypeRepository errorLogTypeRepository, DocFilter docFilter, ESIndexRule esIndexRule, Integer scoreThreshold){
        this.latestDoc = latestDoc;
        this.errorLogTypeRepository = errorLogTypeRepository;
        this.docFilter = docFilter;
        this.esIndexRule = esIndexRule;
        this.SCORE_THRESHOLD = scoreThreshold;
    }

    public void run(){
        WalleNotify notify = new WalleNotify();
        for (Map map : this.latestDoc){
            String message=map.get("message").toString();
            String loglevel=map.get("loglevel").toString();
            Map host=(Map)map.get("host");
            String hostname=host.get("name").toString();
            Map<String,Object> fields=(Map)map.get("fields");
            String topic=fields.get("log_topic").toString();

            ErrorLogTypeIndex errorLogTypeIndex = new ErrorLogTypeIndex();
            JSONObject hitsObject = errorLogTypeIndex.getErrorLogType(message);
            Integer maxScore;
            if (hitsObject.get("max_score").toString() == "null"){
                 maxScore = -1;
            }else{
                 maxScore = Integer.parseInt(hitsObject.get("max_score").toString());
            }
            if (maxScore <= this.SCORE_THRESHOLD){
                logger.warn("maxSocre is "+maxScore);
                message=message.replace("\n","\\n");
                ErrorLogType errorLogType = new ErrorLogType(topic,message,loglevel,hostname,0,new Date(),new Date());
                errorLogTypeIndex.insertToIndex(errorLogType);
                notify.sendMessage(topic,message);

            }else {
                logger.info("maxscore is "+maxScore);
                JSONArray hitArray = new JSONArray(hitsObject.get("hits").toString());
                JSONObject docObject = new JSONObject(hitArray.get(0).toString());
                String docId = docObject.getString("_id");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date createdTime = sdf.parse(docObject.getString("CreatedTime"));
                    Date latestTime = sdf.parse(docObject.getString("LatestUpdateTime"));
                    ErrorLogType errorLogType = new ErrorLogType(docObject.getString("_index"),docObject.getString("Message"),docObject.getString("MonitorField"),
                            docObject.getString("HostName"),docObject.getInt("IsForbid"),createdTime,latestTime);
                    boolean flag = this.docFilter.isFilter(errorLogType,message);
                    if (flag){
                        continue;
                    }else {
                        Date date = new Date();
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        errorLogTypeIndex.updateSingleField(docId,"LatestUpdateTime",sdf.format(date));
                        message = "docId:"+docId+"\\n"+message.replace("\n","\\n");
                        notify.sendMessage(topic,message);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

//            ErrorLogTypeBak errorLogType=this.esIndexRule.identifyErrorType(message);
//            if (errorLogType!=null){        //判断是否新错误类型
//                boolean flag = this.docFilter.isFilter(errorLogType,message);       //错误日志过滤
//                if (flag){
//                    continue;
//                }else{
//                    this.errorLogTypeRepository.updateMessage(message,errorLogType.getId());     //更新lastupdate_time
//                    notify.sendMessage(topic,message);
//                }
//            }else{
//                String keywords="";
//                String space=" ";
//                List<String> wordList = this.esIndexRule.splitString(message);
//                for(int i=0;i<wordList.size();i++){
//                    keywords=keywords+wordList.get(i)+space;
//                }
//                message=message.replace("\n","\\n");
//                this.errorLogTypeRepository.addNewErrorLogType("",topic,loglevel,"",keywords,message,hostname);
//                notify.sendMessage(topic,message);
//            }
        }
    }
}
