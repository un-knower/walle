package com.dashu.log.monitor.util;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * @Description 读取与校验日志监控项的配置文件
 * @Author: xuyouchang
 * @Date 2018/8/27 上午11:29
 **/
public class ReadConf {

    /**
     * MD5校验原配置文件是否发生改变
     * @param ymlPath
     * @return
     */
    public boolean checkYml(String ymlPath){
        //todo 查询原始配置文件的MD5
        String oldMD5="";
        MD5Util md5Util=new MD5Util();
        String newMD5=md5Util.getMD5String(ymlPath);
        return md5Util.checkPassword(oldMD5,newMD5);
    }

    /**
     * 读取YML配置文件
     * @param ymlPath
     * @return
     */
    public List<Map> readYml(String ymlPath) {
        Yaml yaml = new Yaml();
        File f=new File(ymlPath);
        //读入文件
        Object result= null;
        try {
            result = yaml.load(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            System.out.println("please check your yaml file");
        }
        System.out.println(result);
        List<Map> resultAsListMap=(List<Map>) result;
        return resultAsListMap;
    }


}
