/*   
 * @(#)CheckCarNumOrderService.java       2018年2月22日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.interfaces;

import java.util.List;

import com.blchina.cbp.model.CBPCheckCarNumOrder;
import com.blchina.cbp.model.CBPCheckCarNumSource;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public interface CheckCarNumOrderService {

   List<CBPCheckCarNumOrder> selectCheckCarNumByOrderId(Integer orderid);

   String selectCheckCarNumSourceSizeByType(Integer checkcarnumid,
		 String string);

   CBPCheckCarNumOrder getCheckCarNumByOrderId(Integer orderid);


}
