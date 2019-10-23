package cn.xiongdi.ocdm.server.service.sys.impl;


import cn.xiongdi.ocdm.common.constants.Constant;
import cn.xiongdi.ocdm.common.utils.*;
import cn.xiongdi.ocdm.mapper.dao.LogLoginMapper;
import cn.xiongdi.ocdm.mapper.dao.SysMenuMapper;
import cn.xiongdi.ocdm.mapper.dao.SysUserMapper;
import cn.xiongdi.ocdm.mapper.model.*;
import cn.xiongdi.ocdm.server.dto.*;
import cn.xiongdi.ocdm.server.service.common.CommonEntryLogService;
import cn.xiongdi.ocdm.server.service.sys.ConfigManageService;
import cn.xiongdi.ocdm.server.service.sys.SignLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @创建人 lzy
 * @创建时间 2018-09-11
 * @描述 登录服务
 */
@Service
public class SignLoginServiceImpl implements SignLoginService {

    @Autowired
    private SysUserMapper userInfoMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private LogLoginMapper logLoginMapper;

    @Autowired
    private CommonEntryLogService commonEntryLogService;

    @Autowired
    private ConfigManageService configManageService;

    private static final String VALIDATE_IP = "1";

    private Map canNotLoginMessageList = new HashMap<String, String>() {{
        put(EnumUserStatus.FORBIDDEN.getValue(), "用户已被删除！请联系管理员恢复。");
        put(EnumUserStatus.LOCKING.getValue(), "登录失败次数过多，已被锁定！");
        put(EnumUserStatus.USER_INVALID.getValue(), "用户已失效，请联系管理员延长有效期！");
        put(EnumUserStatus.MANUAL_LOCKING.getValue(), "用户已被人工锁定，请联系管理员解锁！");
        put(EnumUserStatus.PASSWORD_INVALID.getValue(), "密码已失效，请联系管理员延长密码有效期！");
        put(EnumUserStatus.INFORMATION_TAMPERING.getValue(), "用户信息已被篡改，请先修改密码并查看个人信息！");
        put(EnumUserStatus.MODIFY_PASSWORD.getValue(), "初次登陆，请先修改密码！");
        put(EnumUserStatus.UNLOCKING_MODIFY_PASSWORD.getValue(), "用户已解锁/延长有效期，请先修改密码！");
    }};

    private Map logLoginStatusMapping = new HashMap<String, String>() {{
        put(EnumUserStatus.FORBIDDEN.getValue(), EnumLoginStatus.DELETE.getValue());
        put(EnumUserStatus.LOCKING.getValue(), EnumLoginStatus.LOCKING.getValue());
        put(EnumUserStatus.USER_INVALID.getValue(), EnumLoginStatus.USER_INVALID.getValue());
        put(EnumUserStatus.MANUAL_LOCKING.getValue(), EnumLoginStatus.LOCKING.getValue());
        put(EnumUserStatus.PASSWORD_INVALID.getValue(), EnumLoginStatus.PASSWORD_INVALID.getValue());
        put(EnumUserStatus.INFORMATION_TAMPERING.getValue(), EnumLoginStatus.INFORMATION_TAMPERING.getValue());
        put(EnumUserStatus.MODIFY_PASSWORD.getValue(), EnumLoginStatus.OTHER_STATUS.getValue());
        put(EnumUserStatus.UNLOCKING_MODIFY_PASSWORD.getValue(), EnumLoginStatus.DELETE.getValue());
    }};

    /**
     * 获取最大在线用户数
     *
     * @return
     */
    private boolean isMaxConnection() {

        int maxOnline = 0;
        ParamRq paramRq = new ParamRq();
        paramRq.setCsdh("maxOnlineCount");
        paramRq.setCslb("系统");
        List<SysParam> sysParamList = configManageService.getByCon(paramRq);

        if (sysParamList.size() > 0) {
            maxOnline = Integer.parseInt(sysParamList.get(0).getCssm());
        }
        int onlineCount = userInfoMapper.selectOnLimeCount();
        return maxOnline == onlineCount;

    }

    /**
     * 是否规定时间登录
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    private boolean isOnTime(String start, String end) {

        String startTime = "";
        String StartEnd = "";

        ParamRq paramRq = new ParamRq();
        paramRq.setCsdh("startTime");
        paramRq.setCslb("系统");
        List<SysParam> sysParamList = configManageService.getByCon(paramRq);
        if (sysParamList.size() > 0) {
            startTime = sysParamList.get(0).getCssm();
        }
        paramRq.setCsdh("endTime");
        sysParamList = configManageService.getByCon(paramRq);
        if (sysParamList.size() > 0) {
            StartEnd = sysParamList.get(0).getCssm();
        }
        if (start == null || end == null) {
            start = startTime;
            end = StartEnd;
        }
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date now = null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(DateUtil.now()));
            beginTime = df.parse(start);
            endTime = df.parse(end);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return now.after(beginTime) && now.before(endTime);
    }

    /**
     * 获取
     *
     * @param days 天数
     * @return 返回日期
     * @throws ParseException
     */
    private Date getReDate(int days) throws ParseException {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = DateUtil.now();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(5, date.get(5) - days);
        Date endDate = dft.parse(dft.format(date.getTime()));
        return endDate;
    }

    /**
     * 是否过期
     *
     * @param date 日期
     * @return
     */
    private boolean isOnDate(Date date) {
        try {
            Date now = getReDate(1);
            return now.before(date);
        } catch (ParseException var5) {
            var5.printStackTrace();
            return false;
        }
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    private List<SysUser> getUserByName(String userName) {
        SysUser userInfo = new SysUser();
        userInfo.setYhm(userName);
        userInfo.setDeleteflg(Long.parseLong("0"));
        userInfo.setEncryptkey(Utils.getEncryptionKey());
        return userInfoMapper.selectByUserName(userInfo);
    }

    /**
     * 删除用户信息
     *
     * @param userList 用户清单
     */
    private void deleteUserList(List<SysUser> userList) {

        // 删除用户重新注册
        for (SysUser userItem : userList) {
            SysUser user = new SysUser();
            user.setDeleteflg(1L);
            userItem.setDeleteflg(1L);
            user.setUserid(userItem.getUserid());
            user.setZhmm(Utils.getSHA256Str(userItem.makeUserVerCode()));
            userInfoMapper.updateByPrimaryKeySelective(user);
        }
    }

    /**
     * 用户名和密码为空的校验
     *
     * @param loginDto 用户信息
     * @return
     */
    private boolean isNullUserInfo(LoginDto loginDto) {
        if (loginDto == null) {
            return true;
        }

        if (StringUtils.isEmptyOrNull(loginDto.getUsername()) || StringUtils.isEmptyOrNull(loginDto.getMypassword())) {
            return true;
        }
        return false;
    }

    /**
     * 密码长度校验
     *
     * @param password 密码长度
     * @return
     */
    private boolean isPasswordLength(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        return true;
    }

    /**
     * 密码复杂度校验
     *
     * @param password 密码
     * @return
     */
    private boolean isComplexityFormat(String password) {
        // String complexityHighRule = "^^(?![a-zA-z]+$)(?!\\d+$)(?![!@#$%^&*_-]+$)(?![a-zA-z\\d]+$)" +
        // "(?![a-zA-z!@#$%^&*_-]+$)(?![\\d!@#$%^&*_-]+$)[a-zA-Z\\d!@#$%^&*_-]+$";
        // 字母、数组、特殊符号（不是字母，数字，下划线，汉字的字符）
        String complexityHighRule = "^^(?![A-Za-z0-9]+$)(?![A-Za-z\\W]+$)(?![0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";

        if (!password.matches(complexityHighRule)) {
            return false;
        }
        return true;
    }

    /**
     * 用户名和密码格式校验
     *
     * @param loginDto 密码信息
     * @return
     */
    private BaseResult validateLoginInfoFormat(LoginDto loginDto) {
        BaseResult result = new BaseResult();
        result.setFailure();

        if (isNullUserInfo(loginDto)) {
            result.setMessage("户名或密码为空！");
            return result;
        }
        if (!isPasswordLength(loginDto.getMypassword())) {

            result.setMessage("密码长度不小于8位！");
            return result;
        }

        if (!isComplexityFormat(loginDto.getMypassword())) {
            result.setMessage("密码必须包含英文字符、数字及特殊符号！");
            return result;
        }
        result.setSuccess();
        return result;
    }

    /**
     * 客户端Ip地址校验
     *
     * @param clientIp     Ip
     * @param confClientIp
     * @return
     */
    private boolean validateIp(String clientIp, String confClientIp) {

        String validateIP = "0";
        ParamRq paramRq = new ParamRq();
        paramRq.setCsdh("validateIP");
        paramRq.setCslb("系统");
        List<SysParam> sysParamList = configManageService.getByCon(paramRq);
        if (sysParamList.size() > 0) {
            validateIP = sysParamList.get(0).getCssm();
        }

        if (VALIDATE_IP.equals(validateIP)) {
            if (confClientIp.indexOf("0:0:0:0:0:0:0:1") != -1) {
                confClientIp = confClientIp.replace("0:0:0:0:0:0:0:1", "127.0.0.1");
            }

            if (confClientIp == null || confClientIp.indexOf(clientIp) == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否为不能登录的状态
     *
     * @param userStatus 状态
     * @return
     */
    private boolean isCannotLoginStatus(String userStatus) {
        if (EnumUserStatus.FORBIDDEN.getValue().equals(userStatus) ||
                EnumUserStatus.LOCKING.getValue().equals(userStatus) ||
                EnumUserStatus.USER_INVALID.getValue().equals(userStatus) ||
                EnumUserStatus.MANUAL_LOCKING.getValue().equals(userStatus)) {
            return true;
        }
        return false;
    }

    /**
     * 判断用户信息是否篡改
     *
     * @param user 用户信息
     * @return
     */
    private boolean isTamperUserInfo(SysUser user) {

        String zhyzm = Utils.getSHA256Str(user.makeUserVerCode());
        if (!zhyzm.equals(user.getZhmm())) {
            return true;
        }
        return false;
    }

    /**
     * 获取最大登录次数
     *
     * @return
     */
    private int getMaxLoginTimes() {

        int maxLoginTimes = 0;
        ParamRq paramRq = new ParamRq();
        paramRq.setCsdh("maxFailLoginTime");
        paramRq.setCslb("系统");
        List<SysParam> sysParamList = configManageService.getByCon(paramRq);
        if (sysParamList.size() > 0) {
            maxLoginTimes = Integer.parseInt(sysParamList.get(0).getCssm());
        }
        return maxLoginTimes;
    }

    /**
     * 是否超过最大登录次数
     *
     * @param times 登录次数
     * @return
     */
    private boolean isOverLoginTimes(int times) {

        if (times >= getMaxLoginTimes()) {
            return true;
        }
        return false;
    }

    /**
     * 判断用户密码是否正确
     *
     * @param userInfo      用户信息
     * @param inputPassword 密码信息
     * @return
     */
    private boolean isRightPassword(SysUser userInfo, String inputPassword) {

        if (userInfo.getMm().equals(Utils.getSHA256Str(userInfo.getPassWord(inputPassword)))) {
            return true;
        }
        return false;
    }

    /**
     * 登录次数更新
     *
     * @param failLoginTimes 登录失败次数
     * @param userID         用户ID
     */
    private void updateFailLoginTime(int failLoginTimes, String userID) {

        SysUser user = new SysUser();
        user.setUserid(userID);
        user.setDlsbcs(failLoginTimes + "");
        userInfoMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * /是否需要修改密码
     *
     * @param userStatus 用户状态
     * @return
     */
    private boolean isNeedModifyPassword(String userStatus) {

        if (EnumUserStatus.MODIFY_PASSWORD.getValue().equals(userStatus) ||
                EnumUserStatus.UNLOCKING_MODIFY_PASSWORD.getValue().equals(userStatus)) {
            return true;
        }
        return false;
    }

    /**
     * 密码失效
     *
     * @param userStatus 用户状态
     * @return
     */
    private boolean isPasswordAbate(String userStatus) {
        //注释了一个条件 || EnumUserStatus.INFORMATION_TAMPERING.getValue().equals(userStatus)
        if (EnumUserStatus.PASSWORD_INVALID.getValue().equals(userStatus)) {
            return true;
        }
        return false;
    }

    /**
     * 登录日志
     *
     * @param clientIp  Ip
     * @param userName  用户名
     * @param isOkLogin 是否登录成功
     * @param status    登录状态
     * @param message   Log Message
     */
    private void insertLogLogin(String clientIp, String userName, boolean isOkLogin, String status, String message,
                                String dllx) {
        LogLogin logLogin = new LogLogin();
        logLogin.setDlip(clientIp);
        // logLogin.setDlmac(Utils.getMACAddress(clientIp));
        Date now = DateUtil.now();
        logLogin.setDlsj(now);
        logLogin.setDlyhm(userName);
        logLogin.setDllx(dllx);
        logLogin.setDljg("失败");
        logLogin.setCreateip(clientIp);
        logLogin.setCreatetime(DateUtil.now());
        if (isOkLogin) {
            logLogin.setDljg("成功");
        }
        logLogin.setBz(message);
        logLogin.setDlzt(Long.parseLong(status));
        this.logLoginMapper.insert(logLogin);
    }

    /**
     * 用户登录验证
     *
     * @param loginDto
     * @return
     */
    private BaseResult validateLogin(LoginDto loginDto, List<SysUser> userList, String clientIp) {

        BaseResult result = new BaseResult();
        result.setFailure();

        String messageLog;

        // 用户不存在的场合
        if (userList.size() < 1) {
            result.setMessage("用户不存在，请重新输入！");
            return result;
        }

        // 用户名重复的场合
        if (userList.size() > 1) {
            // 删除用户重新注册
            deleteUserList(userList);
            result.setMessage("用户名重复，信息异常，用户已删除，请联系管理员重新注册！");
            return result;
        }

        SysUser userInfo = userList.get(0);

        // 用户是否在规定登录时间段登录系统
//        if (!isOnTime(userInfo.getDlsjks(), userInfo.getDlsjdjs())) {
//
//            messageLog = "由于不在登陆时间段(" + userInfo.getDlsjks() + "-" + userInfo.getDlsjdjs() + ")," + "用户" + userInfo
//                    .getYhm() + "在" + clientIp + "登录系统失败,时间" + DateUtil.dateFormat(DateUtil.now(), Constant
//                    .DATETIME_FORMAT);
//            insertLogLogin(clientIp, userInfo.getYhm(), false, EnumLoginStatus.NOT_IN_TIME.getValue(), messageLog,
//                    "登录");
//
//            result.setMessage("当前时间不在登陆时间范围内，请在规定时间（" + userInfo.getDlsjks() + "--" + userInfo.getDlsjdjs() + "）登陆系统！");
//            return result;
//        }

        // 判断用户IP地址是否
        if (!validateIp(clientIp, userInfo.getIplist())) {

            messageLog = "由于ip登录限制,用户" + userInfo.getYhm() + "在" + clientIp + "登录系统失败,时间" + DateUtil.dateFormat
                    (DateUtil.now(), Constant.DATETIME_FORMAT);
            insertLogLogin(clientIp, userInfo.getYhm(), false, EnumLoginStatus.IP_NOT_ACCESS.getValue(), messageLog,
                    "登录");

            result.setMessage("当前客户端[" + clientIp + "]不允许该用户登陆！");
            return result;
        }
        // 是否为不能登录的状态
        if (isCannotLoginStatus(userInfo.getYhzt())) {
            messageLog = "由于用户[" + userInfo.getYhm() + "]状态为" + EnumUserStatus.getNameById(userInfo.getYhzt()) + ",在"
                    + clientIp + "登录系统失败,时间" + DateUtil.dateFormat(DateUtil.now(), Constant.DATETIME_FORMAT);
            insertLogLogin(clientIp, userInfo.getYhm(), false, logLoginStatusMapping.get(userInfo.getYhzt()).toString
                    (), messageLog, "登录");

            result.setMessage(canNotLoginMessageList.get(userInfo.getYhzt()).toString());
            return result;
        }
        // 用户是否过期
//        if (!isOnDate(userInfo.getYhyxq())) {
//
//            messageLog = "由于用户已过有效期,用户" + userInfo.getYhm() + "在" + clientIp + "登录系统失败,时间" + DateUtil.dateFormat
//                    (DateUtil.now(), Constant.DATETIME_FORMAT);
//            insertLogLogin(clientIp, userInfo.getYhm(), false, EnumLoginStatus.USER_INVALID.getValue(), messageLog,
//                    "登录");
//
//            result.setMessage("用户已过有效期，请先联系管理员延长有效期！");
//            return result;
//        }
        // 密码是否过期
//        if (!isOnDate(userInfo.getMmyxq())) {
//
//            messageLog = "由于密码已过有效期,用户" + userInfo.getYhm() + "在" + clientIp + "登录系统失败,时间" + DateUtil.dateFormat
//                    (DateUtil.now(), Constant.DATETIME_FORMAT);
//            insertLogLogin(clientIp, userInfo.getYhm(), false, EnumLoginStatus.PASSWORD_INVALID.getValue(),
//                    messageLog, "登录");
//
//            result.setMessage("密码已过有效期，请联系管理员延迟密码有效期！");
//            return result;
//        }

        // 用户信息是否被篡改
//        if (isTamperUserInfo(userInfo)) {
//            return insertTamperLog(userInfo, clientIp);
//        }

        // 登录失败次数过多判断
        if (isOverLoginTimes(Integer.parseInt(userInfo.getDlsbcs()))) {

            lockUser(userInfo, clientIp);

            messageLog = "由于用户锁定(登录失败次数过多),用户" + userInfo.getYhm() + "在" + clientIp + "登录系统失败,时间" + DateUtil
                    .dateFormat(DateUtil.now(), Constant.DATETIME_FORMAT);
            insertLogLogin(clientIp, userInfo.getYhm(), false, EnumLoginStatus.LOCKING.getValue(), messageLog, "登录");

            insertEntryLog(loginDto.getUsername(), clientIp, "用户登录", "超出最大用户登录失败次数", "登录失败", "", "");

            result.setMessage("登录失败次数过多，用户已被锁定！请联系管理员解锁");
            return result;
        }

        // 密码是否正确判断
        if (!isRightPassword(userInfo, loginDto.getMypassword())) {

            int failTimes = Integer.parseInt(userInfo.getDlsbcs()) + 1;
            updateFailLoginTime(failTimes, userInfo.getUserid());

            messageLog = "由于密码输入错误,用户" + userInfo.getYhm() + "在" + clientIp + "登录系统失败,时间" + DateUtil.dateFormat
                    (DateUtil.now(), Constant.DATETIME_FORMAT);
            insertLogLogin(clientIp, userInfo.getYhm(), false, EnumLoginStatus.PASSWORD_ERROR.getValue(), messageLog,
                    "登录");

            result.setMessage("密码错误，剩余用户登录次数:" + (getMaxLoginTimes() - failTimes + "次"));
            return result;
        }

        // 用户密码失效或信息篡改,不能登录
//        if (isPasswordAbate(userInfo.getYhzt())) {
//            messageLog = "由于用户[" + userInfo.getYhm() + "]状态为" + EnumUserStatus.getNameById(userInfo.getYhzt()) + ",在"
//                    + clientIp + "登录系统失败,时间" + DateUtil.dateFormat(DateUtil.now(), Constant.DATETIME_FORMAT);
//            insertLogLogin(clientIp, userInfo.getYhm(), false, logLoginStatusMapping.get(userInfo.getYhzt()).toString
//                    (), messageLog, "登录");
//
//            result.setMessage(canNotLoginMessageList.get(userInfo.getYhzt()).toString());
//            return result;
//        }

        result.setSuccess();
        return result;
    }

    /**
     * 登录
     *
     * @param loginDto
     * @return
     */
    public ResultMap login(HttpServletRequest request, LoginDto loginDto) {

        String clientIp = Utils.getRemortIP(request);

        // 验证系统是否达到最大并发访问量（最大用户登录数）
        if (isMaxConnection()) {
            // 记录Audit日志
            insertEntryLog(loginDto.getUsername(), clientIp, "用户登录", "超出最大用户在线数", "登录失败", "", "");
            return ResultMap.error("系统已达到最大用户登录数，请稍后重试！");
        }

        // 输入格式校验
        BaseResult validateResult = validateLoginInfoFormat(loginDto);
        if (!validateResult.isOk()) {
            return ResultMap.error(validateResult.getMessage());
        }

        // 查询用户信息
        List<SysUser> userList = getUserByName(loginDto.getUsername());

        // 登录业务逻辑校验
        validateResult = validateLogin(loginDto, userList, clientIp);
        if (!validateResult.isOk()) {
            return ResultMap.error(Constant.ZERO, validateResult.getMessage());
        }

        // 查询用户信息
        SysUser userInfo = getUserByID(userList.get(0).getUserid());

        // 需要修改密码
        if (isNeedModifyPassword(userInfo.getYhzt())) {
            // 设置用户需要修改密码，修改密码前判断Session，防止直接修改密码，跳过身份确认。
            request.getSession().setAttribute("userID", userList.get(0).getUserid());
            return ResultMap.ok(Constant.TWO, canNotLoginMessageList.get(userInfo.getYhzt()).toString());
        }

        // 设置api权限
        userInfo.setListPermission(getPermissionList(userInfo.getJsbhid().toString(), userInfo.getSfjy()));
        // 记录session
        request.getSession().setAttribute(Constant.LOGIN_USER_KEY, userInfo);

        return setSuccessLogin(request, userInfo, new SysUser(), clientIp);
    }

    /**
     * 登录失败插入日志
     *
     * @param userName 用户名
     * @param ip       Ip
     * @param czmc     操作模块
     * @param xw       行为
     * @param jg       结果
     * @param czqsj    操作前数据
     * @param czhsj    操作后数据
     */
    private void insertEntryLog(String userName, String ip, String czmc, String xw, String jg, String czqsj, String
            czhsj) {
        LogCommon logCommon = new LogCommon();
        logCommon.setCdbh("0020");
        logCommon.setCzyhm(userName);
        logCommon.setTablenames("SYS_USER");
        logCommon.setLy(ip);
        logCommon.setXw(xw); // 行为
        logCommon.setJg(jg); // 结果
        logCommon.setBz(logCommon.getXw() + logCommon.getJg());
        logCommon.setCzmk("用户登录"); // 模块
        logCommon.setCzqsj(czqsj); // 操作前数据
        logCommon.setCzhsj(czhsj); // 操作后数据
        logCommon.setCzmc(czmc);
        commonEntryLogService.entryLog(logCommon);
    }

    /**
     * 修改密码校验
     *
     * @param passwordInput 密码信息
     * @return
     */
    private BaseResult validateNewPasswordFormat(PasswordDto passwordInput) {
        BaseResult result = new BaseResult();
        result.setFailure();

        if (passwordInput == null) {
            result.setMessage("新密码应长度不小于8位，且包含英文字符、数字及特殊符号！");
            return result;
        }

        if (!isPasswordLength(passwordInput.getPassword1())
                || !isComplexityFormat(passwordInput.getPassword1())) {

            result.setMessage("新密码应长度不小于8位，且包含英文字符、数字及特殊符号！");
            return result;
        }
        if (!passwordInput.getPassword1().equals(passwordInput.getPassword2())) {
            result.setMessage("新密码和确认密码不一致，请重新输入！");
            return result;
        }

        if (passwordInput.getPassword1().equals(passwordInput.getOldpassowrd())) {
            result.setMessage("新密码与原密码不能相同，请重新输入！");
            return result;
        }

        result.setSuccess();
        return result;
    }

    /**
     * 修改密码校验
     *
     * @param userInfo      用户信息
     * @param passwordInput 用户信息
     * @param clientIp      Ip信息
     * @return
     */
    private BaseResult validateNewPassword(SysUser userInfo, PasswordDto passwordInput, String clientIp) {
        BaseResult result = new BaseResult();
        result.setFailure();

        String messageLog;
        // 用户是否存在
        if (userInfo == null) {
            result.setMessage("用户已删除，请联系管理员重新注册！");
            return result;
        }

        // 登录失败次数过多判断
        if (isOverLoginTimes(Integer.parseInt(userInfo.getDlsbcs()))) {

            lockUser(userInfo, clientIp);
            messageLog = "由于用户锁定,用户" + userInfo.getYhm() + "在" + clientIp + "登录系统失败,时间" + DateUtil.dateFormat
                    (DateUtil.now(), Constant.DATETIME_FORMAT);
            insertLogLogin(clientIp, userInfo.getYhm(), false, EnumLoginStatus.LOCKING.getValue(), messageLog, "登录");
            insertEntryLog(userInfo.getYhm(), clientIp, "用户登录", "超出最大用户登录失败次数", "登录失败", "", "");
            result.setMessage("身份鉴别失败次数过多，用户已被锁定！请联系管理员解锁！");
            return result;
        }
        if (!isRightPassword(userInfo, passwordInput.getOldpassowrd())) {
            result.setMessage("原密码不正确，请重新输入原密码");
            return result;
        }
        result.setSuccess();
        return result;
    }

    /**
     * 密码重置
     *
     * @param request       请求
     * @param userID        用户名
     * @param clientIp      Ip
     * @param passwordInput 密码信息
     * @return
     */
    public ResultMap resetPassword(HttpServletRequest request, String userID, String clientIp, PasswordDto
            passwordInput) {

        if (userID.length() == 0) {
            return ResultMap.error(Constant.ZERO, "登录可能已过期，请回到登录画面重新登录！");
        }

        BaseResult validateResult = validateNewPasswordFormat(passwordInput);
        if (!validateResult.isOk()) {
            return ResultMap.error(Constant.ZERO, validateResult.getMessage());
        }

        // 查询用户信息
        SysUser userInfo = getUserByID(userID);

        // 密码是否正确
        validateResult = validateNewPassword(userInfo, passwordInput, clientIp);
        if (!validateResult.isOk()) {

            return ResultMap.error(Constant.ZERO, validateResult.getMessage());
        }

        // 更新用户信息
        SysUser updateUserInfo = new SysUser();
        updateUserInfo.setMm(Utils.getSHA256Str(userInfo.getPassWord(passwordInput.getPassword1())));
        userInfo.setMm(updateUserInfo.getMm());

        userInfo.setListPermission(getPermissionList(userInfo.getJsbhid().toString(), userInfo.getSfjy()));

        request.getSession().invalidate();
        request.getSession().setAttribute(Constant.LOGIN_USER_KEY, userInfo);

        insertEntryLog(userInfo.getYhm(), clientIp, "修改密码", "修改用户密码 ，用户id为:" + userInfo.getUserid(), "成功", "", "");

        return setSuccessLogin(request, userInfo, updateUserInfo, clientIp);
    }

    /**
     * 用户确认信息为空的判断
     *
     * @param userInfoDto 用户信息
     * @return
     */
    private boolean isNullConfirmInfo(UserInfoDto userInfoDto) {
        if (userInfoDto == null) {
            return true;
        }

        if (StringUtils.isEmptyOrNull(userInfoDto.getXm())
                || StringUtils.isEmptyOrNull(userInfoDto.getYhbh())
                || StringUtils.isEmptyOrNull(userInfoDto.getSfzh())) {
            return true;
        }
        return false;
    }

    /**
     * 插入篡改日志
     *
     * @param userInfo 用户信息
     * @param clientIp IP
     * @return
     */
    private BaseResult insertTamperLog(SysUser userInfo, String clientIp) {
        BaseResult result = new BaseResult();
        result.setFailure();
        String messageLog = "由于信息篡改,用户" + userInfo.getYhm() + "在" +
                clientIp + "登录系统失败,时间" + DateUtil.dateFormat(DateUtil.now(), Constant.DATETIME_FORMAT);
        insertLogLogin(clientIp, userInfo.getYhm(), false, EnumLoginStatus.INFORMATION_TAMPERING.getValue(),
                messageLog, "登录");

        insertEntryLog(userInfo.getYhm(), clientIp, "用户信息确认", "确认身份", "用户信息被篡改", "", "");

        result.setMessage("用户信息被篡改，请联系管理员！");
        return result;
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return
     */
    private SysUser getUserByID(String userId) {
        if (StringUtils.isEmptyOrNull(userId)) {
            return null;
        }
        SysUser user = new SysUser();
        user.setUserid(userId);
        user.setEncryptkey(Utils.getEncryptionKey());
        return userInfoMapper.selectByUserId(user);
    }


    /**
     * 锁定用户
     *
     * @param oldUserInfo 修改前用户信息
     * @param clientIP    用户IP
     */
    private void lockUser(SysUser oldUserInfo, String clientIP) {

        String userStatus = oldUserInfo.getYhzt();
        oldUserInfo.setYhzt(EnumUserStatus.LOCKING.getValue());
        SysUser newUserInfo = new SysUser();
        newUserInfo.setZhmm(Utils.getSHA256Str(oldUserInfo.makeUserVerCode()));
        newUserInfo.setUserid(oldUserInfo.getUserid());
        newUserInfo.setYhzt(EnumUserStatus.LOCKING.getValue());
        newUserInfo.setTyyy("登陆失败次数过多");
        newUserInfo.setUpdateip(clientIP);
        newUserInfo.setUpdatetime(DateUtil.now());
        newUserInfo.setUpdateuid(oldUserInfo.getYhm());
        newUserInfo.setBz2(userStatus); // 保存前次用户状态

        userInfoMapper.updateByPrimaryKeySelective(newUserInfo);

    }

    /**
     * 更新用户登录信息
     *
     * @param oldUserInfo 更新前用户数据
     * @param newUserInfo 更新后用户数据
     * @param clientIP
     */
    private void updateLoginSuccessInfo(SysUser oldUserInfo, SysUser newUserInfo, String clientIP) {

        String userStatus = oldUserInfo.getYhzt();
        oldUserInfo.setYhzt(EnumUserStatus.NORMAL.getValue());
        newUserInfo.setZhmm(Utils.getSHA256Str(oldUserInfo.makeUserVerCode()));
        newUserInfo.setUserid(oldUserInfo.getUserid());
        newUserInfo.setYhzt(EnumUserStatus.NORMAL.getValue());
        newUserInfo.setTyyy("正常登陆");
        newUserInfo.setDlip(clientIP);
        newUserInfo.setScdlsj(DateUtil.now());
        newUserInfo.setDlsbcs("0");
        newUserInfo.setDlzt("1");
        newUserInfo.setYhdlcs(oldUserInfo.getYhdlcs() + 1L);
        newUserInfo.setUpdateip(clientIP);
        newUserInfo.setUpdatetime(DateUtil.now());
        newUserInfo.setUpdateuid(oldUserInfo.getYhm());
        newUserInfo.setBz2(userStatus);  // 保存前次用户状态

        userInfoMapper.updateByPrimaryKeySelective(newUserInfo);
    }

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     */
    public void updateUserStatus(String userId, String status) {
        SysUser user = new SysUser();
        user.setUserid(userId);
        user.setDlzt(status);
        userInfoMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 设置用户登录成功处理
     *
     * @param request     用户请求
     * @param oldUserInfo 旧的用户信息
     * @param newUserInfo 输入的用户信息
     * @param clientIp    Ip
     * @return
     */
    private ResultMap setSuccessLogin(HttpServletRequest request, SysUser oldUserInfo, SysUser newUserInfo, String
            clientIp) {

        String message = "用户" + oldUserInfo.getYhm() + "在" + clientIp + "登录系统成功,时间" + DateUtil.dateFormat(DateUtil
                .now(), Constant.DATETIME_FORMAT);
        insertLogLogin(clientIp, oldUserInfo.getYhm(), true, EnumLoginStatus.NORMAL.getValue(), message, "登录");

        // 更新用户信息
        updateLoginSuccessInfo(oldUserInfo, newUserInfo, clientIp);
        return ResultMap.ok().setCode(Constant.ONE);
    }

    /**
     * 设置菜单数据
     *
     * @param menu 菜单
     * @return 显示对象
     */
    private MenuDto setMenu(SysMenu menu) {
        MenuDto menuDto = new MenuDto();
        menuDto.setMenuid(menu.getMenuid().toString());
        menuDto.setMc(menu.getMc());
        menuDto.setPic(menu.getPic());
        menuDto.setSjcdid(menu.getSjcdid().toString());
        menuDto.setUrl(menu.getUrl());
        return menuDto;
    }

    /**
     * 获取菜单树
     *
     * @param request 用户请求
     * @return
     */
    public ResultMap getMenuTreeByRole(HttpServletRequest request) {

        SysUser user = (SysUser) request.getSession().getAttribute(Constant.LOGIN_USER_KEY);

        String roleID = user.getJsbhid().toString();
        String sfjy = user.getSfjy();

        // 菜单树形结构
        ArrayList<MenuDto> menuTree = new ArrayList<MenuDto>();

        // 菜单List，按照父节点ID以及排序字段输出
        List<SysMenu> menuList = sysMenuMapper.selectMenuTreeByRole(roleID, sfjy);

        // 一级菜单
        for (SysMenu menu : menuList) {
            if (menu.getSjcdid() == 0L) {
                menuTree.add(setMenu(menu));
            }
        }
        // 二级菜单
        for (MenuDto menuParentDto : menuTree) {
            ArrayList<MenuDto> menuChildren = new ArrayList<MenuDto>();
            for (SysMenu menu : menuList) {
                if (menu.getSjcdid().toString().equals(menuParentDto.getMenuid())) {
                    menuChildren.add(setMenu(menu));
                }
            }
            menuParentDto.setChildren(menuChildren);
        }
        return ResultMap.ok().setData(menuTree);
    }


    /**
     * 获取用户api权限
     *
     * @param roleID 角色ID
     * @param sfjy   是否警员
     * @return
     */
    private List<String> getPermissionList(String roleID, String sfjy) {

        // 菜单List，按照父节点ID以及排序字段输出
        List<SysMenu> menuList = sysMenuMapper.selectMenuTreeByRole(roleID, sfjy);
        List<String> listPermission = new ArrayList<String>();
        String apiUrl;
        for (SysMenu menu : menuList) {
            apiUrl = menu.getApiurl();

            listPermission.add(menu.getUrl());
            if (apiUrl != null && apiUrl.length() > 0) {
                for (String oneApiUrl : apiUrl.split("\\|")) {
                    listPermission.add(oneApiUrl);
                }
            }
        }
        return listPermission;
    }

    /**
     * 登出
     *
     * @param request 用户请求
     * @return
     */
    public ResultMap loginOut(HttpServletRequest request) {

        // 记录登出日志
        String clientIp = Utils.getRemortIP(request);
        SysUser userInfo = (SysUser) request.getSession().getAttribute(Constant.LOGIN_USER_KEY);

        request.getSession().invalidate();
        String message = "用户" + userInfo.getYhm() + "在" + clientIp + "登出系统成功,时间" + DateUtil.dateFormat(DateUtil.now()
                , Constant.DATETIME_FORMAT);
        insertLogLogin(clientIp, userInfo.getYhm(), true, EnumLoginStatus.NORMAL.getValue(), message, "登出");

        return ResultMap.ok();
    }

    /**
     * 是否存在权限
     *
     * @param request 用户请求
     * @param url     URL
     * @return
     */
    public boolean hasPermission(HttpServletRequest request, String url) {

        SysUser userInfo = (SysUser) request.getSession().getAttribute(Constant.LOGIN_USER_KEY);

        String compUrl = url;
        if (url.startsWith("/")) {
            compUrl = url.substring(1);
        }

        return userInfo.getListPermission().contains(compUrl);
    }

}

