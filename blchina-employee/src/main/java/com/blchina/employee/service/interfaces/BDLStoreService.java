/*   
 * @(#)BDLStoreService.java       2017年11月23日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.service.interfaces;

import com.blchina.employee.dto.BDLStoreDTO;
import com.blchina.employee.model.BDLStore;

import java.util.List;

/** 
 * 门店操作service   
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public interface BDLStoreService {
   
  public int insertOrUpdateStore(BDLStoreDTO.Store store);


  BDLStore getSignStoreIdByBrandId(String brandid);

  List<BDLStore> getAllStore();


public String getStoreIdByBrandId(String brandid);

  BDLStore getStoreByBrandId(String companycode);
}
