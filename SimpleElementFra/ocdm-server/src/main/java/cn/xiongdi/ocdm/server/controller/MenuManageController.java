package cn.xiongdi.ocdm.server.controller;

import cn.xiongdi.ocdm.server.base.BaseController;
import cn.xiongdi.ocdm.server.service.sys.MenuManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @创建人 符志勇
 * @创建时间 2018/11/1
 * @描述 权限模块controller类
 */
@RestController
@RequestMapping("/webapi/menuManage")
public class MenuManageController extends BaseController{

    @Autowired
    private MenuManageService menuManageService;

    /**
     * 获取用户角色信息
     * @return  角色信息
     */
    @RequestMapping("/getRoleInfo")
    public Map<String, Object> getRoleInfo() {
        return menuManageService.getRoleInfo();
    }

    /**
     * 获取角色权限菜单
     * @param jsid 角色ID
     * @return 角色权限
     */
    @RequestMapping("/getPermissionInfo")
    public Map<String, Object> getPermissionInfo(Long jsid) {
        return menuManageService.getRolePermission(jsid);
    }

    /**
     * 修改角色权限菜单
     * @param request HttpServletRequest
     * @param jsid 角色ID
     * @param menuids 角色菜单权限ID
     * @return 修改后的菜单权限
     */
    @RequestMapping("/updateRolePermission")
    public Map<String, Object> updateRolePermission(HttpServletRequest request, Long jsid, String menuids) {
        return menuManageService.updateRolePermission(request, jsid, menuids);
    }

}
