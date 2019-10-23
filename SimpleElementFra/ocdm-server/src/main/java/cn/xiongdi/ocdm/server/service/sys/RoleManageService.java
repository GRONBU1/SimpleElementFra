package cn.xiongdi.ocdm.server.service.sys;

import cn.xiongdi.ocdm.mapper.model.SysRole;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @创建人 粟磊
 * @创建时间 2018-11-01
 * @描述 角色管理 Service接口
 */
public interface RoleManageService {

    /**
     * 获取角色信息
     *
     * @return 角色信息
     */
    List<SysRole> getRoleInfo();

    /**
     * 新增角色
     *
     * @param sysRole
     * @return
     */
    Map<String, Object> save(HttpServletRequest request, SysRole sysRole);

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    Map<String, Object> delete(Long id);
}
