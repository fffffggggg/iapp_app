package com.convenient.iapp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.convenient.iapp.common.CommonConstant;
import com.convenient.iapp.dao.AscTaskDao;
import com.convenient.iapp.dao.ConvenientDao;
import com.convenient.iapp.entity.CityTree;
import com.convenient.iapp.job.ExchangeJob;
import com.convenient.iapp.service.ConvenientService;
import com.convenient.iapp.threadpool.AscTask;
import com.convenient.iapp.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@SuppressWarnings("all")
@Service
@Slf4j
public class ConvenientServiceImpl implements ConvenientService {

    private static ExecutorService service = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

    private static String LOCATION_1 = "市辖区";
    private static String LOCATION_2 = "县";

    private static String cityTree;

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

    @Value("${api.juhe.ip.url}")
    private String ipUrl;

    @Value("${api.juhe.ip.key}")
    private String ipKey;

    @Value("${api.juhe.chengyu.url}")
    private String chengyuUrl;

    @Value("${api.juhe.chengyu.key}")
    private String chengyuKey;

    @Value("${api.juhe.tyfy.url}")
    private String tyfyUrl;

    @Value("${api.juhe.tyfy.key}")
    private String tyfyKey;

    @Value("${api.juhe.xhzd.url}")
    private String xhzdUrl;

    @Value("${api.juhe.xhzd.key}")
    private String xhzdKey;

    @Value("${api.juhe.lottery.url}")
    private String lotterUrl;

    @Value("${api.juhe.lottery.key}")
    private String lotteryKey;

    @Value("${api.tian.rainbowword.url}")
    private String rainbowWordUrl;

    @Value("${api.tian.getname.url}")
    private String getNameUrl;

    @Value("${api.tian.article.url}")
    private String articleUrl;

    @Value("${api.tian.pyqwa.url}")
    private String pyqWaUrl;

    @Value("${api.tian.garbage.url}")
    private String garbageUrl;

    @Value("${api.tian.calorie.url}")
    private String calorieUrl;

    @Value("${api.tian.key}")
    private String tianKey;

    @Value("${api.youdao.translate.url}")
    private String youdaoTranslateUrl;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ExchangeJob job;

    @Autowired
    private ConvenientDao convenientDao;

    @Autowired
    private AscTaskDao ascTaskDao;

    /**
     * 根据手机号查询归属地
     * @param requestData
     * @return
     */
    @Override
    public String getLocationByMobile(String requestData) {
        log.info("mobile : " + requestData);
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

    /**
     * 根据身份证号查询归属地
     * @param requestData
     * @return
     */
    @Override
    public String getLocationByIdCard(String requestData) {
        log.info("idcard : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String idCard = requestMap.get("idCard");
            if(IdentityUtil.idLeaglePre(idCard)){
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

    /**
     * 根据邮编查询归属地
     * @param requestData
     * @return
     */
    @Override
    public String getLocationByZipCode(String requestData) {
        log.info("zip : " + requestData);
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

    /**
     * 查询星座
     * @param requestData
     * @return
     */
    @Override
    public String getFortuneByConstellation(String requestData) {
        log.info("xz : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String constellation = requestMap.get("constellation");
            String type = requestMap.get("type");
            String constellationStr = URLEncoder.encode(CommonConstant.CONSTELLATION_MAP.get(constellation));
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

    /**
     * 根据ip查询位置
     * @param requestData
     * @return
     */
    @Override
    public String getLocationByIp(String requestData) {
        log.info("ip : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String ip = requestMap.get("ip");
            StringBuffer sb = new StringBuffer();
            sb.append(ipUrl).append("?key=").append(ipKey).append("&ip=").append(ip);
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
            log.error("getLocationByIp", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    /**
     * 成语查询
     * @param requestData
     * @return
     */
    @Override
    public String getChengyu(String requestData) {
        log.info("chengyu : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String chengyu = requestMap.get("word");
            StringBuffer sb = new StringBuffer();
            sb.append(chengyuUrl).append("?key=").append(chengyuKey).append("&word=").append(chengyu);
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
            log.error("getChengyu", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    /**
     * 同义词反义词查询
     * @param requestData
     * @return
     */
    @Override
    public String getTyfy(String requestData) {
        log.info("tyfy : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String word = requestMap.get("word");
            String type = requestMap.get("type"); //1 同义词 2 反义词
            StringBuffer sb = new StringBuffer();
            sb.append(tyfyUrl).append("?key=").append(tyfyKey).append("&type=").append(type).append("&word=").append(word);
            String url = sb.toString();
            String response = HttpUtil.doGet(url);
            //将response转为map 取出返回值 然后返回
            Map<String,Object> map = JSONObject.parseObject(response, Map.class);
            Object paramMapStr = map.get("result");
            if(!StringUtils.isEmpty(paramMapStr)){
                Map<String,Object> dataMap = JSONObject.parseObject(paramMapStr.toString(), Map.class);
                Object str = dataMap.get("words");
                String returnStr = "";
                if(!StringUtils.isEmpty(str)) {
                    returnStr = str.toString().substring(1, str.toString().length() - 1);
                    returnStr = returnStr.replace("\"", "");
                }
                returnMap.put("data", returnStr);
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        } catch (Exception e) {
            log.error("getTyfy", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    /**
     * 新华字典查询
     * @param requestData
     * @return
     */
    @Override
    public String getXhzd(String requestData) {
        log.info("xhzd : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String word = requestMap.get("word");
            StringBuffer sb = new StringBuffer();
            sb.append(xhzdUrl).append("?key=").append(xhzdKey).append("&word=").append(word);
            String url = sb.toString();
            String response = HttpUtil.doGet(url);
            //将response转为map 取出返回值 然后返回
            Map<String,Object> map = JSONObject.parseObject(response, Map.class);
            Object paramMapStr = map.get("result");
            if(!StringUtils.isEmpty(paramMapStr)){

                Map<String, Object> currentMap = JSONObject.parseObject(paramMapStr.toString(), Map.class);
                Object xj = currentMap.get("xiangjie");
                String xjStr = xj.toString().substring(1, xj.toString().length()-1);
                xjStr = xjStr.replaceAll("\"", "");
                currentMap.put("xiangjie", xjStr);
                returnMap.put("data", currentMap);
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        } catch (Exception e) {
            log.error("getXhzd", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    @Override
    public String getExchange(String requestData) {
        log.info("exchange : " + requestData);
        //初始化返回map
        Map<String, Object> returnMap = new HashMap<String, Object>();
        //初始化 存储money
        Map<String, String> paramMap = new HashMap<>();

        try {
            //从redis读取 汇率  其他币种 -----》 人民币
            Object rateMapObj = redisUtil.get("rate");
            //从redis读取 汇率  人民币 ------》 其他币种
            Object rmbRateMapObj = redisUtil.get("rmbRate");

            if(StringUtils.isEmpty(rateMapObj) || StringUtils.isEmpty(rmbRateMapObj)){
                //调用定时任务方法
                job.doGetExchangeRate();
                //从redis读取 汇率  其他币种 -----》 人民币
                rateMapObj = redisUtil.hmget("rate");
                //从redis读取 汇率  人民币 ------》 其他币种
                rmbRateMapObj = redisUtil.hmget("rmbRate");
            }
            if(!StringUtils.isEmpty(rateMapObj) && !StringUtils.isEmpty(rmbRateMapObj)) {
                Map<Object, Object> rateMap = (Map<Object, Object>)rateMapObj;
                Map<Object, Object> rmbRateMap = (Map<Object, Object>)rmbRateMapObj;
                //解析传入参数
                Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);

                //获取当前币种
                String unit = requestMap.get("unit");
                //获取转换的钱数
                String moneyStr = requestMap.get("money");
                //如果有空则返回null
                if (StringUtils.isEmpty(unit) || StringUtils.isEmpty(moneyStr))
                    return null;

                //将钱数转为double
                double money = Double.valueOf(moneyStr);
//            String unit = transMap.get(from);

                //如果请求币种不是人民币 则需要将其转化为人民币
                if (!CommonConstant.RMB.equals(unit)) {
//                money = exchangeRate(unit, Double.valueOf(moneyStr));
                    money = ((double) rateMap.get(unit)) * money;
                }

                //获取全部币种集合
                Set<Object> set = rmbRateMap.keySet();

                for (Object key : set) {
                    //获取币种的汇率进行计算
                    double toMoney = ((double) rmbRateMap.get(key)) * money;
                    //存入返回的map中
                    paramMap.put((String) key, CalculatorUtil.getRoundForRate(toMoney));
                }

                if (!StringUtils.isEmpty(paramMap)) {
                    returnMap.put("data", paramMap);
                    returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
                } else {
                    returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
                }
            }else {
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        } catch (Exception e) {
            log.error("getExchange", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    @Override
    public String getLottery(String requestData) {
        log.info("lottery : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String type = requestMap.get("type"); //彩票种类
            StringBuffer sb = new StringBuffer();
            sb.append(lotterUrl).append("?key=").append(lotteryKey).append("&lottery_id=").append(type);
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
            log.error("getTyfy", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }



    /**
     * 生成彩虹屁
     * @param toJSONString
     * @return
     */
    @Override
    public String getRainbowWord() {
        String content = null;
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(rainbowWordUrl).append("?key=").append(tianKey);
            String url = sb.toString();
            String response = HttpUtil.doGet(url);
            //将response转为map 取出返回值 然后返回
            Map<String,Object> map = JSONObject.parseObject(response, Map.class);
            Object paramMapStr = map.get("newslist");
            if(!StringUtils.isEmpty(paramMapStr)){
                List<Map<String, Object>> paramList = (List<Map<String, Object>>)paramMapStr;
                Map<String, Object> itemMap = paramList.get(0);
                content = (String)itemMap.get("content");
                int count = convenientDao.checkRainbowIfexist(content);
                if(count == 0) {
                    //将返回结果先保存数据备用
                    String id = UUID.randomUUID().toString().replaceAll("-", "");
                    Map<String, String> insertMap = new HashMap<String, String>();
                    insertMap.put("id", id);
                    insertMap.put("word", content);
                    convenientDao.insertRainbow(insertMap);
                }
                //返回结果
                returnMap.put("data", content);
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        } catch (Exception e) {
            log.error("getChengyu", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    /**
     * 生成朋友圈文案
     * @param toJSONString
     * @return
     */
    @Override
    public String getPyqWa() {
        log.info("pyqwa : " );
        String content = null;
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(pyqWaUrl).append("?key=").append(tianKey);
            String url = sb.toString();
            String response = HttpUtil.doGet(url);
            //将response转为map 取出返回值 然后返回
            Map<String,Object> map = JSONObject.parseObject(response, Map.class);
            Object paramMapStr = map.get("newslist");
            if(!StringUtils.isEmpty(paramMapStr)){
                List<Map<String, Object>> paramList = (List<Map<String, Object>>)paramMapStr;
                Map<String, Object> itemMap = paramList.get(0);
                content = (String)itemMap.get("content");
                int count = convenientDao.checkPyqwaIfexist(content);
                if(count == 0) {
                    String id = UUID.randomUUID().toString().replaceAll("-", "");
                    Map<String, String> insertMap = new HashMap<String, String>();
                    insertMap.put("id", id);
                    insertMap.put("word", content);
                    convenientDao.insertPyqwn(insertMap);
                }
                returnMap.put("data", content);
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        } catch (Exception e) {
            log.error("getChengyu", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    @Override
    public String getGarbageList(String requestData) {
        log.info("garbageliest : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String word = requestMap.get("word");
            StringBuffer sb = new StringBuffer();
            sb.append(garbageUrl).append("?key=").append(tianKey).append("&word=").append(word);
            String url = sb.toString();
            String response = HttpUtil.doGet(url);
            //将response转为map 取出返回值 然后返回
            Map<String,Object> map = JSONObject.parseObject(response, Map.class);
            Object paramMapStr = map.get("newslist");
            if(!StringUtils.isEmpty(paramMapStr)){
                //0为可回收、1为有害、2为厨余(湿)、3为其他(干)
                List<Map<String, Object>> list = (List<Map<String, Object>>)paramMapStr;
                StringBuilder returnSb = new StringBuilder();
                List<Map<String, String>> dbList = new ArrayList<>();
                for(Map<String, Object> item : list){
                    String name = (String)item.get("name");
                    Object type = item.get("type");
                    String typeName = CommonConstant.GARBAGE_MAP.get(type);
                    returnSb.append(name).append(",").append(typeName).append(";  ");
                    String id = UUID.randomUUID().toString().replaceAll("-", "");
                    Map<String, String> dbMap = new HashMap<>();
                    dbMap.put("id", id);
                    dbMap.put("name", name);
                    dbMap.put("type", typeName);
                    dbList.add(dbMap);
                }
                AscTask ascTask = new AscTask("garbage", dbList, ascTaskDao);
//                service.execute(ascTask);
                service.submit(ascTask);
                returnMap.put("data", returnSb.toString().substring(0, returnSb.toString().length()-3));
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        } catch (Exception e) {
            log.error("getChengyu", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    /**
     * 根据位置查找邮政编码
     * @param toJSONString
     * @return
     */
    @Override
    public String getZipCodeByArea(String requestData) {
        log.info("location : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String province = requestMap.get("province");
            String city = requestMap.get("city");
            String area = requestMap.get("county");
            if(!StringUtils.isEmpty(province)&& !StringUtils.isEmpty(city) && !StringUtils.isEmpty(area)){
                Map<String, String> queryMap = new HashMap<String, String>();
                queryMap.put("province" ,province);
                queryMap.put("city" ,city);
                queryMap.put("area" ,area);

                String zipCode = convenientDao.getZipcodeByArea(queryMap);

                returnMap.put("data", zipCode);
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
                returnMap.put("msg", "请输入正确的身份证号");
            }
        } catch (Exception e) {
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
            log.error("getZipCodeByArea", e);
        } finally {
            return JSON.toJSONString(returnMap);
        }
    }

    //?key=APIKEY&word=油条&mode=0

    @Override
    public String getCalorie(String requestData) {
        log.info("getCalorie : " + requestData);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String word = requestMap.get("word");
            StringBuffer sb = new StringBuffer();
            sb.append(calorieUrl).append("?key=").append(tianKey).append("&mode=0&word=").append(word);
            String url = sb.toString();
            String response = HttpUtil.doGet(url);
            //将response转为map 取出返回值 然后返回
            Map<String,Object> map = JSONObject.parseObject(response, Map.class);
            Object paramMapStr = map.get("newslist");
            if(!StringUtils.isEmpty(paramMapStr)){
                //0为可回收、1为有害、2为厨余(湿)、3为其他(干)
                List<Map<String, Object>> list = (List<Map<String, Object>>)paramMapStr;
                StringBuilder returnSb = new StringBuilder();
                List<Map<String, String>> dbList = new ArrayList<>();
                for(Map<String, Object> item : list){
                    String name = (String)item.get("name");
                    Object rl = item.get("rl");
                    String type = (String)item.get("type");

                    returnSb.append("每100g ").append(rl).append("大卡").append(";  ");
                    String id = UUID.randomUUID().toString().replaceAll("-", "");
                    Map<String, String> dbMap = new HashMap<>();
                    dbMap.put("id", id);
                    dbMap.put("name", name);
                    dbMap.put("calorie", String.valueOf(rl));
                    dbMap.put("type", type);
                    dbList.add(dbMap);
                }
                AscTask ascTask = new AscTask("calorie", dbList, ascTaskDao);
                service.submit(ascTask);
                returnMap.put("data", returnSb.toString().substring(0, returnSb.toString().length()-3));
                returnMap.put("code", CommonConstant.RETURN_CODE_SUCCESS);
            }else{
                returnMap.put("code", CommonConstant.RETURN_CODE_FAIL);
            }
        } catch (Exception e) {
            log.error("getChengyu", e);
            returnMap.put("code", CommonConstant.RETURN_CODE_EXCEPTION);
        }finally {
            return JSON.toJSONString(returnMap);
        }
    }

    @Override
    public String buildCityTree() {

        if (StringUtils.isEmpty(cityTree)) {
            List<Map<String, Object>> cityList = convenientDao.getCityList();
            List<CityTree> s = getCityTree(cityList);
            cityTree =JSONObject.toJSONString(s);
            redisUtil.set("cityTree", cityTree);
        }
        return cityTree;
    }



    /**
     * 创建树节点
     * @param cityList
     * @return
     */
    private List<CityTree> getCityTree(List<Map<String, Object>> cityList){
        List<CityTree> treeList = cityList.stream()
                .filter(city -> !city.get("id").equals(city.get("pid")))
                .map(city ->{
                    CityTree node = new CityTree();
                    node.setId(String.valueOf(city.get("id")));
                    node.setPid(String.valueOf(city.get("pid")));
                    node.setName(String.valueOf(city.get("name")));
                    return node;
                }).collect(Collectors.toList());
        return TreeUtil.build(treeList, "0");
    }


   /* @Override
    public String getTranslate(String requestData) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map<String, String> requestMap = JSONObject.parseObject(requestData, Map.class);
            String word = requestMap.get("word");
            String type = requestMap.get("type");

            StringBuffer sb = new StringBuffer();
            String returnStr = null;
//            sb.append(googleTranslateUrl).append("&tl=").append(type).append("&q=").append(word);
            sb.append(youdaoTranslateUrl).append(URLEncoder.encode(word));
            String url = sb.toString();
            String response = HttpUtil.doGet(url);
            //将response转为map 取出返回值 然后返回
            Map<String,Object> map = JSONObject.parseObject(response, Map.class);
            Object paramListStr = map.get("translateResult");
            if (!StringUtils.isEmpty(paramListStr)) {
                List<String> returnList = new ArrayList<>();
                List<List<JSONObject>>  s = (List<List<JSONObject>>)paramListStr;
                for (List<JSONObject> jsonList : s){
                    for(JSONObject json : jsonList){
                        String str = (String)json.get("tgt");
                        returnList.add(str);
                    }
                }

                if(!StringUtils.isEmpty(returnList)){
                    returnMap.put("data", JSONObject.toJSONString(returnList).replaceAll("\"", ""));
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
    }*/

}
