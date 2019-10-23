package cn.xiongdi.ocdm.server.service.sys.impl;

import cn.xiongdi.ocdm.common.constants.Constant;
import cn.xiongdi.ocdm.common.utils.DateUtil;
import cn.xiongdi.ocdm.common.utils.Utils;
import cn.xiongdi.ocdm.mapper.dao.SysAreamMapper;
import cn.xiongdi.ocdm.mapper.dao.SysOrganizationMapper;
import cn.xiongdi.ocdm.mapper.model.SysAream;
import cn.xiongdi.ocdm.mapper.model.SysOrganization;
import cn.xiongdi.ocdm.mapper.model.SysUser;
import cn.xiongdi.ocdm.server.service.sys.UnitManageService;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 粟磊
 * @创建时间 2018-11-01
 * @描述 单位管理
 */
@Service
public class UnitManageServiceImpl implements UnitManageService {

    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    @Autowired
    private SysAreamMapper sysAreamMapper;


    @Override
    public SysOrganization selectByPrimaryKey(Long id) {
        return sysOrganizationMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysOrganization record) {
        return sysOrganizationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<SysAream> getListAream(SysAream sysAream) {
        return sysAreamMapper.getListAream(sysAream);
    }

    @Override
    public List<SysOrganization> getListPage(String orderColName, String order, SysOrganization sysOrganization) {
        if (orderColName == null || "".equals(orderColName)) {
            sysOrganization.setOrderBy("createtime desc");
        } else {
            sysOrganization.setOrderBy(orderColName + " " + order);
        }
        return sysOrganizationMapper.getListPage(sysOrganization);
    }

    @Override
    public Map<String, Object> save(HttpServletRequest request, SysOrganization sysOrganization) {
        Map<String, Object> resultMap;
        // 获取当前用户信息
        SysUser sysUser = (SysUser) Utils.getLoginUserInfo(request);

        int result = 0;
        //校验
        resultMap = checkInfo(sysOrganization);
        if (resultMap.size() > 0) {
            return resultMap;
        }
        JSONArray jsonArray = JSONArray.parseArray(sysOrganization.getWorkareaStr());
        String xzqh = jsonArray.get(jsonArray.size() - 1).toString();
        sysOrganization.setXzqh(xzqh);
        if (sysOrganization.getId() == null) {
            sysOrganization.setScbs(Constant.ZERO);
            sysOrganization.setCreatetime(DateUtil.now());
            sysOrganization.setCreateip(Utils.getRemortIP(request));
            sysOrganization.setCreateuid(sysUser.getCreateuid());
            sysOrganization.setDwjb(getDwjb(sysOrganization));
            result = sysOrganizationMapper.insertSelective(sysOrganization);
        } else {
            sysOrganization.setUpdatetime(DateUtil.now());
            sysOrganization.setUpdateip(Utils.getRemortIP(request));
            sysOrganization.setUpdateuid(sysUser.getCreateuid());
            sysOrganization.setDwjb(getDwjb(sysOrganization));
            result = sysOrganizationMapper.updateByPrimaryKeySelective(sysOrganization);
        }
        if (result > 0) {
            resultMap.put(Constant.CODE_KEY, Constant.CODE_SUCCESS);
            resultMap.put(Constant.MESSAGE_KEY, "保存成功!");
            return resultMap;
        } else {
            resultMap.put(Constant.CODE_KEY, Constant.CODE_FAILURE);
            resultMap.put(Constant.MESSAGE_KEY, "保存失败!");
            return resultMap;
        }
    }

    /**
     * 校验
     *
     * @param sysOrganization 校验信息
     * @return JsonString
     */
    private Map<String, Object> checkInfo(SysOrganization sysOrganization) {
        Map<String, Object> map = new HashMap();
        if (sysOrganization.getDwmc() == null || "".equals(sysOrganization.getDwmc())) {
            map.put(Constant.CODE_KEY, "0");
            map.put(Constant.MESSAGE_KEY, "单位名称不能为空");
            return map;
        }
        SysOrganization sysOrganizationParm = new SysOrganization();
        sysOrganizationParm.setDwmc(sysOrganization.getDwmc());
        if (sysOrganization.getId() != null) {
            sysOrganizationParm.setId(sysOrganization.getId());
        }
        int count = this.sysOrganizationMapper.checkInfo(sysOrganizationParm);
        if (count != 0) {
            map.put(Constant.CODE_KEY, "0");
            map.put(Constant.MESSAGE_KEY, "单位名称已存在，请重新输入");
            return map;
        }
        SysOrganization sysOrganizationParmDwbm = new SysOrganization();
        sysOrganizationParmDwbm.setDwbm(sysOrganization.getDwbm());
        if (sysOrganization.getId() != null) {
            sysOrganizationParmDwbm.setId(sysOrganization.getId());
        }
        int countDwbm = this.sysOrganizationMapper.checkInfo(sysOrganizationParmDwbm);
        if (countDwbm != 0) {
            map.put(Constant.CODE_KEY, "0");
            map.put(Constant.MESSAGE_KEY, "单位编码已存在，请重新输入");
            return map;
        }

        return map;
    }

    /**
     * 计算单位级别
     *
     * @param sysOrganization
     * @return
     */
    public String getDwjb(SysOrganization sysOrganization) {
        String result = "0";
        if (Constant.ZERO.equals(sysOrganization.getDwlx()) || Constant.TWO.equals(sysOrganization.getDwlx())) {
            result = Constant.ZERO;
        } else {
            SysOrganization sysOrganizationParent = sysOrganizationMapper.selectByPrimaryKey(sysOrganization.getLsdw());
            if (sysOrganizationParent != null) {
                Long dwjb = Long.parseLong(sysOrganizationParent.getDwjb()) + 1;
                result = dwjb.toString();
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> delete(Long id) {
        Map<String, Object> resultMap = new HashMap<>();
        int result = sysOrganizationMapper.deleteByPrimaryKey(id);
        if (result > 0) {
            resultMap.put(Constant.CODE_KEY, Constant.CODE_SUCCESS);
            resultMap.put(Constant.MESSAGE_KEY, "删除成功!");
        } else {
            resultMap.put(Constant.CODE_KEY, Constant.CODE_FAILURE);
            resultMap.put(Constant.MESSAGE_KEY, "删除失败!");
        }
        return resultMap;
    }

    @Override
    public SysAream selectByPrimaryKeyArea(String xzqh) {
        return sysAreamMapper.selectByPrimaryKey(xzqh);
    }


}
