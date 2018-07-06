/*******************************************************************************
 *     Cloud Foundry 
 *     Copyright (c) [2009-2014] Pivotal Software, Inc. All Rights Reserved.
 *
 *     This product is licensed to you under the Apache License, Version 2.0 (the "License").
 *     You may not use this product except in compliance with the License.
 *
 *     This product includes a number of subcomponents with
 *     separate copyright notices and license terms. Your use of these
 *     subcomponents is subject to the terms and conditions of the
 *     subcomponent's license, as noted in the LICENSE file.
 *******************************************************************************/
package com.nycloud.auth.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Controller which decodes access tokens for clients who are not able to do so (or where opaque token values are used).
 * 
 * @author Luke Taylor
 * @author Joel D'sa
 */
@RestController
public class CheckTokenEndpoint {


	@RequestMapping(value = "/oauth/check_token", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ?> checkToken(String token) {
		Map<String, Object> response = new HashMap<>(1);

		// gh-1070
		response.put("active", true);	// Always true if token exists and not expired
		return response;
	}


}
