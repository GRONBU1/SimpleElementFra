package cn.xiongdi.ocdm.server.base;

import cn.xiongdi.ocdm.common.constants.ConfigConstant;
import cn.xiongdi.ocdm.common.utils.StringUtils;
import cn.xiongdi.ocdm.mapper.dao.SysParamMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 控制器基类
 *
 * @author 汪礼
 */
public abstract class BaseController {

    private final static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private SysParamMapper sysParamMapper;

    /**
     * 设定分页开始
     *
     * @param pageNum Integer
     */
    protected void startPage(Integer pageNum) {

        // 设置分页尺寸(default)
        int pageSize = 10 ;
        String str=this.sysParamMapper.getValueByCode(ConfigConstant.PAGE_SIZE);

        if(!StringUtils.isEmptyOrNull(str)){
            pageSize = Integer.parseInt(str);
        }
        startPage(pageNum, pageSize);

    }

    /**
     * 设定分页开始(参数为request或Map)
     *
     * @param params Object
     */
    protected void startPage(Object params) {
        PageHelper.startPage(params);
    }

    /**
     * 设定分页开始
     *
     * @param pageNum int
     * @param pageSize int
     */
    protected Page startPage(int pageNum, int pageSize) {
        return PageHelper.startPage(pageNum, pageSize);
    }


}
