package com.blchina.cbp.model;

public class CBPCheckCarNumOrder {
    private Integer checkcarnumid;

    private Integer orderid;

    private String buytax;

    private String buytaxvalue;

    private String tempplate;

    private String tempplatevalue;

    private String checkcar;

    private String checkcarvalue;

    private String reservecheckcartime;

    private Integer tempplatecount;

    private String status;
    
    private String chooseType;
    
    private String chooseName;
    
    private Integer sumValues;
    

	public Integer getSumValues() {
		return sumValues;
	}

	public void setSumValues(Integer sumValues) {
		this.sumValues = sumValues;
	}

	public String getChooseType() {
		return chooseType;
	}

	public void setChooseType(String chooseType) {
		this.chooseType = chooseType;
	}

	public String getChooseName() {
		return chooseName;
	}

	public void setChooseName(String chooseName) {
		this.chooseName = chooseName;
	}
    
	public Integer getCheckcarnumid() {
        return checkcarnumid;
    }

    public void setCheckcarnumid(Integer checkcarnumid) {
        this.checkcarnumid = checkcarnumid;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getBuytax() {
        return buytax;
    }

    public void setBuytax(String buytax) {
        this.buytax = buytax == null ? null : buytax.trim();
    }

    public String getBuytaxvalue() {
        return buytaxvalue;
    }

    public void setBuytaxvalue(String buytaxvalue) {
        this.buytaxvalue = buytaxvalue == null ? null : buytaxvalue.trim();
    }

    public String getTempplate() {
        return tempplate;
    }

    public void setTempplate(String tempplate) {
        this.tempplate = tempplate == null ? null : tempplate.trim();
    }

    public String getTempplatevalue() {
        return tempplatevalue;
    }

    public void setTempplatevalue(String tempplatevalue) {
        this.tempplatevalue = tempplatevalue == null ? null : tempplatevalue.trim();
    }

    public String getCheckcar() {
        return checkcar;
    }

    public void setCheckcar(String checkcar) {
        this.checkcar = checkcar == null ? null : checkcar.trim();
    }

    public String getCheckcarvalue() {
        return checkcarvalue;
    }

    public void setCheckcarvalue(String checkcarvalue) {
        this.checkcarvalue = checkcarvalue == null ? null : checkcarvalue.trim();
    }

    public String getReservecheckcartime() {
        return reservecheckcartime;
    }

    public void setReservecheckcartime(String reservecheckcartime) {
        this.reservecheckcartime = reservecheckcartime == null ? null : reservecheckcartime.trim();
    }

    public Integer getTempplatecount() {
        return tempplatecount;
    }

    public void setTempplatecount(Integer tempplatecount) {
        this.tempplatecount = tempplatecount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}