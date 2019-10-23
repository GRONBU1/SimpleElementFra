package cn.xiongdi.ocdm.mapper.model;

import java.io.Serializable;
import java.util.Date;

public class SysRolePermission implements Serializable {
    private Long id;

    private Long jsid;

    private Long menuid;

    private String bz;

    private String qxyzm;

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

    public Long getJsid() {
        return jsid;
    }

    public void setJsid(Long jsid) {
        this.jsid = jsid;
    }

    public Long getMenuid() {
        return menuid;
    }

    public void setMenuid(Long menuid) { this.menuid = menuid ; }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getQxyzm() {
        return qxyzm;
    }

    public void setQxyzm(String qxyzm) {
        this.qxyzm = qxyzm == null ? null : qxyzm.trim();
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