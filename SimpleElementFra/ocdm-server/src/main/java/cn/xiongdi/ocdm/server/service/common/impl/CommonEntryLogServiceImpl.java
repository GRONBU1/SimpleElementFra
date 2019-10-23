package cn.xiongdi.ocdm.server.service.common.impl;


import cn.xiongdi.ocdm.mapper.dao.LogOperationMapper;
import cn.xiongdi.ocdm.mapper.dao.SysMenuMapper;
import cn.xiongdi.ocdm.mapper.model.LogCommon;
import cn.xiongdi.ocdm.mapper.model.LogOperationWithBLOBs;
import cn.xiongdi.ocdm.mapper.model.SysMenu;
import cn.xiongdi.ocdm.server.service.common.CommonEntryLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @创建人 朱照星
 * @创建时间 2018/9/14
 * @描述 记录日志共通 service接口实现类，实现service接口方法
 */
@PropertySource(value = "classpath:/properties/config.properties", ignoreResourceNotFound = true)
@Service
public class CommonEntryLogServiceImpl implements CommonEntryLogService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private LogOperationMapper logOperationMapper;

    /**
     * 设置日志类型
     *
     * @param menuInfo
     * @param logCommon
     * @return
     */
    private int setLogType(SysMenu menuInfo, LogCommon logCommon) {
        int logType = 0;
        boolean hxgn = false;
        boolean fcgyw = false;

        if (menuInfo != null) {
            // 核心功能
            if (menuInfo.getHxgn() != null && "1".equals(menuInfo.getHxgn())) {
                hxgn = true;
            }
            // 非常规业务
            if (menuInfo.getFcgyw() != null && "1".equals(menuInfo.getFcgyw())) {
                fcgyw = true;
            }

            // 操作时间
            logCommon.setCzsj(new Date());

            if (hxgn && fcgyw) {
                logCommon.setLogtype("2");
                logCommon.setSfhxgn(1L);
                logCommon.setSfcgyw(0L);
                return 1;
            } else if (hxgn && !fcgyw) {
                logCommon.setLogtype("1");
                logCommon.setSfhxgn(1L);
                logCommon.setSfcgyw(1L);
                return 2;
            } else if (fcgyw && !hxgn) {
                logCommon.setLogtype("4");
                logCommon.setSfhxgn(0L);
                logCommon.setSfcgyw(0L);
                return 3;
            } else if (!hxgn && !fcgyw) {
                logCommon.setLogtype("3");
                logCommon.setSfhxgn(0L);
                logCommon.setSfcgyw(1L);
                return 4;
            }
        }
        return logType;
    }

    @Override
    public void entryLog(LogCommon logCommon) {
        if (logCommon != null) {
            SysMenu menuInfo = sysMenuMapper.selectByCdbh(logCommon.getCdbh());
            if (menuInfo != null) {
                int logType = setLogType(menuInfo, logCommon);
                if (logType > 0) {
                    insertLog(logType, logCommon);
                }
            }
        }

    }

    /**
     * 插入日志表
     *
     * @param hxgnOrFcgyw 类别
     * @param logCommon   日志信息
     * @return void
     */
    private void insertLog(Integer hxgnOrFcgyw, LogCommon logCommon) {

        LogOperationWithBLOBs logOperation;

            if (hxgnOrFcgyw.equals(1) || hxgnOrFcgyw.equals(2)
                    || hxgnOrFcgyw.equals(3)) {
                logOperation = new LogOperationWithBLOBs();
                logOperation.setCzyhm(logCommon.getCzyhm());
                logOperation.setLogtype(logCommon.getLogtype());
                logOperation.setTablenames(logCommon.getTablenames());
                logOperation.setCzmc(logCommon.getCzmc());
                logOperation.setCzqsj(logCommon.getCzqsj());
                logOperation.setCzhsj(logCommon.getCzhsj());
                logOperation.setCzsj(logCommon.getCzsj());
                logOperation.setBz(logCommon.getBz());
                logOperation.setLy(logCommon.getLy());
                logOperation.setXw(logCommon.getXw());
                logOperation.setJg(logCommon.getJg());
                logOperation.setCreateip(logCommon.getLy());
                logOperation.setCreatetime(logCommon.getCzsj());
                logOperation.setCreateuid(logCommon.getCzyhm());
                // 插入系统操作日志
                logOperationMapper.insertSelective(logOperation);
            } else if (hxgnOrFcgyw.equals(4)) {
                logOperation = new LogOperationWithBLOBs();
                logOperation.setCzyhm(logCommon.getCzyhm());
                logOperation.setLogtype(logCommon.getLogtype());
                logOperation.setTablenames(logCommon.getTablenames());
                logOperation.setCzmc(logCommon.getCzmc());
                logOperation.setCzqsj(logCommon.getCzqsj());
                logOperation.setCzhsj(logCommon.getCzhsj());
                logOperation.setCzsj(logCommon.getCzsj());
                logOperation.setBz(logCommon.getBz());
                logOperation.setLy(logCommon.getLy());
                logOperation.setXw(logCommon.getXw());
                logOperation.setJg(logCommon.getJg());
                logOperation.setCreateip(logCommon.getLy());
                logOperation.setCreatetime(logCommon.getCzsj());
                logOperation.setCreateuid(logCommon.getCzyhm());
                // 插入系统操作日志
                logOperationMapper.insertSelective(logOperation);
            }
    }

}
