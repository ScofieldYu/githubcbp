/*   
 * @(#)CheckCarNumServiceImpl.java       2018年2月22日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.cbp.dao.CBPCheckCarNumOrderMapper;
import com.blchina.cbp.dao.CBPCheckCarNumSourceMapper;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.model.CBPCheckCarNumOrder;
import com.blchina.cbp.model.CBPCheckCarNumSource;
import com.blchina.cbp.service.interfaces.CheckCarNumService;

/** 
 * 此类功能描述    验车上牌
 *
 * @author Zhangtong 
 * @since JDK 1.7 
 */
@Service("/CheckCarNumService")
public class CheckCarNumServiceImpl implements CheckCarNumService{
	@Autowired
	private CBPCheckCarNumOrderMapper checkCarNumMapper;
	@Autowired
	private CBPCheckCarNumSourceMapper checkCarNumSourceMapper;
	@Override
	public int insertCheckCarNumOrder(CBPCheckCarNumOrder checkCarNumOrder) {
		// TODO Auto-generated method stub
		checkCarNumOrder.setStatus(String.valueOf(CBPConstant.ModuleStatusEnum.PROCESSING.getType()));
		CBPCheckCarNumOrder checkCarNumOrder1 = checkCarNumMapper.getCheckCarNumByOrderId(checkCarNumOrder.getOrderid());
		if (checkCarNumOrder.getBuytax().equals("1")) {
			checkCarNumOrder.setBuytaxvalue("200");//代办购置税费用
		}else {
			checkCarNumOrder.setBuytaxvalue("0");//代办购置税费用
		}
		if (checkCarNumOrder.getTempplate().equals("1")) {
			checkCarNumOrder.setCheckcarvalue("200");//代办验车上牌费用
		}else {
			checkCarNumOrder.setCheckcarvalue("0");//代办购置税费用
		}
		if (checkCarNumOrder.getCheckcar().equals("1")) {
			checkCarNumOrder.setTempplatevalue("200");;//代办临牌费用
		}else {
			checkCarNumOrder.setTempplatevalue("0");//代办购置税费用
		}
		checkCarNumOrder.setSumValues(Integer.parseInt(checkCarNumOrder.getBuytaxvalue())+Integer.parseInt(checkCarNumOrder.getTempplatevalue())
				+Integer.parseInt(checkCarNumOrder.getCheckcarvalue()));//总价
		return checkCarNumMapper.insertSelective(checkCarNumOrder);
	}
	@Override
	public int  updateByOrderId(CBPCheckCarNumOrder checkCarNumOrder) {
		// TODO Auto-generated method stub
		return checkCarNumMapper.updateByOrderId(checkCarNumOrder);
	}
	@Override
	public List<CBPCheckCarNumOrder> selectCheckCarNumByOrderId(Integer orderid) {
		// TODO Auto-generated method stub
		List<CBPCheckCarNumOrder> checkCarNumOrder = checkCarNumMapper.selectCheckCarNumByOrderId(orderid);
		String buytaxvalue="0";
		String checkcarvalue = "0";
		String tempplatevalue = "0";
		for (CBPCheckCarNumOrder cbpCheckCarNumOrder : checkCarNumOrder) {
			if (cbpCheckCarNumOrder.getBuytax().equals("1")) {
				cbpCheckCarNumOrder.setBuytaxvalue("200");//代办购置税费用
			}else {
				cbpCheckCarNumOrder.setBuytaxvalue("0");//代办购置税费用
			}
			if (cbpCheckCarNumOrder.getTempplate().equals("1")) {
				cbpCheckCarNumOrder.setCheckcarvalue("200");//代办验车上牌费用
			}else {
				cbpCheckCarNumOrder.setCheckcarvalue("0");//代办购置税费用
			}
			if (cbpCheckCarNumOrder.getCheckcar().equals("1")) {
				cbpCheckCarNumOrder.setTempplatevalue("200");;//代办临牌费用
			}else {
				cbpCheckCarNumOrder.setTempplatevalue("0");//代办购置税费用
			}
			cbpCheckCarNumOrder.setSumValues(Integer.parseInt(buytaxvalue)+Integer.parseInt(checkcarvalue)
					+Integer.parseInt(tempplatevalue));//总价
		}
		return checkCarNumOrder;
	}
	@Override
	public int collectionInfo(CBPCheckCarNumSource checkCarNumSource) {
		// TODO Auto-generated method stub
		//查询所有收集到的资料
		List<CBPCheckCarNumSource> selectByPrimaryKey = checkCarNumSourceMapper.getInfoByTypeAndNumId(checkCarNumSource);
		for (CBPCheckCarNumSource cbpCheckCarNumSource : selectByPrimaryKey) {
			if (cbpCheckCarNumSource.getSourcetype().equals(checkCarNumSource.getSourcetype())) {
				int i = checkCarNumSourceMapper.updateSource(checkCarNumSource);//资料类型一样修改勾选状态
				return i;
			}
		}
		int	i = checkCarNumSourceMapper.insertSelective(checkCarNumSource);//资料类型不同添加资料类型
		return i ;
	}
	@Override
	public List<CBPCheckCarNumSource> getInfoBySelected(CBPCheckCarNumSource checkCarNumSource) {
		// TODO Auto-generated method stub
		return checkCarNumSourceMapper.getInfoBySelected(checkCarNumSource);
	}
	@Override
	public int updateCheckNumOrder(CBPCheckCarNumOrder checkCarNumOrder) {
		// TODO Auto-generated method stub
		checkCarNumOrder.setStatus(String.valueOf(CBPConstant.ModuleStatusEnum.PROCESSING.getType()));
		CBPCheckCarNumOrder checkCarNumOrder1 = checkCarNumMapper.getCheckCarNumByOrderId(checkCarNumOrder.getOrderid());
		if (checkCarNumOrder.getBuytax().equals("1")) {
			checkCarNumOrder.setBuytaxvalue("200");
		}else {
			checkCarNumOrder.setBuytaxvalue("0");
		}
		if (checkCarNumOrder.getTempplate().equals("1")) {
			checkCarNumOrder.setTempplatevalue("200");
		}else {
			checkCarNumOrder.setTempplatevalue("0");
		}
		if (checkCarNumOrder.getCheckcar().equals("1")) {
			checkCarNumOrder.setCheckcarvalue("200");
		}else {
			checkCarNumOrder.setCheckcarvalue("0");
		}
		checkCarNumOrder.setSumValues(Integer.parseInt(checkCarNumOrder.getBuytaxvalue())+Integer.parseInt(checkCarNumOrder.getTempplatevalue())
				+Integer.parseInt(checkCarNumOrder.getCheckcarvalue()));//总价
		return checkCarNumMapper.updateByOrderId(checkCarNumOrder);
	}
}
