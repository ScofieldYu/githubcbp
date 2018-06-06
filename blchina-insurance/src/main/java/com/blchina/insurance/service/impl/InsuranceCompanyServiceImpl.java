/*   
 * @(#)InsuranceCompanyServiceImpl.java       2018年2月6日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.insurance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blchina.insurance.dao.*;
import com.blchina.insurance.model.BDLInsuranceCompany;
import com.blchina.insurance.service.interfaces.InsuranceCompanyService;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@Service("/InsuranceCompanyService")
public class InsuranceCompanyServiceImpl implements InsuranceCompanyService {
   
   @Autowired
   private BDLInsuranceCompanyMapper bdlInsuranceCompanyMapper;

   @Override
   public List<BDLInsuranceCompany> selectListByInsuranceId(String substring) {
	  return bdlInsuranceCompanyMapper.selectListByInsuranceId(substring);
   }

   @Override
   public List<BDLInsuranceCompany> getInsuranceCompany() {
      return bdlInsuranceCompanyMapper.getInsuranceCompany();
   }
}
