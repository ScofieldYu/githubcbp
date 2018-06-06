/*
 * @(#)ConstractService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.interfaces;

import com.blchina.cbp.datamodel.sap.SAPResult;
import com.blchina.cbp.dto.OrderDTO;
import com.blchina.cbp.dto.SapOrderDTO;
import com.blchina.cbp.dto.StatisticalDTO;
import com.blchina.cbp.model.CBPCloseCause;
import com.blchina.cbp.model.CBPContract;
import com.blchina.cbp.model.CBPContractItem;
import com.blchina.cbp.model.CBPOrder;


/**
 * 合同操作ConstractService
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface ConstractService {
   int addConstract(CBPContract contract);

   int updateConstractByOrderId(CBPContract contract);

   int updateConstractByVinNo(String vinno);

   CBPContract findConstract(CBPContract constract);

   void updataConstractById(Integer contractid,String uuid);

   CBPContract getMaxConstract(Integer orderid);

   Boolean compareContract(CBPContract constract, OrderDTO orderDTO);

    void updateConstract(CBPContract contract);

   int updateConstractAndContractItem(CBPContractItem cbpContractItem);

   String selectContract(String orderId);

   int updateContract(CBPCloseCause cbpCloseCause);

   CBPOrder selectCustomerIdByContractId(String contract_id);

   void updateisChangeByOrderId(Integer orderid);

   SAPResult autoSign(SapOrderDTO saporderDTO);

   CBPContract findConstractById(String contract_id);

   boolean cancelContract(Integer orderid);

   StatisticalDTO statisticalCloseCause(CBPCloseCause cbpCloseCause);
}
