/*   
 * @(#)CarServiceImpl.java       2018年2月24日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.cbp.dao.CBPCarMapper;
import com.blchina.cbp.model.CBPCar;
import com.blchina.cbp.service.interfaces.CarService;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@Service("/CarService")
public class CarServiceImpl implements CarService {

   @Autowired
   private CBPCarMapper cbpCarMapper;

   @Override
   public CBPCar selectCarByOrderId(Integer orderid) {
	  return cbpCarMapper.selectCarByOrderId(orderid);
   }
}
