/*
 * @(#)ContractManageService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.interfaces;

import com.blchina.cbp.dto.CustmerDTO;
import com.blchina.cbp.dto.OrderDTO;
import com.blchina.cbp.dto.UploadDTO;
import com.blchina.cbp.model.CBPOrder;

/**
 *合同管理Service
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface ContractManageService {
    Boolean uploadContractManageFile(UploadDTO uploadDTO);
    Boolean uploadOrderData(CBPOrder order,OrderDTO  orderDTO);
    Boolean updateOrderStauts( CBPOrder order);
    Boolean deleteFile(UploadDTO uploadDTO);
}
