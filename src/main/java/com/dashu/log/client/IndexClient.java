package com.dashu.log.client;


import com.dashu.log.client.dao.ErrorTypeIdRepository;
import com.dashu.log.client.dao.IndexConfRepository;
import com.dashu.log.entity.ErrorLogType;
import com.dashu.log.entity.IndexConf;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description index client
 * @Author: xuyouchang
 * @Date 2018/11/27 下午3:18
 **/
@RestController
public class IndexClient {

    @Resource
    private IndexConfRepository indexConfRepository;
    @Resource
    private ClientUtil clientUtil;
    @Resource
    private ErrorTypeIdRepository errorTypeIdRepository;


    /**
     * get All Index Conf
     * @return
     */
    @RequestMapping(value = "/index/getAllIndexConf",method = RequestMethod.GET)
    public String getAllIndexConf(){
        try {
            List<IndexConf> indexConfList = indexConfRepository.getAllIndexConf();
            JSONArray datas = new JSONArray();
            for (IndexConf indexConf : indexConfList){
                JSONObject data = new JSONObject();
                data.put("index",indexConf.getIndex());
                data.put("filed",indexConf.getFiled());
                data.put("keywords",indexConf.getKeywords());
                datas.put(data);
            }
            return clientUtil.successMessage(datas.toString());
        }catch (Exception e){
            return clientUtil.failMessage(e.toString());
        }

    }

    /**
     * add Index Conf
     * @param index
     * @param filed
     * @param keywords
     * @return
     */
    @RequestMapping(value = "/index/addIndexConf",method = RequestMethod.GET)
    public String addIndexConf(@RequestParam(value = "index")String index,
                               @RequestParam(value = "filed")String filed,
                               @RequestParam(value = "keywords")String keywords){
        if (index == null || filed == null || keywords == null){
            return clientUtil.nullMessage();
        }
        try{
            indexConfRepository.addIndexConf(index,filed,keywords);
            String data = index+" "+filed+" "+keywords;
            return clientUtil.successMessage(data);
        }catch (Exception e){
            return clientUtil.failMessage(e.toString());
        }


    }

    /**
     * add Forbid Id
     * @param errorTypeId
     * @return
     */
    @RequestMapping(value = "/index/addForbidId",method = RequestMethod.GET)
    public String addForbidId(@RequestParam(value = "errorTypeId")Integer errorTypeId){
        if (errorTypeId == null){
            return clientUtil.nullMessage();
        }
        try{
            errorTypeIdRepository.addFilterErrorType(errorTypeId);
            return clientUtil.successMessage(errorTypeId.toString());
        }catch (Exception e){
            return clientUtil.failMessage(e.toString());
        }

    }

    /**
     * get All Forbid Id
     * @return
     */
    @RequestMapping(value = "/index/getAllForbidId",method = RequestMethod.GET)
    public String getAllForbidId(){
        try{
            List<Integer> errorLogTypeList = errorTypeIdRepository.getAllFilterErrorId();
            return clientUtil.successMessage(errorLogTypeList.toString());
        }catch (Exception e){
            return clientUtil.failMessage(e.toString());
        }
    }

}
