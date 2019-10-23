package cn.xiongdi.ocdm.server.filter;


import cn.xiongdi.ocdm.common.constants.Constant;
import cn.xiongdi.ocdm.common.utils.AjaxUtils;
import cn.xiongdi.ocdm.common.utils.StringUtils;
import cn.xiongdi.ocdm.common.utils.Utils;
import cn.xiongdi.ocdm.mapper.model.SysUser;
import cn.xiongdi.ocdm.server.dto.ResultMap;
import cn.xiongdi.ocdm.server.interceptor.OCDMSessionContext;
import cn.xiongdi.ocdm.server.service.sys.SignLoginService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：权限过滤类，负责对用户的访问权限进行过滤
 *
 * @author 汪礼
 */
public class AuthFilter implements Filter {

    private Logger log = Logger.getLogger(AuthFilter.class);

    @Value("${param.noauth-url}")
    private String noAuthUrl;

    @Value("${param.home-url}")
    private String homeUrl;


    @Autowired
    private SignLoginService signLoginService;

    private List<Pattern> patterns = null;

    /**
     * 是否需要过滤
     *
     * @param url
     * @return
     */
    private boolean isExclude(String url) {

        if (this.noAuthUrl != null &&
                this.patterns == null) {

            this.patterns = new ArrayList<Pattern>();

            for (String item : noAuthUrl.split(",")) {
                patterns.add(Pattern.compile(item));
            }
        }

        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 销毁
     */
    public void destroy() {
    }

    /**
     * 过滤实现
     *
     * @param request  ServletRequest
     * @param response ServletResponse
     * @param chain    FilterChain
     * @throws IOException      if has error
     * @throws ServletException if has error
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // 例外
        String uri = httpServletRequest.getRequestURI();
        uri = uri.replace(httpServletRequest.getContextPath(), "");


        if (uri.indexOf(";jsessionid=") > -1) {
            uri = uri.substring(0, uri.indexOf(";jsessionid="));
        }

        if (isExclude(uri)) {
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // ------ 打印请求参数 ------ start
        if (StringUtils.isNotEmpty(httpServletRequest.getQueryString())) {
            log.debug("queryString: " + httpServletRequest.getQueryString());
        }
        String paraName = null;
        String paraValue = null;
        StringBuffer sb = new StringBuffer();
        Enumeration<String> paraNames = request.getParameterNames();
        for (Enumeration<String> e = paraNames; e.hasMoreElements(); ) {
            paraName = e.nextElement().toString();
            paraValue = request.getParameter(paraName);
            sb.append(paraName + "=" + paraValue + ",");
        }
        if (StringUtils.isNotEmpty(sb.toString())) {
            log.debug("请求参数: " + sb.toString());
        }
        // ------ 打印请求参数 ------ end

        //当前账号访问IP
        String currUserIp = Utils.getRemortIP(httpServletRequest);

        // 获取web用户
        SysUser loginUser;
        loginUser = (SysUser) Utils.getLoginUserInfo(httpServletRequest);

        String userName = "";
        if (loginUser != null) {
            userName = loginUser.getYhm();
        }
        log.debug("当前会话:" + httpServletRequest.getSession().getId() +
                "\t 当前访问IP：" + currUserIp +
                "\t 登录账号：" + userName +
                "\t 当前访问地址：" + uri);

        // Http类型
        String type = httpServletRequest.getHeader("X-Requested-With");

        // 账号未登录
        if (loginUser == null) {


            log.info("账号未登录");

            if (type != null && type.equalsIgnoreCase("XMLHttpRequest")) {
                // 返回999编号
                AjaxUtils.sendAjaxForMap(httpServletResponse,
                        ResultMap.error(Constant.NO_LOGIN_CODE,
                                "您尚未登录或登录已失效，请先登录系统！"));

            } else {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.html");
            }

            return;

        }

        // 账号已登录，检查是否被其它PC登录
        HttpSession session = httpServletRequest.getSession();
        HttpSession userCurrentSession = OCDMSessionContext.getSessionContext().getSession(loginUser.getUserid());

        if (userCurrentSession != null &&
                !userCurrentSession.getId().equals(session.getId())) {

            log.info(String.format("用户(%s)已在其它PC进行了登录，被迫下线。", loginUser.getYhm()));
            session.invalidate();
            if (type != null && type.equalsIgnoreCase("XMLHttpRequest")) {

                // 返回999编号
                AjaxUtils.sendAjaxForMap(httpServletResponse,
                        ResultMap.error(Constant.NO_LOGIN_CODE,
                                "重复登录，被迫下线。"));

            } else {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.html?msg=" + Constant.NO_LOGIN_CODE);
            }

            return;
        }

        if (this.homeUrl.indexOf(uri) == -1) {

            // 用户机能权限
            boolean isHaveRight = false;

            isHaveRight = signLoginService.hasPermission(httpServletRequest, uri);
            if (!isHaveRight) {


                if (type != null && type.equalsIgnoreCase("XMLHttpRequest")) {

                    // 返回500
                    httpServletResponse.setStatus(500);

                } else {

                    // 暂定也回 index 画面
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/permissionDenied.html");
                }
                return;
            }
        }

        // 继续
        chain.doFilter(httpServletRequest, httpServletResponse);

    }

    /**
     * 初始化
     *
     * @param fc 过虑器配置
     * @throws ServletException servlet异常
     */
    public void init(FilterConfig fc) throws ServletException {

    }


}
