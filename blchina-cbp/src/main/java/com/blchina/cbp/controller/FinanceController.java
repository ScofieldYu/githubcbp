/*   
 * @(#)FinanceController.java       2018年2月25日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.controller;

import java.util.List;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.model.BDLCard;
import com.blchina.cbp.model.CBPFinanceOrder;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.model.CBPSecondHandCarOrder;
import com.blchina.cbp.service.interfaces.FinanceService;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.cbp.service.interfaces.WxService;
import com.blchina.common.util.http.HttpUtil;

/** 
 * 此类功能描述    金融分期
 *
 * @author Zhangtong 
 * @since JDK 1.7 
 */
@RestController
@RequestMapping("/finance")
public class FinanceController {
	@Autowired
	protected Properties systemConfig;
	@Autowired
	private FinanceService financeService;
	@Autowired
	private WxService wxService;
	@Autowired
    private OrderService orderService;
	/**
	 * 
	 * {方法的功能/动作描述}    获取所有银行
	 *    
	 * @param      {引入参数名}   {引入参数说明}   
	 * @return      {返回参数名}   {返回参数说明}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong
	 * @param jsonObject
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/selectBank")
	public JSONObject selectBank(){
		WeixinResult res = new WeixinResult();
		String result = null;
		 try {
			 result = HttpUtil.postbody(systemConfig.getProperty("url.finance") + "/finance/selectBank",null);
			 JSONObject jsonObject=JSONObject.fromObject(result);
             return jsonObject;
         } catch (Exception e) {
             res.setCode(CBPConstant.CODE_FAILURE);
             res.setMessage(CBPConstant.MESSAGE_FAILURE);
         }
		return new JSONObject();
	}
	/**
	 * 
	 * {方法的功能/动作描述}    获取查询所有银行对应的期数
	 *    
	 * @param      {引入参数名}   {引入参数说明}   
	 * @return      {返回参数名}   {返回参数说明}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/getAllPeriodList")
	public JSONObject getPeriodListByperiodId(){
		WeixinResult res = new WeixinResult();
		String result = null;
		 try {
			 result = HttpUtil.postbody(systemConfig.getProperty("url.finance") + "/finance/getAllPeriodList",null);
			 JSONObject jsonObject=JSONObject.fromObject(result);
             return jsonObject;
         } catch (Exception e) {
             res.setCode(CBPConstant.CODE_FAILURE);
             res.setMessage(CBPConstant.MESSAGE_FAILURE);
         }
		return new JSONObject();
	}
	

	/**
	 * 
	 * {方法的功能/动作描述}    添加选中的分期信息
	 *    					  给销售经理发送企业消息    给金融经理发送企业消息
	 * @param      {引入参数名}   {引入参数说明}   
	 * @return      {返回参数名}   {返回参数说明}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong
	 * @param cbpFinanceOrder
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/insertfinanceOrder")
	public WeixinResult insertfinanceOrder(@RequestBody CBPFinanceOrder cbpFinanceOrder){
		WeixinResult res = new WeixinResult();
		if (cbpFinanceOrder.getPeriodid()==null||cbpFinanceOrder.getBankid()==null||cbpFinanceOrder.getOrderid()==null) {
			 res.setCode(CBPConstant.CODE_NULL_PARAM);
			 res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
			 return res;
		}
		CBPOrder order = orderService.selectByPrimaryKey(cbpFinanceOrder.getOrderid());
		String brandid = order.getBrandid();
		String customername = order.getCustomername();
		Integer customerid = order.getCustomerid();
		Integer orderid = order.getOrderid();
		Integer employeeid = order.getEmployeeid();
		String tourl = CBPConstant.BDLSELL_CARD;
		//获取userid
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
			try {
				JSONObject jsonObject = new JSONObject();
				//卡片类型为确认金融分期收款
				jsonObject.put("cardtype", CBPConstant.CardTypeEnum.TRANSACTFINANCIALSTAGE.getType());
				jsonObject.put("discription", CBPConstant.CardTypeDescriptionEnum.TRANSACTFINANCIALSTAGE.getType());
				jsonObject.put("employeeid", employeeid);
	            jsonObject.put("customerid", customerid);
	            jsonObject.put("customername", customername);
	            jsonObject.put("orderid", orderid);
			    String result1 = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/saveCard", jsonObject.toString());
			    JSONObject getJson = JSONObject.fromObject(result1);
			    WeixinResult getRes = (WeixinResult) JSONObject.toBean(getJson, WeixinResult.class);
			} catch (Exception e) {
			    res.setCode(CBPConstant.CODE_FAILURE);
			    res.setMessage(CBPConstant.MESSAGE_FAILURE);
			    return res;
			}
			
			String content = "请为"+customername+"(订单编号:"+orderid+")办理金融分期";
			String title = "金融分期";
			String url=CBPConstant.BDLSELL_CARD;
			//给销售顾问发送企业微信
			wxService.pushCardToEmployee(userid, url, content, title);
			
			//查询orderid对应的分期信息是否存在
			List<CBPFinanceOrder> financeOrderList = financeService.getFinanceByOrderId(orderid);
			//修改状态
			cbpFinanceOrder.setStatus(String.valueOf(CBPConstant.ModuleStatusEnum.PROCESSING.getType()));
			if (financeOrderList.size()>0) {
				//修改精品信息
				int i = financeService.updateFinanceOrder(cbpFinanceOrder);
				res.setCode(CBPConstant.CODE_SUCCESS);
		        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
				return res;
			}
			//添加选中精品消息
			int i = financeService.insertfinanceOrder(cbpFinanceOrder);	
			res.setCode(CBPConstant.CODE_SUCCESS);
	        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
			return res;
	}
	/**
	 * 
	 * {方法的功能/动作描述}   预约家访时间 
	 *    					   将预约时间加入任务中
	 * @param      {引入参数名}   {引入参数说明}   
	 * @return      {返回参数名}   {返回参数说明}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/appointmentVisit")
	public WeixinResult appointmentVisit(@RequestBody CBPFinanceOrder cbpFinanceOrder){
		WeixinResult res = new WeixinResult();
		if (cbpFinanceOrder.getOrderid()==null||cbpFinanceOrder.getReservetime()==null) {
			 res.setCode(CBPConstant.CODE_NULL_PARAM);
			 res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
			 return res;
		}
		int i = financeService.updateReserveTimeByOrderid(cbpFinanceOrder);
		res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		return res;
	}
	
	/**
     * 
     * {方法的功能/动作描述}    完成预约家访后
     *    
     * @param      {引入参数名}   {引入参数说明}   
     * @return      {返回参数名}   {返回参数说明}    
     * @exception   {说明在某情况下,将发生什么异常}   
     *
     * @author zhangtong
     * @param jsonObject
     * @return 
     * @since JDK 1.7
     */
    @RequestMapping("/appointmentVisitComplete")
    public WeixinResult appointmentVisitComplete(@RequestBody JSONObject jsonObject){
  	  WeixinResult res=new WeixinResult();
        try {
            if (jsonObject.get("createtime")==null||jsonObject.get("orderid")==null) {
            	res.setCode(CBPConstant.CODE_NULL_PARAM);
                res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
                return res;
            }
            String createtime = jsonObject.get("createtime").toString();
            Integer orderid = (Integer) jsonObject.get("orderid");
            CBPOrder order = orderService.selectByPrimaryKey(orderid);
    		String brandid = order.getBrandid();
    		String customername = order.getCustomername();
    		Integer customerid = order.getCustomerid();
    		Integer employeeid = order.getEmployeeid();
            String tourl = CBPConstant.BDLSELL_CARD;
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
			try {
				//获取门店财务id
				String resultOne = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/userRole/getPayNoticeUser", "{\"brandid\":" + brandid + "}");
		        String[] splitOne = resultOne.split(",");
		        String insuranceEmployeeId=splitOne[0];
		        String insuranceEmployeeUserid="splitOne[1]";//splitOne[1];
				//卡片类型为确认金融分期收款
				jsonObject.put("cardtype", CBPConstant.CardTypeEnum.AFFIRMCOLLECTFINANCIALSTAGE.getType());
				jsonObject.put("discription", CBPConstant.CardTypeDescriptionEnum.AFFIRMCOLLECTFINANCIALSTAGE.getType());
				jsonObject.put("employeeid", insuranceEmployeeId);
	            jsonObject.put("customerid", customerid);
	            jsonObject.put("customername", customername);
	            jsonObject.put("orderid", orderid);
			    String result1 = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/saveCard", jsonObject.toString());
			    JSONObject getJson = JSONObject.fromObject(result1);
			    WeixinResult getRes = (WeixinResult) JSONObject.toBean(getJson, WeixinResult.class);
			    String code = getRes.getCode();
			    if (code.equals(CBPConstant.CODE_SUCCESS)) {
			    	 res.setCode(CBPConstant.CODE_SUCCESS);
		             res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		             return res;
			    }
			} catch (Exception e) {
			    res.setCode(CBPConstant.CODE_FAILURE);
			    res.setMessage(CBPConstant.MESSAGE_FAILURE);
			    return res;
			}
			String content = "已为"+customername+"(订单编号:"+orderid+")完成预约家访";
			String title = "预约家访";
			String url=CBPConstant.ORDER_DETAILS;
			String contentCustomer = "已为您将金融分期家访约至"+createtime+"，请您准备相关文件";//客户内容
			String title1 = "预约家访";
			String tourl1=CBPConstant.ORDER_DETAILS;
			//查询openid
			JSONObject jsonObjecta=new JSONObject();
			jsonObjecta.put("customerid",order.getCustomerid());
			jsonObjecta.put("brandid",order.getBrandid());
			String   getcus = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerNameAndOpenid",jsonObjecta.toString());
			String[] split = getcus.split(",");
			String customername1=split[0];
			String openid=split[1];
			//给销售顾问发送企业微信
			wxService.pushCardToEmployee(userid, url, content, title);//String userid, String url, String content,String title
          //给客户发送企业微信消息
			wxService.pushCardToCustomer(brandid, openid, customername1, contentCustomer, tourl1, title1);//openid
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage(CBPConstant.MESSAGE_FAILURE);
        }
        return res;
    }
    
	/**
	 * 
	 * {方法的功能/动作描述}    根据订单id查询分期信息
	 *    
	 * @param      {引入参数名}   {引入参数说明}   
	 * @return      {返回参数名}   {返回参数说明}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong
	 * @param cbpFinanceOrder
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/selectFinanceOrderByOrderId")
	public WeixinResult selectFinanceOrderByOrderId(@RequestBody CBPFinanceOrder cbpFinanceOrder){
		WeixinResult res = new WeixinResult();
		if (cbpFinanceOrder.getOrderid()==null) {
			res.setCode(CBPConstant.CODE_NULL_PARAM);
			res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
			return res;
		}
		CBPFinanceOrder financeOrder = financeService.selectFinanceOrderByOrderId(cbpFinanceOrder.getOrderid());
		if (financeOrder==null) {
			res.setData(new JSONObject());
			return res;
		}
		JSONObject fromObject = JSONObject.fromObject(financeOrder);
		res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        res.setData(fromObject);
		return res;
	}
}
