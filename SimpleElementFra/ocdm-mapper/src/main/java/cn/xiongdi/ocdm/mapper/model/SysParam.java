package cn.xiongdi.ocdm.mapper.model;

import java.io.Serializable;
import java.util.Date;

public class SysParam implements Serializable {
    private Long csid;

    private String cslb;

    private String csdh;

    private String cssm;

    private String bz1;

    private String bz2;

    private String lbsm;
    private  Integer editflg;

    private Date createtime;

    private String createuid;

    private String createip;

    private Date updatetime;

    private String updateuid;

    private String updateip;

    private static final long serialVersionUID = 1L;

    public Long getCsid() {
        return csid;
    }

    public void setCsid(Long csid) {
        this.csid = csid;
    }

    public String getCslb() {
        return cslb;
    }

    public void setCslb(String cslb) {
        this.cslb = cslb == null ? null : cslb.trim();
    }

    public String getCsdh() {
        return csdh;
    }

    public void setCsdh(String csdh) {
        this.csdh = csdh == null ? null : csdh.trim();
    }

    public String getCssm() {
        return cssm;
    }

    public void setCssm(String cssm) {
        this.cssm = cssm == null ? null : cssm.trim();
    }

    public String getBz1() {
        return bz1;
    }

    public void setBz1(String bz1) {
        this.bz1 = bz1 == null ? null : bz1.trim();
    }

    public String getBz2() {
        return bz2;
    }

    public void setBz2(String bz2) {
        this.bz2 = bz2 == null ? null : bz2.trim();
    }

    public String getLbsm() {
        return lbsm;
    }

    public void setLbsm(String lbsm) {
        this.lbsm = lbsm == null ? null : lbsm.trim();
    }

    public Integer getEditflg() {
        return editflg;
    }

    public void setEditflg(Integer editflg) {
        this.editflg = editflg;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateuid() {
        return createuid;
    }

    public void setCreateuid(String createuid) {
        this.createuid = createuid == null ? null : createuid.trim();
    }

    public String getCreateip() {
        return createip;
    }

    public void setCreateip(String createip) {
        this.createip = createip == null ? null : createip.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdateuid() {
        return updateuid;
    }

    public void setUpdateuid(String updateuid) {
        this.updateuid = updateuid == null ? null : updateuid.trim();
    }

    public String getUpdateip() {
        return updateip;
    }

    public void setUpdateip(String updateip) {
        this.updateip = updateip == null ? null : updateip.trim();
    }
}