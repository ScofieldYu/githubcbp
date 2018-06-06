package com.blchina.cbp.dao;

import org.springframework.stereotype.Service;

import com.blchina.cbp.model.CBPTimeVisible;

import java.util.List;

@Service
public interface CBPTimeVisibleMapper {
    int deleteByPrimaryKey(Integer timevisibleid);

    int insert(CBPTimeVisible record);

    int insertSelective(CBPTimeVisible record);

    CBPTimeVisible selectByPrimaryKey(Integer timevisibleid);

    int updateByPrimaryKeySelective(CBPTimeVisible record);

    int updateByPrimaryKey(CBPTimeVisible record);

    List<CBPTimeVisible> getVisibleByOrderid(Integer orderid);
}