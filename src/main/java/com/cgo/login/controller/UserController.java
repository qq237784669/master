package com.cgo.login.controller;


import com.alibaba.fastjson.JSON;
import com.cgo.login.service.LoginService;
import com.cgo.login.vo.req.LoginRequest;
import com.cgo.login.vo.resp.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    @Autowired
    LoginService loginService;

    // 登录接口
    @RequestMapping("/login")
    public Object login(@RequestBody LoginRequest loginRequest){
        Map map = new HashMap<>();
        try {
            String access_token = loginService.login(loginRequest);
            map.put("status","200");
            map.put("access_token",access_token);
        }catch (Exception e){
            map.put("status","100");
            map.put("access_token","");
           log.error(" login error >>>  ",e);
        }
        return map;
    }


    // 将jwt 转换为  用户信息 接口
    @RequestMapping("/userInfo")
    public Object userInfo(@RequestBody Map<String,String> map){
        String access_token = map.get("access_token");
        Jwt decode = JwtHelper.decode(access_token);
        String claims = decode.getClaims();
        return JSON.parseObject(claims, LoginResponse.class);
    }



}
