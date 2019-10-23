package cn.xiongdi.ocdm.mapper.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class LogLogin implements Serializable {
    private Long id;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dlsj;

    private String dlip;

    private String dlmac;

    private Long dlzt;

    private String bz;

    private String dlyhm;

    private String dllx;

    private String dljg;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    private String createuid;

    private String createip;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
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

    public Date getDlsj() {
        return dlsj;
    }

    public void setDlsj(Date dlsj) {
        this.dlsj = dlsj;
    }

    public String getDlip() {
        return dlip;
    }

    public void setDlip(String dlip) {
        this.dlip = dlip == null ? null : dlip.trim();
    }

    public String getDlmac() {
        return dlmac;
    }

    public void setDlmac(String dlmac) {
        this.dlmac = dlmac == null ? null : dlmac.trim();
    }

    public Long getDlzt() {
        return dlzt;
    }

    public void setDlzt(Long dlzt) {
        this.dlzt = dlzt;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getDlyhm() {
        return dlyhm;
    }

    public void setDlyhm(String dlyhm) {
        this.dlyhm = dlyhm == null ? null : dlyhm.trim();
    }

    public String getDllx() {
        return dllx;
    }

    public void setDllx(String dllx) {
        this.dllx = dllx == null ? null : dllx.trim();
    }

    public String getDljg() {
        return dljg;
    }

    public void setDljg(String dljg) {
        this.dljg = dljg == null ? null : dljg.trim();
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