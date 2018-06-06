/*
 * @(#)OrderFileService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.interfaces;

import com.blchina.cbp.dto.UploadDTO;
import com.blchina.cbp.model.CBPOrderFile;

/**
 *订单文件上传Service
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface OrderFileService {
    CBPOrderFile  getFileByOrderId(Integer orderid);

    int insertOrUpdateFile(UploadDTO uploadDTO,String uuid);
}
