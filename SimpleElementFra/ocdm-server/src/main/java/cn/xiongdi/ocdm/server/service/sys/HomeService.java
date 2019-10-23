package cn.xiongdi.ocdm.server.service.sys;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @创建者 pengfuming
 * @创建时间 2018-11-05
 * @描述 主页
 */
public interface HomeService {

    /**
     * 获取登录信息
     *
     * @param request
     * @param flag    显示用户名flag
     * @return
     */
    Map<String, Object> getLoginInfo(HttpServletRequest request, Integer flag);
}
