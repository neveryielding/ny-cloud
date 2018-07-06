package com.nycloud.auth.dto;

import lombok.Data;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/7/6 0006
 * @modified By:
 * @version: 1.0
 **/
@Data
public class LoginDto {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 客户端
     */
    private String client;


}
