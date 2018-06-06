package com.blchina.cbp.dao;
import org.springframework.stereotype.Service;
import com.blchina.cbp.dto.SignMonitorDTO;
import com.blchina.cbp.model.CBPTimeConf;
@Service
public interface CBPTimeConfMapper {
    int deleteByPrimaryKey(Integer timeconfid);

    int insert(CBPTimeConf record);

    int insertSelective(CBPTimeConf record);

    CBPTimeConf selectByPrimaryKey(Integer timeconfid);

    int updateByPrimaryKeySelective(CBPTimeConf record);

    int updateByPrimaryKey(CBPTimeConf record);

    CBPTimeConf getShopConfig(Integer shop);

    CBPTimeConf getEveryContractCountAndPrice(SignMonitorDTO smdto);
}