/*
 * @(#)BDLCustomerServiceImpl.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.customer.service.impl;

import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;
import com.blchina.customer.dao.BDLCustomerMapper;
import com.blchina.customer.dao.BDLFourCustomerMapper;
import com.blchina.customer.dto.*;
import com.blchina.customer.model.BDLCustomer;
import com.blchina.customer.model.BDLFourCustomer;
import com.blchina.customer.service.interfaces.BDLCustomerService;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *客户操作service
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("BDLCustomerService")
class BDLCustomerServiceImpl implements BDLCustomerService {
    @Autowired
    BDLCustomerMapper bdlCustomerMapper;
    @Autowired
    protected Properties systemConfig;
    @Autowired
    private BDLFourCustomerMapper fourCustomerMapper;
    @Override
    public BDLCustomer findCustomerByPrimaryKey(int customerId) {
       BDLCustomer BDLCustomer = bdlCustomerMapper.selectByPrimaryKey(customerId);
       return BDLCustomer;
    }

    @Override
    public int insertCustomer(BDLCustomer customer){
       int status = bdlCustomerMapper.insert(customer);
       return status;
    }

    @Override
    public Boolean findCustmerByPhone(Long phonenumber) {
        List<BDLCustomer> custmerByPhoneOrCard = bdlCustomerMapper.findCustmerByPhone(phonenumber);
         if(custmerByPhoneOrCard.size()>0){
             return true;
         }
        return false;
    }

    @Override
    public BDLCustomer getCustmerByPhone(Long phonenumber) {
        List<BDLCustomer> custmerByPhoneOrCard = bdlCustomerMapper.findCustmerByPhone(phonenumber);
        if(custmerByPhoneOrCard.size()>0){
            return custmerByPhoneOrCard.get(0);
        }
        return null;
    }

    @Override
    public BDLCustomer getCustmerByCard(String idcardnum) {
        List<BDLCustomer> custmerByPhoneOrCard = bdlCustomerMapper.getCustmerByCard(idcardnum);
        if(custmerByPhoneOrCard.size()>0){
            return custmerByPhoneOrCard.get(0);
        }
        return null;
    }

    @Override
    public int updateCustmerByCard(BDLCustomer customer) {
        return bdlCustomerMapper.updateCustmerByCard(customer);
    }

    @Override
    public int updateCustmerByPhone(BDLCustomer customer) {
        return bdlCustomerMapper.updateCustmerByPhone(customer);
    }

    /**
     * 同步客户信息
     * @param custmerDTO
     * @return
     */
    @Override
    public Boolean synCustomer(CustmerDTO custmerDTO) {
        CustomerManagerDTO cmd=new CustomerManagerDTO();
        String secret="baideli";
        try {
            String timeStamp = HttpsUtil.getTimeStamp();
            String sign = FddEncryptTool.sha1(FddEncryptTool.md5Digest(secret)+timeStamp);
            cmd.setTimestamp(timeStamp);
            cmd.setSign(sign);
            CustomerManagerDetailDTO cmdd=new CustomerManagerDetailDTO();
            cmdd.setCompanyCode(custmerDTO.getOrganizecode());
            cmdd.setEmail(custmerDTO.getEmail());
            cmdd.setMobile(String.valueOf(custmerDTO.getPhonenumber()));
            cmdd.setUserName(custmerDTO.getCustomername());
            List<CustomerManagerDetailDTO> list=new ArrayList<CustomerManagerDetailDTO>();
            list.add(cmdd);
            cmd.setUserInfoList(list);
            JSONObject jsonObject=JSONObject.fromObject(cmd);
            String result = HttpUtil.postbody(systemConfig.getProperty("contract.url") + "sysApi/userInfo/save.do", jsonObject.toString());
            JSONObject getJson=JSONObject.fromObject(result);
            String code=(String)getJson.get("code");
            if(code.equals("200"))
                return true;
        }catch (Exception e){
            e.printStackTrace();
            return  false;

        }

        return false;
    }

    @Override
    public int updateCustomer(BDLCustomer customer) {
        return bdlCustomerMapper.updateByPrimaryKeySelective(customer);
    }

    @Override
    public List<BDLCustomer> getCustmerByPhoneBase(Long phonenumber) {
        return bdlCustomerMapper.getCustmerByPhoneBase(phonenumber);
    }

   @Override
   public String insertOrUpdateFourCustomer(
		 BDLFourCustomerListDTO bdlFourCustomerListDTO) {
	  List<BDLFourCustomer> fourCustomerList = bdlFourCustomerListDTO.getFourCustomerListDTO();
	  int i = 0;
	  List<BDLFourCustomer> list = fourCustomerMapper.selectFourListByCustomerid(fourCustomerList.get(0).getCustomerid());
	  int status = 0;
	  if(list.size()==0){
		 for(BDLFourCustomer fourCustomer:fourCustomerList){
			   status = fourCustomerMapper.insertSelective(fourCustomer);
			   if(status!=0){
				  i++;
			   }
			}
	  }else{
		 for(BDLFourCustomer fourCustomer:fourCustomerList){
			BDLFourCustomer bdlFourCustomer = fourCustomerMapper.selectFourListByCustomeridAndType(fourCustomer);
			bdlFourCustomer.setIdcard(fourCustomer.getIdcard());
			bdlFourCustomer.setName(fourCustomer.getName());
			bdlFourCustomer.setPhonenumber(fourCustomer.getPhonenumber());
			bdlFourCustomer.setType(fourCustomer.getType());
			bdlFourCustomer.setTypename(fourCustomer.getTypename());
			bdlFourCustomer.setAddress(fourCustomer.getAddress());
			bdlFourCustomer.setArea(fourCustomer.getArea());
			status = fourCustomerMapper.updateByPrimaryKeySelective(bdlFourCustomer);
			if(status!=0){
			   i++;
			}
		 }
		 }
	  if(fourCustomerList.size()==i){
		 return "success";
	  }
	  return "error";
   }

   @Override
   public List<BDLFourCustomer> selectFourListByCustomerid(Integer customerid) {
	  
	  return fourCustomerMapper.selectFourListByCustomerid(customerid);
   }

   @Override
   public List<BDLFourCustomer> searchFourCustomer(CardDTO cardDTO) {
	  return fourCustomerMapper.searchFourCustomer(cardDTO);
   }

    @Override
    public List<OpenIdDTO> findOpenId() {
        return bdlCustomerMapper.findOpenId();
    }

}
