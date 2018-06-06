/*   
 * @(#)InsuranceandCompanyServiceImpl.java       2018年2月6日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.insurance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.insurance.dao.BDLInsuranceandCompanyMapper;
import com.blchina.insurance.model.BDLInsuranceandCompany;
import com.blchina.insurance.service.interfaces.InsuranceandCompanyService;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@Service("/InsuranceandCompanyService")
public class InsuranceandCompanyServiceImpl
	  implements InsuranceandCompanyService {
   
   @Autowired
   private BDLInsuranceandCompanyMapper bdlInsuranceandCompanyMapper;

   @Override
   public List<BDLInsuranceandCompany> selectListByInsuranceId(
		 String substring) {
	  return bdlInsuranceandCompanyMapper.selectListByInsuranceId(substring);
   }
   
   
}
