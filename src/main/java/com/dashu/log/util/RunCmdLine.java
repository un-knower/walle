package com.dashu.log.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description java执行shell命令
 * @Author: xuyouchang
 * @Date 2018/11/21 下午2:00
 **/
public class RunCmdLine {
    private static Logger logger = null;

    public RunCmdLine(Object o){
        logger = LoggerFactory.getLogger(o.getClass());
    }

    /**
     * 执行shell命令并获得返回结果
     * @param cmd
     */
    public List callShell(String cmd){
        List<String> processList = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                processList.add(line);
                logger.info("run command "+cmd+" return:"+line);
            }
            input.close();
            return processList;
        } catch (IOException e) {
            logger.info("run command "+cmd+" fail\n"+e.toString());
            return null;
        }
    }
}
