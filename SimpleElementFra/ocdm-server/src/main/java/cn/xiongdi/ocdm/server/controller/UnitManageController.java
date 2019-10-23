package cn.xiongdi.ocdm.server.controller;

import cn.xiongdi.ocdm.common.constants.Constant;
import cn.xiongdi.ocdm.common.utils.DateUtil;
import cn.xiongdi.ocdm.mapper.model.SysAream;
import cn.xiongdi.ocdm.mapper.model.SysOrganization;
import cn.xiongdi.ocdm.mapper.model.SysUser;
import cn.xiongdi.ocdm.server.base.BaseController;
import cn.xiongdi.ocdm.server.service.sys.UnitManageService;
import cn.xiongdi.ocdm.server.service.sys.UseManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 粟磊
 * @创建时间 2018-11-01
 * @描述 单位管理
 */
@RestController
@RequestMapping("/webapi/unitManage")
public class UnitManageController extends BaseController {

    @Autowired
    private UnitManageService unitManageService;

    @Autowired
    private UseManageService useManageService;

    /**
     * 查询分页列表
     *
     * @param request      HttpServletRequest
     * @param orderColName 排序的字段
     * @param order        排序规则
     * @return
     */
    @RequestMapping("/getListPage")
    public Map<String, Object> getListPage(HttpServletRequest request, String orderColName, String order, SysOrganization sysOrganization) {
        Map<String, Object> resultMap = new HashMap();
        sysOrganization.setLsdw(0L);
        List<SysOrganization> sysOrganizationList = unitManageService.getListPage(orderColName, order, sysOrganization);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (SysOrganization sysOrganizationModel : sysOrganizationList) {
            Map<String, Object> map = new HashMap<>();
            map.putAll(getMap(sysOrganizationModel));
            mapList.add(map);
        }
        resultMap.put(Constant.CODE_KEY, "1");
        resultMap.put(Constant.MESSAGE_KEY, "成功");
        resultMap.put(Constant.DATA_KEY, mapList);
        return resultMap;
    }

    /**
     * 递归查找
     *
     * @param id 当前id
     * @return
     */
    private List<Map> getChild(Long id) {
        List<Map> childList = new ArrayList<>();
        SysOrganization sysOrganization = new SysOrganization();
        sysOrganization.setLsdw(id);
        List<SysOrganization> sysOrganizationList = unitManageService.getListPage(null, null, sysOrganization);
        for (SysOrganization sysOrganizationModel : sysOrganizationList) {
            Map<String, Object> map = new HashMap<>();
            map.putAll(getMap(sysOrganizationModel));
            childList.add(map);
        }
        return childList;
    }

    /**
     * 组装map
     *
     * @param sysOrganizationModel
     * @return
     */
    private Map getMap(SysOrganization sysOrganizationModel) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", sysOrganizationModel.getId());
        map.put("dwlx", sysOrganizationModel.getDwlx());
        map.put("dwbm", sysOrganizationModel.getDwbm());
        map.put("dwmc", sysOrganizationModel.getDwmc());
        map.put("dwjb", sysOrganizationModel.getDwjb());
        map.put("xzqh", sysOrganizationModel.getXzqh());
        map.put("lsdw", sysOrganizationModel.getLsdw());
        map.put("fzr", sysOrganizationModel.getFzr());
        map.put("sjhm", sysOrganizationModel.getSjhm());
        map.put("wjbsbid", sysOrganizationModel.getWjbsbid());
        map.put("dz", sysOrganizationModel.getDz());
        map.put("bz", sysOrganizationModel.getBz());
        map.put("scbs", sysOrganizationModel.getScbs());
        map.put("createtime", DateUtil.dateFormat(sysOrganizationModel.getCreatetime(), "yyyy-MM-dd HH:mm:ss"));
        map.put("createuid", sysOrganizationModel.getCreateuid());
        map.put("createip", sysOrganizationModel.getCreateip());
        map.put("updatetime", sysOrganizationModel.getUpdatetime());
        map.put("updateuid", sysOrganizationModel.getUpdateuid());
        map.put("updateip", sysOrganizationModel.getUpdateip());
        map.put("children", getChild(sysOrganizationModel.getId()));
        return map;
    }


    /**
     * 查询行政区划
     *
     * @return
     */
    @RequestMapping("/getListAream")
    public Map<String, Object> getListAream() {
        Map<String, Object> resultMap = new HashMap();
        SysAream sysAream = new SysAream();
        sysAream.setFjxzqh("000000");
        List<SysAream> areamList = unitManageService.getListAream(sysAream);
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (areamList != null && areamList.size() > 0) {
            for (SysAream sysAreamModel : areamList) {
                Map<String, Object> map = new HashMap<>();
                map.putAll(getMapAream(sysAreamModel));
                mapList.add(map);
            }
        }
        resultMap.put(Constant.CODE_KEY, "1");
        resultMap.put(Constant.MESSAGE_KEY, "成功");
        resultMap.put(Constant.DATA_KEY, mapList);
        return resultMap;
    }

    /**
     * 递归查找行政区划
     *
     * @param areamList areamList
     * @return
     */
    private List<Map> getChildAream(List<SysAream> areamList) {
        List<Map> childList = new ArrayList<>();
        for (SysAream sysAreamModel : areamList) {
            Map<String, Object> map = new HashMap<>();
            map.putAll(getMapAream(sysAreamModel));
            childList.add(map);
        }
        return childList;
    }

    /**
     * 组装行政区划map
     *
     * @param sysAreamModel
     * @return
     */
    private Map getMapAream(SysAream sysAreamModel) {
        Map<String, Object> map = new HashMap<>();
        map.put("value", sysAreamModel.getXzqh());
        map.put("label", sysAreamModel.getQhmc());
        SysAream sysAream = new SysAream();
        sysAream.setFjxzqh(sysAreamModel.getXzqh());
        List<SysAream> areamList = unitManageService.getListAream(sysAream);
        if (areamList != null && areamList.size() > 0) {
            map.put("children", getChildAream(areamList));
        }
        return map;
    }

    /**
     * 查询所有的单位
     *
     * @return
     */
    @RequestMapping("/getListAll")
    public Map<String, Object> getListAll() {
        Map<String, Object> resultMap = new HashMap();
        List<SysOrganization> sysOrganizationList = unitManageService.getListPage("", "", new SysOrganization());
        resultMap.put(Constant.CODE_KEY, "1");
        resultMap.put(Constant.MESSAGE_KEY, "成功");
        resultMap.put(Constant.DATA_KEY, sysOrganizationList);
        return resultMap;
    }

    /**
     * 查询单个单位信息
     *
     * @param id 单位id
     * @return 单个单位信息
     */
    @RequestMapping("/selectByPrimaryKey")
    public Map<String, Object> selectByPrimaryKey(Long id) {
        Map<String, Object> resultMap = new HashMap();
        SysOrganization sysOrganization = unitManageService.selectByPrimaryKey(id);
        String aream = "";
        String areamOne = "";
        String areamTwo = "";
        SysAream sysAream = unitManageService.selectByPrimaryKeyArea(sysOrganization.getXzqh());
        if (sysAream != null) {
            areamTwo = sysAream.getXzqh();
            SysAream sysAreamOne = unitManageService.selectByPrimaryKeyArea(sysAream.getFjxzqh());
            if (sysAreamOne != null) {
                areamOne = sysAreamOne.getXzqh();
                SysAream sysAreamTwo = unitManageService.selectByPrimaryKeyArea(sysAreamOne.getFjxzqh());
                if (sysAreamTwo != null) {
                    aream = sysAreamTwo.getXzqh();
                } else {
                    aream = sysAreamOne.getXzqh();
                    areamOne = sysAream.getXzqh();
                    areamTwo = "";
                }
            } else {
                aream = sysAream.getXzqh();
                areamOne = "";
                areamTwo = "";
            }
        }
        List<String> stringList = new ArrayList<>();
        if (aream.length() > 0) {
            stringList.add(aream);
        }
        if (areamOne.length() > 0) {
            stringList.add(areamOne);
        }
        if (areamTwo.length() > 0) {
            stringList.add(areamTwo);
        }
        resultMap.put(Constant.CODE_KEY, "1");
        resultMap.put(Constant.MESSAGE_KEY, "成功");
        resultMap.put(Constant.DATA_KEY, sysOrganization);
        resultMap.put(Constant.TEMP_DATA_KEY, stringList);
        return resultMap;
    }

    /**
     * 保存
     *
     * @param sysOrganization
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(HttpServletRequest request, SysOrganization sysOrganization) {
        Map<String, Object> map = new HashMap();
        map = unitManageService.save(request, sysOrganization);
        return map;
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Map<String, Object> delete(Long id) {
        Map<String, Object> resultMap = new HashMap<>();
        SysOrganization sysOrganizationParm = new SysOrganization();
        sysOrganizationParm.setLsdw(id);
        List<SysOrganization> sysOrganizationList = unitManageService.getListPage(null, null, sysOrganizationParm);
        if (sysOrganizationList.size() > 0) {
            resultMap.put(Constant.CODE_KEY, Constant.CODE_FAILURE);
            resultMap.put(Constant.MESSAGE_KEY, "该单位下有子集单位，不可删除！");
            return resultMap;
        }
        List<SysUser> sysUserList = useManageService.selectByUserList(null, null, null, id);
        if (sysUserList.size() > 0) {
            resultMap.put(Constant.CODE_KEY, Constant.CODE_FAILURE);
            resultMap.put(Constant.MESSAGE_KEY, "该单位下有操作员，不可删除！");
            return resultMap;
        }
        SysOrganization sysOrganization = unitManageService.selectByPrimaryKey(id);
        sysOrganization.setScbs(Constant.ONE);
        int result = unitManageService.updateByPrimaryKeySelective(sysOrganization);
        if (result > 0) {
            resultMap.put(Constant.CODE_KEY, Constant.CODE_SUCCESS);
            resultMap.put(Constant.MESSAGE_KEY, "删除成功!");
            return resultMap;
        } else {
            resultMap.put(Constant.CODE_KEY, Constant.CODE_FAILURE);
            resultMap.put(Constant.MESSAGE_KEY, "删除失败!");
            return resultMap;
        }
    }

}
