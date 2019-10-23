package cn.xiongdi.ocdm.mapper.dao;

import cn.xiongdi.ocdm.mapper.model.SysOrganization;

import java.util.List;
import java.util.Map;

public interface SysOrganizationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysOrganization record);

    int insertSelective(SysOrganization record);

    SysOrganization selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysOrganization record);

    int updateByPrimaryKey(SysOrganization record);

    List<SysOrganization> selectSysOrganizationAll();

    List<SysOrganization> getListPage(SysOrganization sysOrganization);

    List<Map> getComBoxBySsdwId(Long id);

    int checkInfo(SysOrganization record);

    //查询下级单位
    Integer[] getXjDw(String lsdw);
}