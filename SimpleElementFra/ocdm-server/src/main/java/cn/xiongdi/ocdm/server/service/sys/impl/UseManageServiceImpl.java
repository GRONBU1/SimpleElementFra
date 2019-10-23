package cn.xiongdi.ocdm.server.service.sys.impl;

import cn.xiongdi.ocdm.common.constants.Constant;
import cn.xiongdi.ocdm.common.utils.Utils;
import cn.xiongdi.ocdm.mapper.dao.LogLoginMapper;
import cn.xiongdi.ocdm.mapper.dao.SysOrganizationMapper;
import cn.xiongdi.ocdm.mapper.dao.SysRoleMapper;
import cn.xiongdi.ocdm.mapper.dao.SysUserMapper;
import cn.xiongdi.ocdm.mapper.model.SysOrganization;
import cn.xiongdi.ocdm.mapper.model.SysRole;
import cn.xiongdi.ocdm.mapper.model.SysUser;
import cn.xiongdi.ocdm.server.service.sys.UseManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 粟磊
 * @创建时间 2018/11/01
 * @描述 UseManage模块service接口实现类，实现service接口方法
 */
@Service
public class UseManageServiceImpl implements UseManageService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LogLoginMapper logLoginMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;


    @Override
    public List<SysUser> selectByUserList(String orderColName, String order, String yhm, Long ssdw) {
        // 设置用户名、排序规则、分页信息
        SysUser userInfo = new SysUser();
        userInfo.setYhm(yhm);
        if (orderColName == null || "".equals(orderColName)) {
            userInfo.setOrderBy("createtime desc");
        } else {
            userInfo.setOrderBy(orderColName + " " + order);
        }
        // 设置密钥
        userInfo.setEncryptkey(Utils.getEncryptionKey());
        userInfo.setSsdw(ssdw);
        // 取得用户信息
        List<SysUser> userInfoList = this.sysUserMapper.selectByUserList(userInfo);
        return userInfoList;
    }

    @Override
    public SysUser selectByUserId(SysUser userInfo) {
        return sysUserMapper.selectByUserId(userInfo);
    }

    @Override
    public Map<String, Object> updateUserInfo(SysUser userInfo) {
        HashMap resultMap = new HashMap();
        int re = sysUserMapper.updateByPrimaryKeySelective(userInfo);
        if (re == 1) {
            resultMap.put(Constant.CODE_KEY, "1");
            resultMap.put(Constant.MESSAGE_KEY, "成功");
        } else {
            resultMap.put(Constant.CODE_KEY, String.valueOf(re));
            resultMap.put(Constant.MESSAGE_KEY, "更新失败");
        }
        return resultMap;

    }

    @Override
    public Map<String, Object> delUserInfo(SysUser userInfo) {
        HashMap resultMap = new HashMap();
        int re = sysUserMapper.updateByPrimaryKeySelective(userInfo);
        logLoginMapper.deleteByPrimaryKey(userInfo.getYhm());
        if (re == 1) {
            resultMap.put(Constant.CODE_KEY, "1");
            resultMap.put(Constant.MESSAGE_KEY, "成功");
        } else {
            resultMap.put(Constant.CODE_KEY, String.valueOf(re));
            resultMap.put(Constant.MESSAGE_KEY, "删除失败");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getSysOrganizationTree(SysOrganization sysOrganization) {
        HashMap resultMap = new HashMap();
        List<SysOrganization> sysOrganizationList = sysOrganizationMapper.getListPage(new SysOrganization());
        List<Map> menuList = new ArrayList<>();
        // 先找到所有的一级菜单
        for (int i = 0; i < sysOrganizationList.size(); i++) {
            // 一级菜单没有parentId
            if (sysOrganization == null) {
                if (("0").equals(sysOrganizationList.get(i).getDwjb())) {
                    Map map = new HashMap();
                    map.put("id", sysOrganizationList.get(i).getId());
                    map.put("label", sysOrganizationList.get(i).getDwmc());
                    menuList.add(map);
                }
            } else {
                if (sysOrganization.getLsdw().toString().equals(sysOrganizationList.get(i).getId().toString())) {
                    Map map = new HashMap();
                    map.put("id", sysOrganizationList.get(i).getId());
                    map.put("label", sysOrganizationList.get(i).getDwmc());
                    menuList.add(map);
                }
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (Map map : menuList) {
            map.put("children", getChild(map.get("id").toString(), sysOrganizationList));
        }
        resultMap.put(Constant.CODE_KEY, "1");
        resultMap.put(Constant.MESSAGE_KEY, "成功");
        resultMap.put(Constant.DATA_KEY, menuList);
        resultMap.put("sysOrganizationList", sysOrganizationList);
        return resultMap;
    }

    /**
     * 递归查找子菜单
     *
     * @param id       当前菜单id
     * @param rootMenu 要查找的列表
     * @return
     */
    private List<Map> getChild(String id, List<SysOrganization> rootMenu) {
        // 子菜单
        List<Map> childList = new ArrayList<>();
        for (SysOrganization menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getLsdw().toString().equals(id)) {
                Map map = new HashMap();
                map.put("id", menu.getId());
                map.put("label", menu.getDwmc());
                childList.add(map);
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (Map menu : childList) {// 没有url子菜单还有子菜单
            // 递归
            menu.put("children", getChild(menu.get("id").toString(), rootMenu));
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    @Override
    public Map<String, Object> getRoleInfo() {
        HashMap resultMap = new HashMap();
        List<SysRole> listRole = sysRoleMapper.selectByJslb();
        if (listRole != null && listRole.size() > 0) {
            resultMap.put(Constant.CODE_KEY, "1");
            resultMap.put(Constant.MESSAGE_KEY, "成功");
            resultMap.put(Constant.DATA_KEY, listRole);
        } else {
            resultMap.put(Constant.CODE_KEY, "-4");
            resultMap.put(Constant.MESSAGE_KEY, "无角色信息");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> saveUserInfo(SysUser userInfo) {
        HashMap resultMap = new HashMap();
        int re = sysUserMapper.insertSelective(userInfo);
        if (re == 1) {
            resultMap.put(Constant.CODE_KEY, "1");
            resultMap.put(Constant.MESSAGE_KEY, "成功");
        } else {
            resultMap.put(Constant.CODE_KEY, String.valueOf(re));
            resultMap.put(Constant.MESSAGE_KEY, "添加失败");
        }
        return resultMap;
    }

    @Override
    public int selectYhmCheck(SysUser userInfo) {
        return sysUserMapper.selectYhmOfCheck(userInfo);
    }

}
