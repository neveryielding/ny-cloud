package com.nycloud.admin.security.feign;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/7/9 0009
 * @modified By:
 * @version: 1.0
 **/
@Component
public class FeignAuthClientFallback implements FeignAuthClient {

    @Override
    public ResponseEntity<Boolean> checkResource(Long userId, String url, String method) {
        return new ResponseEntity<Boolean>(false, HttpStatus.OK);
    }
}
