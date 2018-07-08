package com.nycloud.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.nycloud.common.jwt.JwtEntity;
import com.nycloud.common.jwt.JwtUtil;
import com.nycloud.gateway.properties.PermitAllUrlProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/7/6 0006
 * @modified By:
 * @version: 1.0
 **/
public class AccessFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    @Autowired
    private PermitAllUrlProperties urlProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestURI = request.getRequestURI();
        // 如果URL需要做权限校验
        if (!urlProperties.isPermitAllUrl(requestURI)) {
            String authorization = request.getHeader("Authorization");
            if (StringUtils.isNotEmpty(authorization) && JwtUtil.isJwtBearerToken(authorization)) {
                JwtEntity jwtEntity = JwtUtil.parseToken(authorization);
                if (jwtEntity.isExpire()) {
                    //过滤该请求，不往下级服务去转发请求，到此结束
                    ctxError(ctx, 403, "token已过期，请重新登录");
                    log.warn("token expire", authorization);
                } else {
                    ctx.addZuulRequestHeader("userId", jwtEntity.getUserId());
                    ctx.addZuulRequestHeader("username", jwtEntity.getUsername());
                    ctx.addZuulRequestHeader("roles", jwtEntity.getRoles());
                    log.info("token success", authorization);
                }
            } else {
                ctxError(ctx, 401, "您没有携带有效token,无权限访问!");
                log.warn("token error", requestURI);
            }
        }
        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        return null;
    }

    /**
     * 设置Zuul网关不再转发路由，返回错误信息
     * @param ctx
     * @param stateCode
     * @param msg
     */
    private void ctxError(RequestContext ctx, int stateCode, String msg) {
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(stateCode);
        Map<String, Object> map = new HashMap<String, Object>(2) {{
            put("code", stateCode);
            put("msg", msg);
        }};
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(map);
            ctx.setResponseBody(jsonString);
        } catch (Exception e) {

        }
        ctx.getResponse().setContentType("text/html;charset=UTF-8");
    }

}
