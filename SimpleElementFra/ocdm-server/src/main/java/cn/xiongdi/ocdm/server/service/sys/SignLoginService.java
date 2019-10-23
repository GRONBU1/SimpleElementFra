package cn.xiongdi.ocdm.server.service.sys;


import cn.xiongdi.ocdm.server.dto.LoginDto;
import cn.xiongdi.ocdm.server.dto.PasswordDto;
import cn.xiongdi.ocdm.server.dto.ResultMap;
import cn.xiongdi.ocdm.server.dto.UserInfoDto;

import javax.servlet.http.HttpServletRequest;

/**
 * @创建人 lzy
 * @创建时间 2018-09-11
 * @描述 登录
 */
public interface SignLoginService {

    /**
     * 登录
     *
     * @param loginDto 用户密码对象
     * @return 结果Map
     * @parm request  请求对象
     */
    ResultMap login(HttpServletRequest request, LoginDto loginDto);

    /**
     * 密码重置
     *
     * @param request       请求对象
     * @param userID        用户名
     * @param clientIp      IP
     * @param passwordInput 密码对象
     * @return 结果Map
     */
    ResultMap resetPassword(HttpServletRequest request, String userID, String clientIp, PasswordDto passwordInput);

     /**
     * 更新用户状态
     *
     * @param userId 用户名
     * @param status 状态
     */
    void updateUserStatus(String userId, String status);


    /**
     * 获取菜单
     *
     * @param request 请求对象
     * @return 菜单Map
     */
    ResultMap getMenuTreeByRole(HttpServletRequest request);

    /**
     * 注销
     *
     * @param request
     */
    ResultMap loginOut(HttpServletRequest request);

    /**
     * 是否存在权限
     *
     * @param request
     * @param url
     * @return True：有权限；False：无权限
     */
    boolean hasPermission(HttpServletRequest request, String url);


}
