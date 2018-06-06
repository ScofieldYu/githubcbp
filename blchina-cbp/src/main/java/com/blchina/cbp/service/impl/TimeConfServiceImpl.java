/*
 * @(#)TimeConfServiceImpl.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.impl;

import com.blchina.cbp.dao.CBPTimeConfMapper;
import com.blchina.cbp.model.CBPTimeConf;
import com.blchina.cbp.service.interfaces.TimeConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *门店天数设置TimeConfService
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("TimeConfService")
public class TimeConfServiceImpl implements TimeConfService{
    @Autowired
    private CBPTimeConfMapper  cbpTimeConfMapper;
    /**
     * 获取门店预约天数设置
     * @param shop
     * @return
     */
    @Override
    public CBPTimeConf getShopConfig(Integer shop) {

        return cbpTimeConfMapper.getShopConfig(shop);
    }

    @Override
    public int insertShopConfig(CBPTimeConf shopConfig) {
        return cbpTimeConfMapper.insert(shopConfig);
    }

    @Override
    public int updateShopConfig(CBPTimeConf timeConfDTO) {
        return cbpTimeConfMapper.updateByPrimaryKeySelective(timeConfDTO);
    }
}
