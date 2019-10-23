package cn.xiongdi.ocdm.server.listener;

import cn.xiongdi.ocdm.common.constants.Constant;
import cn.xiongdi.ocdm.mapper.model.SysUser;
import cn.xiongdi.ocdm.server.interceptor.OCDMSessionContext;
import cn.xiongdi.ocdm.server.service.sys.SignLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

/**
 * @创建人 汪礼
 * @创建时间 2018-09-17
 * @描述
 */
@WebListener
public class OCDMSessionListener implements HttpSessionListener, HttpSessionAttributeListener {
    @Autowired
    private SignLoginService signLoginService;

    private static final Logger log = LoggerFactory.getLogger(OCDMSessionListener.class);

    // 程序启动时可以在控制台中输出这句话
    public OCDMSessionListener() {
        log.info("-----VehlicSessionListenerInitialized-----");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("-----sessionCreated-----" + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("-----sessionDestroyed-----" + se.getSession().getId());
        HttpSession session = se.getSession();

        Object userInfoObj = session.getAttribute(Constant.LOGIN_USER_KEY);
        if (userInfoObj != null) {
            SysUser userInfo = (SysUser) userInfoObj;
            OCDMSessionContext sessionContext = OCDMSessionContext.getSessionContext();
            HttpSession newSession = sessionContext.getSession(userInfo.getUserid());

            // session过期或者登录注销
            if (newSession != null && newSession.getId().equals(session.getId())) {
                sessionContext.delSession(userInfo.getUserid());
            }

            // 更新用户状态
            signLoginService.updateUserStatus(userInfo.getUserid(), "0");

        }
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        log.info("-----attributeAdded: " + se.getSession().getId());

        HttpSession session = se.getSession();
        String sessionAttributeKey = se.getName();
        if (sessionAttributeKey.equals(Constant.LOGIN_USER_KEY)) {
            SysUser user = (SysUser) session.getAttribute(Constant.LOGIN_USER_KEY);

            OCDMSessionContext sessionContext = OCDMSessionContext.getSessionContext();

            if (sessionContext.getSession(user.getUserid()) != null) {
                sessionContext.delSession(user.getUserid());
            }
            sessionContext.addSession(user.getUserid(), session);
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        log.info("-----attributeRemoved: " + se.getSession().getId());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        log.info("-----attributeReplaced: " + se.getSession().getId());
    }
} 
