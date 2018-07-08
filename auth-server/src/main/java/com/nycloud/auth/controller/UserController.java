package com.nycloud.auth.controller;

import com.nycloud.auth.service.JdbcUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
/**
 * Created by wangyunfei on 2017/6/12.
 */
@RestController
public class UserController {

    @Autowired
    private JdbcUserDetailsService jdbcUserDetailsService;

    @GetMapping("/user/check_resource")
    public ResponseEntity checkResource(@RequestParam("userId") Long userId,
                                                  @RequestParam("url") String url,
                                                  @RequestParam("method") String method) {
        return new ResponseEntity<Boolean>(jdbcUserDetailsService.isResource(userId, url, method), HttpStatus.OK);
    }

}
