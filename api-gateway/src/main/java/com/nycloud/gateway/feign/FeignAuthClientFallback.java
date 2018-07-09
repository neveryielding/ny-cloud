package com.nycloud.gateway.feign;

import com.nycloud.common.constants.Constant;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/7/9 0009
 * @modified By:
 * @version: 1.0
 **/
@Component
public class FeignAuthClientFallback implements FeignAuthClient {

    @Override
    public Map<String, ?> checkToken(String userId, String Authorization) {
        return new HashMap<String, Boolean>(1) {{
            put(Constant.ACTIVE, false);
        }};
    }
}


