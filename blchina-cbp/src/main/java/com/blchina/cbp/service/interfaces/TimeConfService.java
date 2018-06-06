/*
 * @(#)TimeConfService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.interfaces;

import com.blchina.cbp.dto.TimeConfDTO;
import com.blchina.cbp.model.CBPTimeConf;

/**
 *门店天数设置TimeConfService
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface TimeConfService {
    CBPTimeConf getShopConfig(Integer shop);

    int insertShopConfig(CBPTimeConf shopConfig);

    int updateShopConfig(CBPTimeConf timeConfDTO);

}
