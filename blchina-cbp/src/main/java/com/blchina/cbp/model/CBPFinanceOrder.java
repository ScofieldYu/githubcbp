package com.blchina.cbp.model;

public class CBPFinanceOrder {
    private Integer financeorderid;

    private Integer totalloan;

    private String bankname;

    private String fee;

    private String periodnum;

    private String firstpay;

    private String bankid;

    private String periodid;
    
    private Integer orderid;
    
    private String reservetime;
    
    private String status;
    
    
    
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReservetime() {
		return reservetime;
	}

	public void setReservetime(String reservetime) {
		this.reservetime = reservetime;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Integer getFinanceorderid() {
        return financeorderid;
    }

    public void setFinanceorderid(Integer financeorderid) {
        this.financeorderid = financeorderid;
    }

    public Integer getTotalloan() {
        return totalloan;
    }

    public void setTotalloan(Integer totalloan) {
        this.totalloan = totalloan;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname == null ? null : bankname.trim();
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee == null ? null : fee.trim();
    }

    public String getPeriodnum() {
        return periodnum;
    }

    public void setPeriodnum(String periodnum) {
        this.periodnum = periodnum == null ? null : periodnum.trim();
    }

    public String getFirstpay() {
        return firstpay;
    }

    public void setFirstpay(String firstpay) {
        this.firstpay = firstpay == null ? null : firstpay.trim();
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid == null ? null : bankid.trim();
    }

    public String getPeriodid() {
        return periodid;
    }

    public void setPeriodid(String periodid) {
        this.periodid = periodid == null ? null : periodid.trim();
    }
}