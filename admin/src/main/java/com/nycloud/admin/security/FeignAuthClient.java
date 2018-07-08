package com.nycloud.admin.security;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 授权调用
 * @author: super.wu
 * @date: Created in 2018/5/22 0022
 * @modified By:
 * @version: 1.0
 **/
@Component
@FeignClient(value = "nycloud-auth-server")
public interface FeignAuthClient {

    /**
     * 远程检查用户是否拥有该资源
     * @param userId
     * @return
     */
    @GetMapping(value = "/user/check_resource")
    ResponseEntity<Boolean> checkResource(@RequestParam(value = "userId") Long userId,
                                          @RequestParam(value = "url") String url,
                                          @RequestParam(value = "method") String method);


}


