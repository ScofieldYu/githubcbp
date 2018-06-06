/*   
 * @(#)ContractDTO.java       2017年12月6日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

/** 
 * 合同传入模板（手动签）    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class ContractDTO {
   
   private String contractId;//合同编号
   
   private String docTitle;//文档标题
   
   private String signKeyword;//定位关键字
   
   private String returnUrl;//页面跳转 URL
   
   private String organizeCode;//机构代码
   
   private String buyerIdCardNum;//买家身份证

   public String getContractId() {
      return contractId;
   }

   public void setContractId(String contractId) {
      this.contractId = contractId;
   }

   public String getDocTitle() {
      return docTitle;
   }

   public void setDocTitle(String docTitle) {
      this.docTitle = docTitle;
   }

   public String getSignKeyword() {
      return signKeyword;
   }

   public void setSignKeyword(String signKeyword) {
      this.signKeyword = signKeyword;
   }

   public String getReturnUrl() {
      return returnUrl;
   }

   public void setReturnUrl(String returnUrl) {
      this.returnUrl = returnUrl;
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
   
   
}
