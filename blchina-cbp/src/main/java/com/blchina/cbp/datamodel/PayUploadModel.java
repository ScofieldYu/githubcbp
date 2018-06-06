/*   
 * @(#)PayUploadModel.java       2017年12月20日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** 
 * 支付结果上传模板    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class PayUploadModel<T> {
   @JsonProperty
   private T Records;
   @JsonIgnore
   public T getRecords() {
      return Records;
   }
   @JsonIgnore
   public void setRecords(T records) {
      Records = records;
   }

   
   
}
