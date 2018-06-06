/*   
 * @(#)ContractController.java       2017年11月29日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blchina.cbp.model.CBPLogistics;
import com.blchina.cbp.service.interfaces.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.sap.SAPResponse;
import com.blchina.cbp.datamodel.sap.SAPResult;
import com.blchina.cbp.datamodel.signature.NotificationResult;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.ContractDTO;
import com.blchina.cbp.dto.FileListDTO;
import com.blchina.cbp.dto.UploadDTO;
import com.blchina.cbp.model.CBPContract;
import com.blchina.cbp.model.CBPContractItem;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.util.UploadUtil;
import com.blchina.common.util.file.FileUtil;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;
import com.fadada.sdk.client.FddClientBase;
import com.fadada.sdk.util.crypt.FddEncryptTool;

import net.sf.json.JSONObject;

/** 
 * 文档传输接口  
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/contractSign")
public class ContractSignController {
   
   @Autowired
   protected Properties systemConfig;
   @Autowired
   protected ConstractService contractService;
   @Autowired
   protected ContractItemService contractItemService;
   @Autowired
   private OrderService orderService;
	@Autowired
	private LogisticsService logisticsService;
	@Autowired
	private WxService wxService;
   private static Logger log = Logger.getLogger(PayController.class);
   /**
    * 从CBP获取合同 调文档传输接口传给法大大
    * @param 
    * @return
    */
   @RequestMapping("/getContract")
   public SAPResult getContract(@RequestBody CBPContract cbpContract) {
	   SAPResult sapResult = new SAPResult();
	   SAPResponse record = new SAPResponse();
	   record.setBL_ID("22222");
   	   record.setSAP_ID("22222");
   	   record.setStatus("E");
   	   record.setDescription("失败");
       Date date=new Date();
       SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
       String time=sdf.format(date);
       //去法大大认证
       try {
           String app_id=systemConfig.getProperty("fadada.appid");//接入方  ID
           String secret=systemConfig.getProperty("fadada.secret");
           //Integer contract_id = cbpContract.getContractid();//合同编号
           String doc_title = "测试合同";//合同标题
           String doc_url="http://bdl-cbp.blchina.com/cbp/contract.pdf";
           //File file=new File("E:\\contract.pdf");
           String doc_type = ".pdf";//文档类型
           //String url = "http://49.4.8.8:8888/api/";  //systemConfig.getProperty("fadada.documentTransmission");
           String url = "http://192.168.101.64:8888/api/";  //systemConfig.getProperty("fadada.documentTransmission");
           //String url = "http://10.106.101.222:8888/api/uploaddocs.api";  //systemConfig.getProperty("fadada.documentTransmission");
           String contract_id = "22222";
           FddClientBase clientbase = new FddClientBase(app_id, secret, "2.0", url);
           String response = clientbase.invokeUploadDocs(contract_id,doc_title,null,doc_url,doc_type);
           System.out.println(response);
           JSONObject  getJSON=JSONObject.fromObject(response);
           String result=(String) getJSON.get("result");
           if(result.equals("success")){
           record.setBL_ID("111111");
       	   record.setSAP_ID("111111");
       	   record.setStatus("S");
       	   record.setDescription("成功");       	  	
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       		sapResult.setRecords(record);
			return sapResult;
   }
   
   
   /**
    * 客户调用签署接口（手动签模式）
    * @param 
    * @return
    */
   @RequestMapping("/getDocument")
   public WeixinResult getDocument(@RequestBody ContractDTO contractDTO,
		 HttpServletResponse response){
	  	WeixinResult res = new WeixinResult();
	  	res.setCode(CBPConstant.CODE_NULL_PARAM);
	  	res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if(contractDTO!=null){
		 String app_id=systemConfig.getProperty("fadada.appid");//接入方  ID
		 String secret=systemConfig.getProperty("fadada.secret");
		 String contract_id = contractDTO.getContractId()+"";
		 //customer_id（认证过的） 需根据contract_id来获取
		 CBPOrder cbpOrder = contractService.selectCustomerIdByContractId(contract_id);
		 //判断合同是否需要撤销
		 boolean flag = contractService.cancelContract(cbpOrder.getOrderid());
		 if(flag){
			System.out.println("撤销合同成功");
		 }
		 Integer orderid = null;
		 Integer customerId = null;
		 String customer_id=null;
		 if(cbpOrder!=null){
			orderid = cbpOrder.getOrderid();
			customerId = cbpOrder.getCustomerid();
		 }
		 try {
	            if(cbpOrder.getOrganizecode()==null||cbpOrder.getOrganizecode().equals("")){
	               customer_id = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getsignCustomerId", "{\"customerid\":" + customerId +"}");
	            }else {
	               customer_id = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getsignCustomerId", "{\"customerid\":" + customerId +",\"organizecode\":\""+cbpOrder.getOrganizecode()+ "\"}");
	            }
	            if(StringUtil.isNullorEmpty(customer_id)){
	               	res.setCode(CBPConstant.CODE_CA_SELF_FALSE);
	      		 	res.setMessage(CBPConstant.MESSAGE_CA_SELF_FALSE);
	      		 	return  res;
	            }
			}
			catch (Exception e1) {
			  e1.printStackTrace();
			}
		 String doc_title = contractDTO.getDocTitle()+"";
		 String transaction_id = contract_id;
		 String notify_url = systemConfig.getProperty("url.Manual");//签署结果异步通知url
		 String url = systemConfig.getProperty("fadada.contractManual");
		 FddClientBase clientbase = new FddClientBase(app_id, secret, "2.0", url);
		 System.out.println("transaction_id = "+transaction_id+"---customer_id = "+customer_id+"---contract_id = "+contract_id+"---doc_title = "+doc_title+"---notify_url = "+notify_url);
		 log.info("transaction_id = "+transaction_id+"---customer_id = "+customer_id+"---contract_id = "+contract_id+"---doc_title = "+doc_title+"---notify_url = "+notify_url);
		 String  sign_url  =  clientbase.invokeExtSign(transaction_id,   
			   customer_id,  contract_id, doc_title, "客户签章处", systemConfig.get("signReturn.url")+"/signResult.html", notify_url);
		 System.out.println(sign_url);
		 log.info(sign_url);
		 res.setCode(CBPConstant.CODE_SUCCESS);
		 res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		 res.setData(sign_url);
		 return  res;
	  }
	  return res;
	  
   }
   
   /**
    * 处理法大大回调notify_url(客戶手动签)
    * @param request
    * @return
    */
   @RequestMapping("/fddCallbackManual")
   public void fddCallbackManual(HttpServletResponse response,HttpServletRequest request) {
	  Date date1=new Date();
      SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMddHHmmss");
      String time1=sdf1.format(date1);
	  System.out.println("-----异步请求开始时间----------"+time1);
	   System.out.print("手动签署通知");
		 String titleId = null;
		 String transaction_id =request.getParameter("transaction_id");
		 String contract_id =request.getParameter("contract_id");
		 String result_code =request.getParameter("result_code");
		 String result_desc =request.getParameter("result_desc");
		 String download_url =request.getParameter("download_url");
		 String viewpdf_url =request.getParameter("viewpdf_url");
		 String msg_digest_return =request.getParameter("msg_digest");
		 String timestamp =request.getParameter("timestamp");
		 System.out.print("下载地址   ："+download_url);
		 System.out.println("返回参数   ："+"transaction_id: "+transaction_id+ " contract_id: "+contract_id+" result_code:"+result_code
               +" result_desc:"+result_desc+" download_url:"+download_url+" viewpdf_url:"+viewpdf_url+" msg_digest_return："+msg_digest_return+" timestamp:"+timestamp); 		 
		 String app_id = systemConfig.getProperty("fadada.appid");
		 String secret = systemConfig.getProperty("fadada.secret");
		 try {
			//加密后判断msg_digest
			String sha1 = FddEncryptTool
				  .sha1(app_id + FddEncryptTool.md5Digest(timestamp)
						+ FddEncryptTool.sha1(secret + transaction_id));
			String msg_digest = new String(
				  FddEncryptTool.Base64Encode(sha1.getBytes()));
			if (msg_digest.equals(msg_digest_return) && CBPConstant.SIGN_SUCCESS.equals(result_code)) {
			   //根据download_url上传合同到合同管理系统返回UUID
			   System.out.println("根据download_url上传合同到合同管理系统返回UUID");
				CBPContract  getConstract=contractService.findConstractById(contract_id);
				CBPContractItem contractItem = contractItemService.selectContractItemByContractIdAndContractStatus(contract_id);
				if(contractItem!=null){
				   return;
				}
			     CBPOrder cbpOrder = orderService.getOrderById(getConstract.getOrderid());
			   	  System.out.println("cbpOrder Employeeid = "+cbpOrder.getEmployeeid());
			     UploadDTO uploadDTO = new UploadDTO();
				  uploadDTO.setOrderid(cbpOrder.getOrderid());
				  uploadDTO.setIdcardnum(cbpOrder.getIdcardnum());//联系人身份证号码
				  uploadDTO.setType(CBPConstant.FileEnum.CONTRACT.getType());
				  uploadDTO.setEmployeeid(cbpOrder.getEmployeeid());
				  uploadDTO.setContractid(Integer.valueOf(contract_id));
				  uploadDTO.setSapOrderId(cbpOrder.getSaporderid());
				  try {
			    	 if(uploadDTO.getEmployeeid()!=null){
			    		titleId = HttpUtil.postbody(systemConfig.getProperty("url.employee")+ "/ee/getEmployeeTitleId", "{\"employeeid\":" + uploadDTO.getEmployeeid() + "}");
			    		System.out.println("title = "+titleId);
			    	 }
			    	 String respone = UploadUtil.uploadByUrl(systemConfig.getProperty("contract.url") + "upload/fileUpload.do", download_url, uploadDTO, titleId, cbpOrder.getBrandid(),systemConfig.getProperty("fadada.appSecret"),"1");
			    	 System.out.print("上传合同管理系统返回"+respone);
			    	 JSONObject getJson=JSONObject.fromObject(respone);
			    	 System.out.print("返回JSONObject = "+getJson);
			         if(String.valueOf(getJson.get("code")).equals("200")){
			        	 getJson.remove("data");
		                 FileListDTO fileuuidList = (FileListDTO)JSONObject.toBean(getJson, FileListDTO.class);
			        	 String uuid = fileuuidList.getObj()[0].getUuid();
			        	 System.out.println("合同管理系统返回"+uuid);
			        	 CBPContractItem cbpContractItem = new CBPContractItem();
			        	 Date date=new Date();
			        	 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			        	 String time=sdf.format(date);
			        	 cbpContractItem.setContractid(Integer.valueOf(contract_id));
			        	 cbpContractItem.setContractuuid(uuid);
			        	 cbpContractItem.setContractresult(result_code);
			        	 cbpContractItem.setRequesttime(time);
			        	 cbpContractItem.setContractdes(result_desc);
			        	 System.out.println("+++++++++++修改状态++++++++++++");
			        	 CBPContract contract = contractService.findConstractById(contract_id);
			        	 if((CBPConstant.ContractStatusEnum.FINISH.getType()+"").equals(contract.getContractstatus())){
			        		cbpContractItem.setContractstatus(CBPConstant.ContractStatusEnum.FINISH.getType()+"");
			        	 }else{
			        		if(CBPConstant.SIGN_SUCCESS.equals(result_code)){
			        		   cbpContractItem.setContractstatus(CBPConstant.ContractStatusEnum.SIGNED.getType()+"");
			        		}else{
			        		   cbpContractItem.setContractstatus(CBPConstant.ContractStatusEnum.WAITINSIGN.getType()+"");
			        		}
			        	 }
			        	 int status = contractService.updateConstractAndContractItem(cbpContractItem);
						try{
							JSONObject jsonObject=new JSONObject();
							jsonObject.put("customerid",cbpOrder.getCustomerid());
							jsonObject.put("brandid",cbpOrder.getBrandid());
							String   getcus = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerNameAndOpenid",jsonObject.toString());
							String[] split = getcus.split(",");
							String customername = split[0]+",";
							String openid=split[1];
							customername=customername+systemConfig.getProperty("logistics.message.import.confirmed");
							Boolean flag=wxService.pushLogisticsStart(
									cbpOrder.getBrandid(), cbpOrder.getSaporderid(), openid,
									customername, cbpOrder.getOrderid(),
									cbpOrder.getCartype(), "订单已确认");
							if(flag){
								CBPLogistics record=new CBPLogistics();
								record.setSequence("0000");
								record.setOrderid(cbpOrder.getOrderid());
								record.setSenddate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
								record.setStatus("订单已确认");
								record.setSendStatus("1");
								//物流信息
								logisticsService.insertOrUpdateLogistic(record);
							}
						}catch (Exception e){
							log.info("客户没有openid");
							CBPLogistics record=new CBPLogistics();
							record.setSequence("0000");
							record.setOrderid(cbpOrder.getOrderid());
							record.setSenddate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
							record.setStatus("订单已确认");
							record.setSendStatus("0");
							//物流信息
							logisticsService.insertOrUpdateLogistic(record);
						}
						  Date date2=new Date();
					      SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMddHHmmss");
					      String time2=sdf2.format(date2);
						  System.out.println("---异步开始时间"+time1+"----异步结束时间--------"+time2);
			        	 response.setStatus(HttpServletResponse.SC_OK);
			         }else{
						   response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
						}
			      } catch (Exception var11) {
			    	 log.info(var11.getMessage().toString());
			          var11.printStackTrace();
			          response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
			          throw new RuntimeException(var11);
			      }
			}else{
			   response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
			}
		 }
		 catch (Exception e) {
			log.info(e.getMessage().toString());
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
		 }
   }
   /**
    * 处理法大大回调notify_url(公司自动签)
    * @param notificationResult
    * @return
    */
   @RequestMapping("/fddCallbackAutomatic")
   public void fddCallbackAutomatic(
		 @RequestBody NotificationResult notificationResult,HttpServletResponse response) {
	  if (notificationResult != null) {
		 String transaction_id = notificationResult.getTransaction_id();
		 String timestamp = notificationResult.getTimestamp();
		 String contract_id = notificationResult.getContract_id();
		 String result_code = notificationResult.getResult_code();
		 String result_desc = notificationResult.getResult_desc();
		 String download_url = notificationResult.getDownload_url();
		 String viewpdf_url = notificationResult.getViewpdf_url();
		 String msg_digest_return = notificationResult.getMsg_digest();
		 String app_id = systemConfig.getProperty("fadada.appid");
		 String secret = systemConfig.getProperty("fadada.secret");
		 CBPContractItem cbpContractItem = new CBPContractItem();
		 try {
			//加密后判断msg_digest
			String sha1 = FddEncryptTool
				  .sha1(app_id + FddEncryptTool.md5Digest(timestamp)
						+ FddEncryptTool.sha1(secret + transaction_id));
			String msg_digest = new String(
				  FddEncryptTool.Base64Encode(sha1.getBytes()));
			if (msg_digest.equals(msg_digest_return)) {
			   //根据download_url上传合同到合同管理系统返回UUID
			   
			   //成功修改CBPContractItem和CBPContract状态
			   Date date=new Date();
			   SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			   String time=sdf.format(date);
			   cbpContractItem.setContractid(Integer.parseInt(contract_id));
			   //cbpContractItem.setContractuuid("111111111111111");
			   cbpContractItem.setContractresult(result_code);
			   cbpContractItem.setRequesttime(time);
			   cbpContractItem.setContractdes(result_desc);
			   if(CBPConstant.SIGN_SUCCESS.equals(result_code)){
				  cbpContractItem.setContractstatus(CBPConstant.ContractStatusEnum.FINISH.getType()+"");
			   }else{
				  cbpContractItem.setContractstatus(CBPConstant.ContractStatusEnum.SIGNED.getType()+"");
			   }
			   int status = contractService.updateConstractAndContractItem(cbpContractItem);
			   response.setStatus(HttpServletResponse.SC_OK);
			}else{
			   response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		 }
		 catch (Exception e) {
			e.printStackTrace();
		 }
	  }else{
		 response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
	  }
   }
   
   /**
    * 处理法大大回调notify_url(公司自动签)
    * @param order
    * @return
    */
   @RequestMapping("/signTemp")
   public WeixinResult signTemplateTest(@RequestBody CBPOrder order) {
	   WeixinResult res = new WeixinResult();
       String saporderId = order.getSaporderid();
       if (saporderId == null) {
           res.setCode(CBPConstant.CODE_NULL_PARAM);
           res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
           return res;
       } else {
    	   order.setOrderstatus("1");
           orderService.updateOrderBySapId(order);
           res.setCode(CBPConstant.CODE_SUCCESS);
           res.setMessage(CBPConstant.MESSAGE_SUCCESS);
           return res;

       }
   }
   
}
