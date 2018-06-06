/*   
 * @(#)InsuranceServiceImpl.java       2018年2月2日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.insurance.service.impl;

import java.util.List;

import com.blchina.insurance.dto.InsuranceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.insurance.dao.BDLInsuranceMapper;
import com.blchina.insurance.model.BDLInsurance;
import com.blchina.insurance.service.interfaces.InsuranceService;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@Service("/InsuranceService")
public class InsuranceServiceImpl implements InsuranceService {

   @Autowired
   private BDLInsuranceMapper bdlInsuranceMapper;
   @Override
   public List<BDLInsurance> getInsuranceListByInsuranceId(String insuranceid) {
	  return bdlInsuranceMapper.selectListByInsuranceId(insuranceid);
   }

   @Override
   public List<InsuranceDTO> getAllInsuranceByCompany(InsuranceDTO insurance) {
      return bdlInsuranceMapper.getAllInsuranceByCompany(insurance);
   }

   @Override
   public List<BDLInsurance> getAllInsurance() {
      return bdlInsuranceMapper.getAllInsurance();
   }

}
