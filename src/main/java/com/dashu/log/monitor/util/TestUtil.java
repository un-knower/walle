package com.dashu.log.monitor.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TestUtil {
    public static void main(String[] args){
//        String time="2018/09/04 15:07:57.063 [04/Sep/2018 12:57:53 +0800]";
//        String[] array=time.replace("[","").split(" ");
//        String pattern3="^(\\d\\d\\d\\d)/(\\d\\d)/(\\d\\d)$";
//        String pattern4="^(\\d\\d)/(\\S\\S\\S)/(\\d\\d\\d\\d)$";
//        for (int i=0;i<array.length;i++){
//            System.out.println("origin:"+array[i]);
//            if(Pattern.matches(pattern3,array[i])||Pattern.matches(pattern4,array[i])){
//                System.out.println(array[i]);
//            }
//
//        }
        int[] forbidErrorType={365,76};
        String test=forbidErrorType.toString();
        if (test.contains('"'+"76"+'"')){
            System.out.println("yes");
        }


    }
}
