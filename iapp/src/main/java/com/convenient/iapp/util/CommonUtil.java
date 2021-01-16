package com.convenient.iapp.util;

public class CommonUtil {


    public static String getSerialNoByRandom(){
        String time = TimeUtils.getYMDHMSS();
        long random = RandomNumberUtil.getRandomNumber(6);
        return time + String.valueOf(random);
    }
}
