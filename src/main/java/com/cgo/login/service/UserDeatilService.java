package com.cgo.login.service;

import com.alibaba.fastjson.JSON;
import com.cgo.login.vo.resp.UserExt;
import com.cgo.login.vo.req.LoginRequest;
import com.cgo.login.vo.resp.LoginResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 *  spring security 回调的接口 此函数验证 密码 是否正确
 */
@Service
public class UserDeatilService implements UserDetailsService {


    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private TokenStore tokenStore;


    @Autowired
    private UserService userService;



    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 第一次验证 clientId
        if (authentication==null){
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(userId);
            return new User(userId,"{bcrypt}"+clientDetails.getClientSecret(),AuthorityUtils.createAuthorityList(""));
        }


        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        LoginRequest loginRequest = JSON.parseObject(request.getParameter("loginRequest"), LoginRequest.class);
        LoginResponse data=null;
        try {
            // 使用 你写的 方法 来获取用户 权限信息
             data = (LoginResponse) userService.login(loginRequest).getData();
        } catch (SQLException e) {
            e.printStackTrace();
            return new UserExt(userId,"", null);
        }
        //  no exception is  pwd ok
        String password = loginRequest.getPassword();
        UserExt userExt = new UserExt(userId, "{bcrypt}" + new BCryptPasswordEncoder().encode(password), AuthorityUtils.createAuthorityList("USER"));
        BeanUtils.copyProperties(data,userExt);
        return userExt;
    }



}
