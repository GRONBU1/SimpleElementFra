package cn.xiongdi.ocdm.mapper.model;

import java.io.Serializable;

public class SysAream implements Serializable {
    private String xzqh;

    private String fjxzqh;

    private String qhmc;

    private static final long serialVersionUID = 1L;

    public String getXzqh() {
        return xzqh;
    }

    public void setXzqh(String xzqh) {
        this.xzqh = xzqh == null ? null : xzqh.trim();
    }

    public String getFjxzqh() {
        return fjxzqh;
    }

    public void setFjxzqh(String fjxzqh) {
        this.fjxzqh = fjxzqh == null ? null : fjxzqh.trim();
    }

    public String getQhmc() {
        return qhmc;
    }

    public void setQhmc(String qhmc) {
        this.qhmc = qhmc == null ? null : qhmc.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", xzqh=").append(xzqh);
        sb.append(", fjxzqh=").append(fjxzqh);
        sb.append(", qhmc=").append(qhmc);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}