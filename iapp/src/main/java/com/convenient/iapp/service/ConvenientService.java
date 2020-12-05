package com.convenient.iapp.service;

public interface ConvenientService {

    //根据手机号获取归属地
    String getLocationByMobile(String mobile);

    //根据身份证号获取归属地
    String getLocationByIdCard(String idCard);

    //根据快递号及快递公司查询位置信息
    String getLocationByExpress(String expressCom, String expressNum);

    //根据邮编查询地址
    String getLocationByZipCode(String zipCode);

    //根据数字获取大写数字
    String getBigNumByNumber(String number);

    //根据一个或几个汉字查询相关成语
    String getIdiomBySingleWord(String word);
}
