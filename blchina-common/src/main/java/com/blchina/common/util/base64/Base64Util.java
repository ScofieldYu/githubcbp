package com.blchina.common.util.base64;

import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Base64Util {

    public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();

            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        GenerateImage("", "/Users/emp/temp/abc.jpg");
    }


    public static String decode(String str){
        byte[] bt = null;
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            bt = decoder.decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(bt);
    }
    public static String encode(String str){
        String encode  = null;
        try {
            sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
            encode = encoder.encode(str.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encode;
    }

}
