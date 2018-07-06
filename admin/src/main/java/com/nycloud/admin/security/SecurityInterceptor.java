package com.nycloud.admin.security;

import com.nycloud.admin.model.SysResource;
import com.nycloud.admin.service.SysResourceService;
import com.nycloud.common.constants.SysConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author super.wu
 */
@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);

    @Autowired
    private SysResourceService sysResourceService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (logger.isDebugEnabled()) {
            logger.debug("current request url: {}" , requestURI);
        }
        String userId = request.getHeader("userId");
        String userName = request.getHeader("username");
        String roles = request.getHeader("roles");
        try{
            if (userId == null) {
                // swagger-ui 测试用
                userId = "196618686130565120";
                userName = "admin";
                roles = SysConstant.SUPER_ADMIN_ROLE_CODE;
            }

            Long uid = Long.valueOf(userId);
            List<String> roleCodes = Arrays.stream(roles.split(",")).collect(Collectors.toList());
            UserEntity userEntity = new UserEntity(uid, userName, roleCodes);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 判断是否是不需要权限检查的URL
            if (requestURI.indexOf(SysConstant.API_NO_PERMISSION) != -1) {
                return super.preHandle(request, response, handler);
            }

            // 如果该用户没有拥有超级管理员角色就对访问的接口进行权限检查
            if (!roles.contains(SysConstant.SUPER_ADMIN_ROLE_CODE)) {
                // 检查权限
                String urlRequestType = request.getMethod().toUpperCase();
                Map<String, Object> map = new HashMap<String, Object>(3) {{
                   put("url", requestURI);
                   put("urlRequestType", urlRequestType);
                   put("roleCodes", roleCodes);
                }};
                // 获取用户是否和该资源有关联
                SysResource sysResource = sysResourceService.selectUserRolePermissionResource(map);
                if (sysResource == null) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return false;
                }
            };
            Object p = authentication.getPrincipal();
            if (logger.isDebugEnabled()) {
                logger.debug("Receive a request from ,authentication: {}", authentication);
                logger.debug("SecurityInterceptor principal = {}", p);
            }
            return super.preHandle(request, response, handler);
        } catch(NumberFormatException e) {
            logger.debug(e.getLocalizedMessage());
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            return false;
        }
    }

}