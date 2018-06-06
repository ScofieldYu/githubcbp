/*   
 * @(#)BoutiqueOrderServiceImpl.java       2018年2月11日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.impl;

import java.util.List;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blchina.cbp.dao.CBPBoutiqueItemMapper;
import com.blchina.cbp.dao.CBPBoutiqueOrderMapper;
import com.blchina.cbp.dao.CBPOrderMapper;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.CBPBoutiqueItemDTO;
import com.blchina.cbp.model.CBPBoutiqueOrder;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.service.interfaces.BoutiqueOrderService;
import com.blchina.common.util.http.HttpUtil;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@Service("/BoutiqueOrderService")
public class BoutiqueOrderServiceImpl implements BoutiqueOrderService {

   @Autowired
   private CBPBoutiqueOrderMapper cbpBoutiqueOrderMapper;
   @Autowired
   private CBPBoutiqueItemMapper cbpBoutiqueItemMapper;
   @Autowired
   protected Properties systemConfig;
   @Autowired
   private CBPOrderMapper cbpOrderMapper;
   @Override
   public List<CBPBoutiqueOrder> selectBoutiqueByOrderId(Integer orderid) {
	  return cbpBoutiqueOrderMapper.selectBoutiqueByOrderId(orderid);
   }
	@Override
	public int updateCreateTime(CBPBoutiqueOrder cbpBoutiqueOrder) {
		// TODO Auto-generated method stub
		return cbpBoutiqueOrderMapper.updateCreateTime(cbpBoutiqueOrder);
	}
	@Override
	public int deleteBoutiqueOrderByBoutiqueId(CBPBoutiqueOrder boutiqueOrder) {
		// TODO Auto-generated method stub
		return cbpBoutiqueOrderMapper.deleteBoutiqueOrderByBoutiqueId(boutiqueOrder.getBoutiqueorderid());
	}
	@Override
	public int insertBoutiqueOrder(CBPBoutiqueItemDTO boutiqueItem) {
		// TODO Auto-generated method stub
		//获取门店号
	 	 CBPOrder order = cbpOrderMapper.selectByPrimaryKey(boutiqueItem.getOrderid());
		 String brandid = order.getBrandid();//门店id
		 //获取精品顾问id
		 String resultOne;
		try {
			resultOne = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/userRole/getboutiqueEmployUserIdAndEmployeeId", "{\"companycode\":\"" + brandid + "\"}");
			String[] splitOne = resultOne.split(",");
	        String insuranceEmployeeId=splitOne[0];
	        String insuranceEmployeeUserid="splitOne[1]";//splitOne[1];
	        CBPBoutiqueOrder cbpBoutiqueOrder2 = new CBPBoutiqueOrder();
	        cbpBoutiqueOrder2.setStatus(String.valueOf(CBPConstant.ModuleStatusEnum.PROCESSING.getType()));
	        cbpBoutiqueOrder2.setOrderid(boutiqueItem.getOrderid());
	        cbpBoutiqueOrder2.setBoutiqueconsultantid(Integer.parseInt(insuranceEmployeeId));
	        resultOne = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployeeNameById", "{\"employeeid\":\"" + Integer.parseInt(insuranceEmployeeId) + "\"}");
	        cbpBoutiqueOrder2.setBoutiqueconsultantname(resultOne);
	        resultOne = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployeePhoneNumberById", "{\"employeeid\":\"" + Integer.parseInt(insuranceEmployeeId) + "\"}");
	        cbpBoutiqueOrder2.setBoutiqueconsultantphone(resultOne);
	        cbpBoutiqueOrderMapper.insertSelective(cbpBoutiqueOrder2);
	        List<CBPBoutiqueOrder> selectBoutiqueByOrderId = cbpBoutiqueOrderMapper.selectBoutiqueByOrderId(boutiqueItem.getOrderid());
	        if (selectBoutiqueByOrderId.size()>0) {
				CBPBoutiqueOrder cbpBoutiqueOrder = selectBoutiqueByOrderId.get(0);
				boutiqueItem.setBoutiqueorderid(cbpBoutiqueOrder.getBoutiqueorderid());
	         }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         return cbpBoutiqueItemMapper.saveSelective(boutiqueItem);
	}

}
