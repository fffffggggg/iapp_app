package com.convenient.iapp.service;

public interface ToolService {

    //获取数字转大写
    String getNumConvert(String toJSONString);

    //获取md5签名
    String getMd5Sign(String toJSONString);

    //摩尔斯电码转换
    String getMorseCode(String toJSONString);

    //获取随机数
    String getRandomNumber(String toJSONString);

    //rc4加解密
    String getRc4(String toJSONString);

    //获取汉字拼音
    String getPy(String toJSONString);

    //base64加解密
    String getBase64(String toJSONString);

    //根据数学表式进行计算并返回字符串
    String getResultByExp(String toJSONString);

    //根据输入内容生成二位
    String getQrCode(String toJSONString);

}
