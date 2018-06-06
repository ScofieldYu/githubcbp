/*   
 * @(#)ReceiptServiceImpl.java       2017年11月28日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.service.impl;
import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blchina.cbp.dao.CBPOrderMapper;
import com.blchina.cbp.dao.CBPReceiptMapper;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.sap.SAPResponse;
import com.blchina.cbp.datamodel.sap.SAPResult;
import com.blchina.cbp.dto.FileListDTO;
import com.blchina.cbp.dto.ReceiptDTO;
import com.blchina.cbp.dto.UploadDTO;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.model.CBPReceipt;
import com.blchina.cbp.service.interfaces.ReceiptService;
import com.blchina.cbp.util.UploadUtil;
import com.blchina.common.util.file.FileUtil;
import com.blchina.common.util.http.HttpUtil;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import com.fadada.sdk.util.http.HttpsUtil;

import net.sf.json.JSONObject;
/** 
 * 收据操作ReceiptServiceImpl    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@Service("ReceiptService")
public class ReceiptServiceImpl implements ReceiptService {
   
   @Autowired
   private CBPReceiptMapper cbpReceiptMapper;
   @Autowired
   private CBPOrderMapper cbpOrderMapper;
   @Autowired
   protected Properties systemConfig;
   @Override
   public int insertOrUpdateReceipt(CBPReceipt cbpReceipt) {
	  int status = 0;
		 status = cbpReceiptMapper.insert(cbpReceipt);
	  return status;
   }

   @Override
   public List<String> selectReceipt(String orderId) {
	  String receiptUUID = "";
	  List<String> uuidList = new ArrayList<String>();
	  if(orderId!=null){
		 List<CBPReceipt> list = cbpReceiptMapper.selectReceipt(Integer.parseInt(orderId));
		 for (CBPReceipt cbpReceipt2 : list) {
			uuidList.add(cbpReceipt2.getReceiptuuid());
		 }
	  }
	  return uuidList;
   }

	@Override
	public int saveReceipt(CBPReceipt cbpReceipt) {
		return cbpReceiptMapper.insert(cbpReceipt);
	}
   @Override
   public String uploadReceipt(ReceiptDTO.Receipt receipt) {
	  String titleId = null;
	  String storeId = null;
	  UploadDTO uploadDTO = new UploadDTO();
	  File receiptfile = FileUtil.byte2File(FileUtil.hexStr2BinArr(receipt.getReceiptfile()), systemConfig.getProperty("file.url.pdf")+ new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date())+".pdf", "收据下发" + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
	  CBPOrder cbpOrder = cbpOrderMapper.getOrderBySapId(receipt.getSaporderrid());
	  uploadDTO.setOrderid(cbpOrder.getOrderid());
	  uploadDTO.setIdcardnum(cbpOrder.getIdcardnum());//联系人身份证号码
	  uploadDTO.setType(CBPConstant.FileEnum.RECEIPT.getType());
	  uploadDTO.setEmployeeid(cbpOrder.getEmployeeid());
	   uploadDTO.setSapOrderId(cbpOrder.getSaporderid());
      List<File> list = new ArrayList<File>();
	  list.add(receiptfile);
      try {
    	 if(uploadDTO.getEmployeeid()!=null){
    		titleId = HttpUtil.postbody(systemConfig.getProperty("url.employee")+"/ee/getEmployeeTitleId", "{\"employeeid\":" + uploadDTO.getEmployeeid() + "}");
    		storeId = cbpOrder.getBrandid();
    	 }
		  List<String> urllist=new ArrayList<String>();
    	 String result = UploadUtil.uploadByFile(systemConfig.getProperty("contract.url")+"upload/fileUpload.do",list,urllist,uploadDTO,titleId,storeId,systemConfig.getProperty("fadada.appSecret"));
         JSONObject getJson=JSONObject.fromObject(result);
         getJson.remove("data");
         FileListDTO fileuuidList = (FileListDTO)JSONObject.toBean(getJson, FileListDTO.class);
         if(String.valueOf(fileuuidList.getCode()).equals("200")){
        	return fileuuidList.getObj()[0].getUuid();
         }
          return null;
      } catch (Exception var11) {
          var11.printStackTrace();
          throw new RuntimeException(var11);
      }
  
	  }
}
