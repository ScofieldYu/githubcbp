/*
 * @(#)UnsignReasonServiceImpl.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.impl;

import com.blchina.cbp.dao.CBPUnsignReasonMapper;
import com.blchina.cbp.model.CBPUnsignReason;
import com.blchina.cbp.service.interfaces.UnsignReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *UnsignReasonServiceImpl
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("UnsignReasonService")
public class UnsignReasonServiceImpl implements UnsignReasonService {
    @Autowired
    CBPUnsignReasonMapper unsignReasonMapper;
    @Override
    public int saveOrUpdateSignReason(CBPUnsignReason cbpUnsignReason) {
        CBPUnsignReason getUnsignReason=unsignReasonMapper.getUnsignReasonByContractId(cbpUnsignReason.getContractid());
        if(getUnsignReason==null){
            unsignReasonMapper.insert(cbpUnsignReason);
        }else {
            cbpUnsignReason.setUnsignreasonid(getUnsignReason.getUnsignreasonid());
            unsignReasonMapper.updateByPrimaryKeySelective(cbpUnsignReason);
        }
        return 0;
    }
}
