package cn.xiongdi.ocdm.server.dto;

import java.util.List;

/**
 * @创建人 lzy
 * @创建时间 2018-10-17
 * @描述 菜单结果集
 */
public class MenuDto {
private String menuid;
private String mc;
private String sjcdid;
private String pic;
private String url;
private List<MenuDto> children;

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getSjcdid() {
        return sjcdid;
    }

    public void setSjcdid(String sjcdid) {
        this.sjcdid = sjcdid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MenuDto> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDto> children) {
        this.children = children;
    }
}
