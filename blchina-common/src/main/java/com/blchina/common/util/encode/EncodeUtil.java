package com.blchina.common.util.encode;


import com.blchina.common.util.constant.Constant;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

public class EncodeUtil {

    public static String getMD5For32(String input) {
        return encode(input, "MD5", 16, Constant.UTF8_ENCODING);
    }

    public static String getMD5For32UTF8(String input) {
        return encode(input, "MD5", 16, Constant.UTF8_ENCODING);
    }

    public static String getSHA1(String input) {
        return encode(input, "SHA-1", 20, Constant.GBK_ENCODING);
    }

    public static String getSHA1UTF8(String input) {
        return encode(input, "SHA-1", 20, Constant.UTF8_ENCODING);
    }

    public static String encode(String input, String algorithm, int length, String encoding) {
        byte[] source;
        try {
            source = input.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            source = input.getBytes();
        }
        String result = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(source);
            //The result should be one 128 integer
            byte temp[] = md.digest();
            char str[] = new char[length * 2];
            int k = 0;
            for (int i = 0; i < length; i++) {
                byte byte0 = temp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            result = new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String encodeStr(String value) {
        try {
            return URLEncoder.encode(value, Constant.UTF8_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encodeStrByDual(String value) {

        return encodeStr(encodeStr(value));
    }

    public static String decodeStr(String value) {
        try {
            return URLDecoder.decode(value, Constant.UTF8_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMD5(byte[] source) {
        String result = "";
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，用字节表示就是 16 个字节

            for (int i = 0; i < 16; i++) {
                // 从第一个字节开始，对 MD5 的每一个字节转换成 16 进制字符的转换
                byte byte0 = tmp[i]; // 取第 i 个字节
                String str = Integer.toHexString(byte0&0XFF);//&0XFF将除后8位，其他都置为0，这样toString时，就只显示后8位的字符串了
                if(str.length()==1){
                    str="0"+str;//排除如09但str只有9
                }
                result+= str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getMD5For32("tcl"));
    }
}
