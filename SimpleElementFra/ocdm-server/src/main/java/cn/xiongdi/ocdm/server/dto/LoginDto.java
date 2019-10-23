package cn.xiongdi.ocdm.server.dto;

/**
 * @创建人 lzy
 * @创建时间 2018-09-11
 * @描述 登录信息
 */
public class LoginDto {

    private String username;
    private String mypassword;
    private  String userId;
    public String getUsername() {
        return username;
    }

    public String getMypassword() {
        return mypassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMypassword(String mypassword) {
        this.mypassword = mypassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}