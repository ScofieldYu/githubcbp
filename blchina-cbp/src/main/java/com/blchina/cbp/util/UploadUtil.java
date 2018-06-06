/*
 * @(#)UploadUtil.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.util;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.dto.UploadDTO;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.common.util.encode.EncodeUtil;
import com.blchina.common.util.http.HttpUtil;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.entity.mime.content.StringBody;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 文件上传util
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public class UploadUtil {
    // 每个post参数之间的分隔。随意设定，只要不会和其他的字符串重复即可。
    private static final String BOUNDARY = "----------HV2ymHFg03ehbqgZCaKO6jyH";

    public static String uploadByFile(String serverUrl,List<File> filelist,List<String> urllist, UploadDTO uploadDTO,String titleId,String storeid,String secret) throws Exception {
        String end = "\r\n";
        String twoHyphens = "--";
        String response = "";
        try{
            URL url = new URL(serverUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            //发送post请求需要下面两行
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //设置请求参数
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            //获取请求内容输出流
            DataOutputStream ds = new DataOutputStream(connection.getOutputStream());
            //开始写表单格式内容
            //写参数
            Map<String,String> parameters = new HashMap<String,String>();
            String timestamp = System.currentTimeMillis()+"";
            String temp = EncodeUtil.getMD5(secret.getBytes()) + timestamp + "";
            String sign = FddEncryptTool.sha1(temp);
            if(uploadDTO.getSapOrderId()!=null){
                parameters.put("orderId",uploadDTO.getSapOrderId());
            }
            if(uploadDTO.getIdcardnum()!=null){
                parameters.put("idCardNum",uploadDTO.getIdcardnum());
            }
            if(storeid!=null){
                parameters.put("storeId", storeid);
            }
            if(titleId!=null){
                parameters.put("titleId", titleId);
            }
            if(uploadDTO.getType().equals(CBPConstant.FileEnum.CUSTOMERIDCARD.getType())){
                uploadDTO.setType("10001");
            }else if(uploadDTO.getType().equals(CBPConstant.FileEnum.BUYERIDCARD.getType())){
                uploadDTO.setType("10002");
            }else if(uploadDTO.getType().equals(CBPConstant.FileEnum.BUSINLICENSE.getType())){
                uploadDTO.setType("10003");
            }else if(uploadDTO.getType().equals(CBPConstant.FileEnum.ATTORNEY.getType())){
                uploadDTO.setType("10004");
            }else if(uploadDTO.getType().equals(CBPConstant.FileEnum.RECEIPT.getType())){
                uploadDTO.setType("10005");
            }else if(uploadDTO.getType().equals(CBPConstant.FileEnum.CONTRACT.getType())){
                uploadDTO.setType("10006");
            }else if(uploadDTO.getType().equals(CBPConstant.FileEnum.ADVANCEDPAID.getType())){
                uploadDTO.setType("10007");
            }
            parameters.put("fileTypeId", uploadDTO.getType());
            parameters.put("terminalType", "2");
            parameters.put("timestamp", timestamp);
            parameters.put("sign", sign);
            Set<String> keys = parameters.keySet();
            for(String key : keys){
                ds.writeBytes(twoHyphens + BOUNDARY + end);
                ds.writeBytes("Content-Disposition: form-data; name=\"");
                ds.write(key.getBytes());
                ds.writeBytes("\"" + end);
                ds.writeBytes(end);
                ds.write(parameters.get(key).getBytes());
                ds.writeBytes(end);
            }
            // 2. 处理文件上传
            for (int i = 0; i < filelist.size(); i++) {
                //写文件
                ds.writeBytes(twoHyphens + BOUNDARY + end);
                ds.writeBytes("Content-Disposition: form-data; " + "name=\"file\"; " + "filename=\"");
                //防止中文乱码
                ds.write(filelist.get(i).getName().getBytes());
                ds.writeBytes("\"" + end);
                ds.writeBytes("Content-Type: " + filelist.get(i).getName().substring(filelist.get(i).getName().indexOf(".")-1,filelist.get(i).getName().length()) + end);
                ds.writeBytes(end);
                //根据路径读取文件
                FileInputStream fis = new FileInputStream(filelist.get(i));
                byte[] buffer = new byte[1024];
                int length = -1;
                while((length = fis.read(buffer)) != -1){
                    ds.write(buffer, 0, length);
                }
                ds.writeBytes(end);
                fis.close();
            }
            List<MultipartFileBean> urlList = new ArrayList<MultipartFileBean>();
            for (int i = 0; i<urllist.size(); i++) {
                MultipartFileBean bean = new MultipartFileBean();
                bean.setDocUrl(urllist.get(i));
                bean.setFileName("垫付证明"+i);
                bean.setSuffix(".png");
                urlList.add(bean);
            }
            JSONArray array =null;
            if(urllist.size()!=0){
                array = JSONArray.fromObject(urlList);
            }
            ds.writeBytes(twoHyphens + BOUNDARY + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"");
            ds.write("docUrl".getBytes());
            ds.writeBytes("\"" + end);
            ds.writeBytes(end);
            if(array!=null){
                ds.write(array.toString().getBytes());
            }else {
                ds.write("".getBytes());
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + BOUNDARY + twoHyphens + end);
            ds.writeBytes(end);
            ds.flush();
            try{
                //获取URL的响应
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                String s = "";
                String reStr = "";
                while((reStr = reader.readLine()) != null){
                    s += reStr;
                }
                response = s;
                reader.close();
            }catch(IOException e){
                e.printStackTrace();
                System.out.println("No response get!!!");
            }
            ds.close();
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Request failed!");
        }
        return response;
    }

    public static String uploadByUrl(String serverUrl,String docurl, UploadDTO uploadDTO, String titleId,String storeId,String secret,String type)  throws Exception{
        String end = "\r\n";
        String twoHyphens = "--";
        String response = "";
        try{
            URL url = new URL(serverUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            //发送post请求需要下面两行
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //设置请求参数
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            //获取请求内容输出流
            DataOutputStream ds = new DataOutputStream(connection.getOutputStream());
            //开始写表单格式内容
            //写参数
            Map<String,String> parameters = new HashMap<String,String>();
            String timestamp = System.currentTimeMillis()+"";
            String temp = EncodeUtil.getMD5(secret.getBytes()) + timestamp + "";
            String sign = FddEncryptTool.sha1(temp);
            parameters.put("orderId",uploadDTO.getSapOrderId());
            if(uploadDTO.getIdcardnum()!=null){
                parameters.put("idCardNum",uploadDTO.getIdcardnum());
            }
            if(storeId!=null){
                parameters.put("storeId", storeId);
            }
            if(titleId!=null){
                parameters.put("titleId", titleId);
            }
            if(uploadDTO.getType().equals(CBPConstant.FileEnum.CUSTOMERIDCARD.getType())){
                uploadDTO.setType("10001");
            }else if(uploadDTO.getType().equals(CBPConstant.FileEnum.BUYERIDCARD.getType())){
                uploadDTO.setType("10002");
            }else if(uploadDTO.getType().equals(CBPConstant.FileEnum.BUSINLICENSE.getType())){
                uploadDTO.setType("10003");
            }else if(uploadDTO.getType().equals(CBPConstant.FileEnum.ATTORNEY.getType())){
                uploadDTO.setType("10004");
            }else if(uploadDTO.getType().equals(CBPConstant.FileEnum.RECEIPT.getType())){
                uploadDTO.setType("10005");
            }else if(uploadDTO.getType().equals(CBPConstant.FileEnum.CONTRACT.getType())){
                uploadDTO.setType("10006");
            }else if(uploadDTO.getType().equals(CBPConstant.FileEnum.ADVANCEDPAID.getType())){
                uploadDTO.setType("10007");
            }
            parameters.put("fileTypeId", uploadDTO.getType());
            parameters.put("terminalType", "2");
            parameters.put("timestamp", timestamp);
            parameters.put("sign", sign);
            Set<String> keys = parameters.keySet();
            for(String key : keys){
                ds.writeBytes(twoHyphens + BOUNDARY + end);
                ds.writeBytes("Content-Disposition: form-data; name=\"");
                ds.write(key.getBytes());
                ds.writeBytes("\"" + end);
                ds.writeBytes(end);
                ds.write(parameters.get(key).getBytes());
                ds.writeBytes(end);
            }
                MultipartFileBean bean = new MultipartFileBean();
                bean.setDocUrl(docurl);
            if(type.equals("1")){
                bean.setFileName("手动签合同文档");
            }else if(type.equals("2")){
                bean.setFileName("自动签合同文档");
            }else {
                bean.setFileName("合同文档");
            }

                bean.setSuffix(".pdf");
            List<MultipartFileBean> urlList = new ArrayList<MultipartFileBean>();
            urlList.add(bean);
            JSONArray array = JSONArray.fromObject(urlList);
            ds.writeBytes(twoHyphens + BOUNDARY + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"");
            ds.write("docUrl".getBytes());
            ds.writeBytes("\"" + end);
            ds.writeBytes(end);
            ds.write(array.toString().getBytes());
            ds.writeBytes(end);

            ds.writeBytes(twoHyphens + BOUNDARY + twoHyphens + end);
            ds.writeBytes(end);
            ds.flush();
            try{
                //获取URL的响应
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                String s = "";
                String reStr = "";
                while((reStr = reader.readLine()) != null){
                    s += reStr;
                }
                response = s;
                reader.close();
            }catch(IOException e){
                e.printStackTrace();
                System.out.println("No response get!!!");
            }
            ds.close();
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Request failed!");
        }
        return response;
    }
    
    public static void main(String[] args) {
 	  
 	  System.out.println(formatData("20171228163638"));
    }
    
    public static String formatData(String data){
 		 StringBuffer sb = new StringBuffer();
 		 sb.append(data.substring(0, 4)).append("年")
 		 .append(data.substring(4, 6)).append("月")
 		 .append(data.substring(6, 8)).append("日");
 	  return sb.toString();
    }
}
