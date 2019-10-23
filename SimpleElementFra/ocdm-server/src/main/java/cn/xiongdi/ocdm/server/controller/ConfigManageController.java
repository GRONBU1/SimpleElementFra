package cn.xiongdi.ocdm.server.controller;

import cn.xiongdi.ocdm.common.constants.Constant;
import cn.xiongdi.ocdm.common.utils.StringUtils;
import cn.xiongdi.ocdm.common.utils.Utils;
import cn.xiongdi.ocdm.mapper.model.SysParam;
import cn.xiongdi.ocdm.mapper.model.ParamRq;
import cn.xiongdi.ocdm.mapper.model.SysUser;
import cn.xiongdi.ocdm.server.base.BaseController;
import cn.xiongdi.ocdm.server.service.sys.ConfigManageService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参数管理 controller
 *
 * @创建人 粟磊
 * @创建时间 2018-11-01
 * @描述
 */
@RestController
@RequestMapping("/webapi/tbParam")
public class ConfigManageController extends BaseController {
    /*------start:获取常量------*/
    private static final String CSLB_VALID_MSG = "参数类别必填且长度不大于16位！";
    private static final String CSDH_VALID_MSG = "参数代号必填且长度不大于16位！";
    /*------end:获取常量结束------*/
    @Autowired
    private ConfigManageService configManageService;

    /**
     * 获取参数列表
     *
     * @param paramRq 请求参数
     * @return
     */
    @RequestMapping("/getParamByPage")
    public Map<String, Object> getParamByPage(ParamRq paramRq) {
        Map<String, Object> map = new HashMap();
        if (paramRq == null) {
            map.put(Constant.CODE_KEY, Constant.CODE_FAILURE);
            return map;
        }
        //获取参数列表
        Page page = this.startPage(paramRq.getPage(), paramRq.getRows());
        List<SysParam> list = this.configManageService.getByCon(paramRq);
        map.put(Constant.CODE_KEY, Constant.CODE_SUCCESS);
        map.put(Constant.TOTAL_KEY, page.getTotal());
        map.put(Constant.DATA_KEY, list);
        return map;
    }

    /**
     * 获取参数类别
     *
     * @return
     */
    @RequestMapping("/getCslb")
    public Map<String, Object> getCsLb() {
        Map<String, Object> map = new HashMap();
        List<SysParam> list = this.configManageService.getCslb();
        map.put(Constant.CODE_KEY, Constant.CODE_SUCCESS);
        map.put(Constant.DATA_KEY, list);
        return map;
    }

    /**
     * 新增参数
     *
     * @param param 请求参数
     * @return
     */
    @RequestMapping("/saveParam")
    public Map<String, Object> saveParam(HttpServletRequest request, SysParam param) {
        Map<String, Object> map = new HashMap();
        if (param == null) {
            map.put(Constant.CODE_KEY, Constant.CODE_FAILURE);
            return map;
        }

        //定义变量
        String cslb = param.getCslb().toString();
        String csdh = param.getCsdh().toString();

        //校验参数类别格式是否正确
        if (!(StringUtils.isNotBlank(cslb) && cslb.length() <= 16)) {
            map.put(Constant.CODE_KEY, Constant.CODE_FAILURE);
            map.put(Constant.MESSAGE_KEY, CSLB_VALID_MSG);
            return map;
        }
        if (!(StringUtils.isNotBlank(csdh) && csdh.length() <= 16)) {
            map.put(Constant.CODE_KEY, Constant.CODE_FAILURE);
            map.put(Constant.MESSAGE_KEY, CSDH_VALID_MSG);
            return map;
        }

        SysUser user = (SysUser) Utils.getLoginUserInfo(request);
        param.setEditflg(1);
        param.setCreateuid(user.getYhm());
        param.setCreateip(Utils.getRemortIP(request));
        configManageService.saveParam(param);
        map.put(Constant.CODE_KEY, Constant.CODE_SUCCESS);
        return map;
    }

    /**
     * 修改参数
     *
     * @param param 请求参数
     * @return
     */
    @RequestMapping("/editParam")
    public Map<String, Object> editParam(HttpServletRequest request, SysParam param) {
        Map<String, Object> map = new HashMap();
        if (param == null) {
            map.put(Constant.CODE_KEY, Constant.CODE_FAILURE);
            return map;
        }
        //定义变量
        String csdh = param.getCsdh().toString();

        //校验参数类别格式是否正确
        if (!(StringUtils.isNotBlank(csdh) && csdh.length() <= 16)) {
            map.put(Constant.CODE_KEY, Constant.CODE_FAILURE);
            map.put(Constant.MESSAGE_KEY, CSDH_VALID_MSG);
            return map;
        }

        SysUser user = (SysUser) Utils.getLoginUserInfo(request);
        param.setUpdateuid(user.getYhm());
        param.setUpdateip(Utils.getRemortIP(request));

        configManageService.editParam(param);
        map.put(Constant.CODE_KEY, Constant.CODE_SUCCESS);
        return map;
    }

    /**
     * 删除参数
     *
     * @param id 主键id
     * @return
     */
    @RequestMapping("/deleteParam")
    public Map<String, Object> deleteParam(Long id) {

        Map<String, Object> map = new HashMap();
        configManageService.deleteParam(id);
        map.put(Constant.CODE_KEY, Constant.CODE_SUCCESS);
        return map;
    }

    /**
     * 获取当前参数的详情数据
     *
     * @param id 主键
     * @return
     */
    @RequestMapping("/getParam")
    public Map<String, Object> getParam(Long id) {
        Map<String, Object> map = new HashMap();
        SysParam sysParam = configManageService.getInfoByCsId(id);
        map.put(Constant.CODE_KEY, Constant.CODE_SUCCESS);
        map.put(Constant.DATA_KEY, sysParam);
        return map;
    }
}
