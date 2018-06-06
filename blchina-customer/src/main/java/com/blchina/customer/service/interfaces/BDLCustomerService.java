/*
 * @(#)BDLCustomerService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.customer.service.interfaces;

import com.blchina.customer.dto.BDLFourCustomerListDTO;
import com.blchina.customer.dto.CardDTO;
import com.blchina.customer.dto.CustmerDTO;
import com.blchina.customer.dto.OpenIdDTO;
import com.blchina.customer.model.BDLCustomer;
import com.blchina.customer.model.BDLFourCustomer;

import java.util.List;

/**
 *客户操作service
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface BDLCustomerService {
    public BDLCustomer findCustomerByPrimaryKey(int customerId);
    public int insertCustomer(BDLCustomer customer);
    public Boolean findCustmerByPhone(Long phonenumber);
    public BDLCustomer getCustmerByPhone(Long phonenumber);
    public BDLCustomer getCustmerByCard(String idcardnum);
    int updateCustmerByCard(BDLCustomer customer);
    int updateCustmerByPhone(BDLCustomer customer);
    Boolean synCustomer(CustmerDTO custmerDTO);
    int updateCustomer(BDLCustomer customer);

    List<BDLCustomer> getCustmerByPhoneBase(Long phonenumber);
   public String insertOrUpdateFourCustomer(
		 BDLFourCustomerListDTO bdlFourCustomerListDTO);
   public List<BDLFourCustomer> selectFourListByCustomerid(Integer customerid);
   public List<BDLFourCustomer> searchFourCustomer(CardDTO cardDTO);
    List<OpenIdDTO> findOpenId();
}
