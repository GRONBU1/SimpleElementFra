package cn.xiongdi.ocdm.mapper.dao;

import cn.xiongdi.ocdm.mapper.model.SysUserWhereSql;

import java.util.List;

public interface SysUserWhereSqlMapper {
    int deleteByPrimaryKey(Long id);

    int updateByYhm(@Param("yhm") String yhm);

    int deleteByYhm(@Param("yhm") String yhm,@Param("rownum") Integer rownum);

    int insert(SysUserWhereSql record);

    int insertSelective(SysUserWhereSql record);

    List<SysUserWhereSql> getUserWhereField(@Param("yhm") String yhm,@Param("deteleflag") Integer deteleflag);

    SysUserWhereSql selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserWhereSql record);

    int updateByPrimaryKey(SysUserWhereSql record);
}