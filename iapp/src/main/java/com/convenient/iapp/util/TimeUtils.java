package com.convenient.iapp.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
public class TimeUtils {

    private static final String YYYYMM = "yyyyMM";
    private static final String YYYY_MM = "yyyy-MM";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final String YYYYMMDD = "yyyyMMdd";
    private static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    private static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    private static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

    /** 时间字段常量， 表示 秒*/
    public final static int SECOND = 0;
    /** 时间字段常量， 表示 分*/
    public final static int MINUTE = 1;
    /** 时间字段常量， 表示 时*/
    public final static int HOUR = 2;
    /** 时间字段常量， 表示 天*/
    public final static int DAY = 3;

    /** 各常量允许的最大值 */
    private final int [] maxFields = { 59, 59, 23, Integer.MAX_VALUE - 1};

    /** 各常量允许的最小值 */
    private final int [] minFields = { 0, 0, 0, Integer.MIN_VALUE};

    /** 默认的字符串格式时间分隔符*/
    private String timeSeparator = ":";
    /** 时间数据容器*/
    private int [] fields = new int[4];

    /** 无参构造， 将各字段置为 0*/
    public TimeUtils(){
        this(0, 0, 0, 0);
    }
    /** 使用 时，分 构造一个时间*/
    public TimeUtils(int hour, int minute){
        this(0, hour, minute, 0);
    }
    /** 使用 时，分，秒 构造一个时间*/
    public TimeUtils(int hour, int minute, int second){
        this(0, hour, minute, second);
    }
    /** 使用一个字符串构造时间*/
    public TimeUtils(String time){
        this(time, null);
    }
    /** 使用时间毫秒构造时间*/
    public TimeUtils(long time){
        this(new Date(time));
    }
    /** 使用日期对象构造时间*/
    public TimeUtils(Date date){
//        this(DateFormatUtils.formatUTC(date, "HH:mm:ss"));
    }

    /** 使用天，时，分，秒构造时间，进行全字符的构造*/
    public TimeUtils(int day, int hour, int minute, int second){
        initialize(day, hour, minute, second);
    }
    /** 使用一个字符串构造时间， 指定分隔符*/
    public TimeUtils(String time, String timeSeparator){
        if(timeSeparator != null){
            setTimeSeparator(timeSeparator);
        }
        parseTime(time);
    }
    /** 设置时间字段的值*/
    public void set(int field, int value){
        if(value < minFields[field]){
            throw new IllegalArgumentException(value + ", time value must be positive.");
        }
        fields [field] = value % (maxFields[field] + 1);
        //进行进位计算
        int carry =  value / (maxFields[field] + 1);
        if(carry > 0){
            int upFieldValue = get(field + 1);
            set(field + 1, upFieldValue + carry);
        }
    }
    /** 获得时间字段的值*/
    public int get(int field){
        if(field < 0 || field > fields.length - 1){
            throw new IllegalArgumentException(field + ", field value is error.");
        }
        return fields[field];
    }

    /** 将时间进行 加 运算 即加上一个时间*/
    public TimeUtils addTime(TimeUtils time){
        TimeUtils result = new TimeUtils();
        int up = 0; //进位标志
        for(int i = 0; i < fields.length; i++){
            int sum = fields[i] + time.fields[i] + up;
            up = sum / (maxFields[i] + 1);
            result.fields[i] = sum % (maxFields[i] + 1);
        }
        return result;
    }
    /** 将时间进行 减 运算 即减去一个时间*/
    public TimeUtils subtractTime(TimeUtils time){
        TimeUtils result = new TimeUtils();
        int down = 0; //退位标志
        for(int i = 0, k = fields.length - 1; i < k; i++){
            int difference = fields[i] + down;
            if(difference >= time.fields[i]){
                difference -= time.fields[i];
                down = 0;
            }else{
                difference += maxFields[i] + 1 - time.fields[i];
                down = -1;
            }
            result.fields[i] = difference;
        }
        result.fields[DAY] = fields[DAY] - time.fields[DAY] + down;
        return result;
    }

    /** 获得时间字段的分隔符 */
    public String getTimeSeparator(){
        return timeSeparator;
    }

    /** 设置时间字段的分隔符(用于字符串格式的时间)*/
    public void setTimeSeparator(String timeSeparator){
        this.timeSeparator = timeSeparator;
    }

    private void initialize(int day, int hour, int minute, int second){
        set(DAY, day);
        set(HOUR, hour);
        set(MINUTE, minute);
        set(SECOND, second);
    }

    private void parseTime(String time){
        if(time == null){
            initialize(0, 0, 0, 0);
            return;
        }
        String t = time;
        int field = DAY;
        set(field--, 0);
        int p = -1;
        while((p = t.indexOf((timeSeparator))) > -1){
            parseTimeField(time, t.substring(0, p), field--);
            t = t.substring(p + timeSeparator.length());
        }
        parseTimeField(time, t, field--);
    }

    private void parseTimeField(String time, String t, int field){
        if(field < SECOND || t.length() < 1){
            parseTimeException(time);
        }
        char [] chs = t.toCharArray();
        int n = 0;
        for(int i=0; i<chs.length; i++){
            if(chs[i] <= ' '){
                continue;
            }
            if(chs[i] >= '0' && chs[i] <= '9'){
                n = n*10 + chs[i] - '0';
                continue;
            }
            parseTimeException(time);
        }
        set(field, n);
    }

    private void parseTimeException(String time){
        throw new IllegalArgumentException(time + ", time format error, HH" + this.timeSeparator + "mm" + this.timeSeparator + "ss");
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(16);
        sb.append(fields[DAY]).append(',').append(' ');
        buildString(sb, HOUR).append(timeSeparator);
        buildString(sb, MINUTE).append(timeSeparator);
        buildString(sb, SECOND);
        return sb.toString();
    }

    private StringBuilder buildString(StringBuilder sb, int field){
        if(fields[field] < 10){
            sb.append('0');
        }
        return sb.append(fields[field]);
    }

    public int hashCode(){
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + Arrays.hashCode(fields);
        return result;
    }

    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        final TimeUtils other = (TimeUtils) obj;
        if(!Arrays.equals(fields, other.fields)){
            return false;
        }
        return true;
    }

    /** 获取当前时间*/
    public static Long getNowLong(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String strDate = new SimpleDateFormat(YYYYMMDDHHMMSS).format(date);
        long longDate = Long.valueOf(strDate);
        return longDate;
    }

    /** 获取当前时间字符串*/
    public static String getNowStr(String format){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return new SimpleDateFormat(format).format(date);
    }
    /** 转换日期字符串*/
    public static  String parseDateToStr(Date date, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String formatDate = simpleDateFormat.format(date);
        return formatDate;
    }

    public static Date parseStrToDate(String date, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date newDate = null;
        try {
            newDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    /**
     * 获取昨天日期字符串
     * @param format
     * @return
     */
    public static String getYesterday(String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        Date date  = calendar.getTime();
        String dateStr = dateFormat.format(date);
        return dateStr;
    }

    public static String getRetreat(int retreatNum){
        return getRetreat(retreatNum, TimeUtils.YYYY_MM_DD);
    }

    public static String getRetreat(int retreatNum, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, retreatNum);
        Date date  = calendar.getTime();
        String dateStr = dateFormat.format(date);
        return dateStr;


    }

    public static long timestampation(String date){
        return timestampation(date, YYYY_MM_DD_HH_MM_SS);
    }
    public static long timestampation(String date, String format){
        SimpleDateFormat formater = new SimpleDateFormat(format);

        try {
            Date parse = formater.parse(date);
            formater.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            return parse.getTime();
        } catch (ParseException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    public static String toTimeString(long time, String format){
        Date date = new Date(time);
        return TimeUtils.parseDateToStr(date, format);
    }

    public static String toTimeString(long time){
        TimeUtils t = new TimeUtils(time);
        int day = t.get(TimeUtils.DAY);
        int hour = t.get(TimeUtils.HOUR);
        int minute = t.get(TimeUtils.MINUTE);
        int second = t.get(TimeUtils.SECOND);
        StringBuilder sb = new StringBuilder();
        if(day > 0){
            sb.append(day).append("天");
        }
        if(hour > 0){
            sb.append(day).append("时");
        }
        if(minute > 0){
            sb.append(day).append("分");
        }
        if(second > 0){
            sb.append(day).append("秒");
        }
        return sb.toString();
    }


    public static String timeStamp(Integer statusMinute){
        if(statusMinute == null){
            return null;
        }
        int day = statusMinute / 60 / 60 / 24;
        int hour = statusMinute / 60 / 60 % 24;
        int min = statusMinute /60 % 60;
        String result = "";
        if(day > 0){
            result += day + "天";
        }
        if(hour > 0){
            result += hour + "小时";
        }
        if(min > 0){
            result += min + "分钟";
        }
        return result;
    }

    /** 获取当前时间*/
    public static String getYMDHMSS(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String strDate = new SimpleDateFormat(YYYYMMDDHHMMSSSSS).format(date);
        return strDate;
    }

}
