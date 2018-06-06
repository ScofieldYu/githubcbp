package com.blchina.cbp.dao;

import com.blchina.cbp.dto.*;
import org.springframework.stereotype.Service;

import com.blchina.cbp.model.CBPOrder;

import java.util.List;
import java.util.Map;

@Service
public interface CBPOrderMapper {
    int deleteByPrimaryKey(Integer orderid);

    int insert(CBPOrder record);

    int insertSelective(CBPOrder record);

    CBPOrder selectByPrimaryKey(Integer orderid);

    int updateByPrimaryKeySelective(CBPOrder record);

    int updateByPrimaryKey(CBPOrder record);

    List<OrderDTO> getOrderListByphone(CBPOrder record);

    List<OrderDTO> getOrderByEmployeeAndContract(OrderDTO orderDTO);

    OrderDTO  getOrderAndContractById(Integer orderid);

    CBPOrder getOrderIDByVinNO(String vinno);

    List<CBPOrder> getOrderListByCustomerIdAndStatus(CBPOrder record);

    List<CBPOrder> getOrderList(String customerid);

    CBPOrder getOrderBySapId(String saporderid);

    int updateOrderBySapId(CBPOrder order);

    List<CBPOrder> getOrderByVinNo(String vinno);

    List<OrderDTO> getCustomerByEmployeeId(Integer employeeid);
    List<OrderDTO> getCustomerByEmployeeIdBase(Integer eployeeid);
    
    List<OrderDTO> searchContract(ContractSearchDTO constractSearch);

   List<CBPOrder> getOrderByEmployeeId(Integer employeeId);

   List<CBPOrder> getOrderListByEmployeeId(Map map);

   Integer getOrderListCountByEmployeeId(Map map);

   List<CBPOrder> getOrderListByEmployeeIds(Map map);

   Integer getOrderListCountByEmployeeIds(Map map);
    List<SearchCustomerDTO> searchCustomerPublic(CustomerInfoQueryDTO customerInfoQueryDTO);

    List<SearchCustomerDTO> searchCustomerPrivate(CustomerInfoQueryDTO customerInfoQueryDTO);

    List<MonitorSearchDTO> searchOrderMonitorManager(MonitorSearchDTO monitorSearchDTO);

    List<MonitorSearchDTO> searchOrderMonitorBrand(MonitorSearchDTO monitorSearchDTO);

   CBPOrder selectOrderByCarId(String carid);

    List<SignMonitorDTO> showOrderSignMonitor(SignMonitorDTO signMonitorDTO);

   Integer getOrderCountByBrandIdAndOrderStatus(SignMonitorDTO smdto);

   List<CBPOrder> getOrderListByEmployeeIdAndCustomerId(CBPOrder cbpOrder);

   List<CBPOrder> getOrderListByBrandIdAndCustomerId(CBPOrder cbpOrder);

   List<CBPOrder> getOrderListBySearchCard(CardDTO cardDTO);

    ProcessDTO getOrderProcess(Integer orderid);

    List<CBPOrder> getAllOrder();
}