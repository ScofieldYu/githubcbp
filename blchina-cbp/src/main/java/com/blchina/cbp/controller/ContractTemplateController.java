/*   
 * @(#)ContractTemplateController.java       2017年11月30日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fadada.sdk.client.FddClientBase;
import com.fadada.sdk.util.crypt.FddEncryptTool;

import net.sf.json.JSONObject;

/** 
 * 合同模板传输操作controller    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/contractTemplate")
public class ContractTemplateController {
   
   @Autowired
   protected Properties systemConfig;
   
   
   /**
    * 合同模板传输接口传给法大大
    * @param 
    * @return
    */
   @RequestMapping("/getContractTemplate")
   public void getContractTemplate(){
	 try {
		 Date date=new Date();
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
         String app_id=systemConfig.getProperty("fadada.appid");
         String secret=systemConfig.getProperty("fadada.secret");
         String time=sdf.format(date);
         //String doc_url="http://bdl-cbp.blchina.com/cbp/contract.pdf";
         File file=new File("E:\\contract.pdf");
         String template_id = "123456";
         String url = "http://49.4.8.8:8888/api/uploadtemplate.api";  //systemConfig.getProperty("fadada.documentTransmission");
         FddClientBase clientbase = new FddClientBase(app_id, secret, "2.0", url);
         String response = clientbase.invokeUploadTemplate(template_id, file, null);
         System.out.println(response);         
         JSONObject  getJSON=JSONObject.fromObject(response);
         String result=(String) getJSON.get("result");
         if(result.equals("success")){
        	System.out.println(getJSON.get("msg"));
         }
     } catch (Exception e) {
         e.printStackTrace();
     }
	  
   }

}
