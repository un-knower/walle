package com.dashu.log.alterRules.multiThread;

import com.dashu.log.alter.WalleNotify;
import com.dashu.log.alterRules.ESIndexRule;
import com.dashu.log.classification.dao.ErrorLogTypeRepository;
import com.dashu.log.entity.ErrorLogType;
import com.dashu.log.filter.DocFilter;
import java.util.List;
import java.util.Map;

/**
 * @Description 日志分析线程
 * @Author: xuyouchang
 * @Date 2018/11/29 上午9:49
 **/
public class IndexMultiThread extends Thread {
    private List<Map> latestDoc;
    private ErrorLogTypeRepository errorLogTypeRepository;
    private DocFilter docFilter;
    private ESIndexRule esIndexRule;

    public IndexMultiThread (List<Map> latestDoc,ErrorLogTypeRepository errorLogTypeRepository,DocFilter docFilter,ESIndexRule esIndexRule){
        this.latestDoc = latestDoc;
        this.errorLogTypeRepository = errorLogTypeRepository;
        this.docFilter = docFilter;
        this.esIndexRule = esIndexRule;
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

            ErrorLogType errorLogType=this.esIndexRule.identifyErrorType(message);
            if (errorLogType!=null){        //判断是否新错误类型
                boolean flag = this.docFilter.isFilter(errorLogType,message);       //错误日志过滤
                if (flag){
                    continue;
                }else{
                    this.errorLogTypeRepository.updateMessage(message,errorLogType.getId());     //更新lastupdate_time
                    notify.sendMessage(topic,message);
                }
            }else{
                String keywords="";
                String space=" ";
                List<String> wordList = this.esIndexRule.splitString(message);
                for(int i=0;i<wordList.size();i++){
                    keywords=keywords+wordList.get(i)+space;
                }
                message=message.replace("\n","\\n");
                this.errorLogTypeRepository.addNewErrorLogType("",topic,loglevel,"",keywords,message,hostname);
                notify.sendMessage(topic,message);
            }
        }
    }
}
