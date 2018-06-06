/*   
 * @(#)ContractGenerateController.java       2017年11月30日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fadada.sdk.client.FddClientBase;
import com.fadada.sdk.util.crypt.FddEncryptTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * 合同生成操作controller    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/contractGenerate")
public class ContractGenerateController {
   
   @Autowired
   protected Properties systemConfig;
   
   
   /**
    * 合同生成接口传给法大大
    * @param 
    * @return
    */
   @RequestMapping("/getContractGenerate")
   public void getContractGenerate(){
	 try {
		 Date date=new Date();
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		 String time=sdf.format(date);
         String app_id=systemConfig.getProperty("fadada.appid");//接入方  ID
         String secret=systemConfig.getProperty("fadada.secret");
         String template_id = "123456";
         String contract_id = "147258";
         String doc_title = "123456";
         String font_size = "";
         String font_type = "";
         String url = "http://49.4.8.8:8888/api/generate_contract.api";  //systemConfig.getProperty("fadada.documentTransmission");
         JSONObject paramter = new JSONObject();
         paramter.put("borrower", "Boss Horse");
         paramter.put("platformName", "The Earth");
         JSONObject dynamic1 = new JSONObject();
         dynamic1.put("pageBegin", 1);
         dynamic1.put("cellHeight", 30);
         dynamic1.put("theFirstHeader", "附一");
         dynamic1.put("headers", new String[] { "序号", "借款人", "贷款人", "金额" });
         String row1[] = new String[] { "1", "小网", "小易", "1000" };
         String row2[] = new String[] { "2", "小云", "小音", "2000" };
         String row3[] = new String[] { "3", "小乐", "天马", "3000" };
         dynamic1.put("datas", new String[][] { row1, row2, row3 });
         dynamic1.put("cellHorizontalAlignment", 0);
         dynamic1.put("cellVerticalAlignment", 5);
         dynamic1.put("colWidthPercent", new int[] { 1, 4, 4, 4 });
         //动态表格(可选)
         JSONArray dynamic_tables = new JSONArray();
         dynamic_tables.add(dynamic1);
         FddClientBase clientbase = new FddClientBase(app_id, secret, "2.0", url);
         String response = clientbase.invokeGenerateContract(template_id,contract_id,doc_title, font_size, font_type, paramter.toString(), null);
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
