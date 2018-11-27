package com.dashu.log.client;

import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/27 下午4:12
 **/
@Component
public class ClientUtil {

    public String successMessage(String data){

        return MessageFormat.format("['errorType': 0,'status': 'success','datas': {0}]",data);
    }

    public String nullMessage(){
        return "{'errorType': -1,'result': 'the message is null'}";
    }

    public String failMessage(String error){
        return MessageFormat.format("['errorType': 1,'status': 'fail','datas': {0}]",error);
    }
}
