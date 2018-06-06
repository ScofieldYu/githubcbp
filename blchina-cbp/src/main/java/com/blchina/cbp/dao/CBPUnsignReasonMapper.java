package com.blchina.cbp.dao;

import com.blchina.cbp.model.CBPUnsignReason;

public interface CBPUnsignReasonMapper {
    int deleteByPrimaryKey(Integer unsignreasonid);

    int insert(CBPUnsignReason record);

    int insertSelective(CBPUnsignReason record);

    CBPUnsignReason selectByPrimaryKey(Integer unsignreasonid);

    int updateByPrimaryKeySelective(CBPUnsignReason record);

    int updateByPrimaryKey(CBPUnsignReason record);

    CBPUnsignReason getUnsignReasonByContractId(Integer contractid);
}