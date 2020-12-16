package com.convenient.iapp.common;

import java.util.HashMap;

public class CommonConstant {

    //返回成功
    public final static String RETURN_CODE_SUCCESS = "000000";
    //返回失败
    public final static String RETURN_CODE_FAIL = "100000";
    //程序异常
    public final static String RETURN_CODE_EXCEPTION = "200000";
    //十二星座码表
    public static HashMap<String, String> CONSTELLATION_MAP;

    static {
        CONSTELLATION_MAP  = new HashMap<String, String>();
//        CONSTELLATION_MAP.put("Capricorn", "摩羯座");
        CONSTELLATION_MAP.put("Capricorn", "%E6%91%A9%E7%BE%AF%E5%BA%A7");
//        CONSTELLATION_MAP.put("Aquarius", "水瓶座");
        CONSTELLATION_MAP.put("Aquarius", "%E6%B0%B4%E7%93%B6%E5%BA%A7");
//        CONSTELLATION_MAP.put("Pisces", "双鱼座");
        CONSTELLATION_MAP.put("Pisces", "%E5%8F%8C%E9%B1%BC%E5%BA%A7");
//        CONSTELLATION_MAP.put("Aries", "白羊座");
        CONSTELLATION_MAP.put("Aries", "%E7%99%BD%E7%BE%8A%E5%BA%A7");
//        CONSTELLATION_MAP.put("Taurus", "金牛座");
        CONSTELLATION_MAP.put("Taurus", "%E9%87%91%E7%89%9B%E5%BA%A7");
//        CONSTELLATION_MAP.put("Gemini", "双子座");
        CONSTELLATION_MAP.put("Gemini", "%E5%8F%8C%E5%AD%90%E5%BA%A7");
//        CONSTELLATION_MAP.put("Cancer", "巨蟹座");
        CONSTELLATION_MAP.put("Cancer", "%E5%B7%A8%E8%9F%B9%E5%BA%A7");
//        CONSTELLATION_MAP.put("Leo", "狮子座");
        CONSTELLATION_MAP.put("Leo", "%E7%8B%AE%E5%AD%90%E5%BA%A7");
//        CONSTELLATION_MAP.put("Virgo", "处女座");
        CONSTELLATION_MAP.put("Virgo", "%E5%A4%84%E5%A5%B3%E5%BA%A7");
//        CONSTELLATION_MAP.put("Libra", "天秤座");
        CONSTELLATION_MAP.put("Libra", "%E5%A4%A9%E7%A7%A4%E5%BA%A7");
//        CONSTELLATION_MAP.put("Scorpio", "天蝎座");
        CONSTELLATION_MAP.put("Scorpio", "%E5%A4%A9%E8%9D%8E%E5%BA%A7");
//        CONSTELLATION_MAP.put("Sagittarius", "射手座");
        CONSTELLATION_MAP.put("Sagittarius", "%E5%B0%84%E6%89%8B%E5%BA%A7");
    }

}
