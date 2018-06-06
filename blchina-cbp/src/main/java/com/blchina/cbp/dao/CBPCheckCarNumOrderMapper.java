package com.blchina.cbp.dao;

import org.springframework.stereotype.Service;

import java.util.List;

import com.blchina.cbp.model.CBPCheckCarNumOrder;

@Service
public interface CBPCheckCarNumOrderMapper {
    int deleteByPrimaryKey(Integer checkcarnumid);

    int insert(CBPCheckCarNumOrder record);

    int insertSelective(CBPCheckCarNumOrder record);

    CBPCheckCarNumOrder selectByPrimaryKey(Integer checkcarnumid);

    int updateByPrimaryKeySelective(CBPCheckCarNumOrder record);

    int updateByPrimaryKey(CBPCheckCarNumOrder record);

    List<CBPCheckCarNumOrder> selectCheckCarNumByOrderId(Integer orderid);

	int updateByOrderId(CBPCheckCarNumOrder checkCarNumOrder);

	CBPCheckCarNumOrder getCheckCarNumByOrderId(Integer orderid);
}