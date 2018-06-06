/*   
 * @(#)InsuranceCompanyService.java       2018年2月6日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.insurance.service.interfaces;

import java.util.List;

import com.blchina.insurance.model.BDLInsuranceCompany;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public interface InsuranceCompanyService {

   List<BDLInsuranceCompany> selectListByInsuranceId(String substring);

   List<BDLInsuranceCompany> getInsuranceCompany();
}
