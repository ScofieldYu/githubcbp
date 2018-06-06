/*   
 * @(#)OutpocketServiceImpl.java       2017年12月12日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.cbp.dao.CBPOutpocketMapper;
import com.blchina.cbp.model.CBPOutpocket;
import com.blchina.cbp.service.interfaces.OutpocketService;

/** 
 * 垫付证明Service   
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@Service("OutpocketService")
public class OutpocketServiceImpl implements OutpocketService {

   @Autowired
   private CBPOutpocketMapper cbpOutpocketMapper;
   
   @Override
   public List<CBPOutpocket> selectOutpocket(String orderId) {

	  return cbpOutpocketMapper.selectByOrderId(Integer.parseInt(orderId));
   }

   @Override
   public int saveOutpocket(CBPOutpocket cbpOutpocket) {
      return cbpOutpocketMapper.insert(cbpOutpocket);
   }

   @Override
   public void deleteOutpocket(CBPOutpocket cbpOutpocket) {
      cbpOutpocketMapper.deleteByPrimaryKey(cbpOutpocket.getOutpocketid());
   }

}
