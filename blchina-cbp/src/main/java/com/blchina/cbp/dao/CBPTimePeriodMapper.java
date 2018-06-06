package com.blchina.cbp.dao;

import org.springframework.stereotype.Service;

import com.blchina.cbp.model.CBPTimePeriod;

import java.util.List;

@Service
public interface CBPTimePeriodMapper {
    int deleteByPrimaryKey(Integer timeperiodid);

    int insert(CBPTimePeriod record);

    int insertSelective(CBPTimePeriod record);

    CBPTimePeriod selectByPrimaryKey(Integer timeperiodid);

    int updateByPrimaryKeySelective(CBPTimePeriod record);

    int updateByPrimaryKey(CBPTimePeriod record);

    List<CBPTimePeriod> getTimePeriodByConfig(Integer timeconfid);

    void deleteShopTemplateByshop(Integer timeconfid);
}