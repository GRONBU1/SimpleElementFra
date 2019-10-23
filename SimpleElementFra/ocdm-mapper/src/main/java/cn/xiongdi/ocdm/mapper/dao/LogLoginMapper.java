package cn.xiongdi.ocdm.mapper.dao;

import cn.xiongdi.ocdm.mapper.model.SysUser;
import cn.xiongdi.ocdm.mapper.model.LogLogin;
import cn.xiongdi.ocdm.mapper.model.LoginLogRq;

import java.util.List;

/**
 * 登录历史记录dao
 *
 * @创建人 tanlulu
 * @创建时间 2018-09-12
 * @描述
 */
public interface LogLoginMapper {
    /**
     * 获取列表
     *
     * @param loginLogRq 请求参数
     * @return
     */
    List<LogLogin> getLoginLogList(LoginLogRq loginLogRq);

    /**
     * 获取详情
     *
     * @param id 主键
     * @return
     */
    LogLogin selectByPrimaryKey(Long id);

    /**
     * 根据登录状态获取列表
     *
     * @param dlzt 登录状态
     * @return
     */
    List<LogLogin> selectByDlzt (Long dlzt);

    Long selectSecondId(String loginUserName);

    List<LogLogin> getHistoryUserInfo(SysUser userInfo);

    int insert(LogLogin record);

    void deleteByPrimaryKey(String dlyhm);

}