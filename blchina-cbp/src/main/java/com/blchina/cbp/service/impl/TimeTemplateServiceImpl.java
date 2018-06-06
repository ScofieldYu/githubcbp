/*
 * @(#)TimeTemplateServiceImpl.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.impl;

import com.blchina.cbp.dao.CBPTimePeriodMapper;
import com.blchina.cbp.model.CBPTimePeriod;
import com.blchina.cbp.service.interfaces.TimeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *时间设置service
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("TimeTemplateService")
public class TimeTemplateServiceImpl implements TimeTemplateService {
    @Autowired
    private CBPTimePeriodMapper  cbpTimePeriodMapper;
    @Override
    public List<CBPTimePeriod> getTimePeriodByConfig(Integer timeconfid) {
        return cbpTimePeriodMapper.getTimePeriodByConfig(timeconfid);
    }

    @Override
    public void inTemplatePeriod(CBPTimePeriod period) {
        cbpTimePeriodMapper.insert(period);
    }

    @Override
    public int updateTemplatePeriod(CBPTimePeriod cbpTimePeriod) {
        return cbpTimePeriodMapper.updateByPrimaryKeySelective(cbpTimePeriod);
    }

    @Override
    public void deleteShopTemplateByshop(Integer timeConfId) {
        cbpTimePeriodMapper.deleteShopTemplateByshop(timeConfId);
    }
}
