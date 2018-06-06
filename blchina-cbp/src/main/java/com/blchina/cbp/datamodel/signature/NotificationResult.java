/*   
 * @(#)NotificationResult.java       2017年11月29日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.datamodel.signature;

/** 
 * 签署结果异步通知    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class NotificationResult {
   
   private String transaction_id;//交易号
   
   private String contract_id;//合同编号
   
   private String result_code;//签章结果代码
   
   private String result_desc;//签章结果描述
   
   private String download_url;//下载地址
   
   private String viewpdf_url;//查看地址
   
   private String timestamp;//请求时间
   
   private String msg_digest;//摘要

   public String getTransaction_id() {
      return transaction_id;
   }

   public void setTransaction_id(String transaction_id) {
      this.transaction_id = transaction_id;
   }

   public String getContract_id() {
      return contract_id;
   }

   public void setContract_id(String contract_id) {
      this.contract_id = contract_id;
   }

   public String getResult_code() {
      return result_code;
   }

   public void setResult_code(String result_code) {
      this.result_code = result_code;
   }

   public String getResult_desc() {
      return result_desc;
   }

   public void setResult_desc(String result_desc) {
      this.result_desc = result_desc;
   }

   public String getDownload_url() {
      return download_url;
   }

   public void setDownload_url(String download_url) {
      this.download_url = download_url;
   }

   public String getViewpdf_url() {
      return viewpdf_url;
   }

   public void setViewpdf_url(String viewpdf_url) {
      this.viewpdf_url = viewpdf_url;
   }

   public String getTimestamp() {
      return timestamp;
   }

   public void setTimestamp(String timestamp) {
      this.timestamp = timestamp;
   }

   public String getMsg_digest() {
      return msg_digest;
   }

   public void setMsg_digest(String msg_digest) {
      this.msg_digest = msg_digest;
   }

}
