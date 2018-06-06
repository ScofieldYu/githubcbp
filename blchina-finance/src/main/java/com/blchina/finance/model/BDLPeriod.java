package com.blchina.finance.model;

import java.util.List;

public class BDLPeriod {
    private Integer periodid;

    private Integer bankid;
    
    private String bankname;

    private String periodvalue;

    private String yearaccrual;
    

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public Integer getPeriodid() {
        return periodid;
    }

    public void setPeriodid(Integer periodid) {
        this.periodid = periodid;
    }

    public Integer getBankid() {
        return bankid;
    }

    public void setBankid(Integer bankid) {
        this.bankid = bankid;
    }
    
	public String getPeriodvalue() {
		return periodvalue;
	}

	public void setPeriodvalue(String periodvalue) {
		this.periodvalue = periodvalue;
	}

	public String getYearaccrual() {
        return yearaccrual;
    }

    public void setYearaccrual(String yearaccrual) {
        this.yearaccrual = yearaccrual == null ? null : yearaccrual.trim();
    }
}