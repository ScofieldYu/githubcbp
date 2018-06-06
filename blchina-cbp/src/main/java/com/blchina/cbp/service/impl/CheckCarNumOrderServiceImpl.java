/*   
 * @(#)CheckCarNumOrderServiceImpl.java       2018年2月22日  
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
import com.blchina.cbp.model.CBPCheckCarNumOrder;
import com.blchina.cbp.model.CBPCheckCarNumSource;
import com.blchina.cbp.service.interfaces.CheckCarNumOrderService;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@Service("/CheckCarNumOrderService")
public class CheckCarNumOrderServiceImpl implements CheckCarNumOrderService {

   @Autowired
   private CBPCheckCarNumOrderMapper cbpCheckCarNumOrderMapper;
   @Autowired
   private CBPCheckCarNumSourceMapper cbpCheckCarNumSourceMapper;
   @Override
   public List<CBPCheckCarNumOrder> selectCheckCarNumByOrderId(
		 Integer orderid) {
	  return cbpCheckCarNumOrderMapper.selectCheckCarNumByOrderId(orderid);
   }
   @Override
   public String selectCheckCarNumSourceSizeByType(Integer checkcarnumid,
		 String string) {
	  CBPCheckCarNumSource ccns = new CBPCheckCarNumSource();
	  ccns.setBelongtype(string);
	  ccns.setCheckcarnumid(checkcarnumid);
	  List<CBPCheckCarNumSource> list = cbpCheckCarNumSourceMapper.selectCheckCarNumSourceListByType(ccns);
	  int i =0;
	  if(list.size()!=0){
		 for(CBPCheckCarNumSource checkCarNumSource :list){
			if("1".equals(checkCarNumSource.getIschoiced())){
			   i++;
			}
		 }
	  }
	  return String.valueOf(i)+"/"+String.valueOf(list.size());
   }
@Override
public CBPCheckCarNumOrder getCheckCarNumByOrderId(Integer orderid) {
	// TODO Auto-generated method stub
	return cbpCheckCarNumOrderMapper.getCheckCarNumByOrderId(orderid);
}


}
