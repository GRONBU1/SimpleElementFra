package cn.xiongdi.ocdm.mapper.dao;

import cn.xiongdi.ocdm.mapper.model.SysOrganization;
import cn.xiongdi.ocdm.mapper.model.SysRole;

import java.util.List;

public interface SysRoleMapper {

    //  获取角色信息
    List<SysRole> selectRoleInfo();

    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    int checkInfo(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    List<SysRole> selectByJslb();

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
}