/*   
 * @(#)FourCustomerServiceImpl.java       2018年2月22日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.cbp.dao.CBPFourCustomerMapper;
import com.blchina.cbp.dao.CBPOrderMapper;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.BDLFourCustomer;
import com.blchina.cbp.dto.BDLFourCustomerListDTO;
import com.blchina.cbp.dto.CardDTO;
import com.blchina.cbp.dto.CustmerDTO;
import com.blchina.cbp.dto.CustomerInfoDTO;
import com.blchina.cbp.dto.FileListDTO;
import com.blchina.cbp.dto.FourCustomerDTO;
import com.blchina.cbp.dto.UploadDTO;
import com.blchina.cbp.exception.BusinessException;
import com.blchina.cbp.exception.WXException;
import com.blchina.cbp.model.BDLCustomer;
import com.blchina.cbp.model.CBPFourCustomer;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.service.interfaces.AuthService;
import com.blchina.cbp.service.interfaces.FourCustomerService;
import com.blchina.cbp.util.UploadUtil;
import com.blchina.common.util.file.FileUtil;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;

import net.sf.json.JSONObject;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@Service("/FourCustomerService")
public class FourCustomerServiceImpl implements FourCustomerService {
   
   @Autowired
   private CBPFourCustomerMapper cbpFourCustomerMapper;
   @Autowired
   private CBPOrderMapper cbpOrderMapper;
   @Autowired
   private Properties systemConfig;
   @Autowired
   private AuthService authService;
   @Override
   public List<CBPFourCustomer> selectFourCustomerListByOrderId(
		 Integer orderid) {
	  return cbpFourCustomerMapper.selectFourCustomerListByOrderId(orderid);
   }

   @Override
   public WeixinResult insertOrUpdateFourCustomer(FourCustomerDTO dto) {
	  WeixinResult result111 = new WeixinResult();
	  result111.setCode(CBPConstant.CODE_FAILURE);
	  result111.setMessage(CBPConstant.MESSAGE_FAILURE);
	  List<CBPFourCustomer> fourCustomerList = dto.getFourCustomerList();
	  int i = 0;
	  Integer customerid = null;
	  int status = 0;
	  for(CBPFourCustomer cfc:fourCustomerList){
		 if(CBPConstant.FourCustomerTypeEnum.SOLD.getType().equals(cfc.getType())){
			//验证客户是否实名认证
			CustmerDTO customer = new CustmerDTO();
			customer.setCustomername(cfc.getName());
			customer.setIdcardnum(cfc.getIdcard());
			customer.setPhonenumber(Long.valueOf(cfc.getPhonenumber()));
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("phonenumber", Long.valueOf(cfc.getPhonenumber()));
			jsonObject.put("idcardnum", cfc.getIdcard());
			CustomerInfoDTO cid = null;
			try {
			   String result = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerBase", jsonObject.toString());
			   JSONObject jsonObject2 = JSONObject.fromObject(result);
			   WeixinResult res = (WeixinResult) JSONObject.toBean(jsonObject2, WeixinResult.class);
			   Object object = res.getData();
			   JSONObject object2 = JSONObject.fromObject(object);
			   cid = (CustomerInfoDTO) JSONObject.toBean(object2, CustomerInfoDTO.class);
			   if(StringUtil.isNullorEmpty(object+"")){
				  //用户没有认证过
				  WeixinResult weixinResult = custmerAuth(customer);
				  if(!CBPConstant.CODE_SUCCESS.equals(weixinResult.getCode())){
					 return weixinResult;
				  }
				  customerid = (Integer) weixinResult.getData();
			   }else{
				  //用户认证过
				  BDLCustomer bdlCustomer = cid.getCustomer();
				  customerid = bdlCustomer.getCustomerid();
			   }
			}
			catch (Exception e) {
			   throw new WXException("用户认证异常");
			}
		   }
	  }
	  CBPOrder cbpOrder = null;
	  if(!StringUtil.isNullorEmpty(fourCustomerList.get(0).getOrderid()+"")){
		 cbpOrder = cbpOrderMapper.selectByPrimaryKey(Integer.valueOf(dto.getFourCustomerList().get(0).getOrderid()));
	  }
	  BDLFourCustomerListDTO bdlFourCustomerListDTO = new BDLFourCustomerListDTO();
	  List<BDLFourCustomer> bdlFourCustomerList = new ArrayList<>();
	  for(CBPFourCustomer fourCustomer:fourCustomerList){
		 if(!StringUtil.isNullorEmpty(fourCustomer.getOrderid()+"")){
			CBPFourCustomer cfc = cbpFourCustomerMapper.selectByOrderIdAndType(fourCustomer);
			if(cfc!=null){
			   fourCustomer.setFourcustomerid(cfc.getFourcustomerid());
			   status = cbpFourCustomerMapper.updateByPrimaryKeySelective(fourCustomer);
			}
			if(status!=0){
			   i++;
			}
			if(CBPConstant.FourCustomerTypeEnum.SOLD.getType().equals(fourCustomer.getType())){
			   cbpOrder.setIdcardnum(fourCustomer.getIdcard());
    		   cbpOrder.setPhonenumber(fourCustomer.getPhonenumber());
    		   cbpOrder.setCustomername(fourCustomer.getName());
			}
			if(CBPConstant.FourCustomerTypeEnum.DELIVERY.getType().equals(fourCustomer.getType())){
			   cbpOrder.setBuyername(fourCustomer.getName());
    		   cbpOrder.setBuyeridcardnum(fourCustomer.getIdcard());
    		   cbpOrder.setCustomerid(customerid);
			}
		 }
			BDLFourCustomer bfc = new BDLFourCustomer();
			 bfc.setCustomerid(customerid);
			 bfc.setIdcard(fourCustomer.getIdcard());
			 bfc.setName(fourCustomer.getName());
			 bfc.setType(fourCustomer.getType());
			 bfc.setTypename(fourCustomer.getTypename());
			 bfc.setPhonenumber(fourCustomer.getPhonenumber());
			 bfc.setAddress(fourCustomer.getAddress());
			 bfc.setArea(fourCustomer.getArea());
			 bdlFourCustomerList.add(bfc);
	  }
	  	if(!StringUtil.isNullorEmpty(fourCustomerList.get(0).getOrderid()+"")){
	  	   int j = cbpOrderMapper.updateByPrimaryKeySelective(cbpOrder);
	  	   if(j==0){
	  		  throw new WXException("修改订单信息异常");
	  	   }
	  	}
	  		if(fourCustomerList.size()==i || bdlFourCustomerList.size()==fourCustomerList.size()){
			 bdlFourCustomerListDTO.setFourCustomerListDTO(bdlFourCustomerList);
			 try {
				 JSONObject jsonObject7 = JSONObject.fromObject(bdlFourCustomerListDTO);
				 String result = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/insertOrUpdateFourCustomer", jsonObject7.toString());
				 if(!"success".equals(result)){
					throw new WXException("维护CS四方数据异常");
				 }else{
					result111.setCode(CBPConstant.CODE_SUCCESS);
					result111.setMessage(CBPConstant.MESSAGE_SUCCESS);
					result111.setData(customerid);
				 }
			  }
			  catch (Exception e) {
				 throw new WXException("维护CS四方数据异常");
			  }
	  		}else{
	  		   throw new WXException("维护CBP四方数据异常");
	  		}
	  		return result111;
   		}

   @Override
   public CBPFourCustomer selectFourCustomerByOrderIdAndType(Integer orderid) {
	  CBPFourCustomer fourCustomer = new CBPFourCustomer();
	  fourCustomer.setOrderid(orderid);
	  fourCustomer.setType(CBPConstant.FourCustomerTypeEnum.DELIVERY.getType());
	  return cbpFourCustomerMapper.selectByOrderIdAndType(fourCustomer);
   }
   
   
   public WeixinResult custmerAuth(CustmerDTO customer) {
      WeixinResult res = new WeixinResult();
      res.setCode(CBPConstant.CODE_NULL_PARAM);
      res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
      String identitycardnum = customer.getIdcardnum();
      Long phonenumber = customer.getPhonenumber();
      String customername = customer.getCustomername();
      customer.setAccounttype(String.valueOf(CBPConstant.CAccountTypeEnum.PRIVATE.getType()));
      if (!StringUtil.isNullorEmpty(identitycardnum) && !StringUtil.isNullorEmpty(customername) && !StringUtil.isNullorEmpty(String.valueOf(phonenumber))) {
          JSONObject jsonOject =new JSONObject();
          jsonOject.put("phonenumber",phonenumber);
          jsonOject.put("customername",customername);
          jsonOject.put("idcardnum",identitycardnum);
          jsonOject.put("accounttype",String.valueOf(CBPConstant.CAccountTypeEnum.PRIVATE.getType()));

          try {
              //去customer校验验证次数
              String getInfo = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/authInfo", jsonOject.toString());
              JSONObject getInfoJSON = JSONObject.fromObject(getInfo);
              res = (WeixinResult) JSONObject.toBean(getInfoJSON, WeixinResult.class);
              if (!res.getCode().equals(CBPConstant.CODE_SUCCESS)) {
                  return res;
              }
              //去法大大认证
              WeixinResult getRes = authService.seltauth(customer);
              String code = getRes.getCode();
              JSONObject fadadaJson = JSONObject.fromObject(getRes.getData());
              if (code.equals(CBPConstant.CODE_SUCCESS)) {//认证通过更新用户信息
                  String customer_id = (String) fadadaJson.get("customer_id");
                  String customercarduuid = customer.getCustomercarduuid();
                  if(!StringUtil.isNullorEmpty(customercarduuid)){
                      if(customercarduuid.equals("1")){//是1不传
                      }else {
                          File file= FileUtil.GenerateImage(customercarduuid,systemConfig.getProperty("file.url.image")+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".png","image");
                          List<File> cardList=new ArrayList<File>();
                          cardList.add(file);
                          UploadDTO uploadDTO=new UploadDTO();
                          uploadDTO.setIdcardnum(customer.getIdcardnum());
                          uploadDTO.setType(CBPConstant.FileEnum.CUSTOMERIDCARD.getType());
                          String result = UploadUtil.uploadByFile(systemConfig.getProperty("contract.url")+"upload/fileUpload.do",cardList,new ArrayList<String>(),uploadDTO,null,null,systemConfig.getProperty("fadada.appSecret"));
                          JSONObject getJson=JSONObject.fromObject(result);
                          if(String.valueOf(getJson.get("code")).equals("200")){
                              getJson.remove("data");
                              FileListDTO fileuuidList = (FileListDTO)JSONObject.toBean(getJson, FileListDTO.class);
                              if(String.valueOf(fileuuidList.getCode()).equals("200")){
                                  String uuid=fileuuidList.getObj()[0].getUuid();
                                  jsonOject.put("customercarduuid",uuid);
                              }

                          }
                      }
                  }else {
                      jsonOject.put("customercarduuid","");
                  }
                  jsonOject.put("signcustomerid", customer_id);
                  String result = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/updateAuthCustmer", jsonOject.toString());
                  JSONObject resObject = JSONObject.fromObject(result);
                  res = (WeixinResult) JSONObject.toBean(resObject, WeixinResult.class);
              } else {
                  res.setCode(CBPConstant.CODE_FAILURE);
                  res.setMessage("认证错误");
              }
          } catch (Exception e) {
              return res;
          }
      }
      return res;
  }

   @Override
   public List<CBPFourCustomer> searchFourCustomer(CardDTO cd) {
	  return cbpFourCustomerMapper.searchFourCustomer(cd);
   }
}
