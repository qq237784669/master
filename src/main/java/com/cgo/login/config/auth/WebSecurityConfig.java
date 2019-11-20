package com.cgo.login.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
//这个类主要是做一个  授权之前的认证  如果美誉这个的话 直接去授权拿  认证码 的话 会报错  必须在授权之前
// 先认证   认证后 才能 拿授权码   -------也就是 在拿授权码 之前 做一个认证
// 即是  授权服务器 也是资源服务器
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/user/**").permitAll()
                .anyRequest().authenticated()
                // .anyRequest().permitAll()
                .and().formLogin();
    }
}
