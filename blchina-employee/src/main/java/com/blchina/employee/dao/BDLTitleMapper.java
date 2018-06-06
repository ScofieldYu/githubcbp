package com.blchina.employee.dao;

import org.springframework.stereotype.Service;

import com.blchina.employee.model.BDLTitle;
@Service
public interface BDLTitleMapper {
    int deleteByPrimaryKey(Integer titleid);

    int insert(BDLTitle record);

    int insertSelective(BDLTitle record);

    BDLTitle selectByPrimaryKey(Integer titleid);

    int updateByPrimaryKeySelective(BDLTitle record);

    int updateByPrimaryKey(BDLTitle record);
}