package com.cgo.login.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class KeyConfig {
    @Value("${key.keyFileName}")
    String keyFileName;

    //密钥密码
    @Value("${key.pwd}")
    String pwd;

    //密钥库 密码
    @Value("${key.storePwd}")
    String storePwd;

    //密钥库 密码
    @Value("${key.alias}")
    String alias;
}
