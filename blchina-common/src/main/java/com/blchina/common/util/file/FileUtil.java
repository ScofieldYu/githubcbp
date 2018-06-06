package com.blchina.common.util.file;



import sun.misc.BASE64Decoder;

import java.io.*;

public class FileUtil {
    private static String hexStr =  "0123456789ABCDEF";
    public static void writeFile(String pathname, String content, String encoding) throws IOException {
        File path = new File(pathname);
        File parent = path.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
        } catch (IOException e) {
            if (!parent.exists()){
                parent.mkdirs();
            }
            fos = new FileOutputStream(path);
        }
        try {
            byte[] buff = content.getBytes(encoding);
            fos.write(buff, 0, buff.length);
        } finally {
            path.setWritable(false);
            fos.close();
        }
    }

    public static String readFile(String path,String encoding) throws UnsupportedEncodingException {
        File file = new File(path);
        Long fileLength = file.length();
        byte [] fcontent = new byte[fileLength.intValue()];
        try {
            FileInputStream fin = new FileInputStream(file);
            fin.read(fcontent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(fcontent,encoding);
    }
    //生成目录地址
    public static boolean genDirectory(String path){
        File file=new File(path);
        if(!file.isDirectory()&&!file.exists()){
            return file.mkdirs();
        }
        return true;
    }
    public static File byte2File(byte[] buf, String filePath, String fileName)
    {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try
        {
           file = new File(filePath);
            if (!file.exists() &&  file.isDirectory())
            {
                file.mkdirs();
            }
            OutputStream output = new FileOutputStream(file);
             bos = new BufferedOutputStream(output);
            bos.write(buf);
            return  file;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return  file;
    }
    public static byte[] hexStr2BinArr(String hexString){
        //hexString的长度对2取整，作为bytes的长度
        int len = hexString.length()/2;
        byte[] bytes = new byte[len];
        byte high = 0;//字节高四位
        byte low = 0;//字节低四位
        for(int i=0;i<len;i++){
            //右移四位得到高位
            high = (byte)((hexStr.indexOf(hexString.charAt(2*i)))<<4);
            low = (byte)hexStr.indexOf(hexString.charAt(2*i+1));
            bytes[i] = (byte) (high|low);//高地位做或运算
        }
        return bytes;
    }
    // base64字符串转化成图片
    public static File GenerateImage(String imgStr, String filePath, String fileName) { // 对字节数组字符串进行Base64解码并生成图片
        BASE64Decoder decoder = new BASE64Decoder();
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }

                file = new File(filePath);
                if (!file.exists() && file.isDirectory())
                {
                    file.mkdirs();
                }
                OutputStream output = new FileOutputStream(file);
                bos = new BufferedOutputStream(output);
                bos.write(b);
                return  file;
            }  catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (bos != null)
                {
                    try
                    {
                        bos.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                if (fos != null)
                {
                    try
                    {
                        fos.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            return file;
    }

}
