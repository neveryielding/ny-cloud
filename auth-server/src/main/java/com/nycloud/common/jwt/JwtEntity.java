package com.nycloud.common.jwt;

import lombok.Data;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/7/6 0006
 * @modified By:
 * @version: 1.0
 **/
@Data
public class JwtEntity {

    private String userId;

    private String username;

    private boolean enabled;

    private long expire;

    private String roles;

    public boolean isExpire() {
        return expire >= System.currentTimeMillis();
    }

}
