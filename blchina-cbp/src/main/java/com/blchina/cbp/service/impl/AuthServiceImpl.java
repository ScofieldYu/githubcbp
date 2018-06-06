/*
 * @(#)AuthServiceImpl.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.impl;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.CustmerDTO;
import com.blchina.cbp.exception.BusinessException;
import com.blchina.cbp.model.BDLCustomer;
import com.blchina.cbp.service.interfaces.AuthService;
import com.fadada.sdk.client.FddClientBase;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 *认证service
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("AuthService")
public class AuthServiceImpl implements AuthService {
    @Autowired
    protected Properties systemConfig;

    @Override
    public WeixinResult seltauth(BDLCustomer customer) {
        WeixinResult res=new WeixinResult();
        res.setCode(CBPConstant.CODE_LOSE);
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String time=sdf.format(date);
        //去法大大认证
        try {
            String app_id=systemConfig.getProperty("fadada.appid");
            String secret=systemConfig.getProperty("fadada.secret");
            String sha1 = FddEncryptTool.sha1(app_id + FddEncryptTool.md5Digest(time) + FddEncryptTool.sha1(secret));
            String msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("name", customer.getCustomername()));
            params.add(new BasicNameValuePair("app_id",app_id));
            params.add(new BasicNameValuePair("timestamp",time));
            String encrypt = FddEncryptTool.encrypt(customer.getIdcardnum(),secret);
            params.add(new BasicNameValuePair("idCard",encrypt));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));
            FddEncryptTool.decrypt(encrypt,secret);
            //调用二要素认证
            String response = HttpsUtil.doPost(systemConfig.getProperty("fadada.checkIdCard"), params);
            JSONObject getJSON=JSONObject.fromObject(response);
            String result=(String) getJSON.get("result");
            if(result.equals("success")){//成功去CA认证
                FddClientBase clientbase = new FddClientBase(app_id, secret, "", systemConfig.getProperty("fadada.selfauth"));
                String responseCA = clientbase.invokeSyncPersonAuto(customer.getCustomername(), "", customer.getIdcardnum(),
                        "0", String.valueOf(customer.getPhonenumber()));
               /* String sha1CA = FddEncryptTool.sha1(app_id + FddEncryptTool.md5Digest(time) + FddEncryptTool.sha1(secret+encrypt));
                String msgDigestCA = new String(FddEncryptTool.Base64Encode(sha1CA.getBytes()));
                ArrayList paramsCA = new ArrayList();
                paramsCA.add(new BasicNameValuePair("customer_name", customer.getCustomername()));
                paramsCA.add(new BasicNameValuePair("app_id",app_id));
                paramsCA.add(new BasicNameValuePair("timestamp",time));
                paramsCA.add(new BasicNameValuePair("id_card",encrypt));
                paramsCA.add(new BasicNameValuePair("msg_digest", msgDigestCA));
                String responseCA = HttpsUtil.doPost(systemConfig.getProperty("fadada.selfauth"), paramsCA);*/
                JSONObject jsonObject=JSONObject.fromObject(responseCA);
                if(jsonObject.get("result").equals("success")){
                    res.setCode(CBPConstant.CODE_SUCCESS);
                }else {
                    res.setCode(CBPConstant.CODE_FAILURE);
                }

                res.setData(responseCA);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return res;
        }
    }

    @Override
    public WeixinResult companyauth(CustmerDTO customer) {
        WeixinResult res=new WeixinResult();
        res.setCode(CBPConstant.CODE_LOSE);
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String time=sdf.format(date);
        //去法大大认证
        try {
            if(customer.getEmail()==null||customer.getEmail().indexOf("@")<0) {
                customer.setEmail("123456@qq.com");
            }
            String app_id=systemConfig.getProperty("fadada.appid");
            String secret=systemConfig.getProperty("fadada.secret");
            String encrypt = FddEncryptTool.encrypt(customer.getOrganizecode()+"|"+customer.getPhonenumber(),secret);
            String sha1 = FddEncryptTool.sha1(app_id + FddEncryptTool.md5Digest(time) + FddEncryptTool.sha1(secret+customer.getOrganizename()+customer.getEmail()+encrypt));
            String msgDigest = new String(FddEncryptTool.Base64Encode(sha1.getBytes()));
            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("customer_name", customer.getOrganizename()));
            params.add(new BasicNameValuePair("app_id",app_id));
            params.add(new BasicNameValuePair("email",customer.getEmail()));
            params.add(new BasicNameValuePair("timestamp",time));
            params.add(new BasicNameValuePair("id_mobile",encrypt));
            params.add(new BasicNameValuePair("msg_digest", msgDigest));

            //调用二要素认证
            String response = HttpsUtil.doPost(systemConfig.getProperty("fadada.companyAuth"), params);
            JSONObject getJSON=JSONObject.fromObject(response);
            String result=(String) getJSON.get("result");
            if(result.equals("success")){
                res.setCode(CBPConstant.CODE_SUCCESS);
                res.setData((String)getJSON.get("customer_id"));
            }else {
                res.setCode(CBPConstant.CODE_FAILURE);
            }

            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return res;
        }
    }

    @Override
    public String autosign(String signcustomerid, Integer contractid) {
        String appId=systemConfig.getProperty("fadada.appid");
        String secret=systemConfig.getProperty("fadada.secret");
        FddClientBase client = new FddClientBase(appId, secret,"2",systemConfig.getProperty("fadada.autosign"));
        String response = client.invokeExtSignAuto(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+String.valueOf(contractid),signcustomerid,"1",String.valueOf(contractid),"自动签署","公司签章处","2","");
        JSONObject jsonObject=JSONObject.fromObject(response);
        String result=(String)jsonObject.get("result");
        if(result.equals("success")){
            String docurl=(String)jsonObject.get("download_url");
            return docurl;
        }
        return null;
    }

    @Override
    public String uploadFile(File file, String constratid) {
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String time=sdf.format(date);
        //去法大大认证
        try {
            String app_id=systemConfig.getProperty("fadada.appid");
            String secret=systemConfig.getProperty("fadada.secret");
            FddClientBase clientbase = new FddClientBase(app_id, secret, "", systemConfig.getProperty("fadada.uploaddocs"));
            String response = clientbase.invokeUploadDocs(constratid, "合同上传", file, "",
                    ".pdf");
            //调用二要素认证
            JSONObject getJSON=JSONObject.fromObject(response);
            String result=(String) getJSON.get("result");
            if(result.equals("success")){
                return "success";
            }else {
                throw new BusinessException("1",response);
            }
        } catch (Exception e) {
            e.printStackTrace();
           throw new BusinessException("1","上传合同失败");
        }
    }
}
