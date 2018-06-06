package com.blchina.cbp.dao;

import org.springframework.stereotype.Service;

import com.blchina.cbp.model.CBPCloseCause;
@Service
public interface CBPCloseCauseMapper {
    int deleteByPrimaryKey(Integer closecauseid);

    int insert(CBPCloseCause record);

    int insertSelective(CBPCloseCause record);

    CBPCloseCause selectByPrimaryKey(Integer closecauseid);

    int updateByPrimaryKeySelective(CBPCloseCause record);

    int updateByPrimaryKey(CBPCloseCause record);

   CBPCloseCause selectByContractId(Integer contractid);

   String selectTotalCount();

   String selectTotalCountByCloseType(String closetype);
}