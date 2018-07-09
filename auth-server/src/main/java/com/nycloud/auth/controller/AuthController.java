package com.nycloud.auth.controller;

import com.nycloud.auth.model.Login;
import com.nycloud.auth.model.UserDetails;
import com.nycloud.auth.service.JdbcUserDetailsService;
import com.nycloud.common.constants.Constant;
import com.nycloud.common.jwt.JwtEntity;
import com.nycloud.common.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author super.wu
 */
@RestController
public class AuthController {

	@Autowired
	private JdbcUserDetailsService userDetailsService;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@PostMapping("/login")
	public ResponseEntity login(@Validated @RequestBody Login login, BindingResult bindingResult) {
		if (bindingResult.hasErrors() ) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		UserDetails userDetails = userDetailsService.loadUserDetailsByUserName(login.getUsername());
		if (userDetails == null || !userDetails.isEnabled()) {
			return new ResponseEntity<String>("账号不可用", HttpStatus.FORBIDDEN);
		}
		if (userDetails.getPassword().equals(login.getPassword())) {
			// 获取用户的角色
			Map<String, Object> map = null;
			String token = null;
			if (redisTemplate.hasKey(userDetails.getUserId())) {
				map = (Map<String, Object>) redisTemplate.opsForValue().get(userDetails.getUserId());
				token = map.get("token").toString();
			} else {
				List<String> roles = userDetailsService.loadUserRolesByUserId(Long.valueOf(userDetails.getUserId()));
				map = new HashMap<>(5);
				map.put("userId", userDetails.getUserId());
				map.put("username", userDetails.getUsername());
				map.put("enabled", userDetails.isEnabled());
				map.put("roles", String.join(",", roles));
				token = JwtUtil.generateToken(map);
				map.put("token", token);
				redisTemplate.opsForValue().set(userDetails.getUserId(), map);
			}
			return new ResponseEntity<String>(token, HttpStatus.OK);
		}
		return new ResponseEntity<String>("用户名或密码错误", HttpStatus.FORBIDDEN);
	}

	@PostMapping("/logout")
	public ResponseEntity loginOut(@RequestHeader(value = "Authorization") String Authorization) {
		if (JwtUtil.isJwtBearerToken(Authorization)) {
			JwtEntity jwtEntity = JwtUtil.parseToken(Authorization);
			if (redisTemplate.hasKey(jwtEntity.getUserId())) {
				// 登出后清楚缓存
				redisTemplate.delete(jwtEntity.getUserId());
			}
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/check_token")
	public Map<String, ?> checkToken(@RequestParam(value = "userId") String userId,
									 @RequestParam(value = "authorization") String authorization) {
		Map<String, Object> response = new HashMap<>(1);
		boolean active = false;
		if (redisTemplate.hasKey(userId)) {
			Map<String, Object> map = (Map<String, Object>) redisTemplate.opsForValue().get(userId);
			if (map.containsKey(Constant.TOKEN) && authorization.equals(map.get(Constant.TOKEN).toString())) {
				active = true;
			}
		}
		response.put(Constant.ACTIVE, active);
		return response;
	}

}

