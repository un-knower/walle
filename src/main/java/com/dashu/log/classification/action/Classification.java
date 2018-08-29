package com.dashu.log.classification.action;

import com.dashu.log.Entity.ErrorLogType;
import com.dashu.log.classification.dao.ErrorLogTypeRepository;

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
public class Classification {
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
            ErrorLogType errorLogType=identifyErrorType(message);
            if(errorLogType!=null){
               Date lastUpdateTime= errorLogType.getLastUpdatetime();
               long timeInterval=lastUpdateTime.getTime()-curTime.getTime();
               if(timeInterval/1000/60>timeThreshold){
                   alterInfoList.add(errorLogType);
               }
            }else{
                String keywords="";
                String space=" ";
                List<String> wordList=splitString(message);
                for(int i=0;i<wordList.size();i++){
                   keywords=keywords+wordList.get(i)+space;
                }
                errorLogTypeRepository.addNewErrorLogType("","","","",keywords,message);
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
        double threshold=0.9;
        int beginIndex=0;
        int endIndex=50;
        if(message.length()>50){
            endIndex=50;
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
            double similarityRatio=positive/keywords.length;
            if (similarityRatio>threshold){
                //识别出已有异常,返回
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
        int endIndex=50;
        if(message.length()>50){
            endIndex=50;
        }else {
            endIndex=message.length();
        }
        String[] getInfo=message.substring(beginIndex,endIndex).split(" ");
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
