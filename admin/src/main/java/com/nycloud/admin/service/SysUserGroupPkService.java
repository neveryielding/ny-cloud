package com.nycloud.admin.service;

import com.github.pagehelper.PageHelper;
import com.nycloud.admin.dto.UserGroupDto;
import com.nycloud.admin.mapper.SysUserGroupPkMapper;
import com.nycloud.admin.mapper.SysUserMapper;
import com.nycloud.admin.model.SysUserGroupPk;
import com.nycloud.common.vo.ResponsePage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/9 0009
 * @modified By:
 * @version: 1.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserGroupPkService extends BaseService<SysUserGroupPkMapper, SysUserGroupPk> {

    @Autowired
    private SysUserMapper sysUserMapper;

    public void setSysUserMapper(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    public ResponsePage loadGroupNoRelationUsers(UserGroupDto dto){
        PageHelper.startPage(dto.getPage(), dto.getSize());
        Map<String, Object> map = getQueryParams(dto);
        return new ResponsePage<>(sysUserMapper.selectGroupNoUsers(map));
    }

    public ResponsePage loadGroupUsers(UserGroupDto dto){
        PageHelper.startPage(dto.getPage(), dto.getSize());
        Map<String, Object> map = getQueryParams(dto);
        return new ResponsePage<>(sysUserMapper.selectGroupUsers(map));
    }

    private Map<String, Object> getQueryParams(UserGroupDto dto) {
        return new HashMap<String, Object>(1){{
            put("groupId", dto.getGroupId());
            if (StringUtils.isNotBlank(dto.getName())) {
                put("name", dto.getName());
            }
        }};
    }

    public Integer batchInsert(List<SysUserGroupPk> list) {
        return this.mapper.insertGroupUsers(list);
    }

    public Integer batchDelete(int groupId, Long [] userIds) {
        Map<String, Object> map = new HashMap<String, Object>(2){{
            put("groupId", groupId);
            put("userIds", userIds);
        }};
        return this.mapper.deleteGroupUsers(map);
    }

}
