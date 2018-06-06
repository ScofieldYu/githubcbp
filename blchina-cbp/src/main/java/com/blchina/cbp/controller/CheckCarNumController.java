/*   
 * @(#)CheckCarNumController.java       2018年2月22日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.model.CBPCheckCarNumOrder;
import com.blchina.cbp.model.CBPCheckCarNumSource;
import com.blchina.cbp.model.CBPFinanceOrder;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.service.interfaces.CheckCarNumOrderService;
import com.blchina.cbp.service.interfaces.CheckCarNumService;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.cbp.service.interfaces.WxService;
import com.blchina.common.util.http.HttpUtil;
/** 
 * 此类功能描述    验车上牌
 *
 * @author Zhangtong 
 * @since JDK 1.7 
 */
@RequestMapping("/checkCarNum")
@RestController
public class CheckCarNumController {
	@Autowired
	private CheckCarNumService checkCarNumService;
	@Autowired
	private WxService wxService;
	@Autowired
	protected Properties systemConfig;
	@Autowired
    private OrderService orderService;
	@Autowired
	private CheckCarNumOrderService carNumOrderService;
	/**
	 * 
	 * {方法的功能/动作描述}    获取所有的选号类型
	 *    
	 * @param      {引入参数名}   {引入参数说明}   
	 * @return      {返回参数名}   {返回参数说明}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/getChooseNumType")
	public WeixinResult getChooseNumType(){
		WeixinResult result = new WeixinResult();
		List<CBPCheckCarNumOrder> list = new ArrayList<CBPCheckCarNumOrder>();
		CBPCheckCarNumOrder c1 = new CBPCheckCarNumOrder();
        c1.setChooseType(CBPConstant.ChoiceNumberEnum.KEPPNUMBER.getType());//1
        c1.setChooseName(CBPConstant.KEPP_NUMBER);//保持原号
        CBPCheckCarNumOrder c2 = new CBPCheckCarNumOrder();
        c2.setChooseType(CBPConstant.ChoiceNumberEnum.FIFTHONE.getType());//2
        c2.setChooseName(CBPConstant.FIFTH_ONE);//50选一
        CBPCheckCarNumOrder c3 = new CBPCheckCarNumOrder();
        c3.setChooseType(CBPConstant.ChoiceNumberEnum.SELFNUMBER.getType());//3
        c3.setChooseName(CBPConstant.SELF_NUMBER);//自编号码
        list.add(c1);
        list.add(c2);
        list.add(c3);
        result.setCode(CBPConstant.CODE_SUCCESS);
	    result.setMessage(CBPConstant.MESSAGE_SUCCESS);
        result.setData(list);
		return result;
	}
	
	
	/**
	 * 
	 * {方法的功能/动作描述}    添加选中代办信息
	 *    
	 * @param      {引入参数名}   {引入参数说明}   
	 * @return      {返回参数名}   {返回参数说明}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong
	 * @param checkCarNumOrder  验车上牌ID，订单ID，
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/insertCheckCarNumOrder")
	public WeixinResult insertCheckCarNumOrder(@RequestBody CBPCheckCarNumOrder checkCarNumOrder){
		WeixinResult res = new WeixinResult();
		List<CBPCheckCarNumOrder> checkCarNumOrderList = checkCarNumService.selectCheckCarNumByOrderId(checkCarNumOrder.getOrderid());
		//查询orderid对应的分期信息是否存在
		if (checkCarNumOrderList.size()>0) {
			int i = checkCarNumService.updateCheckNumOrder(checkCarNumOrder);
			res.setCode(CBPConstant.CODE_SUCCESS);
	        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
			return res;
		}
		int i = checkCarNumService.insertCheckCarNumOrder(checkCarNumOrder);
		res.setCode(CBPConstant.CODE_SUCCESS);
		res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		return res;
	}
	/**
	 * 
	 * {方法的功能/动作描述}    添加临牌次数
	 *    
	 * @param      {引入参数名}   {引入参数说明}   
	 * @return      {返回参数名}   {返回参数说明}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong
	 * @param checkCarNumOrder 临牌次数 ， 验车上牌ID，订单ID,代办购置税,代办临牌,代办验车上牌
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/updateByOrderId")
	public WeixinResult updateCheckCarNumOrder(@RequestBody CBPCheckCarNumOrder checkCarNumOrder){
		WeixinResult res = new WeixinResult();
		if (null!=checkCarNumOrder) {
			int i = checkCarNumService.updateByOrderId(checkCarNumOrder);
			res.setCode(CBPConstant.CODE_SUCCESS);
			res.setMessage(CBPConstant.MESSAGE_SUCCESS);
			return res;
		}else {
			res.setCode(CBPConstant.CODE_FAILURE);
			res.setMessage(CBPConstant.MESSAGE_FAILURE);
			return res;
		}
	}
	/**
	 * 
	 * {方法的功能/动作描述}    获取验车上牌的信息
	 *    
	 * @param      {引入参数名}   {引入参数说明}   
	 * @return      {返回参数名}   {返回参数说明}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/getCheckCarInfoByOrderid")
	public WeixinResult getCheckCarInfoByOrderid(@RequestBody CBPCheckCarNumOrder checkCarNumOrder){
		
		List<CBPCheckCarNumOrder> checkCarNumOrderList = checkCarNumService.selectCheckCarNumByOrderId(checkCarNumOrder.getOrderid());
		WeixinResult result = new WeixinResult();
		if (checkCarNumOrder.getOrderid()==null) {
			result.setCode(CBPConstant.CODE_NULL_PARAM);
		    result.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
			return result;
		}
		result.setCode(CBPConstant.CODE_SUCCESS);
	    result.setMessage(CBPConstant.MESSAGE_SUCCESS);
	    result.setData(checkCarNumOrderList);
		return result;
	}
	/**
	 * 
	 * {方法的功能/动作描述}    获取所有资料类型和名称
	 *    
	 * @param      {引入参数名}   {引入参数说明}   
	 * @return      {返回参数名}   {返回参数说明}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/getInfoBySelected")
	public WeixinResult collectionInfo(@RequestBody CBPCheckCarNumSource checkCarNumSource2){
		WeixinResult result = new WeixinResult();
		List<CBPCheckCarNumSource> list = new ArrayList<CBPCheckCarNumSource>();
		CBPCheckCarNumSource c1 = new CBPCheckCarNumSource();
        c1.setSourcetype(CBPConstant.CheckCarResourceEnum.FOURINVOICE.getType());//1
        c1.setSourcename(CBPConstant.FOUR_INVOICE);//新车机动车发票(四联)原件
        CBPCheckCarNumSource c2 = new CBPCheckCarNumSource();
        c2.setSourcetype(CBPConstant.CheckCarResourceEnum.CARCERTIFICATION.getType());//2
        c2.setSourcename(CBPConstant.CAR_CERTIFICATION);//机动车合格证原件
        CBPCheckCarNumSource c3 = new CBPCheckCarNumSource();
        c3.setSourcetype(CBPConstant.CheckCarResourceEnum.ENVIRONINFO.getType());//3
        c3.setSourcename(CBPConstant.ENVIRON_INFO);//轻型汽油车环保信息原件
        CBPCheckCarNumSource c4 = new CBPCheckCarNumSource();
        c4.setSourcetype(CBPConstant.CheckCarResourceEnum.CONSISTENT.getType());//4
        c4.setSourcename(CBPConstant.CONSISTENT);//车辆一致性证书原件
        CBPCheckCarNumSource c5 = new CBPCheckCarNumSource();
        c5.setSourcetype(CBPConstant.CheckCarResourceEnum.CARENVIRON.getType());//5
        c5.setSourcename(CBPConstant.CAR_ENVIRON);//轻型汽油车环保信息随车清单原件
        CBPCheckCarNumSource c6 = new CBPCheckCarNumSource();
        c6.setSourcetype(CBPConstant.CheckCarResourceEnum.IDENTIFICATION.getType());//6
        c6.setSourcename(CBPConstant.IDENTIFICATION);//身份证及居住证原件
        CBPCheckCarNumSource c7 = new CBPCheckCarNumSource();
        c7.setSourcetype(CBPConstant.CheckCarResourceEnum.COMPULSORY.getType());//7
        c7.setSourcename(CBPConstant.COMPULSORY);//车辆交强险原件
        CBPCheckCarNumSource c8 = new CBPCheckCarNumSource();
        c8.setSourcetype(CBPConstant.CheckCarResourceEnum.BUYTAX.getType());//8
        c8.setSourcename(CBPConstant.BUY_TAX);//车辆购置税申请信息表
        CBPCheckCarNumSource c9 = new CBPCheckCarNumSource();
        c9.setSourcetype(CBPConstant.CheckCarResourceEnum.CARUPDATE.getType());//9
        c9.setSourcename(CBPConstant.CAR_UPDATE);//车辆更新指标三张
        list.add(c1);
        list.add(c2);
        list.add(c3);
        list.add(c4);
        list.add(c5);
        list.add(c6);
        list.add(c7);
        list.add(c8);
        list.add(c9);
        CBPCheckCarNumOrder  carNumOrder= carNumOrderService.getCheckCarNumByOrderId(checkCarNumSource2.getOrderid());
        checkCarNumSource2.setCheckcarnumid(carNumOrder.getCheckcarnumid());
        checkCarNumSource2.setBelongtype("1");
        List<CBPCheckCarNumSource> checkCarNumSourcesList = checkCarNumService.getInfoBySelected(checkCarNumSource2);
        for (CBPCheckCarNumSource cbpCheckCarNumSource : checkCarNumSourcesList) {
        	for (CBPCheckCarNumSource cbpCheckCarNumSource1 : list) {
        		if (cbpCheckCarNumSource.getSourcetype().equals(CBPConstant.CheckCarResourceEnum.FOURINVOICE.getType())) {
        			cbpCheckCarNumSource1.setBelongtype("1");
        			cbpCheckCarNumSource1.setIschoiced("1");
        			cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}else {
    				cbpCheckCarNumSource1.setBelongtype("1");
    				cbpCheckCarNumSource1.setIschoiced("0");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}
    			if (cbpCheckCarNumSource.getSourcetype().equals(CBPConstant.CheckCarResourceEnum.CARCERTIFICATION.getType())) {
    				cbpCheckCarNumSource1.setBelongtype("");
    				cbpCheckCarNumSource1.setIschoiced("1");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}else {
    				cbpCheckCarNumSource1.setBelongtype("");
    				cbpCheckCarNumSource1.setIschoiced("0");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}
    			if (cbpCheckCarNumSource.getSourcetype().equals(CBPConstant.CheckCarResourceEnum.ENVIRONINFO.getType())) {
    				cbpCheckCarNumSource1.setBelongtype("1");
    				cbpCheckCarNumSource1.setIschoiced("1");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}else {
    				cbpCheckCarNumSource1.setBelongtype("1");
    				cbpCheckCarNumSource1.setIschoiced("0");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}
    			if (cbpCheckCarNumSource.getSourcetype().equals(CBPConstant.CheckCarResourceEnum.CONSISTENT.getType())) {
    				cbpCheckCarNumSource1.setBelongtype("1");
    				cbpCheckCarNumSource1.setIschoiced("1");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}else {
    				cbpCheckCarNumSource1.setBelongtype("1");
    				cbpCheckCarNumSource1.setIschoiced("0");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}
    			if (cbpCheckCarNumSource.getSourcetype().equals(CBPConstant.CheckCarResourceEnum.CARENVIRON.getType())) {
    				cbpCheckCarNumSource1.setBelongtype("1");
    				cbpCheckCarNumSource1.setIschoiced("1");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}else {
    				cbpCheckCarNumSource1.setBelongtype("1");
    				cbpCheckCarNumSource1.setIschoiced("0");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}
    			if (cbpCheckCarNumSource.getSourcetype().equals(CBPConstant.CheckCarResourceEnum.IDENTIFICATION.getType())) {
    				cbpCheckCarNumSource1.setBelongtype("");
    				cbpCheckCarNumSource1.setIschoiced("1");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}else {
    				cbpCheckCarNumSource1.setBelongtype("");
    				cbpCheckCarNumSource1.setIschoiced("0");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}
    			if (cbpCheckCarNumSource.getSourcetype().equals(CBPConstant.CheckCarResourceEnum.COMPULSORY.getType())) {
    				cbpCheckCarNumSource1.setBelongtype("1");
    				cbpCheckCarNumSource1.setIschoiced("1");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}else {
    				cbpCheckCarNumSource1.setBelongtype("1");
    				cbpCheckCarNumSource1.setIschoiced("0");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}
    			if (cbpCheckCarNumSource.getSourcetype().equals(CBPConstant.CheckCarResourceEnum.BUYTAX.getType())) {
    				cbpCheckCarNumSource1.setBelongtype("1");
    				cbpCheckCarNumSource1.setIschoiced("1");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}else {
    				cbpCheckCarNumSource1.setBelongtype("1");
    				cbpCheckCarNumSource1.setIschoiced("0");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}
    			if (cbpCheckCarNumSource.getSourcetype().equals(CBPConstant.CheckCarResourceEnum.CARUPDATE.getType())) {
    				cbpCheckCarNumSource1.setBelongtype("1");
    				cbpCheckCarNumSource1.setIschoiced("1");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}else {
    				cbpCheckCarNumSource1.setBelongtype("1");
    				cbpCheckCarNumSource1.setIschoiced("0");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}
    		}
		}
        
        result.setCode(CBPConstant.CODE_SUCCESS);
	    result.setMessage(CBPConstant.MESSAGE_SUCCESS);
        result.setData(list);
		return result;
	}
	
	/**
	 * 
	 * {方法的功能/动作描述}    获取临牌资料类型和名称
	 *    
	 * @param      {引入参数名}   {引入参数说明}   
	 * @return      {返回参数名}   {返回参数说明}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/getTemporaryLicenseInfo")
	public WeixinResult getTemporaryLicenseInfo(@RequestBody CBPCheckCarNumSource checkCarNumSource2){
		WeixinResult result = new WeixinResult();
		List<CBPCheckCarNumSource> list = new ArrayList<CBPCheckCarNumSource>();
		CBPCheckCarNumSource c1 = new CBPCheckCarNumSource();
        c1.setSourcetype(CBPConstant.CheckCarResourceEnum.IDENTIFICATION.getType());//6
        c1.setSourcename(CBPConstant.IDENTIFICATION);//身份证及居住证原件
        CBPCheckCarNumSource c2 = new CBPCheckCarNumSource();
        c2.setSourcetype(CBPConstant.CheckCarResourceEnum.TAXFIRST.getType());//10
        c2.setSourcename(CBPConstant.TAX_FIRST);//新车机动车发票第一联原件
        CBPCheckCarNumSource c3 = new CBPCheckCarNumSource();
        c3.setSourcetype(CBPConstant.CheckCarResourceEnum.CARCERTIFICATION.getType());//2
        c3.setSourcename(CBPConstant.CAR_CERTIFICATION);//机动车合格证原件
        list.add(c1);
        list.add(c2);
        list.add(c3);
        CBPCheckCarNumOrder  carNumOrder= carNumOrderService.getCheckCarNumByOrderId(checkCarNumSource2.getOrderid());
        checkCarNumSource2.setCheckcarnumid(carNumOrder.getCheckcarnumid());
        checkCarNumSource2.setBelongtype("1");
        List<CBPCheckCarNumSource> checkCarNumSourcesList = checkCarNumService.getInfoBySelected(checkCarNumSource2);
        for (CBPCheckCarNumSource cbpCheckCarNumSource : checkCarNumSourcesList) {
        	for (CBPCheckCarNumSource cbpCheckCarNumSource1 : list) {
        		if (cbpCheckCarNumSource.getSourcetype().equals(CBPConstant.CheckCarResourceEnum.TAXFIRST.getType())) {
        			cbpCheckCarNumSource1.setBelongtype("2");
        			cbpCheckCarNumSource1.setIschoiced("1");
        			cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
        		}else {
        			cbpCheckCarNumSource1.setBelongtype("2");
        			cbpCheckCarNumSource1.setIschoiced("0");
        			cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
        		}
        		if (cbpCheckCarNumSource.getSourcetype().equals(CBPConstant.CheckCarResourceEnum.IDENTIFICATION.getType())) {
    				cbpCheckCarNumSource1.setBelongtype("1");
    				cbpCheckCarNumSource1.setIschoiced("1");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}else {
    				cbpCheckCarNumSource1.setBelongtype("1");
    				cbpCheckCarNumSource1.setIschoiced("0");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}
        		if (cbpCheckCarNumSource.getSourcetype().equals(CBPConstant.CheckCarResourceEnum.CARCERTIFICATION.getType())) {
    				cbpCheckCarNumSource1.setBelongtype("1");
    				cbpCheckCarNumSource1.setIschoiced("1");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}else {
    				cbpCheckCarNumSource1.setBelongtype("1");
    				cbpCheckCarNumSource1.setIschoiced("0");
    				cbpCheckCarNumSource1.setCheckcarnumid(carNumOrder.getCheckcarnumid());
    			}
        	}
        }
        
        result.setCode(CBPConstant.CODE_SUCCESS);
	    result.setMessage(CBPConstant.MESSAGE_SUCCESS);
        result.setData(list);
		return result;
	}
	
	/**
	 * 
	 * {方法的功能/动作描述}    收集资料
	 *    
	 * @param      {引入参数名}   {引入参数说明}   
	 * @return      {返回参数名}   {返回参数说明}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong
	 * @param checkCarNumSource
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/collectionInfo")
	public WeixinResult collectionInfo(@RequestBody JSONObject jsonObject){
		WeixinResult result = new WeixinResult();
		String sourcetype = jsonObject.get("sourcetype").toString();
		String ischoiced = jsonObject.get("ischoiced").toString();
		String sourcename = jsonObject.get("sourcename").toString();
		String belongtype = jsonObject.get("belongtype").toString();
		Integer orderid = Integer.parseInt(jsonObject.get("orderid").toString());
		List<CBPCheckCarNumOrder> selectCheckCarNumByOrderId = carNumOrderService.selectCheckCarNumByOrderId(orderid);
		if (selectCheckCarNumByOrderId==null) {
			result.setCode(CBPConstant.CODE_NULL_PARAM);
		    result.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
			return result;
		}
		CBPCheckCarNumOrder cbpCheckCarNumOrder = selectCheckCarNumByOrderId.get(0);
		CBPCheckCarNumSource cbpCheckCarNumSource = new CBPCheckCarNumSource();
		cbpCheckCarNumSource.setCheckcarnumid(cbpCheckCarNumOrder.getCheckcarnumid());
		cbpCheckCarNumSource.setSourcetype(sourcetype);
		cbpCheckCarNumSource.setIschoiced(ischoiced);
		cbpCheckCarNumSource.setSourcename(sourcename);
		cbpCheckCarNumSource.setBelongtype(belongtype);
		int i = checkCarNumService.collectionInfo(cbpCheckCarNumSource);
		result.setCode(CBPConstant.CODE_SUCCESS);
	    result.setMessage(CBPConstant.MESSAGE_SUCCESS);
		return result;
	}
	
	/**
	 * 
	 * {方法的功能/动作描述}    预约验车上牌
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
	@RequestMapping("/appointmentCheckCarComplete")
    public WeixinResult appointmentCheckCarComplete(@RequestBody JSONObject jsonObject){
  	  WeixinResult res=new WeixinResult();
        try {
            if (jsonObject.get("reservecheckcartime")==null||jsonObject.get("orderid")==null) {
            	res.setCode(CBPConstant.CODE_NULL_PARAM);
                res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
                return res;
            }
            String reservecheckcartime = jsonObject.get("reservecheckcartime").toString();
            Integer orderid = Integer.parseInt(jsonObject.get("orderid").toString()) ;
            
            CBPOrder order = orderService.selectByPrimaryKey(orderid);
    		String brandid = order.getBrandid();
    		String customername = order.getCustomername();
    		Integer employeeid = order.getEmployeeid();
    		Integer customerid = order.getCustomerid();
            String tourl = CBPConstant.ORDER_DETAILS;
            //获取userid
            String userid = "";
		    String result1 = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployUserId", "{\"employeeid\":" + employeeid + "}");
		    JSONObject getJson = JSONObject.fromObject(result1);
		    WeixinResult getRes = (WeixinResult) JSONObject.toBean(getJson, WeixinResult.class);
		    String code = getRes.getCode();
		    if (code.equals(CBPConstant.CODE_SUCCESS)) {
		        userid = (String) getRes.getData();
		    }
			//获取门店的验车专员id
			String resultOne = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/userRole/getCheckCarEmployUserIdAndEmployeeId", "{\"companycode\":\"" + brandid + "\"}");
	        String[] splitOne = resultOne.split(",");
	        String insuranceEmployeeId=splitOne[0];
	        String insuranceEmployeeUserid="splitOne[1]";//splitOne[1];
			//卡片类型为验车上牌
			jsonObject.put("cardtype", CBPConstant.CardTypeEnum.CHECKCARBANDFORCHECK.getType());
			jsonObject.put("discription", CBPConstant.CardTypeDescriptionEnum.CHECKCARBANDFORCHECK.getType());
			jsonObject.put("employeeid", insuranceEmployeeId);
            jsonObject.put("customerid", customerid);
            jsonObject.put("customername", customername);
		    String result2 = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/saveCard", jsonObject.toString());
		
			String content = "已为客户"+customername+"(订单编号:"+orderid+")预约验车上牌时间";
			String title = "验车上牌";
			String contentCustomer = "请于"+reservecheckcartime+"携相关文件至店办理验车上牌";//客户内容
			String url=CBPConstant.CUSTOMER_ORDERDETAILS;
			//查询openid
			JSONObject jsonObjecta=new JSONObject();
			jsonObjecta.put("customerid",order.getCustomerid());
			jsonObjecta.put("brandid",order.getBrandid());
			String   getcus = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerNameAndOpenid",jsonObjecta.toString());
			String[] split = getcus.split(",");
			String customername1=split[0];
			String openid=split[1];
			//给验车专员发送企业微信
			wxService.pushMessageToCheckCar(brandid, customername, orderid, url, content);//String brandid, String customername, Integer orderid, String tourl,String content
			//给客户发送企业微信消息
			wxService.pushCardToCustomer(brandid, openid, customername1, contentCustomer, tourl, title);//openid
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
	 * {方法的功能/动作描述}    验车上牌完成后修改卡片状态为完成
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
	@RequestMapping("/completeCheckCarUpdateCardStatic")
    public WeixinResult completeCheckCarUpdateCardStatic(@RequestBody JSONObject jsonObject){
  	  WeixinResult res=new WeixinResult();
        try {
            if (jsonObject.get("orderid")==null||jsonObject.get("cardid")==null) {
            	res.setCode(CBPConstant.CODE_NULL_PARAM);
                res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
                return res;
            }
            Integer orderid = Integer.parseInt(jsonObject.get("orderid").toString()) ;
            Integer cardid = Integer.parseInt(jsonObject.get("cardid").toString()) ;
            CBPOrder order = orderService.selectByPrimaryKey(orderid);
    		String brandid = order.getBrandid();
    		String customername = order.getCustomername();
    		Integer employeeid = order.getEmployeeid();
            String tourl = CBPConstant.ORDER_DETAILS;
            String url = CBPConstant.CUSTOMER_ORDERDETAILS;
            String userid = "";
		    String result1 = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployUserId", "{\"employeeid\":" + employeeid + "}");
		    JSONObject getJson = JSONObject.fromObject(result1);
		    WeixinResult getRes = (WeixinResult) JSONObject.toBean(getJson, WeixinResult.class);
		    String code = getRes.getCode();
		    if (code.equals(CBPConstant.CODE_SUCCESS)) {
		        userid = (String) getRes.getData();
		    }
		    //将销售顾问验车上牌任务置为完成
		    //判断查询该任务下的子任务是否为完成
		    String result2 = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/getTaskStatusByCardId", jsonObject.toString());
		    JSONObject jsonObject2 = JSONObject.fromObject(result2);
		    WeixinResult getRes1 = (WeixinResult) JSONObject.toBean(getJson, WeixinResult.class);
		    String code1 = getRes1.getCode();
		    if (code1.equals(CBPConstant.CODE_FAILURE)) {
		       res.setData("该任务下的子任务还有未完成的");
		       return res;
		    }
		    String result = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/updateCardStatusComplete", jsonObject.toString());
			
			String content = "已为您的客户"+customername+"(订单编号:"+orderid+")办理验车上牌,请预约加装精品时间(如果还未约加装精品时间)";
			String title = "验车上牌";
			String contentCustomer = "已为您办理临牌任务，开始为您办理验车上牌";//客户内容
			//查询openid
			JSONObject jsonObjecta=new JSONObject();
			jsonObjecta.put("customerid",order.getCustomerid());
			jsonObjecta.put("brandid",order.getBrandid());
			String   getcus = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerNameAndOpenid",jsonObjecta.toString());
			String[] split = getcus.split(",");
			String customername1=split[0];
			String openid=split[1];
			//给销售顾问发送企业微信
			wxService.pushCardToEmployee(userid, tourl, content, title);
			//给客户发送企业微信消息
			wxService.pushCardToCustomer(brandid, openid, customername1, contentCustomer, url, title);//openid
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
	 * {方法的功能/动作描述}    验车上牌完成后为销售顾问创建加装精品任务
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
	@RequestMapping("/completeCheckCarSaveCard")
	 public WeixinResult completeCheckCarSaveCard(@RequestBody JSONObject jsonObject){
	  	  WeixinResult res=new WeixinResult();
	        try {
	            if (jsonObject.get("orderid")==null) {
	            	res.setCode(CBPConstant.CODE_NULL_PARAM);
	                res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	                return res;
	            }
	            Integer orderid = Integer.parseInt(jsonObject.get("orderid").toString()) ;
	            
	            CBPOrder order = orderService.selectByPrimaryKey(orderid);
	    		String brandid = order.getBrandid();
	    		String customername = order.getCustomername();
	    		Integer employeeid = order.getEmployeeid();
	    		Integer customerid = order.getCustomerid();
	            String tourl = CBPConstant.ORDER_DETAILS;
	            String url = CBPConstant.CUSTOMER_ORDERDETAILS;
	            //卡片类型为加装精品
				jsonObject.put("cardtype", CBPConstant.CardTypeEnum.INSTALLBOUTIQUE.getType());
				jsonObject.put("discription", CBPConstant.CardTypeDescriptionEnum.INSTALLBOUTIQUE.getType());
				jsonObject.put("employeeid", employeeid);
	            jsonObject.put("customerid", customerid);
	            jsonObject.put("customername", customername);
	            //给销售顾问创建加装精品任务
			    String result1 = HttpUtil.postbody(systemConfig.getProperty("url.card") + "/card/saveCard", jsonObject.toString());
			    JSONObject getJson = JSONObject.fromObject(result1);
	        } catch (Exception e) {
	            e.printStackTrace();
	            res.setCode(CBPConstant.CODE_FAILURE);
	            res.setMessage(CBPConstant.MESSAGE_FAILURE);
	        }
	        res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
	        return res;
	    }
	
}
