/*
 * @(#)WxServiceImpl.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.blchina.cbp.model.CBPLogistics;
import com.blchina.cbp.service.interfaces.LogisticsService;
import com.blchina.cbp.service.interfaces.UserService;
import com.blchina.cbp.service.interfaces.WxService;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.http.HttpsConnection;
import com.blchina.common.util.redis.RedisUtil;
import com.blchina.common.util.weixin.WeixinUtil;

import net.sf.json.JSONObject;

/**
 * wx service
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("WxService")
public class WxServiceImpl implements WxService {
    private static Logger log = Logger.getLogger(WxServiceImpl.class);
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    protected Properties systemConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private LogisticsService logisticsService;
    @Value("${saler.message.configuration.fiveinternal}")
    private String fiveinternal;
    @Value("${saler.message.check}")
    private String check;
    @Value("${saler.message.configuration.fiveexternal}")
    private String fiveexternal;
    
    @Override
    public String getWxToken(String brandid,String type) {
        String access_token = null;

            try {
                String secret = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/store/getSecretStoreId", "{\"brandid\":" + brandid + "}");
                String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
                        + "&appid=" + secret.split(",")[0]
                        + "&secret=" + secret.split(",")[1];
                String response = HttpsConnection.doGet(url);
                log.info(response);
                JSONObject getJson = JSONObject.fromObject(response);
                access_token = (String) getJson.get("access_token");
                String templateId="";
                if(type.equals("1")){
                    templateId=secret.split(",")[2];
                }else if(type.equals("2")) {
                    templateId=secret.split(",")[3];
                }else {
                    templateId=secret.split(",")[4];
                }
                return access_token+","+templateId;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


    }

    public String getTemplateId(String template, String accessToken) {
        String templateId = null;
        String url = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=" + accessToken;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("template_id_short", template);
            String response = HttpsConnection.doPost(url, jsonObject.toString(), false);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return templateId;

    }

    @Override
    public String getToken() {
        String access_token = null;
        boolean flag = redisUtil.exists("access_token_corp");
        if (flag) {
            access_token = redisUtil.get("access_token_corp");
        } else {
            String url = systemConfig.getProperty("enterprise.bdlchina.wx.url")
                    + "gettoken?" + "corpid=" + systemConfig.getProperty("enterprise.bdlchina.wx.reservecar.corpID")
                    + "&corpsecret=" + systemConfig.getProperty("enterprise.bdlchina.wx.reservecar.secret");
            try {
                String response = HttpsConnection.doGet(url);
                JSONObject getJson = JSONObject.fromObject(response);
                Integer errcode = (Integer) getJson.get("errcode");
                if (errcode.intValue() == 0) {
                    access_token = (String) getJson.get("access_token");
                    redisUtil.setex("access_token_corp", 7100, access_token);
                    return access_token;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
        return access_token;
    }

    /**
     * 给销售顾问推送设置预约时间
     *
     * @param userid
     * @param orderid
     * @param day
     * @param sapId
     * @param employeeid
     * @return
     */
    @Override
    public Boolean pushCarStart(String userid, Integer orderid, String day, String sapId, Integer employeeid) {
        String token = getToken();
        if (token == null) {
            return false;
        }
        String content = MessageFormat.format(fiveinternal, sapId, day);
        String bcpurl = systemConfig.getProperty("bcp.url");
        String url = bcpurl + "/new-bookingHandCar.html?orderid=" + orderid + "&employeeid=" + employeeid;
        String title = "预约通知";
        String tourl = systemConfig.getProperty("enterprise.bdlchina.wx.url") + "message/send?access_token=" + token;
        return WeixinUtil.pushMessage(userid, content, title, url, tourl, systemConfig.getProperty("enterprise.bdlchina.wx.reservecar.agentId"));
    }

    /**
     * 给销售顾问推送查询交车任务
     *
     * @param userid
     * @param customerName
     * @param date
     * @param starttime
     * @param endtime
     * @param orderid
     * @param employeeid
     */
    @Override
    public void pushViewMessage(String userid, String customerName, String date, String starttime, String endtime, Integer orderid, Integer employeeid, String storeid, String flag) {
        String token = getToken();
        if (token == null) {
            return;
        }
        String content = MessageFormat.format(check, customerName, date, starttime, endtime);
        String bcpurl = systemConfig.getProperty("bcp.url");
        String url = bcpurl + "/checkTransferTheCar.html?type=1&orderid=" + orderid + "&employeeid=" + employeeid + "&storeid=" + storeid;
        String title = null;
        if (flag.equals("1")) {
            title = "查看客户预约时间";
        } else {
            title = "查看客户修改预约时间";
        }

        String tourl = systemConfig.getProperty("enterprise.bdlchina.wx.url") + "message/send?access_token=" + token;
        WeixinUtil.pushMessage(userid, content, title, url, tourl, systemConfig.getProperty("enterprise.bdlchina.wx.reservecar.agentId"));

    }

    @Override
    public Boolean pustSetTime(String userid, String saporderid, Integer orderid, Integer employeeid) {
        String token = getToken();
        if (token == null) {
            return false;
        }
        String content = MessageFormat.format(fiveexternal, saporderid);
        String bcpurl = systemConfig.getProperty("bcp.url");
        String url = bcpurl + "/new-bookingHandCar.html?orderid=" + orderid + "&employeeid=" + employeeid;
        String title = "预约通知";
        String tourl = systemConfig.getProperty("enterprise.bdlchina.wx.url") + "message/send?access_token=" + token;
        return WeixinUtil.pushMessage(userid, content, title, url, tourl, systemConfig.getProperty("enterprise.bdlchina.wx.reservecar.agentId"));
    }

    /**
     * 给客户推送物流消息
     *
     * @param shopid
     * @param sapID
     * @param openid
     * @param customername
     * @return
     */
    @Override
    public Boolean pushLogisticsStart(String shopid, String sapID, String openid, String customername, Integer orderid, String cartype, String status) {
        String baseurl = systemConfig.getProperty("service.url");
        //   String AID=Base64Util.encode(systemConfig.getProperty("service.AppID"));+"&AID="+AID+"&ASec="+ASec
        //  String ASec=Base64Util.encode(systemConfig.getProperty("service.AppSecret"));
        try {
            String type=systemConfig.getProperty("message.type");
            if(type.equals("1")){
                int TemplateCode=1;
                String First= URLEncoder.encode("客户:"+customername, "utf-8");
                String getcartype=URLEncoder.encode(cartype, "utf-8");
                String getstatus=URLEncoder.encode(status, "utf-8");
                String bcpurl=systemConfig.getProperty("bcp.url");
                String redirectUrl=bcpurl+"/logisticsInformation.html?orderid="+orderid;
                String url=baseurl+"brandId="+shopid+"&OID="+openid+"&TemplateCode="+TemplateCode+"&first="+First+
                        "&keyword1="+sapID+"&keyword2="+getcartype+"&remark="+getstatus+"&redirectUrl="+redirectUrl;
                String result = HttpUtil.getUrl(url,new HashMap<String, String>());
                if(result!=null&&result.equals("\"ok\"")){
                    return true;
                }
                return false;
            }else {
                String First = URLEncoder.encode("客户:" + customername, "utf-8");
                String bcpurl = systemConfig.getProperty("bcp.url");
                String redirectUrl = bcpurl + "/logisticsInformation.html?orderid=" + orderid;
                String getInfo = getWxToken(shopid,"1");
                String accesstoken=getInfo.split(",")[0];
                String templateId=getInfo.split(",")[1];
                JSONObject dataJson=new JSONObject();
                dataJson.put("first","{\"value\":"+"\"客户" + customername+"\",\"color\":\"#173177\"}");
                dataJson.put("keyword1","{\"value\":"+"\"" + sapID+"\",\"color\":\"#173177\"}");
                dataJson.put("keyword2","{\"value\":"+"\"" + cartype+"\",\"color\":\"#173177\"}");
                dataJson.put("remark","");
                Boolean flag = WeixinUtil.pushTemplateMassage(accesstoken, templateId, openid, dataJson.toString(), redirectUrl);
                return flag ;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getUserId(String code) {
        String token = getToken();
        String url = systemConfig.getProperty("enterprise.bdlchina.getuser") + "access_token=" + token + "&code=" + code;
        try {
            String s = HttpsConnection.doGet(url);
            JSONObject json = JSONObject.fromObject(s);
            Integer errcode = Integer.valueOf(String.valueOf(json.get("errcode")));
            if (errcode.intValue() == 0) {
                return (String) json.get("UserId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getEmployee(String username) {
        String token = getToken();
        String tourl = systemConfig.getProperty("enterprise.bdlchina.wx.url") + "user/get?access_token=" + token + "&userid=" + username;
        try {
            String result = HttpsConnection.doGet(tourl);
            JSONObject json = JSONObject.fromObject(result);
            String avatar = (String) json.get("avatar");
            String name = (String) json.get("name");
            return avatar + "," + name;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getEmployeeByCode(String code) {
        String token = getToken();
        String tourl = systemConfig.getProperty("enterprise.bdlchina.wx.url") + "user/getuserinfo?access_token=" + token + "&code=" + code;
        try {
            String result = HttpsConnection.doGet(tourl);
            JSONObject json = JSONObject.fromObject(result);
            String userid = (String) json.get("UserId");
            String getresult = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployeeByUserid", "{\"email\":\"" + userid + "\"}");
            String[] split = getresult.split(",");
            return split[0] + "," + split[1];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 给客户推送预约轿车
     *
     * @param shopid
     * @param sapID
     * @param openid
     * @param customername
     * @param orderid
     * @param employeeid
     * @return
     */
    @Override
    public Boolean pushCustomerStart(String shopid, String sapID, String openid, String customername, Integer orderid, Integer employeeid) {
        String baseurl = systemConfig.getProperty("service.url");
        //   String AID=Base64Util.encode(systemConfig.getProperty("service.AppID"));+"&AID="+AID+"&ASec="+ASec
        //  String ASec=Base64Util.encode(systemConfig.getProperty("service.AppSecret"));
        String OID = openid;
        int TemplateCode=2;
        try {
            String type=systemConfig.getProperty("message.type");
            if(type.equals("1")){
                String First= URLEncoder.encode("您的车辆已到店，请您设置预约轿车时间", "utf-8");
                String keyword1="1";
                String remark="1";
                String bcpurl=systemConfig.getProperty("bcp.url");
                String redirectUrl=bcpurl+"/new-clientAppointmentTime.html?data=employeeid-"+employeeid+"-orderid-"+orderid+"-storeid-"+shopid;
                String url=baseurl+"brandId="+shopid+"&OID="+openid+"&TemplateCode="+TemplateCode+"&first="+First+
                        "&keyword1="+keyword1+"&keyword2="+keyword1+"&remark="+remark+"&redirectUrl="+redirectUrl;
                String result = HttpUtil.getUrl(url,new HashMap<String, String>());
                if(result!=null&&result.equals("\"ok\"")){
                    CBPLogistics record=new CBPLogistics();
                    record.setSequence("0050");
                    record.setOrderid(orderid);
                    record.setSenddate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
                    record.setStatus("车辆已到店");
                    //物流信息
                    logisticsService.insertOrUpdateLogistic(record);
                    return true;

                }
                return false;
            }else {
                String getInfo = getWxToken(shopid,"2");
                String accesstoken=getInfo.split(",")[0];
                String templateId=getInfo.split(",")[1];
                JSONObject dataJson=new JSONObject();
                dataJson.put("first","{\"value\":"+"\"您的车辆已到店，请您设置预约轿车时间\"}");
                dataJson.put("keyword1","{\"value\":"+"\"暂无\",\"color\":\"#173177\"}");
                dataJson.put("keyword2","{\"value\":"+"\"点击后设置\",\"color\":\"#173177\"}");
                dataJson.put("keynote3","");
                dataJson.put("remark","");
                String bcpurl = systemConfig.getProperty("bcp.url");
                String redirectUrl = bcpurl + "/new-clientAppointmentTime.html?data=employeeid-" + employeeid + "-orderid-" + orderid + "-storeid-" + shopid;
                WeixinUtil.pushTemplateMassage(accesstoken, templateId, openid,dataJson.toString(), redirectUrl);
                CBPLogistics record = new CBPLogistics();
                record.setSequence("0050");
                record.setOrderid(orderid);
                record.setSenddate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
                record.setStatus("车辆已到店");
                //物流信息
                logisticsService.insertOrUpdateLogistic(record);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 给客户推送预约轿车
     *
     * @param shopid
     * @param openid
     * @return
     */
    @Override
    public Boolean pushCustomerTime(String shopid, String openid, String content) {
        String baseurl = systemConfig.getProperty("service.url");
        String OID = openid;
        int TemplateCode = 2;
            try {
                String flag=systemConfig.getProperty("message.type");
                if(flag.equals("1")){
                    String First=URLEncoder.encode(content,"utf-8");
                    String keyword1="1";
                    String remark="1";
                    String redirectUrl="#";
                    String url=baseurl+"brandId="+shopid+"&OID="+openid+"&TemplateCode="+TemplateCode+"&first="+First+
                            "&keyword1="+keyword1+"&keyword2="+keyword1+"&remark="+remark+"&redirectUrl="+redirectUrl;
                    String result = HttpUtil.getUrl(url,new HashMap<String, String>());
                    if(result!=null&&result.equals("\"ok\"")){
                        return true;

                    }
                    return false;
                }else {
                    String getInfo = getWxToken(shopid,"2");
                    String accesstoken=getInfo.split(",")[0];
                    String templateId=getInfo.split(",")[1];
                    JSONObject dataJson=new JSONObject();
                    dataJson.put("first","{\"value\":"+content+"}");
                    dataJson.put("keyword1","{\"value\":"+"\"暂无\",\"color\":\"#173177\"}");
                    dataJson.put("keyword2","{\"value\":\""+content+"\",\"color\":\"#173177\"}");
                   // dataJson.put("keyword3","");
                   // dataJson.put("remark","");
                    String bcpurl = systemConfig.getProperty("bcp.url");
                    String redirectUrl = "#";
                    WeixinUtil.pushTemplateMassage(accesstoken, templateId, openid,dataJson.toString(), redirectUrl);
                    return true;
                }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 给财务消息通知
     *
     * @param customerId
     * @param money
     * @param brandid
     */
    @Override
    public void pushPayMoney(Integer customerId, String money, String brandid) {
        String token = getToken();
        if (token == null) {
            return;
        }
        String userid = null;
        String getCustomer = null;
        String customername = null;
        String sapCustomerid = null;
        try {
            userid = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/store/getPayNoticeUser", "{\"brandid\":" + brandid + "}");
            getCustomer = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getSapCustomerId", "{\"customerid\":" + customerId + "}");
            customername = getCustomer.split(",")[0];
            sapCustomerid = getCustomer.split(",")[1];
            String content = "有新的客户成功使用微信\n支付购车定金，信息如下:\n客户姓名:" + customername + "\n客户编码:" + sapCustomerid
                    + "\n到账金额:" + money
                    + "\n到账时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String tourl = systemConfig.getProperty("enterprise.bdlchina.wx.url") + "message/send?access_token=" + token;
            WeixinUtil.pushtextMessage(userid, content, tourl, systemConfig.getProperty("enterprise.bdlchina.wx.reservecar.agentId"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 给员工推送卡片相关消息
     * @param userid 员工
     * @param url   消息链接地址
     * @param content 显示内容
     * @param title 消息显示标题
     */
    @Override
    public void pushCardToEmployee(String userid, String url, String content,String title) {
        String token = getToken();
        if (token == null) {
            return;
        }
         url = systemConfig.getProperty("bcp.url")+url;
        String tourl = systemConfig.getProperty("enterprise.bdlchina.wx.url") + "message/send?access_token=" + token;
        WeixinUtil.pushMessage(userid, content, title, url, tourl, systemConfig.getProperty("enterprise.bdlchina.wx.reservecar.agentId"));
    }

    /**
     * 给客户推送卡片消息
     * @param shopid  门店id
     * @param openid
     * @param content 显示内容
     *
     */
    @Override
    public void pushCardToCustomer(String shopid, String openid,String customername, String content,String tourl,String title) {
        String getInfo = getWxToken(shopid,"3");
        String accesstoken=getInfo.split(",")[0];
        String templateId=getInfo.split(",")[1];
        JSONObject dataJson=new JSONObject();
        dataJson.put("first",title);
        dataJson.put("XM","{\"value\":"+customername+"}");
        dataJson.put("KH","{\"value\":"+"\"暂无\"}");
        dataJson.put("Title",title);
        dataJson.put("Url",tourl);
        dataJson.put("CONTENTS","{\"value\":"+content+"}");
        tourl = systemConfig.getProperty("bcp.url")+tourl;
        WeixinUtil.pushTemplateMassage(accesstoken, templateId, openid,dataJson.toString(), tourl);

    }
    /**
     * 给承保顾问发送消息
     *
     */
    @Override
    public void pushMessageToInsurance(String brandid,String customername,Integer orderid,String tourl) {
        String result = null;
        try {
            result = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployUserIdAndEmployeeId", "{\"companycode\":\"" + brandid + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
        }
         String[] splitOne = result.split(",");
         String insuranceEmployeeId=splitOne[0];
         String insuranceEmployeeUserid="yuzhicheng";//splitOne[1];
         String contentInsurance="请为"+customername+"（订单编号"+orderid+"）办理车险";
         pushCardToEmployee(insuranceEmployeeUserid,tourl,contentInsurance,"车险办理");
    }

    /**
     * 给精品顾问推送消息
     * @param brandid 门店id
     * @param customername 客户姓名
     * @param orderid  订单id
     * @param tourl  跳转地址
     */
    @Override
    public String pushMessageToboutique(String brandid, String customername, Integer orderid, String tourl) {
        String result = null;
        String accounttype = null;
        try {
            result = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/userRole/getboutiqueEmployUserIdAndEmployeeId", "{\"companycode\":\"" + brandid + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] splitOne = result.split(",");
        String insuranceEmployeeId=splitOne[0];
        String insuranceEmployeeUserid=splitOne[1];//splitOne[1];
        accounttype = userService.getTitleIdByEmployeeId(Integer.valueOf(insuranceEmployeeId));
        String contentInsurance="请为"+customername+"（订单编号"+orderid+"）精品加装";
        pushCardToEmployee(insuranceEmployeeUserid,tourl+"?employeeid="+insuranceEmployeeId+"&accounttype="+accounttype,contentInsurance,"精品加装");
        return insuranceEmployeeId;
    }
    /**
     * 给财务推送消息
     * @param brandid 门店id
     * @param customername 客户姓名
     * @param orderid  订单id
     * @param tourl  跳转地址
     */
    @Override
    public String pushMessageToFinance(String brandid, String customername, Integer orderid, String tourl) {
        String userid = null;
        String email = null;
        String employeeid = null;
        String accounttype = null;
        try {
            userid = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/userRole/getPayNoticeUser", "{\"companycode\":" + brandid + "}");
            String[] split = userid.split(",");
            employeeid = split[0];
            email = split[1];
            accounttype = userService.getTitleIdByEmployeeId(Integer.valueOf(employeeid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String contentInsurance="请为"+customername+"（订单编号"+orderid+"）确认收款";
        pushCardToEmployee(email,tourl+"?employeeid="+employeeid+"&accounttype="+accounttype,contentInsurance,"收款通知");
        return employeeid;
    }
    /**
     * 给二手车评估师推送消息
     * @param brandid 门店id
     * @param customername 客户姓名
     * @param orderid  订单id
     * @param tourl  跳转地址
     * @param content  跳转地址
     */
    @Override
    public void pushMessageToSecondCarConsultant(String brandid, String customername, Integer orderid, String tourl,String time) {
        String result = null;
        try {
            result = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getSecendCarEmployUserIdAndEmployeeId", "{\"companycode\":\"" + brandid + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] splitOne = result.split(",");
        String insuranceEmployeeId=splitOne[0];
        String insuranceEmployeeUserid="yangyuchao";//splitOne[1];
        String contentInsurance="已将"+customername+"（订单编号"+orderid+"）的二手车评估约至"+time;
        pushCardToEmployee(insuranceEmployeeUserid,tourl,contentInsurance,"二手车评估");
    }
    /**
     * 给二手车评估师推送消息
     * @param brandid 门店id
     * @param customername 客户姓名
     * @param orderid  订单id
     * @param tourl  跳转地址
     * @param content  跳转地址
     */
    @Override
    public void pushMessageToSecondCarConsultant1(String brandid, String customername, Integer orderid, String tourl) {
        String result = null;
        try {
            result = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getSecendCarEmployUserIdAndEmployeeId", "{\"companycode\":\"" + brandid + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] splitOne = result.split(",");
        String insuranceEmployeeId=splitOne[0];
        String insuranceEmployeeUserid="splitOne[1]";//splitOne[1];
        String contentInsurance="请为"+customername+"（订单编号"+orderid+"）办理车辆置换";
        pushCardToEmployee(insuranceEmployeeUserid,tourl,contentInsurance,"二手车评估");
    }
    
    
    /**
     * 给验车专员推送消息
     * @param brandid 门店id
     * @param customername 客户姓名
     * @param orderid  订单id
     * @param tourl  跳转地址
     */
    @Override
    public void pushMessageToCheckCar(String brandid, String customername, Integer orderid, String tourl,String content) {
        String result = null;
        try {
            result = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getCheckCarEmployUserIdAndEmployeeId", "{\"companycode\":\"" + brandid + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] splitOne = result.split(",");
        String checkCarEmployeeId=splitOne[0];
        String checkCarEmployeeUserid="yuzhicheng";//splitOne[1];
        String contentInsurance="请为"+customername+"（订单编号"+orderid+"）"+content;
        pushCardToEmployee(checkCarEmployeeUserid,tourl,contentInsurance,content);
    }
    
    
    
    
    /**
     * 给二手车财务推送消息
     * @param brandid 门店id
     * @param customername 客户姓名
     * @param orderid  订单id
     * @param tourl  跳转地址
     */

    @Override
    public String pushMessageToSecondCarFinance(String brandid, String customername, Integer orderid, String tourl) {
        String result = null;
        String accounttype = null;
        try {
            result = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getSecondHandCarPayNoticeUser", "{\"companycode\":\"" + brandid + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] splitOne = result.split(",");
        String insuranceEmployeeId=splitOne[0];
        accounttype = userService.getTitleIdByEmployeeId(Integer.valueOf(insuranceEmployeeId));
        String insuranceEmployeeUserid=splitOne[1];//splitOne[1];
        String contentInsurance="已完成"+customername+"（订单编号"+orderid+"）的二手车收购，请确认";
        pushCardToEmployee(insuranceEmployeeUserid,tourl+"?employeeid="+insuranceEmployeeId+"&accounttype="+accounttype,contentInsurance,"确认收款");
        return insuranceEmployeeId;
    }

    /**
     * 给精品顾问推送消息
     * @param brandid 门店id
     * @param customername 客户姓名
     * @param orderid  订单id
     * @param tourl  跳转地址
     */
    @Override
    public void pushMessageToboutique1(String brandid, String customername, Integer orderid, String tourl) {
        String result = null;
        try {
            result = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getboutiqueEmployUserIdAndEmployeeId", "{\"companycode\":\"" + brandid + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] splitOne = result.split(",");
        String insuranceEmployeeId=splitOne[0];
        String insuranceEmployeeUserid=splitOne[1];//splitOne[1];
        String contentInsurance="已为"+customername+"（订单编号"+orderid+"）预约加装精品时间";
        pushCardToEmployee(insuranceEmployeeUserid,tourl,contentInsurance,"精品加装");
    }
}
