package com.blchina.common.util.http;

import com.blchina.common.util.constant.Constant;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
    private static Logger log = Logger.getLogger(HttpUtil.class);

    /**
     * post发送文件
     */
    public static String postWeixinMaterial(String url, String imageLocalPath, Map<String, String> paramMap) {
        File file = new File(imageLocalPath);
        PostMethod method = new PostMethod(url);
        HttpClient client = new HttpClient();

        try {
            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, Constant.UTF8_ENCODING);        // 中文乱码

//            NameValuePair[] pairs = new NameValuePair[paramMap.size()];
//            int i = 0;
            for (Map.Entry entry : paramMap.entrySet()) {
                method.getParams().setParameter(entry.getKey().toString(), entry.getValue().toString());
            }

            // 设置文件
            Part[] parts = {new FilePart("media", file)};
            method.setRequestEntity(new MultipartRequestEntity(parts, method.getParams()));

            // 设置参数
//            NameValuePair[] pairs = new NameValuePair[paramMap.size()];
//            int i = 0;
//            for (Map.Entry entry : paramMap.entrySet()) {
//                pairs[i++] = new NameValuePair(entry.getKey().toString(), entry.getValue().toString());
//            }
//            method.setRequestBody(pairs);


            return post(method);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            method.releaseConnection();
        }

        return null;
    }

    public static String post(String url, Map<String, String> paramMap) {
        PostMethod method = buildPostMethod(url, paramMap);
        return post(method);
    }

    public static String post(String url, String data) {
        PostMethod method = buildPostMethod(url, data);
        return post(method);
    }

    public static String post(PostMethod method) {
        HttpClient client = new HttpClient();
        StringBuilder sb = null;
        BufferedReader reader = null;
        try {
            int code = client.executeMethod(method);
            if (code != 200) return null;

            InputStream in = method.getResponseBodyAsStream();
            reader = new BufferedReader(new InputStreamReader(in, Constant.UTF8_ENCODING));
            String result;
            sb = new StringBuilder();
            while ((result = reader.readLine()) != null) {
                sb.append(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private static PostMethod buildPostMethod(String url, Map<String, String> paramMap) {
        PostMethod method = new PostMethod(url);
        NameValuePair[] pairs = new NameValuePair[paramMap.size()];
        int i = 0;
        for (Map.Entry entry : paramMap.entrySet()) {
            pairs[i++] = new NameValuePair(entry.getKey().toString(), entry.getValue().toString());
        }
        method.setRequestBody(pairs);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, Constant.UTF8_ENCODING);        // 中文乱码
        return method;
    }

    private static PostMethod buildPostMethod(String url, String data) {
        PostMethod method = new PostMethod(url);
        RequestEntity entity = null;
        try {
            entity = new StringRequestEntity(data, null, Constant.UTF8_ENCODING);
            method.setRequestEntity(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return method;
    }

    public static String getUrl(String url, Map<String, String> headers)
           {

        log.info("GET请求调用!" + url.toString());

        HttpURLConnection httpUrlConn = null;
        InputStream inputStream = null;
        try {
            URL geturl=new URL(url);
            httpUrlConn = (HttpURLConnection) geturl.openConnection();
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod("GET");

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                log.info("[Header]  key= " + ((String) entry.getKey())
                        + " ,value= " + ((String) entry.getValue()));
                httpUrlConn.setRequestProperty((String) entry.getKey(),
                        (String) entry.getValue());
            }

            inputStream = httpUrlConn.getInputStream();
            String reqmsg = IOUtils.toString(inputStream, "UTF-8");
            log.info("GET调用成功，返回：" + reqmsg);
            return reqmsg;
        } catch (Exception e){
            return  null;
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                  return null;
                }
                inputStream = null;
            }

            if (httpUrlConn != null)
                httpUrlConn.disconnect();
        }
    }
    public static String get(String url) {
        return get(url, new HashMap<String, String>());
    }
    public static String get(String url, Map<String, String> map) {
        log.info(url + "?" + map);

        HttpClient client = new HttpClient();
        HttpMethod method = buildGetMethod(url, map);
        StringBuilder sb = null;
        BufferedReader reader = null;
        try {
            int code = client.executeMethod(method);
            if (code != 200) return null;

            InputStream in = method.getResponseBodyAsStream();
            reader = new BufferedReader(new InputStreamReader(in, Constant.UTF8_ENCODING));
            String result;
            sb = new StringBuilder();
            while ((result = reader.readLine()) != null) {
                sb.append(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private static GetMethod buildGetMethod(String url, Map<String, String> paramMap) {
        GetMethod method = new GetMethod(url);
        NameValuePair[] pairs = new NameValuePair[paramMap.size()];
        int i = 0;
        for (Map.Entry entry : paramMap.entrySet()) {
            pairs[i++] = new NameValuePair(entry.getKey().toString(), entry.getValue().toString());
        }

        method.setQueryString(pairs);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");        // 中文乱码
        return method;
    }

    public static void main(String[] args) {
        String a = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=fer8Jm4mLCrKrR-L1DXsTw5ZxTn3v8KmM7TvGpU21kQjjA0vczMYPFGEkUxaiDKxpbZsrznk6ZXHNpCl82xug7fNsHHLCLPJzvNuKrtuoyW3Zyxat7YcZSxCQdsTMjSlUMOaAFDBTV&type=image";
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=fer8Jm4mLCrKrR-L1DXsTw5ZxTn3v8KmM7TvGpU21kQjjA0vczMYPFGEkUxaiDKxpbZsrznk6ZXHNpCl82xug7fNsHHLCLPJzvNuKrtuoyW3Zyxat7YcZSxCQdsTMjSlUMOaAFDBTV&";
        String im = "/Users/emp/Downloads/a.png";
        Map<String, String> param = new HashMap<>();
        param.put("id", "media");
        param.put("type", "image");

        System.out.println(postWeixinMaterial(url, im, param));
    }
    /**
     *调用内部系统post接口
     */
    public static  String postbody(String tourl,String body)
            throws Exception {
        URL  url=null;
        log.info("POST请求调用!" + tourl.toString() + body);

        HttpURLConnection httpUrlConn = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            url = new URL(tourl);
            httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod("POST");

            if (body != null) {
                byte[] b = body.getBytes("UTF-8");
                httpUrlConn.setRequestProperty("Charset", "UTF-8");
                httpUrlConn.setRequestProperty("Content-Type", "application/json");
                httpUrlConn.setRequestProperty("Content-length", String.valueOf(b.length));

                outputStream = httpUrlConn.getOutputStream();
                outputStream.write(b);
                outputStream.close();
            }

            inputStream = httpUrlConn.getInputStream();
            String reqmsg = IOUtils.toString(inputStream, "UTF-8");
            log.info("POST调用成功，返回：" + reqmsg);
            return reqmsg;
        } catch (Exception e){
           throw new Exception("error");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }

            if (httpUrlConn != null)
                httpUrlConn.disconnect();
        }
    }

    /**
     * 1原始post方法
     * @param strURL
     * @param params
     * @return
     */
    public static String postjson(String strURL, String params) {
        log.info("post方法：url=" + strURL + ",params=" + params);
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
            int length = (int) connection.getContentLength();// 获取长度
            InputStream is = connection.getInputStream();
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                String result = new String(data, "UTF-8"); // utf-8编码
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error"; // 自定义错误信息
    }

    public static String postAuth(String strURL, String params) {
        log.info("post方法：url=" + strURL + ",params=" + params);
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.setRequestProperty("Authorization","Basic cGkybWNvOlNhcDEyMzQ1Ng==");
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
            int length = (int) connection.getContentLength();// 获取长度
            InputStream is = connection.getInputStream();
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                String result = new String(data, "UTF-8"); // utf-8编码
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error"; // 自定义错误信息
    }
    public static String postAuth(String strURL, String params,String userName,String userPass) {
       log.info("post方法：url=" + strURL + ",params=" + params);
       try {
           URL url = new URL(strURL);// 创建连接
           HttpURLConnection connection = (HttpURLConnection) url
                   .openConnection();
           connection.setDoOutput(true);
           connection.setDoInput(true);
           connection.setUseCaches(false);
           connection.setInstanceFollowRedirects(true);
           connection.setRequestMethod("POST"); // 设置请求方式
           connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
           connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
           byte[] tokenByte = Base64.encodeBase64((userName+":"+userPass).getBytes());  
    	    	String tokenStr = new String(tokenByte);
    	    	//Basic YFUDIBGDJHFK78HFJDHF==    token的格式  
    	    	String token = "Basic "+tokenStr;
           connection.setRequestProperty("Authorization",token);
           connection.connect();
           OutputStreamWriter out = new OutputStreamWriter(
                   connection.getOutputStream(), "UTF-8"); // utf-8编码
           out.append(params);
           out.flush();
           out.close();
           // 读取响应
           int length = (int) connection.getContentLength();// 获取长度
           InputStream is = connection.getInputStream();
           if (length != -1) {
               byte[] data = new byte[length];
               byte[] temp = new byte[512];
               int readLen = 0;
               int destPos = 0;
               while ((readLen = is.read(temp)) > 0) {
                   System.arraycopy(temp, 0, data, destPos, readLen);
                   destPos += readLen;
               }
               String result = new String(data, "UTF-8"); // utf-8编码
               return result;
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
       return "error"; // 自定义错误信息
   }
}
