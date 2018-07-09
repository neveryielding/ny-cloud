package com.nycloud.auth.controller;

import com.nycloud.auth.service.JdbcUserDetailsService;
import com.nycloud.common.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author super.wu
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

    @GetMapping("/user/check_resource_codes")
    public ResponseEntity checkResourceCodes(@RequestHeader(value = "userId") String userId,
                                             @RequestParam("codes") List<String> codes) {
        if (StringUtils.isEmpty(userId) || ListUtils.isEmpty(codes)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        List<String> result = jdbcUserDetailsService.loadResourceCodesByUserIdAndCodes(Long.valueOf(userId), codes);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

}
