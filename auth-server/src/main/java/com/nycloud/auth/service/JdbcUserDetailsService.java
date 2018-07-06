package com.nycloud.auth.service;

import com.nycloud.auth.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        this.selectUserDetailsSql = "select id, username, `password`, `state`, `name` from sys_user where username = ?";
        this.selectUserDetailsRoleSql = "select code from sys_role where id in (select role_id from sys_user_role_pk where user_id = ? union all select role_id from sys_user_group_role_pk where group_id in (select group_id from sys_user_group_pk where user_id = ?))";
    }

    public UserDetails loadUserDetailsByUserName(String username)  {
        UserDetails details = this.jdbcTemplate.queryForObject(this.selectUserDetailsSql, new JdbcUserDetailsService.UserDetailsRowMapper(), new Object[]{username});
        return details;
    }

    public List<String> loadUserRolesByUserId(Long userId) {
        List<String> roles = this.jdbcTemplate.query(this.selectUserDetailsRoleSql, new JdbcUserDetailsService.RoleRowMapper(), new Object[]{userId, userId});
        return roles;
    }

    private static class UserDetailsRowMapper implements RowMapper<UserDetails> {

        @Override
        public UserDetails mapRow(ResultSet resultSet, int i) throws SQLException {
            UserDetails customUserDetails = new UserDetails();
            customUserDetails.setUserId(resultSet.getString(1));
            customUserDetails.setUsername(resultSet.getString(2));
            customUserDetails.setPassword(resultSet.getString(3));
            customUserDetails.setEnabled(resultSet.getBoolean(4));
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