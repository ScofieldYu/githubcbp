package com.blchina.cbp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.blchina.cbp.dto.SignMonitorDTO;
import com.blchina.cbp.model.CBPReceipt;

public interface CBPReceiptMapper {
    int deleteByPrimaryKey(Integer receiptid);

    int insert(CBPReceipt record);

    int insertSelective(CBPReceipt record);

    CBPReceipt selectByPrimaryKey(Integer receiptid);

    int updateByPrimaryKeySelective(CBPReceipt record);

    int updateByPrimaryKey(CBPReceipt record);
    
    List<CBPReceipt> selectReceipt(@Param("orderId")Integer orderId);

   CBPReceipt selectBySapOrderId(String saporderid);

   int updateBySapOrderId(CBPReceipt cbpReceipt);

   Integer getReceiptCountByBrandIdAndOrderStatus(SignMonitorDTO smdto);

}