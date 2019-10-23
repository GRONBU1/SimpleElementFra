package cn.xiongdi.ocdm.server.controller;

import cn.xiongdi.ocdm.common.constants.Constant;
import cn.xiongdi.ocdm.common.utils.Utils;
import cn.xiongdi.ocdm.mapper.model.SysUser;
import cn.xiongdi.ocdm.server.dto.LoginDto;
import cn.xiongdi.ocdm.server.dto.PasswordDto;
import cn.xiongdi.ocdm.server.dto.UserInfoDto;
import cn.xiongdi.ocdm.server.service.sys.SignLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @创建人 lzy
 * @创建时间 2018-09-11
 * @描述 登录
 */
@RestController
@RequestMapping("/webapi/sign")
public class SignLoginController {

    @Autowired
    private SignLoginService signLoginService;

    @PostMapping("/login")
    public Map<String, Object> login(HttpServletRequest request, LoginDto loginInfo) {
        return signLoginService.login(request, loginInfo);
    }

    @PostMapping("/resetPassword")
    public Map<String, Object> resetPassword(HttpServletRequest request, PasswordDto passwordDto) {
        String userID = "";
        // 登录需要修改密码的场合
        if (request.getSession().getAttribute("userID") != null) {
                userID = request.getSession().getAttribute("userID").toString();
        } else {
            // 登录之后修改密码
            if (request.getSession().getAttribute(Constant.LOGIN_USER_KEY) != null) {
                SysUser userinfo = (SysUser) request.getSession().getAttribute(Constant.LOGIN_USER_KEY);
                userID = userinfo.getUserid();
            }
        }

        String clientIp = Utils.getRemortIP(request);
        return signLoginService.resetPassword(request, userID, clientIp, passwordDto);

    }

    @PostMapping("/getMenuTreeByRole")
    public Map<String, Object> getMenuTreeByRole(HttpServletRequest request, UserInfoDto userInfoDto) {

        return signLoginService.getMenuTreeByRole(request);

    }

    @PostMapping("/loginOut")
    public Map<String, Object> loginOut(HttpServletRequest request) {

        return signLoginService.loginOut(request);

    }
}
