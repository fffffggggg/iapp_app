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

}
