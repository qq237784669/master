package com.cgo.login.config.auth;


import com.alibaba.fastjson.JSON;
import com.cgo.login.vo.resp.UserExt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.LinkedHashMap;
import java.util.Map;


public class UserJwtExpansion extends DefaultUserAuthenticationConverter {


    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap();

        UserExt principal=null;
        if (authentication.getPrincipal() instanceof UserExt){
            principal = (UserExt) authentication.getPrincipal();
        }else {
            return super.convertUserAuthentication(authentication);
        }

        response.putAll(   JSON.parseObject(JSON.toJSONString(principal), Map.class)   );

        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }

        return response;
    }
}
