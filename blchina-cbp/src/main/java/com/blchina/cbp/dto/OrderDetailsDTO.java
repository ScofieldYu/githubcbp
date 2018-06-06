/*   
 * @(#)OrderDetailsDTO.java       2018年2月1日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.cbp.dto;

import java.util.List;

import com.blchina.cbp.model.*;

/** 
 * 此类功能描述    
 *
 * @author yangyuchao 
 * @since JDK 1.8
 */
public class OrderDetailsDTO {

       private Integer orderid;
   
       private Integer customerid;
    
       private String orderstatus;
    
       private String orderdate;
   
       private String cartype;
    
       private String vinno;
      
       private String saporderid;
    
       private String phonenumber;
       
       private String buyphonenumber;
       
       private String organizecode;
    
       private String idcardnum;
    
       private String delivertime;
    
       private Integer employeeid;
    
       private String buyeridcardnum;
       
       private String brandid;
    
       private String ordertype;
       
       private String realvinno;
    
       private String buyername;
    
       private String customername;
    
       private String totalprice;
       
       private String cartotalprice;
       
       private String organizename;
       
       private String carid;
       
       private String accouttype;
       
       private String financephonenumber;
       
       private String secondhandcarphonenumber;
       
       private CBPReturnCarTime returnCarTime;
       
       private CBPLogistics logistics;
       
       private List<CBPInsuranceOrder> insuranceOrderList;
       
       private List<CBPBoutiqueOrder> boutiqueOrderList;
       
       private List<CBPCheckCarNumOrder> checkCarNumOrderList;
       
       private List<CheckCarNumOrderDTO> checkCarNumOrderDTOList;
       
       private List<CBPSecondHandCarOrder> secondHandCarOrderList;
       
       private List<CBPFourCustomer> fourCustomerList;
       
       private List<CBPFinanceOrder> financeOrderList;
       
       private CBPDeposit cbpDeposit;
       
       private CBPCar car;
       
       public List<CheckCarNumOrderDTO> getCheckCarNumOrderDTOList() {
	     return checkCarNumOrderDTOList;
	  }

	  public void setCheckCarNumOrderDTOList(
	  	  List<CheckCarNumOrderDTO> checkCarNumOrderDTOList) {
	     this.checkCarNumOrderDTOList = checkCarNumOrderDTOList;
	  }

	  public String getSecondhandcarphonenumber() {
	     return secondhandcarphonenumber;
	  }

	  public void setSecondhandcarphonenumber(String secondhandcarphonenumber) {
	     this.secondhandcarphonenumber = secondhandcarphonenumber;
	  }

	  public String getFinancephonenumber() {
	     return financephonenumber;
	  }

	  public void setFinancephonenumber(String financephonenumber) {
	     this.financephonenumber = financephonenumber;
	  }

	  public CBPReturnCarTime getReturnCarTime() {
	     return returnCarTime;
	  }

	  public void setReturnCarTime(CBPReturnCarTime returnCarTime) {
	     this.returnCarTime = returnCarTime;
	  }

	  public CBPLogistics getLogistics() {
	     return logistics;
	  }

	  public void setLogistics(CBPLogistics logistics) {
	     this.logistics = logistics;
	  }

	  public String getAccouttype() {
	     return accouttype;
	  }

	  public void setAccouttype(String accouttype) {
	     this.accouttype = accouttype;
	  }

	  
       
	  public List<CBPFinanceOrder> getFinanceOrderList() {
	     return financeOrderList;
	  }

	  public void setFinanceOrderList(List<CBPFinanceOrder> financeOrderList) {
	     this.financeOrderList = financeOrderList;
	  }

	  public CBPCar getCar() {
	     return car;
	  }

	  public void setCar(CBPCar car) {
	     this.car = car;
	  }

	  public String getBuyphonenumber() {
	     return buyphonenumber;
	  }

	  public void setBuyphonenumber(String buyphonenumber) {
	     this.buyphonenumber = buyphonenumber;
	  }

	  public List<CBPFourCustomer> getFourCustomerList() {
	     return fourCustomerList;
	  }

	  public void setFourCustomerList(List<CBPFourCustomer> fourCustomerList) {
	     this.fourCustomerList = fourCustomerList;
	  }

	  public String getCartotalprice() {
	     return cartotalprice;
	  }

	  public void setCartotalprice(String cartotalprice) {
	     this.cartotalprice = cartotalprice;
	  }

	  public List<CBPSecondHandCarOrder> getSecondHandCarOrderList() {
	     return secondHandCarOrderList;
	  }

	  public void setSecondHandCarOrderList(
	  	  List<CBPSecondHandCarOrder> secondHandCarOrderList) {
	     this.secondHandCarOrderList = secondHandCarOrderList;
	  }

	  public List<CBPCheckCarNumOrder> getCheckCarNumOrderList() {
	     return checkCarNumOrderList;
	  }

	  public void setCheckCarNumOrderList(
	  	  List<CBPCheckCarNumOrder> checkCarNumOrderList) {
	     this.checkCarNumOrderList = checkCarNumOrderList;
	  }

	  public List<CBPBoutiqueOrder> getBoutiqueOrderList() {
	     return boutiqueOrderList;
	  }

	  public void setBoutiqueOrderList(List<CBPBoutiqueOrder> boutiqueOrderList) {
	     this.boutiqueOrderList = boutiqueOrderList;
	  }

	  public CBPDeposit getCbpDeposit() {
	     return cbpDeposit;
	  }

	  public void setCbpDeposit(CBPDeposit cbpDeposit) {
	     this.cbpDeposit = cbpDeposit;
	  }

	  public List<CBPInsuranceOrder> getInsuranceOrderList() {
	     return insuranceOrderList;
	  }

	  public void setInsuranceOrderList(List<CBPInsuranceOrder> insuranceOrderList) {
	     this.insuranceOrderList = insuranceOrderList;
	  }

	  public Integer getOrderid() {
	     return orderid;
	  }

	  public void setOrderid(Integer orderid) {
	     this.orderid = orderid;
	  }

	  public Integer getCustomerid() {
	     return customerid;
	  }

	  public void setCustomerid(Integer customerid) {
	     this.customerid = customerid;
	  }

	  public String getOrderstatus() {
	     return orderstatus;
	  }

	  public void setOrderstatus(String orderstatus) {
	     this.orderstatus = orderstatus;
	  }

	  public String getOrderdate() {
	     return orderdate;
	  }

	  public void setOrderdate(String orderdate) {
	     this.orderdate = orderdate;
	  }

	  public String getCartype() {
	     return cartype;
	  }

	  public void setCartype(String cartype) {
	     this.cartype = cartype;
	  }

	  public String getVinno() {
	     return vinno;
	  }

	  public void setVinno(String vinno) {
	     this.vinno = vinno;
	  }

	  public String getSaporderid() {
	     return saporderid;
	  }

	  public void setSaporderid(String saporderid) {
	     this.saporderid = saporderid;
	  }

	  public String getPhonenumber() {
	     return phonenumber;
	  }

	  public void setPhonenumber(String phonenumber) {
	     this.phonenumber = phonenumber;
	  }

	  public String getOrganizecode() {
	     return organizecode;
	  }

	  public void setOrganizecode(String organizecode) {
	     this.organizecode = organizecode;
	  }

	  public String getIdcardnum() {
	     return idcardnum;
	  }

	  public void setIdcardnum(String idcardnum) {
	     this.idcardnum = idcardnum;
	  }

	  public String getDelivertime() {
	     return delivertime;
	  }

	  public void setDelivertime(String delivertime) {
	     this.delivertime = delivertime;
	  }

	  public Integer getEmployeeid() {
	     return employeeid;
	  }

	  public void setEmployeeid(Integer employeeid) {
	     this.employeeid = employeeid;
	  }

	  public String getBuyeridcardnum() {
	     return buyeridcardnum;
	  }

	  public void setBuyeridcardnum(String buyeridcardnum) {
	     this.buyeridcardnum = buyeridcardnum;
	  }

	  public String getBrandid() {
	     return brandid;
	  }

	  public void setBrandid(String brandid) {
	     this.brandid = brandid;
	  }

	  public String getOrdertype() {
	     return ordertype;
	  }

	  public void setOrdertype(String ordertype) {
	     this.ordertype = ordertype;
	  }

	  public String getRealvinno() {
	     return realvinno;
	  }

	  public void setRealvinno(String realvinno) {
	     this.realvinno = realvinno;
	  }

	  public String getBuyername() {
	     return buyername;
	  }

	  public void setBuyername(String buyername) {
	     this.buyername = buyername;
	  }

	  public String getCustomername() {
	     return customername;
	  }

	  public void setCustomername(String customername) {
	     this.customername = customername;
	  }

	  public String getTotalprice() {
	     return totalprice;
	  }

	  public void setTotalprice(String totalprice) {
	     this.totalprice = totalprice;
	  }

	  public String getCarid() {
	     return carid;
	  }

	  public void setCarid(String carid) {
	     this.carid = carid;
	  }

	  public String getOrganizename() {
	     return organizename;
	  }

	  public void setOrganizename(String organizename) {
	     this.organizename = organizename;
	  }
       
       
}
