/*   
 * @(#)OutpocketService.java       2017年12月12日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.interfaces;

import java.util.List;

import com.blchina.cbp.model.CBPOutpocket;

/** 
 * 垫付证明Service    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public interface OutpocketService {

   List<CBPOutpocket> selectOutpocket(String orderId);

   int  saveOutpocket(CBPOutpocket cbpOutpocket);

   void deleteOutpocket(CBPOutpocket cbpOutpocket);
}
