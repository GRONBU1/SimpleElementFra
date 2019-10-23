package cn.xiongdi.ocdm.mapper.dao;

import cn.xiongdi.ocdm.mapper.model.SysRolePermission;

import java.util.List;

public interface SysRolePermissionMapper {
    // 插入角色权限
    void insertPermission(List<SysRolePermission> item);

    // 根据角色ID获取权限信息
    void deletePermissionByRoleId(Long jsid);

    // 根据角色ID获取权限信息
    List<SysRolePermission> selectPermissionByRoleId(Long jsid);

    // 根据角色ID获取权限
    List<Long> selectMenuidByRoleId(Long jsid);

    int deleteByPrimaryKey(Long id);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);

    SysRolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRolePermission record);

    int updateByPrimaryKey(SysRolePermission record);
}