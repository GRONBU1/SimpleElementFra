package cn.xiongdi.ocdm.server.controller;

import cn.xiongdi.ocdm.common.constants.Constant;
import cn.xiongdi.ocdm.mapper.model.SysRole;
import cn.xiongdi.ocdm.server.base.BaseController;
import cn.xiongdi.ocdm.server.service.sys.RoleManageService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 粟磊
 * @创建时间 2018-11-01
 * @描述 角色管理模块controller类
 */

@RestController
@RequestMapping("/webapi/sysRolePermission")
public class RoleManageController extends BaseController {

    @Autowired
    private RoleManageService roleManageService;

    /**
     * 获取用户角色信息
     *
     * @return 角色信息
     */
    @RequestMapping("/getRoleInfo")
    public Map<String, Object> getRoleInfo(int page, int rows) {
        Map<String, Object> resultMap = new HashMap<>();
        Page pageClass = this.startPage(page, rows);
        List<SysRole> roleList = roleManageService.getRoleInfo();
        resultMap.put(Constant.CODE_KEY, Constant.CODE_SUCCESS);
        resultMap.put(Constant.MESSAGE_KEY, "成功");
        resultMap.put(Constant.DATA_KEY, roleList);
        resultMap.put(Constant.TOTAL_KEY, pageClass.getTotal());
        return resultMap;
    }


    /**
     * 新增角色
     *
     * @param sysRole
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(HttpServletRequest request, SysRole sysRole) {
        return roleManageService.save(request,sysRole);
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Map<String, Object> delete(Long id) {
        return roleManageService.delete(id);
    }
}
