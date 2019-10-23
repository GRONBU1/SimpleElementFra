package cn.xiongdi.ocdm.mapper.dao;

import cn.xiongdi.ocdm.mapper.model.SysUserSelectSql;
import org.apache.ibatis.annotations.Param;

public interface SysUserSelectSqlMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserSelectSql record);

    int insertSelective(SysUserSelectSql record);

    SysUserSelectSql getUserSelectField(@Param("yhm") String yhm);

    SysUserSelectSql selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserSelectSql record);

    int updateByPrimaryKey(SysUserSelectSql record);
}