package cn.xiongdi.ocdm.server.service.sys;


import cn.xiongdi.ocdm.mapper.model.SysParam;
import cn.xiongdi.ocdm.mapper.model.ParamRq;

import java.util.List;

/**
 * 描述：参数管理 service接口，提供controller操作所需方法
 *
 * @author 粟磊
 * @version 1.0
 * @since 2018-11-01
 */
public interface ConfigManageService {
    /**
     * 获取参数集合
     *
     * @param paramRq
     * @return
     */
    List<SysParam> getByCon(ParamRq paramRq);

    /**
     * 取得参数类别
     *
     * @return
     */
    List<SysParam> getCslb();

    /**
     * 新增参数
     *
     * @param sysParam
     * @return
     */
    int saveParam(SysParam sysParam);

    /**
     * 编辑参数
     *
     * @param sysParam
     * @return
     */
    int editParam(SysParam sysParam);

    /**
     * 删除参数
     *
     * @param csId 参数id
     * @return
     */
    int deleteParam(Long csId);

    /**
     * 通过参数id获取单条记录
     *
     * @param csid
     * @return
     */
    SysParam getInfoByCsId(Long csid);
}

