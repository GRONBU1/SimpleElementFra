package cn.xiongdi.ocdm.server.service.sys;

import cn.xiongdi.ocdm.mapper.model.SysAream;
import cn.xiongdi.ocdm.mapper.model.SysOrganization;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @创建人 粟磊
 * @创建时间 2018-11-01
 * @描述 单位管理
 */
public interface UnitManageService {

    SysOrganization selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysOrganization record);

    List<SysAream> getListAream(SysAream sysAream);

    List<SysOrganization> getListPage(String orderColName, String order, SysOrganization sysOrganization);

    Map<String, Object> save(HttpServletRequest request, SysOrganization sysOrganization);

    Map<String, Object> delete(Long id);

    SysAream selectByPrimaryKeyArea(String  xzqh);

}
