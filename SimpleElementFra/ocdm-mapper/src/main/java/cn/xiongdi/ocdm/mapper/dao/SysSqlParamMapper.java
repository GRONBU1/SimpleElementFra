package cn.xiongdi.ocdm.mapper.dao;

import cn.xiongdi.ocdm.mapper.model.SysSqlParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysSqlParamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysSqlParam record);

    int insertSelective(SysSqlParam record);

    List<SysSqlParam> getQueryFieldCondition(@Param("jsbs") String jsbs);

    SysSqlParam getSqlParm(@Param("wlzdm") String wlzdm,@Param("jsbs") String jsbs);

    SysSqlParam selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysSqlParam record);

    int updateByPrimaryKey(SysSqlParam record);
}