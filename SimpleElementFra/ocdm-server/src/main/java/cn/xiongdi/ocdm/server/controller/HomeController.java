package cn.xiongdi.ocdm.server.controller;

import cn.xiongdi.ocdm.server.base.BaseController;
import cn.xiongdi.ocdm.server.service.sys.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 主页 controller
 *
 * @创建者 pengfuming
 * @创建时间 2018-11-05
 * @描述 主页 controller
 */
@RestController
@RequestMapping("/webapi/home")
public class HomeController extends BaseController {

    @Autowired
    private HomeService homeService;

    /**
     * 获取登录信息
     *
     * @param request
     * @param flag    显示用户名flag
     * @return
     */
    @RequestMapping("/getLoginInfo")
    public Map<String, Object> getLoginInfo(HttpServletRequest request, Integer flag) {
        return homeService.getLoginInfo(request, flag);
    }
}
