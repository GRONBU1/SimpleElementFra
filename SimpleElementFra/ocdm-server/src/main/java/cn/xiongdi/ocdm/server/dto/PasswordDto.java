package cn.xiongdi.ocdm.server.dto;

import java.io.Serializable;

/**
 * @创建人 lzy
 * @创建时间 2018-09-11
 * @描述 修改密码信息
 */
public class PasswordDto {
    private String username;
    private String oldpassowrd;
    private String password2;
    private String password1;

    public String getUsername() {
        return username;
    }

    public String getPassword2() {
        return password2;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getOldpassowrd() {
        return oldpassowrd;
    }

    public void setOldpassowrd(String oldpassowrd) {
        this.oldpassowrd = oldpassowrd;
    }
}