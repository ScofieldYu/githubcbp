/*   
 * @(#)FinanceController.java       2018年2月22日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.finance.controller;


import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blchina.finance.datamodel.CBPConstant;
import com.blchina.finance.datamodel.weixin.WeixinResult;
import com.blchina.finance.model.BDLBank;
import com.blchina.finance.model.BDLPeriod;
import com.blchina.finance.service.interfaces.FinanceService;

/** 
 * 此类功能描述   金融分期 
 *
 * @author Zhangtong 
 * @since JDK 1.7 
 */
@RequestMapping("/finance")
@Controller
public class FinanceController {
	@Autowired
	private FinanceService financeService;
	/**
	 * 
	 * {方法的功能/动作描述}    查询分期的信息
	 *    
	 * @param      {引入参数名}   {引入参数说明}   
	 * @return      {返回参数名}   {返回参数说明}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/selectBank")
	@ResponseBody
	public WeixinResult selectPeriod(){
		WeixinResult result = new WeixinResult();
		List<BDLBank> banksList = financeService.selectPeriod();
		JSONArray getBankJSON=JSONArray.fromObject(banksList);
		if (banksList.size()>0) {
			//添加选择分期信息
			result.setCode(CBPConstant.CODE_SUCCESS);
			result.setMessage(CBPConstant.MESSAGE_SUCCESS);
			result.setData(getBankJSON);
			return result;
		}else {
			result.setCode(CBPConstant.CODE_NULL_PARAM);
			result.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
			return result;
		}
	}
	/**
	 * 
	 * {方法的功能/动作描述}    查询银行对应的期数
	 *    
	 * @param      {引入参数名}   {引入参数说明}   
	 * @return      {返回参数名}   {返回参数说明}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong
	 * @param bdlPeriod
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/getAllPeriodList")
	@ResponseBody
	public WeixinResult getAllPeriodList(){
		WeixinResult res = new WeixinResult();
		List<BDLBank> bDLPeriodList = financeService.getAllPeriodList();
		JSONArray getBankJSON=JSONArray.fromObject(bDLPeriodList);
		res.setCode(CBPConstant.CODE_SUCCESS);
		res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		res.setData(getBankJSON);
		return res;
	}
	/**
	 * 
	 * {方法的功能/动作描述}    添加选中的分期
	 *    
	 * @param      {引入参数名}   {引入参数说明}   
	 * @return      {返回参数名}   {返回参数说明}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong
	 * @param bdlPeriod 银行ID,银行名称,分期值,年息
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/insertPeriod")
	@ResponseBody
	public WeixinResult insertFinance(@RequestBody BDLPeriod bdlPeriod){
		WeixinResult res =new WeixinResult();
			//添加选择分期信息
			int i = financeService.insertPeriod(bdlPeriod);
			res.setCode(CBPConstant.CODE_SUCCESS);
			res.setMessage(CBPConstant.MESSAGE_SUCCESS);
			return res;
	}
}
