package com.convenient.iapp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.convenient.iapp.common.CommonConstant;
import com.convenient.iapp.service.ToolService;
import com.convenient.iapp.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ToolServiceImpl implements ToolService {


    @Value("${qrcode.save.path}")
    private String createPath;

    @Value("${qrcode.read.path}")
    private String readPath;

    /**
     * 数字转化为大写
     * @param requestData
     * @return
     */
    @Override
    public String getNumConvert(String requestData) {
        log.info("numconvert : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String numberStr = requestMap.get("number");
            if(!StringUtils.isEmpty(numberStr)){
                double number = Double.valueOf(numberStr);
                NumberConvertUtil nc = new NumberConvertUtil();
                String returnStr = nc.transComb(number);
                returnMap.put("data", returnStr);
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        }catch (Exception e){
            log.error("getNumConvert", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    /**
     * MD5 签名
     * @param requestData
     * @return
     */
    @Override
    public String getMd5Sign(String requestData) {
        log.info("md5 : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String str = requestMap.get("str");
            if(!StringUtils.isEmpty(str)){
                String returnStr = Md5Util.encrype(str);
                returnMap.put("data", returnStr);
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        }catch (Exception e){
            log.error("getMd5Sign", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    /**
     * 摩尔斯密码加解密
     * @param requestData
     * @return
     */
    @Override
    public String getMorseCode(String requestData) {
        log.info("morse : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String str = requestMap.get("str");
            String type = requestMap.get("type");//1  加密  2 解密
            if(!StringUtils.isEmpty(str) && !StringUtils.isEmpty(type)){
                MorseCoderUtil mc = new MorseCoderUtil();
                String returnStr = "";
                if("1".equals(type))
                    returnStr = mc.encode(str);
                else
                    returnStr = mc.decode(str);
                returnMap.put("data", returnStr);
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        }catch (Exception e){
            log.error("getMorseCode", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    /**
     * 获取随机数字
     * @param requestData
     * @return
     */
    @Override
    public String getRandomNumber(String requestData) {
        log.info("random : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();

        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String numberStr = requestMap.get("number");
            if(!StringUtils.isEmpty(numberStr)){
                int number = Integer.valueOf(numberStr);
                if(number < 1 || number > 20)
                    throw new RuntimeException("数字需大于0 小于20");

                long returnNumber = RandomNumberUtil.getRandomNumber(number);
                returnMap.put("data", String.valueOf(returnNumber));
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        }catch (Exception e){
            log.error("getRandomNumber", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    /**
     * 加解密RC4
     * @param requestData
     * @return
     */
    @Override
    public String getRc4(String requestData) {
        log.info("rc4 : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String str = requestMap.get("str");
            String type = requestMap.get("type");//1  加密  2 解密
            String key = requestMap.get("key");//用户自定义密钥
            if (StringUtils.isEmpty(key)) {
                key = "123";
            }
            if(!StringUtils.isEmpty(str) && !StringUtils.isEmpty(type)){

                String returnStr = "";
                if("1".equals(type))
                    returnStr = RC4Util.encryRC4String(str, key, "UTF-8");
                else
                    returnStr = RC4Util.decryRC4(str, key, "UTF-8");
                returnMap.put("data", returnStr);
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        }catch (Exception e){
            log.error("getRc4", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    /**
     * 根据汉字获取拼音
     * @param requestData
     * @return
     */
    @Override
    public String getPy(String requestData) {
        log.info("py : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String word = requestMap.get("word");
            if(!StringUtils.isEmpty(word)){
                if(word.length() > 30)
                    throw new RuntimeException("不能超过30个字");
                String returnStr = WordToPinUtil.getPinyin(word);
                returnMap.put("data", returnStr);
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        }catch (Exception e){
            log.error("getPy", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    @Override
    public String getBase64(String requestData) {
        log.info("base64 : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String str = requestMap.get("str");
            String type = requestMap.get("type");//1  加密  2 解密
            if(!StringUtils.isEmpty(str) && !StringUtils.isEmpty(type)){
                String returnStr = "";
                if("1".equals(type))
                    returnStr = Base64Util.encode(str);
                else
                    returnStr = Base64Util.decode(str);
                returnMap.put("data", returnStr);
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        }catch (Exception e){
            log.error("getBase64", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    @Override
    public String getResultByExp(String requestData) {
        log.info("exp : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String srcExp = requestMap.get("srcExp");
            String destExp = requestMap.get("destExp");
            String number = requestMap.get("number");

            if(!StringUtils.isEmpty(number) && !StringUtils.isEmpty(srcExp)&& !StringUtils.isEmpty(destExp)){
                CalculatorUtil cal = new CalculatorUtil();
                String src = srcExp.replace("x", number);
                double dSrc = cal.getResult(src);
                String dest = destExp.replace("x", cal.getRound(dSrc));
                double dDest = cal.getResult(dest);
                String returnStr = cal.getRound(dDest);
                returnMap.put("data", returnStr);
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        }catch (Exception e){
            log.error("getResultByExp", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    @Override
    public String getQrCode(String requestData) {
        log.info("getQrCode : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String str = requestMap.get("str");
            if(!StringUtils.isEmpty(str)){
                //初始化返回数据
                String returnStr = "";
                String fileName = CommonUtil.getSerialNoByRandom() + QrCodeUtil.FILE_SUFFIX_NAME;
                //定义文件路径
                String filePath = createPath + fileName;
                //生成文件
                QrCodeUtil.encode(str, null, filePath, true);
                //将图片转为base64返回
                returnStr = Base64Util.GetImageStr(filePath);

                if(!StringUtils.isEmpty(returnStr)){
                    returnMap.put("data", returnStr);
                    returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
                }else{
                    returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
                }
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        }catch (Exception e){
            log.error("getBase64", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }


}
