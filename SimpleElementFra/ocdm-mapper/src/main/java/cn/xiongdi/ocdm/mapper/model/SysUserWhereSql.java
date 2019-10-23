package cn.xiongdi.ocdm.mapper.model;

import java.io.Serializable;
import java.util.Date;

public class SysUserWhereSql implements Serializable {
    private Long id;

    private String yhm;

    private String kh;

    private String zd;

    private String tj;

    private String srz;

    private String fkh;

    private String gx;

    private Integer deteleflag;

    private Date createtime;

    private String createuid;

    private String createip;

    private Date updatetime;

    private String updateuid;

    private String updateip;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYhm() {
        return yhm;
    }

    public void setYhm(String yhm) {
        this.yhm = yhm == null ? null : yhm.trim();
    }

    public String getKh() {
        return kh;
    }

    public void setKh(String kh) {
        this.kh = kh == null ? null : kh.trim();
    }

    public String getZd() {
        return zd;
    }

    public void setZd(String zd) {
        this.zd = zd == null ? null : zd.trim();
    }

    public String getTj() {
        return tj;
    }

    public void setTj(String tj) {
        this.tj = tj == null ? null : tj.trim();
    }

    public String getSrz() {
        return srz;
    }

    public void setSrz(String srz) {
        this.srz = srz == null ? null : srz.trim();
    }

    public String getFkh() {
        return fkh;
    }

    public void setFkh(String fkh) {
        this.fkh = fkh == null ? null : fkh.trim();
    }

    public String getGx() {
        return gx;
    }

    public void setGx(String gx) {
        this.gx = gx == null ? null : gx.trim();
    }

    public Integer getDeteleflag() {
        return deteleflag;
    }

    public void setDeteleflag(Integer deteleflag) {
        this.deteleflag = deteleflag;
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
        sb.append(", yhm=").append(yhm);
        sb.append(", kh=").append(kh);
        sb.append(", zd=").append(zd);
        sb.append(", tj=").append(tj);
        sb.append(", srz=").append(srz);
        sb.append(", fkh=").append(fkh);
        sb.append(", gx=").append(gx);
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