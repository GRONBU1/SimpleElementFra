package cn.xiongdi.ocdm.server.controller;

import cn.xiongdi.ocdm.common.constants.Constant;
import cn.xiongdi.ocdm.common.utils.CommonCheck;
import cn.xiongdi.ocdm.common.utils.DateUtil;
import cn.xiongdi.ocdm.common.utils.EnumUserStatus;
import cn.xiongdi.ocdm.common.utils.Utils;
import cn.xiongdi.ocdm.mapper.model.*;
import cn.xiongdi.ocdm.server.base.BaseController;
import cn.xiongdi.ocdm.server.service.common.CommonEntryLogService;
import cn.xiongdi.ocdm.server.service.sys.UseManageService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @创建人 粟磊
 * @创建时间 2018-11-01
 * @描述 用户(办事员)管理
 */
@RestController
@RequestMapping("/webapi/useManage")
public class UseManageController extends BaseController {

    @Autowired
    private UseManageService useManageService;

    @Autowired
    private CommonEntryLogService commonEntryLogService;


    /**
     * 获取用户信息（POST）
     *
     * @param request      HttpServletRequest
     * @param response     HttpServletResponse
     * @param page         页
     * @param rows         每页行数
     * @param orderColName 排序的字段
     * @param order        排序规则
     * @param yhm          用户名
     * @return 用户信息
     */
    @RequestMapping("/getUserInfoByPage")
    public Map<String, Object> getUserByPage(HttpServletResponse response,
                                             HttpServletRequest request,
                                             int page,
                                             int rows,
                                             String orderColName,
                                             String order,
                                             String yhm, Long ssdw) {
        Map<String, Object> resultMap = new HashMap();
        Page pageClass = this.startPage(page, rows);
        // 取得用户信息
        List<SysUser> userInfoList = useManageService.selectByUserList(orderColName, order, yhm, ssdw);
        if (userInfoList != null && userInfoList.size() > 0) {
            for (SysUser oneDATA :
                    userInfoList) {
                if ("0:0:0:0:0:0:0:1".equals(oneDATA.getDlip())) {
                    oneDATA.setDlip("127.0.0.1");
                }
            }
        }
        resultMap.put(Constant.CODE_KEY, "1");
        resultMap.put(Constant.MESSAGE_KEY, "成功");
        resultMap.put("num", pageClass.getTotal());
        resultMap.put(Constant.DATA_KEY, userInfoList);
        resultMap.put(Constant.TOTAL_KEY, pageClass.getTotal());
        return resultMap;
    }


    /**
     * 获取单位树
     *
     * @return 单位树
     */
    @RequestMapping("/getSysOrganizationTree")
    public Map<String, Object> getSysOrganizationTree() {
        return this.useManageService.getSysOrganizationTree(null);
    }

    /**
     * 获取角色（POST）
     *
     * @return 角色
     */
    @RequestMapping("/getRoleInfo")
    public Map<String, Object> getRoleInfo() {
        return this.useManageService.getRoleInfo();
    }

    /**
     * 添加用户信息（POST）
     *
     * @param request  HttpServletRequest
     * @param userInfo 用户信息
     * @return JsonString
     */
    @RequestMapping("/saveUserInfo")
    public Map<String, Object> insertUserInfo(HttpServletRequest request, SysUser userInfo) {
        Map<String, Object> map = new HashMap();
        try {
            if (userInfo != null) {
                SimpleDateFormat dft = new SimpleDateFormat(Constant.DATE_FORMAT);
                // 设置密钥
                userInfo.setEncryptkey(Utils.getEncryptionKey());
                // 检查用户信息
                if (checkUserInfo(userInfo) != null) {
                    map = checkUserInfo(userInfo);
                    return map;
                }
                userInfo.setLxdh(userInfo.getLxdh()==null?"":userInfo.getLxdh());
                // 电话号码检查
                if (!CommonCheck.checkMobile(userInfo.getLxdh())) {
                    map.put(Constant.CODE_KEY, "0");
                    map.put(Constant.MESSAGE_KEY, "联系电话格式不正确");
                    return map;
                }
                if (userInfo.getIplist() != null && !"".equals(userInfo.getIplist())) {
                    if (!checkIpList(userInfo)) {
                        map.put(Constant.CODE_KEY, "0");
                        map.put(Constant.MESSAGE_KEY, "IP格式错误");
                        return map;
                    }
                }
                // 取得登录用户信息
                SysUser loginUser = (SysUser) Utils.getLoginUserInfo(request);

                // 设置用户ID
                userInfo.setUserid(UUID.randomUUID().toString());
                // 设置初始密码为ocdm@123456
                String password = userInfo.getPassWord("ocdm@123456");
                // 设置删除FLG
                userInfo.setDeleteflg(0L);
                userInfo.setMm(Utils.getSHA256Str(password));
                // 设置用户状态6：初次登录修改密码
                userInfo.setYhzt(EnumUserStatus.MODIFY_PASSWORD.getValue());
                // 设置登录状态为0：离线
                userInfo.setDlzt("0");
                // 设置登录失败次数
                userInfo.setDlsbcs("0");
                // 设置有效期
                userInfo.setYhyxq(dft.parse("9999-12-31"));
                userInfo.setMmyxq(dft.parse("9999-12-31"));
                // 设置默认的允许登录时间
                userInfo.setDlsjks("00:00");
                userInfo.setDlsjdjs("23:59");
                userInfo.setSfzh("");
                userInfo.setYhbh("");
                userInfo.setSfjy(Constant.ONE);
                if (!checkLoginTime(userInfo)) {
                    map.put(Constant.CODE_KEY, "0");
                    map.put(Constant.MESSAGE_KEY, "登录时间段错误");
                    return map;
                }
                // 做成用户验证码
                String userVerCode = userInfo.makeUserVerCode();
                // 设置账户验证码
                userInfo.setZhmm(Utils.getSHA256Str(userVerCode));
                userInfo.setCreateip(Utils.getRemortIP(request));
                userInfo.setCreatetime(new Date());
                userInfo.setCreateuid(loginUser.getYhm());
                map = this.useManageService.saveUserInfo(userInfo);

                // 记录日志
                LogCommon logCommon = new LogCommon();
                logCommon.setCdbh("0019");
                logCommon.setCzyhm(loginUser.getYhm());
                logCommon.setTablenames("SYS_USER");
                logCommon.setLy(Utils.getRemortIP(request));
                logCommon.setXw("新增用户信息");
                logCommon.setJg("成功");
                logCommon.setBz(logCommon.getXw() + logCommon.getJg());
                logCommon.setCzmk("用户管理");
                logCommon.setCzqsj("");
                logCommon.setCzhsj(userInfo.toStringExcludeBlob());
                logCommon.setCzmc("Create");
                commonEntryLogService.entryLog(logCommon);

            } else {
                map.put(Constant.CODE_KEY, "-2");
                map.put(Constant.MESSAGE_KEY, "用户信息为空，不能添加");
            }

            return map;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return map;
        }
    }

    /**
     * 更新用户信息（POST）
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param userInfo 用户信息
     * @return JsonString
     */
    @RequestMapping("/editUser")
    public Map<String, Object> updateUserInfo(HttpServletRequest request, HttpServletResponse response, SysUser userInfo) {
        Map<String, Object> map = new HashMap();
        if (userInfo != null) {
            // 设置密钥
            userInfo.setEncryptkey(Utils.getEncryptionKey());
            // 检查用户信息
            if (checkUserInfo(userInfo) != null) {
                map = checkUserInfo(userInfo);
                return map;
            }
            // 电话号码检查
            if (!CommonCheck.checkMobile(userInfo.getLxdh())) {
                map.put(Constant.CODE_KEY, "0");
                map.put(Constant.MESSAGE_KEY, "联系电话格式不正确");
                return map;
            }
            if (userInfo.getIplist() != null && !"".equals(userInfo.getIplist())) {
                if (!checkIpList(userInfo)) {
                    map.put(Constant.CODE_KEY, "0");
                    map.put(Constant.MESSAGE_KEY, "IP格式错误");
                    return map;
                }
            }

            // 取得登录用户信息
            SysUser loginUser = (SysUser) Utils.getLoginUserInfo(request);
            // 取得当前更新用户在表中的信息
            SysUser oldInfo = this.useManageService.selectByUserId(userInfo);
            if (loginUser != null) {
                // 判断登录的用户是否是更新的用户
                if (loginUser.getUserid().equals(userInfo.getUserid())) {
                    map.put(Constant.CODE_KEY, "-5");
                    map.put(Constant.MESSAGE_KEY, "不能修改当前登录用户的信息！");
                    return map;
                }

            }
            // 设置身份证号
            oldInfo.setSfzh("");
            // 判断用户信息是否已被篡改
            if (oldInfo != null) {
//                // 检查验证码
//                if (checkUserFalsify(request, oldInfo, loginUser)) {
//                    map.put(Constant.CODE_KEY, "-5");
//                    map.put(Constant.MESSAGE_KEY, "该用户信息已被篡改，请联系管理员！");
//                    return map;
//                }
                if (!checkLoginTime(userInfo)) {
                    map.put(Constant.CODE_KEY, "0");
                    map.put(Constant.MESSAGE_KEY, "登录时间段错误");
                    return map;
                }
                // 设置身份证号
                userInfo.setSfzh("");
                // 做成用户验证码
                String userVerCode = userInfo.makeUserVerCode();
                // 设置账户验证码
                userInfo.setZhmm(Utils.getSHA256Str(userVerCode));
                userInfo.setUpdateip(Utils.getRemortIP(request));
                userInfo.setUpdatetime(new Date());
                userInfo.setUpdateuid(loginUser.getYhm());
                map = this.useManageService.updateUserInfo(userInfo);

                // 记录日志
                LogCommon logCommon = new LogCommon();
                logCommon.setCdbh("0019");
                logCommon.setCzyhm(loginUser.getYhm());
                logCommon.setTablenames("SYS_USER");
                logCommon.setLy(Utils.getRemortIP(request));
                logCommon.setXw("更新用户信息");
                logCommon.setJg("成功");
                logCommon.setBz(logCommon.getXw() + logCommon.getJg());
                logCommon.setCzmk("用户管理");
                logCommon.setCzqsj(oldInfo.toStringExcludeBlob());
                logCommon.setCzhsj(userInfo.toStringExcludeBlob());
                logCommon.setCzmc("Update");
                commonEntryLogService.entryLog(logCommon);
            } else {
                map.put(Constant.CODE_KEY, "-2");
                map.put(Constant.MESSAGE_KEY, "用户信息为空，不能更新");
            }
        } else {
            map.put(Constant.CODE_KEY, "-2");
            map.put(Constant.MESSAGE_KEY, "用户信息为空，不能更新");
        }

        return map;
    }

    /**
     * 删除用户信息（POST）
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param id       用户ID
     * @return JsonString
     */
    @RequestMapping("/delUser")
    public Map<String, Object> delUserInfo(HttpServletRequest request, HttpServletResponse response, String id) {
        Map<String, Object> map = new HashMap();
        // 用户参数信息
        SysUser userParam;
        // 当前删除用户的信息
        SysUser userInfo;
        if (id != null && !"".equals(id)) {
            userParam = new SysUser();
            // 设置ID
            userParam.setUserid(id);
            // 设置密钥
            userParam.setEncryptkey(Utils.getEncryptionKey());
            // 取得登录用户信息
            SysUser loginUser = (SysUser) Utils.getLoginUserInfo(request);
            // 取得当前删除用户的信息
            userInfo = this.useManageService.selectByUserId(userParam);
            if (loginUser != null) {
                // 判断登录的用户是否是更新的用户
                if (loginUser.getUserid().equals(id)) {
                    map.put(Constant.CODE_KEY, "-5");
                    map.put(Constant.MESSAGE_KEY, "不能删除当前登录用户的信息！");
                    return map;
                }
                if (userInfo != null) {
                    // 取得登录用户是否是警员
                    String loginPolice = loginUser.getSfjy();
                    String police = userInfo.getSfjy();
                    if ("0".equals(loginPolice) && "1".equals(police)) {
                        map.put(Constant.CODE_KEY, "-5");
                        map.put(Constant.MESSAGE_KEY, "非警员不能删除警员用户！");
                        return map;
                    }
                }
            }
            // 判断用户信息是否已被篡改
            if (userInfo != null) {
//                // 检查验证码
//                if (checkUserFalsify(request, userInfo, loginUser)) {
//                    map.put(Constant.CODE_KEY, "-5");
//                    map.put(Constant.MESSAGE_KEY, "该用户信息已被篡改，请联系管理员！");
//                    return map;
//                }
                // 删除处理
                SysUser delUser = new SysUser();
                // 设置用户ID
                delUser.setUserid(id);
                // 设置用户名
                delUser.setYhm(userInfo.getYhm());
                // 设置用户状态为禁用
                userInfo.setYhzt(EnumUserStatus.FORBIDDEN.getValue());
                delUser.setYhzt(EnumUserStatus.FORBIDDEN.getValue());
                // 设置停用原因
                delUser.setTyyy("逻辑删除");
                // 设置删除FLG
                userInfo.setDeleteflg(1L);
                delUser.setDeleteflg(1L);
                // 做成用户验证码
                String userVerCode = userInfo.makeUserVerCode();
                // 设置账户验证码
                delUser.setZhmm(Utils.getSHA256Str(userVerCode));
                delUser.setUpdateip(Utils.getRemortIP(request));
                delUser.setUpdatetime(new Date());
                delUser.setUpdateuid(loginUser.getYhm());
                // 取得当前删除用户的信息
                userInfo = this.useManageService.selectByUserId(userParam);
                map = this.useManageService.delUserInfo(delUser);

                // 记录日志
                LogCommon logCommon = new LogCommon();
                logCommon.setCdbh("0019");
                logCommon.setCzyhm(loginUser.getYhm());
                logCommon.setTablenames("SYS_USER");
                logCommon.setLy(Utils.getRemortIP(request));
                logCommon.setXw("删除用户信息");
                logCommon.setJg("成功");
                logCommon.setBz(logCommon.getXw() + "和该用户的登录历史记录" + logCommon.getJg());
                logCommon.setCzmk("用户管理");
                logCommon.setCzqsj(userInfo.toStringExcludeBlob());
                logCommon.setCzhsj("");
                logCommon.setCzmc("Delete");
                commonEntryLogService.entryLog(logCommon);

            } else {
                map.put(Constant.CODE_KEY, "-2");
                map.put(Constant.MESSAGE_KEY, "用户信息为空，不能删除");
            }
        } else {
            map.put(Constant.CODE_KEY, "-2");
            map.put(Constant.MESSAGE_KEY, "用户信息为空，不能删除");
        }

        return map;
    }

    /**
     * 重置密码（POST）
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param id       用户ID
     * @return JsonString
     */
    @RequestMapping("/resetPassword")
    public Map<String, Object> resetPassword(HttpServletRequest request, HttpServletResponse response, String id) {
        Map<String, Object> map = new HashMap();
        if (id != null && !id.equals("")) {
            // 取得登录用户信息
            SysUser loginUser = (SysUser) Utils.getLoginUserInfo(request);
            SysUser userInfoParam = new SysUser();
            // 设置用户ID
            userInfoParam.setUserid(id);
            // 设置密钥
            userInfoParam.setEncryptkey(Utils.getEncryptionKey());
            // 取得当前更新用户在表中的信息
            SysUser userInfo = this.useManageService.selectByUserId(userInfoParam);
            if (loginUser != null) {
                // 判断登录的用户是否是更新的用户
                if (loginUser.getUserid().equals(userInfo.getUserid())) {
                    map.put(Constant.CODE_KEY, "-5");
                    map.put(Constant.MESSAGE_KEY, "不能重置当前登录用户的密码！");
                    return map;
                }
            }
            // 判断用户信息是否已被篡改
            if (userInfo != null) {
                // 检查验证码
//                if (checkUserFalsify(request, userInfo, loginUser)) {
//                    map.put(Constant.CODE_KEY, "-5");
//                    map.put(Constant.MESSAGE_KEY, "该用户信息已被篡改，请联系管理员！");
//                    return map;
//                }
                // 取得旧密码
                String oldPwd = userInfo.getMm();

                SysUser updUser = new SysUser();
                // 设置用户ID
                updUser.setUserid(userInfo.getUserid());
                // 设置初始密码为ocdm@123456
                String password = userInfo.getPassWord("ocdm@123456");
                userInfo.setMm(Utils.getSHA256Str(password));
                updUser.setMm(Utils.getSHA256Str(password));
                // 设置用户状态7：已解锁修改密码
                userInfo.setYhzt(EnumUserStatus.UNLOCKING_MODIFY_PASSWORD.getValue());
                updUser.setYhzt(EnumUserStatus.UNLOCKING_MODIFY_PASSWORD.getValue());
                // 做成用户验证码
                String userVerCode = userInfo.makeUserVerCode();
                // 设置账户验证码
                updUser.setZhmm(Utils.getSHA256Str(userVerCode));
                // 设置登录失败次数
                updUser.setDlsbcs("0");
                // 设置停用原因
                updUser.setTyyy("已重置密码并消除登录失败次数");
                updUser.setUpdateip(Utils.getRemortIP(request));
                updUser.setUpdatetime(new Date());
                updUser.setUpdateuid(loginUser.getYhm());
                map = this.useManageService.updateUserInfo(updUser);

                // 记录日志
                LogCommon logCommon = new LogCommon();
                logCommon.setCdbh("0019");
                logCommon.setCzyhm(loginUser.getYhm());
                logCommon.setTablenames("SYS_USER");
                logCommon.setLy(Utils.getRemortIP(request));
                logCommon.setXw("重置密码,用户id:" + updUser.getUserid());
                logCommon.setJg("成功");
                logCommon.setBz("重置密码,用户id:" + updUser.getUserid());
                logCommon.setCzmk("用户管理");
                logCommon.setCzqsj("mm:" + oldPwd);
                logCommon.setCzhsj("mm:" + updUser.getMm());
                logCommon.setCzmc("Update");
                commonEntryLogService.entryLog(logCommon);
                map.put(Constant.CODE_KEY, "1");
                map.put(Constant.MESSAGE_KEY, "重置密码成功");
            } else {
                map.put(Constant.CODE_KEY, "-2");
                map.put(Constant.MESSAGE_KEY, "用户信息为空，不能重置密码！");
            }
        } else {
            map.put(Constant.CODE_KEY, "-2");
            map.put(Constant.MESSAGE_KEY, "用户信息为空，不能重置密码！");
        }

        return map;
    }

    /**
     * 更新用户其他信息（POST）
     *
     * @param request     HttpServletRequest
     * @param response    HttpServletResponse
     * @param userId      用户ID
     * @param extendDate  扩展日期
     * @param lockUserFlg 操作标识(1:锁定\2:解锁\3:延长用户有效期\4:延长密码有效期)
     * @return JsonString
     */
    @RequestMapping("/lockUser")
    public Map<String, Object> lockUser(HttpServletRequest request, HttpServletResponse response, String userId,
                                        Date extendDate, int lockUserFlg) {
        Map<String, Object> map = new HashMap();
        // 用户验证码
        String userVerCode = "";
        // 用户参数信息
        SysUser userParam;
        // 用户信息
        SysUser userInfo;
        // 更新用的用户信息
        SysUser updInfo = new SysUser();
        SimpleDateFormat dft = new SimpleDateFormat(Constant.DATE_FORMAT);
        if (userId != null && !"".equals(userId)) {
            if (lockUserFlg == 0 && extendDate == null) {
                map.put(Constant.CODE_KEY, "0");
                map.put(Constant.MESSAGE_KEY, "参数为空");
            } else {
                userParam = new SysUser();
                // 设置ID
                userParam.setUserid(userId);
                // 设置密钥
                userParam.setEncryptkey(Utils.getEncryptionKey());
                // 取得用户信息
                userInfo = this.useManageService.selectByUserId(userParam);
                if (userInfo != null) {
                    // 取得登录用户信息
                    SysUser loginUser = (SysUser) Utils.getLoginUserInfo(request);
//                    // 检查验证码
//                    if (checkUserFalsify(request, userInfo, loginUser)) {
//                        map.put(Constant.CODE_KEY, "-5");
//                        map.put(Constant.MESSAGE_KEY, "该用户信息已被篡改，请联系管理员！");
//                        return map;
//                    }
                    // 取得用户状态
                    String status = userInfo.getYhzt();
                    // 备注
                    String remark = "";
                    LogCommon logCommon = new LogCommon();
                    // 操作标识
                    switch (lockUserFlg) {
                        case 1:// 锁定处理
                            if (EnumUserStatus.NORMAL.getValue().equals(status)
                                    || EnumUserStatus.MODIFY_PASSWORD.getValue().equals(status)
                                    || EnumUserStatus.UNLOCKING_MODIFY_PASSWORD.getValue().equals(status)) {
                                if (loginUser != null) {
                                    // 判断登录的用户是否是更新的用户
                                    if (loginUser.getUserid().equals(userId)) {
                                        map.put(Constant.CODE_KEY, "-5");
                                        map.put(Constant.MESSAGE_KEY, "不能锁定当前登录用户！");
                                        return map;
                                    }
                                }
                                updInfo = new SysUser();
                                // 设置用户ID
                                updInfo.setUserid(userId);
                                // 设置用户状态
                                userInfo.setYhzt(EnumUserStatus.MANUAL_LOCKING.getValue());
                                updInfo.setYhzt(EnumUserStatus.MANUAL_LOCKING.getValue());
                                // 设置停用原因
                                updInfo.setTyyy("人工锁定");
                                // 做成用户验证码
                                userVerCode = userInfo.makeUserVerCode();
                                // 设置账户验证码
                                updInfo.setZhmm(Utils.getSHA256Str(userVerCode));

                                logCommon.setXw("锁定用户");
                                remark = "锁定用户,用户id为:" + userInfo.getUserid() + "用户名为:" + userInfo.getYhm();
                            } else {
                                map.put(Constant.CODE_KEY, "0");
                                map.put(Constant.MESSAGE_KEY, "已经锁定，无需再次锁定");
                                return map;
                            }
                            break;
                        case 2:// 解锁处理
                            if (EnumUserStatus.LOCKING.getValue().equals(status)
                                    || EnumUserStatus.MANUAL_LOCKING.getValue().equals(status)) {
                                updInfo = new SysUser();
                                // 设置用户ID
                                updInfo.setUserid(userId);
                                // 设置用户状态
                                userInfo.setYhzt(EnumUserStatus.UNLOCKING_MODIFY_PASSWORD.getValue());
                                updInfo.setYhzt(EnumUserStatus.UNLOCKING_MODIFY_PASSWORD.getValue());
                                // 设置停用原因
                                updInfo.setTyyy("已解锁提示修改密码");
                                // 做成用户验证码
                                userVerCode = userInfo.makeUserVerCode();
                                // 设置登录失败次数
                                updInfo.setDlsbcs("0");

                                logCommon.setXw("解锁用户");
                                remark = "解锁用户,用户id为:" + userInfo.getUserid() + "用户名为:" + userInfo.getYhm();
                            } else {
                                map.put(Constant.CODE_KEY, "0");
                                map.put(Constant.MESSAGE_KEY, "已经解锁，无需再次解锁");
                                return map;
                            }
                            break;
                    }

                    // 设置账户验证码
                    updInfo.setZhmm(Utils.getSHA256Str(userVerCode));
                    updInfo.setUpdateip(Utils.getRemortIP(request));
                    updInfo.setUpdatetime(new Date());
                    updInfo.setUpdateuid(loginUser.getYhm());
                    map = this.useManageService.updateUserInfo(updInfo);
                    // 记录日志
                    logCommon.setCdbh("0019");
                    logCommon.setCzyhm(loginUser.getYhm());
                    logCommon.setTablenames("SYS_USER");
                    logCommon.setLy(Utils.getRemortIP(request));
                    logCommon.setJg("成功");
                    logCommon.setBz(remark + logCommon.getJg());
                    logCommon.setCzmk("用户管理");
                    logCommon.setCzqsj("用户状态：" + EnumUserStatus.getNameById(status));
                    logCommon.setCzhsj("用户状态：" + EnumUserStatus.getNameById(updInfo.getYhzt()));
                    logCommon.setCzmc("Update");
                    commonEntryLogService.entryLog(logCommon);
                } else {
                    map.put(Constant.CODE_KEY, "-2");
                    map.put(Constant.MESSAGE_KEY, "用户信息为空，不能操作！");
                }
            }
        } else {
            map.put(Constant.CODE_KEY, "0");
            map.put(Constant.MESSAGE_KEY, "参数信息为空");
        }
        return map;
    }


    /**
     * 检查用户信息
     *
     * @param userInfo 用户信息
     * @return JsonString
     */
    private Map<String, Object> checkUserInfo(SysUser userInfo) {
        Map<String, Object> map = new HashMap();

        if (userInfo.getYhm() == null || "".equals(userInfo.getYhm())) {
            map.put(Constant.CODE_KEY, "0");
            map.put(Constant.MESSAGE_KEY, "用户名不能为空");
            return map;
        } else if (userInfo.getYhm().length() > 32) {
            map.put(Constant.CODE_KEY, "0");
            map.put(Constant.MESSAGE_KEY, "用户名长度不能大于32位");
            return map;
        }

        if (userInfo.getXm() == null || "".equals(userInfo.getXm())) {
            map.put(Constant.CODE_KEY, "0");
            map.put(Constant.MESSAGE_KEY, "姓名不能为空");
            return map;
        } else if (userInfo.getXm().length() > 15) {
            map.put(Constant.CODE_KEY, "0");
            map.put(Constant.MESSAGE_KEY, "姓名长度不能大于15位");
            return map;
        }


        if (userInfo.getJsbhid() == null || "".equals(userInfo.getJsbhid())) {
            map.put(Constant.CODE_KEY, "0");
            map.put(Constant.MESSAGE_KEY, "角色编号不能为空");
            return map;
        }


        // 判断用户名是否存在
        int count = this.useManageService.selectYhmCheck(userInfo);
        if (count != 0) {
            map.put(Constant.CODE_KEY, "0");
            map.put(Constant.MESSAGE_KEY, "用户名已存在，请重新输入");
            return map;
        }


        return null;
    }

    /**
     * 检查用户信息是否被篡改
     *
     * @param userInfo  用户信息
     * @param loginUser 登录用户信息
     * @param request
     * @return false：未被篡改，true：被篡改
     */
    private boolean checkUserFalsify(HttpServletRequest request, SysUser userInfo, SysUser loginUser) {
        boolean isFalsify = false;
        // 做成用户验证码
        String userVerCode = userInfo.makeUserVerCode();
        // 验证码不一致
        if (!userInfo.getZhmm().equals(Utils.getSHA256Str(userVerCode))) {
            isFalsify = true;
            // 记录日志
            LogCommon logCommon = new LogCommon();
            logCommon.setCdbh("0019");
            logCommon.setCzyhm(loginUser.getYhm());
            logCommon.setTablenames("SYS_USER");
            logCommon.setLy(Utils.getRemortIP(request));
            logCommon.setXw("检查用户信息是否被篡改");
            logCommon.setJg("被篡改");
            logCommon.setBz(logCommon.getXw() + logCommon.getJg());
            logCommon.setCzmk("用户管理");
            logCommon.setCzqsj("");
            logCommon.setCzhsj("");
            logCommon.setCzmc("Check");
            commonEntryLogService.entryLog(logCommon);
        }
        return isFalsify;
    }

    /**
     * 检查登录时间段
     *
     * @param userInfo 用户信息
     * @return false：未通过，true：通过
     */
    private boolean checkLoginTime(SysUser userInfo) {
        boolean isFalsify = true;
        String startTime = userInfo.getDlsjks().replace(":", "");
        String endTime = userInfo.getDlsjdjs().replace(":", "");
        // 判断开始时间是否大于结束时间
        if (startTime.compareTo(endTime) > 0) {
            isFalsify = false;
        }
        return isFalsify;
    }

    /**
     * 检查允许登录IP
     *
     * @param userInfo 用户信息
     * @return false：未通过，true：通过
     */
    private boolean checkIpList(SysUser userInfo) {
        boolean isFalsify = true;
        String regex = "(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])" +
                "(\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])){3}(\\s*\\|\\s*(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])" +
                "(\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])){3})*";
        String ipList = userInfo.getIplist();
        if (ipList.indexOf("0:0:0:0:0:0:0:1") != -1) {
            ipList = ipList.replace("0:0:0:0:0:0:0:1", "");
        }
        if (!"".equals(ipList)) {
            if (ipList.substring(0, 1).equals("|")) {
                ipList = ipList.substring(1, ipList.length());
            }
            if (ipList.substring(ipList.length() - 1, ipList.length()).equals("|")) {
                ipList = ipList.substring(0, ipList.length() - 1);
            }
        } else {
            return isFalsify;
        }
        // 判断允许登录IP是否符合IP格式
        if (!ipList.matches(regex)) {
            isFalsify = false;
        }
        return isFalsify;
    }

}
