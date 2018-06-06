/*   
 * @(#)WXPayDTO.java       2018年1月31日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class WXPayDTO {
   
   private String brandId;
   
   private String orderId;
   
   private String openId;
   
   private String paySum;
   
   private String notifyUrl;
   
   private String uniqueCode;
   
   public String getOpenId() {
      return openId;
   }

   public void setOpenId(String openId) {
      this.openId = openId;
   }

   public String getBrandId() {
      return brandId;
   }

   public void setBrandId(String brandId) {
      this.brandId = brandId;
   }

   public String getUniqueCode() {
      return uniqueCode;
   }

   public void setUniqueCode(String uniqueCode) {
      this.uniqueCode = uniqueCode;
   }

   public String getOrderId() {
      return orderId;
   }

   public void setOrderId(String orderId) {
      this.orderId = orderId;
   }

   public String getPaySum() {
      return paySum;
   }

   public void setPaySum(String paySum) {
      this.paySum = paySum;
   }

   public String getNotifyUrl() {
      return notifyUrl;
   }

   public void setNotifyUrl(String notifyUrl) {
      this.notifyUrl = notifyUrl;
   }
   
   
}
