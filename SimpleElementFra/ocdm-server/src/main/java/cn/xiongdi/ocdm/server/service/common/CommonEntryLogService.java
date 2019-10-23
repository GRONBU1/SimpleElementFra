package cn.xiongdi.ocdm.server.service.common;

import cn.xiongdi.ocdm.mapper.model.LogCommon;

/**
 * @创建人 朱照星
 * @创建时间 2018/9/14
 * @描述 记录日志共通 Service接口
 */
public interface CommonEntryLogService {

    /**
     * 记录日志
     *
     * @param logCommon 日志信息
     * @return void
     */
    void entryLog(LogCommon logCommon);
}