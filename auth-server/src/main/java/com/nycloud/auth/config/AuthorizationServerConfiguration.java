package com.nycloud.auth.config;

import com.nycloud.auth.config.custom.CustomJwtAccessTokenConverter;
import com.nycloud.auth.config.custom.CustomRedisTokenStore;
import com.nycloud.auth.config.impl.ClientDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/4 0004
 * @modified By:
 * @version: 1.0
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration  extends AuthorizationServerConfigurerAdapter {

    /**
     * 认证管理器，当你选择了资源所有者密码（password）授权类型的时候，
     * 请设置这个属性注入一个 AuthenticationManager 对象
     */
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;


    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    /**
     * 配置令牌端点(Token Endpoint)的安全约束.
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    /**
     * 配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

    /**
     * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter());
    }


    /**
     * 使用Jwt的方式生成token
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        CustomJwtAccessTokenConverter converter = new CustomJwtAccessTokenConverter();
        converter.setSigningKey("secret");
        return converter;
    }


    @Bean
    public TokenStore tokenStore() {
       return new CustomRedisTokenStore(redisConnectionFactory);
    }

    @Bean
    public ClientDetailsService clientDetailsService() {
        return new ClientDetailsServiceImpl();
    }

}