package com.dashu.log;

import com.dashu.log.monitor.action.Monitor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description 主程序入口
 * @Author: xuyouchang
 * @Date 2018/8/27 下午5:28
 **/
public class MainExe {
    //YML配置文件路径
    private static final String YML_PATH="/Users/dashu/xyc/walle/src/main/java/com/dashu/log/monitor/monitor.yml";

    public static void main(String[] args) throws IOException {
        Monitor monitor=new Monitor();
        //获取关键监控信息
        List<Map> message=monitor.getMonitorInfo(YML_PATH);
        //分类

    }
}
