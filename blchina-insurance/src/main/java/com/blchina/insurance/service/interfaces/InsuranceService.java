/*   
 * @(#)InsuranceService.java       2018年2月2日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.insurance.service.interfaces;

import java.util.List;

import com.blchina.insurance.dto.InsuranceDTO;
import com.blchina.insurance.model.BDLInsurance;
import com.blchina.insurance.model.BDLInsuranceLimit;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public interface InsuranceService {

   List<BDLInsurance> getInsuranceListByInsuranceId(String insuranceid);

   List<InsuranceDTO> getAllInsuranceByCompany(InsuranceDTO insurance);

   List<BDLInsurance> getAllInsurance();
}
