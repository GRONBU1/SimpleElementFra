package cn.xiongdi.ocdm.mapper.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SysMenu implements Serializable {
    private Long menuid;

    private String cdbh;

    private String mc;

    private Long cdjb;

    private Long sjcdid;

    private String url;

    private String apiurl;

    private String pic;

    private String sfqy;

    private String bz;

    private String sjcdmc;

    private String jxjy;

    private String hxgn;

    private String fcgyw;

    private String ssjslb;

    private Long sortnumber;

    private Date createtime;

    private String createuid;

    private String createip;

    private Date updatetime;

    private String updateuid;

    private String updateip;

    private List<SysMenu> children;

    private static final long serialVersionUID = 1L;

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }


    public Long getMenuid() {
        return menuid;
    }

    public void setMenuid(Long menuid) {
        this.menuid = menuid;
    }

    public String getCdbh() {
        return cdbh;
    }

    public void setCdbh(String cdbh) {
        this.cdbh = cdbh == null ? null : cdbh.trim();
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc == null ? null : mc.trim();
    }

    public Long getCdjb() {
        return cdjb;
    }

    public void setCdjb(Long cdjb) {
        this.cdjb = cdjb;
    }

    public Long getSjcdid() {
        return sjcdid;
    }

    public void setSjcdid(Long sjcdid) {
        this.sjcdid = sjcdid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getSfqy() {
        return sfqy;
    }

    public void setSfqy(String sfqy) {
        this.sfqy = sfqy == null ? null : sfqy.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getSjcdmc() {
        return sjcdmc;
    }

    public void setSjcdmc(String sjcdmc) {
        this.sjcdmc = sjcdmc == null ? null : sjcdmc.trim();
    }

    public String getJxjy() {
        return jxjy;
    }

    public void setJxjy(String jxjy) {
        this.jxjy = jxjy == null ? null : jxjy.trim();
    }

    public String getHxgn() {
        return hxgn;
    }

    public void setHxgn(String hxgn) {
        this.hxgn = hxgn == null ? null : hxgn.trim();
    }

    public String getFcgyw() {
        return fcgyw;
    }

    public void setFcgyw(String fcgyw) {
        this.fcgyw = fcgyw == null ? null : fcgyw.trim();
    }

    public String getSsjslb() {
        return ssjslb;
    }

    public void setSsjslb(String ssjslb) {
        this.ssjslb = ssjslb == null ? null : ssjslb.trim();
    }

    public Long getSortnumber() {
        return sortnumber;
    }

    public void setSortnumber(Long sortnumber) {
        this.sortnumber = sortnumber;
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

    public String getApiurl() {
        return apiurl;
    }

    public void setApiurl(String apiurl) {
        this.apiurl = apiurl;
    }
}