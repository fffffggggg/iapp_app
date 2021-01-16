package com.convenient.iapp.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.Base64;

public class Base64Util {

    static Base64.Decoder decoder = Base64.getDecoder();
    static Base64.Encoder encoder = Base64.getEncoder();

    /**
     * Base64  加密
     * @param str
     * @return
     */
    public static String encode(String str){
        String encodedText = null;
        try {
            byte[] textByte = str.getBytes("UTF-8");
            encodedText = encoder.encodeToString(textByte);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedText;
    }

    /**
     * Base64  解密
     * @param str
     * @return
     */
    public static String decode(String str){
        String decodedText = null;
        try {
            decodedText = new String(decoder.decode(str), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodedText;
    }

    public static String GetImageStr(String imgPath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理

        File file = new File(imgPath);
        if(!file.exists()){
            return null;
        }

        InputStream in = null;
        byte[] data = null;
        String encode = null; // 返回Base64编码过的字节数组字符串
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            in = new FileInputStream(file);
            // 读取图片字节数组
            data = new byte[in.available()];
            in.read(data);
            encode = encoder.encode(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return encode;
    }

    /**
     * base64字符串转化成图片
     *
     * @param imgData
     *            图片编码
     * @param imgFilePath
     *            存放到本地路径
     * @return
     * @throws IOException
     */
    public static boolean GenerateImage(String imgData, String imgFilePath) throws IOException { // 对字节数组字符串进行Base64解码并生成图片
        boolean flag = false;
        if (imgData == null) // 图像数据为空
            return flag;
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            out = new FileOutputStream(imgFilePath);
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgData);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            out.write(b);
            flag = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
            return flag;
        }
    }

}
