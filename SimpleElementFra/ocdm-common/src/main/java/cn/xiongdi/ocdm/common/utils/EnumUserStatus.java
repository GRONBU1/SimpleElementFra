package cn.xiongdi.ocdm.common.utils;

/**
 * 用户状态枚举
 */
public enum EnumUserStatus {

    FORBIDDEN("0", "禁用"),
    NORMAL("1", "正常"),
    LOCKING("2", "登录失败锁定"),
    USER_INVALID("3", "用户失效"),
    PASSWORD_INVALID("4", "密码失效"),
    INFORMATION_TAMPERING("5", "信息篡改"),
    MODIFY_PASSWORD("6", "初次登录修改密码"),
    UNLOCKING_MODIFY_PASSWORD("7", "已解锁修改密码"),
    OTHER("8", "其他"),
    MANUAL_LOCKING("9", "人工锁定");

    String value;
    String name;

    EnumUserStatus(String value, String name) {
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
        EnumUserStatus[] listEnum = values();
        for (EnumUserStatus oneEnum : listEnum) {
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
        EnumUserStatus[] listEnum = values();
        for (EnumUserStatus oneEnum : listEnum) {
            if (oneEnum.getValue().equals(value)) {
                return oneEnum.getName();
            }
        }
        return null;
    }

}
