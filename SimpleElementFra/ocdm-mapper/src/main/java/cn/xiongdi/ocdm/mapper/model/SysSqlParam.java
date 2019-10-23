package cn.xiongdi.ocdm.mapper.model;

import java.io.Serializable;

public class SysSqlParam implements Serializable {
    private Long id;

    private String xszdm;

    private String wlzdm;

    private String zdlx;

    private String zdzz;

    private String jsbs;

    private Long sort;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getXszdm() {
        return xszdm;
    }

    public void setXszdm(String xszdm) {
        this.xszdm = xszdm == null ? null : xszdm.trim();
    }

    public String getWlzdm() {
        return wlzdm;
    }

    public void setWlzdm(String wlzdm) {
        this.wlzdm = wlzdm == null ? null : wlzdm.trim();
    }

    public String getZdlx() {
        return zdlx;
    }

    public void setZdlx(String zdlx) {
        this.zdlx = zdlx == null ? null : zdlx.trim();
    }

    public String getZdzz() {
        return zdzz;
    }

    public void setZdzz(String zdzz) {
        this.zdzz = zdzz == null ? null : zdzz.trim();
    }

    public String getJsbs() {
        return jsbs;
    }

    public void setJsbs(String jsbs) {
        this.jsbs = jsbs == null ? null : jsbs.trim();
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", xszdm=").append(xszdm);
        sb.append(", wlzdm=").append(wlzdm);
        sb.append(", zdlx=").append(zdlx);
        sb.append(", zdzz=").append(zdzz);
        sb.append(", jsbs=").append(jsbs);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}