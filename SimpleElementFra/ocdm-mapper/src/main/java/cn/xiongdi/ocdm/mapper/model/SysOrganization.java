package cn.xiongdi.ocdm.mapper.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class SysOrganization implements Serializable {
    private Long id;

    private String dwlx;

    private String dwbm;

    private String dwmc;

    private String dwjb;

    private String xzqh;

    private Long lsdw;

    private String fzr;

    private String sjhm;

    private String wjbsbid;

    private String dz;

    private String bz;

    private String scbs;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    private String createuid;

    private String createip;

    private Date updatetime;

    private String updateuid;

    private String updateip;

    private String orderBy;

    public String getOrderBy() {
        return orderBy;
    }

    private String workareaStr;

    public String getWorkareaStr() {
        return workareaStr;
    }

    public void setWorkareaStr(String workareaStr) {
        this.workareaStr = workareaStr;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDwlx() {
        return dwlx;
    }

    public void setDwlx(String dwlx) {
        this.dwlx = dwlx == null ? null : dwlx.trim();
    }

    public String getDwbm() {
        return dwbm;
    }

    public void setDwbm(String dwbm) {
        this.dwbm = dwbm == null ? null : dwbm.trim();
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc == null ? null : dwmc.trim();
    }

    public String getDwjb() {
        return dwjb;
    }

    public void setDwjb(String dwjb) {
        this.dwjb = dwjb == null ? null : dwjb.trim();
    }

    public String getXzqh() {
        return xzqh;
    }

    public void setXzqh(String xzqh) {
        this.xzqh = xzqh == null ? null : xzqh.trim();
    }

    public Long getLsdw() {
        return lsdw;
    }

    public void setLsdw(Long lsdw) {
        this.lsdw = lsdw;
    }

    public String getFzr() {
        return fzr;
    }

    public void setFzr(String fzr) {
        this.fzr = fzr == null ? null : fzr.trim();
    }

    public String getSjhm() {
        return sjhm;
    }

    public void setSjhm(String sjhm) {
        this.sjhm = sjhm == null ? null : sjhm.trim();
    }

    public String getWjbsbid() {
        return wjbsbid;
    }

    public void setWjbsbid(String wjbsbid) {
        this.wjbsbid = wjbsbid == null ? null : wjbsbid.trim();
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz == null ? null : dz.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getScbs() {
        return scbs;
    }

    public void setScbs(String scbs) {
        this.scbs = scbs == null ? null : scbs.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", dwlx=").append(dwlx);
        sb.append(", dwbm=").append(dwbm);
        sb.append(", dwmc=").append(dwmc);
        sb.append(", dwjb=").append(dwjb);
        sb.append(", xzqh=").append(xzqh);
        sb.append(", lsdw=").append(lsdw);
        sb.append(", fzr=").append(fzr);
        sb.append(", sjhm=").append(sjhm);
        sb.append(", wjbsbid=").append(wjbsbid);
        sb.append(", dz=").append(dz);
        sb.append(", bz=").append(bz);
        sb.append(", scbs=").append(scbs);
        sb.append(", createtime=").append(createtime);
        sb.append(", createuid=").append(createuid);
        sb.append(", createip=").append(createip);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", updateuid=").append(updateuid);
        sb.append(", updateip=").append(updateip);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}