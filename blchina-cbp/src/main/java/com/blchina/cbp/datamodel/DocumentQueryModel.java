/*   
 * @(#)DocumentQueryModel.java       2017年12月1日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.datamodel;

/** 
 * 客户文档查询模板
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class DocumentQueryModel {
   
   private String customerId;//联系人ID
   
   private String organizeCode;//机构代码
   
   private String buyerIdCardNum;//买家身份证
   
   private String documentType;//查询文档类型

   public String getCustomerId() {
      return customerId;
   }

   public void setCustomerId(String customerId) {
      this.customerId = customerId;
   }

   public String getOrganizeCode() {
      return organizeCode;
   }

   public void setOrganizeCode(String organizeCode) {
      this.organizeCode = organizeCode;
   }

   public String getBuyerIdCardNum() {
      return buyerIdCardNum;
   }

   public void setBuyerIdCardNum(String buyerIdCardNum) {
      this.buyerIdCardNum = buyerIdCardNum;
   }

   public String getDocumentType() {
      return documentType;
   }

   public void setDocumentType(String documentType) {
      this.documentType = documentType;
   }
   
   
}
