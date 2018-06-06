/*   
 * @(#)CheckCarNumOrderDTO.java       2018年3月8日  
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
public class CheckCarNumOrderDTO {
   
   private Integer checkcarnumid;

   private Integer orderid;

   private String buytax;

   private String buytaxsize;
   
   private String buytaxvalue;

   private String tempplate;

   private String tempplatesize;

   private String tempplatevalue;

   private String checkcar;

   private String checkcarsize;

   private String checkcarvalue;

   private String reservecheckcartime;

   private Integer tempplatecount;

   private String status;
   
   private String choosetype; 

   public String getChoosetype() {
      return choosetype;
   }

   public void setChoosetype(String choosetype) {
      this.choosetype = choosetype;
   }

   public Integer getCheckcarnumid() {
      return checkcarnumid;
   }

   public void setCheckcarnumid(Integer checkcarnumid) {
      this.checkcarnumid = checkcarnumid;
   }

   public Integer getOrderid() {
      return orderid;
   }

   public void setOrderid(Integer orderid) {
      this.orderid = orderid;
   }

   public String getBuytax() {
      return buytax;
   }

   public void setBuytax(String buytax) {
      this.buytax = buytax;
   }

   public String getBuytaxsize() {
      return buytaxsize;
   }

   public void setBuytaxsize(String buytaxsize) {
      this.buytaxsize = buytaxsize;
   }

   public String getBuytaxvalue() {
      return buytaxvalue;
   }

   public void setBuytaxvalue(String buytaxvalue) {
      this.buytaxvalue = buytaxvalue;
   }

   public String getTempplate() {
      return tempplate;
   }

   public void setTempplate(String tempplate) {
      this.tempplate = tempplate;
   }

   public String getTempplatesize() {
      return tempplatesize;
   }

   public void setTempplatesize(String tempplatesize) {
      this.tempplatesize = tempplatesize;
   }

   public String getTempplatevalue() {
      return tempplatevalue;
   }

   public void setTempplatevalue(String tempplatevalue) {
      this.tempplatevalue = tempplatevalue;
   }

   public String getCheckcar() {
      return checkcar;
   }

   public void setCheckcar(String checkcar) {
      this.checkcar = checkcar;
   }

   public String getCheckcarsize() {
      return checkcarsize;
   }

   public void setCheckcarsize(String checkcarsize) {
      this.checkcarsize = checkcarsize;
   }

   public String getCheckcarvalue() {
      return checkcarvalue;
   }

   public void setCheckcarvalue(String checkcarvalue) {
      this.checkcarvalue = checkcarvalue;
   }

   public String getReservecheckcartime() {
      return reservecheckcartime;
   }

   public void setReservecheckcartime(String reservecheckcartime) {
      this.reservecheckcartime = reservecheckcartime;
   }

   public Integer getTempplatecount() {
      return tempplatecount;
   }

   public void setTempplatecount(Integer tempplatecount) {
      this.tempplatecount = tempplatecount;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }
   
}
