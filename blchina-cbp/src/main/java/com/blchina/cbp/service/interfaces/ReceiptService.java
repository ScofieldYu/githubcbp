/*   
 * @(#)ReceiptService.java       2017年11月28日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.interfaces;

import java.util.List;

import com.blchina.cbp.dto.ReceiptDTO;
import com.blchina.cbp.model.CBPReceipt;

/** 
 * 收据操作service    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public interface ReceiptService {

   int insertOrUpdateReceipt(CBPReceipt cbpReceipt);

   List<String> selectReceipt(String orderId);

   int  saveReceipt(CBPReceipt cbpReceipt);

   String uploadReceipt(ReceiptDTO.Receipt receipt);

}
