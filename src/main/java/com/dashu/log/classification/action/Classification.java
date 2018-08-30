package com.dashu.log.classification.action;

import com.dashu.log.entity.ErrorLogType;
import com.dashu.log.classification.dao.ErrorLogTypeRepository;
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
    @Resource
    private ErrorLogTypeRepository errorLogTypeRepository;

    /**
     * 告警信息
     * @param messageMap
     * @return
     */
    public List<ErrorLogType> alterInfo(List<Map> messageMap){
        List<ErrorLogType> alterInfoList=new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        Date curTime = new Date(System.currentTimeMillis());//获取当前时间
        int timeThreshold=5;
        for(Map map:messageMap){
            String message=map.get("message").toString();
            String loglevel=map.get("loglevel").toString();
            String businessName=map.get("source").toString();
            ErrorLogType errorLogType=identifyErrorType(message);
            if(errorLogType!=null){
               Date lastUpdateTime= errorLogType.getLastUpdatetime();
               long timeInterval=curTime.getTime()-lastUpdateTime.getTime();
               if(timeInterval/1000/60>timeThreshold){
                   alterInfoList.add(errorLogType);
                   //更新lastupdate_time
                   errorLogTypeRepository.updateMessage(message,errorLogType.getCategory());
               }
            }else{
                String keywords="";
                String space=" ";
                List<String> wordList=splitString(message);
                for(int i=0;i<wordList.size();i++){
                   keywords=keywords+wordList.get(i)+space;
                }
                errorLogTypeRepository.addNewErrorLogType("",businessName,loglevel,wordList.get(0),keywords,message);
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
        int beginIndex=0;
        int endIndex=300;
        if(message.length()>300){
            endIndex=300;
        }else {
            endIndex=message.length();
        }
        String getInfo=message.substring(beginIndex,endIndex);
        List<ErrorLogType> errorLogTypesList=errorLogTypeRepository.findAll();
        //查看是否是已存在异常
        for(ErrorLogType errorLogType: errorLogTypesList){
            String[] keywords=errorLogType.getKeywords().split(" ");
            int positive=0;
            for(int i=0;i<keywords.length;i++){
                if(getInfo.contains(keywords[i])){
                    positive++;
                }
            }
            double similarityRatio=(double) positive/keywords.length;
            if (similarityRatio>threshold){
                //识别出已有异常,返回
                System.out.println("已有类型");
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
        int beginIndex=0;
        int endIndex=300;
        if(message.length()>300){
            endIndex=300;
        }else {
            endIndex=message.length();
        }
        message=message.substring(beginIndex,endIndex);
        String[] getInfo=message.split("\\s+");
        String pattern="^(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)$";
        String pattern2="^(\\d\\d):(\\d\\d):(\\d\\d),(\\d\\d\\d|\\d\\d)$";
        for(int i=0;i<getInfo.length;i++){
            if(Pattern.matches(pattern,getInfo[i])||Pattern.matches(pattern2,getInfo[i])){
                System.out.println("filter word"+getInfo[i]);
            }else{
                wordList.add(getInfo[i]);
            }
        }
        return wordList;
    }

}
