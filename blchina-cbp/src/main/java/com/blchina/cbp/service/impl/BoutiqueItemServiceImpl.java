/*   
 * @(#)BoutiqueItemServiceImpl.java       2018年3月8日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.cbp.dao.CBPBoutiqueItemMapper;
import com.blchina.cbp.model.CBPBoutiqueItem;
import com.blchina.cbp.model.CBPBoutiqueOrder;
import com.blchina.cbp.service.interfaces.BoutiqueItemService;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@Service("/BoutiqueItemService")
public class BoutiqueItemServiceImpl implements BoutiqueItemService {

   @Autowired
   private CBPBoutiqueItemMapper cbpBoutiqueItemMapper;
   
   
   public List<CBPBoutiqueItem> selectBoutiqueItemList(
		 CBPBoutiqueOrder cbpBoutiqueOrder) {
	  return cbpBoutiqueItemMapper.selectBoutiqueItemList(cbpBoutiqueOrder.getBoutiqueorderid());
   }

}
