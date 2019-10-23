package cn.xiongdi.ocdm.mapper.model;

import java.io.Serializable;

public class LoginLogRq implements Serializable {
    private String dlip;
    private String dlyhm;
    private String order;

    public String getDlip() {
        return dlip;
    }

    public void setDlip(String dlip) {
        this.dlip = dlip;
    }

    public String getDlyhm() {
        return dlyhm;
    }

    public void setDlyhm(String dlyhm) {
        this.dlyhm = dlyhm;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
    private  int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
    private int rows;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
