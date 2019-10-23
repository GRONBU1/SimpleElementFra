package cn.xiongdi.ocdm.mapper.dao;

import cn.xiongdi.ocdm.mapper.model.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper {

    // 根据上级菜单ID获取菜单
    List<SysMenu> selectSysMenuBySjcdid(SysMenu record);

    // 获取除系统管理以外的1级菜单
    List<SysMenu> selectSysMenuByCdjb(SysMenu record);

    int deleteByPrimaryKey(Long menuid);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long menuid);

    SysMenu selectByCdbh(String cdbh);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    List<SysMenu> selectMenuTreeByRole(@Param("sjcdid")String roleID, @Param("sfjy")String sfjy);

    SysMenu selectSysMenuByUrl(@Param("url")String url);
}