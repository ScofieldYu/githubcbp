/*   
 * @(#)BoutiqueOrderService.java       2018年2月11日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.interfaces;

import java.util.List;

import net.sf.json.JSONObject;

import com.blchina.cbp.dto.CBPBoutiqueItemDTO;
import com.blchina.cbp.model.CBPBoutiqueOrder;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public interface BoutiqueOrderService {

   List<CBPBoutiqueOrder> selectBoutiqueByOrderId(Integer orderid);

   int insertBoutiqueOrder(CBPBoutiqueItemDTO boutiqueItem);

   int updateCreateTime(CBPBoutiqueOrder cbpBoutiqueOrder);

   int deleteBoutiqueOrderByBoutiqueId(CBPBoutiqueOrder boutiqueOrder);


}
