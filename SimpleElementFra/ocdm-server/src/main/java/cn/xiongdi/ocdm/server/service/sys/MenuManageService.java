package cn.xiongdi.ocdm.server.service.sys;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @创建人 符志勇
 * @创建时间 2018/11/1
 * @描述 Service接口
 */
public interface MenuManageService {

    /**
     * 获取角色信息
     * @return 角色信息
     */
    Map<String, Object> getRoleInfo();

    /**
     * 获取角色的权限
     * @param jsid 角色ID
     * @return 角色权限
     */
    Map<String, Object> getRolePermission(Long jsid);

    /**
     * 修改角色的权限
     * @param request HttpServletRequest
     * @param jsid 角色ID
     * @param menuids 菜单权限ID
     * @return 菜单权限
     */
    Map<String, Object> updateRolePermission(HttpServletRequest request, Long jsid, String menuids);
}
