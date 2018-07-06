/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nycloud.auth.controller;
import com.nycloud.auth.model.UserDetails;
import com.nycloud.auth.service.JdbcUserDetailsService;
import com.nycloud.common.jwt.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class LoginController {


	@Autowired
	private JdbcUserDetailsService userDetailsService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestParam Map<String, String> parameters) {
		String username = parameters.get("username");
		String password = parameters.get("password");
		String client = parameters.get("client");
		if (StringUtils.isEmpty(username) ||
		 	StringUtils.isEmpty(password) ||
			StringUtils.isEmpty(client) ||
			password.length() < 6 ) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		UserDetails userDetails = userDetailsService.loadUserDetailsByUserName(username);
		if (userDetails != null && userDetails.getPassword().equals(password)) {
			// 获取用户的校色
			List<String> roles = userDetailsService.loadUserRolesByUserId(Long.valueOf(userDetails.getUserId()));
			Map<String, Object> map = new HashMap<>();
			map.put("userId", userDetails.getUserId());
			map.put("username", userDetails.getUsername());
			map.put("enabled", userDetails.isEnabled());
			map.put("roles", String.join(",", roles));
			String token = JwtUtil.generateToken(map);
			return new ResponseEntity<String>(token, HttpStatus.OK);
		}
		return new ResponseEntity<String>("用户名或密码错误", HttpStatus.FORBIDDEN);
	}




}

