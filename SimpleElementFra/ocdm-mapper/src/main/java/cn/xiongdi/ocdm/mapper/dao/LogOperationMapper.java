package cn.xiongdi.ocdm.mapper.dao;

import cn.xiongdi.ocdm.mapper.model.LogOperation;
import cn.xiongdi.ocdm.mapper.model.LogOperationWithBLOBs;

import java.util.List;

public interface LogOperationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LogOperationWithBLOBs record);

    int insertSelective(LogOperationWithBLOBs record);

    List<LogOperationWithBLOBs> selectByPrimaryKey(LogOperation record);

    int updateByPrimaryKeySelective(LogOperationWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(LogOperationWithBLOBs record);

    int updateByPrimaryKey(LogOperation record);
}