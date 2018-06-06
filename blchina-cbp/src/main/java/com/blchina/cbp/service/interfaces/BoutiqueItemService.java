/*   
 * @(#)BoutiqueItemService.java       2018年3月8日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.interfaces;

import java.util.List;

import com.blchina.cbp.model.CBPBoutiqueItem;
import com.blchina.cbp.model.CBPBoutiqueOrder;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public interface BoutiqueItemService {

   List<CBPBoutiqueItem> selectBoutiqueItemList(
		 CBPBoutiqueOrder cbpBoutiqueOrder);

}
