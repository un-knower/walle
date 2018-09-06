package com.dashu.log.classification.action;

import com.dashu.log.entity.ErrorLogType;
import com.dashu.log.classification.dao.ErrorLogTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Description 消息分类主程序
 * @Author: xuyouchang
 * @Date 2018/8/28 上午10:55
 **/
@Service
public class Classification {
    private static final Logger logger = LoggerFactory.getLogger(Classification.class);
    @Resource
    private ErrorLogTypeRepository errorLogTypeRepository;

    /**
     * 告警信息
     * @param messageMap
     * @return
     */
    public List<ErrorLogType> alterInfo(List<Map> messageMap){
        List<ErrorLogType> alterInfoList=new ArrayList<>();
        Date curTime = new Date(System.currentTimeMillis());//获取当前时间
        int timeThreshold=25500;
        for(Map map:messageMap){
            String message=map.get("message").toString();
            String loglevel=map.get("loglevel").toString();
            Map host=(Map)map.get("host");
            String hostname=host.get("name").toString();
            Map<String,Object> fields=(Map)map.get("fields");
            String topic=fields.get("log_topic").toString();
            ErrorLogType errorLogType=identifyErrorType(message);
            if(errorLogType!=null){
               Date lastUpdateTime= errorLogType.getLastUpdatetime();
               long timeInterval=curTime.getTime()-lastUpdateTime.getTime();
               if(timeInterval/1000>timeThreshold){
                   alterInfoList.add(errorLogType);
                   //更新lastupdate_time
                   errorLogTypeRepository.updateMessage(message,errorLogType.getId());
               }
            }else{
                String keywords="";
                String space=" ";
                List<String> wordList=splitString(message);
                for(int i=0;i<wordList.size();i++){
                   keywords=keywords+wordList.get(i)+space;
                }
                message=message.replace("\n","\\n");
                errorLogTypeRepository.addNewErrorLogType("",topic,loglevel,"",keywords,message,hostname);
                alterInfoList.add(errorLogType);
            }

        }
        return alterInfoList;
    }

    /**
     * 识别信息是否为已知类型
     * @param message
     * @return
     */
    public ErrorLogType identifyErrorType(String message){
        double threshold=0.7;
        String comparedMessage=message.split("\n")[0];
        List<ErrorLogType> errorLogTypesList=errorLogTypeRepository.findAll();

        //查看是否是已存在异常
        for(ErrorLogType errorLogType: errorLogTypesList){
            String[] keywords=errorLogType.getKeywords().split(" ");
            int positive=0;
            int negative=0;
            for(int i=0;i<keywords.length;i++){
                    if(filterInvalidWord(keywords[i])==false){
                        negative++;
                    }else{
                        if(comparedMessage.contains(keywords[i])) {
                            positive++;
                        }
                    }

            }
            double similarityRatio=(double) positive/(keywords.length-negative);
            if (similarityRatio>threshold){
                //识别出已有异常,返回
                logger.info("相似度："+similarityRatio);
                logger.info("已有类型:"+errorLogType.getKeywords());
                return errorLogType;
            }
        }
        return null;
    }

    /**
     * 关键信息提取与分词
     * @param message
     * @return
     */
    public List<String> splitString(String message){
        List<String> wordList=new ArrayList<>();
        String keywords=message.split("\n")[0];
        String[] getInfo=keywords.split(" ");
        for(int i=0;i<getInfo.length;i++){
                wordList.add(getInfo[i]);
        }
        return wordList;
    }

    /**
     * 过滤无效字符
     * @param words
     * @return
     */
    public static boolean filterInvalidWord(String words){
        //替换特殊字符
        words=words.replace("[","");
        words=words.replace("]","");
        words=words.replace("+","");

        String pattern="^(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)$";
        String pattern2="^(\\d\\d):(\\d\\d):(\\d\\d),(\\d\\d\\d|\\d\\d)$";
        String pattern3="^(\\d\\d\\d\\d)/(\\d\\d)/(\\d\\d)$";
        String pattern4="^(\\d\\d)/(\\S\\S\\S)/(\\d\\d\\d\\d)$";
        String pattern5="^(\\d\\d\\d\\d)$";
        if (Pattern.matches(pattern,words)||Pattern.matches(pattern2,words)
                ||Pattern.matches(pattern3,words)||Pattern.matches(pattern4,words)
                ||Pattern.matches(pattern5,words)){
            logger.info("过滤字符：日期字符");
            return false;
        }
        if (words=="ERROR"||words=="error"){
            logger.info("过滤字符：error 关键字");
            return false;
        }else {
            return true;
        }
    }

}
