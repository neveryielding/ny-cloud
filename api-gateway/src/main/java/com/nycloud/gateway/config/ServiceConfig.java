package com.nycloud.gateway.config;

import com.nycloud.gateway.properties.PermitAllUrlProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author keets
 * @date 2017/9/29
 */
@Configuration
public class ServiceConfig {


    @Value("${server.port}")
    private int securePort;


    @Bean
    @ConfigurationProperties(prefix = "auth")
    public PermitAllUrlProperties getPermitAllUrlProperties() {
        return new PermitAllUrlProperties();
    }

}
