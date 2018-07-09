package com.nycloud.auth.service;

import com.nycloud.auth.model.Resource;
import com.nycloud.auth.model.UserDetails;
import com.nycloud.common.utils.ListUtils;
import com.sun.rowset.internal.Row;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/25 0025
 * @modified By:
 * @version: 1.0
 **/
@Repository
public class JdbcUserDetailsService {

    private String selectUserDetailsSql;

    private String selectUserDetailsRoleSql;

    private String selectUserResourceSql;

    private String selectUserResourceCodesSql;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcUserDetailsService() {
        this.selectUserDetailsSql = "select id, username, `password`, `state`, `name` from sys_user where username = ?";
        this.selectUserDetailsRoleSql = "select code from sys_role where id in (select role_id from sys_user_role_pk where user_id = ? union all select role_id from sys_user_group_role_pk where group_id in (select group_id from sys_user_group_pk where user_id = ?))";
        this.selectUserResourceSql = "select A.* from sys_resource A left join sys_permission_resource_pk B on (A.id = B.resource_id) where B.permission_id in (select permission_id from sys_role_permission_pk where role_id in (select id from sys_role where code in (select code from sys_user_role_pk where user_id = ?))) and type = 2 and A.url = ? and A.url_request_type = ?";
        this.selectUserResourceCodesSql = "select code from sys_resource where id in (select resource_id from sys_permission_resource_pk where permission_id in (select permission_id from sys_role_permission_pk where role_id in (select id from sys_role where id in (select role_id from sys_user_group_role_pk where group_id in (select group_id from sys_user_group_pk where user_id = :userId) union all select role_id from sys_user_role_pk where user_id = :userId)))) and code in (:codes)";
    }

    public UserDetails loadUserDetailsByUserName(String username)  {
        UserDetails details = this.jdbcTemplate.queryForObject(this.selectUserDetailsSql, new JdbcUserDetailsService.UserDetailsRowMapper(), new Object[]{username});
        return details;
    }

    public List<String> loadUserRolesByUserId(Long userId) {
        List<String> roles = this.jdbcTemplate.query(this.selectUserDetailsRoleSql, new JdbcUserDetailsService.RoleRowMapper(), new Object[]{userId, userId});
        return roles;
    }


    public boolean isResource(Long userId, String url, String method) {
        ResourceMapper resourceMapper = new ResourceMapper();
        Object[] params = new Object[] {userId, url, method};
        List<Resource> list = this.jdbcTemplate.query(this.selectUserResourceSql, resourceMapper, params);
        return !ListUtils.isEmpty(list);
    }


    public List<String> loadResourceCodesByUserIdAndCodes(Long userId, List<String> codes) {
        NamedParameterJdbcTemplate jt = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        Map<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        map.put("codes", codes);
        List<String> result = jt.query(this.selectUserResourceCodesSql, map, new ResourceCodeeMapper());
        return result;
    }

    private static class UserDetailsRowMapper implements RowMapper<UserDetails> {

        @Override
        public UserDetails mapRow(ResultSet resultSet, int i) throws SQLException {
            UserDetails userDetails = new UserDetails();
            userDetails.setUserId(resultSet.getString(1));
            userDetails.setUsername(resultSet.getString(2));
            userDetails.setPassword(resultSet.getString(3));
            userDetails.setEnabled(resultSet.getBoolean(4));
            return userDetails;
        }

    }

    private static class RoleRowMapper implements RowMapper<String> {
        @Override
        public String mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getString(1);
        }
    }

    private static class ResourceMapper implements RowMapper<Resource> {
        @Override
        public Resource mapRow(ResultSet resultSet, int i) throws SQLException {
            Resource resource = new Resource();
            resource.setId(resultSet.getLong(1));
            resource.setName(resultSet.getString(2));
            resource.setCode(resultSet.getString(3));
            return resource;
        }
    }


    private static class ResourceCodeeMapper implements RowMapper<String> {

        @Override
        public String mapRow(ResultSet resultSet, int i) throws SQLException {
            String code = resultSet.getString(1);
            return code;
        }
    }

}