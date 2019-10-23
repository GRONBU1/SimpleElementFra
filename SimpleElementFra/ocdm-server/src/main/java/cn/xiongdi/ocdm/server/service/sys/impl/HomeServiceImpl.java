package cn.xiongdi.ocdm.server.service.sys.impl;

import cn.xiongdi.ocdm.common.constants.Constant;
import cn.xiongdi.ocdm.common.utils.Utils;
import cn.xiongdi.ocdm.mapper.dao.*;
import cn.xiongdi.ocdm.mapper.model.LogLogin;
import cn.xiongdi.ocdm.mapper.model.SysUser;
import cn.xiongdi.ocdm.server.service.sys.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @创建者 pengfuming
 * @创建时间 2018-11-05
 * @描述 主页
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LogLoginMapper logLoginMapper;


    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    /**
     * 获取登录信息
     *
     * @param request
     * @param flag    显示用户名flag
     * @return
     */
    public Map<String, Object> getLoginInfo(HttpServletRequest request, Integer flag) {

        //获取在线用户数
        int userCount = 0;
        userCount = sysUserMapper.selectZXYHS();

        //取session中的用户id
        SysUser user = (SysUser) Utils.getLoginUserInfo(request);

        //根据用户id查询登录信息
        SysUser sysUserParam = new SysUser();
        sysUserParam.setUserid(user.getUserid());
        sysUserParam.setEncryptkey(Utils.getEncryptionKey());

        //登录用户信息
        SysUser userInfoResult = sysUserMapper.selectByUserId(sysUserParam);
        if (userInfoResult != null) {
            if ("0:0:0:0:0:0:0:1".equals(userInfoResult.getDlip())) {
                userInfoResult.setDlip("127.0.0.1");
            }
            if ("0:0:0:0:0:0:0:1".equals(userInfoResult.getHis_dlip())) {
                userInfoResult.setHis_dlip("127.0.0.1");
            }
        }

        Map<String, Object> map = new HashMap<>();
        if (flag == 0) {
            map.put("useName", userInfoResult.getYhm());
            return map;
        }

        //一级审批管理权限控制
        if (user.getListPermission().contains("approvalManage.html")) {
            userInfoResult.setSpglqx("1");
        } else {
            userInfoResult.setSpglqx("0");
        }
        //二级审批管理权限控制
        if (user.getListPermission().contains("approvalManage2.html")) {
            userInfoResult.setSpglqx2("1");
        } else {
            userInfoResult.setSpglqx2("0");
        }
        //证件管理权限控制
        if (user.getListPermission().contains("infoQuery.html")) {
            userInfoResult.setZjglqx("1");
        } else {
            userInfoResult.setZjglqx("0");
        }
        //证件转保权限控制
        if (user.getListPermission().contains("removeInfo.html")) {
            userInfoResult.setZjzbqx("1");
        } else {
            userInfoResult.setZjzbqx("0");
        }
        //催缴管理权限控制
        if (user.getListPermission().contains("urgeAudit.html")) {
            userInfoResult.setCjglqx("1");
        } else {
            userInfoResult.setCjglqx("0");
        }
        //年审管理权限控制
        if (user.getListPermission().contains("annualAudit.html")) {
            userInfoResult.setNsglqx("1");
        } else {
            userInfoResult.setNsglqx("0");
        }

        //护照统计信息
        int jCCount = 0;
        int dNSCount = 0;
        int zKCount = 0;
        int zBCount = 0;
        int ySZCount = 0;
        int eSZCount = 0;
        int cJCount = 0;
        int hZZSCount = 0;
        int xJDWHZZSCount = 0;
        int yXHZCount = 0;
        int sXHZCount = 0;

        //查询本单位下所有的下级单位，包括本单位
        //String dwIds = getAllXjdw(user.getSsdw().toString());

        yXHZCount = hZZSCount - sXHZCount;

        userInfoResult.setJc(jCCount);
        userInfoResult.setDns(dNSCount);
        userInfoResult.setZk(zKCount);
        userInfoResult.setZb(zBCount);
        userInfoResult.setYsz(ySZCount);
        userInfoResult.setEsz(eSZCount);
        userInfoResult.setCj(cJCount);
        userInfoResult.setSpz(ySZCount + eSZCount);
        userInfoResult.setHzzs(hZZSCount);
        userInfoResult.setXjdwhzzs(xJDWHZZSCount);
        userInfoResult.setYxhz(yXHZCount);
        userInfoResult.setSxhz(sXHZCount);

        userInfoResult.setHis_yhzt(user.getYhzt());
        userInfoResult.setZxyhsl(userCount);

        //计算密码有效期和用户有效期
        Date now = new Date();
        int userEffectiveDate = (int) ((userInfoResult.getYhyxq().getTime() - now.getTime()) / (1000 * 3600 * 24)) + 1;
        int passwordEffectiveDate = (int) ((userInfoResult.getMmyxq().getTime() - now.getTime()) / (1000 * 3600 * 24)) + 1;
        userInfoResult.setNow_yhsxday(userEffectiveDate);
        userInfoResult.setNow_mmsxday(passwordEffectiveDate);

        //从登录历史中取上一次用户id
        Long id = logLoginMapper.selectSecondId(userInfoResult.getYhm());

        //根据取得的id查询信息
        LogLogin historyUserInfo = logLoginMapper.selectByPrimaryKey(id);

        if (historyUserInfo == null) {
            map.put(Constant.DATA_KEY, userInfoResult);
            return map;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date historyLoginTime = historyUserInfo.getDlsj();
        if (historyLoginTime != null) {
            userInfoResult.setHis_dlsj(sdf.format(historyLoginTime));
        }
        userInfoResult.setHis_dlip(historyUserInfo.getDlip());

        map.put(Constant.DATA_KEY, userInfoResult);
        return map;
    }

    /**
     * 查询隶属单位对应的id
     *
     * @param lsdw
     * @return
     */
    private Integer[] getXjDw(String lsdw) {
        return sysOrganizationMapper.getXjDw(lsdw);
    }

    /**
     * 查询下级单位
     *
     * @param lsdw
     * @return
     */
    private String getAllXjdw(String lsdw) {
        String allXjdw = lsdw + ",";
        Integer[] xjdw = getXjDw(lsdw);
        if (xjdw.length > 0) {
            for (int i = 0; i < xjdw.length; i++) {
                allXjdw += getAllXjdw(xjdw[i].toString()) + ",";
            }
        }
        return allXjdw.substring(0, allXjdw.length() - 1);
    }
}
