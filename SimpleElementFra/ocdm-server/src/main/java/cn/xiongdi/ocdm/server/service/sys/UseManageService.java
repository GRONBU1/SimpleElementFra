package cn.xiongdi.ocdm.server.service.sys;

import cn.xiongdi.ocdm.mapper.model.SysOrganization;
import cn.xiongdi.ocdm.mapper.model.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @创建人 粟磊
 * @创建时间 2018/11/01
 * @描述 UseManage Service接口，提供controller操作所需方法
 */
public interface UseManageService {


    /**
     * 获取用户信息
     *
     * @param orderColName 排序的字段
     * @param order        排序规则
     * @param yhm          用户名
     * @return 用户信息
     */
    List<SysUser> selectByUserList(String orderColName, String order, String yhm, Long ssdw);

    /**
     * 获取用户信息
     *
     * @param userInfo 用户ID
     * @return 用户信息
     */
    SysUser selectByUserId(SysUser userInfo);

    /**
     * 更新用户信息
     *
     * @param userInfo 用户信息
     * @return Map
     */
    Map<String, Object> updateUserInfo(SysUser userInfo);

    /**
     * 删除用户信息
     *
     * @param userInfo 用户信息
     * @return Map
     */
    Map<String, Object> delUserInfo(SysUser userInfo);


    Map<String, Object> getSysOrganizationTree(SysOrganization sysOrganization);

    /**
     * 获取角色（POST）
     *
     * @return 角色
     */
    Map<String, Object> getRoleInfo();

    /**
     * 添加用户信息
     *
     * @param userInfo 用户信息
     * @return Map
     */
    Map<String, Object> saveUserInfo(SysUser userInfo);

    /**
     * 判断用户名是否存在
     *
     * @param userInfo 用户名
     * @return 0：不存在； 否则存在
     */
    int selectYhmCheck(SysUser userInfo);


}
