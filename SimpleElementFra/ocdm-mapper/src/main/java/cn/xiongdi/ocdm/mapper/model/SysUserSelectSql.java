package cn.xiongdi.ocdm.mapper.model;

import java.io.Serializable;
import java.util.Date;

public class SysUserSelectSql implements Serializable {
    private Long id;

    private String yhm;

    private String xzxm;

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

    public String getXzxm() {
        return xzxm;
    }

    public void setXzxm(String xzxm) {
        this.xzxm = xzxm == null ? null : xzxm.trim();
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
        sb.append(", xzxm=").append(xzxm);
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