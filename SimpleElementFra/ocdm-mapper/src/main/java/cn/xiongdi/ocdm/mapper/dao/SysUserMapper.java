package cn.xiongdi.ocdm.mapper.dao;

import cn.xiongdi.ocdm.mapper.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int insert(SysUser record);

    int insertSelective(SysUser record);

    int selectCount(@Param("yhm") String yhm);

    int selectYhmOfCheck(SysUser record);

    List<SysUser> selectByUserList(SysUser record);

    SysUser selectByUserId(SysUser record);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    List<SysUser> selectByUserName(SysUser record);

    int selectZXYHS();

    int selectOnLimeCount();
}