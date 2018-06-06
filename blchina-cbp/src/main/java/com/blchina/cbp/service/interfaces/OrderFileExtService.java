/*
 * @(#)OrderFileExtService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.interfaces;

import com.blchina.cbp.dto.FileListDTO;
import com.blchina.cbp.dto.UploadDTO;
import com.blchina.cbp.model.CBPOrderFileExt;

import java.util.List;

/**
 *订单扩展文件上传Service
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface OrderFileExtService {
    void saveOrupdateFileExt(CBPOrderFileExt extFile, FileListDTO fileuuidList);

    void deleteFile(CBPOrderFileExt extFile);

    List<CBPOrderFileExt> selectFileExt(UploadDTO uploadDTO);
}
