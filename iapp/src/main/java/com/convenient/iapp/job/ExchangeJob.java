package com.convenient.iapp.job;

import com.alibaba.fastjson.JSONObject;
import com.convenient.iapp.common.CommonConstant;
import com.convenient.iapp.util.HttpUtil;
import com.convenient.iapp.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务
 */
@Component
@Slf4j
public class ExchangeJob {

    @Autowired
    private RedisUtil redisUtil;

    @Value("${api.juhe.exchange.url}")
    private String exchangeUrl;

    @Value("${api.juhe.exchange.key}")
    private String exchangeKey;


    @Scheduled(cron = "0 0/30 * * * ?")
    public void doGetExchangeRateJob(){
        doGetExchangeRate();
    }

    /**
     * 定时任务执行方法， 通过该定时任务更新汇率
     */
    public void doGetExchangeRate(){

        Map<Object, Object> rateMap = new HashMap<Object, Object>();
        Map<Object, Object> rmbRateMap = new HashMap<Object, Object>();

        try {
            StringBuffer sb = new StringBuffer();
            sb.append(exchangeUrl).append("?key=").append(exchangeKey);
            String url = sb.toString();
            String response = HttpUtil.doGet(url);
            //将response转为map 取出返回值 然后返回
            Map<String,Object> map = JSONObject.parseObject(response, Map.class);
            Object objResult = map.get("result");
            if(!StringUtils.isEmpty(objResult)) {
                Map objMap = JSONObject.parseObject(objResult.toString(), Map.class);
                List<Object> list = (List<Object>) objMap.get("list");
                if(!CollectionUtils.isEmpty(list)){
                    for (Object obj : list){
                        if(!StringUtils.isEmpty(obj)){
                            List<String> currentList = (List<String>)obj;
                            //获取货币名称
                            String cnName = currentList.get(0);
                            //获取货币数量
                            String baseNum = currentList.get(1);
                            //获取当前数量下汇率
                            String rateStr = currentList.get(4);

                            double baseD = Double.valueOf(baseNum);
                            double rateD = Double.valueOf(rateStr);
                            //计算单位为1的情况下的汇率
                            double rate = rateD / baseD;
                            //计算反转汇率
                            double reverseRate = 1 / rate;
                            //获取单位表示值
                            String unit = CommonConstant.TRANS_MAP.get(cnName);
                            if(!StringUtils.isEmpty(unit)) {
                                rateMap.put(unit, rate);
                                rmbRateMap.put(unit, reverseRate);
                            }
                        }
                    }
                    //将rmb放入集合
                    rateMap.put(CommonConstant.RMB, 1d);
                    rmbRateMap.put(CommonConstant.RMB, 1d);
                    //存入redis
                    if(!StringUtils.isEmpty(rateMap)) {
                        redisUtil.set("rate", rateMap);
                        redisUtil.set("rmbRate", rmbRateMap);
                    }
                }
            }
        } catch (NumberFormatException e) {
            log.error("定时任务出现异常，异常信息为 ："  + e);
        }
    }
}
