/*
 * @(#)OrderService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.interfaces;

import java.util.List;
import java.util.Map;

import com.blchina.cbp.datamodel.sap.SAPResult;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.*;
import com.blchina.cbp.model.CBPFinanceOrder;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.common.util.page.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 订单操作OrderService
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface OrderService {

    CBPOrder selectByPrimaryKey(Integer orderid);
    List<OrderDTO> getOrderListByphone(CBPOrder order);

    List<OrderDTO> getOrderByEmployeeAndContract(OrderDTO orderDTO);

    OrderDTO getOrderAndContractById(Integer orderid);

    CBPOrder findOrderIDByVinNO(String vinno);

    CBPOrder getOrderById(Integer orderid);

    List<CBPOrder> getOrderListByCustomerIdAndStatus(CBPOrder record);

    CBPOrder getOrderBySapId(String saporderid);

    int insertOrder(CBPOrder order);

    int updateOrderBySapId(CBPOrder orderDTO);

    List<CBPOrder> getOrderByVinNo(String vinno);

    List<CBPOrder> getOrderList(String customerid);

    Boolean compareOrder(CBPOrder order, OrderDTO orderDTO);

    List<OrderDTO> getCustomerByEmployeeId(Integer integer);
    List<OrderDTO> getCustomerByEmployeeIdBase(Integer eployeeid);
    
    List<OrderDTO> searchContract(ContractSearchDTO constractSearch);

    SAPResult orderDliverry(SapOrderDTO saporderDTO);
    Page getOrderByEmployeeId(CustomerInfoQueryDTO customerInfoQueryDTO);
   List<CBPOrder> getOrderByEmployeeId(String employeeId);
    List<SearchCustomerDTO> searchCustomerPrivate(CustomerInfoQueryDTO customerInfoQueryDTO);
    List<SearchCustomerDTO> searchCustomerPublic(CustomerInfoQueryDTO customerInfoQueryDTO);

    List<MonitorSearchDTO> searchOrderMonitorBrand(MonitorSearchDTO monitorSearchDTO);

    List<MonitorSearchDTO> searchOrderMonitorManager(MonitorSearchDTO monitorSearchDTO);
   int insertOrUpdateOrder(CBPOrder cbpOrder);

    List<SignMonitorDTO> showOrderSignMonitor(SignMonitorDTO signMonitorDTO);
    List<PaperCostsDTO> getPaperCosts(SignMonitorDTO smdto);
   List<CBPOrder> getOrderListByEmployeeIdAndCustomerId(CBPOrder cbpOrder);
   List<CBPOrder> getOrderListByBrandIdAndCustomerId(CBPOrder cbpOrder);
   int maintenancePrice(ChangeAllFee caf);
   WeixinResult insertChooseCarAndFourCustomer(ChooseCarDTO cshco);
   List<CBPOrder> getOrderListBySearchCard(CardDTO cardDTO);
   ProcessDTO   getOrderProcess(Integer orderid);
   List<BDLBrandStore<BrandStore>> getPaperCostsForPad(SignMonitorDTO smdto);
   AllFeeDTO getAllFee(Integer orderid);

    List<CBPOrder> getAllOrder();

    int updateOrder(CBPOrder order);
}
