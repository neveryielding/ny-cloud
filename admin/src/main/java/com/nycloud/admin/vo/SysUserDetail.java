package com.nycloud.admin.vo;

import com.nycloud.admin.model.SysRole;
import java.util.List;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/6/13 0013
 * @modified By:
 * @version: 1.0
 **/
public class SysUserDetail extends SysUserInfo {

    private List<String> roleCodes;

    private List<SysRole> roleList;

    public void setUserGroupInfo(SysUserInfo userInfo) {
        this.setId(userInfo.getId());
        this.setUsername(userInfo.getUsername());
        this.setPassword(userInfo.getPassword());
        this.setName(userInfo.getName());
        this.setCreateTime(userInfo.getCreateTime());
        this.setLastPasswordChange(userInfo.getLastPasswordChange());
        this.setAuthorities(userInfo.getAuthorities());
        this.setState(userInfo.getState());
        this.setGroupId(userInfo.getGroupId());
        this.setGroupName(userInfo.getGroupName());
    }

    public static class SysUserDetailBuilder {

        private SysUserDetail userDetail = new SysUserDetail();

        public SysUserDetailBuilder setRoleCodes(List<String> roleCodes) {
            userDetail.setRoleCodes(roleCodes);
            return this;
        }

        public SysUserDetailBuilder setRoleList(List<SysRole> roleList) {
            userDetail.setRoleList(roleList);
            return this;
        }

        public SysUserDetail build() {
            return userDetail;
        }

    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public List<SysRole> getRoleList() {
        return roleList;
    }

}
