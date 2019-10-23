package cn.xiongdi.ocdm.mapper.dao;

import cn.xiongdi.ocdm.mapper.model.ParamRq;
import cn.xiongdi.ocdm.mapper.model.SysParam;

import java.util.List;

/**
 * 参数管理dao
 *
 * @创建人 tanlulu
 * @创建时间 2018-09-12
 * @描述
 */
public interface SysParamMapper {
    /**
     * 删除数据
     *
     * @param csid 主键
     * @return
     */
    int deleteByPrimaryKey(Long csid);

    /**
     * 新增
     *
     * @param record 请求参数
     * @return
     */
    int insert(SysParam record);

    /**
     * 通过主键查询
     *
     * @param csid 主键
     * @return
     */
    SysParam selectByPrimaryKey(Long csid);

    /**
     * 获取列表
     *
     * @param paramRq 请求参数
     * @return
     */
    List<SysParam> getParamList(ParamRq paramRq);

    /**
     * 修改数据
     *
     * @param record 修改参数
     * @return
     */
    int updateByPrimaryKey(SysParam record);

    /**
     * 获取参数类别
     *
     * @return
     */
    List<SysParam> getCsLb();

    /**
     * 获取参数值
     * @param code 请求参数
     * @return
     */
    String getValueByCode(String code);

    /**
     * 获取列表
     *
     * @param paramRq 请求参数
     * @return
     */
    List<SysParam> getParamByCssm(ParamRq paramRq);
}