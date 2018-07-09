package com.nycloud.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

/**
 * @description: 授权调用
 * @author: super.wu
 * @date: Created in 2018/5/22 0022
 * @modified By:
 * @version: 1.0
 **/
@Component
@FeignClient(value = "nycloud-auth-server", fallback = FeignAuthClientFallback.class)
public interface FeignAuthClient {

    @GetMapping(value = "/check_token")
    Map<String, ?> checkToken(@RequestParam(value = "userId") String userId,
                              @RequestParam(value = "authorization") String authorization);


}


