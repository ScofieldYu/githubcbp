package com.blchina.cbp.model;

public class CBPTimeConf {
    private Integer timeconfid;

    private Integer storeid;

    private String storename;

    private String daynum;
    
    private String paperCost;
    
    private Integer contractCount;

    public Integer getTimeconfid() {
        return timeconfid;
    }

    public void setTimeconfid(Integer timeconfid) {
        this.timeconfid = timeconfid;
    }

    public Integer getStoreid() {
        return storeid;
    }

    public void setStoreid(Integer storeid) {
        this.storeid = storeid;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename == null ? null : storename.trim();
    }

    public String getDaynum() {
        return daynum;
    }

    public void setDaynum(String daynum) {
        this.daynum = daynum == null ? null : daynum.trim();
    }

	
	public String getPaperCost() {
		return paperCost;
	}

	public void setPaperCost(String paperCost) {
		this.paperCost = paperCost;
	}

	public Integer getContractCount() {
		return contractCount;
	}

	public void setContractCount(Integer contractCount) {
		this.contractCount = contractCount;
	}
    
}