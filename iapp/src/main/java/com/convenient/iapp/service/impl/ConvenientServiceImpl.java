package com.convenient.iapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.convenient.iapp.dao.ConvenientDao;
import com.convenient.iapp.service.ConvenientService;
import com.convenient.iapp.util.HttpUtil;
import com.convenient.iapp.util.IdentityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ConvenientServiceImpl implements ConvenientService {

    private final static String HTTP_PRE = "http://api.avatardata.cn/";

    @Value("${avatardata.bignum.key}")
    private String bigNumKey;

    @Value("${avatardata.chengyu.key}")
    private String chengYuKey;

    @Value("${avatardata.express.key}")
    private String expressKey;

    @Value("${avatardata.zipcode.key}")
    private String zipCodeKey;

    @Value("${avatardata.mobile.key}")
    private String mobileKey;

    @Autowired
    private ConvenientDao convenientDao;

    @Override
    public String getLocationByMobile(String mobile) {
        try {
            StringBuffer sb = new StringBuffer();
            String returnStr = null;
            sb.append(HTTP_PRE).append("MobilePlace/LookUp?key=").append(mobileKey).append("&mobileNumber=").append(mobile);
            String url = sb.toString();
            String response = HttpUtil.doGet(url);
            //将response转为map 取出返回值 然后返回
            Map<String,Object> map = JSONObject.parseObject(response, Map.class);
            Object paramMapStr = map.get("result");
            if(!StringUtils.isEmpty(paramMapStr)){
                Map<String, Object> pMap = JSONObject.parseObject(paramMapStr.toString(), Map.class);
                if(!StringUtils.isEmpty(pMap.get("mobilearea"))) {
                    returnStr = pMap.get("mobilearea").toString();
                }
            }
            //将response转为map 取出返回值 然后返回
            return returnStr;
        } catch (Exception e) {
            log.error("getLocationByMobile", e);
        }
        return null;
    }

    @Override
    public String getLocationByIdCard(String idCard) {
        try {
            if(IdentityUtil.isLegalPattern(idCard)){
                StringBuilder sb = new StringBuilder();
                String idCardKey1 = idCard.substring(0, 2);
                String idCardKey2 = idCard.substring(0, 4);
                String idCardKey3 = idCard.substring(0, 6);
                Map<String, String> queryMap = new HashMap<String, String>();
                queryMap.put("idCardKey1",idCardKey1);
                queryMap.put("idCardKey2",idCardKey2);
                queryMap.put("idCardKey3",idCardKey3);
                List<String> idCardLocationList = convenientDao.getIdCardInfo(queryMap);
                for(String str : idCardLocationList){
                    sb.append(str);
                }
                return sb.toString();
            }else{
                return "请输入正确身份证号！";
            }
        } catch (Exception e) {
            log.error("getLocationByIdCard", e);
        }
        return null;
    }

    @Override
    public String getLocationByExpress(String expressCom, String expressNum) {
        StringBuffer sb = new StringBuffer();
        String returnStr = null;
        sb.append(HTTP_PRE).append("ExpressNumber/Lookup?key=").append(expressKey).append("&company=").append(expressCom).append("&id").append(expressNum);
        String url = sb.toString();
        String response = HttpUtil.doGet(url);
        return returnStr;
    }

    @Override
    public String getLocationByZipCode(String zipCode) {
        try {
            StringBuffer sb = new StringBuffer();
            String returnStr = null;
            sb.append(HTTP_PRE).append("PostNumber/QueryPostnumber?key=").append(zipCodeKey).append("&rows=1&postnumber=").append(zipCode);
            String url = sb.toString();
            String response = HttpUtil.doGet(url);
            //将response转为map 取出返回值 然后返回
            Map<String,Object> map = JSONObject.parseObject(response, Map.class);
            Object paramListStr = map.get("result");
            if (!StringUtils.isEmpty(paramListStr)) {
                List<Map<String, Object>> list = JSONObject.parseObject(paramListStr.toString(), List.class);
                for(Map<String, Object> pMap : list){
                    if(!StringUtils.isEmpty(pMap.get("jd")))
                        returnStr = pMap.get("jd").toString();
                }
            }
            return returnStr;
        } catch (Exception e) {
            log.error("getLocationByZipCode", e);
        }
        return null;
    }

    @Override
    public String getBigNumByNumber(String number) {
        try {
            StringBuffer sb = new StringBuffer();
            String returnStr = null;
            sb.append(HTTP_PRE).append("CnMoney/Convert?key=").append(bigNumKey).append("&money=").append(number);
            String url = sb.toString();

            String response = HttpUtil.doGet(url);
            //将response转为map 取出返回值 然后返回
            Map<String,Object> map = JSONObject.parseObject(response, Map.class);
            Object result = map.get("result");
            if (!StringUtils.isEmpty(result)) {
                returnStr = result.toString();
            }
            return returnStr;
        } catch (Exception e) {
            log.error("getBigNumByNumber", e);
        }
        return null;
    }

    @Override
    public String getIdiomBySingleWord(String word) {
        try {
            StringBuffer sb = new StringBuffer();
            String returnStr = null;
            sb.append(HTTP_PRE).append("ChengYu/Search?key=").append(chengYuKey).append("&rows=10&keyWord=").append(word);
            String url = sb.toString();
            String response = HttpUtil.doGet(url);
            //将response转为map 取出返回值 然后返回
            Map<String,Object> map = JSONObject.parseObject(response, Map.class);
            Object paramListStr = map.get("result");
            if (!StringUtils.isEmpty(paramListStr)) {
                sb = new StringBuffer();
                List<Map<String, Object>> list = JSONObject.parseObject(paramListStr.toString(), List.class);
                for(Map<String, Object> pMap : list){
                    sb.append(pMap.get("name")).append(";");
                }
            }
            if(!StringUtils.isEmpty(sb)){
                returnStr = sb.toString().substring(0, sb.toString().length() - 1);
            }
            return returnStr;
        } catch (Exception e) {
            log.error("getIdiomBySingleWord", e);
        }
        return null;
    }
}
