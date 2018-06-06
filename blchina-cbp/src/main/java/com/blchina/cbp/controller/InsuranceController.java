/*
 * @(#)InsuranceController.java       2018年2月6日
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.controller;


import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.InsuranceDTO;
import com.blchina.cbp.dto.InsuranceOrderInfoDTO;
import com.blchina.cbp.model.BDLCard;
import com.blchina.cbp.model.CBPInsuranceExt;
import com.blchina.cbp.model.CBPInsuranceOrder;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.service.interfaces.InsuranceOrderExtService;
import com.blchina.cbp.service.interfaces.InsuranceOrderService;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.cbp.service.interfaces.WxService;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Properties;

/**
 * 保险操作
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/insurance")
public class InsuranceController {
    @Autowired
    protected Properties systemConfig;
    @Autowired
    private InsuranceOrderService insuranceOrderService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private WxService wxService;
    @Autowired
    private InsuranceOrderExtService extService;

    /**
     * 获取保险相关信息
     * @return
     */
    @RequestMapping("/getInsuranceInfo")
    public String getInsuranceInfo(){
        try {
            String result = HttpUtil.postbody(systemConfig.getProperty("url.insurance") + "/insurance/getInsuranceInfo",null);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
      @RequestMapping("/getInsuranceCompany")
      public WeixinResult getInsuranceCompany(){
           WeixinResult res=new WeixinResult();
          try {
              String result = HttpUtil.postbody(systemConfig.getProperty("url.insurance") + "/insurance/getInsuranceCompany",null);
              JSONArray jsonArray=JSONArray.fromObject(result);
              res.setCode(CBPConstant.CODE_SUCCESS);
              res.setMessage(CBPConstant.MESSAGE_SUCCESS);
              res.setData(jsonArray);
              return res;
          } catch (Exception e) {
              e.printStackTrace();
              res.setCode(CBPConstant.CODE_FAILURE);
              res.setMessage(CBPConstant.MESSAGE_FAILURE);
          }
          return res;
    }

    /**
     * 获取保险种类
     * @param jsonObject
     * @return
     */
      @RequestMapping("/getInsuranceList")
    public WeixinResult getInsuranceList(@RequestBody JSONObject jsonObject){
        WeixinResult res=new WeixinResult();
        try {
            Object insurancecompanyid = jsonObject.get("insurancecompanyid");
            if(StringUtil.isNullorEmpty(String.valueOf(insurancecompanyid))){
                res.setCode(CBPConstant.CODE_NULL_PARAM);
                res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
                return res;
            }
            String result = HttpUtil.postbody(systemConfig.getProperty("url.insurance") + "/insurance/getInsuranceList",jsonObject.toString());
            JSONArray jsonArray=JSONArray.fromObject(result);
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            res.setData(jsonArray);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage(CBPConstant.MESSAGE_FAILURE);
        }
        return res;
    }

    /**
     * 获取保额列表
     * @param jsonObject
     * @return
     */
    @RequestMapping("/getInsuranceLimitList")
    public WeixinResult getInsuranceLimitList(@RequestBody JSONObject jsonObject){
        WeixinResult res=new WeixinResult();
        try {
            Object insurancecompanyid = jsonObject.get("insurancecompanyid");
            if(StringUtil.isNullorEmpty(String.valueOf(insurancecompanyid))){
                res.setCode(CBPConstant.CODE_NULL_PARAM);
                res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
                return res;
            }
            String result = HttpUtil.postbody(systemConfig.getProperty("url.insurance") + "/insurance/getInsuranceLimitList",jsonObject.toString());
            JSONArray jsonArray=JSONArray.fromObject(result);
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            res.setData(jsonArray);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage(CBPConstant.MESSAGE_FAILURE);
        }
        return res;
    }

    /**
     * 获取保额列表
     * @param list
     * @return
     */
    @RequestMapping("/saveOrderInsurance")
    public WeixinResult saveOrderInsurance(@RequestBody List<CBPInsuranceOrder> list){
        WeixinResult res=new WeixinResult();
        if(list.size()==0){
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return  res;
        }
        for(int i=0;i<list.size();i++){
            CBPInsuranceOrder cbpInsuranceOrder = list.get(i);
            if(cbpInsuranceOrder.getInsurancecompanyid()==null||cbpInsuranceOrder.getInsuranceid()==null||cbpInsuranceOrder.getOrderid()==null){
                res.setCode(CBPConstant.CODE_NULL_PARAM);
                res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
                return  res;
            }
        }
        insuranceOrderService.saveOrUpdateInsuranceOrder(list);
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping("/insuranceConfirmReceipt")
    public WeixinResult insuranceConfirmReceipt(@RequestBody BDLCard bdlCard){
        WeixinResult res=new WeixinResult();
        Integer cardid = bdlCard.getCardid();
        Integer orderid = bdlCard.getOrderid();
        if(cardid==null||orderid==null){
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        CBPOrder order = orderService.getOrderById(orderid);
        if(order==null){
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
       Boolean flag= insuranceOrderService.insuranceConfirmReceipt(bdlCard,order);
        if(flag){
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        }else {
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage("操作失败，请重新操作");
        }
        return res;
    }
    /**
     * 
     * {方法的功能/动作描述}    更改承卡片状态发送微信消息(保险确认)
     *    
     * @param      {引入参数名}   {引入参数说明}   
     * @return      {返回参数名}   {返回参数说明}    
     * @exception   {说明在某情况下,将发生什么异常}   
     *
     * @author zhangtong
     * @return 
     * @since JDK 1.7
     */
    @RequestMapping("/updateCardStatusComplete")
    public WeixinResult updateCardStatusComplete(@RequestBody JSONObject jsonObject){
        WeixinResult res=new WeixinResult();
       try {
    	   if (jsonObject.get("orderid")==null||jsonObject.get("cardid")==null) {
        	   res.setCode(CBPConstant.CODE_NULL_PARAM);
               res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
               return res;
           }
    	   Integer cardid = (Integer) jsonObject.get("cardid");//卡片id
           String result = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/updateCardStatusComplete",jsonObject.toString());
           JSONObject jsonArray=JSONObject.fromObject(result);
           Integer orderid = (Integer) jsonObject.get("orderid");//订单id
    	   CBPOrder order = orderService.selectByPrimaryKey(orderid);
    	   String brandid = order.getBrandid();
   		   String customername = order.getCustomername();
   		   Integer employeeid = order.getEmployeeid();
           String tourl = CBPConstant.CUSTOMER_ORDERDETAILS;
		   String userid = "";
   			try {
   			    String result1 = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployUserId", "{\"employeeid\":" + employeeid + "}");
   			    JSONObject getJson = JSONObject.fromObject(result1);
   			    WeixinResult getRes = (WeixinResult) JSONObject.toBean(getJson, WeixinResult.class);
   			    String code = getRes.getCode();
   			    if (code.equals(CBPConstant.CODE_SUCCESS)) {
   			        userid = (String) getRes.getData();
   			    }
   			} catch (Exception e) {
   			    res.setCode(CBPConstant.CODE_FAILURE);
   			    res.setMessage(CBPConstant.MESSAGE_FAILURE);
   			    return res;
   			}
   			
   			String content = "已为您的客户"+customername+"(订单编号:"+orderid+")办理了车险";
   			String content1 = "已为您办理了车险，并开始为您办理缴纳购置税业务，请您准备相关资料并设置选号方式";
   			String title = "办理保险";
   			String url=CBPConstant.BDLSELL_CARD;
   			//给销售顾问发送企业微信
   			wxService.pushCardToEmployee(userid, url, content, title);//String userid, String url, String content,String title
   			//查询openid
  			JSONObject jsonObjecta=new JSONObject();
  			jsonObjecta.put("customerid",order.getCustomerid());
  			jsonObjecta.put("brandid",order.getBrandid());
			String   getcus = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerNameAndOpenid",jsonObjecta.toString());
			String[] split = getcus.split(",");
			String customername1=split[0];
			String openid=split[1];
   			//给客户发送企业微信
   			wxService.pushCardToCustomer(brandid, openid, customername1, content1, tourl, title);
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            res.setData(jsonArray);
            return res;
       } catch (Exception e) {
           e.printStackTrace();
           res.setCode(CBPConstant.CODE_FAILURE);
           res.setMessage(CBPConstant.MESSAGE_FAILURE);
       }
       return res;
 }

    /**
     * 查询订单保险数据
     * @param jsonObject
     * @return
     */
    @RequestMapping("getInsuranceInfoByOrderId")
    public WeixinResult getInsuranceInfoByOrderId(@RequestBody JSONObject jsonObject){
        WeixinResult res=new WeixinResult();
        Integer orderid = Integer.valueOf(String.valueOf(jsonObject.get("orderid")));
        CBPInsuranceOrder insuranceOrder=insuranceOrderService.getInsuranceOrder(orderid);
        if(insuranceOrder==null){
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            return res;
        }
        InsuranceOrderInfoDTO infoDTO=new InsuranceOrderInfoDTO();
        List<CBPInsuranceExt> extList=extService.getOrderExtInsurance(orderid);
        infoDTO.setExtList(extList);
        infoDTO.setExpense(insuranceOrder.getExpense());
        infoDTO.setInsurancebeneficiary(insuranceOrder.getInsurancebeneficiary());
        infoDTO.setInsurancecompanyid(insuranceOrder.getInsurancecompanyid());
        infoDTO.setInsurancecompanyname(insuranceOrder.getInsurancecompanyname());
        infoDTO.setInsuranceid(insuranceOrder.getInsuranceid());
        infoDTO.setInsuranceinvoice(insuranceOrder.getInsuranceinvoice());
        infoDTO.setInsurancelimit(insuranceOrder.getInsurancelimit());
        infoDTO.setInsurancename(insuranceOrder.getInsurancename());
        infoDTO.setInsurancetype(insuranceOrder.getInsurancetype());
        infoDTO.setOrderid(insuranceOrder.getOrderid());
        infoDTO.setInsuranceorderid(insuranceOrder.getInsuranceorderid());
        infoDTO.setStatus(insuranceOrder.getStatus());
        infoDTO.setIsnodeductible(insuranceOrder.getIsnodeductible());
        infoDTO.setGiveinsurance(insuranceOrder.getGiveinsurance());
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setData(infoDTO);
        return res;
    }

    /**
     * 保存更新保险信息
     * @param infoDTO
     * @return
     */
    @RequestMapping("saveOrUpdateInsuranceInfo")
    public WeixinResult saveOrUpdateInsuranceInfo(@RequestBody InsuranceOrderInfoDTO infoDTO){
        WeixinResult res=new WeixinResult();
        Integer orderid = infoDTO.getOrderid();
        Integer insurancecompanyid = infoDTO.getInsurancecompanyid();
        if(orderid==null||insurancecompanyid==null){
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        infoDTO.setStatus("2");
        insuranceOrderService.saveOrUpdateInsuranceInfo(infoDTO);
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        return res;
    }
}
