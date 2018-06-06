/*
 * @(#)CustomerInfoServiceImpl.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.customer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blchina.customer.model.BDLCustomer;
import com.blchina.customer.service.interfaces.BDLCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.common.util.page.Page;
import com.blchina.common.util.string.StringUtil;
import com.blchina.customer.dao.BDLCustomerInfoMapper;
import com.blchina.customer.datamodel.CBPConstant;
import com.blchina.customer.datamodel.weixin.WeixinResult;
import com.blchina.customer.dto.CustmerDTO;
import com.blchina.customer.dto.CustomerInfoQueryDTO;
import com.blchina.customer.dto.CustomerMessageDTO;
import com.blchina.customer.dto.DocumentQueryModel;
import com.blchina.customer.model.BDLCustomerInfo;
import com.blchina.customer.service.interfaces.CustomerInfoService;
import com.mysql.fabric.xmlrpc.base.Array;

import antlr.StringUtils;

/**
 *客户info操作service
 * @author zhuchenglong
 * @since JDK 1.8
 */

@Service("CustomerInfoService")
public class CustomerInfoServiceImpl implements CustomerInfoService{
    @Autowired
    private BDLCustomerInfoMapper customerInfoMapper;
    @Autowired
    private BDLCustomerService customerService;
    @Override
    public int insertCustomerInfo(BDLCustomerInfo customer) {
        return customerInfoMapper.insert(customer);
    }

    @Override
    public WeixinResult updateCusomerInfo(Integer customerid, CustmerDTO customer) {
        WeixinResult res = new WeixinResult();
        String accounttype = customer.getAccounttype();
        BDLCustomerInfo info=new BDLCustomerInfo();
        info.setCustomerid(customerid);
        info.setOrganizecode(customer.getOrganizecode());
        info.setOrganizename(customer.getOrganizename());
        info.setAccounttype(accounttype);
        info.setBuyeridcardnum(customer.getBuyeridcardnum());
        info.setBuyername(customer.getBuyername());
        info.setAttorneyuuid(customer.getAttorneyuuid());
        info.setBusilicenseuuid(customer.getBusilicenseuuid());
        info.setCustomercarduuid(customer.getCustomercarduuid());
        BDLCustomerInfo customerInfo=null;
        int i =0;
        if (accounttype == null || accounttype.equals("1")) {//是私户
             info.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PRIVATE.getType()));
             List<BDLCustomerInfo> list=customerInfoMapper.getPrivateInfoList(info);
            if (list.size() == 0) {//没有认证需要认证
                res.setCode(CBPConstant.CODE_CA_SELF_FALSE);
                res.setMessage(CBPConstant.MESSAGE_CA_SELF_FALSE);
                return res;
            }
            customerInfo=customerInfoMapper.getPrivateInfo(info);
            info.setBuyeridcarduuid(customer.getBuyeridcarduuid());
            info.setBuyername(customer.getBuyername());
            if(customerInfo==null){
                i=customerInfoMapper.insert(info);
            }else {
                info.setCustomerinfoid(customerInfo.getCustomerinfoid());
                i=customerInfoMapper.updateByPrimaryKeySelective(info);
            }

        } else {//是公户
            info.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PUBLIC.getType()));
            customerInfo=customerInfoMapper.getPublicInfo(info);
            if (customerInfo == null) {//公户没有认证
                res.setCode(CBPConstant.CODE_CA_COMPANY_FALSE);
                res.setMessage(CBPConstant.MESSAGE_CA_COMPANY_FALSE);
                return res;
            }
            info.setCustomerinfoid(customerInfo.getCustomerinfoid());
           i= customerInfoMapper.updateByPrimaryKeySelective(info);
        }
        if(i==1){
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        }else{
            res.setCode(CBPConstant.CODE_FAILURE);
            res.setMessage(CBPConstant.MESSAGE_FAILURE);
        }
        return  res;
    }

    @Override
    public void insertOrUpdateCustomerInfo(BDLCustomerInfo customerInfo) {
        String accounttype=customerInfo.getAccounttype();
        BDLCustomerInfo info=null;
        if (accounttype == null || accounttype.equals("1")) {//是私户
            customerInfo.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PRIVATE.getType()));
            info=customerInfoMapper.getPrivateInfo(customerInfo);
        } else {//是公户
            customerInfo.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PUBLIC.getType()));
            info=customerInfoMapper.getPublicInfo(customerInfo);
        }
        if(info==null){
            if (accounttype == null || accounttype.equals("1")) {//是私户
                customerInfo.setSigncount(9);
            }
            customerInfoMapper.insert(customerInfo);
        }else{
            customerInfo.setCustomerinfoid(info.getCustomerinfoid());
            customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
        }
    }


    @Override
    public Boolean doCustomerInfoByCustomerid(BDLCustomerInfo customerinfo) {
        BDLCustomerInfo info=customerInfoMapper.getPrivateInfo(customerinfo);
        if(info==null){
            return true;
        }
        Integer signcount = info.getSigncount();
        if(signcount==null){
            info.setSigncount(9);
            customerInfoMapper.updateByPrimaryKeySelective(info);
            return true;
        }else  if(signcount.intValue()==0){
            return false;
        }else {
            info.setSigncount(signcount.intValue()-1);
            customerInfoMapper.updateByPrimaryKeySelective(info);
            return true;

        }
    }

    @Override
    public List<BDLCustomerInfo> getCustomerInfo(Integer customerid) {
        return customerInfoMapper.getCustomerInfo(customerid);
    }

    @Override
    public String getsignCustomerId(BDLCustomerInfo customerinfo) {
        return customerInfoMapper.getsignCustomerId(customerinfo);
    }

    @Override
    public int updateCustomerInfo(BDLCustomerInfo info) {
        return customerInfoMapper.updateByPrimaryKeySelective(info);
    }

    @Override
    public int updateCustomerInfoUUID(BDLCustomerInfo info) {
        int i=0;
        BDLCustomerInfo getinfo=null;
        if (info.getOrganizecode()==null||info.getOrganizecode().equals("")){
            info.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PRIVATE.getType()));
            getinfo=customerInfoMapper.getPrivateInfo(info);
        }else {
            info.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PUBLIC.getType()));
            getinfo=customerInfoMapper.getPublicInfo(info);
        }
        if(getinfo==null){
            i= customerInfoMapper.insert(info);
        }else {
            info.setCustomerinfoid(getinfo.getCustomerinfoid());
            i=customerInfoMapper.updateByPrimaryKeySelective(info);
        }
        return i;
    }

    @Override
    public void synOrderCustomer(BDLCustomerInfo info) {
        if (info.getOrganizecode() == null||info.getOrganizecode().equals("")) {//是私户
            info.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PRIVATE.getType()));
            BDLCustomerInfo privateInfo = customerInfoMapper.getPrivateInfo(info);
            if (privateInfo == null) {//没有认证需要认证
             info.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PRIVATE.getType()));
                customerInfoMapper.insert(info);
            }
        } else {//是公户
            BDLCustomerInfo publicInfo = customerInfoMapper.getPublicInfo(info);
            if (publicInfo == null) {//公户没有认证
                info.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PUBLIC.getType()));
                customerInfoMapper.insert(info);
            }
        }
    }

    @Override
    public List<BDLCustomerInfo> getCustomerInfoBase(Integer customerid) {
        return customerInfoMapper.getCustomerInfoBase(customerid);
    }

    @Override
    public List<BDLCustomerInfo> getCustomerInfoOnly(Integer customerid, CustmerDTO customer) {
        BDLCustomerInfo info=new BDLCustomerInfo();
        info.setCustomerid(customerid);
        List<BDLCustomerInfo> list=new ArrayList<BDLCustomerInfo>();
        if(StringUtil.isNullorEmpty(customer.getOrganizecode())){
            info.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PRIVATE.getType()));
            BDLCustomerInfo privateInfo = customerInfoMapper.getPrivateInfo(info);
            if(privateInfo!=null) {
                list.add(privateInfo);
            }

        }else {
            info.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PUBLIC.getType()));
            BDLCustomerInfo publicInfo = customerInfoMapper.getPublicInfo(info);
            if(publicInfo!=null){
                list.add(publicInfo);
            }
        }
        return list;
    }

    @Override
    public List<BDLCustomerInfo> getCustomerInfoTotal(Integer customerid) {
        return customerInfoMapper.getCustomerInfoTotal(customerid);
    }

    @Override
   public String selectCustomerInfoUUID(DocumentQueryModel documentQueryModel) {
	  	Map map = new HashMap();
	  if(StringUtil.isNullorEmpty(documentQueryModel.getOrganizeCode())){
		 map.put("customerId", Integer.parseInt(documentQueryModel.getCustomerId()));
		 map.put("buyerIdCardNum", documentQueryModel.getBuyerIdCardNum());
		 BDLCustomerInfo bdlCustomerInfo = customerInfoMapper.selectCustomerInfo(map);
		 if(CBPConstant.FileEnum.CUSTOMERIDCARD.getType().equals(documentQueryModel.getDocumentType())){
			return bdlCustomerInfo.getCustomercarduuid();
		 }
		 if(CBPConstant.FileEnum.BUYERIDCARD.getType().equals(documentQueryModel.getDocumentType())){
			return bdlCustomerInfo.getBuyeridcarduuid();
		 }
		 if(CBPConstant.FileEnum.BUSINLICENSE.getType().equals(documentQueryModel.getDocumentType())){
			return bdlCustomerInfo.getBusilicenseuuid();
		 }
		 if(CBPConstant.FileEnum.ATTORNEY.getType().equals(documentQueryModel.getDocumentType())){
			return bdlCustomerInfo.getAttorneyuuid();
		 }
	  }else{
		 map.put("customerId", documentQueryModel.getCustomerId());
		 map.put("organizeCode", documentQueryModel.getOrganizeCode());
		 BDLCustomerInfo bdlCustomerInfo = customerInfoMapper.selectCustomerInfo1(map);
		 if(CBPConstant.FileEnum.CUSTOMERIDCARD.getType().equals(documentQueryModel.getDocumentType())){
			return bdlCustomerInfo.getCustomercarduuid();
		 }
		 if(CBPConstant.FileEnum.BUYERIDCARD.getType().equals(documentQueryModel.getDocumentType())){
			return bdlCustomerInfo.getBuyeridcarduuid();
		 }
		 if(CBPConstant.FileEnum.BUSINLICENSE.getType().equals(documentQueryModel.getDocumentType())){
			return bdlCustomerInfo.getBusilicenseuuid();
		 }
		 if(CBPConstant.FileEnum.ATTORNEY.getType().equals(documentQueryModel.getDocumentType())){
			return bdlCustomerInfo.getAttorneyuuid();
		 }
	  }
	  return null;
   }

   @Override
   public String getsignCustomerId(Integer customerid) {
	  // TODO Auto-generated method stub
	  return null;
   }

   @Override
   public Page getCustomerInfoList(CustomerInfoQueryDTO customerInfoQueryDTO) {
	  Page page = new Page();
	  //私户
	  if(customerInfoQueryDTO.getAccountType().equals(CBPConstant.CAccountTypeEnum.PRIVATE.getType()+"")){
		 String customerId = customerInfoQueryDTO.getCustomerId();
		 String accountType = customerInfoQueryDTO.getAccountType();
		 //起始索引
		 Integer index = null;
		 Integer pageSize = null;
		 if(StringUtil.isNullorEmpty(customerInfoQueryDTO.getPageSize()+"") || customerInfoQueryDTO.getPageSize()==0){
			pageSize = 10;
		 }else{
			pageSize = customerInfoQueryDTO.getPageSize();
		 }
		 if(StringUtil.isNullorEmpty(customerInfoQueryDTO.getCurrentPage()+"")){
			index = 0;
		 }else{
			index = (customerInfoQueryDTO.getCurrentPage()-1)*pageSize;
		 }
		 Map map = new HashMap();
		 map.put("customerId", customerId);
		 map.put("accountType", accountType);
		 map.put("index", index);
		 map.put("pageSize", pageSize);
		 List<CustomerMessageDTO> list =  customerInfoMapper.getCustomerInfoListByCustomerIdAndAccountType(map);
		 System.out.println(list.size());
		 Integer count = customerInfoMapper.getCustomerInfoCountByByCustomerIdAndAccountType(map);
		 System.out.println("======================="+count);
		 page.setCurrentPage(customerInfoQueryDTO.getCurrentPage());
		 page.setPageSize(pageSize);
		 page.setTotalRecord(count);
		 page.setDatas(CustomerMessageDTONoNull(list));
	  }else{
		 String customerId = customerInfoQueryDTO.getCustomerId();
		 String accountType = customerInfoQueryDTO.getAccountType();
		 //起始索引
		 Integer index = null;
		 Integer pageSize = null;
		 if(StringUtil.isNullorEmpty(customerInfoQueryDTO.getPageSize()+"")|| customerInfoQueryDTO.getPageSize()==0){
			pageSize = 10;
		 }else{
			pageSize = customerInfoQueryDTO.getPageSize();
		 }
		 if(StringUtil.isNullorEmpty(customerInfoQueryDTO.getCurrentPage()+"")){
			index = 0;
		 }else{
			index = (customerInfoQueryDTO.getCurrentPage()-1)*pageSize;
		 }
		 Map map = new HashMap();
		 map.put("customerId", customerId);
		 map.put("accountType", accountType);
		 map.put("index", index);
		 map.put("pageSize", pageSize);
		 System.out.println("-----"+customerId+"---"+accountType+"index="+index);
		 List<CustomerMessageDTO> list =  customerInfoMapper.getCustomerInfoListByCustomerIdAndAccountTypes(map);
		 System.out.println("------------------"+list.size());
		 Integer count = customerInfoMapper.getCustomerInfoCountByByCustomerIdAndAccountType(map);
		 System.out.println("======================="+count);
		 page.setCurrentPage(customerInfoQueryDTO.getCurrentPage());
		 page.setPageSize(pageSize);
		 page.setTotalRecord(count);
		 page.setDatas(CustomerMessageDTONoNull(list));
	  }
	  return page;
   }

   private static List<CustomerMessageDTO> CustomerMessageDTONoNull(List<CustomerMessageDTO> list){
	  List<CustomerMessageDTO> list1 = new ArrayList<CustomerMessageDTO>();
		 for(CustomerMessageDTO customerMessageDTO :list){
			CustomerMessageDTO cmDTO = new CustomerMessageDTO();
			if(!StringUtil.isNullorEmpty(customerMessageDTO.getPhoneNumber())){
			   cmDTO.setPhoneNumber(customerMessageDTO.getPhoneNumber());
			}else{
			   cmDTO.setPhoneNumber("");
			}
			if(!StringUtil.isNullorEmpty(customerMessageDTO.getBuyerIdCardNum())){
			   cmDTO.setBuyerIdCardNum(customerMessageDTO.getBuyerIdCardNum());
			}else{
			   cmDTO.setBuyerIdCardNum("");
			}
			if(!StringUtil.isNullorEmpty(customerMessageDTO.getBuyerName())){
			   cmDTO.setBuyerName(customerMessageDTO.getBuyerName());
			}else{
			   cmDTO.setBuyerName("");
			}
			if(!StringUtil.isNullorEmpty(customerMessageDTO.getCustomerName())){
			   cmDTO.setCustomerName(customerMessageDTO.getCustomerName());
			}else{
			   cmDTO.setCustomerName("");
			}
			if(!StringUtil.isNullorEmpty(customerMessageDTO.getIdcardNum())){
			   cmDTO.setIdcardNum(customerMessageDTO.getIdcardNum());
			}else{
			   cmDTO.setIdcardNum("");
			}
			if(!StringUtil.isNullorEmpty(customerMessageDTO.getOrganizeCode())){
			   cmDTO.setOrganizeCode(customerMessageDTO.getOrganizeCode());
			}else{
			   cmDTO.setOrganizeCode("");
			}
			if(!StringUtil.isNullorEmpty(customerMessageDTO.getOrganizeName())){
			   cmDTO.setOrganizeName(customerMessageDTO.getOrganizeName());
			}else{
			   cmDTO.setOrganizeName("");
			}
			if(!StringUtil.isNullorEmpty(customerMessageDTO.getCustomerInfoId()+"")){
			   cmDTO.setCustomerInfoId(customerMessageDTO.getCustomerInfoId());
			}else{
			   cmDTO.setCustomerInfoId(0);
			}
			list1.add(cmDTO);
		 }
	  return list1;
   }
}
