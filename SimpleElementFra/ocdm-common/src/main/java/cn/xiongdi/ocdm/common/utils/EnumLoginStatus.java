package cn.xiongdi.ocdm.common.utils;

/**
 * 用户状态枚举
 */
public enum EnumLoginStatus {

    DELETE("0", "用户删除"),
    NORMAL("1", "成功"),
    USER_INVALID("2", "用户失效"),
    PASSWORD_INVALID("3", "密码失效"),
    IP_INVALID("4", "IP不合规定"),
    INFORMATION_TAMPERING("5", "信息篡改"),
    IP_LOCK("6", "终端IP锁定"),
    NOT_IN_TIME("7","不在登陆时间段内"),
    IP_NOT_ACCESS("8","IP不允许登录"),
    LOCKING("9", "用户锁定"),
    OTHER_STATUS("10", "其他状态"),
    PASSWORD_ERROR("11", "密码输入错误"),
    LOGIN_OUT("12", "退出登录");
    String value;
    String name;

    EnumLoginStatus(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 获取状态值
     *
     * @return 返回状态
     */
    public String getValue() {
        return value;
    }

    /**
     * 获取名称
     *
     * @return 返回名称
     */
    public String getName() {
        return name;
    }

    /**
     * 根据ID取得ID
     *
     * @return 返回ID
     */
    public static String getIdById(String value) {
        EnumLoginStatus[] listEnum = values();
        for (EnumLoginStatus oneEnum : listEnum) {
            if (oneEnum.getValue().equals(value)) {
                return oneEnum.getValue();
            }
        }
        return null;
    }

    /**
     * 根据ID取得名称
     *
     * @return 返回名称
     */
    public static String getNameById(String value) {
        EnumLoginStatus[] listEnum = values();
        for (EnumLoginStatus oneEnum : listEnum) {
            if (oneEnum.getValue().equals(value)) {
                return oneEnum.getName();
            }
        }
        return null;
    }

}
