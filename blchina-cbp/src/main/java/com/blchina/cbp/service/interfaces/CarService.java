/*   
 * @(#)CarService.java       2018年2月24日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.interfaces;

import com.blchina.cbp.model.CBPCar;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public interface CarService {

   CBPCar selectCarByOrderId(Integer orderid);

}
