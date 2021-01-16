package com.convenient.iapp.service;

public interface ConvenientService {

    //根据手机号获取归属地
    String getLocationByMobile(String mobile);

    //根据身份证号获取归属地
    String getLocationByIdCard(String idCard);

    //根据邮编查询地址
    String getLocationByZipCode(String zipCode);

    //根据星座查运势
    String getFortuneByConstellation(String requestData);

    //根据ip查归属地
    String getLocationByIp(String toJSONString);

    //获取成语释义
    String getChengyu(String toJSONString);

    //获取同义词反义词
    String getTyfy(String toJSONString);

    //获取新华字典
    String getXhzd(String toJSONString);

    //根据汇率计算
    String getExchange(String toJSONString);

    //获取彩虹屁
    String getRainbowWord();

    //获取朋友圈文案
    String getPyqWa();

    //垃圾分类
    String getGarbageList(String toJSONString);

    //根据位置寻找邮政编码
    String getZipCodeByArea(String toJSONString);

    String buildCityTree();

    //彩票结果查询
    String getLottery(String toJSONString);

    String getCalorie(String toJSONString);

    //翻译
//    String getTranslate(String requestData);
}
