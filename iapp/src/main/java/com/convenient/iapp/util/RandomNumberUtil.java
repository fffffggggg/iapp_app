package com.convenient.iapp.util;

public class RandomNumberUtil {

    public static long getRandomNumber(int n){
        if(n<1 || n >20){
            throw new IllegalArgumentException("随机数位数必须大于0");
        }
        return (long)(Math.random()*9*Math.pow(10,n-1)) + (long)Math.pow(10,n-1);
    }

}
