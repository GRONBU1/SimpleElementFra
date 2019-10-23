package cn.xiongdi.ocdm.server.dto;

/**
 * @创建人 lzy
 * @创建时间 2018-09-11
 * @描述 用户确认信息
 */
public class UserInfoDto {
    private String xm;
    private String sfzh;
    private  String yhbh;
    private  String sfjy;

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getYhbh() {
        return yhbh;
    }

    public void setYhbh(String yhbh) {
        this.yhbh = yhbh;
    }

    public String getSfjy() {
        return sfjy;
    }

    public void setSfjy(String sfjy) {
        this.sfjy = sfjy;
    }
}