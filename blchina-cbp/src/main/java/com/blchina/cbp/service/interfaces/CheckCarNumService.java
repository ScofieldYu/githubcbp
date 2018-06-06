/*   
 * @(#)CheckCarNumService.java       2018年2月22日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.interfaces;

import java.util.List;

import com.blchina.cbp.model.CBPCheckCarNumOrder;
import com.blchina.cbp.model.CBPCheckCarNumSource;

/** 
 * 此类功能描述    验车上牌
 *
 * @author Zhangtong 
 * @since JDK 1.7 
 */
public interface CheckCarNumService {

	int insertCheckCarNumOrder(CBPCheckCarNumOrder checkCarNumOrder);

	int updateByOrderId(CBPCheckCarNumOrder checkCarNumOrder);
	
	List<CBPCheckCarNumOrder> selectCheckCarNumByOrderId(
			Integer orderid);

	int collectionInfo(CBPCheckCarNumSource checkCarNumSource);

	List<CBPCheckCarNumSource> getInfoBySelected(
			CBPCheckCarNumSource checkCarNumSource);

	int updateCheckNumOrder(CBPCheckCarNumOrder checkCarNumOrder);

	

}
