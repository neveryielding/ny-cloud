package com.nycloud.auth.config.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 自定义，添加了缓存配置
 * @author: super.wu
 * @date: Created in 2018/5/8 0008
 * @modified By:
 * @version: 1.0
 **/
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Resource
    DataSource dataSource;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    private final String CACHE_KEY = "CACHE_CLIENT_DETAILS";

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException{
        return getClientDetails(clientId);
    }

    private ClientDetails getClientDetails(String clientId) {
        if (redisTemplate.hasKey(CACHE_KEY)) {
            Map<String, ClientDetails> map = (Map<String, ClientDetails>) redisTemplate.opsForValue().get(CACHE_KEY);
            if (map.containsKey(clientId)) {
                return map.get(clientId);
            }
        }
        return loadClientDetails(clientId);
    }

    private ClientDetails loadClientDetails(String clientId) {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(clientId);
        if(clientDetails == null) {
            throw new ClientRegistrationException("应用" + clientId + "不存在!");
        }
        cacheClientDetails(clientDetails);
        return clientDetails;
    }

    private void cacheClientDetails(ClientDetails clientDetails) {
        Map<String, ClientDetails> map;
        if (redisTemplate.hasKey(CACHE_KEY)) {
            map = (Map<String, ClientDetails>) redisTemplate.opsForValue().get(CACHE_KEY);
        } else {
            map = new HashMap<>(1);
        }
        map.put(clientDetails.getClientId(), clientDetails);
        redisTemplate.opsForValue().set(CACHE_KEY, map);
    }

}
