/*   
 * @(#)BDLCustomerInfoMapper.java       2017年12月4日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */
package com.blchina.customer.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.blchina.customer.dto.CustomerMessageDTO;
import com.blchina.customer.model.BDLCustomerInfo;

/**
 * 此类功能描述
 *
 * @author yangyuchao
 * @since JDK 1.8
 */
@Service
public interface BDLCustomerInfoMapper {
   int deleteByPrimaryKey(Integer customerinfoid);

   int insert(BDLCustomerInfo record);

   int insertSelective(BDLCustomerInfo record);

   BDLCustomerInfo selectByPrimaryKey(Integer customerinfoid);

   int updateByPrimaryKeySelective(BDLCustomerInfo record);

   int updateByPrimaryKey(BDLCustomerInfo record);

   BDLCustomerInfo getPrivateInfo(BDLCustomerInfo info);

   BDLCustomerInfo getPublicInfo(BDLCustomerInfo info);

   List<BDLCustomerInfo> getCustomerInfo(Integer customerid);

   String getsignCustomerId(Integer customerid);

   BDLCustomerInfo selectCustomerInfo(Map map);

   BDLCustomerInfo selectCustomerInfo1(Map map);

   String getsignCustomerId(BDLCustomerInfo customerinfo);

   int updateCustomerInfoUUID(BDLCustomerInfo info);

   List<BDLCustomerInfo> getPrivateInfoList(BDLCustomerInfo info);

   List<CustomerMessageDTO> getCustomerInfoListByCustomerIdAndAccountType(Map map);

   Integer getCustomerInfoCountByByCustomerIdAndAccountType(Map map);

   List<CustomerMessageDTO> getCustomerInfoListByCustomerIdAndAccountTypes(
		 Map map);
   List<BDLCustomerInfo> getCustomerInfoBase(Integer customerid);

   List<BDLCustomerInfo> getCustomerInfoTotal(Integer customerid);
}

