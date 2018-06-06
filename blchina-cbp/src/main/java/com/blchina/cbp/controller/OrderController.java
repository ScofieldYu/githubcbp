/*
 * @(#)OrderController.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.blchina.cbp.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.sap.SAPResult;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.model.CBPBoutiqueItem;
import com.blchina.cbp.model.CBPBoutiqueOrder;
import com.blchina.cbp.model.CBPCar;
import com.blchina.cbp.model.CBPCheckCarNumOrder;
import com.blchina.cbp.model.CBPContract;
import com.blchina.cbp.model.CBPDeposit;
import com.blchina.cbp.model.CBPFinanceOrder;
import com.blchina.cbp.model.CBPFourCustomer;
import com.blchina.cbp.model.CBPInsuranceOrder;
import com.blchina.cbp.model.CBPLogistics;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.model.CBPReturnCarTime;
import com.blchina.cbp.model.CBPSecondHandCarOrder;
import com.blchina.cbp.service.interfaces.AuthService;
import com.blchina.cbp.service.interfaces.BoutiqueItemService;
import com.blchina.cbp.service.interfaces.BoutiqueOrderService;
import com.blchina.cbp.service.interfaces.CarService;
import com.blchina.cbp.service.interfaces.CheckCarNumOrderService;
import com.blchina.cbp.service.interfaces.ConstractService;
import com.blchina.cbp.service.interfaces.ContractItemService;
import com.blchina.cbp.service.interfaces.ContractManageService;
import com.blchina.cbp.service.interfaces.DepositService;
import com.blchina.cbp.service.interfaces.FinanceService;
import com.blchina.cbp.service.interfaces.FourCustomerService;
import com.blchina.cbp.service.interfaces.InsuranceOrderService;
import com.blchina.cbp.service.interfaces.LogisticsService;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.cbp.service.interfaces.ReturnCarTimeService;
import com.blchina.cbp.service.interfaces.SecondHandCarService;
import com.blchina.cbp.service.interfaces.UserService;
import com.blchina.cbp.util.UploadUtil;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;
import com.github.pagehelper.PageHelper;

import net.sf.json.JSONObject;

/**
 * 订单操作controller
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/order")
@Transactional
public class OrderController {
    @Autowired
    private Properties systemConfig;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ConstractService constractService;
    @Autowired
    private AuthService authService;
    @Autowired
    private ContractItemService contractItemService;
    @Autowired
    private ContractManageService contractManageService;
    @Autowired
    private InsuranceOrderService insuranceOrderService;
    @Autowired
    private DepositService depositService;
    @Autowired
    private BoutiqueOrderService boutiqueOrderService;
    @Autowired
    private CheckCarNumOrderService checkCarNumOrderService;
    @Autowired
    private SecondHandCarService secondHandCarService;
    @Autowired
    private FourCustomerService fourCustomerService;
    @Autowired
    private CarService carService;
    @Autowired
    private UserService userService;
    @Autowired
    private FinanceService financeService;
    @Autowired
    private ReturnCarTimeService returnCarTimeService;
    @Autowired
    private LogisticsService logisticsService;
    @Autowired
    private BoutiqueItemService boutiqueItemService;
    /**
     * 获取订单列表
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping("/getOrderList")
    public WeixinResult getOrderListByPhone(@RequestBody JSONObject jsonObject) {
        WeixinResult res = new WeixinResult();
        String openid = (String)jsonObject.get("openid");
        String brandid= jsonObject.get("brandid").toString();
        if (StringUtil.isNullorEmpty(openid)||StringUtil.isNullorEmpty(brandid)) {
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        } else {
            String customerid=null;
            try {
             customerid = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerIdByOpenid",jsonObject.toString());
            }catch (Exception e){
                res.setCode(CBPConstant.CODE_NULL_PARAM);
                res.setMessage("获取信息失败");
                return res;
            }
            CBPOrder cbpOrder=new CBPOrder();
            cbpOrder.setBrandid(brandid);
            cbpOrder.setCustomerid(Integer.valueOf(customerid));
            List<OrderDTO> orderListByphone = orderService.getOrderListByphone(cbpOrder);
            for(int i=0;i<orderListByphone.size();i++){
                try {
                    String employeePhone = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployeePhoneNumberById", "{\"employeeid\":" + orderListByphone.get(i).getEmployeeid() + "}");
                    orderListByphone.get(i).setEmployeePhone(employeePhone);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            res.setData(orderListByphone);
            return res;

        }
    }
    /**
     * 获取订单
     *
     * @param order
     * @return
     */
    @RequestMapping("/getOrderById")
    public WeixinResult getOrderById(@RequestBody CBPOrder order) {
        WeixinResult res = new WeixinResult();
        Integer orderId = order.getOrderid();
        if (orderId == null) {
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        } else {
            CBPOrder getOrder = orderService.selectByPrimaryKey(orderId);
            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setMessage(CBPConstant.MESSAGE_SUCCESS);
            res.setData(getOrder);
            return res;

        }
    }

    /**
     * 根据员工id和合同状态获取订单
     * @param orderDTO
     * @return
     */
    @RequestMapping("/getOrderByEmployeeAndContract")
    public WeixinResult getOrderByEmployeeAndContract(@RequestBody OrderDTO orderDTO){
        WeixinResult res=new WeixinResult();
        Integer employeeid = orderDTO.getEmployeeid();
        if(employeeid==null){
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        List<BDLEmployee> employeeList=null;
        try{
            String response = HttpUtil.postbody(
                    systemConfig.getProperty("url.employee") + "/ee/getEmployeeListByEmployeeId",
                    "{\"employeeid\":" + orderDTO.getEmployeeid() + "}");
            System.out.println("ES系统返回"+response);
            employeeList = JSONArray.parseArray(response, BDLEmployee.class);
            List<String>  allEmployeelist=new ArrayList<String>();
            if(employeeList.size()!=0){
                for(int i=0;i<employeeList.size();i++){
                    allEmployeelist.add(String.valueOf(employeeList.get(i).getEmployeeid()));
                }
            }
            Object[] employeeArray = allEmployeelist.toArray();
            orderDTO.setEmployelist(employeeArray);

        }catch (Exception e){
            System.out.print("获取下级员工失败");
        }
        List<OrderDTO> list=orderService.getOrderByEmployeeAndContract(orderDTO);
        for(int i=0;i<list.size();i++){
            for(int j=0;j<employeeList.size();j++){
                if(String.valueOf(list.get(i).getEmployeeid()).equals(String.valueOf(employeeList.get(j).getEmployeeid()))){
                    list.get(i).setEmployeeName(employeeList.get(j).getEmployeename());
                }
            }
        }
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        res.setData(list);
        return  res;
    }
    
    /**
     * 搜索合同
     * 
     * @param contractSearchDTO
     * @return
     */
    @RequestMapping("/searchContract")
    public WeixinResult searchContract(
 		 @RequestBody ContractSearchDTO contractSearchDTO) {
 	  WeixinResult res = new WeixinResult();
 	  List<OrderDTO> list = null;
 	  int returnstatus = 0;
 	  try {
          String response = HttpUtil.postbody(
                  systemConfig.getProperty("url.employee") + "/ee/getEmployeeListByEmployeeId",
                  "{\"employeeid\":" + contractSearchDTO.getEmployeeId() + "}");
          System.out.println("ES系统返回"+response);
          List<BDLEmployee> employeeList = JSONArray.parseArray(response, BDLEmployee.class);
          if(!StringUtil.isNullorEmpty(contractSearchDTO.getSearchEmployee())){
              String searchEmployee = contractSearchDTO.getSearchEmployee();
              for(int i=0;i<employeeList.size();i++){
                  if(!String.valueOf(employeeList.get(i).getEmployeeid()).equals(searchEmployee)&&!employeeList.get(i).getEmployeename().contains(searchEmployee)){
                      employeeList.get(i).setEmployeeid(0);
                  }
              }
          }

          List<String>  allEmployeelist=new ArrayList<String>();
          if(employeeList.size()!=0){
              for(int i=0;i<employeeList.size();i++){
                  allEmployeelist.add(String.valueOf(employeeList.get(i).getEmployeeid()));
              }
          }
          Object[] employeeArray = allEmployeelist.toArray();
          contractSearchDTO.setEmployelist(employeeArray);
 		 list = orderService.searchContract(contractSearchDTO);
          for(int i=0;i<list.size();i++){
              for(int j=0;j<employeeList.size();j++){
                  if(String.valueOf(list.get(i).getEmployeeid()).equals(String.valueOf(employeeList.get(j).getEmployeeid()))){
                      list.get(i).setEmployeeName(employeeList.get(j).getEmployeename());
                  }
              }
          }
 	  }
 	  catch (Exception e) {
 		 res.setCode(CBPConstant.CODE_FAILURE);
 		 res.setMessage(e.getMessage().toString());
 		 returnstatus = 1;
 		 e.printStackTrace();
 	  }
 	  if (returnstatus == 0) {
 		 res.setCode(CBPConstant.CODE_SUCCESS);
 		 res.setMessage(CBPConstant.MESSAGE_SUCCESS);
 		 res.setData(list);
 	  }
 	  return res;
    }

    /**
     * 根据订单id获取订单
     * @param orderDTO
     * @return
     */
    @RequestMapping("/getOrderAndContractById")
    public WeixinResult getOrderAndContractById(@RequestBody OrderDTO orderDTO){
        WeixinResult res=new WeixinResult();
        Integer orderid = orderDTO.getOrderid();
        String contractstatus = orderDTO.getContractstatus();
        if(orderid==null){
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        OrderDTO order=orderService.getOrderAndContractById(orderid);
        res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        res.setData(order);
        return  res;
    }

    /**
     * 订单下发
     * @param saporderDTO
     * @return
     */
    @RequestMapping("/sap/orderDelivery")
    public SAPResult orderDelivery(@RequestBody SapOrderDTO saporderDTO){
     return orderService.orderDliverry(saporderDTO);
    }

    /**
     * 查询销售顾问下的客户信息
     * @param customerInfoQueryDTO
     * @return
     */
    @RequestMapping("/searchCustomer")
    public WeixinResult searchCustomer(@RequestBody CustomerInfoQueryDTO customerInfoQueryDTO){
        WeixinResult res=new WeixinResult();
        if(StringUtil.isNullorEmpty( customerInfoQueryDTO.getEmployeeId())){
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
            return res;
        }
        try {
            String response = HttpUtil.postbody(
                    systemConfig.getProperty("url.employee") + "/ee/getEmployeeListByEmployeeId",
                    "{\"employeeid\":" + customerInfoQueryDTO.getEmployeeId() + "}");
            System.out.println("ES系统返回"+response);
            List<BDLEmployee> list = JSONArray.parseArray(response, BDLEmployee.class);
            if(!StringUtil.isNullorEmpty(customerInfoQueryDTO.getSearchEmployee())){
                for(int i=0;i<list.size();i++){
                    String searchEmployee = customerInfoQueryDTO.getSearchEmployee();
                    if(!String.valueOf(list.get(i).getEmployeeid()).equals(searchEmployee)&&!list.get(i).getEmployeename().contains(searchEmployee)){
                        list.get(i).setEmployeeid(0);
                    }
                }
            }

            List<String>  allEmployeelist=new ArrayList<String>();
            if(list.size()!=0){
                  for(int i=0;i<list.size();i++){
                      allEmployeelist.add(String.valueOf(list.get(i).getEmployeeid()));
                  }
            }
            Object[] employeeArray = allEmployeelist.toArray();
            customerInfoQueryDTO.setEmployelist(employeeArray);
            PageHelper.startPage(customerInfoQueryDTO.getCurrentPage(),20);
            String accountType = customerInfoQueryDTO.getAccountType();
            List<SearchCustomerDTO>  listCustomer=null;
            if(accountType.equals("1")){
                listCustomer= orderService.searchCustomerPrivate(customerInfoQueryDTO);
            }else {
                listCustomer= orderService.searchCustomerPublic(customerInfoQueryDTO);
            }
             PagePojo pojo = new PagePojo(listCustomer);
             List<SearchCustomerDTO> datas = pojo.getDatas();
            for(int i=0;i<datas.size();i++){
                SearchCustomerDTO searchCustomerDTO = datas.get(i);
                 for(int j=0;j<list.size();j++){
                     if(searchCustomerDTO.getEmployeeId().intValue()==list.get(j).getEmployeeid().intValue()){
                         datas.get(i).setEmployeeName(list.get(j).getEmployeename());
                     }
                 }
            }

            res.setCode(CBPConstant.CODE_SUCCESS);
            res.setData(pojo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;

    }

    
   /**
    * {智能选车订单接收}
    */
   @RequestMapping("/receiveOrder")
   public WeixinResult receiveOrder(@RequestBody CBPOrder cbpOrder) {
	  WeixinResult res = new WeixinResult();
	  res.setCode(CBPConstant.CODE_NULL_PARAM);
	  res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if (cbpOrder != null && !StringUtil.isNullorEmpty(cbpOrder.getCarid())) {
		 int status = orderService.insertOrUpdateOrder(cbpOrder);
		 if (status != 0) {
			res.setCode(CBPConstant.CODE_SUCCESS);
			res.setMessage(CBPConstant.MESSAGE_SUCCESS);
			return res;
		 }
	  }
	  return res;
   }

   /**
    * 查询订单详情（客户版）
    * 
    */
   @RequestMapping("/getOrderByOrderId")
   public WeixinResult getOrderByOrderId(@RequestBody CBPOrder order) {
	  WeixinResult res = new WeixinResult();
	  res.setCode(CBPConstant.CODE_NULL_PARAM);
	  res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if (order != null && !StringUtil.isNullorEmpty(order.getOrderid() + "")) {
		 CBPOrder cbpOrder = orderService
			   .selectByPrimaryKey(order.getOrderid());
		//客户资料
		 List<CBPFourCustomer> fourCustomerList = fourCustomerService.selectFourCustomerListByOrderId(order.getOrderid());
		 //贷款信息
		 List<CBPFinanceOrder> financeOrderList = financeService.selectFianceByOrderId(order.getOrderid());
		 //车辆信息
		 CBPCar car = carService.selectCarByOrderId(order.getOrderid());
		 //保险
		 List<CBPInsuranceOrder> insuranceOrderList = insuranceOrderService.selectInsuranceOrderListByOrderId(order.getOrderid());
		 //精品
		 List<CBPBoutiqueOrder> boutiqueOrderList = boutiqueOrderService.selectBoutiqueByOrderId(order.getOrderid());
		 List<CBPBoutiqueItem> BoutiqueItemList = null;
		 if(boutiqueOrderList!=null && boutiqueOrderList.size()!=0){
			//精品分类
			BoutiqueItemList = boutiqueItemService.selectBoutiqueItemList(boutiqueOrderList.get(0));
		 }
		 //验车上牌
 		 List<CBPCheckCarNumOrder> checkCarNumOrderList = checkCarNumOrderService.selectCheckCarNumByOrderId(order.getOrderid());
 		 //车辆置换
 		 List<CBPSecondHandCarOrder> secondHandCarOrderList = secondHandCarService.selectSecondHandCarByOrderId(order.getOrderid());
		 //添加费用
		 double totlePrice = 0;
		 double totlePriceAll = 0;
		 double totlePrice2 = 0;
		 double totlePrice3 = 0;
		 double checkCarNumtotlePrice = 0;
 		 double secondHandCartotlePrice = 0;
 		if(financeOrderList.size()!=0){
			for(CBPFinanceOrder cio :financeOrderList){
			   if(cio!=null && !StringUtil.isNullorEmpty(cio.getFirstpay())){
				  totlePrice3+=Double.valueOf(cio.getFirstpay());
			   }
			}
		 }
 		if(insuranceOrderList.size()!=0){
			for(CBPInsuranceOrder cio :insuranceOrderList){
			   if(cio!=null && !StringUtil.isNullorEmpty(cio.getExpense())){
				  totlePrice+=Double.valueOf(cio.getExpense());
			   }
			}
		 }
		 if( BoutiqueItemList!=null && BoutiqueItemList.size()!=0 ){
			for(CBPBoutiqueItem cbo :BoutiqueItemList){
			   if(cbo!=null && !StringUtil.isNullorEmpty(cbo.getBoutiqueprice())){
				  totlePrice2+=Double.valueOf(cbo.getBoutiqueprice());
			   }
			}
		 }
		if(checkCarNumOrderList.size()!=0){
			for(CBPCheckCarNumOrder cbo :checkCarNumOrderList){
			   if(cbo!=null && !StringUtil.isNullorEmpty(cbo.getBuytaxvalue())){
				  checkCarNumtotlePrice+=Double.valueOf(cbo.getBuytaxvalue());
			   }else if(cbo!=null && !StringUtil.isNullorEmpty(cbo.getCheckcarvalue())){
				  checkCarNumtotlePrice+=Double.valueOf(cbo.getCheckcarvalue());
			   }else if(cbo!=null && !StringUtil.isNullorEmpty(cbo.getTempplatevalue())){
				  checkCarNumtotlePrice+=Double.valueOf(cbo.getTempplatevalue());
			   }
			}
		 }
		if(secondHandCarOrderList.size()!=0){
		   for(CBPSecondHandCarOrder cbo :secondHandCarOrderList){
			  if(cbo!=null && !StringUtil.isNullorEmpty(cbo.getPrice())){
				 secondHandCartotlePrice+=Double.valueOf(cbo.getPrice());
			  }
		   }
		}
		 CBPContract maxConstract = constractService.getMaxConstract(order.getOrderid());//-secondHandCartotlePrice
		 if(maxConstract!=null){
			totlePriceAll+=Double.valueOf(maxConstract.getTotalprice());
		 }
		 totlePriceAll = totlePrice+totlePrice2+totlePrice3+checkCarNumtotlePrice;
		 DecimalFormat df = new DecimalFormat("00.00");
		 String format = df.format(totlePriceAll);
		 
		 OrderDetailsDTO orderDetailsDTO = cbpOrder2OrderDetailsDTO(cbpOrder);
		 orderDetailsDTO.setTotalprice(format);
		 orderDetailsDTO.setFourCustomerList(fourCustomerList);
		 //预约交车时间
		 CBPReturnCarTime returnCarTime = returnCarTimeService.getMaxDateByOrderId(order.getOrderid());
		 orderDetailsDTO.setReturnCarTime(returnCarTime);
		 //物流信息
		 CBPLogistics logistics = logisticsService.getMaxStatusByOrderId(order.getOrderid());
		 orderDetailsDTO.setLogistics(logistics);
		 //车辆信息
		 orderDetailsDTO.setCar(car);
		 //贷款信息
		 for(CBPFinanceOrder cbpFinanceOrder :financeOrderList){
			try {
			   String response = HttpUtil.postbody(
                  systemConfig.getProperty("url.employee") + "/ee/getPhoneNumberByCompanycode",
                  "{\"companycode\":" + cbpOrder.getBrandid() + "}");
			   cbpFinanceOrder.setFee(response);
			}
			catch (Exception e) {
			  e.printStackTrace();
			}
		 }
		 orderDetailsDTO.setFinanceOrderList(financeOrderList);
		 //车险
		 orderDetailsDTO.setInsuranceOrderList(insuranceOrderList);
		 //精品
		 orderDetailsDTO.setBoutiqueOrderList(boutiqueOrderList);
		//验车上牌
		 orderDetailsDTO.setCheckCarNumOrderList(checkCarNumOrderList);
    	 //车辆置换
		 orderDetailsDTO.setSecondHandCarOrderList(secondHandCarOrderList);
    	 //车辆价格（不包括业务）
//		 orderDetailsDTO.setCartotalprice(Double.valueOf(maxConstract.getTotalprice()).toString());
		 //买方手机号
		 CBPFourCustomer cfc = fourCustomerService.selectFourCustomerByOrderIdAndType(order.getOrderid());
		 if(cfc!=null){
			orderDetailsDTO.setBuyphonenumber(cfc.getPhonenumber());
		 }else{
			orderDetailsDTO.setBuyphonenumber(cbpOrder.getPhonenumber());
		 }
		 //定金
		 CBPDeposit deposit = depositService.selectDepositBySapOrderId(cbpOrder.getSaporderid());
		 orderDetailsDTO.setCbpDeposit(deposit);
		 res.setCode(CBPConstant.CODE_SUCCESS);
		 res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		 res.setData(orderDetailsDTO);
	  }
	  return res;
   }

   public static CheckCarNumOrderDTO CBPCheckCarNumOrder2CheckCarNumOrderDTO(CBPCheckCarNumOrder cccno){
	  CheckCarNumOrderDTO ccod = new CheckCarNumOrderDTO();
	  ccod.setCheckcarnumid(cccno.getCheckcarnumid());
	  ccod.setBuytax(cccno.getBuytax());
	  ccod.setBuytaxvalue(cccno.getBuytaxvalue());
	  ccod.setCheckcar(cccno.getCheckcar());
	  ccod.setCheckcarvalue(cccno.getCheckcarvalue());
	  ccod.setOrderid(cccno.getOrderid());
	  ccod.setReservecheckcartime(cccno.getReservecheckcartime());
	  ccod.setStatus(cccno.getStatus());
	  ccod.setTempplate(cccno.getTempplate());
	  ccod.setTempplatecount(cccno.getTempplatecount());
	  ccod.setTempplatevalue(cccno.getTempplatevalue());
	  ccod.setChoosetype(cccno.getChooseType());
	  return ccod;
   }
   
   public static OrderDetailsDTO cbpOrder2OrderDetailsDTO(CBPOrder cbpOrder) {
	  OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
	  orderDetailsDTO.setBrandid(cbpOrder.getBrandid());
	  orderDetailsDTO.setBuyeridcardnum(cbpOrder.getBuyeridcardnum());
	  orderDetailsDTO.setBuyername(cbpOrder.getBuyername());
	  orderDetailsDTO.setCarid(cbpOrder.getCarid());
	  orderDetailsDTO.setCartype(cbpOrder.getCartype());
	  orderDetailsDTO.setCustomerid(cbpOrder.getCustomerid());
	  orderDetailsDTO.setCustomername(cbpOrder.getCustomername());
	  orderDetailsDTO.setDelivertime(cbpOrder.getDelivertime());
	  orderDetailsDTO.setEmployeeid(cbpOrder.getEmployeeid());
	  orderDetailsDTO.setIdcardnum(cbpOrder.getIdcardnum());
	  orderDetailsDTO.setOrderdate(UploadUtil.formatData(cbpOrder.getOrderdate()).toString());
	  orderDetailsDTO.setOrderid(cbpOrder.getOrderid());
	  orderDetailsDTO.setOrderstatus(cbpOrder.getOrderstatus());
	  orderDetailsDTO.setOrdertype(cbpOrder.getOrdertype());
	  orderDetailsDTO.setOrganizecode(cbpOrder.getOrganizecode());
	  orderDetailsDTO.setOrganizename(cbpOrder.getOrganizename());
	  orderDetailsDTO.setPhonenumber(cbpOrder.getPhonenumber());
	  orderDetailsDTO.setRealvinno(cbpOrder.getRealvinno());
	  orderDetailsDTO.setSaporderid(cbpOrder.getSaporderid());
	  orderDetailsDTO.setVinno(cbpOrder.getVinno());
	  return orderDetailsDTO;

   }

  
   /**
    * 获取订单列表(二期客户版)
    *
    * @param jsonObject
    * @return
    */
   @RequestMapping("/getOrderListCustomer")
   public WeixinResult getOrderListCustomer(@RequestBody JSONObject jsonObject){
	  WeixinResult res = new WeixinResult();
    String openid = (String)jsonObject.get("openid");
    String brandid= jsonObject.get("brandid").toString();
    	if (StringUtil.isNullorEmpty(openid)||StringUtil.isNullorEmpty(brandid)) {
        res.setCode(CBPConstant.CODE_NULL_PARAM);
        res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
        return res;
    	} else {
        String customerid=null;
        try {
         customerid = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/getCustomerIdByOpenid",jsonObject.toString());
         if(StringUtil.isNullorEmpty(customerid)){
        	res.setCode(CBPConstant.CODE_SUCCESS);
	        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
	        res.setData(new ArrayList<>());
	        return res;
         }
        }catch (Exception e){
            res.setCode(CBPConstant.CODE_NULL_PARAM);
            res.setMessage("获取信息失败");
            return res;
        }
        CBPOrder cbpOrder=new CBPOrder();
        cbpOrder.setBrandid(brandid);
        cbpOrder.setCustomerid(Integer.valueOf(customerid));
		 List<CBPOrder> cbpOrderList = orderService.getOrderListByBrandIdAndCustomerId(cbpOrder);
		 if(cbpOrderList.isEmpty()){
			res.setCode(CBPConstant.CODE_SUCCESS);
	        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
	        res.setData(new ArrayList<>());
	        return res;
		 }
		 List<OrderDTO> orderDTOList = new ArrayList<>();
		 for(CBPOrder co:cbpOrderList){
			CBPCar car = carService.selectCarByOrderId(co.getOrderid());
			OrderDTO orderDTO = new OrderDTO();
			if(car!=null){
			   orderDTO.setAppearanceinterior(car.getCarappearance());
			   orderDTO.setDerivename(car.getCarinterior());
			}
			orderDTO.setSaporderid(co.getSaporderid());
			orderDTO.setRealvinno(co.getRealvinno());
			orderDTO.setCartype(co.getCartype());
			orderDTO.setCustomername(co.getCustomername());
			orderDTO.setOrderid(co.getOrderid());
			orderDTOList.add(orderDTO);
		 }
		 res.setCode(CBPConstant.CODE_SUCCESS);
         res.setMessage(CBPConstant.MESSAGE_SUCCESS);
         res.setData(orderDTOList);
	  }
	  return res;
   }
   
   
   
   /**
    * 获取订单列表(二期销售顾问版)
    *
    * @param jsonObject
    * @return
    */
   @RequestMapping("/getOrderListEmployee")
   public WeixinResult getOrderListEmployee(@RequestBody CardDTO cardDTO){
	  WeixinResult res = new WeixinResult();
	  if(cardDTO == null ||StringUtil.isNullorEmpty(cardDTO.getEmployeeid()+"")){
		 res.setCode(CBPConstant.CODE_NULL_PARAM);
         res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
         return res;
	  }else{
		 PageHelper.startPage(cardDTO.getCurrentPage(),5);
		 List<CBPOrder> cbpOrderList = orderService.getOrderByEmployeeId(cardDTO.getEmployeeid().toString());
		 PagePojo pojo = new PagePojo(cbpOrderList);
		 if(cbpOrderList.isEmpty()){
			res.setCode(CBPConstant.CODE_SUCCESS);
	        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
	        return res;
		 }
		 List<OrderDTO> orderDTOList = new ArrayList<>();
		 for(CBPOrder co:cbpOrderList){
			CBPCar car = carService.selectCarByOrderId(co.getOrderid());
			OrderDTO orderDTO = new OrderDTO();
			if(car!=null){
			   orderDTO.setAppearanceinterior(car.getCarappearance());
			   orderDTO.setDerivename(car.getCarinterior());
			}
			orderDTO.setRealvinno(co.getRealvinno());
			orderDTO.setCartype(co.getCartype());
			orderDTO.setCustomername(co.getCustomername());
			orderDTO.setOrderid(co.getOrderid());
			orderDTO.setSaporderid(co.getSaporderid());
			orderDTOList.add(orderDTO);
		 }
		 pojo.setDatas(orderDTOList);
		 res.setCode(CBPConstant.CODE_SUCCESS);
         res.setMessage(CBPConstant.MESSAGE_SUCCESS);
         res.setData(pojo);
	  }
	  return res;
   }
   
   
   /**
    * 查询订单详情（销售顾问版）
    * 
    */
   @RequestMapping("/getOrderByOrderIdEmployee")
   public WeixinResult getOrderByOrderIdEmployee(@RequestBody CBPOrder order) {
	  WeixinResult res = new WeixinResult();
	  res.setCode(CBPConstant.CODE_NULL_PARAM);
	  res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if(order != null && StringUtil.isNullorEmpty(order.getOrderid() + "")){
		 res.setCode(CBPConstant.CODE_SUCCESS);
		 res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		 return res;
	  }
	  if (order != null && !StringUtil.isNullorEmpty(order.getOrderid() + "")) {
		 CBPOrder cbpOrder = orderService
			   .selectByPrimaryKey(order.getOrderid());
		 //客户资料
		 List<CBPFourCustomer> fourCustomerList = fourCustomerService.selectFourCustomerListByOrderId(order.getOrderid());
		 //贷款信息
		 List<CBPFinanceOrder> financeOrderList = financeService.selectFianceByOrderId(order.getOrderid());
		 //车辆信息
		 CBPCar car = carService.selectCarByOrderId(order.getOrderid());
		 //保险
		 List<CBPInsuranceOrder> insuranceOrderList = insuranceOrderService.selectInsuranceOrderListByOrderId(order.getOrderid());
		 //精品
		 List<CBPBoutiqueOrder> boutiqueOrderList = boutiqueOrderService.selectBoutiqueByOrderId(order.getOrderid());
		 List<CBPBoutiqueItem> BoutiqueItemList = null;
		 if(boutiqueOrderList!=null && boutiqueOrderList.size()!=0){
			//精品分类
			BoutiqueItemList = boutiqueItemService.selectBoutiqueItemList(boutiqueOrderList.get(0));
		 }
		 //验车上牌
 		 List<CBPCheckCarNumOrder> checkCarNumOrderList = checkCarNumOrderService.selectCheckCarNumByOrderId(order.getOrderid());
 		CheckCarNumOrderDTO dto = null;
 		 if(checkCarNumOrderList.size()!=0){
 			dto = CBPCheckCarNumOrder2CheckCarNumOrderDTO(checkCarNumOrderList.get(0));
 			//购置税完成度
 			String type1 = checkCarNumOrderService.selectCheckCarNumSourceSizeByType(checkCarNumOrderList.get(0).getCheckcarnumid(),"1");
 			//临牌完成度
 			String type2 = checkCarNumOrderService.selectCheckCarNumSourceSizeByType(checkCarNumOrderList.get(0).getCheckcarnumid(),"2");
 			//验车上牌完成度
 			String type3 = checkCarNumOrderService.selectCheckCarNumSourceSizeByType(checkCarNumOrderList.get(0).getCheckcarnumid(),"3");
 			dto.setBuytaxsize(type1);
 			dto.setCheckcarsize(type3);
 			dto.setTempplatesize(type2);
 		 }
 		 
 		 //车辆置换
 		 List<CBPSecondHandCarOrder> secondHandCarOrderList = secondHandCarService.selectSecondHandCarByOrderId(order.getOrderid());
		 //添加费用
		 double totlePrice = 0;
		 double totlePriceAll = 0;
		 double totlePrice2 = 0;
		 double totlePrice3 = 0;
		 double checkCarNumtotlePrice = 0;
 		 double secondHandCartotlePrice = 0;
 		if(financeOrderList.size()!=0){
			for(CBPFinanceOrder cio :financeOrderList){
			   if(cio!=null && !StringUtil.isNullorEmpty(cio.getFirstpay())){
				  totlePrice3+=Double.valueOf(cio.getFirstpay());
			   }
			}
		 }
 		if(insuranceOrderList.size()!=0){
			for(CBPInsuranceOrder cio :insuranceOrderList){
			   if(cio!=null && !StringUtil.isNullorEmpty(cio.getExpense())){
				  totlePrice+=Double.valueOf(cio.getExpense());
			   }
			}
		 }
 		if( BoutiqueItemList!=null && BoutiqueItemList.size()!=0 ){
			for(CBPBoutiqueItem cbo :BoutiqueItemList){
			   if(cbo!=null && !StringUtil.isNullorEmpty(cbo.getBoutiqueprice())){
				  totlePrice2+=Double.valueOf(cbo.getBoutiqueprice());
			   }
			}
		 }
		if(checkCarNumOrderList.size()!=0){
			for(CBPCheckCarNumOrder cbo :checkCarNumOrderList){
			   if(cbo!=null && !StringUtil.isNullorEmpty(cbo.getBuytaxvalue())){
				  checkCarNumtotlePrice+=Double.valueOf(cbo.getBuytaxvalue());
			   }else if(cbo!=null && !StringUtil.isNullorEmpty(cbo.getCheckcarvalue())){
				  checkCarNumtotlePrice+=Double.valueOf(cbo.getCheckcarvalue());
			   }else if(cbo!=null && !StringUtil.isNullorEmpty(cbo.getTempplatevalue())){
				  checkCarNumtotlePrice+=Double.valueOf(cbo.getTempplatevalue());
			   }
			}
		 }
		if(secondHandCarOrderList.size()!=0){
		   for(CBPSecondHandCarOrder cbo :secondHandCarOrderList){
			  if(cbo!=null && !StringUtil.isNullorEmpty(cbo.getPrice())){
				 secondHandCartotlePrice+=Double.valueOf(cbo.getPrice());
			  }
		   }
		}
		 CBPContract maxConstract = constractService.getMaxConstract(order.getOrderid());//-secondHandCartotlePrice
		 if(maxConstract!=null){
			totlePriceAll+=Double.valueOf(maxConstract.getTotalprice());
		 }
		 totlePriceAll = totlePrice+totlePrice2+totlePrice3+checkCarNumtotlePrice;
		 DecimalFormat df = new DecimalFormat("00.00");
		 String format = df.format(totlePriceAll);
		 
		 OrderDetailsDTO orderDetailsDTO = cbpOrder2OrderDetailsDTO(cbpOrder);
		 List<CheckCarNumOrderDTO> checkCarNumOrderDTOList = new ArrayList<>();
		 if(dto!=null){
			checkCarNumOrderDTOList.add(dto);
		 }
		 //员工角色
		 String titleIdByEmployeeId = userService.getTitleIdByEmployeeId(order.getEmployeeid());
		 orderDetailsDTO.setAccouttype(titleIdByEmployeeId);
		//预约交车时间
		 CBPReturnCarTime returnCarTime = returnCarTimeService.getMaxDateByOrderId(order.getOrderid());
		 orderDetailsDTO.setReturnCarTime(returnCarTime);
		 //物流信息
		 CBPLogistics logistics = logisticsService.getMaxStatusByOrderId(order.getOrderid());
		 orderDetailsDTO.setLogistics(logistics);
		 orderDetailsDTO.setTotalprice(format);
		 //客户资料
		 orderDetailsDTO.setFourCustomerList(fourCustomerList);
		 //车辆信息
		 orderDetailsDTO.setCar(car);
		 //贷款信息
			try {
			   String response = HttpUtil.postbody(
                  systemConfig.getProperty("url.employee") + "/userRole/getFinancePhoneNumberByCompanycode",
                  "{\"companycode\":" + cbpOrder.getBrandid() + "}");
			   //金融经理电话
			   orderDetailsDTO.setFinancephonenumber(response);
			   String response1 = HttpUtil.postbody(
	                  systemConfig.getProperty("url.employee") + "/userRole/getSecondHandCarPhoneNumberByCompanycode",
	                  "{\"companycode\":" + cbpOrder.getBrandid() + "}");
			   //评估师电话
			   orderDetailsDTO.setSecondhandcarphonenumber(response1);
			}
			catch (Exception e) {
			  e.printStackTrace();
			}
		 orderDetailsDTO.setFinanceOrderList(financeOrderList);
		 //车险
		 orderDetailsDTO.setInsuranceOrderList(insuranceOrderList);
		 //精品
		 orderDetailsDTO.setBoutiqueOrderList(boutiqueOrderList);
		//验车上牌
		 orderDetailsDTO.setCheckCarNumOrderDTOList(checkCarNumOrderDTOList);
    	 //车辆置换
		 orderDetailsDTO.setSecondHandCarOrderList(secondHandCarOrderList);
    	 //车辆价格（不包括业务）
//		 orderDetailsDTO.setCartotalprice(Double.valueOf(maxConstract.getTotalprice()).toString());
		 //买方手机号
		 CBPFourCustomer cfc = fourCustomerService.selectFourCustomerByOrderIdAndType(order.getOrderid());
		 if(cfc!=null){
			orderDetailsDTO.setBuyphonenumber(cfc.getPhonenumber());
		 }else{
			orderDetailsDTO.setBuyphonenumber(cbpOrder.getPhonenumber());
		 }
		 //定金
		 CBPDeposit deposit = depositService.selectDepositBySapOrderId(cbpOrder.getSaporderid());
		 orderDetailsDTO.setCbpDeposit(deposit);
		 res.setCode(CBPConstant.CODE_SUCCESS);
		 res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		 res.setData(orderDetailsDTO);
	  }
	  return res;
   }
   
   /**    
    * 销售顾问选车   
    * @param cshco
    * @return WeixinResult
    */ 
   @RequestMapping("/chooseCar")
   public WeixinResult chooseCar(@RequestBody ChooseCarDTO cshco){
	  WeixinResult res = new WeixinResult();
	  res.setCode(CBPConstant.CODE_NULL_PARAM);
      res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if(cshco == null ||StringUtil.isNullorEmpty(cshco.getCarseries())
			||StringUtil.isNullorEmpty(cshco.getCartype())
			||StringUtil.isNullorEmpty(cshco.getCarappearance())
			||StringUtil.isNullorEmpty(cshco.getCarinterior())){
         return res;
	  }else{
		 WeixinResult result = orderService.insertChooseCarAndFourCustomer(cshco);
		 return result;
	  }
   }
   
   /**    
    * 查询订单车辆信息    
    *    
    * @return 
    */ 
   @RequestMapping("/getCarMessage")
   public WeixinResult getCarMessage(@RequestBody CBPOrder order){
	  WeixinResult res = new WeixinResult();
	  res.setCode(CBPConstant.CODE_NULL_PARAM);
      res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
      if(order!=null && !StringUtil.isNullorEmpty(order.getOrderid()+"")){
//    	 orderService.getCarMessage(order.getOrderid());
      }
      return res;
   }
   
   /**    
    * 订单搜索
    * @param cd
    * @return WeixinResult
    * @since JDK 1.8 
    */ 
   @RequestMapping("/searchOrderList")
   public WeixinResult searchOrderList(@RequestBody CardDTO cd){
	  WeixinResult res = new WeixinResult();
	  res.setCode(CBPConstant.CODE_NULL_PARAM);
      res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
      if(cd!=null && !StringUtil.isNullorEmpty(cd.getEmployeeId())){
    	 PageHelper.startPage(cd.getCurrentPage(),5);
    	 List<CBPOrder> list = orderService.getOrderListBySearchCard(cd);
    	 PagePojo pojo = new PagePojo(list);
		 List<OrderDTO> orderDTOList = new ArrayList<>();
		 for(CBPOrder co:list){
			CBPCar car = carService.selectCarByOrderId(co.getOrderid());
			OrderDTO orderDTO = new OrderDTO();
			if(car!=null){
			   orderDTO.setAppearanceinterior(car.getCarappearance());
			   orderDTO.setDerivename(car.getCarinterior());
			}
			orderDTO.setRealvinno(co.getRealvinno());
			orderDTO.setCartype(co.getCartype());
			orderDTO.setCustomername(co.getCustomername());
			orderDTO.setOrderid(co.getOrderid());
			orderDTOList.add(orderDTO);
		 }
		 pojo.setDatas(orderDTOList);
		 res.setCode(CBPConstant.CODE_SUCCESS);
        res.setMessage(CBPConstant.MESSAGE_SUCCESS);
        res.setData(pojo);
      }
      return res;
   }
   
   
   /**维护价格
    * @param 
    * @return WeixinResult
    */ 
   @RequestMapping("/maintenancePrice")
   public WeixinResult maintenancePrice(@RequestBody ChangeAllFee caf){
	  WeixinResult res = new WeixinResult();
	  res.setCode(CBPConstant.CODE_NULL_PARAM);
	  res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if(caf==null || caf.getBoutiqueOrder()==null || caf.getCar()==null || caf.getInsuranceOrder()==null){
		 return res;
	  }else{
		 int status = orderService.maintenancePrice(caf);
		 if(status!=0){
			res.setCode(CBPConstant.CODE_SUCCESS);
			res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		 }
	  }
	  return res;
   }
   
   @RequestMapping("/getAllFee")
   public WeixinResult getAllFee(@RequestBody CBPOrder order){
	  WeixinResult res = new WeixinResult();
	  res.setCode(CBPConstant.CODE_NULL_PARAM);
	  res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
	  if(order!=null && !StringUtil.isNullorEmpty(order.getOrderid()+"")){
		 AllFeeDTO afd = orderService.getAllFee(order.getOrderid());
		 res.setCode(CBPConstant.CODE_SUCCESS);
		 res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		 res.setData(afd);
	  }
	  return res;
   }
    @RequestMapping("/synOrderOpenid")
   public void  synOrderOpenid(@RequestBody JSONArray array){
        List<OpenIdDTO> list=array.toJavaList(OpenIdDTO.class);
        List<CBPOrder> listOrder=orderService.getAllOrder();
        for(int i=0;i<listOrder.size();i++){
             for(int j=0;j<list.size();j++){
                 if(list.get(j).getCustomerid().intValue()==listOrder.get(i).getCustomerid().intValue()&&list.get(j).getBrandid().equals(listOrder.get(i).getBrandid())){
                     listOrder.get(i).setOpenid(list.get(j).getOpenid());
                     orderService.updateOrder(listOrder.get(i));
                     break;
                 }
             }
        }
    }
   
}
