package cn.xiongdi.ocdm.server.service.sys.impl;

import cn.xiongdi.ocdm.mapper.dao.SysParamMapper;
import cn.xiongdi.ocdm.mapper.model.SysParam;
import cn.xiongdi.ocdm.server.service.sys.ConfigManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.xiongdi.ocdm.mapper.model.ParamRq;
import java.util.List;

/**
 * 描述:参数设定 service接口实现类，实现service接口方法
 *
 * @Auther: WD 2018/10/31 17:29
 */
@Service
public class ConfigManageServiceImpl implements ConfigManageService {
    @Autowired
    private SysParamMapper sysParamMapper;

    /**
     * 取得参数列表
     *
     * @param paramRq
     * @return 返回集合
     */
    @Override
    public List<SysParam> getByCon(ParamRq paramRq) {
        return sysParamMapper.getParamList(paramRq);
    }

    /**
     * 取得参数类别
     *
     * @return
     */
    @Override
    public List<SysParam> getCslb() {
        return sysParamMapper.getCsLb();
    }

    /**
     * 新增参数
     *
     * @param sysParam
     * @return
     */
    @Override
    public int saveParam(SysParam sysParam) {
        return sysParamMapper.insert(sysParam);
    }

    /**
     * 修改参数
     *
     * @param sysParam
     * @return
     */
    @Override
    public int editParam(SysParam sysParam) {
        return sysParamMapper.updateByPrimaryKey(sysParam);
    }

    /**
     * 删除参数
     *
     * @param csId 参数id
     * @return
     */
    @Override
    public int deleteParam(Long csId) {
        return sysParamMapper.deleteByPrimaryKey(csId);
    }

    /**
     * 通过参数id获取单条记录
     *
     * @param csId
     * @return
     */
    public SysParam getInfoByCsId(Long csId) {
        return sysParamMapper.selectByPrimaryKey(csId);
    }
}

