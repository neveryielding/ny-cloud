package com.nycloud.auth.config.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nycloud.auth.config.custom.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcUserDetailsService() {
        this.selectUserDetailsSql = "select id, username, `password`, `state`, authorities, `name` from sys_user where username = ?";
        this.selectUserDetailsRoleSql = "select code from sys_role where id in (select role_id from sys_user_role_pk where user_id = ? union all select role_id from sys_user_group_role_pk where group_id in (select group_id from sys_user_group_pk where user_id = ?))";
    }

    public CustomUserDetails loadUserDetailsByUserName(String username) throws InvalidClientException {
        try {
            CustomUserDetails details = this.jdbcTemplate.queryForObject(this.selectUserDetailsSql, new JdbcUserDetailsService.UserDetailsRowMapper(), new Object[]{username});
            return details;
        } catch (EmptyResultDataAccessException var4) {
            throw new NoSuchClientException("No client with requested username: " + username);
        }
    }

    public List<String> loadUserRolesByUserId(Long userId) {
        List<String> roles = this.jdbcTemplate.query(this.selectUserDetailsRoleSql, new JdbcUserDetailsService.RoleRowMapper(), new Object[]{userId, userId});
        return roles;
    }

    private static class UserDetailsRowMapper implements RowMapper<CustomUserDetails> {

        @Override
        public CustomUserDetails mapRow(ResultSet resultSet, int i) throws SQLException {
            CustomUserDetails customUserDetails = new CustomUserDetails();
            customUserDetails.setUserId(resultSet.getString(1));
            customUserDetails.setUsername(resultSet.getString(2));
            customUserDetails.setPassword(resultSet.getString(3));
            customUserDetails.setEnabled(resultSet.getBoolean(4));
            customUserDetails.setAuthorities(new HashSet<>());
            return customUserDetails;
        }

    }

    private static class RoleRowMapper implements RowMapper<String> {
        @Override
        public String mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getString(1);
        }
    }

}
