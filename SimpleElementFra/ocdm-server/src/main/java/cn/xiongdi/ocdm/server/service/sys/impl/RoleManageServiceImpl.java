package cn.xiongdi.ocdm.server.service.sys.impl;

import cn.xiongdi.ocdm.common.constants.Constant;
import cn.xiongdi.ocdm.common.utils.DateUtil;
import cn.xiongdi.ocdm.common.utils.Utils;
import cn.xiongdi.ocdm.mapper.dao.SysRoleMapper;
import cn.xiongdi.ocdm.mapper.model.SysRole;
import cn.xiongdi.ocdm.mapper.model.SysUser;
import cn.xiongdi.ocdm.server.service.sys.RoleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 粟磊
 * @创建时间 2018-11-01
 * @描述 角色管理 Service接口实现类
 */
@Service
public class RoleManageServiceImpl implements RoleManageService {

    @Autowired
    private SysRoleMapper sysRoleMapper;


    /**
     * 获取角色信息
     *
     * @return 角色信息
     */
    @Override
    public List<SysRole> getRoleInfo() {
        List<SysRole> roleList = sysRoleMapper.selectRoleInfo();
        return roleList;
    }

    @Override
    public Map<String, Object> save(HttpServletRequest request, SysRole sysRole) {
        Map<String, Object> resultMap = new HashMap<>();
        // 获取当前用户信息
        SysUser sysUser = (SysUser) Utils.getLoginUserInfo(request);

        SysRole sysRoleParm=new SysRole();
        sysRoleParm.setMc(sysRole.getMc());
        if (sysRole.getId() != null) {
            sysRoleParm.setId(sysRole.getId());
        }
        int count = this.sysRoleMapper.checkInfo(sysRoleParm);
        if (count != 0) {
            resultMap.put(Constant.CODE_KEY, "0");
            resultMap.put(Constant.MESSAGE_KEY, "角色名称已存在，请重新输入");
            return resultMap;
        }

        int result = 0;
        if (sysRole.getId() == null) {
            sysRole.setSfqy(Constant.ONE);
            sysRole.setSfjy(Constant.ONE);
            sysRole.setJsbh(Utils.getRandomCharAndNumr(16));
            sysRole.setCreatetime(DateUtil.now());
            sysRole.setCreateip(Utils.getRemortIP(request));
            sysRole.setCreateuid(sysUser.getCreateuid());
            result = sysRoleMapper.insertSelective(sysRole);
        } else {
            sysRole.setUpdatetime(DateUtil.now());
            sysRole.setUpdateip(Utils.getRemortIP(request));
            sysRole.setUpdateuid(sysUser.getCreateuid());
            result = sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        }
        if (result > 0) {
            resultMap.put(Constant.CODE_KEY, Constant.CODE_SUCCESS);
            resultMap.put(Constant.MESSAGE_KEY, "保存成功");
            return resultMap;
        } else {
            resultMap.put(Constant.CODE_KEY, Constant.CODE_FAILURE);
            resultMap.put(Constant.MESSAGE_KEY, "保存失败");
            return resultMap;
        }

    }

    @Override
    public Map<String, Object> delete(Long id) {
        Map<String, Object> resultMap = new HashMap<>();
        int result = sysRoleMapper.deleteByPrimaryKey(id);
        if (result > 0) {
            resultMap.put(Constant.CODE_KEY, Constant.CODE_SUCCESS);
            resultMap.put(Constant.MESSAGE_KEY, "删除成功");
            return resultMap;
        } else {
            resultMap.put(Constant.CODE_KEY, Constant.CODE_FAILURE);
            resultMap.put(Constant.MESSAGE_KEY, "删除失败");
            return resultMap;
        }
    }
}
