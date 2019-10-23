package cn.xiongdi.ocdm.mapper.model;

import java.io.Serializable;

public class ParamRq implements Serializable {

    //参数类别
    private String cslb;
    public String getCslb() {
        return cslb;
    }
    public void setCslb(String cslb) {
        this.cslb = cslb;
    }

    //参数代号
    private  String csdh;
    public String getCsdh() {
        return csdh;
    }
    public void setCsdh(String csdh) {
        this.csdh = csdh;
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

    private String cssm;

    public String getCssm() {
        return cssm;
    }

    public void setCssm(String cssm) {
        this.cssm = cssm;
    }
}
