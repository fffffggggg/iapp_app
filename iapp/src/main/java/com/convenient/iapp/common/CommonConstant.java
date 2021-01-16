package com.convenient.iapp.common;

import java.util.HashMap;
import java.util.Map;

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
        CONSTELLATION_MAP.put("Capricorn", "摩羯座");
        CONSTELLATION_MAP.put("Aquarius", "水瓶座");
        CONSTELLATION_MAP.put("Pisces", "双鱼座");
        CONSTELLATION_MAP.put("Aries", "白羊座");
        CONSTELLATION_MAP.put("Taurus", "金牛座");
        CONSTELLATION_MAP.put("Gemini", "双子座");
        CONSTELLATION_MAP.put("Cancer", "巨蟹座");
        CONSTELLATION_MAP.put("Leo", "狮子座");
        CONSTELLATION_MAP.put("Virgo", "处女座");
        CONSTELLATION_MAP.put("Libra", "天秤座");
        CONSTELLATION_MAP.put("Scorpio", "天蝎座");
        CONSTELLATION_MAP.put("Sagittarius", "射手座");
    }

    public final static String RMB = "RMB";

    //汇率兑换币种集合
    public static Map<String, String> TRANS_MAP;

    static {
        TRANS_MAP = new HashMap<String, String>();
        TRANS_MAP.put("美元", "USD");
        TRANS_MAP.put("日元", "JPY");
        TRANS_MAP.put("欧元", "EUR");
        TRANS_MAP.put("港币", "HKD");
        TRANS_MAP.put("英镑", "GBP");
        TRANS_MAP.put("新西兰元", "NZD");
        TRANS_MAP.put("韩元", "KER");
        TRANS_MAP.put("加拿大元", "CAD");
        TRANS_MAP.put("澳大利亚元", "AUD");
    }


    //汇率兑换币种集合
    public static Map<Object, String> GARBAGE_MAP;

    static {
        GARBAGE_MAP = new HashMap<Object, String>();
        GARBAGE_MAP.put(0, "可回收");
        GARBAGE_MAP.put(1, "有害垃圾");
        GARBAGE_MAP.put(2, "厨余(湿)垃圾");
        GARBAGE_MAP.put(3, "其他(干)垃圾");
    }

}
