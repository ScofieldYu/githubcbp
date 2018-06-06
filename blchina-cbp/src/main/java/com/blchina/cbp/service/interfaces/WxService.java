/*
 * @(#)WxService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.interfaces;

/**
 * wx service
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface WxService {
    String getWxToken(String brandid,String type);
    String getToken();

    Boolean pushCarStart(String userid,Integer orderid,String day,String sapID,Integer employeeid);

    void pushViewMessage(String userid,String customerName,String date,String starttime, String endtime,Integer orderid,Integer employeeid,String storeid,String flag);

    Boolean pustSetTime(String userid,String saporderid,Integer orderid,Integer employeeid);

    Boolean pushCustomerStart(String shopid,String sapID,String openid,String customername,Integer orderid,Integer employeeid);

    Boolean  pushLogisticsStart(String shopid,String sapID, String openid, String customername,Integer orderid,String cartype,String status);

    String getUserId(String code);

    String getEmployee(String username);
    String  getEmployeeByCode(String code);
    public Boolean pushCustomerTime(String shopid, String openid, String content);
    public void pushPayMoney(Integer customerId,String money,String brandid);
    public void  pushCardToEmployee(String userid,String tourl,String content,String title);
    public void pushCardToCustomer(String shopid, String openid,String customername, String content,String tourl,String title);

    void pushMessageToInsurance(String brandid,String customername,Integer orderid,String tourl);
    String pushMessageToboutique(String brandid,String customername,Integer orderid,String tourl);
    public String pushMessageToFinance(String brandid, String customername, Integer orderid, String tourl);
    public void pushMessageToSecondCarConsultant(String brandid, String customername, Integer orderid, String tourl,String time);
    public void pushMessageToCheckCar(String brandid, String customername, Integer orderid, String tourl,String content);
    public void pushMessageToboutique1(String brandid,String customername,Integer orderid,String tourl);
    public String pushMessageToSecondCarFinance(String brandid, String customername, Integer orderid, String tourl);
	public void pushMessageToSecondCarConsultant1(String brandid, String customername,Integer orderid, String tourl);
}
