package com.nycloud.admin.mapper;

import com.nycloud.admin.model.SysUser;
import com.nycloud.admin.vo.SysUserInfo;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;
import java.util.Map;

public interface SysUserMapper extends Mapper<SysUser> {

    SysUserInfo selectUserGroup(Long userId);

    List<SysUser> selectGroupNoUsers(Map<String, Object> map);

    List<SysUser> selectGroupUsers(Map<String, Object> map);

}