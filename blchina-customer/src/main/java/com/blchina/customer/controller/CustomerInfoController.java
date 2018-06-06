/*   
 * @(#)CustomerInfoController.java       2017年12月1日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.common.util.page.Page;
import com.blchina.common.util.string.StringUtil;
import com.blchina.customer.datamodel.CBPConstant;
import com.blchina.customer.dto.CustomerInfoFileDTO;
import com.blchina.customer.dto.CustomerInfoQueryDTO;
import com.blchina.customer.dto.DocumentQueryModel;
import com.blchina.customer.model.BDLCustomer;
import com.blchina.customer.model.BDLCustomerInfo;
import com.blchina.customer.service.interfaces.BDLCustomerService;
import com.blchina.customer.service.interfaces.CustomerInfoService;

/** 
 * 客户信息操作controller    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/customerInfo")
public class CustomerInfoController {
   
   @Autowired
   private CustomerInfoService customerInfoService;
    @Autowired
    private BDLCustomerService bdlCustomerService;


    @RequestMapping("/updateCustomerInfoUUID")
    public String updateCustomerInfoUUID(@RequestBody CustomerInfoFileDTO infoFileDTO ){
        BDLCustomerInfo info=new BDLCustomerInfo();
        BDLCustomer getCustomer = bdlCustomerService.findCustomerByPrimaryKey(infoFileDTO.getCustomerid());
        String type = infoFileDTO.getType();
        if(type.equals(CBPConstant.FileEnum.CUSTOMERIDCARD.getType())){
            info.setCustomercarduuid(infoFileDTO.getUuid());
        }else if(type.equals(CBPConstant.FileEnum.BUYERIDCARD.getType())){
            info.setBuyeridcardnum(infoFileDTO.getUuid());
        }else if(type.equals(CBPConstant.FileEnum.ATTORNEY.getType())){
            if(infoFileDTO.getOrganizecode()==null||infoFileDTO.getOrganizecode().equals("")){
            }else {
                info.setOrganizecode(infoFileDTO.getOrganizecode());
            }
            info.setAttorneyuuid(infoFileDTO.getUuid());
        }else if(type.equals(CBPConstant.FileEnum.BUSINLICENSE.getType())){
            info.setOrganizecode(infoFileDTO.getOrganizecode());
            info.setBusilicenseuuid(infoFileDTO.getUuid());
        }
        if(infoFileDTO.getBuyeridcardnum()!=null&&getCustomer.getIdcardnum()!=null&&getCustomer.getIdcardnum().equals(infoFileDTO.getBuyeridcardnum())){
           info.setBuyeridcardnum(null);
        }else {
            info.setBuyeridcardnum(infoFileDTO.getBuyeridcardnum());
        }
        info.setOrganizecode(infoFileDTO.getOrganizecode());
        info.setCustomerid(infoFileDTO.getCustomerid());

        int i=customerInfoService.updateCustomerInfoUUID(info);
        if(i==1){
            return CBPConstant.CODE_SUCCESS;
        }
        return CBPConstant.CODE_FAILURE;
    }
   

   
   @RequestMapping("/getCustomerInfoUUID")
   public String getCustomerInfoUUID(@RequestBody DocumentQueryModel documentQueryModel){
	  String uuid =null;
	  if(documentQueryModel!=null){
		 uuid = customerInfoService.selectCustomerInfoUUID(documentQueryModel);
	  }
	   return uuid;
   }
   
   @RequestMapping("/getCustomerInfoList")
   public Page getCustomerInfoList(@RequestBody CustomerInfoQueryDTO customerInfoQueryDTO){
	  if(customerInfoQueryDTO!=null&&!StringUtil.isNullorEmpty(customerInfoQueryDTO.getEmployeeId())
	  		&&!StringUtil.isNullorEmpty(customerInfoQueryDTO.getAccountType())){
		 return customerInfoService.getCustomerInfoList(customerInfoQueryDTO);
	  }
	  return null;
   }
}
