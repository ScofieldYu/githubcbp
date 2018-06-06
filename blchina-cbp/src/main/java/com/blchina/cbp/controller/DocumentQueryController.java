/*   
 * @(#)DocumentQueryController.java       2017年12月1日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.blchina.cbp.model.CBPContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.DocumentQueryModel;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.DocumentModelDTO;
import com.blchina.cbp.dto.FileAddressDTO;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.model.CBPOrderFile;
import com.blchina.cbp.model.CBPOutpocket;
import com.blchina.cbp.service.interfaces.ConstractService;
import com.blchina.cbp.service.interfaces.ContractItemService;
import com.blchina.cbp.service.interfaces.OrderFileService;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.cbp.service.interfaces.OutpocketService;
import com.blchina.cbp.service.interfaces.ReceiptService;
import com.blchina.common.util.encode.EncodeUtil;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;
import com.fadada.sdk.client.FddClientExtra;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;

import net.sf.json.JSONObject;

/** 
 * 第三方查询文档操作controller    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/documentQuery")
public class DocumentQueryController {

   @Autowired
   private ReceiptService receiptService;
   @Autowired
   private ContractItemService contractItemService;
   @Autowired
   private ConstractService constractService;
   @Autowired
   private OrderService orderService;
   @Autowired
   private OutpocketService outpocketService;
   @Autowired
   private OrderFileService orderFileService;
   @Autowired
   protected Properties systemConfig;
   /**
    * 第三方查询文档
    * @param 
    * @return
    */
   @RequestMapping("/getDocumentQuery")
   public WeixinResult getDocumentQuery(@RequestBody DocumentModelDTO documentModelDTO){
	  WeixinResult res = new WeixinResult();
      res.setCode(CBPConstant.CODE_NULL_PARAM);
      res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  String downloadUrl="";
	  List<String> urlList = new ArrayList<String>();
	  //查询收据
	  if(documentModelDTO!=null){
		 if(CBPConstant.FileEnum.RECEIPT.getType().equals(documentModelDTO.getDocumentType())){
			String orderId = documentModelDTO.getOrderId();
			List<String> uuidlist = receiptService.selectReceipt(orderId);
			for (String uuid1 : uuidlist) {
			    downloadUrl = getUrl(uuid1);
			    if(!StringUtil.isNullorEmpty(downloadUrl)){
			       urlList.add(downloadUrl);
			    }
			}
		 }else if(CBPConstant.FileEnum.CONTRACT.getType().equals(documentModelDTO.getDocumentType())){
			//查询合同  orderId 获取contractId
			String orderId = documentModelDTO.getOrderId();
			String contract_id = constractService.selectContract(orderId);
			String app_id=systemConfig.getProperty("fadada.appid");//接入方  ID
	        String secret=systemConfig.getProperty("fadada.secret");
			String url = systemConfig.getProperty("fadada.documentQuery");
	        FddClientExtra clientextra = new FddClientExtra(app_id, secret, "2.0", url);
			String view_url = clientextra.invokeViewPdfURL(contract_id);
			if(!StringUtil.isNullorEmpty(view_url)){
		       urlList.add(view_url);
		    }
		 }else if(CBPConstant.FileEnum.ADVANCEDPAID.getType().equals(documentModelDTO.getDocumentType())){
			//查询垫付证明
			String orderId = documentModelDTO.getOrderId();
			List<CBPOutpocket>  outpocketList = outpocketService.selectOutpocket(orderId);
			if(!outpocketList.isEmpty()){
			   for(CBPOutpocket outpocket: outpocketList){
				  String url = getUrl(outpocket.getOutpocketuuid());
				  if(!StringUtil.isNullorEmpty(url)){
				       urlList.add(url);
				    }
			   }
			}
		 }else if(CBPConstant.FileEnum.CUSTOMERIDCARD.getType().equals(documentModelDTO.getDocumentType())
			   		|| CBPConstant.FileEnum.BUYERIDCARD.getType().equals(documentModelDTO.getDocumentType())
			   		   || CBPConstant.FileEnum.BUSINLICENSE.getType().equals(documentModelDTO.getDocumentType())
			   		   	  || CBPConstant.FileEnum.ATTORNEY.getType().equals(documentModelDTO.getDocumentType())){
			// 1--客户身份证,2--买方身份证，      3--营业执照，4--委托书
			//去customer项目 查CustomerInfo 需传customerId organizeCode buyerIdCardNum documentType（文档类型）
			//根据orderId查customerId   organizeCode 或 buyerIdCardNum
			try {
				String getUUID = documentModelDTO.getUuid();
				if(StringUtil.isNullorEmpty(getUUID)){
					String orderId = documentModelDTO.getOrderId();
					String documentType = documentModelDTO.getDocumentType();
					CBPOrderFile cbpOrderFile = orderFileService.getFileByOrderId(Integer.valueOf(orderId));
					if(cbpOrderFile==null){
						res.setCode(CBPConstant.CODE_SUCCESS);
						res.setMessage(CBPConstant.MESSAGE_SUCCESS);
						res.setData(new ArrayList());
						return  res;
					}
					if(CBPConstant.FileEnum.CUSTOMERIDCARD.getType().equals(documentType)){
						getUUID = cbpOrderFile.getCustomercarduuid();
					}else if(CBPConstant.FileEnum.BUYERIDCARD.getType().equals(documentType)){
						getUUID = cbpOrderFile.getBuyeridcarduuid();
					}else if(CBPConstant.FileEnum.BUSINLICENSE.getType().equals(documentType)){
						getUUID = cbpOrderFile.getBusilicenseuuid();
					}else if(CBPConstant.FileEnum.ATTORNEY.getType().equals(documentType)){
						getUUID = cbpOrderFile.getAttorneyuuid();
					}else{
						getUUID = "";
					}
				}

			   if(!StringUtil.isNullorEmpty(getUUID)){
				  String url = getUrl(getUUID);
				  if(!StringUtil.isNullorEmpty(url)){
				       urlList.add(url);
				    }
			   }else{
				   res.setCode(CBPConstant.CODE_SUCCESS);
				   res.setMessage(CBPConstant.MESSAGE_SUCCESS);
				   res.setData(new ArrayList());
					return  res;
				 }
			}
			catch (Exception e) {
			   e.printStackTrace();
			}
		 }else{
			return  res;
		 }
		 	res.setCode(CBPConstant.CODE_SUCCESS);
			res.setMessage(CBPConstant.MESSAGE_SUCCESS);
			if(urlList.isEmpty()){
			   res.setData(new ArrayList());
			}else{
			   res.setData(urlList);
			}
			return  res;
	  }
	  	   return  res;
   }

	/**
	 * 第三方查询文档
	 * @param
	 * @return
	 */
	@RequestMapping("/getDocumentQueryBase")
	public WeixinResult getDocumentQueryBase(@RequestBody DocumentModelDTO documentModelDTO){
		WeixinResult res = new WeixinResult();
		res.setCode(CBPConstant.CODE_NULL_PARAM);
		res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	     if(!StringUtil.isNullorEmpty(documentModelDTO.getUuid())){
			 String url = getUrl(documentModelDTO.getUuid());
			 res.setCode(CBPConstant.CODE_SUCCESS);
			 res.setMessage(CBPConstant.MESSAGE_SUCCESS);
			 res.setData(url);
		 }
		return  res;
	}
   
   @RequestMapping("/getConstractByUUID")
   public  WeixinResult getConstractByUUID(@RequestBody DocumentModelDTO documentModelDTO){
	   WeixinResult res=new WeixinResult();
	   String contractId = documentModelDTO.getContractId();
	   if(StringUtil.isNullorEmpty(contractId)){
		   res.setCode(CBPConstant.CODE_NULL_PARAM);
		   res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
		   return res;
	   }
	   String app_id=systemConfig.getProperty("fadada.appid");//接入方  ID
	   String secret=systemConfig.getProperty("fadada.secret");
	   String url = systemConfig.getProperty("fadada.documentQuery");
	   FddClientExtra clientextra = new FddClientExtra(app_id, secret, "2.0", url);
	   String geturl = clientextra.invokeViewPdfURL(contractId);
	/*   CBPContract constractById = constractService.findConstractById(contractId);
	   if(constractById==null){
		   res.setCode(CBPConstant.CODE_NULL_PARAM);
		   res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
		   return res;
	   }
	   String contractUUID = constractById.getContractUUID();
	   if(StringUtil.isNullorEmpty(contractUUID)){
		   res.setCode(CBPConstant.CODE_NULL_PARAM);
		   res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
		   return res;
	   }
	   String url = getUrl(contractUUID);*/
	   res.setCode(CBPConstant.CODE_SUCCESS);
	   res.setMessage(CBPConstant.MESSAGE_SUCCESS);
	   res.setData(geturl);
	   return res;
   }

   public String getUrl(String uuid){
	  String downloadUrl = "";
	  try {
			String timeStamp = HttpsUtil.getTimeStamp();
			String sign = FddEncryptTool.sha1(EncodeUtil.getMD5(
				  systemConfig.getProperty("fadada.appSecret").getBytes()) + timeStamp);
			FileAddressDTO fileAddressDTO = new FileAddressDTO();
			fileAddressDTO.setUuid(uuid);
			fileAddressDTO.setSign(sign);
			//fileAddressDTO.setSuffix(".pdf");
			fileAddressDTO.setTimestamp(timeStamp);
			JSONObject getJSON = JSONObject.fromObject(fileAddressDTO);
			String getDocument = getJSON.toString();
			String response = HttpUtil
				  .postbody(systemConfig.getProperty("contract.url")+"upload/getFileViewUrl.do", getDocument);
			System.out.println(response);
			JSONObject getObject = JSONObject.fromObject(response);
			boolean isSuccess = (boolean) getObject.get("isSuccess");
			if(isSuccess){
			  Object data= getObject.get("data");
				JSONObject getData=JSONObject.fromObject(data);
				return  (String) getData.get("viewUrl");
			}
		 }
		 catch (Exception e) {
			e.printStackTrace();
		 }
	  return downloadUrl;
   }
   
   
}
