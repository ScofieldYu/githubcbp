/*
 * @(#)OrderFileExtService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.impl;

import com.blchina.cbp.dao.CBPOrderFileExtMapper;
import com.blchina.cbp.dto.FileListDTO;
import com.blchina.cbp.dto.UploadDTO;
import com.blchina.cbp.model.CBPOrderFileExt;
import com.blchina.cbp.service.interfaces.OrderFileExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *订单扩展文件上传Service
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
 @Service("OrderFileExtService")
public class OrderFileExtServiceImpl implements OrderFileExtService {
    @Autowired
    CBPOrderFileExtMapper  orderFileExtMapper;

    /**
     * 保存扩展文件
     * @param extFile
     * @param fileuuidList
     */
    @Override
    public void saveOrupdateFileExt(CBPOrderFileExt extFile, FileListDTO fileuuidList) {
        orderFileExtMapper.deleteOrderFileByType(extFile);
        for(int i=0;i<fileuuidList.getObj().length;i++){
            extFile.setFileuuid(fileuuidList.getObj()[i].getUuid());
            extFile.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
            orderFileExtMapper.insert(extFile);
        }

    }

    @Override
    public void deleteFile(CBPOrderFileExt extFile) {
        orderFileExtMapper.deleteOrderFileByType(extFile);
    }

    @Override
    public List<CBPOrderFileExt> selectFileExt(UploadDTO uploadDTO) {
        return orderFileExtMapper.selectFileExt(uploadDTO);
    }
}
