package com.dashu.log.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/12/6 下午8:01
 **/
public class DateUtils {
    public  Date parseGMT(String strDate) throws ParseException {
        if (strDate != null && strDate.trim().length() > 0) {
            strDate = strDate.substring(4,24).replace(" ","/");
            strDate = strDate.replace("Jan","01");
            strDate = strDate.replace("Feb","02");
            strDate = strDate.replace("Mar","03");
            strDate = strDate.replace("Apr","04");
            strDate = strDate.replace("May","05");
            strDate = strDate.replace("Jun","06");
            strDate = strDate.replace("Jul","07");
            strDate = strDate.replace("Aug","08");
            strDate = strDate.replace("Sep","09");
            strDate = strDate.replace("Oct","10");
            strDate = strDate.replace("Nov","11");
            strDate = strDate.replace("December","12");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
            Date date = sdf.parse(strDate);
            return date;
        }
        return null;
    }

}
