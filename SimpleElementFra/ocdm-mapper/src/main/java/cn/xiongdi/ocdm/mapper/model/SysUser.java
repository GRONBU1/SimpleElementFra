package cn.xiongdi.ocdm.mapper.model;

import cn.xiongdi.ocdm.common.utils.Utils;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SysUser implements Serializable {
    private String userid;

    private String yhm;

    private String mm;

    private String xm;

    private String sfzh;

    private String lxdh;

    private Long jsbhid;

    private String iplist;

    private String zhmm;

    private String yhbh;

    private String yhzt;

    private String tyyy;

    private String sfjy;

    private Date yhyxq;

    private Date mmyxq;

    private String dlsjks;

    private String dlsjdjs;

    private String dlzt;

    private Date xtsj;

    private String dlip;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date scdlsj;

    private String dlsbcs;

    private Long yhdlcs;

    private String bz2;

    private String bz3;

    private String jsmc;

    private String xb;

    private Long deleteflg;

    private Long ssdw;

    private String sjhm;

    private String email;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    private String createuid;

    private String createip;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    private String updateuid;

    private String updateip;

    private String orderBy;

    private String encryptkey;

    private String jslb;

    private String dwmc;

    private Integer pagesize;

    private Integer startindex;

    private Integer zxyhsl;

    private String his_dlsj;

    private String his_yhzt;

    private String his_dlip;

    private Integer now_yhsxday;

    private Integer now_mmsxday;

    private List<String> listPermission;

    private Integer jc;

    private Integer dns;

    private Integer zk;

    private Integer zb;

    private Integer ysz;

    private Integer esz;

    private Integer cj;

    private Integer spz;

    private Integer hzzs;

    private Integer xjdwhzzs;

    private Integer yxhz;

    private Integer sxhz;

    private String spglqx;

    private String zjglqx;

    private String zjzbqx;

    private String cjglqx;

    private String nsglqx;

    private String spglqx2;

    private static final long serialVersionUID = 1L;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getYhm() {
        return yhm;
    }

    public void setYhm(String yhm) {
        this.yhm = yhm == null ? null : yhm.trim();
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm == null ? null : mm.trim();
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm == null ? "" : xm.trim();
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh == null ? "" : sfzh.trim();
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh == null ? "" : lxdh.trim();
    }

    public Long getJsbhid() {
        return jsbhid;
    }

    public void setJsbhid(Long jsbhid) {
        this.jsbhid = jsbhid;
    }

    public String getIplist() {
        return iplist;
    }

    public void setIplist(String iplist) {
        this.iplist = iplist == null ? null : iplist.trim();
    }

    public String getZhmm() {
        return zhmm;
    }

    public void setZhmm(String zhmm) {
        this.zhmm = zhmm == null ? null : zhmm.trim();
    }

    public String getYhbh() {
        return yhbh;
    }

    public void setYhbh(String yhbh) {
        this.yhbh = yhbh == null ? null : yhbh.trim();
    }

    public String getYhzt() {
        return yhzt;
    }

    public void setYhzt(String yhzt) {
        this.yhzt = yhzt == null ? null : yhzt.trim();
    }

    public String getTyyy() {
        return tyyy;
    }

    public void setTyyy(String tyyy) {
        this.tyyy = tyyy == null ? null : tyyy.trim();
    }

    public String getSfjy() {
        return sfjy;
    }

    public void setSfjy(String sfjy) {
        this.sfjy = sfjy == null ? null : sfjy.trim();
    }

    public Date getYhyxq() {
        return yhyxq;
    }

    public void setYhyxq(Date yhyxq) {
        this.yhyxq = yhyxq;
    }

    public Date getMmyxq() {
        return mmyxq;
    }

    public void setMmyxq(Date mmyxq) {
        this.mmyxq = mmyxq;
    }

    public String getDlsjks() {
        return dlsjks;
    }

    public void setDlsjks(String dlsjks) {
        this.dlsjks = dlsjks == null ? null : dlsjks.trim();
    }

    public String getDlsjdjs() {
        return dlsjdjs;
    }

    public void setDlsjdjs(String dlsjdjs) {
        this.dlsjdjs = dlsjdjs == null ? null : dlsjdjs.trim();
    }

    public String getDlzt() {
        return dlzt;
    }

    public void setDlzt(String dlzt) {
        this.dlzt = dlzt == null ? null : dlzt.trim();
    }

    public Date getXtsj() {
        return xtsj;
    }

    public void setXtsj(Date xtsj) {
        this.xtsj = xtsj;
    }

    public String getDlip() {
        return dlip;
    }

    public void setDlip(String dlip) {
        this.dlip = dlip == null ? null : dlip.trim();
    }

    public Date getScdlsj() {
        return scdlsj;
    }

    public void setScdlsj(Date scdlsj) {
        this.scdlsj = scdlsj;
    }

    public String getDlsbcs() {
        return dlsbcs;
    }

    public void setDlsbcs(String dlsbcs) {
        this.dlsbcs = dlsbcs == null ? null : dlsbcs.trim();
    }

    public Long getYhdlcs() {
        return yhdlcs;
    }

    public void setYhdlcs(Long yhdlcs) {
        this.yhdlcs = yhdlcs;
    }

    public String getBz2() {
        return bz2;
    }

    public void setBz2(String bz2) {
        this.bz2 = bz2 == null ? null : bz2.trim();
    }

    public String getBz3() {
        return bz3;
    }

    public void setBz3(String bz3) {
        this.bz3 = bz3 == null ? null : bz3.trim();
    }

    public String getJsmc() {
        return jsmc;
    }

    public void setJsmc(String jsmc) {
        this.jsmc = jsmc == null ? null : jsmc.trim();
    }

    public Long getDeleteflg() {
        return deleteflg;
    }

    public void setDeleteflg(Long deleteflg) {
        this.deleteflg = deleteflg;
    }

    public Long getSsdw() {
        return ssdw;
    }

    public void setSsdw(Long ssdw) {
        this.ssdw = ssdw;
    }

    public String getSjhm() {
        return sjhm;
    }

    public void setSjhm(String sjhm) {
        this.sjhm = sjhm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getEncryptkey() {
        return encryptkey;
    }

    public void setEncryptkey(String encryptkey) {
        this.encryptkey = encryptkey;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getStartindex() {
        return startindex;
    }

    public void setStartindex(Integer startindex) {
        this.startindex = startindex;
    }

    public Integer getZxyhsl() {
        return zxyhsl;
    }

    public void setZxyhsl(Integer zxyhsl) {
        this.zxyhsl = zxyhsl;
    }

    public String getJslb() {
        return jslb;
    }

    public void setJslb(String jslb) {
        this.jslb = jslb == null ? null : jslb.trim();
    }

    public String getHis_dlsj() {
        return his_dlsj;
    }

    public void setHis_dlsj(String his_dlsj) {
        this.his_dlsj = his_dlsj;
    }

    public String getHis_yhzt() {
        return his_yhzt;
    }

    public void setHis_yhzt(String his_yhzt) {
        this.his_yhzt = his_yhzt == null ? null : his_yhzt.trim();
    }

    public String getHis_dlip() {
        return his_dlip;
    }

    public void setHis_dlip(String his_dlip) {
        this.his_dlip = his_dlip == null ? null : his_dlip.trim();
    }

    public Integer getNow_yhsxday() {
        return now_yhsxday;
    }

    public void setNow_yhsxday(Integer now_yhsxday) {
        this.now_yhsxday = now_yhsxday;
    }

    public Integer getNow_mmsxday() {
        return now_mmsxday;
    }

    public void setNow_mmsxday(Integer now_mmsxday) {
        this.now_mmsxday = now_mmsxday;
    }

    public Integer getJc() {
        return jc;
    }

    public void setJc(Integer jc) {
        this.jc = jc;
    }

    public Integer getDns() {
        return dns;
    }

    public void setDns(Integer dns) {
        this.dns = dns;
    }

    public Integer getZk() {
        return zk;
    }

    public void setZk(Integer zk) {
        this.zk = zk;
    }

    public Integer getZb() {
        return zb;
    }

    public void setZb(Integer zb) {
        this.zb = zb;
    }

    public Integer getYsz() {
        return ysz;
    }

    public void setYsz(Integer ysz) {
        this.ysz = ysz;
    }

    public Integer getEsz() {
        return esz;
    }

    public void setEsz(Integer esz) {
        this.esz = esz;
    }

    public Integer getCj() {
        return cj;
    }

    public void setCj(Integer cj) {
        this.cj = cj;
    }

    public Integer getSpz() {
        return spz;
    }

    public void setSpz(Integer spz) {
        this.spz = spz;
    }

    public Integer getHzzs() {
        return hzzs;
    }

    public void setHzzs(Integer hzzs) {
        this.hzzs = hzzs;
    }

    public Integer getYxhz() {
        return yxhz;
    }

    public void setYxhz(Integer yxhz) {
        this.yxhz = yxhz;
    }

    public Integer getSxhz() {
        return sxhz;
    }

    public void setSxhz(Integer sxhz) {
        this.sxhz = sxhz;
    }

    public String getSpglqx() {
        return spglqx;
    }

    public void setSpglqx(String spglqx) {
        this.spglqx = spglqx;
    }

    public String getZjglqx() {
        return zjglqx;
    }

    public void setZjglqx(String zjglqx) {
        this.zjglqx = zjglqx;
    }

    public String getZjzbqx() {
        return zjzbqx;
    }

    public void setZjzbqx(String zjzbqx) {
        this.zjzbqx = zjzbqx;
    }

    public String getCjglqx() {
        return cjglqx;
    }

    public void setCjglqx(String cjglqx) {
        this.cjglqx = cjglqx;
    }

    public String getNsglqx() {
        return nsglqx;
    }

    public void setNsglqx(String nsglqx) {
        this.nsglqx = nsglqx;
    }

    public String getSpglqx2() {
        return spglqx2;
    }

    public void setSpglqx2(String spglqx2) {
        this.spglqx2 = spglqx2;
    }

    public Integer getXjdwhzzs() {
        return xjdwhzzs;
    }

    public void setXjdwhzzs(Integer xjdwhzzs) {
        this.xjdwhzzs = xjdwhzzs;
    }

    public String makeUserVerCode() {
        // 联系电话
        String strLxdh = this.lxdh == null ? "" : this.lxdh;
        // 允许登录IP
        String strIplist = this.iplist == null ? "" : this.iplist;

        String userVerCode = this.yhm
                + "|_|" + this.xm
                + "|_|" + this.mm
                + "|_|" + this.sfzh
                + "|_|" + strLxdh
                + "|_|" + this.jsbhid
                + "|_|" + strIplist
                + "|_|" + this.yhbh
                + "|_|" + this.yhzt
                + "|_|" + this.sfjy
                + "|_|" + this.yhyxq
                + "|_|" + this.mmyxq
                + "|_|" + this.dlsjks
                + "|_|" + this.dlsjdjs
                + "|_|" + this.deleteflg.toString();

        return userVerCode;
    }

    public String getPassWord(String pws) {
        // 密码
        String passWord = this.userid + pws + this.xm;

        return passWord;
    }

    public String toStringExcludeBlob() {
        return "SYS_USER [用户编号=" + this.userid + ", 用户=" + this.yhm + ", 密码=" + this.mm +
                ", 姓名=" + Utils.getSHA256Str(this.xm) + ", 身份证号=" + Utils.getSHA256Str(this.sfzh) +
                ", 联系电话=" + Utils.getSHA256Str(this.lxdh) + ", 角色编号=" + this.jsbhid +
                ", 创建时间=" + this.createtime + ", 更新时间=" + this.updatetime +
                ", 允许登录IP=" + this.iplist + ", 用户验证码=" + this.zhmm + ", 用户状态=" + this.yhzt +
                ", 停用原因=" + this.tyyy + ", 是否警员=" + this.sfjy + ", 用户有效期=" + this.yhyxq +
                ", 密码有效期=" + this.mmyxq + ", 登录开始时间=" + this.dlsjks + ", 登录结束时间=" + this.dlsjdjs +
                ", 登录状态=" + this.dlzt + ", 系统时间=" + this.xtsj + ", 登录IP=" + this.dlip +
                ", 上次登录时间=" + this.scdlsj + ", 登录失败次数=" + this.dlsbcs + ", 用户登录次数=" + this.yhdlcs +
                ", 备注2=" + this.bz2 + ", 备注3=" + this.bz3 + ", 角色名称=" + this.jsmc + ", 用户编号=" + this.yhbh + "]";
    }

    public List<String> getListPermission() {
        return listPermission;
    }

    public void setListPermission(List<String> listPermission) {
        this.listPermission = listPermission;
    }
}