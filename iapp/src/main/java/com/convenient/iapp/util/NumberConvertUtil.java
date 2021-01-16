package com.convenient.iapp.util;

public class NumberConvertUtil {

    /** 单位进制，中文默认为4位即 万 亿*/
    public static int UNIT_STEP = 4;

    /** 单位*/
    public static String [] moneyCount = new String [] {"","拾","佰","仟","万"};
    /** 汉字*/
    public static String [] strHan = new String [] {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};

    private boolean zero;

    public NumberConvertUtil(){
        zero = false;
    }

    private String [] divide(double digitalRemValue){
        //处理参数
        long intPart = (long)digitalRemValue;
        //处理小数，把小数转换成整数形式处理，并进行四舍五入，此时小数四舍五入之后为100的应该另外加一层处理
        long decimalPart = Math.round((double)Math.round((digitalRemValue - intPart) * 10000) / 100);

        if(decimalPart == 100)
            intPart += 1;

        String strIntPart = "";
        if(intPart == 0)
            strIntPart = "0";
        else
            strIntPart = "" + intPart;

        String strDecimalPart = "";
        if(decimalPart < 10 && decimalPart >= 0)
            strDecimalPart = "0" + decimalPart;
        else
            strDecimalPart = "" + decimalPart;

        if(intPart == 0 && decimalPart == 0)
            zero = true;

        return new String [] {strIntPart, strDecimalPart};
    }

    //转换小数部分
    private String transDecimal(String str){
        if(str.equals("100") || str.equals("00")){
            return "";
//            return "零角零分";
        }else{
            String strResult;
            char[] strRmb = new char[str.length()];
            strRmb = str.toCharArray();
            if(strRmb[0] == '0' && strRmb[1] == '0'){
                strResult = "";
            }else{
                int dm = strRmb[0] - '0';
                int dn = strRmb[1] - '0';
                strResult = strHan[dm] + "角" + strHan[dn] + "分";
            }
            return strResult;
        }
    }

    //转换整数部分
    private String transInt(String str){
        char [] firstFourBitRmb = new char [str.length()];
        firstFourBitRmb = str.toCharArray();
        int len = firstFourBitRmb.length;
        String strResult = "";
        for(int i=0; i<len; i++){
            int m = firstFourBitRmb[i] - '0';
            strResult += strHan[m] + moneyCount[len - i - 1];
        }
        return strResult;
    }

    public String transComb(double strValue){
        String [] strTempRmb = new String [2];
        strTempRmb = divide(strValue);
        //获取小数部分
        String decimalStr = transDecimal(strTempRmb[1]);
        //获取整数部分
        String intStr = "";
        int len = strTempRmb[0].length();
        if(len > 12 ){
            return "数据过大，无法进行转换，请重新输入！";
        }else{
            if(len >= 9){
                String strBit1 = strTempRmb[0].substring(0, len - 8);
                String strBit2 = strTempRmb[0].substring(len - 8, len - 4);
                String strBit3 = strTempRmb[0].substring(len - 4, len);
                String strResult1 = transInt(strBit1) + "亿";
                String strResult2 = transInt(strBit2) + "万";
                String strResult3 = transInt(strBit3) + "元";
                intStr = strResult1 + strResult2 + strResult3;
            }else if( len >= 5){
                String strBit1 = strTempRmb[0].substring(0, len - 4);
                String strBit2 = strTempRmb[0].substring(len - 4, len);
                String strResult1 = transInt(strBit1) + "万";
                String strResult2 = transInt(strBit2) + "元";
                intStr = strResult1 + strResult2;
            }else if(len >= 1){
                if(zero == true)
                    intStr = "零元";
                else
                    intStr = transInt(strTempRmb[0]) + "元";
            }
            return intStr + decimalStr;
        }


    }

    public static void main(String [] args){
        NumberConvertUtil n = new NumberConvertUtil();
        System.out.println(n.transComb(12.3));
        System.out.println(n.transComb(0));
        System.out.println(n.transComb(1212123.2));
        System.out.println(n.transComb(0.00));
        System.out.println(n.transComb(99999.99));
    }
}
