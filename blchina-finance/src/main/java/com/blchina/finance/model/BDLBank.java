package com.blchina.finance.model;

import java.util.List;

import net.sf.json.JSONArray;

public class BDLBank {
    private Integer bankid;

    private String bankname;
    
    private List<BDLPeriod> bdlPeriodList;
    

    public List<BDLPeriod> getBdlPeriodList() {
		return  JSONArray.fromObject( bdlPeriodList );
	}

	public void setBdlPeriodList(List<BDLPeriod> bdlPeriodList) {
		this.bdlPeriodList = JSONArray.fromObject( bdlPeriodList );
	}

	public Integer getBankid() {
        return bankid;
    }

    public void setBankid(Integer bankid) {
        this.bankid = bankid;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname == null ? null : bankname.trim();
    }
}