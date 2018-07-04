/**
 * Copyright(c) Cloudolp Technology Co.,Ltd
 * All Rights Reserved.
 * <p>
 * This software is the confidential and proprietary information of Cloudolp
 * Technology Co.,Ltd. ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with Cloudolp.
 * For more information about Cloudolp, welcome to http://www.cloudolp.com
 * <p>
 * project: peony-spring
 * <p>
 * Revision History:
 * Date		Version		Name				Description
 * 5/3/2016	1.0			Franklin			Creation File
 */
package com.nycloud.admin.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * description:
 * 
 * 
 * @author Franklin
 * @date Jul 10, 2015 2:45:42 PM
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	public static final String API_PATH = "/api/**";

	private Logger logger = LoggerFactory.getLogger(WebConfiguration.class);

	@Autowired
	private SecurityInterceptor securityInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		logger.debug("Add interceptor securityInterceptor");
		registry.addInterceptor(securityInterceptor).addPathPatterns(API_PATH);
	}

}
