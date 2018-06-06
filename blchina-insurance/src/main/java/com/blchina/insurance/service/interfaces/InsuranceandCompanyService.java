/*   
 * @(#)InsuranceandCompanyService.java       2018年2月6日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.insurance.service.interfaces;

import java.util.List;

import com.blchina.insurance.model.BDLInsuranceandCompany;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public interface InsuranceandCompanyService {

   List<BDLInsuranceandCompany> selectListByInsuranceId(String substring);

}
