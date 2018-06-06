package com.blchina.cbp.dao;

import com.blchina.cbp.dto.UploadDTO;
import com.blchina.cbp.model.CBPOrderFileExt;

import java.util.List;

public interface CBPOrderFileExtMapper {
    int deleteByPrimaryKey(Integer orderextfileid);

    int insert(CBPOrderFileExt record);

    int insertSelective(CBPOrderFileExt record);

    CBPOrderFileExt selectByPrimaryKey(Integer orderextfileid);

    int updateByPrimaryKeySelective(CBPOrderFileExt record);

    int updateByPrimaryKey(CBPOrderFileExt record);

    void deleteOrderFileByType(CBPOrderFileExt extFile);

    List<CBPOrderFileExt> selectFileExt(UploadDTO uploadDTO);
}