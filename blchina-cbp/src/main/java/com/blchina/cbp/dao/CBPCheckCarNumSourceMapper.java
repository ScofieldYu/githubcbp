package com.blchina.cbp.dao;

import java.util.List;

import com.blchina.cbp.model.CBPCheckCarNumOrder;
import com.blchina.cbp.model.CBPCheckCarNumSource;

public interface CBPCheckCarNumSourceMapper {
    int deleteByPrimaryKey(Integer checkcarnumsourceid);

    int insert(CBPCheckCarNumSource record);

    int insertSelective(CBPCheckCarNumSource record);

    CBPCheckCarNumSource selectByPrimaryKey(Integer checkcarnumsourceid);

    int updateByPrimaryKeySelective(CBPCheckCarNumSource record);

    int updateByPrimaryKey(CBPCheckCarNumSource record);

	List<CBPCheckCarNumSource> getInfoByTypeAndNumId(CBPCheckCarNumSource checkCarNumSource);

	int updateSource(CBPCheckCarNumSource checkCarNumSource);

	int updateByOrderId(CBPCheckCarNumOrder checkCarNumOrder);

   List<CBPCheckCarNumSource> selectCheckCarNumSourceListByType(
		 CBPCheckCarNumSource ccns);

   List<CBPCheckCarNumSource> getInfoBySelected(
		CBPCheckCarNumSource checkCarNumSource);


}