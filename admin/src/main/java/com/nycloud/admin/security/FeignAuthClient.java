package com.nycloud.admin.security;

import com.nycloud.admin.model.SysUser;
import com.nycloud.common.vo.HttpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/22 0022
 * @modified By:
 * @version: 1.0
 **/
@FeignClient(value = "nycloud-admin")
public interface FeignAuthClient {

    /**
     * 远程获取用户资源
     * @param userId
     * @return
     */
    @GetMapping(value = "public/api/sysUser/userResources")
    HttpResponse<SysUser> getUserResources(@RequestParam(value = "userId") Long userId);

}


