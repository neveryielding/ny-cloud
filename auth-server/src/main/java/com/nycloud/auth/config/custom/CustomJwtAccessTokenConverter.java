package com.nycloud.auth.config.custom;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 自定义token生成
 * @author: super.wu
 * @date: Created in 2018/5/7 0007
 * @modified By:
 * @version: 1.0
 **/
public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

    private static int authenticateCodeExpiresTime = 10 * 60;

    private static final String TOKEN_SEG_USER_ID = "X-AOHO-UserId";
    private static final String TOKEN_SEG_CLIENT = "X-AOHO-ClientId";

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getUserAuthentication().getPrincipal();

        authentication.getUserAuthentication().getPrincipal();
        Map<String, Object> info = new HashMap<String, Object>(2) {{
            put(TOKEN_SEG_USER_ID, userDetails.getUserId());
            put("roles", String.join(",", userDetails.getRoles()));
        }};

        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);

        OAuth2AccessToken enhancedToken = super.enhance(customAccessToken, authentication);
        enhancedToken.getAdditionalInformation().put(TOKEN_SEG_CLIENT, userDetails.getClientId());
        return enhancedToken;
    }

}