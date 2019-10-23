package cn.xiongdi.ocdm.server.service.sys.impl;

import cn.xiongdi.ocdm.common.constants.Constant;
import cn.xiongdi.ocdm.common.utils.Utils;
import cn.xiongdi.ocdm.mapper.dao.SysMenuMapper;
import cn.xiongdi.ocdm.mapper.dao.SysRoleMapper;
import cn.xiongdi.ocdm.mapper.dao.SysRolePermissionMapper;
import cn.xiongdi.ocdm.mapper.model.*;
import cn.xiongdi.ocdm.server.service.common.CommonEntryLogService;
import cn.xiongdi.ocdm.server.service.sys.MenuManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @创建人 符志勇
 * @创建时间 2018/11/1
 * @描述 权限 Service接口实现类
 */
@Service
public class MenuManageServiceImpl implements MenuManageService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    CommonEntryLogService commonEntryLogService;

    // 菜单最高级别
    private static final Long SYSTEM_CDJB_TOP = 1L;

    /**
     * 获取角色信息
     *
     * @return 角色信息
     */
    @Override
    public Map<String, Object> getRoleInfo() {
        Map<String, Object> resultMap = new HashMap<>();

        List<SysRole> roleList = sysRoleMapper.selectRoleInfo();

        resultMap.put(Constant.CODE_KEY, Constant.CODE_SUCCESS);
        resultMap.put(Constant.MESSAGE_KEY, "成功");
        resultMap.put(Constant.DATA_KEY, roleList);
        return resultMap;
    }

    /**
     * 获取角色的权限
     *
     * @param jsid 角色ID
     * @return 角色权限
     */
    @Override
    public Map<String, Object> getRolePermission(Long jsid) {
        Map<String, Object> resultMap = new HashMap<>();

        SysMenu sysMenu = new SysMenu();
        sysMenu.setCdjb(SYSTEM_CDJB_TOP);

        // 获取除系统管理以外的1级菜单
        List<SysMenu> sysMenuList = sysMenuMapper.selectSysMenuByCdjb(sysMenu);

        for (SysMenu menu : sysMenuList) {
            SysMenu lbMenu = new SysMenu();
            lbMenu.setSjcdid(menu.getMenuid());

            // 根据上级菜单ID获取菜单
            List<SysMenu> menuList = sysMenuMapper.selectSysMenuBySjcdid(lbMenu);
            menu.setChildren(menuList);
        }

        // 根据角色ID获取用户的菜单权限
        List<Long> permissionList = sysRolePermissionMapper.selectMenuidByRoleId(jsid);

        resultMap.put(Constant.CODE_KEY, Constant.CODE_SUCCESS);
        resultMap.put(Constant.MESSAGE_KEY, "成功");
        resultMap.put("qxdata", sysMenuList);
        resultMap.put("checkeddata", permissionList);
        return resultMap;
    }

    /**
     * 修改角色的权限
     *
     * @param request HttpServletRequest
     * @param jsid    角色ID
     * @param menuids 菜单权限ID
     * @return 菜单权限
     */
    @Override
    public Map<String, Object> updateRolePermission(HttpServletRequest request, Long jsid, String menuids) {
        Map<String, Object> resultMap = new HashMap<>();

        // 根据角色ID获取权限信息
        List<SysRolePermission> sysRolePermissionList = sysRolePermissionMapper.selectPermissionByRoleId(jsid);
        List<String> preMenuName = new ArrayList<>();

        // 信息验证
        for (SysRolePermission sysRolePermission : sysRolePermissionList) {
//            String qxyzm = sysRolePermission.getJsid().toString() + "|_|" + sysRolePermission.getMenuid();
//            if (!sysRolePermission.getQxyzm().equals(Utils.getSHA256Str(qxyzm))) {
//                resultMap.put(Constant.CODE_KEY, Constant.CODE_FAILURE);
//                resultMap.put(Constant.MESSAGE_KEY, "角色权限信息被篡改，请联系管理员处理！");
//                return resultMap;
//            }

            // 修改前数据
            SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(sysRolePermission.getMenuid());
            if (sysMenu != null && sysMenu.getCdjb() != SYSTEM_CDJB_TOP) {
                preMenuName.add(sysMenu.getMc());
            }
        }

        // 删除角色的权限
        sysRolePermissionMapper.deletePermissionByRoleId(jsid);

        // 获取当前用户信息
        SysUser sysUser = (SysUser) Utils.getLoginUserInfo(request);

        // 添加角色权限
        List<SysRolePermission> rolePermissionList = new ArrayList<>();
        List<String> aftMenuName = new ArrayList<>();
        menuids = menuids.replaceAll("[\\[\\]]", "");
        if (menuids != null && menuids.length() > 0) {
            String[] checkeddata = menuids.split(",");
            for (String menuid : checkeddata) {
                SysRolePermission sysRolePermission = new SysRolePermission();
                sysRolePermission.setJsid(jsid);
                sysRolePermission.setMenuid(Long.parseLong(menuid));
                sysRolePermission.setQxyzm(Utils.getSHA256Str(jsid + "|_|" + menuid));
                sysRolePermission.setCreateip(Utils.getRemortIP(request));
                sysRolePermission.setCreateuid(sysUser.getYhm());
                sysRolePermission.setCreatetime(new Date());
                rolePermissionList.add(sysRolePermission);

                // 修改后数据
                SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(Long.parseLong(menuid));
                if (sysMenu.getCdjb() != SYSTEM_CDJB_TOP) {
                    aftMenuName.add(sysMenu.getMc());
                }
            }
            sysRolePermissionMapper.insertPermission(rolePermissionList);
        }

        // 记录日志
        LogCommon logCommon = new LogCommon();
        logCommon.setCzyhm(sysUser.getYhm());
        logCommon.setTablenames("SYS_ROLEPERMISSION");
        logCommon.setLy(Utils.getRemortIP(request));
        logCommon.setXw("更新角色权限");
        logCommon.setJg("成功");
        logCommon.setBz(logCommon.getXw() + logCommon.getJg());
        logCommon.setCzmk("系统管理");
        logCommon.setCzqsj(preMenuName.toString());
        logCommon.setCzhsj(aftMenuName.toString());
        logCommon.setCzmc("权限修改");
        commonEntryLogService.entryLog(logCommon);

        resultMap.put(Constant.CODE_KEY, Constant.CODE_SUCCESS);
        resultMap.put(Constant.MESSAGE_KEY, "操作成功");
        return resultMap;
    }
}
