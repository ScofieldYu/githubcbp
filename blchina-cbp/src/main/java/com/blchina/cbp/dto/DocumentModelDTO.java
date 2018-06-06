/*   
 * @(#)DocumentModel.java       2017年12月4日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

/** 
 * 接受第三方 查询文档模板    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class DocumentModelDTO {
   
   private String receiptId;//收据ID
   
   private String orderId;//订单ID
   
   private String contractId;//合同ID
   
   private String buyerIdCardNum;//买家身份证
   
   private String organizeCode;//机构代码
   
   private String customerId;//联系人ID
   
   private String documentType;//查询文档类型
   
   private String depositId;//垫付证明ID
   
   private String suffix;//文件后缀

   private String uuid;
   
   public String getUuid() {
      return uuid;
   }

   public void setUuid(String uuid) {
      this.uuid = uuid;
   }

   public String getSuffix() {
      return suffix;
   }

   public void setSuffix(String suffix) {
      this.suffix = suffix;
   }

   public String getDepositId() {
      return depositId;
   }

   public void setDepositId(String depositId) {
      this.depositId = depositId;
   }

   public String getReceiptId() {
      return receiptId;
   }

   public void setReceiptId(String receiptId) {
      this.receiptId = receiptId;
   }

   public String getOrderId() {
      return orderId;
   }

   public void setOrderId(String orderId) {
      this.orderId = orderId;
   }

   public String getContractId() {
      return contractId;
   }

   public void setContractId(String contractId) {
      this.contractId = contractId;
   }

   public String getBuyerIdCardNum() {
      return buyerIdCardNum;
   }

   public void setBuyerIdCardNum(String buyerIdCardNum) {
      this.buyerIdCardNum = buyerIdCardNum;
   }

   public String getOrganizeCode() {
      return organizeCode;
   }

   public void setOrganizeCode(String organizeCode) {
      this.organizeCode = organizeCode;
   }

   public String getCustomerId() {
      return customerId;
   }

   public void setCustomerId(String customerId) {
      this.customerId = customerId;
   }

   public String getDocumentType() {
      return documentType;
   }

   public void setDocumentType(String documentType) {
      this.documentType = documentType;
   }
   
   
}
