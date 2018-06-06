/*
 * @(#)OrderFileService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.impl;

import com.blchina.cbp.dao.CBPOrderFileMapper;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.dto.UploadDTO;
import com.blchina.cbp.model.CBPOrderFile;
import com.blchina.cbp.service.interfaces.OrderFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *订单文件上传Service
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("OrderFileService")
public class OrderFileServiceImpl implements OrderFileService {
    @Autowired
    CBPOrderFileMapper cbpOrderFileMapper;
    @Override
    public CBPOrderFile getFileByOrderId(Integer orderid) {
        return cbpOrderFileMapper.getFileByOrderId(orderid);
    }

    @Override
    public int insertOrUpdateFile(UploadDTO uploadDTO,String uuid) {
        int i=0;
        String type = uploadDTO.getType();
        CBPOrderFile fileByOrderId = cbpOrderFileMapper.getFileByOrderId(uploadDTO.getOrderid());
        if(fileByOrderId==null){
            fileByOrderId=new CBPOrderFile();
            fileByOrderId.setOrderid(uploadDTO.getOrderid());
        }
        if(type.equals(CBPConstant.FileEnum.CUSTOMERIDCARD.getType())){
            fileByOrderId.setCustomercarduuid(uuid);
        }else  if(type.equals(CBPConstant.FileEnum.BUYERIDCARD.getType())){
            fileByOrderId.setBuyeridcarduuid(uuid);
        }else  if(type.equals(CBPConstant.FileEnum.BUSINLICENSE.getType())){
            fileByOrderId.setBusilicenseuuid(uuid);
        }else {
            fileByOrderId.setAttorneyuuid(uuid);
        }
       if(fileByOrderId.getOrderfileid()==null&&uuid!=null){
          i= cbpOrderFileMapper.insert(fileByOrderId);
       }else {
         i=  cbpOrderFileMapper.updateByPrimaryKey(fileByOrderId);
       }
        return i;
    }
}
