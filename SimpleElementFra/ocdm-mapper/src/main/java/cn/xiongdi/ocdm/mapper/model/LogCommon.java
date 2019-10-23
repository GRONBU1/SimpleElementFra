package cn.xiongdi.ocdm.mapper.model;

import java.io.Serializable;
import java.util.Date;

public class LogCommon implements Serializable {
    private String cdbh;

    private String czyhm;

    private String logtype;

    private String tablenames;

    private String czmc;

    private Date czsj;

    private String bz;

    private String ly;

    private String xw;

    private String jg;

    private String czqsj;

    private String czhsj;

    private Long sfhxgn;

    private Long sfcgyw;

    private String czmk;

    private static final long serialVersionUID = 1L;

    public String getCdbh() {
        return cdbh;
    }

    public void setCdbh(String cdbh) {
        this.cdbh = cdbh == null ? null : cdbh.trim();
    }

    public String getCzyhm() {
        return czyhm;
    }

    public void setCzyhm(String czyhm) {
        this.czyhm = czyhm == null ? null : czyhm.trim();
    }

    public String getLogtype() {
        return logtype;
    }

    public void setLogtype(String logtype) {
        this.logtype = logtype == null ? null : logtype.trim();
    }

    public String getTablenames() {
        return tablenames;
    }

    public void setTablenames(String tablenames) {
        this.tablenames = tablenames == null ? null : tablenames.trim();
    }

    public String getCzmc() {
        return czmc;
    }

    public void setCzmc(String czmc) {
        this.czmc = czmc == null ? null : czmc.trim();
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getLy() {
        return ly;
    }

    public void setLy(String ly) {
        this.ly = ly == null ? null : ly.trim();
    }

    public String getXw() {
        return xw;
    }

    public void setXw(String xw) {
        this.xw = xw == null ? null : xw.trim();
    }

    public String getJg() {
        return jg;
    }

    public void setJg(String jg) {
        this.jg = jg == null ? null : jg.trim();
    }

    public String getCzqsj() {
        return czqsj;
    }

    public void setCzqsj(String czqsj) {
        this.czqsj = czqsj == null ? null : czqsj.trim();
    }

    public String getCzhsj() {
        return czhsj;
    }

    public void setCzhsj(String czhsj) {
        this.czhsj = czhsj == null ? null : czhsj.trim();
    }

    public Long getSfhxgn() {
        return sfhxgn;
    }

    public void setSfhxgn(Long sfhxgn) {
        this.sfhxgn = sfhxgn;
    }

    public Long getSfcgyw() {
        return sfcgyw;
    }

    public void setSfcgyw(Long sfcgyw) {
        this.sfcgyw = sfcgyw;
    }

    public String getCzmk() {
        return czmk;
    }

    public void setCzmk(String czmk) {
        this.czmk = czmk == null ? null : czmk.trim();
    }
}