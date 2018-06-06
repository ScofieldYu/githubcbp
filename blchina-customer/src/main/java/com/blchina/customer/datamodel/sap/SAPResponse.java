/*   
 * @(#)SAPResponse.java       Nov 3, 2017  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.customer.datamodel.sap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** 
 * 此类功能描述    
 *
 * @author Scofield 
 * @since JDK 1.8
 */
public class SAPResponse {
      @JsonProperty
       private String SAP_ID;  
      @JsonProperty
       private String BL_ID;
      @JsonProperty
       private String Status;
      @JsonProperty
       private String Description;
       
       public SAPResponse(){}
       
       public SAPResponse(String SAP_ID, String BL_ID, String Status, String Description){
       	this.SAP_ID = SAP_ID;
       	this.BL_ID = BL_ID;
       	this.Status = Status;
       	this.Description = Description;
       }
       @JsonIgnore
   	public String getSAP_ID() {
   		return SAP_ID;
   	}
       @JsonIgnore
   	public void setSAP_ID(String sAP_ID) {
   		SAP_ID = sAP_ID;
   	}
       @JsonIgnore
   	public String getBL_ID() {
   		return BL_ID;
   	}
       @JsonIgnore
   	public void setBL_ID(String bL_ID) {
   		BL_ID = bL_ID;
   	}
       @JsonIgnore
   	public String getStatus() {
   		return Status;
   	}
       @JsonIgnore
   	public void setStatus(String status) {
   		Status = status;
   	}
       @JsonIgnore
   	public String getDescription() {
   		return Description;
   	}
       @JsonIgnore
   	public void setDescription(String description) {
   		Description = description;
   	}

}
