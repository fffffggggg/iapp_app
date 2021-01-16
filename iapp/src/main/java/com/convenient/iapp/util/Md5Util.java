package com.convenient.iapp.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

@Slf4j
public class Md5Util {

    /**
     * md5加密
     * @param str
     * @return
     */
    public static String encrype(String str){
        MessageDigest md5 = null;
        String md5Str = null;
        try{
            md5 = MessageDigest.getInstance("MD5");

            char[] charArray = str.toCharArray();
            byte[] byteArray = new byte[charArray.length];

            for(int i=0; i<charArray.length; i++)
                byteArray[i] = (byte)charArray[i];
            byte [] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for(int i=0; i<md5Bytes.length; i++){
                int val = ((int) md5Bytes[i]) & 0xff;
                if(val < 16){
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            md5Str = hexValue.toString();
        }catch (Exception e){
            log.error("md5 加密失败， 异常为：" + e);
        }
        return md5Str;
    }
    
}
