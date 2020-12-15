package com.convenient.iapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static SimpleDateFormat SDF_YY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getCurrentDate(Date date){
        return SDF_YY_MM_DD_HH_MM_SS.format(date);
    }
}
