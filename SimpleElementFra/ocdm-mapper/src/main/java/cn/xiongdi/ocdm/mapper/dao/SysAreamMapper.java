package cn.xiongdi.ocdm.mapper.dao;

import cn.xiongdi.ocdm.mapper.model.SysAream;

import java.util.List;

public interface SysAreamMapper {
    int deleteByPrimaryKey(String xzqh);

    int insert(SysAream record);

    int insertSelective(SysAream record);

    List<SysAream> getListAream(SysAream sysAream);

    SysAream selectByPrimaryKey(String xzqh);

    int updateByPrimaryKeySelective(SysAream record);

    int updateByPrimaryKey(SysAream record);
}