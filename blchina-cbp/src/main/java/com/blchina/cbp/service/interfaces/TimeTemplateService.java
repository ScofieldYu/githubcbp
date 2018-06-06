/*
 * @(#)TimeTemplateService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.interfaces;

import com.blchina.cbp.model.CBPTimePeriod;

import java.util.List;

/**
 *时间设置service
 * @author zhuchenglong
 * @since JDK 1.8
 */
public interface TimeTemplateService {
    List<CBPTimePeriod> getTimePeriodByConfig(Integer timeconfid);

    void inTemplatePeriod(CBPTimePeriod period);

    int updateTemplatePeriod(CBPTimePeriod cbpTimePeriod);

    void deleteShopTemplateByshop(Integer storeid);
}
