package com.blchina.boutique.controller;



import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.boutique.datamodel.weixin.WeixinResult;
import com.blchina.boutique.model.BDLBoutique;
import com.blchina.boutique.service.interfaces.BLDBoutiqueService;

@RequestMapping("/boutique")
@RestController
/**
 * 
 *  此类功能描述    精品信息
 *     
 * @author  zhangtong  
 * @since   JDK1.8
 */
public class BDLBoutiqueController {
	
	@Autowired
	private BLDBoutiqueService boutiqueService;
	
	/**
	 * 
	     * {方法的功能/动作描述 ：根据精品Id查询精品信息}    
	     *    
	     * @param      {boutiqueid}   {精品名称}   
	     * @return       
	 */
	@RequestMapping(value="/getBoutiqueListBySerialNumber",consumes="application/json; charset=utf-8")
	public JSONObject getBoutiqueListBySerialNumber(@RequestBody BDLBoutique boutique1) {
		if (boutique1.getSerialnumber()!=null) {
			BDLBoutique boutique =boutiqueService.getBoutiqueListBySerialNumber(boutique1.getSerialnumber());
			JSONObject jsonObject= JSONObject.fromObject(boutique);
			return jsonObject;
		}else {
			return new JSONObject();
		}
	}
	/**
	 * 
	 * {方法的功能/动作描述}    根据boutiqueid将库存减一
	 *    
	 * @param      {引入参数名}   {引入参数说明}   
	 * @return      {返回参数名}   {返回参数说明}    
	 * @exception   {说明在某情况下,将发生什么异常}   
	 *
	 * @author zhangtong
	 * @param boutique
	 * @return 
	 * @since JDK 1.7
	 */
	@RequestMapping("/updateBoutiqueStockByBoutiqueName")
	public int updateBoutiqueStockByBoutiqueName(@RequestBody BDLBoutique boutique){
		return boutiqueService.updateBoutiqueStockByBoutiqueName(boutique);
	}

}
