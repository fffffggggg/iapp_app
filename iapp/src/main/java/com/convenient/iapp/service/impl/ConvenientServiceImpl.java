package com.convenient.iapp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.convenient.iapp.common.CommonConstant;
import com.convenient.iapp.dao.ConvenientDao;
import com.convenient.iapp.service.ConvenientService;
import com.convenient.iapp.util.HttpUtil;
import com.convenient.iapp.util.IdentityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ConvenientServiceImpl implements ConvenientService {

    private static String LOCATION_1 = "市辖区";
    private static String LOCATION_2 = "县";

    @Value("${api.juhe.phone.url}")
    private String phoneUrl;

    @Value("${api.juhe.phone.key}")
    private String phoneKey;

    @Value("${api.juhe.postcode.url}")
    private String postcodeUrl;

    @Value("${api.juhe.postcode.key}")
    private String postcodeKey;

    @Value("${api.juhe.constellation.url}")
    private String constellationUrl;

    @Value("${api.juhe.constellation.key}")
    private String constellationKey;

    @Autowired
    private ConvenientDao convenientDao;

    @Override
    public String getLocationByMobile(String requestData) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String mobile = requestMap.get("mobile");
            StringBuffer sb = new StringBuffer();
            sb.append(phoneUrl).append("?key=").append(phoneKey).append("&phone=").append(mobile);
            String url = sb.toString();
            String response = HttpUtil.doGet(url);
            //将response转为map 取出返回值 然后返回
            Map<String,Object> map = JSONObject.parseObject(response, Map.class);
            Object paramMapStr = map.get("result");
            if(!StringUtils.isEmpty(paramMapStr)){
                returnMap.put("data", paramMapStr);
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        } catch (Exception e) {
            log.error("getLocationByMobile", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    @Override
    public String getLocationByIdCard(String requestData) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String idCard = requestMap.get("idCard");
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
                    if(LOCATION_1.equals(str))
                        continue;
                    if(LOCATION_2.equals(str))
                        continue;
                    sb.append(str);
                }
                returnMap.put("data", sb);
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
                returnMap.put("msg", "请输入正确的身份证号");
            }
        } catch (Exception e) {
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
            log.error("getLocationByIdCard", e);
        } finally {
            return JSON.toJSONString(returnMap);
        }
    }


    @Override
    public String getLocationByZipCode(String requestData) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String zipCode = requestMap.get("zipCode");

            StringBuffer sb = new StringBuffer();
            String returnStr = null;
            sb.append(postcodeUrl).append("?key=").append(postcodeKey).append("&pageNo=1&pageSize=1&postcode=").append(zipCode);
            String url = sb.toString();
            String response = HttpUtil.doGet(url);
            //将response转为map 取出返回值 然后返回
            Map<String,Object> map = JSONObject.parseObject(response, Map.class);
            Object paramListStr = map.get("result");
            if (!StringUtils.isEmpty(paramListStr)) {
                Map<String, Object> postMap = JSONObject.parseObject(paramListStr.toString(), Map.class);
                List<Map<String, Object>> list = (List<Map<String, Object>>)postMap.get("list");

                if(!CollectionUtils.isEmpty(list)){
                    Map<String, Object> paramMap = list.get(0);
                    paramMap.remove("Address");
                    returnMap.put("data", paramMap);
                    returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
                }else{
                    returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
                }
            }
        } catch (Exception e) {
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
            log.error("getLocationByZipCode", e);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    @Override
    public String getFortuneByConstellation(String requestData) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String constellation = requestMap.get("constellation");
            String type = requestMap.get("type");
            String constellationStr = new String(CommonConstant.CONSTELLATION_MAP.get(constellation).getBytes("utf-8"),"gbk");
            StringBuffer sb = new StringBuffer();
            sb.append(constellationUrl).append("?key=").append(constellationKey).append("&consName=").append(constellationStr).append("&type=").append(type);
            String url = sb.toString();
            String response = HttpUtil.doGet(url);
            //将response转为map 取出返回值 然后返回
            Map<String,Object> map = JSONObject.parseObject(response, Map.class);

            if(!StringUtils.isEmpty(map)){
                returnMap.put("data", map);
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else {
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        } catch (Exception e) {
            log.error("getFortuneByConstellation", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }
}
