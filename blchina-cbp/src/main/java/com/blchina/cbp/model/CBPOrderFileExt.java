package com.blchina.cbp.model;

public class CBPOrderFileExt {
    private Integer orderextfileid;

    private Integer orderid;

    private String filetype;

    private String fileuuid;

    private String status;

    private String createtime;

    private String filetypename;

    private String filetypeext;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFiletypeext() {
        return filetypeext;
    }

    public void setFiletypeext(String filetypeext) {
        this.filetypeext = filetypeext;
    }

    public Integer getOrderextfileid() {
        return orderextfileid;
    }

    public void setOrderextfileid(Integer orderextfileid) {
        this.orderextfileid = orderextfileid;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype == null ? null : filetype.trim();
    }

    public String getFileuuid() {
        return fileuuid;
    }

    public void setFileuuid(String fileuuid) {
        this.fileuuid = fileuuid == null ? null : fileuuid.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getFiletypename() {
        return filetypename;
    }

    public void setFiletypename(String filetypename) {
        this.filetypename = filetypename == null ? null : filetypename.trim();
    }
}