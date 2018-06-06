/*   
 * @(#)CommonUtil.java       2017年12月14日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.datamodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

/** 
 * 通用工具类    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class CommonUtil {
   private static Logger log = Logger.getLogger(CommonUtil.class);
   
   public static JSONObject httpsRequestToJsonObject(String requestUrl, String requestMethod, String outputStr) {
    JSONObject jsonObject = null;
    try {
    StringBuffer buffer = httpsRequest(requestUrl, requestMethod, outputStr);
    jsonObject = JSONObject.fromObject(buffer.toString());
    } catch (ConnectException ce) {
    log.error("连接超时："+ce.getMessage());
    } catch (Exception e) {
    log.error("https请求异常："+e.getMessage());
    }
    return jsonObject;
   }
   
   private static StringBuffer httpsRequest(String requestUrl, String requestMethod, String output)
    throws NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException, MalformedURLException,
    IOException, ProtocolException, UnsupportedEncodingException {
    URL url = new URL(requestUrl);
    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
    connection.setDoOutput(true);
    connection.setDoInput(true);
    connection.setUseCaches(false);
    connection.setRequestMethod(requestMethod);
    if (null != output) {
    OutputStream outputStream = connection.getOutputStream();
    outputStream.write(output.getBytes("UTF-8"));
    outputStream.close();
    }
    // 从输入流读取返回内容
    InputStream inputStream = connection.getInputStream();
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    String str = null;
    StringBuffer buffer = new StringBuffer();
    while ((str = bufferedReader.readLine()) != null) {
    buffer.append(str);
    }
    bufferedReader.close();
    inputStreamReader.close();
    inputStream.close();
    inputStream = null;
    connection.disconnect();
    return buffer;
   } 
   
   
}
