/*
 * @(#)CustomerInfoService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.customer.service.interfaces;

import com.blchina.common.util.page.Page;
import com.blchina.customer.datamodel.weixin.WeixinResult;
import com.blchina.customer.dto.CustmerDTO;
import com.blchina.customer.dto.CustomerInfoQueryDTO;
import com.blchina.customer.dto.DocumentQueryModel;
import com.blchina.customer.model.BDLCustomerInfo;

import java.util.List;

/**
 *客户info操作service
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface CustomerInfoService {
    int insertCustomerInfo(BDLCustomerInfo customer);

    WeixinResult updateCusomerInfo(Integer customerid, CustmerDTO customer);

    void insertOrUpdateCustomerInfo(BDLCustomerInfo customerInfo);

    Boolean doCustomerInfoByCustomerid(BDLCustomerInfo customerinfo);

    List<BDLCustomerInfo> getCustomerInfo(Integer customerid);

    String getsignCustomerId(Integer customerid);

   String selectCustomerInfoUUID(DocumentQueryModel documentQueryModel);
    String getsignCustomerId(BDLCustomerInfo info);

    int updateCustomerInfo(BDLCustomerInfo info);

    int updateCustomerInfoUUID(BDLCustomerInfo info);

    void synOrderCustomer(BDLCustomerInfo info);

   Page getCustomerInfoList(CustomerInfoQueryDTO customerInfoQueryDTO);
    List<BDLCustomerInfo> getCustomerInfoBase(Integer customerid);

    List<BDLCustomerInfo> getCustomerInfoOnly(Integer customerid, CustmerDTO customer);

    List<BDLCustomerInfo> getCustomerInfoTotal(Integer customerid);
}
